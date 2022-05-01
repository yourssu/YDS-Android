package com.yourssu.design.system.atom

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.view.animation.*

import android.widget.PopupWindow
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat

import com.yourssu.design.R

import com.yourssu.design.databinding.LayoutTooltipBinding
import com.yourssu.design.undercarriage.size.dpToIntPx
import com.yourssu.design.undercarriage.size.dpToPx
import java.lang.IllegalStateException
import kotlin.math.abs


class ToolTip private constructor(
    private val context: Context,
    private val windowManager: WindowManager,
    private val layoutInflater: LayoutInflater,
    private var referenceView: View,
    private var isNormal: Boolean, //changeIsNormal로만 바꾸도록. 지금 왜 setter오버라이드가 안되지
    private var textInit: String,
    private var hopeLocation: Int?,
) {

    //빌더를 위한 필수 변수들 세개. 참조뷰를 여기 안넣은 이유는 하나의 빌더를 생성하고
    //참조뷰만 매번 build(참조뷰)호출하는 방법으로 갈아 끼울 수 있게 하기 위해...
    class Builder(
        var context: Context,
        var windowManager: WindowManager, //화면크기를 알기위해
        var layoutInflater: LayoutInflater
    ) {

        lateinit var referenceView: View //해당 툴팁이 붙고싶어하는 뷰
        var isNormal: Boolean = true
        var stringContents: String = "눌러보세요"
        var hopeLocation: Int = -1

        fun withContext(context: Context): Builder {
            this.context = context
            return this
        }

        fun withWindowManager(windowManager: WindowManager): Builder {
            this.windowManager = windowManager
            return this
        }

        fun withLayoutInflater(layoutInflater: LayoutInflater): Builder {
            this.layoutInflater = layoutInflater
            return this
        }

        fun withIsNormal(isNormal: Boolean): Builder {
            this.isNormal = isNormal
            return this
        }

        fun withStringContents(string: String): Builder {
            this.stringContents = string
            return this
        }

        fun withHopeLocation(hopeLocation: Int): Builder {
            this.hopeLocation = hopeLocation
            return this
        }

        //최종적으로 build를 호출하기 위해서는 참조뷰를 인자로 줘야함.
        fun build(referView: View): ToolTip {
            referenceView = referView

            return ToolTip(
                context,
                windowManager, //화면크기를 알기위해
                layoutInflater,
                referenceView, //해당 툴팁이 붙고싶어하는 뷰
                isNormal,
                stringContents,
                hopeLocation
            )
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
    private val d = windowManager.defaultDisplay as Display
    private val metrics = DisplayMetrics()
    private var animDuration = 0L
    //리스너 만들어 줘야함.

    private var Length: Float= ShortTooltip

    var text: CharSequence
        get() {
            return binding.textBox.text
        }
        set(value: CharSequence) {
            val vLength = when (value.toString().length) {
                in 0..14 -> ToolTip.ShortTooltip
                in 15..24 -> ToolTip.MediumTooltip
                else -> ToolTip.LongTooltip
            }
            if (Length != vLength) { //길이가 다르면 다시 생성 필요.
                popup.dismiss() //기존 팝업 삭제
                textInit = value.toString()
                initView(referenceView, textInit) //새로 팝업 객체 생성
                isShowOnce = false
                show() //다시 만든 팝업 객체 다시 띄우기
            } else { //바꿀 문자열과 길이가 둘이 같다면....그냥 텍스트만 수정해주면 됨.
                binding.textBox.text = value.toString()
            }
        }

    //기존 팝업을 제거하고 참조뷰 바꿔서 새로 팝업 띄워야 하는 상황에 사용.
    //그냥 새로 툴팁을 처음부터 만드는게 낫지 않을까 싶지만 일단 기능은 만들었음.
    fun changeReferenceView(view: View) {
        referenceView = view
        referenceView.getLocationOnScreen(referenceViewLocation)
        //참조뷰에 대한 정보를 갱신
        popup.dismiss()
        initView(referenceView, textInit)
        isShowOnce = false
        show() //다시 만든 팝업 객체 다시 띄우기
    }

    fun changeIsNormal(boolean: Boolean) {
        isNormal = boolean
        binding.textBox.backgroundTintList = when (isNormal) {
            true -> ColorStateList.valueOf(
                ContextCompat.getColor(
                    context,
                    R.color.tooltipBG
                )
            )
            else -> ColorStateList.valueOf(
                ContextCompat.getColor(
                    context,
                    R.color.tooltipPoint  //일단은 이거로 설정
                )
            )
        }

        binding.tail.backgroundTintList = when (isNormal) {
            true -> ColorStateList.valueOf(
                ContextCompat.getColor(
                    context,
                    R.color.tooltipBG
                )
            )
            false -> ColorStateList.valueOf(
                ContextCompat.getColor(
                    context,
                    R.color.tooltipPoint  //일단은 이거로 설정
                )
            )
        }

    }


    init {
        setScreenSize() //스크린 크기 저장함
        initView(referenceView, textInit) //하나의 팝업 생성
    }

    //참조뷰나 스트링이 바뀌면 다시 호출돼서 팝업윈도우 재생성 될수도있음.
    private fun initView(reference: View, txt: String) { //팝업객체생성

        inflateLayout(context, reference) //해당 참조뷰에 대한 팝업객체 생성하고 binding 초기화

        binding.textBox.text = txt //문자열 변경

        reference.getLocationOnScreen(referenceViewLocation) //참조뷰에 대한 위치값 수정.
        calculateTooltipPosition() //위치 계산

        popup.isOutsideTouchable = true
        popup.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) // make outside area touchable
    }


    private fun inflateLayout(context: Context, referenceView: View) {

        binding = LayoutTooltipBinding.inflate(layoutInflater)

        popup = PopupWindow(
            binding.root,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )


        var isRemove = false
        popup.setTouchInterceptor(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                if (!isRemove) {
                    when (event?.action) {
                        MotionEvent.ACTION_DOWN -> {

                        }
                        MotionEvent.ACTION_UP -> {
                            isRemove = true
                            val animation = AlphaAnimation(1.0f, 0.0f)
                            animation.duration = animDuration
                            binding.relativeLayout.startAnimation(animation) //애니메이션 시작.
                            val handler = Handler()
                            handler.postDelayed({
                                popup.dismiss()
                            }, animDuration - 10L)
                        }
                        MotionEvent.ACTION_OUTSIDE -> {
                            isRemove = true
                            val animation = AlphaAnimation(1.0f, 0.0f)
                            animation.duration = animDuration
                            binding.relativeLayout.startAnimation(animation) //애니메이션 시작.
                            val handler = Handler()
                            handler.postDelayed({
                                popup.dismiss()
                            }, animDuration - 10L)
                        }
                    }
                }

                return true
            }
        })
    }


    //초기에 한번만 동작.
    private fun setScreenSize() {
        //스크린 크기 저장
        d.getMetrics(metrics)
        mWidthPixels = metrics.widthPixels
        mHeightPixels = metrics.heightPixels
        //상단의 상태바 높이 구하기
        val resourceId =
            context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = context.resources.getDimensionPixelSize(resourceId)
        }

    }

    //텍스트 길이에 따른 알맞은 백그라운드를 선택. 그리고 가로 길이 저장.
    private fun setBackgroundSize() {
        if (text.length in 0..14) {
            Length = ToolTip.ShortTooltip
            binding.textBox.setBackgroundResource(R.drawable.tooltip_short_background)
            (binding.textBox.layoutParams as RelativeLayout.LayoutParams).run {
                width = context.dpToIntPx(ShortTooltip)
            }
            animDuration = 150L
        } else if (text.length in 15..24) {
            Length = ToolTip.MediumTooltip
            binding.textBox.setBackgroundResource(R.drawable.tooltip_medium_background)
            (binding.textBox.layoutParams as RelativeLayout.LayoutParams).run {
                width = context.dpToIntPx(MediumTooltip)
            }
            animDuration = 250L
        } else if (text.length in 25..35) { //25~35자까지. 넘어가면 어차피 안나옴. maxLength=35 로 설정했음.
            Length = ToolTip.LongTooltip
            binding.textBox.setBackgroundResource(R.drawable.tooltip_long_background)
            (binding.textBox.layoutParams as RelativeLayout.LayoutParams).run {
                width = context.dpToIntPx(LongTooltip)
            }
            animDuration = 300L
        }
    }

    private fun calculateTooltipPosition() {
        setBackgroundSize() //글자길이에 맞춰서 백그라운드 크기 지정
        changeIsNormal(isNormal) //틴트색 결정.

        var canSetHopeLocation = hopeLocationSet()  //희망하는 위치에 설정

        //희망하는 자리가 없거나
        //희망하는 위치에 놓을 수 없다면..... 가능한 곳에 배치시키기.
        if (!canSetHopeLocation) {
            val leftByReferView = referenceViewLocation[0]
            val rightByReferView = mWidthPixels - referenceViewLocation[0] - referenceView.width
            val aboveByReferView = referenceViewLocation[1] - statusBarHeight
            val belowByReferView =
                mHeightPixels - referenceViewLocation[1] - referenceView.height - statusBarHeight
            if (aboveByReferView > context.dpToPx(58f)) {
                //위에  배치시키기
                hopeLocation = ABOVE //희망자리 수정.
                hopeLocationSet() //재귀 호출
            } else if (belowByReferView > context.dpToPx(58f)) {
                //아래에 배치시키기
                hopeLocation = BELOW
                hopeLocationSet()
            } else if ((rightByReferView > context.dpToPx(Length!! + 10f)) &&
                mHeightPixels - referenceViewLocation[1] - referenceView.height / 2 > context.dpToPx(
                    25f
                ) &&
                referenceViewLocation[1] + referenceView.height / 2 - statusBarHeight > context.dpToPx(
                    25f
                )
            ) {
                hopeLocation = RIGHT_SIDE
                hopeLocationSet()
            } else if ((leftByReferView > context.dpToPx(Length!! + 10f)) &&
                mHeightPixels - referenceViewLocation[1] - referenceView.height / 2 > context.dpToPx(
                    25f
                ) &&
                referenceViewLocation[1] + referenceView.height / 2 - statusBarHeight > context.dpToPx(
                    25f
                )
            ) {
                hopeLocation = LEFT_SIDE
                hopeLocationSet()
            } else { //다 안된다면..... 최후의 수단. 이런경우는 거의 드물것임. 애초에 이런경우는 툴팁을 안쓰는걸 권장.
                (binding.tail.layoutParams as RelativeLayout.LayoutParams).height =
                    context.dpToPx(17f).toInt()
                binding.tail.setBackgroundResource(R.drawable.tooltip_tail_bottom)
                (binding.tail.layoutParams as RelativeLayout.LayoutParams).addRule(
                    RelativeLayout.BELOW,
                    binding.textBox.id
                )
                if ((referenceViewLocation[0] + referenceView.width / 2) > context.dpToPx(
                        Length
                    ) / 2 &&
                    (mWidthPixels - referenceViewLocation[0] - referenceView.width / 2) > context.dpToPx(
                        Length
                    ) / 2
                ) {                  //뷰의 중앙에 있을 수 있는 경우
                    (binding.tail.layoutParams as RelativeLayout.LayoutParams).addRule(
                        RelativeLayout.CENTER_HORIZONTAL
                    )
                    popupX =
                        referenceViewLocation[0] + referenceView.width - (referenceView.width / 2) - context.dpToPx(
                            Length / 2
                        )
                    popUpY = 0f //최후의 수단의 대책임. 그냥 위에 달아버리기

                } else if ((referenceViewLocation[0] + (referenceView.width / 2)) > mWidthPixels / 2) {
                    (binding.tail.layoutParams as RelativeLayout.LayoutParams).addRule(
                        RelativeLayout.ALIGN_RIGHT,
                        binding.textBox.id
                    )
                    popupX =
                        mWidthPixels - context.dpToPx(Length)
                    popUpY = 0f //최후의 수단.

                    //꼬리가 참조뷰의 중앙을 가리키도록 조정.
                    (binding.tail.layoutParams as RelativeLayout.LayoutParams).rightMargin =
                        mWidthPixels - referenceViewLocation[0] - (referenceView.width / 2) - context.dpToPx(
                            14f
                        ).toInt()

                } else {
                    //만약 화면내의 왼쪽에 참조뷰가 치우쳐져 있다
                    (binding.tail.layoutParams as RelativeLayout.LayoutParams).addRule(
                        RelativeLayout.ALIGN_LEFT,
                        binding.textBox.id
                    )
                    popupX = 0f
                    popUpY = 0f

                    //꼬리가 참조뷰의 중앙을 가리키도록 조정.
                    (binding.tail.layoutParams as RelativeLayout.LayoutParams).leftMargin =
                        referenceViewLocation[0] + (referenceView.width / 2) - context.dpToPx(
                            14f
                        ).toInt()
                }
            }
        }

    }

    fun hopeLocationSet(): Boolean {
        var canSetHopeLocation = false

        if ((hopeLocation!! >= 0) && hopeLocation!! <= 3) { //희망하는 위치가 있다면
            when (hopeLocation) {
                ABOVE -> {                 //위에 올라갈 자리가 있는지 검사
                    if ((referenceViewLocation[1] - statusBarHeight) > context.dpToPx(58f)) {
                        canSetHopeLocation = true //위에 놓을 수 있음.
                        (binding.tail.layoutParams as RelativeLayout.LayoutParams).height =
                            context.dpToPx(9f).toInt()
                        binding.tail.setBackgroundResource(R.drawable.tooltip_tail_bottom)

                        (binding.tail.layoutParams as RelativeLayout.LayoutParams).addRule(
                            RelativeLayout.BELOW,
                            binding.textBox.id
                        )

                        if ((referenceViewLocation[0] + referenceView.width / 2) > context.dpToPx(
                                Length
                            ) / 2 &&
                            (mWidthPixels - referenceViewLocation[0] - referenceView.width / 2) > context.dpToPx(
                                Length
                            ) / 2
                        ) {                  //뷰의 중앙 위에 올라갈 수 있는 경우에는 꼬리를 중앙아래에 달면 됨.
                            (binding.tail.layoutParams as RelativeLayout.LayoutParams).addRule(
                                RelativeLayout.CENTER_HORIZONTAL
                            )
                            popupX =
                                referenceViewLocation[0] + (referenceView.width / 2) - context.dpToPx(
                                    Length
                                ) / 2

                            popUpY =
                                referenceViewLocation[1] - context.dpToPx(49f + 8f)

                        } else if ((referenceViewLocation[0] + (referenceView.width / 2)) > (mWidthPixels / 2)) {
                            //참조뷰가  우측에 붙어있음.
                            (binding.tail.layoutParams as RelativeLayout.LayoutParams).addRule(
                                RelativeLayout.ALIGN_RIGHT,
                                binding.textBox.id
                            )
                            //추가한 코드. 만약 자연스럽게 배치가 가능하면 이렇게 배치하자.
                            if (referenceViewLocation[0] + referenceView.width / 2 > context.dpToPx(
                                    Length - 22f
                                ) &&
                                mWidthPixels - referenceViewLocation[0] - referenceView.width / 2 > context.dpToPx(
                                    22f
                                )
                            ) {
                                popupX =
                                    referenceViewLocation[0] + referenceView.width / 2 - context.dpToPx(
                                        Length - 22f
                                    )

                                popUpY =
                                    referenceViewLocation[1] - context.dpToPx(49f + 8f)

                                //꼬리가 참조뷰의 중앙을 가리키도록 조정.
                                (binding.tail.layoutParams as RelativeLayout.LayoutParams).rightMargin =
                                    context.dpToPx(
                                        8f
                                    ).toInt()
                            } else {
                                popupX =
                                    mWidthPixels - context.dpToPx(Length)

                                popUpY =
                                    referenceViewLocation[1] - context.dpToPx(49f + 8f)


                                //꼬리가 참조뷰의 중앙을 가리키도록 조정.
                                (binding.tail.layoutParams as RelativeLayout.LayoutParams).rightMargin =
                                    mWidthPixels - referenceViewLocation[0] - (referenceView.width / 2) - context.dpToPx(
                                        14f
                                    ).toInt()
                            }

                        } else if (true) {
                            //만약 화면내의 왼쪽에 참조뷰가 치우쳐져 있다
                            (binding.tail.layoutParams as RelativeLayout.LayoutParams).addRule(
                                RelativeLayout.ALIGN_LEFT,
                                binding.textBox.id
                            )

                            //추가한 코드. 만약 자연스럽게 배치가 가능하면 이렇게 배치하자.
                            if (referenceViewLocation[0] + referenceView.width / 2 > context.dpToPx(
                                    22f
                                ) &&
                                mWidthPixels - referenceViewLocation[0] - referenceView.width / 2 > context.dpToPx(
                                    Length - 22f
                                )
                            ) {
                                popupX =
                                    referenceViewLocation[0] + referenceView.width / 2 - context.dpToPx(
                                        22f
                                    )

                                popUpY =
                                    referenceViewLocation[1] - context.dpToPx(49f + 8f)

                                //꼬리가 참조뷰의 중앙을 가리키도록 조정.
                                (binding.tail.layoutParams as RelativeLayout.LayoutParams).leftMargin =
                                    context.dpToPx(
                                        8f
                                    ).toInt()
                            } else {
                                popupX = 0f
                                popUpY =
                                    referenceViewLocation[1] - context.dpToPx(49f + 8f)

                                //꼬리가 참조뷰의 중앙을 가리키도록 조정.
                                (binding.tail.layoutParams as RelativeLayout.LayoutParams).leftMargin =
                                    referenceViewLocation[0] + (referenceView.width / 2) - context.dpToPx(
                                        14f
                                    ).toInt()
                            }
                        }
                    }
                }
                BELOW -> {  // 참조뷰의 아래에 들어갈 자리가 있는지 검사
                    if ((mHeightPixels - (referenceViewLocation[1] + referenceView.height - statusBarHeight)) > context.dpToPx(
                            58f  //원래 57f지만 넉넉하게 1f 늘렸습니다.
                        )
                    ) {
                        canSetHopeLocation = true //아래에 놓을 수 있음.
                        (binding.tail.layoutParams as RelativeLayout.LayoutParams).height =
                            context.dpToPx(9f).toInt()
                        binding.tail.setBackgroundResource(R.drawable.tooltip_tail_top)
                        (binding.textBox.layoutParams as RelativeLayout.LayoutParams).addRule(
                            RelativeLayout.BELOW,
                            binding.tail.id
                        )
                        if ((referenceViewLocation[0] + referenceView.width / 2) > context.dpToPx(
                                Length
                            ) / 2 &&
                            (mWidthPixels - referenceViewLocation[0] - referenceView.width / 2) > context.dpToPx(
                                Length
                            ) / 2
                        ) {                  //뷰의 중앙 위에 올라갈 수 있는 경우에는 꼬리를 중앙위에 달면 됨.
                            (binding.tail.layoutParams as RelativeLayout.LayoutParams).addRule(
                                RelativeLayout.CENTER_HORIZONTAL
                            )
                            popupX =
                                referenceViewLocation[0] + (referenceView.width / 2) - context.dpToPx(
                                    Length
                                ) / 2

                            popUpY =
                                (referenceViewLocation[1] + referenceView.height).toFloat() + context.dpToPx(
                                    8f
                                )

                        } else if ((referenceViewLocation[0] + (referenceView.width / 2)) > mWidthPixels / 2) {
                            //만약 화면내의 오른쪽에 참조뷰가 치우쳐져 있다
                            (binding.tail.layoutParams as RelativeLayout.LayoutParams).addRule(
                                RelativeLayout.ALIGN_RIGHT,
                                binding.textBox.id
                            )

                            if (referenceViewLocation[0] + referenceView.width / 2 > context.dpToPx(
                                    Length - 22f
                                ) &&
                                mWidthPixels - referenceViewLocation[0] - referenceView.width / 2 > context.dpToPx(
                                    22f
                                )
                            ) {
                                popupX =
                                    referenceViewLocation[0] + referenceView.width / 2 - context.dpToPx(
                                        Length - 22f
                                    )

                                popUpY =
                                    (referenceViewLocation[1] + referenceView.height).toFloat() + context.dpToPx(
                                        8f
                                    )

                                //꼬리가 참조뷰의 중앙을 가리키도록 조정.
                                (binding.tail.layoutParams as RelativeLayout.LayoutParams).rightMargin =
                                    context.dpToPx(
                                        8f
                                    ).toInt()
                            } else {
                                popupX =
                                    mWidthPixels - context.dpToPx(Length)
                                popUpY =
                                    (referenceViewLocation[1] + referenceView.height).toFloat() + context.dpToPx(
                                        8f
                                    )


                                //꼬리가 참조뷰의 중앙을 가리키도록 조정.
                                (binding.tail.layoutParams as RelativeLayout.LayoutParams).rightMargin =
                                    mWidthPixels - referenceViewLocation[0] - (referenceView.width / 2) - context.dpToPx(
                                        14f
                                    ).toInt()
                            }

                        } else if (true) {
                            //만약 화면내의 왼쪽에 참조뷰가 치우쳐져 있다
                            (binding.tail.layoutParams as RelativeLayout.LayoutParams).addRule(
                                RelativeLayout.ALIGN_LEFT,
                                binding.textBox.id
                            )

                            if (referenceViewLocation[0] + referenceView.width / 2 > context.dpToPx(
                                    22f
                                ) &&
                                mWidthPixels - referenceViewLocation[0] - referenceView.width / 2 > context.dpToPx(
                                    Length - 22f
                                )
                            ) {
                                popupX =
                                    referenceViewLocation[0] + referenceView.width / 2 - context.dpToPx(
                                        22f
                                    )

                                popUpY =
                                    (referenceViewLocation[1] + referenceView.height).toFloat() + context.dpToPx(
                                        8f
                                    )

                                //꼬리가 참조뷰의 중앙을 가리키도록 조정.
                                (binding.tail.layoutParams as RelativeLayout.LayoutParams).leftMargin =
                                    context.dpToPx(
                                        8f
                                    ).toInt()
                            } else {
                                popupX = 0f
                                popUpY =
                                    (referenceViewLocation[1] + referenceView.height).toFloat() + context.dpToPx(
                                        8f
                                    )


                                //꼬리가 참조뷰의 중앙을 가리키도록 조정.
                                (binding.tail.layoutParams as RelativeLayout.LayoutParams).leftMargin =
                                    referenceViewLocation[0] + (referenceView.width / 2) - context.dpToPx(
                                        14f
                                    ).toInt()
                            }
                        }
                    }
                }
                RIGHT_SIDE -> {
                    //참조뷰의 오른쪽에 붙을 수 있는지 검사.
                    if (mWidthPixels - referenceViewLocation[0] - referenceView.width > context.dpToPx(
                            Length + 10f
                        ) &&
                        mHeightPixels - referenceViewLocation[1] - referenceView.height / 2 > context.dpToPx(
                            25f
                        ) &&
                        referenceViewLocation[1] + referenceView.height / 2 - statusBarHeight > context.dpToPx(
                            25f
                        )
                    ) {

                        canSetHopeLocation = true
                        //Length = Length!! + 10f //늘려줌.
                        binding.tail.setBackgroundResource(R.drawable.tooltip_tail_right_side) //수정필요


                        (binding.textBox.layoutParams as RelativeLayout.LayoutParams).addRule(
                            RelativeLayout.RIGHT_OF,
                            binding.tail.id
                        )

                        //툴팁내의 우측에 붙도록
                        (binding.textBox.layoutParams as RelativeLayout.LayoutParams).addRule(
                            RelativeLayout.ALIGN_RIGHT,
                            binding.tooltip.id
                        )
                        (binding.textBox.layoutParams as RelativeLayout.LayoutParams).addRule(
                            RelativeLayout.ALIGN_BASELINE,
                            binding.tooltip.id
                        )

                        (binding.tail.layoutParams as RelativeLayout.LayoutParams).topMargin =
                            14 //중앙 가리키도록 내리기

                        popupX = (referenceViewLocation[0] + referenceView.width).toFloat()
                        popUpY = //이때는 툴팁 높이가 40임.
                            (referenceViewLocation[1] + referenceView.height / 2).toFloat() - context.dpToPx(
                                20f
                            )
                    }
                }
                LEFT_SIDE -> {
                    //참조뷰의 왼쪽에 붙을 수 있는지 검사.
                    if (referenceViewLocation[0] > context.dpToPx(
                            Length + 10f
                        ) &&
                        mHeightPixels - referenceViewLocation[1] - referenceView.height / 2 > context.dpToPx(
                            25f
                        ) &&
                        referenceViewLocation[1] + referenceView.height / 2 - statusBarHeight > context.dpToPx(
                            25f
                        )
                    ) {

                        canSetHopeLocation = true
                        //Length = Length!! + 10f //늘려줌.
                        binding.tail.setBackgroundResource(R.drawable.tooltip_tail_left_side)

                        (binding.tail.layoutParams as RelativeLayout.LayoutParams).addRule(
                            RelativeLayout.RIGHT_OF,
                            binding.textBox.id
                        )

                        //툴팁내의 좌측에 붙도록
                        (binding.textBox.layoutParams as RelativeLayout.LayoutParams).addRule(
                            RelativeLayout.ALIGN_LEFT,
                            binding.tooltip.id
                        )
                        (binding.textBox.layoutParams as RelativeLayout.LayoutParams).addRule(
                            RelativeLayout.ALIGN_BASELINE,
                            binding.tooltip.id
                        )

                        (binding.tail.layoutParams as RelativeLayout.LayoutParams).topMargin =
                            14
                        //중앙 가리키도록 내리기

                        popupX = (referenceViewLocation[0] - context.dpToPx(Length + 10f))
                        popUpY = //이때는 툴팁 높이가 40임.
                            (referenceViewLocation[1] + referenceView.height / 2).toFloat() - context.dpToPx(
                                20f
                            )

                    }
                }
            }
        }
        return canSetHopeLocation
    }

    private var isShowOnce = false
    fun show() {
        popup.setBackgroundDrawable(BitmapDrawable());
        if (!isShowOnce) {
            popup.showAtLocation(
                referenceView,
                Gravity.NO_GRAVITY,
                popupX.toInt(),
                popUpY.toInt()
            )
            val animation = AlphaAnimation(0.0f, 1.0f)
            animation.duration = animDuration
            binding.relativeLayout.startAnimation(animation) //애니메이션 시작.
            isShowOnce = true //이미 한번 띄웠음을 의미함.
        }
    }


    companion object {

        //해당 커스텀뷰의 가로세로 크기를 상수로 만들기. 나중에 활용함
        const val ShortTooltip = 143f
        const val MediumTooltip = 210f
        const val LongTooltip = 320f

        //툴팁 생성 원할때 희망위치가 있다면 이걸로 지정해주면 됨.
        const val ABOVE = 0
        const val BELOW = 1
        const val RIGHT_SIDE = 2
        const val LEFT_SIDE = 3

    }
}





