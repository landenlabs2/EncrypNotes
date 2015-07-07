package com.landenlabs.encrypnotes.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;

import com.landenlabs.encrypnotes.R;

/**
 * Simple  yes/no or ok dialog.
 * Sends +msgNun on yes/ok or -msgNun on no.
 */
public class YesNoDialog extends DialogFragment {
    
    public static final int MSG_NONE = 0;

    // Save and Restore keys
    static final String STATE_TITLE = "Title";
    static final String STATE_MSGSTR = "MsgStr";
    static final String STATE_MSGNUM = "MsgNum";
    static final String STATE_BUTTONS = "Buttons";

    // Operating modes
    public static final int BTN_YES_NO = 0;
    public static final int BTN_OK = 1;

    String m_title;
    String m_message;
    int m_msgNum;
    int m_buttons;

    // Required callback used on button clicks
    DlgClickListener m_clickListener;

    // Caller values storage.
    Object m_value;
    Object m_view;

    public YesNoDialog() {
        m_msgNum = 0;
        m_buttons = BTN_YES_NO;
    }

    public static YesNoDialog create(String title, String message, int msgNum, int buttons) {
        YesNoDialog yesNoDialog = new YesNoDialog();
        yesNoDialog.m_title = title;
        yesNoDialog.m_message = message;
        yesNoDialog.m_msgNum = msgNum;
        yesNoDialog.m_buttons = buttons;
        return yesNoDialog;
    }

    /**
     * Show a simple message dialog
     * 
     * @param msg
     */
    public static void showOk(Activity activity, String msg) {
        showDialog(activity, "", msg, MSG_NONE, YesNoDialog.BTN_OK);
    }
    
    public static void showOk(Activity activity, String msg, int msgNum) {
        showDialog(activity, "", msg, msgNum, YesNoDialog.BTN_OK);
    }
    
    public static YesNoDialog showDialog(Activity activity, String title,  String msg, int msgNum, int buttons) {
        FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
        YesNoDialog yesNoDialog = YesNoDialog.create(title, msg, msgNum, buttons);
        yesNoDialog.show(ft, "msgDialog" + buttons);
        return yesNoDialog;
    }

    // Getter / Setter to access user data.
    public YesNoDialog setValue(Object obj) {
        m_value = obj;
        return this;
    }

    public final Object getValue() {
        return m_value;
    }

    public YesNoDialog setViewer(Object view) {
        m_view = view;
        return this;
    }

    public final Object getViewer() {
        return m_view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            m_clickListener = (DlgClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement listeners!");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE_TITLE, m_title);
        outState.putString(STATE_MSGSTR, m_message);
        outState.putInt(STATE_MSGNUM, m_msgNum);
        outState.putInt(STATE_BUTTONS, m_buttons);            
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (null != savedInstanceState) {
            m_title = savedInstanceState.getString(STATE_TITLE);
            m_message = savedInstanceState.getString(STATE_MSGSTR);
            m_msgNum = savedInstanceState.getInt(STATE_MSGNUM);
            m_buttons = savedInstanceState.getInt(STATE_BUTTONS);        
        }
        int posBtn = (m_buttons == BTN_YES_NO) ? R.string.yes : android.R.string.ok;

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setTitle(m_title)
                .setMessage(m_message).setPositiveButton(posBtn, new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_clickListener.onClick(YesNoDialog.this, m_msgNum);
                    }
                });

        if (m_buttons == BTN_YES_NO) {
            builder.setNegativeButton(R.string.no, new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    m_clickListener.onClick(YesNoDialog.this, -m_msgNum);
                }
            });
        }

        return builder.create();
    }
}
