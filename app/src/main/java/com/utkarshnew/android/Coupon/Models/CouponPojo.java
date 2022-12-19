package com.utkarshnew.android.Coupon.Models;

import java.io.Serializable;
import java.util.List;

public class CouponPojo implements Serializable {

    List<Available> available;
    List<Available> redeemed;
    List<Available> expired;

    public List<Available> getAvailable() {
        return available;
    }

    public void setAvailable(List<Available> available) {
        this.available = available;
    }

    public List<Available> getRedeemed() {
        return redeemed;
    }

    public void setRedeemed(List<Available> redeemed) {
        this.redeemed = redeemed;
    }

    public List<Available> getExpired() {
        return expired;
    }

    public void setExpired(List<Available> expired) {
        this.expired = expired;
    }
}
