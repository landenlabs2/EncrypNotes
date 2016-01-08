/*
 *  Copyright (c) 2015 Dennis Lang (LanDen Labs) landenlabs@gmail.com
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 *  associated documentation files (the "Software"), to deal in the Software without restriction, including
 *  without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the
 *  following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all copies or substantial
 *  portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT
 *  LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN
 *  NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 *  WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 *  SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 *  @author Dennis Lang  (Dec-2015)
 *  @see http://landenlabs.com
 *
 */

package com.landenlabs.encrypnotes;

import android.app.Dialog;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.landenlabs.encrypnotes.ui.UiUtil;
import com.landenlabs.password.PasswordGrid;
import com.landenlabs.password.PasswordGrid.OnPasswordListener;


/**
 * Class to manage password UI options.
 * 
 * @author dlang_local
 * @see http://landenlabs.com
 * 
 */
class UiPasswordManager implements OnFocusChangeListener, TextView.OnEditorActionListener {
    final EncrypPrefs m_prefs;
    
    final boolean openMode;
    final EditText pwdText;
    final View pwdClear;
    final EditText pwdVerify;
    final TextView pwdVerifyLB;
    final View pwdVerifyClear;
    final PasswordGrid pwdGrid;
    final CheckBox patternCb;
    final CheckBox showCb;
    final TextView pwdHintLB;
    final EditText pwdHintValue;

    public UiPasswordManager(EncrypPrefs prefs, Dialog dlg, boolean openMode) {

        m_prefs = prefs;
        
        this.openMode = openMode;
        pwdText = UiUtil.viewById(dlg, R.id.pwd);
        pwdClear = UiUtil.viewById(dlg, R.id.pwd_clear);
        pwdVerify = UiUtil.viewById(dlg, R.id.pwd_verify);
        pwdVerifyLB = UiUtil.viewById(dlg, R.id.pwd_verifyLB);
        pwdVerifyClear = UiUtil.viewById(dlg, R.id.pwd_verify_clear);
        pwdGrid = UiUtil.viewById(dlg, R.id.pwd_grid);
        patternCb = UiUtil.viewById(dlg, R.id.pwd_patternCB);
        showCb = UiUtil.viewById(dlg, R.id.pwd_showCB);
        pwdHintLB = UiUtil.viewById(dlg, R.id.pwd_hint_label);
        pwdHintValue = UiUtil.viewById(dlg, R.id.pwd_hint_value);

        pwdGrid.clear();
        update();

        patternCb.setChecked(m_prefs.ShowPat);
        showCb.setChecked(m_prefs.ShowPwd);

        patternCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                m_prefs.ShowPat = isChecked;
                update();
            }
        });

        showCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                m_prefs.ShowPwd = isChecked;
                update();
            }
        });

        pwdClear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                pwdText.setText("");
                pwdGrid.clear();
            }
        });

        pwdVerifyClear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                pwdVerify.setText("");
            }
        });

        pwdGrid.setListener(new OnPasswordListener() {

            @Override
            public void onPasswordComplete(String s) {
            }

            @Override
            public void onPasswordChanged(String s) {
                pwdText.setText(s);
            }
        });
        
        
        pwdText.setOnFocusChangeListener(this);
        pwdText.setOnEditorActionListener(this);
        // pwdVerify.setOnFocusChangeListener(this);
        // pwdVerify.setOnEditorActionListener(this);
        // pwdGrid.setOnFocusChangeListener(this);
    }
    
    public EditText getPwdView() {
        return pwdText;
    }

    /***
     * Return true if valid password entered.
     *    Return true if Show Pattern or Password enabled
     *    else return true if both passwords entered match.
     *
     * @return true if valid.
     */
    public boolean isValid() {
        return (m_prefs.ShowPwd || m_prefs.ShowPat) ? true : pwdVerify.getText().toString()
                .equals(pwdText.getText().toString());
    }

    public void update() {
        int vis = m_prefs.ShowPat ? View.VISIBLE : View.GONE;
        pwdGrid.setVisibility(vis);

        boolean hide = m_prefs.ShowPat || m_prefs.ShowPwd || openMode;

        if (hide)
            pwdText.setNextFocusDownId(R.id.okBtn);
        else
            pwdText.setNextFocusDownId(R.id.pwd_verify);

        int otherVis =  hide ? View.GONE : View.VISIBLE;
        pwdVerify.setVisibility(otherVis);
        pwdVerifyLB.setVisibility(otherVis);
        pwdVerifyClear.setVisibility(otherVis);

        pwdText.setInputType(InputType.TYPE_CLASS_TEXT
                | (m_prefs.ShowPwd ? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                        : InputType.TYPE_TEXT_VARIATION_PASSWORD));

        // pwdText.setFocusable(!m_showPat);
    }

    // =====
    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if (!hasFocus || !(view instanceof EditText)) {
            // if (view == null)
            //    view = getCurrentFocus();
            if (view != null)  
                UiUtil.hideSoftKeyboard(view);
        }
    }

    // =====
    @Override
    public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_NEXT) {
            UiUtil.hideSoftKeyboard(view);
        }
        return false;
    }

}