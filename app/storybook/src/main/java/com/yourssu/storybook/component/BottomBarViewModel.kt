package com.yourssu.storybook.component

import android.app.Application
import com.yourssu.design.system.component.BottomBar
import com.yourssu.storybook.BaseViewModel
import com.yourssu.storybook.R

class BottomBarViewModel(application: Application): BaseViewModel(application) {
    val bottomType = listOf<BottomBar.BottomTabInfo>(
        BottomBar.BottomTabInfo(R.drawable.ic_home_line, R.drawable.ic_home_filled),
        BottomBar.BottomTabInfo(R.drawable.ic_board_line, R.drawable.ic_board_filled),
        BottomBar.BottomTabInfo(R.drawable.ic_timecalendar_line, R.drawable.ic_timecalendar_filled),
        BottomBar.BottomTabInfo(R.drawable.ic_rank_line, R.drawable.ic_rank_filled),
        BottomBar.BottomTabInfo(R.drawable.ic_person_line, R.drawable.ic_person_filled)
    )
}