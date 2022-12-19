package com.utkarshnew.android.feeds.dataclass

data class Json(
    var attempt_index: String?,
    var options: List<Option>,
    var right_ans: String,
    var total_attempt: String?,
    var state: String?,

    var time_in_mins: String?,
    var total_questions: String?,
    var test_series_name: String?,
    var view_type: String?,

    )