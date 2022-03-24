package com.yourssu.design.util

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
import com.yourssu.design.system.component.TabBar
import java.lang.ref.WeakReference

/*
 Mediator class for TabBar in YDS
 ref : [TabLayoutMediator](https://developer.android.com/reference/com/google/android/material/tabs/TabLayoutMediator)
 */
class TabBarMediator {
    private var tabBar: TabBar? = null
    private var viewPager: ViewPager2? = null
    private var autoRefresh = false
    private var smoothScroll = false
    private var tabConfigurationStrategy: TabConfigurationStrategy? = null

    private var adapter: RecyclerView.Adapter<*>? = null
    private var attached = false

    private var onPageChangeCallback: TabLayoutOnPageChangeCallback? = null

    private var onTabSelectedListener: OnTabSelectedListener? = null

    private var pagerAdapterObserver: AdapterDataObserver? = null

    constructor(
        tabBar: TabBar,
        viewPager: ViewPager2,
        tabConfigurationStrategy: TabConfigurationStrategy
    ) : this(tabBar, viewPager, true, tabConfigurationStrategy)

    constructor(
        tabBar: TabBar,
        viewPager: ViewPager2,
        autoRefresh: Boolean,
        tabConfigurationStrategy: TabConfigurationStrategy
    ) : this(tabBar, viewPager, autoRefresh, true, tabConfigurationStrategy)


    constructor(
        tabBar: TabBar,
        viewPager: ViewPager2,
        autoRefresh: Boolean,
        smoothScroll: Boolean,
        tabConfigurationStrategy: TabConfigurationStrategy
    ) {
        this.tabBar = tabBar
        this.viewPager = viewPager
        this.autoRefresh = autoRefresh
        this.smoothScroll = smoothScroll
        this.tabConfigurationStrategy = tabConfigurationStrategy
    }

    fun attach() {
        if (attached) {
            throw IllegalStateException("TabLayoutMediator is already attached")
        }
        adapter = viewPager?.adapter
        if (adapter == null) {
            throw IllegalStateException(
                "TabLayoutMediator attached before ViewPager2 has an " + "adapter"
            )
        }
        attached = true;

        onPageChangeCallback = TabLayoutOnPageChangeCallback(tabBar!!)
        viewPager?.registerOnPageChangeCallback(onPageChangeCallback!!)

        onTabSelectedListener = ViewPagerOnTabSelectedListener(viewPager!!, smoothScroll)
        tabBar?.addOnTabSelectedListener(onTabSelectedListener!!)

        if (autoRefresh) {
            pagerAdapterObserver = PagerAdapterObserver()
            adapter!!.registerAdapterDataObserver(pagerAdapterObserver!!)
        }

        populateTabsFromPagerAdapter();

        viewPager?.currentItem?.let { tabBar?.setScrollPosition(it, 0f, true) };
    }

    fun detach() {
        if (autoRefresh && adapter != null) {
            pagerAdapterObserver?.let { adapter?.unregisterAdapterDataObserver(it) };
            pagerAdapterObserver = null;
        }
        onTabSelectedListener?.let { tabBar?.removeOnTabSelectedListener(it) };
        onPageChangeCallback?.let { viewPager?.unregisterOnPageChangeCallback(it) };
        onTabSelectedListener = null;
        onPageChangeCallback = null;
        adapter = null;
        attached = false;
    }

    @SuppressWarnings("WeakReference")
    fun populateTabsFromPagerAdapter() {
        tabBar?.removeAllTabs()
        if (adapter != null && tabBar != null) {
            val adapterCount = adapter!!.itemCount
            for (i in 0 until adapterCount) {
                val tab: TabLayout.Tab = tabBar!!.newTab()
                tabConfigurationStrategy!!.onConfigureTab(tab, i)
                tabBar!!.addTab(tab, false)
            }
            if (adapterCount > 0) {
                val lastItem: Int = tabBar!!.getTabCount() - 1
                val currItem = viewPager!!.currentItem.coerceAtMost(lastItem)
                if (currItem != tabBar!!.getSelectedTabPosition()) {
                    tabBar!!.getTabAt(currItem)?.apply {
                        tabBar!!.selectTab(this)
                    }
                }
            }
        }
    }

    private class ViewPagerOnTabSelectedListener(
        private val viewPager: ViewPager2,
        private val smoothScroll: Boolean
    ) : OnTabSelectedListener {


        override fun onTabSelected(tab: TabLayout.Tab?) {
            if (tab != null) viewPager.setCurrentItem(tab.position, smoothScroll)
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {
        }

        override fun onTabReselected(tab: TabLayout.Tab?) {
        }

    }

    private class TabLayoutOnPageChangeCallback constructor(tabBar: TabBar) :
        ViewPager2.OnPageChangeCallback() {
        private val tabLayoutRef: WeakReference<TabBar> = WeakReference<TabBar>(tabBar)
        private var previousScrollState = 0
        private var scrollState = 0

        init {
            reset()
        }

        override fun onPageScrollStateChanged(state: Int) {
            previousScrollState = scrollState
            scrollState = state
        }

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            val tabBar = tabLayoutRef.get()
            if (tabBar != null) {

                val updateText =
                    scrollState != ViewPager2.SCROLL_STATE_SETTLING || previousScrollState == ViewPager2.SCROLL_STATE_DRAGGING
                val updateIndicator =
                    !(scrollState == ViewPager2.SCROLL_STATE_SETTLING && previousScrollState == ViewPager2.SCROLL_STATE_IDLE)
                tabBar.setScrollPosition(position, positionOffset, updateText, updateIndicator)
            }
        }

        override fun onPageSelected(position: Int) {
            val tabBar = tabLayoutRef.get()
            if (tabBar != null && tabBar.getSelectedTabPosition() != position && position < tabBar.getTabCount()) {
                val updateIndicator = (scrollState == ViewPager2.SCROLL_STATE_IDLE
                        || (scrollState == ViewPager2.SCROLL_STATE_SETTLING
                        && previousScrollState == ViewPager2.SCROLL_STATE_IDLE))
                tabBar.getTabAt(position)?.let { tabBar.selectTab(it, updateIndicator) }
            }
        }

        fun reset() {
            scrollState = ViewPager2.SCROLL_STATE_IDLE
            previousScrollState = scrollState
        }
    }

    private inner class PagerAdapterObserver : AdapterDataObserver() {

        override fun onChanged() {
            populateTabsFromPagerAdapter()
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
            populateTabsFromPagerAdapter()
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
            populateTabsFromPagerAdapter()
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            populateTabsFromPagerAdapter()
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            populateTabsFromPagerAdapter()
        }

        override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
            populateTabsFromPagerAdapter()
        }
    }

}