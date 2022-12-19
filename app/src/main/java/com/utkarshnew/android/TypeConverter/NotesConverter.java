package com.utkarshnew.android.TypeConverter;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.utkarshnew.android.courses.modal.NotesType;

import java.lang.reflect.Type;
import java.util.List;

public class NotesConverter {
    @TypeConverter
    public static List<NotesType> fromString(String value) {
        Type listType = new TypeToken<List<NotesType>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(List<NotesType> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}