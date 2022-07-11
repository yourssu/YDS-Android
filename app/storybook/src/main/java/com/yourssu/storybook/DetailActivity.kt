package com.yourssu.storybook

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.yourssu.storybook.databinding.ActivityDetailBinding
import com.yourssu.storybook.transform.ActivityAnimType

class DetailActivity : BaseActivity() {
    companion object {
        private const val DetailPageName = "DetailPageName"
        private const val DetailFragmentClass = "DetailFragmentClass"

        fun Context.navigateToDetail(pageName: String, fragmentClass: Class<*>) {
            this.startActivity(Intent(this, DetailActivity::class.java).apply {
                putExtra(DetailPageName, pageName)
                putExtra(DetailFragmentClass, fragmentClass)
            })
        }
    }

    lateinit var binding: ActivityDetailBinding
    override var animationType: ActivityAnimType = ActivityAnimType.SLIDE


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        setPageName()
        setFragment()
        setBackButton()
    }

    private fun setPageName() {
        intent.getStringExtra(DetailPageName)?.let {
            binding.topBar.title = it
        }
    }

    private fun setFragment() {
        val fragmentClass = intent.getSerializableExtra(DetailFragmentClass) as Class<*>

        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentFrame.id, fragmentClass.getConstructor().newInstance() as Fragment)
            .commitAllowingStateLoss()
    }

    private fun setBackButton() {
        binding.topBar.startButtonClickListener = View.OnClickListener {
            finish()
        }
    }
}