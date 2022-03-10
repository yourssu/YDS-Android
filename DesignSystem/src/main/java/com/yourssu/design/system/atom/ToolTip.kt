package com.yourssu.design.system.atom

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.BitmapDrawable
import android.util.DisplayMetrics
import android.util.Log
import android.view.*

import android.view.animation.AlphaAnimation
import android.widget.PopupWindow
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat

import com.yourssu.design.R

import com.yourssu.design.databinding.LayoutTooltipBinding
import com.yourssu.design.undercarriage.size.dpToPx


class ToolTip private constructor(
    private val context: Context,
    private val windowManager: WindowManager,
    private val layoutInflater: LayoutInflater,
    private var referenceView: View,
    private var isNormal: Boolean, //changeIsNormal로만 바꾸도록. 지금 왜 setter오버라이드가 안되지
    private var hopeLocation: Int?
) {

    class Builder(
        val context: Context,
        val windowManager: WindowManager, //화면크기를 알기위해
        val layoutInflater: LayoutInflater,
        val referenceView: View, //해당 툴팁이 붙고싶어하는 뷰
        val isNormal: Boolean,
        val text: String? = "눌러보세요",
        val hopeLocation: Int? = -1
    ) {
        fun build(): ToolTip {
            val t = ToolTip(
                context,
                windowManager,
                layoutInflater,
                referenceView = referenceView,
                isNormal = isNormal,
                hopeLocation
            )
            t.text = text!!
            return t
        }
    }

    private lateinit var popup: PopupWindow
    private var popupX = 0f
    private var popUpY = 0f
    private lateinit var binding: LayoutTooltipBinding

    private var statusBarHeight = 0
    private val referenceViewLocation = IntArray(2) //참조할 뷰의 위치정보를 기억
    private var mWidthPixels = 0 //화면 정보를 얻음
    private var mHeightPixels = 0 //화면 정보를 얻음
    private val d = windowManager.getDefaultDisplay() as Display
    private val metrics = DisplayMetrics()

    //리스너 만들어 줘야함.

    private var Length: Float? = null

    var text: CharSequence
        get() {
            return binding.textBox.text
        }
        set(value: CharSequence) {
            if (when (binding.textBox.text.length) { //기존의 문자열길이와
                    in 0..14 -> ToolTip.ShortTooltip
                    in 15..24 -> ToolTip.MediumTooltip
                    else -> ToolTip.LongTooltip
                } >= when (value.length) {          //새로 바꿀 문자열의 길이를 대조.
                    in 0..14 -> ToolTip.ShortTooltip
                    in 15..24 -> ToolTip.MediumTooltip
                    else -> ToolTip.LongTooltip
                }
            ) {   // 기존텍스트길이가 더 크거나 같다면 위치는 재계산 필요 없음.
                setBackgroundSize() //문자열 길이에 맞게 조정.
                binding.textBox.text = value as String
            } else {
                binding.textBox.text = value as String
                calculateTooltipPosition()
                //새로 바뀐 문자열이 기존보다 훨씬 길다면 백그라운드 이미지 다시 계산하고 위치 조정
            }
        }


    fun changeReferenceView(view: View) {
        referenceView = view
        referenceView.getLocationOnScreen(referenceViewLocation)

        Log.d(
            "kmj",
            " 참조뷰 스크린 상 좌표: ${referenceViewLocation[0]} ${referenceViewLocation[1]} "
        )
        calculateTooltipPosition() //참조뷰가 바뀌면 재계산해서 위치 결정.
    }

    fun changeIsNormal(boolean: Boolean) {
        isNormal = boolean
        binding.textBox.backgroundTintList = when (isNormal) {
            true -> ColorStateList.valueOf(
                ContextCompat.getColor(
                    context,
                    R.color.buttonNormal
                )
            )
            false -> ColorStateList.valueOf(
                ContextCompat.getColor(
                    context,
                    R.color.violetItemText  //일단은 이거로 설정
                )
            )
        }

        binding.tail.backgroundTintList = when (isNormal) {
            true -> ColorStateList.valueOf(
                ContextCompat.getColor(
                    context,
                    R.color.buttonNormal
                )
            )
            false -> ColorStateList.valueOf(
                ContextCompat.getColor(
                    context,
                    R.color.violetItemText  //일단은 이거로 설정
                )
            )
        }

    }


    init {
        initView()
    }

    private fun initView() {

        inflateLayout(context)
        setScreenSize() //스크린 크기 저장함
        changeReferenceView(referenceView) //참조뷰 크기 저장.
        calculateTooltipPosition() //크기, 색상, 위치 지정.

        //팝업의 크기 설정. 매치패런트로 해야 호면 전체에 터치 리스너를 달 준비 완료.
        popup.setWindowLayoutMode(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        );
        //팝업 뷰 터치 되도록
        popup.setTouchable(true)
        //팝업 뷰 포커스도 주고
        popup.setFocusable(true)
        //팝업 뷰 이외에도 터치되게 (터치시 팝업 닫기 위한 코드)
        popup.setOutsideTouchable(true)
        popup.setBackgroundDrawable(BitmapDrawable())
    }


    private fun inflateLayout(context: Context) {

        binding = LayoutTooltipBinding.inflate(layoutInflater)
        popup = PopupWindow(referenceView);
        popup.setContentView(binding.root)

        binding.relativeLayout.setOnClickListener {
            val animation = AlphaAnimation(1.0f, 0.0f)
            animation.duration = 2500
            binding.relativeLayout.startAnimation(animation)
            popup.dismiss()
        }

    }

    //초기에 한번만 동작.
    private fun setScreenSize() {
        //스크린 크기 저장
        d.getMetrics(metrics)
        mWidthPixels = metrics.widthPixels
        mHeightPixels = metrics.heightPixels

        //상단의 상태바 높이 구하기
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = context.resources.getDimensionPixelSize(resourceId)
        }
        Log.d("kmj", "상태바 높이:" + statusBarHeight)
    }

    //텍스트 길이에 따른 알맞은 백그라운드를 선택. 그리고 가로 길이 저장.
    private fun setBackgroundSize() {
        if (text.length in 0..14) {
            Length = ToolTip.ShortTooltip
            binding.textBox.setBackgroundResource(R.drawable.tooltip_short_background)
        } else if (text.length in 15..24) {
            Length = ToolTip.MediumTooltip
            binding.textBox.setBackgroundResource(R.drawable.tooltip_medium_background)
        } else { //25~34자까지. 넘어가면 어차피 안나옴. maxLength=34 로 설정했음.
            Length = ToolTip.LongTooltip
            binding.textBox.setBackgroundResource(R.drawable.tooltip_long_background)
        }
    }

    private fun calculateTooltipPosition() {
        setBackgroundSize() //글자길이에 맞춰서 백그라운드 크기 지정
        changeIsNormal(isNormal) //틴트색 결정.

//        (binding.tail.layoutParams as RelativeLayout.LayoutParams).addRule(
//            RelativeLayout.BELOW,
//            binding.textBox.id
//        )

        var canSetHopeLocation = false
        Log.d("kmj", "희망위치" + hopeLocation)
        if ((hopeLocation!! >= 0) && hopeLocation!! <= 3) { //희망하는 위치가 있다면
            when (hopeLocation) {
                0 -> {                 //위에 올라갈 자리가 있는지 검사
                    if ((referenceViewLocation[1] - statusBarHeight) > context.dpToPx(50f)) {
                        canSetHopeLocation = true //위에 놓을 수 있음.
                        binding.tail.setBackgroundResource(R.drawable.tooltip_tail_bottom)
                        (binding.tail.layoutParams as RelativeLayout.LayoutParams).addRule(
                            RelativeLayout.BELOW,
                            binding.textBox.id
                        )
                        if ((referenceViewLocation[0] + referenceView.width / 2) > context.dpToPx(
                                Length!!
                            ) / 2 &&
                            (mWidthPixels - referenceViewLocation[0] - referenceView.width / 2) > context.dpToPx(
                                Length!!
                            ) / 2
                        ) {                  //뷰의 중앙 위에 올라갈 수 있는 경우에는 꼬리를 중앙아래에 달면 됨.
                            (binding.tail.layoutParams as RelativeLayout.LayoutParams).addRule(
                                RelativeLayout.CENTER_HORIZONTAL
                            )
                            popupX =
                                referenceViewLocation[0] + referenceView.width - (referenceView.width / 2) - context.dpToPx(
                                    Length!! / 2
                                )
                            popUpY =
                                referenceViewLocation[1] - context.dpToPx(49f) - statusBarHeight
                            binding.tooltip.x = popupX
                            binding.tooltip.y = popUpY
                        } else if ((referenceViewLocation[0] + (referenceView.width / 2)) > mWidthPixels / 2) {
                            //만약 화면내의 오른쪽에 참조뷰가 치우쳐져 있다
                            (binding.tail.layoutParams as RelativeLayout.LayoutParams).addRule(
                                RelativeLayout.ALIGN_RIGHT,
                                binding.textBox.id
                            )
                            popupX =
                                mWidthPixels - context.dpToPx(Length!!)
                            popUpY =
                                referenceViewLocation[1] - context.dpToPx(49f) - statusBarHeight
                            binding.tooltip.x = popupX
                            binding.tooltip.y = popUpY

                            //꼬리가 참조뷰의 중앙을 가리키도록 조정.
                            (binding.tail.layoutParams as RelativeLayout.LayoutParams).rightMargin =
                                mWidthPixels - referenceViewLocation[0] - (referenceView.width / 2) - context.dpToPx(
                                    14f
                                ).toInt()

                        } else if (true) {
                            //만약 화면내의 왼쪽에 참조뷰가 치우쳐져 있다
                            (binding.tail.layoutParams as RelativeLayout.LayoutParams).addRule(
                                RelativeLayout.ALIGN_LEFT,
                                binding.textBox.id
                            )
                            popupX = 0f
                            popUpY =
                                referenceViewLocation[1] - context.dpToPx(49f) - statusBarHeight
                            binding.tooltip.x = popupX
                            binding.tooltip.y = popUpY

                            //꼬리가 참조뷰의 중앙을 가리키도록 조정.
                            (binding.tail.layoutParams as RelativeLayout.LayoutParams).leftMargin =
                                referenceViewLocation[0] + (referenceView.width / 2) - context.dpToPx(
                                    14f
                                ).toInt()
                        }
                    }
                }
                1 -> {  // 참조뷰의 아래에 들어갈 자리가 있는지 검사
                    if ((mHeightPixels - (referenceViewLocation[1] + referenceView.height - statusBarHeight)) > context.dpToPx(
                            50f
                        )
                    ) {
                        canSetHopeLocation = true //아래에 놓을 수 있음.
                        binding.tail.setBackgroundResource(R.drawable.tooltip_tail_top) //수정필요
                        (binding.textBox.layoutParams as RelativeLayout.LayoutParams).addRule(
                            RelativeLayout.BELOW,
                            binding.tail.id
                        )
                        if ((referenceViewLocation[0] + referenceView.width / 2) > context.dpToPx(
                                Length!!
                            ) / 2 &&
                            (mWidthPixels - referenceViewLocation[0] - referenceView.width / 2) > context.dpToPx(
                                Length!!
                            ) / 2
                        ) {                  //뷰의 중앙 위에 올라갈 수 있는 경우에는 꼬리를 중앙위에 달면 됨.
                            (binding.tail.layoutParams as RelativeLayout.LayoutParams).addRule(
                                RelativeLayout.CENTER_HORIZONTAL
                            )
                            popupX =
                                referenceViewLocation[0] + referenceView.width - (referenceView.width / 2) - context.dpToPx(
                                    Length!! / 2
                                )
                            popUpY =
                                (referenceViewLocation[1] - statusBarHeight + referenceView.height).toFloat()
                            binding.tooltip.x = popupX
                            binding.tooltip.y = popUpY
                        } else if ((referenceViewLocation[0] + (referenceView.width / 2)) > mWidthPixels / 2) {
                            //만약 화면내의 오른쪽에 참조뷰가 치우쳐져 있다
                            (binding.tail.layoutParams as RelativeLayout.LayoutParams).addRule(
                                RelativeLayout.ALIGN_RIGHT,
                                binding.textBox.id
                            )
                            popupX =
                                mWidthPixels - context.dpToPx(Length!!)
                            popUpY =
                                (referenceViewLocation[1] - statusBarHeight + referenceView.height).toFloat()
                            binding.tooltip.x = popupX
                            binding.tooltip.y = popUpY

                            //꼬리가 참조뷰의 중앙을 가리키도록 조정.
                            (binding.tail.layoutParams as RelativeLayout.LayoutParams).rightMargin =
                                mWidthPixels - referenceViewLocation[0] - (referenceView.width / 2) - context.dpToPx(
                                    14f
                                ).toInt()

                        } else if (true) {
                            //만약 화면내의 왼쪽에 참조뷰가 치우쳐져 있다
                            (binding.tail.layoutParams as RelativeLayout.LayoutParams).addRule(
                                RelativeLayout.ALIGN_LEFT,
                                binding.textBox.id
                            )
                            popupX = 0f
                            popUpY =
                                (referenceViewLocation[1] - statusBarHeight + referenceView.height).toFloat()
                            binding.tooltip.x = popupX
                            binding.tooltip.y = popUpY

                            //꼬리가 참조뷰의 중앙을 가리키도록 조정.
                            (binding.tail.layoutParams as RelativeLayout.LayoutParams).leftMargin =
                                referenceViewLocation[0] + (referenceView.width / 2) - context.dpToPx(
                                    14f
                                ).toInt()
                        }
                    }
                }
                2 -> {
                }
                3 -> {
                }
            }
        }

        if (!canSetHopeLocation) {

            popupX =
                referenceViewLocation[0] + referenceView.width - (referenceView.width / 2) - context.dpToPx(
                    Length!! / 2
                )
            popUpY = referenceViewLocation[1] - context.dpToPx(49f) - statusBarHeight
            binding.tooltip.x = popupX
            binding.tooltip.y = popUpY
            Log.d("kmj", "팝업 x:" + popupX + ", 팝업 y:" + popUpY)
        }

    }

    private var isShowOnce = false
    fun show() {
        if (!isShowOnce) {
            popup.showAtLocation(referenceView, Gravity.NO_GRAVITY, 0, 0)
            isShowOnce = true //이미 한번 띄웠음을 의미함.
        }
    }


    companion object {

        //해당 커스텀뷰의 가로세로 크기를 상수로 만들기. 나중에 활용함
        const val ShortTooltip = 143f
        const val MediumTooltip = 210f
        const val LongTooltip = 347f

        const val ABOVE = 0
        const val BELOW = 1
        const val RIGHT_SIDE = 2
        const val LEFT_SIDE = 3


//        //Position에 쓰이는 변수들 테일이 위쪽인지 아래쪽인지
//        const val Position_TopTail = 0
//        const val Position_BottomTail = 1
//        const val Position_SideTail_left = 2 //테일이 왼쪽 사이드에 있음
//        const val Position_SideTail_right = 3 //테일이 오른쪽 사이드에 있음
//
//        //Tail 위치
//        const val Tail_left = 1
//        const val Tail_center = 0
//        const val Tail_right = 2
//        const val Tail_right_side = 3 //오른쪽측면에 꼬리
//        const val Tail_left_side = 4 //왼쪽측면에 꼬리

    }

    private class Range(val start: Int, val end: Int)


}