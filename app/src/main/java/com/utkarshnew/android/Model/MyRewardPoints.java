package com.utkarshnew.android.Model;

import java.io.Serializable;

public class MyRewardPoints implements Serializable {
    private String id;

    private String reward_points;

    private String total_earned_wallet;

    private String current_wallet;

    private String conversion_rate;

    private String total_earned_point;

    private String refer_code;

    private String refer_points;

    private String minimum_coin_to_redeem;

    public String getRefer_points() {
        return refer_points;
    }

    public void setRefer_points(String refer_points) {
        this.refer_points = refer_points;
    }

    public String getTotal_earned_wallet() {
        return total_earned_wallet;
    }

    public void setTotal_earned_wallet(String total_earned_wallet) {
        this.total_earned_wallet = total_earned_wallet;
    }

    public String getCurrent_wallet() {
        return current_wallet;
    }

    public void setCurrent_wallet(String current_wallet) {
        this.current_wallet = current_wallet;
    }

    public String getConversion_rate() {
        return conversion_rate;
    }

    public void setConversion_rate(String conversion_rate) {
        this.conversion_rate = conversion_rate;
    }

    public String getTotal_earned_point() {
        return total_earned_point;
    }

    public void setTotal_earned_point(String total_earned_point) {
        this.total_earned_point = total_earned_point;
    }

    public String getMinimum_coin_to_redeem() {
        return minimum_coin_to_redeem;
    }

    public void setMinimum_coin_to_redeem(String minimum_coin_to_redeem) {
        this.minimum_coin_to_redeem = minimum_coin_to_redeem;
    }

    public String getRefer_code() {
        return refer_code;
    }

    public void setRefer_code(String refer_code) {
        this.refer_code = refer_code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReward_points() {
        return reward_points;
    }

    public void setReward_points(String reward_points) {
        this.reward_points = reward_points;
    }

    @Override
    public String toString() {
        return "ClassPojo [id = " + id + ", reward_points = " + reward_points + "]";
    }
}