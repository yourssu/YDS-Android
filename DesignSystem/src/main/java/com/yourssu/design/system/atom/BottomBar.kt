package com.yourssu.design.system.atom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import androidx.core.content.withStyledAttributes
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableInt
import com.yourssu.design.R
import com.yourssu.design.databinding.ItemBottomTabBinding
import com.yourssu.design.databinding.LayoutBottomBarBinding
import com.yourssu.design.undercarriage.animation.endListener
import com.yourssu.design.undercarriage.animation.startAnim

class BottomBar @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
        defStyleRes: Int = 0
): LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    class BottomTabInfo(@DrawableRes val iconDrawable: Int = -1,
                        @DrawableRes val iconSelectedDrawable: Int = -1,
                        val description: String? = null)

    interface TabClickListener {
        fun tabClicked(itemIndex: Int)
        fun tabChanged(itemIndex: Int)
        fun tabLongClicked(itemIndex: Int)
    }

    lateinit var binding: LayoutBottomBarBinding
    private var isCanChangeTab = true
    private var tabList = listOf<BottomTabInfo>()
    private var bindingMap: MutableMap<Int, ItemBottomTabBinding> = mutableMapOf()
    var bottomTabType = ObservableInt(0)

    var tabClickListener: TabClickListener? = null

    init {
        initialize(attrs, defStyleAttr, defStyleRes)
    }

    private fun initialize(attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_bottom_bar, this, true)

        context.withStyledAttributes(attrs, R.styleable.BottomBar, defStyleAttr, defStyleRes) {
            bottomTabType.set(getInteger(R.styleable.BottomBar_selectedIndex, 0))
            isCanChangeTab = getBoolean(R.styleable.BottomBar_canChangeIndex, true)
        }
    }

    fun setTabList(list: List<BottomTabInfo>) {
        tabList = list
        bindingMap = mutableMapOf()
        updateTabStatus()
    }

    private fun updateTabStatus() {
        binding.tabArea.removeAllViews()
        tabList.forEachIndexed { index, bottomTabInfo ->
            val item: ItemBottomTabBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_bottom_tab, this, false)

            bindingMap[index] = item

            if (bottomTabType.get() == index) {
                item.icon.setBackgroundResource(bottomTabInfo.iconSelectedDrawable)
            } else {
                item.icon.setBackgroundResource(bottomTabInfo.iconDrawable)
            }

            item.root.setOnClickListener {
                tabClicked(index)
                springAnimation(index)
            }
            item.root.setOnLongClickListener {
                longTabClicked(index)
                return@setOnLongClickListener true
            }

            binding.tabArea.addView(item.root)
        }
    }

    private fun tabClicked(index: Int) {
        tabClickListener?.tabClicked(index)
        if (isCanChangeTab) {
            this.bottomTabType.set(index)
            tabClickListener?.tabChanged(index)
        }
        updateTabStatus()
    }

    fun springAnimation(index: Int) {
        bindingMap[index]?.icon?.run {
            startAnim(R.anim.scale_normal_to_large, endListener {
                startAnim(R.anim.scale_large_to_normal)
            })
        }
    }

    private fun longTabClicked(index: Int) {
        tabClickListener?.tabLongClicked(index)
    }

    companion object {
        @JvmStatic
        @BindingAdapter("setTabClickListener")
        fun setTabClickListener(bottomBar: BottomBar, tabClickListener: TabClickListener) {
            bottomBar.tabClickListener = tabClickListener
        }

        @JvmStatic
        @BindingAdapter("setTabInfoList")
        fun setTabInfoList(bottomBar: BottomBar, list: List<BottomTabInfo>) {
            bottomBar.setTabList(list)
        }
    }
}