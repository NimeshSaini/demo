package com.utkarshnew.android.TypeConverter;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.utkarshnew.android.Model.ExtendValidity;

import java.lang.reflect.Type;
import java.util.List;

public class Converters {
    @TypeConverter
    public static List<ExtendValidity> fromString(String value) {
        Type listType = new TypeToken<List<ExtendValidity>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(List<ExtendValidity> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}