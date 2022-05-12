package com.yourssu.design.system.atom

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.text.TextPaint
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.widget.PopupWindow
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import com.yourssu.design.R
import com.yourssu.design.databinding.LayoutTooltipBinding
import com.yourssu.design.undercarriage.animation.endListener
import com.yourssu.design.undercarriage.animation.startAnim
import com.yourssu.design.undercarriage.size.dpToIntPx
import com.yourssu.design.undercarriage.size.dpToPx

class ToolTip private constructor(
    private val context: Context,
    private val windowManager: WindowManager,
    private val layoutInflater: LayoutInflater,
    private var referenceView: View,
    private var isNormal: Boolean,
    private var textInit: String,
    private var hopeLocation: ToolTip.HopeLocation,
    private val toastTime: ToolTip.Length
) {

    // 빌더를 위한 필수 변수들 세개. 참조뷰를 여기 안넣은 이유는 하나의 빌더를 생성하고
    // 참조뷰만 매번 build(참조뷰)호출하는 방법으로 갈아 끼울 수 있게 하기 위해...
    class Builder(
        var context: Context,
        var windowManager: WindowManager, // 화면크기를 알기위해
        var layoutInflater: LayoutInflater
    ) {

        lateinit var referenceView: View // 해당 툴팁이 붙고싶어하는 뷰
        var isNormal: Boolean = true
        var stringContents: String = "눌러보세요"
        var hopeLocation: ToolTip.HopeLocation = ToolTip.HopeLocation.RANDOM
        var toastTime: ToolTip.Length = ToolTip.Length.LONG

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

        fun withHopeLocation(hopeLocation: ToolTip.HopeLocation): Builder { // enum 클래스로 바꿔서 아예 인자의 예외상황을 제거
            this.hopeLocation = hopeLocation
            return this
        }

        fun withToastLength(toastTime: ToolTip.Length): Builder {
            if (toastTime != Length.LONG && toastTime != Length.SHORT) {
                // 잘못된 값을 넣으면 그냥 Long으로
                this.toastTime = Length.LONG
            } else {
                this.toastTime = toastTime
            }
            return this
        }

        // 최종적으로 build를 호출하기 위해서는 참조뷰를 인자로 줘야함.
        fun build(referView: View): ToolTip {
            referenceView = referView

            return ToolTip(
                context,
                windowManager, // 화면크기를 알기위해
                layoutInflater,
                referenceView, // 해당 툴팁이 붙고싶어하는 뷰
                isNormal,
                stringContents,
                hopeLocation,
                toastTime
            )
        }
    }

    private lateinit var popup: PopupWindow
    private var popupX = 0f
    private var popUpY = 0f
    private lateinit var binding: LayoutTooltipBinding

    private var statusBarHeight = 0
    private val referenceViewLocation = IntArray(2) // 참조할 뷰의 위치정보를 기억
    private var mWidthPixels = 0 // 화면 정보를 얻음
    private var mHeightPixels = 0 // 화면 정보를 얻음
    private val d = windowManager.defaultDisplay as Display
    private val metrics = DisplayMetrics()

    private var TooltipWidth: Float = SHORT_TOOLTIP
    private var textRect: Rect = Rect()

    var text: CharSequence
        get() {
            return binding.textBox.text
        }
        set(value: CharSequence) {
            val valuePaint: TextPaint = TextPaint()
            val valueWidth: Float = valuePaint.measureText(value, 0, value.length)
            val vLength = when { // 바꿀 문자열에 맞는 알맞은 타입의 툴팁 고르기.
                valueWidth < context.dpToPx(SHORT_TOOLTIP - PADDING_SUM) -> SHORT_TOOLTIP
                valueWidth < context.dpToPx(MEDIUM_TOOLTIP - PADDING_SUM) -> MEDIUM_TOOLTIP
                else -> LONG_TOOLTIP
            }
            if (TooltipWidth != vLength) { // 길이가 다르면 다시 생성 필요.
                popup.dismiss() // 기존 팝업 삭제
                textInit = value.toString()
                initView(referenceView, textInit) // 새로 팝업 객체 생성
                isShowOnce = false
                show() // 다시 만든 팝업 객체 다시 띄우기
            } else { // 바꿀 문자열과 길이가 둘이 같다면....그냥 텍스트만 수정해주면 됨.
                binding.textBox.text = value.toString()
            }
        }

    // 기존 팝업을 제거하고 참조뷰 바꿔서 새로 팝업 띄워야 하는 상황에 사용.
    // 그냥 새로 툴팁을 처음부터 만드는게 낫지 않을까 싶지만 일단 기능은 만들었음.
    fun changeReferenceView(view: View) {
        // 참조뷰에 대한 정보를 갱신
        referenceView = view
        referenceView.getLocationOnScreen(referenceViewLocation)

        popup.dismiss()
        initView(referenceView, textInit)
        isShowOnce = false
        show() // 다시 만든 팝업 객체 다시 띄우기
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
                    R.color.tooltipPoint  // 일단은 이거로 설정
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
                    R.color.tooltipPoint  // 일단은 이거로 설정
                )
            )
        }

    }

    init {
        setScreenSize() //스크린 크기 저장함
        initView(referenceView, textInit) // 하나의 팝업 생성
    }

    // 참조뷰나 스트링이 바뀌면 다시 호출돼서 팝업윈도우 재생성 될수도있음.
    private fun initView(reference: View, txt: String) { //팝업객체생성
        inflateLayout(context, reference) // 해당 참조뷰에 대한 팝업객체 생성하고 binding 초기화

        binding.textBox.text = txt // 문자열 변경

        reference.getLocationOnScreen(referenceViewLocation) // 참조뷰에 대한 위치값 수정.
        calculateTooltipPosition() // 위치 계산

        popup.isOutsideTouchable = true
        popup.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) // 배경을 투명하게
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
                            binding.relativeLayout.startAnim(R.anim.fade_out_motion_m, endListener {
                                popup.dismiss()
                            })
                        }
                        MotionEvent.ACTION_OUTSIDE -> {
                            isRemove = true
                            binding.relativeLayout.startAnim(R.anim.fade_out_motion_m, endListener {
                                popup.dismiss()
                            })
                        }
                    }
                }

                return true
            }
        })
    }


    // 초기에 한번만 동작.
    private fun setScreenSize() {
        // 스크린 크기 저장
        d.getMetrics(metrics)
        mWidthPixels = metrics.widthPixels
        mHeightPixels = metrics.heightPixels
        // 상단의 상태바 높이 구하기
        val resourceId =
            context.resources.getIdentifier("status_bar_height", "dimen", "android")

        statusBarHeight = context.resources.getDimensionPixelSize(resourceId)
    }

    // 텍스트 길이에 따른 알맞은 백그라운드를 선택. 그리고 가로 길이 저장.
    private fun setBackgroundSize() {
        val textView = binding.textBox
        textView.paint.getTextBounds(textView.text.toString(), 0, textView.text.length, textRect)

        if (textRect.width() < (context.dpToPx(SHORT_TOOLTIP - PADDING_SUM))) { // 양옆의 패딩값 제외한 너비.
            TooltipWidth = SHORT_TOOLTIP
            binding.textBox.setBackgroundResource(R.drawable.tooltip_short_background)
            binding.textBox.width = context.dpToIntPx(SHORT_TOOLTIP)
        } else if (textRect.width() < (context.dpToPx(MEDIUM_TOOLTIP - PADDING_SUM))) {
            TooltipWidth = MEDIUM_TOOLTIP
            binding.textBox.setBackgroundResource(R.drawable.tooltip_medium_background)
            binding.textBox.width = context.dpToIntPx(MEDIUM_TOOLTIP)
        } else if (textRect.width() < (context.dpToPx(LONG_TOOLTIP - PADDING_SUM))) {
            // else로 안한 이유: 일부러 따로 패딩을 지정안했기 때문에 명시적인 가독성을 위해 조건을 걸었음.
            // 넘어가면 어차피 안나옴. maxLength=35 로 설정했음.
            TooltipWidth = LONG_TOOLTIP
            binding.textBox.setBackgroundResource(R.drawable.tooltip_long_background)
            binding.textBox.width = context.dpToIntPx(LONG_TOOLTIP)
        }
    }

    private fun calculateTooltipPosition() {
        setBackgroundSize() // 글자길이너비에 맞춰서 백그라운드 크기 지정
        changeIsNormal(isNormal) // 틴트색 결정.

        var canSetHopeLocation = hopeLocationSet()  // 희망하는 위치에 설정

        // 희망하는 자리가 없거나
        // 희망하는 위치에 놓을 수 없다면..... 가능한 곳에 배치시키기.
        if (!canSetHopeLocation) {
            val leftByReferView = referenceViewLocation[0]
            val rightByReferView = mWidthPixels - referenceViewLocation[0] - referenceView.width
            val aboveByReferView = referenceViewLocation[1]
            val belowByReferView =
                mHeightPixels - referenceViewLocation[1] - referenceView.height
            if (aboveByReferView > context.dpToPx(TEXT_BOX_HEIGHT + TAIL_HEIGHT + DISTANT + 1f)) {
                // 위에  배치시키기
                hopeLocation = HopeLocation.ABOVE // 희망자리 수정.
                hopeLocationSet() // 재귀 호출
            } else if (belowByReferView > context.dpToPx(TEXT_BOX_HEIGHT + TAIL_HEIGHT + DISTANT + 1f)) {
                // 아래에 배치시키기
                hopeLocation = HopeLocation.BELOW
                hopeLocationSet()
            } else if ((rightByReferView > context.dpToPx(TooltipWidth!! + TAIL_HEIGHT + 1f)) &&
                mHeightPixels - referenceViewLocation[1] - referenceView.height / 2 > context.dpToPx(
                    TEXT_BOX_HEIGHT / 2
                ) &&
                referenceViewLocation[1] + referenceView.height / 2 > context.dpToPx(
                    TEXT_BOX_HEIGHT / 2
                )
            ) {
                hopeLocation = HopeLocation.RIGHT_SIDE
                hopeLocationSet()
            } else if ((leftByReferView > context.dpToPx(TooltipWidth!! + TAIL_HEIGHT + 1f)) &&
                mHeightPixels - referenceViewLocation[1] - referenceView.height / 2 > context.dpToPx(
                    TEXT_BOX_HEIGHT / 2
                ) &&
                referenceViewLocation[1] + referenceView.height / 2 > context.dpToPx(
                    TEXT_BOX_HEIGHT / 2
                )
            ) {
                hopeLocation = HopeLocation.LEFT_SIDE
                hopeLocationSet()
            } else { // 다 안된다면..... 최후의 수단. 이런경우는 거의 드물것임. 애초에 이런경우는 툴팁을 안쓰는걸 권장.
                (binding.tail.layoutParams as RelativeLayout.LayoutParams).height =
                    context.dpToPx(TAIL_HEIGHT + DISTANT).toInt()
                binding.tail.setBackgroundResource(R.drawable.tooltip_tail_bottom)
                (binding.tail.layoutParams as RelativeLayout.LayoutParams).addRule(
                    RelativeLayout.BELOW,
                    binding.textBox.id
                )
                if ((referenceViewLocation[0] + referenceView.width / 2) > context.dpToPx(
                        TooltipWidth / 2
                    ) &&
                    (mWidthPixels - referenceViewLocation[0] - referenceView.width / 2) > context.dpToPx(
                        TooltipWidth / 2
                    )
                ) { // 뷰의 중앙에 있을 수 있는 경우
                    (binding.tail.layoutParams as RelativeLayout.LayoutParams).addRule(
                        RelativeLayout.CENTER_HORIZONTAL
                    )
                    popupX =
                        referenceViewLocation[0] + referenceView.width - (referenceView.width / 2) - context.dpToPx(
                            TooltipWidth / 2
                        )
                    popUpY = 0f // 최후의 수단의 대책임. 그냥 위에 달아버리기

                } else if ((referenceViewLocation[0] + (referenceView.width / 2)) > mWidthPixels / 2) {
                    // 참조뷰가 비교적 오른쪽에 치우쳐져 위치해 있다면
                    (binding.tail.layoutParams as RelativeLayout.LayoutParams).addRule(
                        RelativeLayout.ALIGN_RIGHT,
                        binding.textBox.id
                    )
                    popupX =
                        mWidthPixels - context.dpToPx(TooltipWidth)
                    popUpY = 0f // 최후의 수단.

                    // 꼬리가 참조뷰의 중앙을 가리키도록 조정.
                    (binding.tail.layoutParams as RelativeLayout.LayoutParams).rightMargin =
                        mWidthPixels - referenceViewLocation[0] - (referenceView.width / 2) - context.dpToIntPx(
                            TAIL_WIDTH / 2
                        )

                } else {
                    // 만약 화면내의 왼쪽에 참조뷰가 치우쳐져 있다
                    (binding.tail.layoutParams as RelativeLayout.LayoutParams).addRule(
                        RelativeLayout.ALIGN_LEFT,
                        binding.textBox.id
                    )
                    popupX = 0f
                    popUpY = 0f

                    // 꼬리가 참조뷰의 중앙을 가리키도록 조정.
                    (binding.tail.layoutParams as RelativeLayout.LayoutParams).leftMargin =
                        referenceViewLocation[0] + (referenceView.width / 2) - context.dpToIntPx(
                            TAIL_HEIGHT / 2
                        )
                }
            }
        }

    }

    fun hopeLocationSet(): Boolean {
        var canSetHopeLocation = false

        when (hopeLocation) {
            HopeLocation.ABOVE -> {                 // 위에 올라갈 자리가 있는지 검사
                if ((referenceViewLocation[1]) > context.dpToPx(TEXT_BOX_HEIGHT + TAIL_HEIGHT + DISTANT + 1f)) {
                    canSetHopeLocation = true // 위에 놓을 수 있음.
                    (binding.tail.layoutParams as RelativeLayout.LayoutParams).height =
                        context.dpToIntPx(TAIL_HEIGHT)
                    binding.tail.setBackgroundResource(R.drawable.tooltip_tail_bottom)

                    (binding.tail.layoutParams as RelativeLayout.LayoutParams).addRule(
                        RelativeLayout.BELOW,
                        binding.textBox.id
                    )

                    // 참조의 중앙에 툴팁을 위치시킬 수 있는 경우
                    if ((referenceViewLocation[0] + referenceView.width / 2) > context.dpToPx(
                            TooltipWidth / 2
                        ) &&
                        (mWidthPixels - referenceViewLocation[0] - referenceView.width / 2) > context.dpToPx(
                            TooltipWidth / 2
                        )
                    ) { // 뷰의 중앙 위에 올라갈 수 있는 경우에는 꼬리를 중앙아래에 달면 됨.
                        (binding.tail.layoutParams as RelativeLayout.LayoutParams).addRule(
                            RelativeLayout.CENTER_HORIZONTAL
                        )
                        popupX =
                            referenceViewLocation[0] + (referenceView.width / 2) - context.dpToPx(
                                TooltipWidth / 2
                            )

                        // 팝업의 y좌표가 0이면 상태바(시간, 알림, 바때리 등 확인 가능한 영역) 바로 아래에 놓이게 됨.
                        popUpY =
                            referenceViewLocation[1] - context.dpToPx(TEXT_BOX_HEIGHT + TAIL_HEIGHT + DISTANT)
                    } else if ((referenceViewLocation[0] + (referenceView.width / 2)) > (mWidthPixels / 2)) {
                        // 참조뷰가 우측에 붙어있음.
                        (binding.tail.layoutParams as RelativeLayout.LayoutParams).addRule(
                            RelativeLayout.ALIGN_RIGHT,
                            binding.textBox.id
                        )
                        // 추가한 코드. 만약 자연스럽게 배치가 가능하면 이렇게 배치하자.
                        if (referenceViewLocation[0] + referenceView.width / 2 > context.dpToPx(
                                TooltipWidth - TAIL_WIDTH / 2 - FROM_BORDER_TO_TAIL
                            ) &&
                            mWidthPixels - referenceViewLocation[0] - referenceView.width / 2 > context.dpToPx(
                                TAIL_WIDTH / 2 + FROM_BORDER_TO_TAIL
                            )
                        ) {
                            popupX =
                                referenceViewLocation[0] + referenceView.width / 2 - context.dpToPx(
                                    TooltipWidth - TAIL_WIDTH / 2 - FROM_BORDER_TO_TAIL
                                )

                            popUpY =
                                referenceViewLocation[1] - context.dpToPx(TEXT_BOX_HEIGHT + TAIL_HEIGHT + DISTANT)

                            // 꼬리가 참조뷰의 중앙을 가리키도록 조정.
                            (binding.tail.layoutParams as RelativeLayout.LayoutParams).rightMargin =
                                context.dpToIntPx(
                                    FROM_BORDER_TO_TAIL
                                )
                        } else {
                            // 참조뷰가 우측에 가깝게 위치해 있는데, 자연스럽게 툴팁 배치가 안되는 경우
                            popupX =
                                mWidthPixels - context.dpToPx(TooltipWidth)

                            popUpY =
                                referenceViewLocation[1] - context.dpToPx(TEXT_BOX_HEIGHT + TAIL_HEIGHT + DISTANT)

                            // 꼬리가 참조뷰의 중앙을 가리키도록 조정.
                            (binding.tail.layoutParams as RelativeLayout.LayoutParams).rightMargin =
                                mWidthPixels - referenceViewLocation[0] - (referenceView.width / 2) - context.dpToIntPx(
                                    TAIL_WIDTH / 2
                                )
                        }
                    } else if (true) {
                        // 만약 화면내의 왼쪽에 참조뷰가 치우쳐져 있다
                        (binding.tail.layoutParams as RelativeLayout.LayoutParams).addRule(
                            RelativeLayout.ALIGN_LEFT,
                            binding.textBox.id
                        )

                        // 만약 자연스럽게 배치가 가능하면
                        if (referenceViewLocation[0] + referenceView.width / 2 > context.dpToPx(
                                TAIL_WIDTH / 2 + FROM_BORDER_TO_TAIL
                            ) &&
                            mWidthPixels - referenceViewLocation[0] - referenceView.width / 2 > context.dpToPx(
                                TooltipWidth - TAIL_WIDTH / 2 - FROM_BORDER_TO_TAIL
                            )
                        ) {
                            popupX =
                                referenceViewLocation[0] + referenceView.width / 2 - context.dpToPx(
                                    TAIL_WIDTH / 2 + FROM_BORDER_TO_TAIL
                                )

                            popUpY =
                                referenceViewLocation[1] - context.dpToPx(TEXT_BOX_HEIGHT + TAIL_HEIGHT + DISTANT)

                            // 꼬리가 참조뷰의 중앙을 가리키도록 조정.
                            (binding.tail.layoutParams as RelativeLayout.LayoutParams).leftMargin =
                                context.dpToIntPx(
                                    FROM_BORDER_TO_TAIL
                                )
                        } else {
                            // 참조뷰가 화면의 왼쪽에 가깝게 위치해있는데 자연스러운 배치가 불가능한 경우
                            popupX = 0f
                            popUpY =
                                referenceViewLocation[1] - context.dpToPx(TEXT_BOX_HEIGHT + TAIL_HEIGHT + DISTANT)

                            // 꼬리가 참조뷰의 중앙을 가리키도록 조정.
                            (binding.tail.layoutParams as RelativeLayout.LayoutParams).leftMargin =
                                referenceViewLocation[0] + (referenceView.width / 2) - context.dpToIntPx(
                                    TAIL_WIDTH / 2
                                )
                        }
                    }
                }
            }
            HopeLocation.BELOW -> {  // 참조뷰의 아래에 들어갈 자리가 있는지 검사
                if ((mHeightPixels - (referenceViewLocation[1] + referenceView.height)) > context.dpToPx(
                        TEXT_BOX_HEIGHT + TAIL_HEIGHT + DISTANT + 1f
                    )
                ) {
                    canSetHopeLocation = true // 아래에 놓을 수 있음.
                    (binding.tail.layoutParams as RelativeLayout.LayoutParams).height =
                        context.dpToIntPx(TAIL_HEIGHT)
                    binding.tail.setBackgroundResource(R.drawable.tooltip_tail_top)
                    (binding.textBox.layoutParams as RelativeLayout.LayoutParams).addRule(
                        RelativeLayout.BELOW,
                        binding.tail.id
                    )

                    if ((referenceViewLocation[0] + referenceView.width / 2) > context.dpToPx(
                            TooltipWidth / 2
                        ) &&
                        (mWidthPixels - referenceViewLocation[0] - referenceView.width / 2) > context.dpToPx(
                            TooltipWidth / 2
                        )
                    ) {  // 참조뷰의 중앙 위에 툴팁이 올라갈 수 있는 경우에는 꼬리를 중앙위에 달면 됨.
                        (binding.tail.layoutParams as RelativeLayout.LayoutParams).addRule(
                            RelativeLayout.CENTER_HORIZONTAL
                        )
                        popupX =
                            referenceViewLocation[0] + (referenceView.width / 2) - context.dpToPx(
                                TooltipWidth / 2
                            )

                        popUpY =
                            (referenceViewLocation[1] + referenceView.height).toFloat() + context.dpToPx(
                                DISTANT
                            )
                    } else if ((referenceViewLocation[0] + (referenceView.width / 2)) > mWidthPixels / 2) {
                        // 만약 화면내의 오른쪽에 참조뷰가 치우쳐져 있다
                        // 일단 tail을 툴팁의 경계선에 붙히고 생각하자.
                        (binding.tail.layoutParams as RelativeLayout.LayoutParams).addRule(
                            RelativeLayout.ALIGN_RIGHT,
                            binding.textBox.id
                        )

                        // 자연스럽게 배치가 가능하다면
                        if (referenceViewLocation[0] + referenceView.width / 2 > context.dpToPx(
                                TooltipWidth - TAIL_WIDTH / 2 - FROM_BORDER_TO_TAIL
                            ) &&
                            mWidthPixels - referenceViewLocation[0] - referenceView.width / 2 > context.dpToPx(
                                TAIL_WIDTH / 2 + FROM_BORDER_TO_TAIL
                            )
                        ) {
                            popupX =
                                referenceViewLocation[0] + referenceView.width / 2 - context.dpToPx(
                                    TooltipWidth - TAIL_WIDTH / 2 - FROM_BORDER_TO_TAIL
                                )

                            popUpY =
                                (referenceViewLocation[1] + referenceView.height).toFloat() + context.dpToPx(
                                    DISTANT
                                )

                            // 꼬리가 참조뷰의 중앙을 가리키도록 조정.
                            (binding.tail.layoutParams as RelativeLayout.LayoutParams).rightMargin =
                                context.dpToIntPx(
                                    FROM_BORDER_TO_TAIL
                                )
                        } else {
                            // 자연스럽게 배치가 안된다면
                            popupX =
                                mWidthPixels - context.dpToPx(TooltipWidth)
                            popUpY =
                                (referenceViewLocation[1] + referenceView.height).toFloat() + context.dpToPx(
                                    DISTANT
                                )

                            // 꼬리가 참조뷰의 중앙을 가리키도록 조정.
                            (binding.tail.layoutParams as RelativeLayout.LayoutParams).rightMargin =
                                mWidthPixels - referenceViewLocation[0] - (referenceView.width / 2) - context.dpToIntPx(
                                    TAIL_WIDTH / 2
                                )
                        }
                    } else if (true) {
                        // 만약 화면내의 왼쪽에 참조뷰가 치우쳐져 있다
                        (binding.tail.layoutParams as RelativeLayout.LayoutParams).addRule(
                            RelativeLayout.ALIGN_LEFT,
                            binding.textBox.id
                        )

                        // 자연스럽게 배치가 가능하다면
                        if (referenceViewLocation[0] + referenceView.width / 2 > context.dpToPx(
                                TAIL_WIDTH / 2 + FROM_BORDER_TO_TAIL
                            ) &&
                            mWidthPixels - referenceViewLocation[0] - referenceView.width / 2 > context.dpToPx(
                                TooltipWidth - TAIL_WIDTH / 2 - FROM_BORDER_TO_TAIL
                            )
                        ) {
                            popupX =
                                referenceViewLocation[0] + referenceView.width / 2 - context.dpToPx(
                                    TAIL_WIDTH / 2 + FROM_BORDER_TO_TAIL
                                )

                            popUpY =
                                (referenceViewLocation[1] + referenceView.height).toFloat() + context.dpToPx(
                                    DISTANT
                                )

                            // 꼬리가 참조뷰의 중앙을 가리키도록 조정.
                            (binding.tail.layoutParams as RelativeLayout.LayoutParams).leftMargin =
                                context.dpToPx(
                                    FROM_BORDER_TO_TAIL
                                ).toInt()
                        } else {
                            // 자연스럽게 배치가 안된다면
                            popupX = 0f
                            popUpY =
                                (referenceViewLocation[1] + referenceView.height).toFloat() + context.dpToPx(
                                    DISTANT
                                )

                            // 꼬리가 참조뷰의 중앙을 가리키도록 조정.
                            (binding.tail.layoutParams as RelativeLayout.LayoutParams).leftMargin =
                                referenceViewLocation[0] + (referenceView.width / 2) - context.dpToIntPx(
                                    TAIL_WIDTH / 2
                                )
                        }
                    }
                }
            }
            HopeLocation.RIGHT_SIDE -> {
                // 참조뷰의 오른쪽에 붙을 수 있는지 검사.
                if (mWidthPixels - referenceViewLocation[0] - referenceView.width > context.dpToPx(
                        TooltipWidth + TAIL_HEIGHT + 1f
                    ) &&
                    mHeightPixels - referenceViewLocation[1] - referenceView.height / 2 > context.dpToPx(
                        TEXT_BOX_HEIGHT / 2
                    ) &&
                    referenceViewLocation[1] + referenceView.height / 2 > context.dpToPx(
                        TEXT_BOX_HEIGHT / 2
                    )
                ) {
                    canSetHopeLocation = true

                    binding.tail.setBackgroundResource(R.drawable.tooltip_tail_right_side)

                    (binding.textBox.layoutParams as RelativeLayout.LayoutParams).addRule(
                        RelativeLayout.RIGHT_OF,
                        binding.tail.id
                    )

                    // 툴팁내의 우측에 붙도록
                    (binding.textBox.layoutParams as RelativeLayout.LayoutParams).addRule(
                        RelativeLayout.ALIGN_RIGHT,
                        binding.tooltip.id
                    )
                    (binding.textBox.layoutParams as RelativeLayout.LayoutParams).addRule(
                        RelativeLayout.ALIGN_BASELINE,
                        binding.tooltip.id
                    )

                    (binding.tail.layoutParams as RelativeLayout.LayoutParams).topMargin =
                        context.dpToIntPx(TEXT_BOX_HEIGHT / 2 - TAIL_WIDTH / 2) // 중앙 가리키도록 내리기

                    popupX = (referenceViewLocation[0] + referenceView.width).toFloat()
                    popUpY = // 이때는 툴팁 높이가 40임.
                        (referenceViewLocation[1] + referenceView.height / 2).toFloat() - context.dpToPx(
                            TEXT_BOX_HEIGHT / 2
                        )
                }
            }
            HopeLocation.LEFT_SIDE -> {
                // 참조뷰의 왼쪽에 붙을 수 있는지 검사.
                if (referenceViewLocation[0] > context.dpToPx(
                        TooltipWidth + TAIL_HEIGHT + 1f
                    ) &&
                    mHeightPixels - referenceViewLocation[1] - referenceView.height / 2 > context.dpToPx(
                        TEXT_BOX_HEIGHT / 2
                    ) &&
                    referenceViewLocation[1] + referenceView.height / 2 > context.dpToPx(
                        TEXT_BOX_HEIGHT / 2
                    )
                ) {
                    canSetHopeLocation = true

                    binding.tail.setBackgroundResource(R.drawable.tooltip_tail_left_side)

                    (binding.tail.layoutParams as RelativeLayout.LayoutParams).addRule(
                        RelativeLayout.RIGHT_OF,
                        binding.textBox.id
                    )

                    // 툴팁내의 좌측에 붙도록
                    (binding.textBox.layoutParams as RelativeLayout.LayoutParams).addRule(
                        RelativeLayout.ALIGN_LEFT,
                        binding.tooltip.id
                    )
                    (binding.textBox.layoutParams as RelativeLayout.LayoutParams).addRule(
                        RelativeLayout.ALIGN_BASELINE,
                        binding.tooltip.id
                    )

                    (binding.tail.layoutParams as RelativeLayout.LayoutParams).topMargin =
                        context.dpToIntPx(TEXT_BOX_HEIGHT / 2 - TAIL_WIDTH / 2)
                    // 중앙 가리키도록 내리기

                    popupX =
                        (referenceViewLocation[0] - context.dpToPx(TooltipWidth + TAIL_HEIGHT + 1f))
                    popUpY = // 이때는 툴팁 높이가 40임.
                        (referenceViewLocation[1] + referenceView.height / 2).toFloat() - context.dpToPx(
                            TEXT_BOX_HEIGHT / 2
                        )
                }
            }
            HopeLocation.RANDOM -> {
                canSetHopeLocation=false // 명시적으로 가독성을 위해 적음
            }
        }

        return canSetHopeLocation
    }

    private var isShowOnce = false
    fun show() { // 띄우고 나서 지정된 시간 이후에 사라지도록 구성됨.
        popup.setBackgroundDrawable(BitmapDrawable());
        if (!isShowOnce) {
            popup.showAtLocation(
                referenceView,
                Gravity.NO_GRAVITY,
                popupX.toInt(),
                popUpY.toInt()
            )

            isShowOnce = true // 이미 한번 띄웠음을 의미함.

            binding.relativeLayout.startAnim(R.anim.fade_in_motion_m, endListener {
                binding.relativeLayout.startAnim(
                    if (toastTime == Length.LONG) R.anim.none_motion_toast_long else R.anim.none_motion_toast_short,
                    endListener {
                        binding.relativeLayout.startAnim(R.anim.fade_out_motion_m, endListener {
                            popup.dismiss()
                        })
                    })
            })
        }
    }

    companion object {
        // 해당 커스텀뷰의 가로세로 크기를 상수로 만들기. 나중에 활용함
        const val SHORT_TOOLTIP = 143f
        const val MEDIUM_TOOLTIP = 210f
        const val LONG_TOOLTIP = 320f

        // 툴팁의 tail 높이와 너비. 이거는 툴팁이 위 혹은 아래로 배치될때의 tail을 기준으로 함.
        private const val TAIL_HEIGHT = 9f
        private const val TAIL_WIDTH = 28f

        // 툴팁의 꼬리를 위아래로 배치시킬때, 툴팁의 좌 혹은 우의 경계선과 꼬리를 놓는 시작지점까지의 거리.
        private const val FROM_BORDER_TO_TAIL = 8f

        // 툴팁의 텍스트 박스의 높이
        private const val TEXT_BOX_HEIGHT = 40f

        // 툴팁의 tail이 위 혹은 아래에 존재할때 참조뷰와의 거리
        private const val DISTANT = 8f

        // 툴팁 내부의 텍스트를 위한 좌우 패딩 합.
        private const val PADDING_SUM = 32f

    }

    enum class Length(val length: Long) {
        SHORT(1500L),
        LONG(3000L)
    }

    enum class HopeLocation(val num: Int) {
        // 툴팁 생성 원할때 희망위치가 있다면 이걸로 지정해주면 됨.
        ABOVE(0),
        BELOW(1),
        RIGHT_SIDE(2),
        LEFT_SIDE(3),
        RANDOM(-1)
    }
}





