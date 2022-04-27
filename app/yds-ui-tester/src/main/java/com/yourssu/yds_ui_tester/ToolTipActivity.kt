package com.yourssu.yds_ui_tester


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.yourssu.design.system.atom.ToolTip
import com.yourssu.yds_ui_tester.databinding.ActivityToolTipBinding
import kotlinx.coroutines.GlobalScope


class ToolTipActivity : AppCompatActivity() {
    private lateinit var binding: ActivityToolTipBinding
    private lateinit var viewModel: ToolTipViewModel
    var toolTipBuilder: ToolTip.Builder = ToolTip.Builder()

    var hopeReferencePositionNum = -1
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_tool_tip)
        viewModel = ViewModelProvider(this).get(ToolTipViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        toolTipBuilder
            .withContext(context = this)
            .withWindowManager(windowManager)
            .withLayoutInflater(layoutInflater)

        binding.hopePositionSpinner.adapter = ArrayAdapter.createFromResource(
            this,
            R.array.itemList,
            android.R.layout.simple_spinner_item
        )
        //아이템 선택 리스너
        binding.hopePositionSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    when (position) {
                        //참조뷰 위에 붙이길 희망
                        0 -> {
                            hopeReferencePositionNum = 0
                        }
                        //참조뷰 아래에 붙이길 희망
                        1 -> {
                            hopeReferencePositionNum = 1
                        }
                        //참조뷰 오른쪽에 붙이길 희망
                        2 -> {
                            hopeReferencePositionNum = 2
                        }
                        //참조뷰 왼쪽에 붙이길 희망
                        3 -> {
                            hopeReferencePositionNum = 3
                        }
                        //희망하는 위치 없음. 알아서 붙여주길 바람.
                        else -> {
                            hopeReferencePositionNum = -1
                        }
                    }
                }
            }
    }

    fun onClick(view: View) {
        val toolTip: ToolTip =
            toolTipBuilder.withReferenceView(view)
            .withIsNormal(binding.toggle.isSelected)
            .withStringContents(binding.tootipText.text.toString())
            .withHopeLocation(hopeReferencePositionNum).build()

        toolTip.show()
    }

//    fun onClick(view: View){
//        toolTip = ToolTip.Builder().build(
//            this,
//            windowManager, //툴팁을 띄울 뷰그룹. 맨외곽에서 match_parent로 구성된 뷰그룹이면 됨.
//            layoutInflater, //이게 있어야 함.
//            view, //툴팁이 붙을 뷰
//            binding.toggle.isSelected,  //툴팁 색상정보
//            binding.tootipText.text.toString(),
//            hopeReferencePositionNum  //희망 위치
//        )
//
//        toolTip!!.show()  //띄우기
//    }

}