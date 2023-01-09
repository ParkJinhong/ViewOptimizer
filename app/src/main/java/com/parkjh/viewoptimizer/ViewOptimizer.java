package com.parkjh.viewoptimizer;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Dimension;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class ViewOptimizer {
    private static Context mContext;

    private float mXRatio;
    private float mDeviceWidthInDesign;

    public ViewOptimizer(Context context, float deviceWidthInDesign) {
        mContext = context;
        mDeviceWidthInDesign = deviceWidthInDesign;
        Point devicesize = getRealScreenSize(mContext);
        mXRatio = (float) devicesize.x / mDeviceWidthInDesign;
        init();
    }

    private static void init() {
    }

    public void optimize (List<View> v) {
        for(View each : v) {
            String viewClassSimpleName = each.getClass().getSimpleName().trim().toLowerCase();
            if (viewClassSimpleName.contains("imageview")) {
                optimize((ImageView) each);
            } else if (viewClassSimpleName.contains("textview")) {
                optimize((TextView) each);
            } else {
                optimize((View) each);
            }
        }
    }

    public void optimize (ImageView v) {
        ViewGroup.MarginLayoutParams curViewMarginParams = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
        ViewGroup.LayoutParams curViewParams =  v.getLayoutParams();
        int curViewWidth = (int) Math.floor(curViewParams.width * mXRatio);
        int curViewHeight = (int) Math.floor(curViewParams.height * mXRatio);
        curViewParams.width = curViewWidth;
        curViewParams.height = curViewHeight;
        v.setX((int)Math.floor(curViewMarginParams.leftMargin * mXRatio - curViewMarginParams.leftMargin));
        v.setY((int)Math.floor(curViewMarginParams.topMargin * mXRatio - curViewMarginParams.topMargin));
        v.setLayoutParams(curViewParams);
    }

    public void optimize (TextView v) {
        MarginLayoutParams curViewMarginParams = (MarginLayoutParams) v.getLayoutParams();
        LayoutParams curViewParams =  v.getLayoutParams();
        int curViewWidth = (int) Math.floor(curViewParams.width * mXRatio);
        int curViewHeight = (int) Math.floor(curViewParams.height * mXRatio);
        curViewParams.width = curViewWidth;
        curViewParams.height = curViewHeight;
        if (VERSION.SDK_INT >= VERSION_CODES.R) {
            if(v.getTextSizeUnit() == TypedValue.COMPLEX_UNIT_PX) {
                v.setTextSize(TypedValue.COMPLEX_UNIT_PX, v.getTextSize() * mXRatio);
            }
        } else {
            v.setTextSize(TypedValue.COMPLEX_UNIT_PX, v.getTextSize() * mXRatio);
        }
        v.setX((int)Math.floor(curViewMarginParams.leftMargin * mXRatio - curViewMarginParams.leftMargin));
        v.setY((int)Math.floor(curViewMarginParams.topMargin * mXRatio - curViewMarginParams.topMargin));
        v.setLayoutParams(curViewParams);
    }

    public void optimize (View v) {
        ViewGroup.MarginLayoutParams curViewMarginParams = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
        ViewGroup.LayoutParams curViewParams =  v.getLayoutParams();
        int curViewWidth = (int) Math.floor(curViewParams.width * mXRatio);
        int curViewHeight = (int) Math.floor(curViewParams.height * mXRatio);
        curViewParams.width = curViewWidth;
        curViewParams.height = curViewHeight;
        v.setX((int)Math.floor(curViewMarginParams.leftMargin * mXRatio - curViewMarginParams.leftMargin));
        v.setY((int)Math.floor(curViewMarginParams.topMargin * mXRatio - curViewMarginParams.topMargin));
        v.setLayoutParams(curViewParams);
    }

    private static Point getRealScreenSize(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            display.getRealSize(size);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            try {
                size.x = (Integer) Display.class.getMethod("getRawWidth").invoke(display);
                size.y = (Integer) Display.class.getMethod("getRawHeight").invoke(display);
            } catch (IllegalAccessException e) {} catch (InvocationTargetException e) {} catch (NoSuchMethodException e) {}
        }

        return size;
    }
}
