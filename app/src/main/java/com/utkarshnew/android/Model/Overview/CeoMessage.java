package com.utkarshnew.android.Model.Overview;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CeoMessage implements Serializable {
    @SerializedName("ceo_message_english")
    @Expose
    private String ceoMessageEnglish;
    @SerializedName("ceo_message_hindi")
    @Expose
    private String ceoMessageHindi;

    public String getCeoMessageEnglish() {
        return ceoMessageEnglish;
    }

    public void setCeoMessageEnglish(String ceoMessageEnglish) {
        this.ceoMessageEnglish = ceoMessageEnglish;
    }

    public String getCeoMessageHindi() {
        return ceoMessageHindi;
    }

    public void setCeoMessageHindi(String ceoMessageHindi) {
        this.ceoMessageHindi = ceoMessageHindi;
    }

}
