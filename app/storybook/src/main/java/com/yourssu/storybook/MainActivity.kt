package com.yourssu.storybook

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.yourssu.design.system.component.BottomBar
import com.yourssu.storybook.databinding.ActivityMainBinding
import com.yourssu.storybook.transform.ActivityAnimType

class MainActivity : BaseActivity() {

    lateinit var binding: ActivityMainBinding
    override var animationType: ActivityAnimType = ActivityAnimType.STAY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.activity = this

        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentFrame.id, TabType.XML.fragment)
            .commit()
    }

    val tabClickListener = object : BottomBar.TabClickListener {
        override fun tabClicked(primaryName: String) {
            binding.bottomNav.setSelectedTab(primaryName)
            supportFragmentManager.beginTransaction()
                .replace(binding.fragmentFrame.id, TabType.from(primaryName)?.fragment ?: return)
                .commit()
        }
        override fun tabChanged(primaryName: String) {}
        override fun tabLongClicked(primaryName: String) {}
    }

    val tabList = listOf(
        BottomBar.BottomTabInfo(TabType.XML.name, R.drawable.ic_calendar_line, R.drawable.ic_calendar_filled),
        BottomBar.BottomTabInfo(TabType.COMPOSE.name, R.drawable.ic_checkcircle_line, R.drawable.ic_checkcircle_filled),
    )
}

enum class TabType(val fragment: Fragment) {
    XML(MainFragment.newInstance()),
    COMPOSE(MainComposeFragment.newInstance());

    companion object {
        fun from(name: String): TabType? = values().find { it.name == name }
    }
}