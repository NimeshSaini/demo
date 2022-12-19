package com.utkarshnew.android.home.interfaces;

import com.utkarshnew.android.Model.Courses.Cards;

import java.util.ArrayList;

public interface onButtonClicked {
    void onTitleClicked(Cards cards, ArrayList<Cards> tiles, String contentType, int tilePos);
}
