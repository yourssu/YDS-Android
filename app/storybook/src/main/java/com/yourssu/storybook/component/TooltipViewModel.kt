package com.yourssu.storybook.component

import android.app.Application
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.yourssu.design.system.atom.Toggle
import com.yourssu.design.system.atom.ToolTip
import com.yourssu.design.system.atom.TooltipDuration
import com.yourssu.design.undercarriage.base.TextField
import com.yourssu.storybook.BaseViewModel

class TooltipViewModel(application: Application) : BaseViewModel(application) {
    var tooltipBuilders: ToolTip.Builder? = null
    val hopeLocation: MutableLiveData<String> = MutableLiveData("on the reference view")
    val isNormal: MutableLiveData<Boolean> = MutableLiveData(true)
    val explainText: MutableLiveData<String> = MutableLiveData("explain")
    val toastTime: MutableLiveData<Boolean> = MutableLiveData(true)

    //설명문 실시간 저장.
    val onExplainTextChangedListener = object : TextField.OnTextChanged {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s?.toString()?.let { explainText.value = it }
        }
    }

    val selectedStateListener = object : Toggle.SelectedListener {
        override fun onSelected(boolean: Boolean) {
            isNormal.value = boolean
        }
    }

    val toastTimeStateListener = object : Toggle.SelectedListener {
        override fun onSelected(boolean: Boolean) {
            toastTime.value = boolean
        }
    }

    private val hopelocationList = listOf<Pair<String, Int>>(
        "on the reference view" to ToolTip.ABOVE,
        "under the reference view" to ToolTip.BELOW,
        "to the right of the reference view" to ToolTip.RIGHT_SIDE,
        "to the left of the reference view" to ToolTip.LEFT_SIDE,
        "random" to -1
    )
    private val locationList = listOf<String>(
        hopelocationList[0].first,
        hopelocationList[1].first,
        hopelocationList[2].first,
        hopelocationList[3].first,
        hopelocationList[4].first
    )

    fun onClick(view: View) {
        val i = locationList.indexOf(hopeLocation.value)
        val toolTip: ToolTip =
            tooltipBuilders
                ?.withIsNormal(!(isNormal.value!!))
                ?.withStringContents(explainText.value.toString())
                ?.withHopeLocation(hopelocationList[i].second)
                ?.withToastLength(
                    when (toastTime.value) {
                        true -> TooltipDuration.LengthLong
                        else -> TooltipDuration.LengthShort
                    }
                )!!.build(view)

        toolTip.show()
    }


}