package com.utkarshnew.android.Utils.StickyView.provider;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.StyleableRes;

import com.utkarshnew.android.Utils.StickyView.provider.interfaces.IResourceProvider;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class ResourceProvider implements IResourceProvider {

    private final TypedArray mTypeArray;

    public ResourceProvider(Context context, AttributeSet attrs, @StyleableRes int[] styleRes) {
        mTypeArray = context.obtainStyledAttributes(attrs, styleRes);
    }

    @Override
    public int getResourceId(@StyleableRes int styleResId) {
        try {
            return mTypeArray.getResourceId(styleResId, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return styleResId;
    }

    @Override
    public void recycle() {
        try {
            mTypeArray.recycle();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
