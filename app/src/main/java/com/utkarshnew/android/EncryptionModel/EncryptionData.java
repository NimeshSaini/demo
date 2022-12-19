package com.utkarshnew.android.EncryptionModel;

import com.utkarshnew.android.home.model.profileSubmitData.ProfileData;

import java.util.HashMap;

public class EncryptionData {

    private LocationInfo location;

    public ProfileData getProfileData()
    {
        return profile_data;
    }

    public void setProfileData(ProfileData profileData)
    {
        this.profile_data = profileData;
    }

    private ProfileData profile_data;

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    private String quote;

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getMy_like() {
        return my_like;
    }

    public void setMy_like(String my_like) {
        this.my_like = my_like;
    }

    private String post_id;

    public String getPost_pin() {
        return post_pin;
    }

    public void setPost_pin(String post_pin) {
        this.post_pin = post_pin;
    }

    private String post_pin;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    private String comment;
    private String my_like;

    public String getRanges() {
        return ranges;
    }

    public void setRanges(String ranges) {
        this.ranges = ranges;
    }

    public String getAnnotation_id() {
        return annotation_id;
    }

    public void setAnnotation_id(String annotation_id) {
        this.annotation_id = annotation_id;
    }

    private String annotation_id;
    private String ranges;

    public LocationInfo getLocation() {
        return location;
    }

    public void setLocation(LocationInfo location) {
        this.location = location;
    }

    public String getTxn_id() {
        return txn_id;
    }

    public void setTxn_id(String txn_id) {
        this.txn_id = txn_id;
    }

    public String getExtender_id() {
        return extender_id;
    }

    public void setExtender_id(String extender_id) {
        this.extender_id = extender_id;
    }

    private String txn_id;
    private String extender_id;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    String info;
    String note_data;
    String concept_id;


    public String[] getTxn_ids() {
        return txn_ids;
    }

    public void setTxn_ids(String[] txn_ids) {
        this.txn_ids = txn_ids;
    }

    String[] txn_ids;


    String transfer_to_mobile;

    public String getTransfer_to_id() {
        return transfer_to_id;
    }

    public void setTransfer_to_id(String transfer_to_id) {
        this.transfer_to_id = transfer_to_id;
    }

    String transfer_to_id;


    public String getTransfer_to_mobile() {
        return transfer_to_mobile;
    }

    public void setTransfer_to_mobile(String transfer_to_mobile) {
        this.transfer_to_mobile = transfer_to_mobile;
    }


    public String getComment_type() {
        return comment_type;
    }

    public void setComment_type(String comment_type) {
        this.comment_type = comment_type;
    }

    String comment_type;

    public String getComment_msg() {
        return comment_msg;
    }

    public void setComment_msg(String comment_msg) {
        this.comment_msg = comment_msg;
    }

    public String getQuery_id() {
        return query_id;
    }

