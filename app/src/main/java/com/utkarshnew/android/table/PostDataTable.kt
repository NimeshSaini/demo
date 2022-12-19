package com.utkarshnew.android.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PostDataTable")
class PostDataTable  {
    var created: String = ""
    var page: String = ""
    var masterCat:String = ""
    @PrimaryKey(autoGenerate = true)
    var postId: Int = 0
    var id: String = ""
    var json: String = ""
    var meta_url: String = ""
    var thumbnail: String = ""
    var url: String = ""
    var modified: String = ""
    var my_like: String = ""
    var name: String = ""
    var post_type: String = ""
    var profile_picture: String = ""
    var status: String = ""
    var sub_cat_id: String = ""
    var text: String = ""
    var total_comments: String = ""
    var total_likes: String = ""
    var user_id: String = ""
    var newCourseData: String = ""
    var livetest: String = ""
    var liveclass: String = ""
    var testResult: String = ""
    var bannerlist: String = ""
    var liveClassStatus: String = ""
    var liveTestStatus: String = ""
    var iscommentenable: String = ""
    var sectionposiiton: String = ""
    var limit: String = ""
    var my_pinned: String = ""
    var parentId:String = ""
    var description:String = ""
    var image_type:String?=""
    // var image_type:String = ""
}