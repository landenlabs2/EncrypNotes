package com.landenlabs.encrypnotes.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.landenlabs.encrypnotes.R;

/**
 * Created by dlang_local on 7/5/2015.
 */
public class RenameDialog extends DialogFragment {

    static final String STATE_LAYOUT = "Layout";
    static final String STATE_MSGNUM = "MsgNum";
    static final String STATE_FROM = "From";
    static final String STATE_TO = "To";

    int m_layoutId;
    int m_msgNum;
    String m_fromStr;
    String m_toStr;

    EditText m_fromET;
    EditText m_toET;

    // Required callback used on button clicks
    DlgClickListener m_clickListener;

    // Caller auxilary view object.
    Object m_view;

    public RenameDialog() {
        m_layoutId = -1;
        m_msgNum = 0;
    }

    public static RenameDialog create(int layoutId, int msgNum) {
        RenameDialog sliderDialog = new RenameDialog();
        sliderDialog.m_layoutId = layoutId;
        sliderDialog.m_msgNum = msgNum;
        return sliderDialog;
    }

    public String getFrom() {
        return m_fromET.getText().toString();
    }

    public RenameDialog setFrom(String str) {
        m_fromStr = str;
        return this;
    }

    public String getTo() {
        return m_toET.getText().toString();
    }

    public RenameDialog setTo(String str) {
        m_toStr = str;
        return this;
    }

    public RenameDialog setViewer(Object view) {
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
        outState.putInt(STATE_LAYOUT, m_layoutId);
        outState.putInt(STATE_MSGNUM, m_msgNum);
        outState.putString(STATE_FROM, m_fromET.getText().toString());
        outState.putString(STATE_TO, m_toET.getText().toString());
        // TODO - should we save m_view, can it be restored correctly.
    }

    /*
     * // Implement onCreateView if you want to appear in an existing UI. // (see onCreateView)
     *
     * @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
     * savedInstanceState) { View view = inflater.inflate(mLayoutId, container, false); return
     * view; }
     */

    // Implement if want a floating dialog (see onCreateView)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (null != savedInstanceState) {
            m_layoutId = savedInstanceState.getInt(STATE_LAYOUT );
            m_msgNum = savedInstanceState.getInt(STATE_MSGNUM );
            m_fromStr = savedInstanceState.getString(STATE_FROM);
            m_toStr = savedInstanceState.getString(STATE_TO);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = getActivity().getLayoutInflater().inflate(m_layoutId, null);
        builder.setView(view);

        m_fromET = UiUtil.viewById(view, R.id.from_value);
        m_fromET.setText(m_fromStr);
        m_toET = UiUtil.viewById(view, R.id.to_value);
        m_toET.setText(m_toStr);

        Button okBtn = (Button) view.findViewById(R.id.okBtn);
        if (okBtn != null) {
            okBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    m_clickListener.onClick(RenameDialog.this, m_msgNum);
                    RenameDialog.this.dismiss();
                }
            });
        }

        Button cancelBtn = (Button) view.findViewById(R.id.cancelBtn);
        if (cancelBtn != null) {
            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    m_clickListener.onClick(RenameDialog.this, -m_msgNum);
                    RenameDialog.this.dismiss();
                }
            });
        }

        Dialog dialog = builder.create();
        return dialog;
    }

    public RenameDialog showIt(FragmentManager manager, String tag) {
        show(manager, tag);
        return this;
    }
}