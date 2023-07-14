package com.yourssu.design.system.component

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.IntDef
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.yourssu.design.R
import com.yourssu.design.databinding.LayoutTabBarBinding
import com.yourssu.design.system.language.WRAP_CONTENT
import com.yourssu.design.system.language.backgroundColor
import com.yourssu.design.undercarriage.size.dpToIntPx

class TabBar : ConstraintLayout {

    constructor(context: Context) : super(context) {
        initView(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView(context, attrs)
    }

    @SuppressLint("CustomViewStyleable", "PrivateResource")
    private fun initView(context: Context, attrs: AttributeSet?) {
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TabLayout)

            tabMode = typedArray.getInteger(R.styleable.TabLayout_tabMode, MODE_FIXED)

            typedArray.recycle()
        }
    }

    @TabBarMode
    var tabMode: Int = MODE_FIXED
        set(mode) {
            field = mode
            setTabBarInfo()
            requestLayout()
            invalidate()
        }

    private val binding: LayoutTabBarBinding by lazy {
        LayoutTabBarBinding.inflate(LayoutInflater.from(context), this, true)
    }.apply {
        backgroundColor(R.color.bgElevated)
    }

    private fun setTabBarInfo() {
        if (tabMode == MODE_SCROLLABLE) {
            binding.tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
            binding.tabLayout.tabGravity = TabLayout.GRAVITY_START
        } else {
            binding.tabLayout.tabMode = TabLayout.MODE_FIXED
            binding.tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        }

        binding.tabLayout.tabRippleColor = null // disable ripple effect
    }

    fun newTab(): TabLayout.Tab {
        return binding.tabLayout.newTab().apply {
            if (tabMode == MODE_FIXED) {
                setCustomView(R.layout.layout_tab_fixed)
            } else {
                setCustomView(R.layout.layout_tab_scrollable)
            }
        }
    }

    fun addOnTabSelectedListener(listener: TabLayout.OnTabSelectedListener) {
        binding.tabLayout.addOnTabSelectedListener(listener)
    }

    fun addTab(tab: TabLayout.Tab) {
        binding.tabLayout.addTab(tab)
        if (tabMode == MODE_SCROLLABLE) {
            updatePaddingForScrollable()
        } else {
            updatePaddingForFixed()
        }
    }

    private fun updatePaddingForFixed() {
        val slidingTabStrip = binding.tabLayout.getChildAt(SLIDING_TAB_STRIP_INDEX) as LinearLayout
        for (index in 0 until slidingTabStrip.childCount) {
            slidingTabStrip.getChildAt(index).apply {
                updateLayoutParams {
                    width = WRAP_CONTENT
                }
                updatePadding(
                    0, 0, 0, 0
                )
            }
        }
    }

    fun addTab(tab: TabLayout.Tab, setSelected: Boolean) {
        binding.tabLayout.addTab(tab, setSelected)
        if (tabMode == MODE_SCROLLABLE) {
            updatePaddingForScrollable()
        } else {
            updatePaddingForFixed()
        }
    }

    fun addTab(tab: TabLayout.Tab, position: Int) {
        binding.tabLayout.addTab(tab, position)
        if (tabMode == MODE_SCROLLABLE) {
            updatePaddingForScrollable()
        } else {
            updatePaddingForFixed()
        }
    }

    fun addTab(tab: TabLayout.Tab, position: Int, setSelected: Boolean) {
        binding.tabLayout.addTab(tab, position, setSelected)
        if (tabMode == MODE_SCROLLABLE) {
            updatePaddingForScrollable()
        } else {
            updatePaddingForFixed()
        }
    }

    fun clearOnTabSelectedListeners() {
        binding.tabLayout.clearOnTabSelectedListeners()
    }

    fun getSelectedTabPosition(): Int {
        return binding.tabLayout.selectedTabPosition
    }

    fun getTabAt(index: Int): TabLayout.Tab? {
        return binding.tabLayout.getTabAt(index)
    }

    fun getTabCount(): Int {
        return binding.tabLayout.tabCount
    }

    private fun updatePaddingForScrollable() {
        val slidingTabStrip = binding.tabLayout.getChildAt(SLIDING_TAB_STRIP_INDEX) as LinearLayout
        for (index in 0 until slidingTabStrip.childCount) {
            if (index == 0) {
                slidingTabStrip.getChildAt(index).apply {
                    updateLayoutParams {
                        width = context.dpToIntPx(104f)
                    }
                    updatePadding(
                        left = context.dpToIntPx(16f)
                    )
                }

            } else if (index == slidingTabStrip.childCount - 1) {
                slidingTabStrip.getChildAt(index).apply {
                    updateLayoutParams {
                        width = context.dpToIntPx(104f)
                    }
                    updatePadding(
                        right = context.dpToIntPx(16f)
                    )
                }

            } else {
                slidingTabStrip.getChildAt(index).apply {
                    updateLayoutParams {
                        width = WRAP_CONTENT
                    }
                    updatePadding(
                        0, 0, 0, 0
                    )
                }
            }
        }
    }

    fun getTabGravity(): Int {
        return binding.tabLayout.tabGravity
    }

    fun removeAllTabs() {
        binding.tabLayout.removeAllTabs()
    }

    fun removeOnTabSelectedListener(listener: TabLayout.OnTabSelectedListener) {
        binding.tabLayout.removeOnTabSelectedListener(listener)
    }

    fun removeTab(tab: TabLayout.Tab) {
        binding.tabLayout.removeTab(tab)
        if (tabMode == MODE_SCROLLABLE) {
            updatePaddingForScrollable()
        } else {
            updatePaddingForFixed()
        }
    }

    fun removeTabAt(position: Int) {
        binding.tabLayout.removeTabAt(position)
        if (tabMode == MODE_SCROLLABLE) {
            updatePaddingForScrollable()
        } else {
            updatePaddingForFixed()
        }
    }

    fun selectTab(tab: TabLayout.Tab) {
        tab.select()
        binding.tabLayout.selectTab(tab)
    }

    fun selectTab(tab: TabLayout.Tab, updateIndicator: Boolean) {
        if (updateIndicator) tab.select()
        binding.tabLayout.selectTab(tab)
    }

    fun setScrollPosition(
        position: Int, positionOffSet: Float,
        updateSelectedText: Boolean, updateIndicatorPosition: Boolean
    ) {
        binding.tabLayout.setScrollPosition(
            position, positionOffSet, updateSelectedText, updateIndicatorPosition
        )
    }

    fun setScrollPosition(
        position: Int, positionOffSet: Float, updateSelectedText: Boolean
    ) {
        binding.tabLayout.setScrollPosition(
            position, positionOffSet, updateSelectedText
        )
    }

    fun setUpWithViewPager(viewPager: ViewPager?) {
        binding.tabLayout.setupWithViewPager(viewPager)
    }

    fun setUpWithViewPager(viewPager: ViewPager?, autoRefresh: Boolean) {
        binding.tabLayout.setupWithViewPager(viewPager, autoRefresh)
    }

    @Retention(AnnotationRetention.SOURCE)
    @IntDef(value = [MODE_SCROLLABLE, MODE_FIXED])
    annotation class TabBarMode

    companion object {
        private const val SLIDING_TAB_STRIP_INDEX = 0
        const val MODE_SCROLLABLE = 0
        const val MODE_FIXED = 1
    }
}