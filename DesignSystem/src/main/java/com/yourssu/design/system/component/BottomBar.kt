package com.yourssu.design.system.component

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableInt
import com.yourssu.design.R
import com.yourssu.design.databinding.ItemBottomTabBinding
import com.yourssu.design.databinding.LayoutBottomBarBinding
import com.yourssu.design.system.rule.Vibration
import com.yourssu.design.system.rule.vibe
import com.yourssu.design.undercarriage.animation.startAnim
import java.lang.Exception
import kotlin.collections.List

class BottomBar @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
        defStyleRes: Int = 0
): LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    class BottomTabInfo(
        val primaryName: String, // 탭 선택시 반환 받을 고유 name
        @DrawableRes val iconDrawable: Int = -1,
        @DrawableRes val iconSelectedDrawable: Int = -1,
        val description: String? = null
    )

    interface TabClickListener {
        fun tabClicked(primaryName: String)
        fun tabChanged(primaryName: String)
        fun tabLongClicked(primaryName: String)
    }

    private val binding: LayoutBottomBarBinding by lazy {
        LayoutBottomBarBinding.inflate(LayoutInflater.from(context), this, true)
    }

    private var tabList = listOf<BottomTabInfo>()
    private var bindingMap: MutableMap<Int, ItemBottomTabBinding> = mutableMapOf()
    var bottomTabType = ObservableInt(0)

    private var isImpactFeedbackEnabled = true

    var tabClickListener: TabClickListener? = null

    init {
        initialize(attrs, defStyleAttr, defStyleRes)
    }

    private fun initialize(attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {

        context.withStyledAttributes(attrs, R.styleable.BottomBar, defStyleAttr, defStyleRes) {
            isImpactFeedbackEnabled = getBoolean(R.styleable.BottomBar_isImpactFeedbackEnabled, true)
        }
    }

    fun setTabList(list: List<BottomTabInfo>) {
        tabList = list
        bindingMap = mutableMapOf()

        updateTabStatus()
    }

    fun setSelectedTab(primaryName: String) {
        val selectedIndex = try {
            tabList.map { it.primaryName }
                .indexOf(primaryName)
        } catch (e: Exception) {
            INVALID_SELECTED_INDEX
        }

        if (selectedIndex != INVALID_SELECTED_INDEX) {
            this.bottomTabType.set(selectedIndex)
            tabClickListener?.tabChanged(primaryName)

            updateTabStatus(selectedIndex)
        }
    }

    private fun updateTabStatus(animationIndex: Int? = null) {
        binding.tabArea.removeAllViews()
        tabList.forEachIndexed { index, bottomTabInfo ->
            val item: ItemBottomTabBinding = ItemBottomTabBinding.inflate(LayoutInflater.from(context), this, false)

            bindingMap[index] = item

            item.icon.contentDescription = bottomTabInfo.description // 접근성 관련

            if (bottomTabType.get() == index) {
                item.icon.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.bottomBarSelected))
                item.icon.setImageDrawable(ContextCompat.getDrawable(context, bottomTabInfo.iconSelectedDrawable))
            } else {
                item.icon.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.bottomBarNormal))
                item.icon.setImageDrawable(ContextCompat.getDrawable(context, bottomTabInfo.iconDrawable))
            }

            item.root.setOnClickListener {
                tabClicked(index)
            }
            item.root.setOnLongClickListener {
                longTabClicked(index)
                return@setOnLongClickListener true
            }

            binding.tabArea.addView(item.root)
        }

        animationIndex?.let { springAnimation(it) } // 뷰 업데이트 이후 애니메이션이 필요한 경우 실행
    }

    private fun tabClicked(index: Int) {
        val primaryName = tabList[index].primaryName

        tabClickListener?.tabClicked(primaryName)

        springAnimation(index)
        touchVibrationFeedback(Vibration.INTERACT)
    }

    private fun longTabClicked(index: Int) {
        val primaryName = tabList[index].primaryName

        springAnimation(index)
        touchVibrationFeedback(Vibration.SUCCESS)

        tabClickListener?.tabLongClicked(primaryName)
    }

    fun springAnimation(index: Int) {
        bindingMap[index]?.icon?.run {
            startAnim(R.anim.bottomtab_spring_motion)
        }
    }

    private fun touchVibrationFeedback(vibration: Vibration) {
        if (isImpactFeedbackEnabled) {
            context.vibe(vibration)
        }
    }

    companion object {
        private const val INVALID_SELECTED_INDEX = -1

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

        @JvmStatic
        @BindingAdapter("selectedTabPrimaryName")
        fun setSelectedTab(bottomBar: BottomBar, primaryName: String) {
            bottomBar.setSelectedTab(primaryName)
        }

        @JvmStatic
        @BindingAdapter("isImpactFeedbackEnabled")
        fun setIsImpactFeedbackEnabled(bottomBar: BottomBar, boolean: Boolean) {
            bottomBar.isImpactFeedbackEnabled = boolean
        }
    }
}