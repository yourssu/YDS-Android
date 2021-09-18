package com.yourssu.design.undercarriage.custom

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.*
import android.view.animation.DecelerateInterpolator
import android.widget.OverScroller
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.yourssu.design.R
import com.yourssu.design.undercarriage.size.dpToIntPx
import java.util.*
import kotlin.math.abs
import kotlin.math.min

class RawPicker @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val TOP_AND_BOTTOM_FADING_EDGE_STRENGTH = 0.9f
    private val SNAP_SCROLL_DURATION = 300
    private val SELECTOR_MAX_FLING_VELOCITY_ADJUSTMENT = 4
    private val DEFAULT_ITEM_COUNT = 7
    private val DEFAULT_TEXT_SIZE = context.dpToIntPx(15f)

    private var mSelectorItemCount: Int
    private var mSelectorVisibleItemCount: Int
    private var mMinIndex: Int
    private var mMaxIndex: Int
    private var mMaxValidIndex: Int? = null
    private var mMinValidIndex: Int? = null

    private var mRawPickerMiddleItemIndex: Int
    private var mRawPickerVisibleItemMiddleIndex: Int
    private var mSelectorItemIndices: ArrayList<Int>
    private var mSelectorItemValidStatus: ArrayList<Boolean>
    private var mCurSelectedItemIndex = 0
    private var mWrapSelectorRawPickerPreferred: Boolean

    private var mTextPaint: Paint = Paint()
    private var mSelectedTextColor: Int
    private var mUnSelectedTextColor: Int
    private var mTextSize: Int

    private var mOverScroller: OverScroller? = null
    private var mVelocityTracker: VelocityTracker? = null
    private val mTouchSlop: Int
    private val mMaximumVelocity: Int
    private val mMinimumVelocity: Int
    private var mLastY: Float = 0f
    private var mIsDragging: Boolean = false
    private var mCurrentFirstItemOffset: Int = 0
    private var mInitialFirstItemOffset = Int.MIN_VALUE
    private var mTextGapHeight: Int = 0
    private var mItemHeight: Int = 0
    private var mTextHeight: Int = 0
    private var mPreviousScrollerY: Int = 0
    private var mOnValueChangeListener: OnValueChangeListener? = null
    private var mOnScrollListener: OnScrollListener? = null
    private var mAdapter: RawPickerAdapter? = null
    private var mSelectedTextScale = 0.3f

    interface OnValueChangeListener {
        fun onValueChange(picker: RawPicker, oldVal: String, newVal: String)
    }

    interface OnScrollListener {
        fun onScrollStateChange(view: RawPicker, scrollState: Int)
    }

    companion object {
        const val SCROLL_STATE_IDLE = 0
        const val SCROLL_STATE_TOUCH_SCROLL = 1
        const val SCROLL_STATE_FLING = 2
    }

    /**
     * The current scroll state of the number picker.
     */
    private var mScrollState = SCROLL_STATE_IDLE

    init {
        mSelectorItemCount = DEFAULT_ITEM_COUNT + 2
        mRawPickerMiddleItemIndex = (mSelectorItemCount - 1) / 2
        mSelectorVisibleItemCount = mSelectorItemCount - 2
        mRawPickerVisibleItemMiddleIndex = (mSelectorVisibleItemCount - 1) / 2
        mSelectorItemIndices = ArrayList(mSelectorItemCount)
        mSelectorItemValidStatus = ArrayList(mSelectorItemCount)

        mMinIndex = Integer.MIN_VALUE
        mMaxIndex = Integer.MAX_VALUE
        mWrapSelectorRawPickerPreferred = false
        mSelectedTextScale = 0.3f

        mOverScroller = OverScroller(context, DecelerateInterpolator(2.5f))
        val configuration = ViewConfiguration.get(context)
        mTouchSlop = configuration.scaledTouchSlop
        mMaximumVelocity =
            configuration.scaledMaximumFlingVelocity / SELECTOR_MAX_FLING_VELOCITY_ADJUSTMENT
        mMinimumVelocity = configuration.scaledMinimumFlingVelocity

        mSelectedTextColor = ContextCompat.getColor(context, R.color.textPrimary)
        mUnSelectedTextColor = ContextCompat.getColor(context, R.color.textPrimary)
        mTextSize = DEFAULT_TEXT_SIZE

        mTextPaint.run {
            isAntiAlias = true
            textSize = mTextSize.toFloat()
            textAlign = Paint.Align.CENTER
            style = Paint.Style.FILL_AND_STROKE
            typeface = ResourcesCompat.getFont(context, R.font.spoqa_han_sans_neo_regular)
        }

        initializeSelectorRawPickerIndices()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        if (changed) {
            // need to do all this when we know our size
            initializeSelectorRawPicker()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // Try greedily to fit the max width and height.
        var layoutParams: ViewGroup.LayoutParams? = layoutParams
        if (layoutParams == null)
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

        var width = calculateSize(suggestedMinimumWidth, layoutParams.width, widthMeasureSpec)

        width += paddingLeft + paddingRight

        setMeasuredDimension(width, context.dpToIntPx(244f))
    }

    override fun getSuggestedMinimumWidth(): Int {
        var suggested = super.getSuggestedMinimumHeight()
        if (mSelectorVisibleItemCount > 0) {
            suggested = Math.max(suggested, computeMaximumWidth())
        }
        return context.dpToIntPx(20f) + suggested + context.dpToIntPx(20f)
    }

    override fun getSuggestedMinimumHeight(): Int {
        var suggested = super.getSuggestedMinimumWidth()
        if (mSelectorVisibleItemCount > 0) {
            val fontMetricsInt = mTextPaint.fontMetricsInt
            val height = fontMetricsInt.descent - fontMetricsInt.ascent
            suggested = Math.max(suggested, height * mSelectorVisibleItemCount)
        }
        return suggested
    }

    private fun computeMaximumWidth(): Int {
        mTextPaint.textSize = mTextSize * 1.3f
        if (mAdapter != null) {
            return if (!mAdapter!!.getTextWithMaximumLength().isEmpty()) {
                val suggestedWith =
                    mTextPaint.measureText(mAdapter!!.getTextWithMaximumLength()).toInt()
                mTextPaint.textSize = mTextSize * 1.0f
                suggestedWith
            } else {
                val suggestedWith = mTextPaint.measureText("0000").toInt()
                mTextPaint.textSize = mTextSize * 1.0f
                suggestedWith
            }
        }
        val widthForMinIndex = mTextPaint.measureText(mMinIndex.toString()).toInt()
        val widthForMaxIndex = mTextPaint.measureText(mMaxIndex.toString()).toInt()
        mTextPaint.textSize = mTextSize * 1.0f
        return if (widthForMinIndex > widthForMaxIndex)
            widthForMinIndex
        else
            widthForMaxIndex
    }

    private fun calculateSize(suggestedSize: Int, paramSize: Int, measureSpec: Int): Int {
        var result = 0
        val size = MeasureSpec.getSize(measureSpec)
        val mode = MeasureSpec.getMode(measureSpec)

        when (MeasureSpec.getMode(mode)) {
            MeasureSpec.AT_MOST ->
                result = when (paramSize) {
                    ViewGroup.LayoutParams.WRAP_CONTENT -> min(suggestedSize, size)
                    ViewGroup.LayoutParams.MATCH_PARENT -> size
                    else -> min(paramSize, size)
                }
            MeasureSpec.EXACTLY -> result = size
            MeasureSpec.UNSPECIFIED ->
                result =
                    if (paramSize == ViewGroup.LayoutParams.WRAP_CONTENT || paramSize == ViewGroup.LayoutParams.MATCH_PARENT)
                        suggestedSize
                    else {
                        paramSize
                    }
        }

        return result
    }

    private fun initializeSelectorRawPicker() {
        mItemHeight = getItemHeight()
        mTextHeight = computeTextHeight()
        mTextGapHeight = getGapHeight()

        val visibleMiddleItemPos =
            mItemHeight * mRawPickerVisibleItemMiddleIndex + (mItemHeight + mTextHeight) / 2
        mInitialFirstItemOffset = visibleMiddleItemPos - mItemHeight * mRawPickerMiddleItemIndex
        mCurrentFirstItemOffset = mInitialFirstItemOffset
    }

    private fun initializeSelectorRawPickerIndices() {
        mSelectorItemIndices.clear()
        mSelectorItemValidStatus.clear()

        mCurSelectedItemIndex = if (mMinValidIndex == null || mMinValidIndex!! < mMinIndex) {
            if (mMinIndex <= 0) {
                0
            } else {
                mMinIndex
            }
        } else {
            if (mMinValidIndex!! <= 0) {
                0
            } else {
                mMinValidIndex!!
            }
        }

        for (i in 0 until mSelectorItemCount) {
            var selectorIndex = mCurSelectedItemIndex + (i - mRawPickerMiddleItemIndex)
            if (mWrapSelectorRawPickerPreferred) {
                selectorIndex = getWrappedSelectorIndex(selectorIndex)
            }
            mSelectorItemIndices.add(selectorIndex)
            mSelectorItemValidStatus.add(isValidPosition(selectorIndex))
        }
    }

    override fun getBottomFadingEdgeStrength(): Float {
        return TOP_AND_BOTTOM_FADING_EDGE_STRENGTH
    }

    override fun getTopFadingEdgeStrength(): Float {
        return TOP_AND_BOTTOM_FADING_EDGE_STRENGTH
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawVertical(canvas)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        onTouchEventVertical(event)
        return true
    }

    private fun onTouchEventVertical(event: MotionEvent) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain()
        }

        mVelocityTracker?.addMovement(event)

        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                if (!mOverScroller!!.isFinished)
                    mOverScroller!!.forceFinished(true)

                mLastY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                var deltaY = event.y - mLastY
                if (!mIsDragging && abs(deltaY) > mTouchSlop) {
                    parent?.requestDisallowInterceptTouchEvent(true)

                    if (deltaY > 0) {
                        deltaY -= mTouchSlop
                    } else {
                        deltaY += mTouchSlop
                    }
                    onScrollStateChange(SCROLL_STATE_TOUCH_SCROLL)
                    mIsDragging = true
                }

                if (mIsDragging) {
                    scrollBy(0, deltaY.toInt())
                    invalidate()
                    mLastY = event.y
                }
            }
            MotionEvent.ACTION_UP -> {
                if (mIsDragging) {
                    mIsDragging = false
                    parent?.requestDisallowInterceptTouchEvent(false)

                    mVelocityTracker?.computeCurrentVelocity(1000, mMaximumVelocity.toFloat())
                    val velocity = mVelocityTracker?.yVelocity?.toInt()

                    if (abs(velocity!!) > mMinimumVelocity) {
                        mPreviousScrollerY = 0
                        mOverScroller?.fling(
                            scrollX, scrollY, 0, velocity, 0, 0, Integer.MIN_VALUE,
                            Integer.MAX_VALUE, 0, (getItemHeight() * 0.7).toInt()
                        )
                        invalidateOnAnimation()
                        onScrollStateChange(SCROLL_STATE_FLING)
                    }
                    recyclerVelocityTracker()
                } else {
                    //click event
                    val y = event.y.toInt()
                    handlerClickVertical(y)
                }
            }

            MotionEvent.ACTION_CANCEL -> {
                if (mIsDragging) {
                    mIsDragging = false
                }
                recyclerVelocityTracker()
            }
        }
    }

    private fun handlerClickVertical(y: Int) {
        val selectorIndexOffset = y / mItemHeight - mRawPickerVisibleItemMiddleIndex
        changeValueBySteps(selectorIndexOffset)
    }

    override fun scrollBy(x: Int, y: Int) {
        if (y == 0)
            return

        val gap = mTextGapHeight

        if (!mWrapSelectorRawPickerPreferred && y > 0
            && (mSelectorItemIndices[mRawPickerMiddleItemIndex] <= mMinIndex
                    || (mMinValidIndex != null && mSelectorItemIndices[mRawPickerMiddleItemIndex] <= mMinValidIndex!!))
        ) {
            if (mCurrentFirstItemOffset + y - mInitialFirstItemOffset < gap / 2)
                mCurrentFirstItemOffset += y
            else {
                mCurrentFirstItemOffset = mInitialFirstItemOffset + (gap / 2)
                if (!mOverScroller!!.isFinished && !mIsDragging) {
                    mOverScroller!!.abortAnimation()
                }
            }
            return
        }

        if (!mWrapSelectorRawPickerPreferred && y < 0
            && (mSelectorItemIndices[mRawPickerMiddleItemIndex] >= mMaxIndex
                    || (mMaxValidIndex != null && mSelectorItemIndices[mRawPickerMiddleItemIndex] >= mMaxValidIndex!!))
        ) {
            if (mCurrentFirstItemOffset + y - mInitialFirstItemOffset > -(gap / 2))
                mCurrentFirstItemOffset += y
            else {
                mCurrentFirstItemOffset = mInitialFirstItemOffset - (gap / 2)
                if (!mOverScroller!!.isFinished && !mIsDragging) {
                    mOverScroller!!.abortAnimation()
                }
            }
            return
        }

        mCurrentFirstItemOffset += y

        while (mCurrentFirstItemOffset - mInitialFirstItemOffset < -gap) {
            mCurrentFirstItemOffset += mItemHeight
            increaseSelectorsIndex()
            if (!mWrapSelectorRawPickerPreferred
                && (mSelectorItemIndices[mRawPickerMiddleItemIndex] >= mMaxIndex
                        || (mMaxValidIndex != null && mSelectorItemIndices[mRawPickerMiddleItemIndex] >= mMaxValidIndex!!))
            ) {
                mCurrentFirstItemOffset = mInitialFirstItemOffset
            }
        }

        while (mCurrentFirstItemOffset - mInitialFirstItemOffset > gap) {
            mCurrentFirstItemOffset -= mItemHeight
            decreaseSelectorsIndex()
            if (!mWrapSelectorRawPickerPreferred
                && (mSelectorItemIndices[mRawPickerMiddleItemIndex] <= mMinIndex
                        || (mMinValidIndex != null && mSelectorItemIndices[mRawPickerMiddleItemIndex] <= mMinValidIndex!!))
            ) {
                mCurrentFirstItemOffset = mInitialFirstItemOffset
            }
        }
        onSelectionChanged(mSelectorItemIndices[mRawPickerMiddleItemIndex], true)
    }

    override fun computeScroll() {
        super.computeScroll()
        if (mOverScroller!!.computeScrollOffset()) {
            val x = mOverScroller!!.currX
            val y = mOverScroller!!.currY


            if (mPreviousScrollerY == 0) {
                mPreviousScrollerY = mOverScroller!!.startY
            }
            scrollBy(x, y - mPreviousScrollerY)
            mPreviousScrollerY = y
            invalidate()
        } else {
            if (!mIsDragging)
            //align item
                adjustItemVertical()
        }
    }

    private fun adjustItemVertical() {
        mPreviousScrollerY = 0
        var deltaY = mInitialFirstItemOffset - mCurrentFirstItemOffset

        if (Math.abs(deltaY) > mItemHeight / 2) {
            deltaY += if (deltaY > 0)
                -mItemHeight
            else
                mItemHeight
        }

        if (deltaY != 0) {
            mOverScroller!!.startScroll(scrollX, scrollY, 0, deltaY, 800)
            invalidateOnAnimation()
        }

        onScrollStateChange(SCROLL_STATE_IDLE)
    }

    private fun recyclerVelocityTracker() {
        mVelocityTracker?.recycle()
        mVelocityTracker = null
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
    }

    private fun onScrollStateChange(scrollState: Int) {
        if (mScrollState == scrollState) {
            return
        }
        mScrollState = scrollState
        mOnScrollListener?.onScrollStateChange(this, scrollState)
    }

    private fun getItemHeight(): Int {
        return height / (mSelectorItemCount - 2)
    }

    private fun getGapHeight(): Int {
        return getItemHeight() - computeTextHeight()
    }

    private fun computeTextHeight(): Int {
        val metricsInt = mTextPaint.fontMetricsInt
        return abs(metricsInt.bottom + metricsInt.top)
    }

    private fun invalidateOnAnimation() {
        postInvalidateOnAnimation()
    }

    private fun drawVertical(canvas: Canvas) {
        if (mSelectorItemIndices.size == 0)
            return
        val itemHeight = getItemHeight()

        val x = when (mTextPaint.textAlign) {
            Paint.Align.LEFT -> paddingLeft.toFloat()
            Paint.Align.CENTER -> ((right - left) / 2).toFloat()
            Paint.Align.RIGHT -> (right - left).toFloat() - paddingRight.toFloat()
            else -> ((right - left) / 2).toFloat()
        }

        var y = mCurrentFirstItemOffset.toFloat()

        var i = 0

        val topIndexDiffToMid = mRawPickerVisibleItemMiddleIndex
        val bottomIndexDiffToMid = mSelectorVisibleItemCount - mRawPickerVisibleItemMiddleIndex - 1
        val maxIndexDiffToMid = Math.max(topIndexDiffToMid, bottomIndexDiffToMid)

        while (i < mSelectorItemIndices.size) {
            var scale = 1f

            val offsetToMiddle =
                Math.abs(y - (mInitialFirstItemOffset + mRawPickerMiddleItemIndex * itemHeight).toFloat())

            if (maxIndexDiffToMid != 0)
                scale =
                    mSelectedTextScale * (itemHeight * maxIndexDiffToMid - offsetToMiddle) / (itemHeight * maxIndexDiffToMid) + 1

            if (mSelectorItemValidStatus[i]) {
                if (offsetToMiddle < mItemHeight / 2) {
                    mTextPaint.color = mSelectedTextColor
                } else {
                    mTextPaint.color = mUnSelectedTextColor
                }
            } else {
                mTextPaint.color = ContextCompat.getColor(context, R.color.material_grey_300)
            }

            canvas.save()
            canvas.scale(scale, scale, x, y)
            canvas.drawText(getValue(mSelectorItemIndices[i]), x, y, mTextPaint)
            canvas.restore()

            y += itemHeight
            i++
        }
    }

    private fun getPosition(value: String): Int = when {
        mAdapter != null -> {
            validatePosition(mAdapter!!.getPosition(value))
        }
        else -> try {
            val position = value.toInt()
            validatePosition(position)
        } catch (e: NumberFormatException) {
            0
        }
    }

    private fun increaseSelectorsIndex() {
        for (i in 0 until (mSelectorItemIndices.size - 1)) {
            mSelectorItemIndices[i] = mSelectorItemIndices[i + 1]
            mSelectorItemValidStatus[i] = mSelectorItemValidStatus[i + 1]
        }
        var nextScrollSelectorIndex = mSelectorItemIndices[mSelectorItemIndices.size - 2] + 1
        if (mWrapSelectorRawPickerPreferred && nextScrollSelectorIndex > mMaxIndex) {
            nextScrollSelectorIndex = mMinIndex
        }
        mSelectorItemIndices[mSelectorItemIndices.size - 1] = nextScrollSelectorIndex
        mSelectorItemValidStatus[mSelectorItemIndices.size - 1] =
            isValidPosition(nextScrollSelectorIndex)
    }

    private fun decreaseSelectorsIndex() {
        for (i in mSelectorItemIndices.size - 1 downTo 1) {
            mSelectorItemIndices[i] = mSelectorItemIndices[i - 1]
            mSelectorItemValidStatus[i] = mSelectorItemValidStatus[i - 1]
        }
        var nextScrollSelectorIndex = mSelectorItemIndices[1] - 1
        if (mWrapSelectorRawPickerPreferred && nextScrollSelectorIndex < mMinIndex) {
            nextScrollSelectorIndex = mMaxIndex
        }
        mSelectorItemIndices[0] = nextScrollSelectorIndex
        mSelectorItemValidStatus[0] = isValidPosition(nextScrollSelectorIndex)
    }

    private fun changeValueBySteps(steps: Int) {
        mPreviousScrollerY = 0
        mOverScroller!!.startScroll(0, 0, 0, -mItemHeight * steps, SNAP_SCROLL_DURATION)
        invalidate()
    }

    private fun onSelectionChanged(current: Int, notifyChange: Boolean) {
        val previous = mCurSelectedItemIndex
        mCurSelectedItemIndex = current
        if (notifyChange && previous != current) {
            notifyChange(previous, current)
        }
    }

    private fun getWrappedSelectorIndex(selectorIndex: Int): Int {
        if (selectorIndex > mMaxIndex) {
            return mMinIndex + (selectorIndex - mMaxIndex) % (mMaxIndex - mMinIndex + 1) - 1
        } else if (selectorIndex < mMinIndex) {
            return mMaxIndex - (mMinIndex - selectorIndex) % (mMaxIndex - mMinIndex + 1) + 1
        }
        return selectorIndex
    }

    private fun notifyChange(previous: Int, current: Int) {
        mOnValueChangeListener?.onValueChange(this, getValue(previous), getValue(current))
    }

    private fun validatePosition(position: Int): Int {
        return if (!mWrapSelectorRawPickerPreferred) {
            when {
                mMaxValidIndex == null && position > mMaxIndex -> mMaxIndex
                mMaxValidIndex != null && position > mMaxValidIndex!! -> mMaxValidIndex!!
                mMinValidIndex == null && position < mMinIndex -> mMinIndex
                mMinValidIndex != null && position < mMinValidIndex!! -> mMinValidIndex!!
                else -> position
            }
        } else {
            getWrappedSelectorIndex(position)
        }
    }

    fun scrollTo(position: Int) {
        if (mCurSelectedItemIndex == position)
            return

        mCurSelectedItemIndex = position
        mSelectorItemIndices.clear()
        for (i in 0 until mSelectorItemCount) {
            var selectorIndex = mCurSelectedItemIndex + (i - mRawPickerMiddleItemIndex)
            if (mWrapSelectorRawPickerPreferred) {
                selectorIndex = getWrappedSelectorIndex(selectorIndex)
            }
            mSelectorItemIndices.add(selectorIndex)
        }

        invalidate()
    }

    fun setOnValueChangedListener(onValueChangeListener: OnValueChangeListener) {
        mOnValueChangeListener = onValueChangeListener
    }

    fun setOnScrollListener(onScrollListener: OnScrollListener) {
        mOnScrollListener = onScrollListener
    }

    fun smoothScrollTo(position: Int) {
        val realPosition = validatePosition(position)
        changeValueBySteps(realPosition - mCurSelectedItemIndex)
    }

    fun smoothScrollToValue(value: String) {
        smoothScrollTo(getPosition(value))
    }

    fun scrollToValue(value: String) {
        scrollTo(getPosition(value))
    }

    /**
     * Set user define adapter
     *
     * @adapter user define adapter
     * @indexRangeBasedOnAdapterSize specific if the picker's min~max range is based on adapter's size
     */
    fun setAdapter(adapter: RawPickerAdapter?, indexRangeBasedOnAdapterSize: Boolean = true) {
        mAdapter = adapter
        if (mAdapter == null) {
            initializeSelectorRawPickerIndices()
            invalidate()
            return
        }

        if (adapter!!.getSize() != -1 && indexRangeBasedOnAdapterSize) {
            mMaxIndex = adapter.getSize() - 1
            mMinIndex = 0
        }

        mMaxValidIndex = adapter.getMaxValidIndex()
        mMinValidIndex = adapter.getMinValidIndex()

        initializeSelectorRawPickerIndices()
        invalidate()

        mAdapter?.picker = this
    }

    fun setWrapSelectorRawPicker(wrap: Boolean) {
        mWrapSelectorRawPickerPreferred = wrap
        invalidate()
    }

    fun getWrapSelectorRawPicker(): Boolean {
        return mWrapSelectorRawPickerPreferred
    }

    fun setRawPickerItemCount(count: Int) {
        mSelectorItemCount = count + 2
        mRawPickerMiddleItemIndex = (mSelectorItemCount - 1) / 2
        mSelectorVisibleItemCount = mSelectorItemCount - 2
        mRawPickerVisibleItemMiddleIndex = (mSelectorVisibleItemCount - 1) / 2
        mSelectorItemIndices = ArrayList(mSelectorItemCount)
        mSelectorItemValidStatus = ArrayList(mSelectorItemCount)
        reset()
        invalidate()
    }

    fun getValue(position: Int): String = when {
        mAdapter != null -> mAdapter!!.getValue(position)
        else -> if (!mWrapSelectorRawPickerPreferred) {
            when {
                position > mMaxIndex -> ""
                position < mMinIndex -> ""
                else -> position.toString()
            }
        } else {
            getWrappedSelectorIndex(position).toString()
        }
    }

    fun setValue(value: String) {
        scrollToValue(value)
    }

    fun setMaxValue(max: Int) {
        mMaxIndex = max
    }

    fun getMaxValue(): String {
        return if (mAdapter != null) {
            mAdapter!!.getValue(mMaxIndex)
        } else {
            mMaxIndex.toString()
        }
    }

    fun setMinValue(min: Int) {
        mMinIndex = min
    }

    fun setMinValidValue(minValid: Int?) {
        mMinValidIndex = minValid
    }

    fun setMaxValidValue(maxValid: Int?) {
        mMaxValidIndex = maxValid
    }

    fun getMinValue(): String {
        return if (mAdapter != null) {
            mAdapter!!.getValue(mMinIndex)
        } else {
            mMinIndex.toString()
        }
    }

    fun reset() {
        initializeSelectorRawPickerIndices()
        initializeSelectorRawPicker()
        invalidate()
    }

    fun getCurrentItem(): String {
        return getValue(mCurSelectedItemIndex)
    }

    fun isValidPosition(position: Int): Boolean {
        return when {
            mMinValidIndex != null && position < mMinValidIndex!! -> false
            mMaxValidIndex != null && position > mMaxValidIndex!! -> false
            else -> true
        }
    }

    abstract class RawPickerAdapter {

        abstract fun getValue(position: Int): String

        abstract fun getPosition(vale: String): Int

        abstract fun getTextWithMaximumLength(): String

        open fun getSize(): Int = -1

        open fun getMinValidIndex(): Int? {
            return null
        }

        open fun getMaxValidIndex(): Int? {
            return null
        }

        var picker: RawPicker? = null

        fun notifyDataSetChanged() {
            picker?.setAdapter(this)
            picker?.requestLayout()
        }
    }
}