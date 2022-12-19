package com.utkarshnew.android;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.utkarshnew.android.databinding.ActivityFeedDetailsBindingImpl;
import com.utkarshnew.android.databinding.ActivityFeedDetailsBindingSw600dpImpl;
import com.utkarshnew.android.databinding.ActivityFeedDetailsBindingSw720dpImpl;
import com.utkarshnew.android.databinding.ActivityFeedsBindingImpl;
import com.utkarshnew.android.databinding.ActivityFeedsBindingSw600dpImpl;
import com.utkarshnew.android.databinding.ActivityFeedsBindingSw720dpImpl;
import com.utkarshnew.android.databinding.ActivityPurchaseHistoryDetailBindingImpl;
import com.utkarshnew.android.databinding.ActivityPurchaseHistoryDetailBindingSw600dpImpl;
import com.utkarshnew.android.databinding.ActivityPurchaseHistoryDetailBindingSw720dpImpl;
import com.utkarshnew.android.databinding.AddressAdapterBindingImpl;
import com.utkarshnew.android.databinding.AddressAdapterBindingSw600dpImpl;
import com.utkarshnew.android.databinding.AddressAdapterBindingSw720dpImpl;
import com.utkarshnew.android.databinding.ArticleVmBindingImpl;
import com.utkarshnew.android.databinding.ArticleVmBindingSw600dpImpl;
import com.utkarshnew.android.databinding.ArticleVmBindingSw720dpImpl;
import com.utkarshnew.android.databinding.AudioPostBindingImpl;
import com.utkarshnew.android.databinding.AudioPostBindingSw600dpImpl;
import com.utkarshnew.android.databinding.AudioPostBindingSw720dpImpl;
import com.utkarshnew.android.databinding.BannerAdapterImageviewBindingImpl;
import com.utkarshnew.android.databinding.BannerAdapterImageviewBindingSw600dpImpl;
import com.utkarshnew.android.databinding.BannerAdapterImageviewBindingSw720dpImpl;
import com.utkarshnew.android.databinding.BannerViewBindingImpl;
import com.utkarshnew.android.databinding.BannerViewBindingSw600dpImpl;
import com.utkarshnew.android.databinding.BannerViewBindingSw720dpImpl;
import com.utkarshnew.android.databinding.CommentAdapterBindingImpl;
import com.utkarshnew.android.databinding.CommentAdapterBindingSw600dpImpl;
import com.utkarshnew.android.databinding.CommentAdapterBindingSw720dpImpl;
import com.utkarshnew.android.databinding.FeedLiveClassAdapterBindingImpl;
import com.utkarshnew.android.databinding.FeedLiveClassAdapterBindingSw600dpImpl;
import com.utkarshnew.android.databinding.FeedLiveClassAdapterBindingSw720dpImpl;
import com.utkarshnew.android.databinding.FeedLiveTestAdapterBindingImpl;
import com.utkarshnew.android.databinding.FeedLiveTestAdapterBindingSw600dpImpl;
import com.utkarshnew.android.databinding.FeedLiveTestAdapterBindingSw720dpImpl;
import com.utkarshnew.android.databinding.LinkViewBindingImpl;
import com.utkarshnew.android.databinding.LinkViewBindingSw600dpImpl;
import com.utkarshnew.android.databinding.LinkViewBindingSw720dpImpl;
import com.utkarshnew.android.databinding.LiveClassVmBindingImpl;
import com.utkarshnew.android.databinding.LiveClassVmBindingSw600dpImpl;
import com.utkarshnew.android.databinding.LiveClassVmBindingSw720dpImpl;
import com.utkarshnew.android.databinding.LiveTestVmBindingImpl;
import com.utkarshnew.android.databinding.LiveTestVmBindingSw600dpImpl;
import com.utkarshnew.android.databinding.LiveTestVmBindingSw720dpImpl;
import com.utkarshnew.android.databinding.NewCourseVmBindingImpl;
import com.utkarshnew.android.databinding.NewCourseVmBindingSw600dpImpl;
import com.utkarshnew.android.databinding.NewCourseVmBindingSw720dpImpl;
import com.utkarshnew.android.databinding.NewTestResultAdapterBindingImpl;
import com.utkarshnew.android.databinding.NewTestResultAdapterBindingSw600dpImpl;
import com.utkarshnew.android.databinding.NewTestResultAdapterBindingSw720dpImpl;
import com.utkarshnew.android.databinding.NewTestresultVmBindingImpl;
import com.utkarshnew.android.databinding.NewTestresultVmBindingSw600dpImpl;
import com.utkarshnew.android.databinding.NewTestresultVmBindingSw720dpImpl;
import com.utkarshnew.android.databinding.NotesTypeAdapterBindingImpl;
import com.utkarshnew.android.databinding.NotesTypeAdapterBindingSw600dpImpl;
import com.utkarshnew.android.databinding.NotesTypeAdapterBindingSw720dpImpl;
import com.utkarshnew.android.databinding.PostImageBindingImpl;
import com.utkarshnew.android.databinding.PostImageBindingSw600dpImpl;
import com.utkarshnew.android.databinding.PostImageBindingSw720dpImpl;
import com.utkarshnew.android.databinding.QuestionViewBindingImpl;
import com.utkarshnew.android.databinding.QuestionViewBindingSw600dpImpl;
import com.utkarshnew.android.databinding.QuestionViewBindingSw720dpImpl;
import com.utkarshnew.android.databinding.QuizViewBindingImpl;
import com.utkarshnew.android.databinding.QuizViewBindingSw600dpImpl;
import com.utkarshnew.android.databinding.QuizViewBindingSw720dpImpl;
import com.utkarshnew.android.databinding.VideoPostBindingImpl;
import com.utkarshnew.android.databinding.VideoPostBindingSw600dpImpl;
import com.utkarshnew.android.databinding.VideoPostBindingSw720dpImpl;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_ACTIVITYFEEDDETAILS = 1;

  private static final int LAYOUT_ACTIVITYFEEDS = 2;

  private static final int LAYOUT_ACTIVITYPURCHASEHISTORYDETAIL = 3;

  private static final int LAYOUT_ADDRESSADAPTER = 4;

  private static final int LAYOUT_ARTICLEVM = 5;

  private static final int LAYOUT_AUDIOPOST = 6;

  private static final int LAYOUT_BANNERADAPTERIMAGEVIEW = 7;

  private static final int LAYOUT_BANNERVIEW = 8;

  private static final int LAYOUT_COMMENTADAPTER = 9;

  private static final int LAYOUT_FEEDLIVECLASSADAPTER = 10;

  private static final int LAYOUT_FEEDLIVETESTADAPTER = 11;

  private static final int LAYOUT_LINKVIEW = 12;

  private static final int LAYOUT_LIVECLASSVM = 13;

  private static final int LAYOUT_LIVETESTVM = 14;

  private static final int LAYOUT_NEWCOURSEVM = 15;

  private static final int LAYOUT_NEWTESTRESULTADAPTER = 16;

  private static final int LAYOUT_NEWTESTRESULTVM = 17;

  private static final int LAYOUT_NOTESTYPEADAPTER = 18;

  private static final int LAYOUT_POSTIMAGE = 19;

  private static final int LAYOUT_QUESTIONVIEW = 20;

  private static final int LAYOUT_QUIZVIEW = 21;

  private static final int LAYOUT_VIDEOPOST = 22;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(22);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.utkarshnew.android.R.layout.activity_feed_details, LAYOUT_ACTIVITYFEEDDETAILS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.utkarshnew.android.R.layout.activity_feeds, LAYOUT_ACTIVITYFEEDS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.utkarshnew.android.R.layout.activity_purchase_history_detail, LAYOUT_ACTIVITYPURCHASEHISTORYDETAIL);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.utkarshnew.android.R.layout.address_adapter, LAYOUT_ADDRESSADAPTER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.utkarshnew.android.R.layout.article_vm, LAYOUT_ARTICLEVM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.utkarshnew.android.R.layout.audio_post, LAYOUT_AUDIOPOST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.utkarshnew.android.R.layout.banner_adapter_imageview, LAYOUT_BANNERADAPTERIMAGEVIEW);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.utkarshnew.android.R.layout.banner_view, LAYOUT_BANNERVIEW);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.utkarshnew.android.R.layout.comment_adapter, LAYOUT_COMMENTADAPTER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.utkarshnew.android.R.layout.feed_live_class_adapter, LAYOUT_FEEDLIVECLASSADAPTER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.utkarshnew.android.R.layout.feed_live_test_adapter, LAYOUT_FEEDLIVETESTADAPTER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.utkarshnew.android.R.layout.link_view, LAYOUT_LINKVIEW);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.utkarshnew.android.R.layout.live_class_vm, LAYOUT_LIVECLASSVM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.utkarshnew.android.R.layout.live_test_vm, LAYOUT_LIVETESTVM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.utkarshnew.android.R.layout.new_course_vm, LAYOUT_NEWCOURSEVM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.utkarshnew.android.R.layout.new_test_result_adapter, LAYOUT_NEWTESTRESULTADAPTER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.utkarshnew.android.R.layout.new_testresult_vm, LAYOUT_NEWTESTRESULTVM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.utkarshnew.android.R.layout.notes_type_adapter, LAYOUT_NOTESTYPEADAPTER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.utkarshnew.android.R.layout.post_image, LAYOUT_POSTIMAGE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.utkarshnew.android.R.layout.question_view, LAYOUT_QUESTIONVIEW);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.utkarshnew.android.R.layout.quiz_view, LAYOUT_QUIZVIEW);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.utkarshnew.android.R.layout.video_post, LAYOUT_VIDEOPOST);
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
        case  LAYOUT_ACTIVITYFEEDDETAILS: {
          if ("layout-sw600dp/activity_feed_details_0".equals(tag)) {
            return new ActivityFeedDetailsBindingSw600dpImpl(component, view);
          }
          if ("layout-sw720dp/activity_feed_details_0".equals(tag)) {
            return new ActivityFeedDetailsBindingSw720dpImpl(component, view);
          }
          if ("layout/activity_feed_details_0".equals(tag)) {
            return new ActivityFeedDetailsBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_feed_details is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYFEEDS: {
          if ("layout-sw600dp/activity_feeds_0".equals(tag)) {
            return new ActivityFeedsBindingSw600dpImpl(component, view);
          }
          if ("layout/activity_feeds_0".equals(tag)) {
            return new ActivityFeedsBindingImpl(component, view);
          }
          if ("layout-sw720dp/activity_feeds_0".equals(tag)) {
            return new ActivityFeedsBindingSw720dpImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_feeds is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYPURCHASEHISTORYDETAIL: {
          if ("layout-sw720dp/activity_purchase_history_detail_0".equals(tag)) {
            return new ActivityPurchaseHistoryDetailBindingSw720dpImpl(component, view);
          }
          if ("layout/activity_purchase_history_detail_0".equals(tag)) {
            return new ActivityPurchaseHistoryDetailBindingImpl(component, view);
          }
          if ("layout-sw600dp/activity_purchase_history_detail_0".equals(tag)) {
            return new ActivityPurchaseHistoryDetailBindingSw600dpImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_purchase_history_detail is invalid. Received: " + tag);
        }
        case  LAYOUT_ADDRESSADAPTER: {
          if ("layout-sw600dp/address_adapter_0".equals(tag)) {
            return new AddressAdapterBindingSw600dpImpl(component, view);
          }
          if ("layout-sw720dp/address_adapter_0".equals(tag)) {
            return new AddressAdapterBindingSw720dpImpl(component, view);
          }
          if ("layout/address_adapter_0".equals(tag)) {
            return new AddressAdapterBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for address_adapter is invalid. Received: " + tag);
        }
        case  LAYOUT_ARTICLEVM: {
          if ("layout-sw600dp/article_vm_0".equals(tag)) {
            return new ArticleVmBindingSw600dpImpl(component, view);
          }
          if ("layout-sw720dp/article_vm_0".equals(tag)) {
            return new ArticleVmBindingSw720dpImpl(component, view);
          }
          if ("layout/article_vm_0".equals(tag)) {
            return new ArticleVmBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for article_vm is invalid. Received: " + tag);
        }
        case  LAYOUT_AUDIOPOST: {
          if ("layout-sw720dp/audio_post_0".equals(tag)) {
            return new AudioPostBindingSw720dpImpl(component, view);
          }
          if ("layout/audio_post_0".equals(tag)) {
            return new AudioPostBindingImpl(component, view);
          }
          if ("layout-sw600dp/audio_post_0".equals(tag)) {
            return new AudioPostBindingSw600dpImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for audio_post is invalid. Received: " + tag);
        }
        case  LAYOUT_BANNERADAPTERIMAGEVIEW: {
          if ("layout-sw600dp/banner_adapter_imageview_0".equals(tag)) {
            return new BannerAdapterImageviewBindingSw600dpImpl(component, view);
          }
          if ("layout-sw720dp/banner_adapter_imageview_0".equals(tag)) {
            return new BannerAdapterImageviewBindingSw720dpImpl(component, view);
          }
          if ("layout/banner_adapter_imageview_0".equals(tag)) {
            return new BannerAdapterImageviewBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for banner_adapter_imageview is invalid. Received: " + tag);
        }
        case  LAYOUT_BANNERVIEW: {
          if ("layout-sw600dp/banner_view_0".equals(tag)) {
            return new BannerViewBindingSw600dpImpl(component, view);
          }
          if ("layout/banner_view_0".equals(tag)) {
            return new BannerViewBindingImpl(component, view);
          }
          if ("layout-sw720dp/banner_view_0".equals(tag)) {
            return new BannerViewBindingSw720dpImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for banner_view is invalid. Received: " + tag);
        }
        case  LAYOUT_COMMENTADAPTER: {
          if ("layout-sw720dp/comment_adapter_0".equals(tag)) {
            return new CommentAdapterBindingSw720dpImpl(component, view);
          }
          if ("layout-sw600dp/comment_adapter_0".equals(tag)) {
            return new CommentAdapterBindingSw600dpImpl(component, view);
          }
          if ("layout/comment_adapter_0".equals(tag)) {
            return new CommentAdapterBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for comment_adapter is invalid. Received: " + tag);
        }
        case  LAYOUT_FEEDLIVECLASSADAPTER: {
          if ("layout/feed_live_class_adapter_0".equals(tag)) {
            return new FeedLiveClassAdapterBindingImpl(component, view);
          }
          if ("layout-sw720dp/feed_live_class_adapter_0".equals(tag)) {
            return new FeedLiveClassAdapterBindingSw720dpImpl(component, view);
          }
          if ("layout-sw600dp/feed_live_class_adapter_0".equals(tag)) {
            return new FeedLiveClassAdapterBindingSw600dpImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for feed_live_class_adapter is invalid. Received: " + tag);
        }
        case  LAYOUT_FEEDLIVETESTADAPTER: {
          if ("layout-sw720dp/feed_live_test_adapter_0".equals(tag)) {
            return new FeedLiveTestAdapterBindingSw720dpImpl(component, view);
          }
          if ("layout-sw600dp/feed_live_test_adapter_0".equals(tag)) {
            return new FeedLiveTestAdapterBindingSw600dpImpl(component, view);
          }
          if ("layout/feed_live_test_adapter_0".equals(tag)) {
            return new FeedLiveTestAdapterBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for feed_live_test_adapter is invalid. Received: " + tag);
        }
        case  LAYOUT_LINKVIEW: {
          if ("layout-sw600dp/link_view_0".equals(tag)) {
            return new LinkViewBindingSw600dpImpl(component, view);
          }
          if ("layout-sw720dp/link_view_0".equals(tag)) {
            return new LinkViewBindingSw720dpImpl(component, view);
          }
          if ("layout/link_view_0".equals(tag)) {
            return new LinkViewBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for link_view is invalid. Received: " + tag);
        }
        case  LAYOUT_LIVECLASSVM: {
          if ("layout-sw600dp/live_class_vm_0".equals(tag)) {
            return new LiveClassVmBindingSw600dpImpl(component, view);
          }
          if ("layout-sw720dp/live_class_vm_0".equals(tag)) {
            return new LiveClassVmBindingSw720dpImpl(component, view);
          }
          if ("layout/live_class_vm_0".equals(tag)) {
            return new LiveClassVmBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for live_class_vm is invalid. Received: " + tag);
        }
        case  LAYOUT_LIVETESTVM: {
          if ("layout-sw720dp/live_test_vm_0".equals(tag)) {
            return new LiveTestVmBindingSw720dpImpl(component, view);
          }
          if ("layout/live_test_vm_0".equals(tag)) {
            return new LiveTestVmBindingImpl(component, view);
          }
          if ("layout-sw600dp/live_test_vm_0".equals(tag)) {
            return new LiveTestVmBindingSw600dpImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for live_test_vm is invalid. Received: " + tag);
        }
        case  LAYOUT_NEWCOURSEVM: {
          if ("layout/new_course_vm_0".equals(tag)) {
            return new NewCourseVmBindingImpl(component, view);
          }
          if ("layout-sw720dp/new_course_vm_0".equals(tag)) {
            return new NewCourseVmBindingSw720dpImpl(component, view);
          }
          if ("layout-sw600dp/new_course_vm_0".equals(tag)) {
            return new NewCourseVmBindingSw600dpImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for new_course_vm is invalid. Received: " + tag);
        }
        case  LAYOUT_NEWTESTRESULTADAPTER: {
          if ("layout-sw600dp/new_test_result_adapter_0".equals(tag)) {
            return new NewTestResultAdapterBindingSw600dpImpl(component, view);
          }
          if ("layout/new_test_result_adapter_0".equals(tag)) {
            return new NewTestResultAdapterBindingImpl(component, view);
          }
          if ("layout-sw720dp/new_test_result_adapter_0".equals(tag)) {
            return new NewTestResultAdapterBindingSw720dpImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for new_test_result_adapter is invalid. Received: " + tag);
        }
        case  LAYOUT_NEWTESTRESULTVM: {
          if ("layout/new_testresult_vm_0".equals(tag)) {
            return new NewTestresultVmBindingImpl(component, view);
          }
          if ("layout-sw600dp/new_testresult_vm_0".equals(tag)) {
            return new NewTestresultVmBindingSw600dpImpl(component, view);
          }
          if ("layout-sw720dp/new_testresult_vm_0".equals(tag)) {
            return new NewTestresultVmBindingSw720dpImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for new_testresult_vm is invalid. Received: " + tag);
        }
        case  LAYOUT_NOTESTYPEADAPTER: {
          if ("layout-sw600dp/notes_type_adapter_0".equals(tag)) {
            return new NotesTypeAdapterBindingSw600dpImpl(component, view);
          }
          if ("layout/notes_type_adapter_0".equals(tag)) {
            return new NotesTypeAdapterBindingImpl(component, view);
          }
          if ("layout-sw720dp/notes_type_adapter_0".equals(tag)) {
            return new NotesTypeAdapterBindingSw720dpImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for notes_type_adapter is invalid. Received: " + tag);
        }
        case  LAYOUT_POSTIMAGE: {
          if ("layout/post_image_0".equals(tag)) {
            return new PostImageBindingImpl(component, view);
          }
          if ("layout-sw720dp/post_image_0".equals(tag)) {
            return new PostImageBindingSw720dpImpl(component, view);
          }
          if ("layout-sw600dp/post_image_0".equals(tag)) {
            return new PostImageBindingSw600dpImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for post_image is invalid. Received: " + tag);
        }
        case  LAYOUT_QUESTIONVIEW: {
          if ("layout-sw600dp/question_view_0".equals(tag)) {
            return new QuestionViewBindingSw600dpImpl(component, view);
          }
          if ("layout/question_view_0".equals(tag)) {
            return new QuestionViewBindingImpl(component, view);
          }
          if ("layout-sw720dp/question_view_0".equals(tag)) {
            return new QuestionViewBindingSw720dpImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for question_view is invalid. Received: " + tag);
        }
        case  LAYOUT_QUIZVIEW: {
          if ("layout/quiz_view_0".equals(tag)) {
            return new QuizViewBindingImpl(component, view);
          }
          if ("layout-sw600dp/quiz_view_0".equals(tag)) {
            return new QuizViewBindingSw600dpImpl(component, view);
          }
          if ("layout-sw720dp/quiz_view_0".equals(tag)) {
            return new QuizViewBindingSw720dpImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for quiz_view is invalid. Received: " + tag);
        }
        case  LAYOUT_VIDEOPOST: {
          if ("layout-sw600dp/video_post_0".equals(tag)) {
            return new VideoPostBindingSw600dpImpl(component, view);
          }
          if ("layout-sw720dp/video_post_0".equals(tag)) {
            return new VideoPostBindingSw720dpImpl(component, view);
          }
          if ("layout/video_post_0".equals(tag)) {
            return new VideoPostBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for video_post is invalid. Received: " + tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(1);
    result.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(26);

    static {
      sKeys.put(0, "_all");
      sKeys.put(1, "addressadapter");
      sKeys.put(2, "articlebind");
      sKeys.put(3, "audiobind");
      sKeys.put(4, "bannerdata");
      sKeys.put(5, "bannerviewadapter");
      sKeys.put(6, "commentdata");
      sKeys.put(7, "coursedata");
      sKeys.put(8, "feedadapter");
      sKeys.put(9, "feedbind");
      sKeys.put(10, "feeddatatable");
      sKeys.put(11, "feeddetailVm");
      sKeys.put(12, "imagebind");
      sKeys.put(13, "linkbind");
      sKeys.put(14, "liveclass");
      sKeys.put(15, "liveclassdata");
      sKeys.put(16, "livetest");
      sKeys.put(17, "livetestdata");
      sKeys.put(18, "livetestresult");
      sKeys.put(19, "notestype");
      sKeys.put(20, "optionadapter");
      sKeys.put(21, "optionwebadapter");
      sKeys.put(22, "pruchasehistory");
      sKeys.put(23, "questionbind");
      sKeys.put(24, "quizbind");
      sKeys.put(25, "videopostbind");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(66);

    static {
      sKeys.put("layout-sw600dp/activity_feed_details_0", com.utkarshnew.android.R.layout.activity_feed_details);
      sKeys.put("layout-sw720dp/activity_feed_details_0", com.utkarshnew.android.R.layout.activity_feed_details);
      sKeys.put("layout/activity_feed_details_0", com.utkarshnew.android.R.layout.activity_feed_details);
      sKeys.put("layout-sw600dp/activity_feeds_0", com.utkarshnew.android.R.layout.activity_feeds);
      sKeys.put("layout/activity_feeds_0", com.utkarshnew.android.R.layout.activity_feeds);
      sKeys.put("layout-sw720dp/activity_feeds_0", com.utkarshnew.android.R.layout.activity_feeds);
      sKeys.put("layout-sw720dp/activity_purchase_history_detail_0", com.utkarshnew.android.R.layout.activity_purchase_history_detail);
      sKeys.put("layout/activity_purchase_history_detail_0", com.utkarshnew.android.R.layout.activity_purchase_history_detail);
      sKeys.put("layout-sw600dp/activity_purchase_history_detail_0", com.utkarshnew.android.R.layout.activity_purchase_history_detail);
      sKeys.put("layout-sw600dp/address_adapter_0", com.utkarshnew.android.R.layout.address_adapter);
      sKeys.put("layout-sw720dp/address_adapter_0", com.utkarshnew.android.R.layout.address_adapter);
      sKeys.put("layout/address_adapter_0", com.utkarshnew.android.R.layout.address_adapter);
      sKeys.put("layout-sw600dp/article_vm_0", com.utkarshnew.android.R.layout.article_vm);
      sKeys.put("layout-sw720dp/article_vm_0", com.utkarshnew.android.R.layout.article_vm);
      sKeys.put("layout/article_vm_0", com.utkarshnew.android.R.layout.article_vm);
      sKeys.put("layout-sw720dp/audio_post_0", com.utkarshnew.android.R.layout.audio_post);
      sKeys.put("layout/audio_post_0", com.utkarshnew.android.R.layout.audio_post);
      sKeys.put("layout-sw600dp/audio_post_0", com.utkarshnew.android.R.layout.audio_post);
      sKeys.put("layout-sw600dp/banner_adapter_imageview_0", com.utkarshnew.android.R.layout.banner_adapter_imageview);
      sKeys.put("layout-sw720dp/banner_adapter_imageview_0", com.utkarshnew.android.R.layout.banner_adapter_imageview);
      sKeys.put("layout/banner_adapter_imageview_0", com.utkarshnew.android.R.layout.banner_adapter_imageview);
      sKeys.put("layout-sw600dp/banner_view_0", com.utkarshnew.android.R.layout.banner_view);
      sKeys.put("layout/banner_view_0", com.utkarshnew.android.R.layout.banner_view);
      sKeys.put("layout-sw720dp/banner_view_0", com.utkarshnew.android.R.layout.banner_view);
      sKeys.put("layout-sw720dp/comment_adapter_0", com.utkarshnew.android.R.layout.comment_adapter);
      sKeys.put("layout-sw600dp/comment_adapter_0", com.utkarshnew.android.R.layout.comment_adapter);
      sKeys.put("layout/comment_adapter_0", com.utkarshnew.android.R.layout.comment_adapter);
      sKeys.put("layout/feed_live_class_adapter_0", com.utkarshnew.android.R.layout.feed_live_class_adapter);
      sKeys.put("layout-sw720dp/feed_live_class_adapter_0", com.utkarshnew.android.R.layout.feed_live_class_adapter);
      sKeys.put("layout-sw600dp/feed_live_class_adapter_0", com.utkarshnew.android.R.layout.feed_live_class_adapter);
      sKeys.put("layout-sw720dp/feed_live_test_adapter_0", com.utkarshnew.android.R.layout.feed_live_test_adapter);
      sKeys.put("layout-sw600dp/feed_live_test_adapter_0", com.utkarshnew.android.R.layout.feed_live_test_adapter);
      sKeys.put("layout/feed_live_test_adapter_0", com.utkarshnew.android.R.layout.feed_live_test_adapter);
      sKeys.put("layout-sw600dp/link_view_0", com.utkarshnew.android.R.layout.link_view);
      sKeys.put("layout-sw720dp/link_view_0", com.utkarshnew.android.R.layout.link_view);
      sKeys.put("layout/link_view_0", com.utkarshnew.android.R.layout.link_view);
      sKeys.put("layout-sw600dp/live_class_vm_0", com.utkarshnew.android.R.layout.live_class_vm);
      sKeys.put("layout-sw720dp/live_class_vm_0", com.utkarshnew.android.R.layout.live_class_vm);
      sKeys.put("layout/live_class_vm_0", com.utkarshnew.android.R.layout.live_class_vm);
      sKeys.put("layout-sw720dp/live_test_vm_0", com.utkarshnew.android.R.layout.live_test_vm);
      sKeys.put("layout/live_test_vm_0", com.utkarshnew.android.R.layout.live_test_vm);
      sKeys.put("layout-sw600dp/live_test_vm_0", com.utkarshnew.android.R.layout.live_test_vm);
      sKeys.put("layout/new_course_vm_0", com.utkarshnew.android.R.layout.new_course_vm);
      sKeys.put("layout-sw720dp/new_course_vm_0", com.utkarshnew.android.R.layout.new_course_vm);
      sKeys.put("layout-sw600dp/new_course_vm_0", com.utkarshnew.android.R.layout.new_course_vm);
      sKeys.put("layout-sw600dp/new_test_result_adapter_0", com.utkarshnew.android.R.layout.new_test_result_adapter);
      sKeys.put("layout/new_test_result_adapter_0", com.utkarshnew.android.R.layout.new_test_result_adapter);
      sKeys.put("layout-sw720dp/new_test_result_adapter_0", com.utkarshnew.android.R.layout.new_test_result_adapter);
      sKeys.put("layout/new_testresult_vm_0", com.utkarshnew.android.R.layout.new_testresult_vm);
      sKeys.put("layout-sw600dp/new_testresult_vm_0", com.utkarshnew.android.R.layout.new_testresult_vm);
      sKeys.put("layout-sw720dp/new_testresult_vm_0", com.utkarshnew.android.R.layout.new_testresult_vm);
      sKeys.put("layout-sw600dp/notes_type_adapter_0", com.utkarshnew.android.R.layout.notes_type_adapter);
      sKeys.put("layout/notes_type_adapter_0", com.utkarshnew.android.R.layout.notes_type_adapter);
      sKeys.put("layout-sw720dp/notes_type_adapter_0", com.utkarshnew.android.R.layout.notes_type_adapter);
      sKeys.put("layout/post_image_0", com.utkarshnew.android.R.layout.post_image);
      sKeys.put("layout-sw720dp/post_image_0", com.utkarshnew.android.R.layout.post_image);
      sKeys.put("layout-sw600dp/post_image_0", com.utkarshnew.android.R.layout.post_image);
      sKeys.put("layout-sw600dp/question_view_0", com.utkarshnew.android.R.layout.question_view);
      sKeys.put("layout/question_view_0", com.utkarshnew.android.R.layout.question_view);
      sKeys.put("layout-sw720dp/question_view_0", com.utkarshnew.android.R.layout.question_view);
      sKeys.put("layout/quiz_view_0", com.utkarshnew.android.R.layout.quiz_view);
      sKeys.put("layout-sw600dp/quiz_view_0", com.utkarshnew.android.R.layout.quiz_view);
      sKeys.put("layout-sw720dp/quiz_view_0", com.utkarshnew.android.R.layout.quiz_view);
      sKeys.put("layout-sw600dp/video_post_0", com.utkarshnew.android.R.layout.video_post);
      sKeys.put("layout-sw720dp/video_post_0", com.utkarshnew.android.R.layout.video_post);
      sKeys.put("layout/video_post_0", com.utkarshnew.android.R.layout.video_post);
    }
  }
}
