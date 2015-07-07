package com.landenlabs.encrypnotes.ui;

import android.app.DialogFragment;

/**
 * Created by dlang_local on 7/4/2015.
 */
public interface DlgClickListener {

    // Main messages
    public static final int CLKMSG_EXIT = 1;
    public static final int CLKMSG_NEW_FILE = 2;
    public static final int CLKMSG_SAVE_THEN_NEW = 3;
    public static final int CLKMSG_SAVE_THEN_OPEN = 4;
    public static final int CLKMSG_FILENAME_CHANGED = 5;

    public void onClick(DialogFragment dialog, int whichMsg);
}
