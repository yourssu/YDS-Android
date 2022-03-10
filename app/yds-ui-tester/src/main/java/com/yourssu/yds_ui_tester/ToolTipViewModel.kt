package com.yourssu.yds_ui_tester

import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yourssu.design.system.atom.ToolTip

class ToolTipViewModel: ViewModel() {
//    private val _toolTipText: MutableLiveData<String> by lazy{
//        MutableLiveData<String>().also{
//            it.value= "가이드용입니다."
//        }
//    }
//    val toolTipText: LiveData<String>
//        get() = _toolTipText
//
//    private val _positionViewRect:MutableLiveData<Rect> by lazy {
//        MutableLiveData<Rect>().also {
//            it.value=Rect(0,0,0,0)
//        }
//    }
//    val positionViewRect:LiveData<Rect>
//        get() = _positionViewRect
//
//    private val _toolTipLength: MutableLiveData<Float> by lazy{
//        MutableLiveData<Float>().also{
//            it.value= ToolTip.ShortTooltip
//        }
//    }
//    val toolTipLength: LiveData<Float>
//        get() = _toolTipLength
//
//
//    private val _toolTipTailPosition: MutableLiveData<Int> by lazy{
//        MutableLiveData<Int>().also{
//            it.value= ToolTip.Position_BottomTail
//        }
//    }
//    val toolTipTailPosition: LiveData<Int>
//        get() = _toolTipTailPosition
//
//
//    private val _toolTipTail: MutableLiveData<Int> by lazy{
//        MutableLiveData<Int>().also{
//            it.value= ToolTip.Tail_center
//        }
//    }
//    val toolTipTail: LiveData<Int>
//        get() = _toolTipTail
//
//
//
//    fun changePositionView(view: View) {
//        val rect=Rect()
//        view.getGlobalVisibleRect(rect)
//        Log.d("kmj","뷰모델함수: ${rect.left}, ${rect.right}, ${rect.top}, ${rect.bottom} :: $rect")
//        _positionViewRect.postValue(rect)
//    }
}