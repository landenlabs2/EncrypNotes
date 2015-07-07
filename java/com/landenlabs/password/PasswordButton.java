package com.landenlabs.password;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import com.landenlabs.encrypnotes.R;

/**
 * Wrapper class on Button to make it easier to style password buttons
 * and detect password objects in PasswordGrid layout.
 * 
 * @author Dennis Lang v2 9/1/2014
 *         <p>
 * @author ahmed v1 7/2/2014 <br>
 *         Original version: {@link https ://github.com/asghonim/simple_android_lock_pattern}
 *
 */
public class PasswordButton extends Button {

    public PasswordButton(Context context) {
        super(context);
        init(context, null);
    }

    public PasswordButton(Context context, AttributeSet attrs) {
		super(context, attrs, R.attr.passwordButtonStyle);
		init(context, attrs);
    }

    public PasswordButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, ((defStyle == 0) ? R.attr.passwordButtonStyle : defStyle) );
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
    	if (!isInEditMode()) {
	        if (getTag() == null) {
	            setTag(getText());
	        }
    	}   
    }
}
