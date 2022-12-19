package com.utkarshnew.android.TypeConverter;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.utkarshnew.android.table.Subjectfilter;


import java.lang.reflect.Type;
import java.util.List;

public class FilterConverter {

    @TypeConverter
    public static List<Subjectfilter> fromString(String value) {
        Type listType = new TypeToken<List<Subjectfilter>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(List<Subjectfilter> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
