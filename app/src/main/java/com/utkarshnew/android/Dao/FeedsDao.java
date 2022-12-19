package com.utkarshnew.android.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.utkarshnew.android.table.PostDataTable;

import java.util.List;

@Dao
public interface FeedsDao {
    @Insert
    void insertPost(PostDataTable post);

    @Query("DELETE FROM PostDataTable")
    void deletedata();


    @Query("UPDATE PostDataTable  SET  my_pinned =:pinnedPost  WHERE id = :post_id ")
    void updatePinnedPost(String pinnedPost, String post_id);

    @Query("SELECT * FROM PostDataTable WHERE masterCat = :mainCat")
    List<PostDataTable> retrievePostData(String mainCat);



    @Query("SELECT * FROM PostDataTable WHERE parentId = :parentId AND masterCat =:main AND sub_cat_id =:subcat")
    List<PostDataTable> retrievePostData(String parentId,String main,String subcat);


    @Query("SELECT * FROM PostDataTable WHERE parentId = :parentId AND masterCat =:main AND sub_cat_id =:subcat And post_type =:posttype")
    List<PostDataTable> retrievePostData_viaposttype(String parentId,String main,String subcat,String posttype);



    @Query("SELECT * FROM PostDataTable WHERE parentId = :parentId AND masterCat =:main  And post_type =:posttype")
    List<PostDataTable> retrievePostData_viaposttype_withoutsubcat(String parentId,String main,String posttype);


    @Query("SELECT * FROM PostDataTable WHERE parentId = :parentId AND masterCat =:main ")
    List<PostDataTable> retrievePostData_withoutsubcat(String parentId,String main);


    @Query("SELECT * FROM PostDataTable WHERE id = :postid AND masterCat =:mainCat")
    PostDataTable retriveObject(String mainCat,String postid);


    @Query("SELECT * FROM PostDataTable WHERE id = :postid")
    PostDataTable postData(String postid);

  @Query("SELECT EXISTS(SELECT * FROM PostDataTable WHERE id = :postid)")
    boolean isFeedExist(String postid);



    @Query("SELECT EXISTS(SELECT * FROM PostDataTable WHERE post_type = :posttype AND sub_cat_id =:subcat)")
    boolean isFeedExistviamaincat(String posttype,String subcat);



    @Query("SELECT EXISTS(SELECT * FROM PostDataTable WHERE post_type = :posttype  AND masterCat =:maincat )")
    boolean isliveclassExist(String posttype,String maincat);




    @Query("UPDATE PostDataTable  SET  json =:json  WHERE id = :post_id AND masterCat =:mainid ")
    void update_result(String json, String post_id, String mainid);




    @Query("UPDATE PostDataTable  SET  my_like =:myLike,total_likes =:totalike   WHERE id = :post_id ")
    void updateMyLike(String post_id, String myLike,String totalike);


    @Query("UPDATE PostDataTable  SET  json =:json   WHERE id = :post_id ")
    void updateMyjson(String post_id, String json);




    @Query("DELETE FROM PostDataTable")
    void deletePosts();


    @Query("DELETE FROM PostDataTable where masterCat =:mainCat")
    void deletePosts_via_id(String mainCat);



    @Query("DELETE FROM PostDataTable where masterCat =:mainCat AND sub_cat_id =:subcat")
    void deletePost(String mainCat,String subcat);


    @Query("DELETE FROM PostDataTable where masterCat =:mainCat AND post_type IN (:liveclassposttype,:livetestposttype) ")
    void deletePosts_via_post_type(String mainCat,String liveclassposttype,String livetestposttype);

    @Query("DELETE FROM PostDataTable where masterCat =:mainCat AND post_type NOT IN (:liveclassposttype,:livetestposttype) ")
    void deletepost_without_liveclass(String mainCat,String liveclassposttype,String livetestposttype);


    @Query("DELETE FROM PostDataTable where masterCat =:mainCat And sub_cat_id =:subcat AND post_type NOT IN (:liveclassposttype,:livetestposttype) ")
    void deleteSubCatFeed(String mainCat,String subcat,String liveclassposttype,String livetestposttype);

}