    public void setQuery_id(String query_id) {
        this.query_id = query_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    String comment_msg;
    String query_id;
    String text;

    public String getNote_data() {
        return note_data;
    }

    public void setNote_data(String note_data) {
        this.note_data = note_data;
    }

    public String getConcept_id() {
        return concept_id;
    }

    public void setConcept_id(String concept_id) {
        this.concept_id = concept_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    String time;

    public String getCoupon_code() {
        return coupon_code;
    }

    public void setCoupon_code(String coupon_code) {
        this.coupon_code = coupon_code;
    }

    private String coupon_code;
    // bookmark

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    private String video_id;

    public String getRevision_id() {
        return revision_id;
    }

    public void setRevision_id(String revision_id) {
        this.revision_id = revision_id;
    }

    private String revision_id;

    public String getFors() {
        return fors;
    }

    public void setFors(String fors) {
        this.fors = fors;
    }

    String fors;

    public String getServercourseid() {
        return servercourseid;
    }

    public void setServercourseid(String servercourseid) {
        this.servercourseid = servercourseid;
    }

    String servercourseid;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    String token;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    String answer;

    public String getPoll_id() {
        return poll_id;
    }

    public void setPoll_id(String poll_id) {
        this.poll_id = poll_id;
    }

    String poll_id;


    public String getChallenge_text() {
        return challenge_text;
    }

    public void setChallenge_text(String challenge_text) {
        this.challenge_text = challenge_text;
    }

    public String getChallenge_image() {
        return challenge_image;
    }

    public void setChallenge_image(String challenge_image) {
        this.challenge_image = challenge_image;
    }

    public String getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    private String challenge_text;
    private String challenge_image;
    private String question_id;
    private String description;
    private String category;
    private String file;

    public String getTest_segment_id() {
        return test_segment_id;
    }

    public void setTest_segment_id(String test_segment_id) {
        this.test_segment_id = test_segment_id;
    }

    public String getTest_id() {
        return test_id;
    }

    public void setTest_id(String test_id) {
        this.test_id = test_id;
    }

    public String getFirst_attempt() {
        return first_attempt;
    }

    public void setFirst_attempt(String first_attempt) {
        this.first_attempt = first_attempt;
    }

    private String test_id;
    private String first_attempt;

    public String getCourse_ids() {
        return course_ids;
    }

    public void setCourse_ids(String course_ids) {
        this.course_ids = course_ids;
    }

    public String getSubject_ids() {
        return subject_ids;
    }

    public void setSubject_ids(String subject_ids) {
        this.subject_ids = subject_ids;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getQue_count() {
        return que_count;
    }

    public void setQue_count(String que_count) {
        this.que_count = que_count;
    }

    private String test_segment_id;
    private String course_ids;
    private String subject_ids;
    private String limit;
    private String que_count;

    public String getPre_transaction_id() {
        return pre_transaction_id;
    }

    public void setPre_transaction_id(String pre_transaction_id) {
        this.pre_transaction_id = pre_transaction_id;
    }

    public String getTransaction_status() {
        return transaction_status;
    }

    public void setTransaction_status(String transaction_status) {
        this.transaction_status = transaction_status;
    }

    public String getPost_transaction_id() {
        return post_transaction_id;
    }

    public void setPost_transaction_id(String post_transaction_id) {
        this.post_transaction_id = post_transaction_id;
    }

    ////////complete
    private String pre_transaction_id;
    private String transaction_status;
    private String post_transaction_id;


    ///////initlipayment///


    public String getCourse_price() {
        return course_price;
    }

    public void setCourse_price(String course_price) {
        this.course_price = course_price;
    }

    public String getPay_via() {
        return pay_via;
    }

    public void setPay_via(String pay_via) {
        this.pay_via = pay_via;
    }

    public String getCoupon_applied() {
        return coupon_applied;
    }

    public void setCoupon_applied(String coupon_applied) {
        this.coupon_applied = coupon_applied;
    }

    public String getDevice_token() {
        return device_token;
    }

    public void setDevice_token(String device_token) {
        this.device_token = device_token;
    }

    private String type;
    private String course_id;

    public String getPurchase_type() {
        return purchase_type;
    }

    public void setPurchase_type(String purchase_type) {
        this.purchase_type = purchase_type;
    }

    private String purchase_type;

    public String getFile_id() {
        return file_id;
    }

    public void setFile_id(String file_id) {
        this.file_id = file_id;
    }

    private String file_id;
    private String course_price;
    private String tax;

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    private String pay_via;
    private String coupon_applied;


    // Advance filter
    private String sub_cat_id;
    private String sub_cat_ids;

    public String getSub_cat_ids() {
        return sub_cat_ids;
    }

    public void setSub_cat_ids(String sub_cat_ids) {
        this.sub_cat_ids = sub_cat_ids;
    }

    private String lang;
    private String is_paid;
    // search
    private String search;

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
    //

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getIs_paid() {
        return is_paid;
    }

    public void setIs_paid(String is_paid) {
        this.is_paid = is_paid;
    }

    public String getSub_cat_id() {
        return sub_cat_id;
    }

    public void setSub_cat_id(String sub_cat_id) {
        this.sub_cat_id = sub_cat_id;
    }

    // login module // profile
    private String device_id;

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    private String device_name;

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDevice_tokken() {
        return device_token;
    }

    public void setDevice_tokken(String device_tokken) {
        this.device_token = device_tokken;
    }

    public String getIs_social() {
        return is_social;
    }

    public void setIs_social(String is_social) {
        this.is_social = is_social;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String mobile;

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    private String pincode;



    public String getAddressTwo() {
        return addressTwo;
    }

    public void setAddressTwo(String addressTwo) {
        this.addressTwo = addressTwo;
    }

    public String getAddressOne() {
        return addressOne;
    }

    public void setAddressOne(String addressOne) {
        this.addressOne = addressOne;
    }

    private String addressTwo;
    private String delivery_price;

    public String getDelivery_price() {
        return delivery_price;
    }

    public void setDelivery_price(String delivery_price) {
        this.delivery_price = delivery_price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private String address;

    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }

    private String address_id;

    private String addressOne;
    private String device_token;
    private String is_social;
    private String password;

    //

    // otp module

    private String otp;

    private String lat;
    private String lng;
    private String ip;

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getIs_registration() {
        return is_registration;
    }

    public void setIs_registration(String is_registration) {
        this.is_registration = is_registration;
    }

    private String is_registration;

    // state

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    private String user_id;

    public String getFile_ids() {
        return file_ids;
    }

    public void setFile_ids(String file_ids) {
        this.file_ids = file_ids;
    }

    private String file_ids;

    // city

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    private String state_id;

    // registration form module

    private String city;

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    private String city_id;
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    private String state;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;


    public String getDownload_click() {
        return download_click;
    }

    public void setDownload_click(String download_click) {
        this.download_click = download_click;
    }

    private String download_click;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String email;

    // Course detail

    private String course_type;
    private String sub_cat;

    public String getMaster_cat() {
        return master_cat;
    }

    public void setMaster_cat(String master_cat) {
        this.master_cat = master_cat;
    }

    private String master_cat;


    public String getCourse_type() {
        return course_type;
    }

    public void setCourse_type(String course_type) {
        this.course_type = course_type;
    }

    public String getSub_cat() {
        return sub_cat;
    }

    public void setSub_cat(String sub_cat) {
        this.sub_cat = sub_cat;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    private String page;
    private String cat_id;

    public String getPost_type() {
        return post_type;
    }

    public void setPost_type(String post_type) {
        this.post_type = post_type;
    }

    private String post_type;

    public String getMain_cat() {
        return main_cat;
    }

    public void setMain_cat(String main_cat) {
        this.main_cat = main_cat;
    }

    private String main_cat;

    public String getIs_pin() {
        return is_pin;
    }

    public void setIs_pin(String is_pin) {
        this.is_pin = is_pin;
    }

    private String is_pin;

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    private String parent_id;
    private String tile_id;
    private String link;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String title;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTile_id() {
        return tile_id;
    }

    public void setTile_id(String tile_id) {
        this.tile_id = tile_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRevert_api() {
        return revert_api;
    }

    public void setRevert_api(String revert_api) {
        this.revert_api = revert_api;
    }


    private String revert_api;

    public String getLayer() {
        return layer;
    }

    public void setLayer(String layer) {
        this.layer = layer;
    }

    private String layer;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    private String country;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    private String subject_id;

    public String getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(String topic_id) {
        this.topic_id = topic_id;
    }

    private String topic_id;

    public HashMap<String, String> getFinalResponse() {
        return finalResponse;
    }

    public void setFinalResponse(HashMap<String, String> finalResponse) {
        this.finalResponse = finalResponse;
    }

    HashMap<String, String> finalResponse;

    // profile picture

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public String profile_picture;


    // delete index

    public String getIndex_id() {
        return index_id;
    }

    public void setIndex_id(String index_id) {
        this.index_id = index_id;
    }

    private String index_id;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    private String index;

    public String getResend() {
        return resend;
    }

    public void setResend(String resend) {
        this.resend = resend;
    }

    private String resend;


    public HashMap<String, String> getHeader() {
        return header;
    }

    public void setHeader(HashMap<String, String> header) {
        this.header = header;
    }

    public HashMap<String, String> getBody() {
        return body;
    }

    public void setBody(HashMap<String, String> body) {
        this.body = body;
    }

    HashMap<String, String> header;
    HashMap<String, String> body;

    public String getOldpassword() {
        return old_password;
    }

    public void setOldpassword(String oldpassword) {
        this.old_password = oldpassword;
    }

    private String old_password;

    //
}
