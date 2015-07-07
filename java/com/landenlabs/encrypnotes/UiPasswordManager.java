package com.landenlabs.encrypnotes;

import android.app.Dialog;
import android.text.InputType;
import android.view.View;
import android.view.View.OnFocusChangeListener;
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
 * 
 */
class UiPasswordManager implements OnFocusChangeListener {
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
        pwdVerify.setOnFocusChangeListener(this);
    }
    
    public EditText getPwdView() {
        return pwdText;
    }

    public boolean isValid() {
        return m_prefs.ShowPwd ? true : pwdVerify.getText().toString()
                .equals(pwdText.getText().toString());
    }

    public void update() {
        int vis = m_prefs.ShowPat ? View.VISIBLE : View.GONE;
        pwdGrid.setVisibility(vis);

        int otherVis = View.GONE;
        if (!m_prefs.ShowPat && !openMode)
            otherVis = m_prefs.ShowPwd ? View.GONE : View.VISIBLE;

        pwdVerify.setVisibility(otherVis);
        pwdVerifyLB.setVisibility(otherVis);
        pwdVerifyClear.setVisibility(otherVis);

        pwdText.setInputType(InputType.TYPE_CLASS_TEXT
                | (m_prefs.ShowPwd ? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                        : InputType.TYPE_TEXT_VARIATION_PASSWORD));

        // pwdText.setFocusable(!m_showPat);
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if (!hasFocus) {
            // if (view == null)
            //    view = getCurrentFocus();
            if (view != null)  
                UiUtil.hideSoftKeyboard(view);
        }
    }
    
   
}