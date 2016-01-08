package com.landenlabs.encrypnotes.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.io.IOException;
import java.io.InputStream;

public class UiUtil {

    @SuppressWarnings("unchecked")
    public static final <E extends View> E viewById(View rootView, int id) {
        return (E) rootView.findViewById(id);
    }
    @SuppressWarnings("unchecked")
    public static final <E extends View> E viewById(Dialog rootDialog, int id) {
        return (E) rootDialog.findViewById(id);
    }

    /**
     * Hide soft keyboard
     * 
     * @param view
     *     Pass in EditText view that spawned keyboard, else it will 
     *     find the view in focus.
     */
    public static void hideSoftKeyboard(View view) {
        if (view != null) {
            InputMethodManager inputMethodManager = 
                (InputMethodManager) view.getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    
    public static Intent newEmailIntent(Context context, String address, String subject, String body, String cc) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] { address });
        intent.putExtra(Intent.EXTRA_TEXT, body);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_CC, cc);
        intent.setType("message/rfc822");
        return intent;
    }
    
    public static void setText(EditText edText, String str) {
        edText.setEnabled(false);
        edText.setText(str);
        edText.setEnabled(true);
    }
    
    /**
     * Load asset file.
     * @param inFile
     *     Asset file name.
     * @return
     *     String with asset contents. 
     */
    public static String LoadData(Activity activity, String inFile) {
        String tContents = "";

        try {
            InputStream stream = activity.getAssets().open(inFile);

            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            tContents = new String(buffer);
        } catch (IOException e) {
            // Handle exceptions here
        }

        return tContents;
    }

    /**
     * @return  PackageInfo
     */
    public static PackageInfo getPackageInfo(Activity activity) {
        try {
            return activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0);
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            return null;
        }
    }

    /**
     * @return Display Size
     */
    @SuppressWarnings("unused")
    public static Point getDisplaySize(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point displaySize = new Point();
        display.getSize(displaySize);
        return displaySize;
    }
    
    
}
