package com.yourssu.yds_ui_tester

import android.graphics.Point
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.yourssu.design.system.atom.ToolTip
import com.yourssu.yds_ui_tester.databinding.ActivityToolTipBinding
import org.json.JSONObject.NULL

class ToolTipActivity : AppCompatActivity() {
    private lateinit var binding: ActivityToolTipBinding
    private lateinit var viewModel: ToolTipViewModel
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_tool_tip)
        viewModel = ViewModelProvider(this).get(ToolTipViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        var toolTip : ToolTip ?= null


        //외곽에 match_parent뷰그룹이 필요한 이유: 해당 툴팁을 띄우기 위해선 툴팁이 붙을
        //뷰그룹이 필요함. 또한 그 툴팁이 새로 추가 됐을때 뷰그룹의 크기가 변하면 안됨.
        //따라서 툴팁이 붙어도 크기가 안변할 뷰그룹이 필요함.
        //툴팁을 뷰그룹에 붙이지 않을것이라면 뷰개념보다는 팝업개념으로 가야할듯.
        binding.testBt.setOnClickListener {
            toolTip = ToolTip.Builder(
                this,
                windowManager, //툴팁을 띄울 뷰그룹. 맨외곽에서 match_parent로 구성된 뷰그룹이면 됨.
                layoutInflater,
                binding.testBt, //툴팁이 붙을 뷰
                true,  //툴팁 색상정보
                "12345678901234567890123456",
                0
            ).build()

//            val rect=Rect()
//            binding.rootLayout.getGlobalVisibleRect(rect)
//            Log.d(
//                "kmj",
//                " 스크린 사이즈: ${rect.left} ${rect.right} ${rect.top} ${rect.bottom} "
//            )
//            val rect1=Rect()
//            binding.testBt.getGlobalVisibleRect(rect1)
//            Log.d(
//                "kmj",
//                " 참조뷰 절대좌표 사이즈: ${rect1.left} ${rect1.right} ${rect1.top} ${rect1.bottom} "
//            )
            toolTip!!.show()

        }


    }


}