package com.utkarshnew.android.Model.PoJoModel;

import java.io.Serializable;

public class DrishtiDetailOnePOJO implements Serializable {

    String dristi_user_id;
    String id_card_front;
    String id_card_back;
    String is_verified;

    public DrishtiDetailOnePOJO(String dristi_user_id, String id_card_front, String id_card_back, String is_verified) {
        this.dristi_user_id = dristi_user_id;
        this.id_card_front = id_card_front;
        this.id_card_back = id_card_back;
        this.is_verified = is_verified;
    }

    public String getDristi_user_id() {
        return dristi_user_id;
    }

    public void setDristi_user_id(String dristi_user_id) {
        this.dristi_user_id = dristi_user_id;
    }

    public String getId_card_front() {
        return id_card_front;
    }

    public void setId_card_front(String id_card_front) {
        this.id_card_front = id_card_front;
    }

    public String getId_card_back() {
        return id_card_back;
    }

    public void setId_card_back(String id_card_back) {
        this.id_card_back = id_card_back;
    }

    public String getIs_verified() {
        return is_verified;
    }

    public void setIs_verified(String is_verified) {
        this.is_verified = is_verified;
    }
}
