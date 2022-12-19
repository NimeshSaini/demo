package com.utkarshnew.android.feeds.dataclass

import android.app.Activity
import android.content.Intent
import android.view.View
import com.utkarshnew.android.courses.Activity.QuizActivity
import com.utkarshnew.android.Utils.Const
import com.utkarshnew.android.Utils.Helper

data class TestResult(
    val attempts: String,
    val course_id: String,
    val id: String,
    val image: String,
    val state: String,
    val test_series_name: String
)
{
    fun click(v:View,testresultdata: TestResult){
        if (testresultdata.state.equals("1"))
        {
            val resultScreen = Intent(v.context, QuizActivity::class.java)
            resultScreen.putExtra(Const.FRAG_TYPE, Const.RESULT_SCREEN)
            resultScreen.putExtra(Const.STATUS, testresultdata.id)
            resultScreen.putExtra(Const.NAME, testresultdata.test_series_name)
            resultScreen.putExtra("first_attempt", "1")
            Helper.gotoActivity(resultScreen,v.context as Activity)
        }else{
            val resultScreen = Intent(v.context, QuizActivity::class.java)
            resultScreen.putExtra(Const.FRAG_TYPE, "leader_board")
            resultScreen.putExtra(Const.STATUS,  testresultdata.id)
            resultScreen.putExtra(Const.NAME, testresultdata.test_series_name)
            Helper.gotoActivity(resultScreen,v.context as Activity)
        }


    }
}