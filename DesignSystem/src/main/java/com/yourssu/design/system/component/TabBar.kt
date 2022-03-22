package com.yourssu.design.system.component

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.IntDef
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updatePadding
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.yourssu.design.R
import com.yourssu.design.databinding.LayoutTabBarBinding
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
        binding.tabLayout.background
        if (tabMode == MODE_SCROLLABLE) {
            binding.tabLayout.updatePadding(
                left = context.dpToIntPx(16f),
                right = context.dpToIntPx(16f)
            )
            binding.tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
        } else {
            binding.tabLayout.updatePadding(
                left = context.dpToIntPx(0f),
                right = context.dpToIntPx(0f)
            )
            binding.tabLayout.tabMode = TabLayout.MODE_FIXED
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
    }

    fun addTab(tab: TabLayout.Tab, setSelected: Boolean) {
        binding.tabLayout.addTab(tab, setSelected)
    }

    fun addTab(tab: TabLayout.Tab, position: Int) {
        binding.tabLayout.addTab(tab, position)
    }

    fun addTab(tab: TabLayout.Tab, position: Int, setSelected: Boolean) {
        binding.tabLayout.addTab(tab, position, setSelected)
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
    }

    fun removeTabAt(position: Int) {
        binding.tabLayout.removeTabAt(position)
    }

    fun selectTab(tab: TabLayout.Tab) {
        binding.tabLayout.selectTab(tab)
    }

    fun selectTab(tab: TabLayout.Tab, updateIndicator: Boolean) {
        binding.tabLayout.selectTab(tab, updateIndicator)
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
        const val MODE_SCROLLABLE = 0
        const val MODE_FIXED = 1
    }
}