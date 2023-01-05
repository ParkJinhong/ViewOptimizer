package com.parkjh.viewoptimizer;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import java.lang.reflect.InvocationTargetException;

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

    public void optimize (View v) {
        String viewClassSimpleName = v.getClass().getSimpleName().trim().toLowerCase();
        if (viewClassSimpleName.contains("imageview")) {
            imageViewOptimize(v);
        } else if (viewClassSimpleName.contains("textview")) {
            textViewOptimize(v);
        } else {
            imageViewOptimize(v);
        }
    }

    private void imageViewOptimize (View v) {
        ViewGroup.MarginLayoutParams curViewMarginParams = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
        ViewGroup.LayoutParams curViewParams =  v.getLayoutParams();
        final int curViewWidth = (int) Math.floor(curViewParams.width * mXRatio);
        final int curViewHeight = (int) Math.floor(curViewParams.height * mXRatio);
        curViewParams.width = curViewWidth;
        curViewParams.height = curViewHeight;
        v.setX((int)Math.floor(curViewMarginParams.leftMargin * mXRatio - curViewMarginParams.leftMargin));
        v.setY((int)Math.floor(curViewMarginParams.topMargin * mXRatio - curViewMarginParams.topMargin));
        v.setLayoutParams(curViewParams);
    }

    private void textViewOptimize (View v) {
        ViewGroup.MarginLayoutParams curViewMarginParams = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
        ViewGroup.LayoutParams curViewParams =  v.getLayoutParams();
        final int curViewWidth = (int) Math.floor(curViewParams.width * mXRatio);
        final int curViewHeight = (int) Math.floor(curViewParams.height * mXRatio);
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
