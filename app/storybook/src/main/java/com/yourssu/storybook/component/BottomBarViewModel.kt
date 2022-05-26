package com.yourssu.storybook.component

import android.app.Application
import androidx.databinding.ObservableField
import com.yourssu.design.system.component.BottomBar
import com.yourssu.storybook.BaseViewModel
import com.yourssu.storybook.R

class BottomBarViewModel(application: Application): BaseViewModel(application) {
    val bottomType = listOf<BottomBar.BottomTabInfo>(
        BottomBar.BottomTabInfo(BottomType.HOME.name, R.drawable.ic_home_line, R.drawable.ic_home_filled),
        BottomBar.BottomTabInfo(BottomType.BOARD.name, R.drawable.ic_board_line, R.drawable.ic_board_filled),
        BottomBar.BottomTabInfo(BottomType.TIME_TABLE.name, R.drawable.ic_timecalendar_line, R.drawable.ic_timecalendar_filled),
        BottomBar.BottomTabInfo(BottomType.RANK.name, R.drawable.ic_rank_line, R.drawable.ic_rank_filled),
        BottomBar.BottomTabInfo(BottomType.SETTING.name, R.drawable.ic_person_line, R.drawable.ic_person_filled)
    )

    val selectedBottomBarTapName = ObservableField<String>(BottomType.HOME.name)

    val tabClickListener = object : BottomBar.TabClickListener {
        override fun tabClicked(primaryName: String) {
            selectedBottomBarTapName.set(primaryName)
        }
        override fun tabChanged(primaryName: String) {}
        override fun tabLongClicked(primaryName: String) {}
    }

    enum class BottomType {
        HOME,
        BOARD,
        TIME_TABLE,
        RANK,
        SETTING,
    }
}