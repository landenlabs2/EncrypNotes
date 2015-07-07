package com.landenlabs.encrypnotes;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

/**
 * Simple wrapper class to manage EncrypNote preferences.
 * 
 * @author Dennis Lang
 *
 */
public class EncrypPrefs {

    public static final String PREFS_FILENAME = "preferences.xls";
    
    // -- Preferences saved/restored
    public boolean Paranoid = false;
    public boolean Global_pwd = false;
    public boolean InvertBg = false;
    public float TextScale = 0.0f; // -1.0f = half size, 0=normal, 1.0 = double size.
    public boolean ShowPat = true;
    public boolean ShowPwd = true;
    
    private static final String PREF_PARANOID = "paranoid";
    private static final String PREF_INVERT_COLOR = "invertColor";
    private static final String PREF_GLOBAL_PWD = "globalPwd";
    private static final String PREF_TEXT_SCALE = "textScale";
    private static final String PREF_SHOW_PAT = "showPat";
    private static final String PREF_SHOW_PWD = "showPwd";
    private static final String TAG = EncrypPrefs.class.getName();
    private Activity m_context;
    
    public EncrypPrefs(Activity context) {
        m_context = context;
    }
    public boolean load() {
        try {
            InputStream is = m_context.openFileInput(PREFS_FILENAME);
            Properties properties = new Properties();
    
            properties.loadFromXML(is);
            if (properties.getProperty(PREF_PARANOID) != null)
                Paranoid = Boolean.parseBoolean(properties.getProperty(PREF_PARANOID));
    
            if (properties.getProperty(PREF_INVERT_COLOR) != null)
                InvertBg = Boolean.parseBoolean(properties.getProperty(PREF_INVERT_COLOR));
    
            if (properties.getProperty(PREF_GLOBAL_PWD) != null)
                Global_pwd = Boolean.parseBoolean(properties.getProperty(PREF_GLOBAL_PWD));
            
            if (properties.getProperty(PREF_SHOW_PAT) != null)
                ShowPat = Boolean.parseBoolean(properties.getProperty(PREF_SHOW_PAT));
            
            if (properties.getProperty(PREF_SHOW_PWD) != null)
                ShowPwd = Boolean.parseBoolean(properties.getProperty(PREF_SHOW_PWD));
            
            if (properties.getProperty(PREF_TEXT_SCALE) != null)
                TextScale = Float.parseFloat(properties.getProperty(PREF_TEXT_SCALE));
            
            is.close();
        } catch (FileNotFoundException e) {
            return false;
        } catch (InvalidPropertiesFormatException e) {
            Log.e(TAG, "load preferences failed", e);
        } catch (IOException e) {
            Log.e(TAG, "load preferences failed", e);
        }
        
        return true;
    }
    
    
    /**
     * Save user preferences.
     */
    public void save() {
        try {
            OutputStream os = m_context.openFileOutput(PREFS_FILENAME, Context.MODE_PRIVATE);
            Properties properties = new Properties();
            properties.setProperty(PREF_PARANOID, Paranoid ? "true" : "false");
            properties.setProperty(PREF_INVERT_COLOR, InvertBg ? "true" : "false");
            properties.setProperty(PREF_GLOBAL_PWD, Global_pwd ? "true" : "false");
            properties.setProperty(PREF_SHOW_PAT, ShowPat ? "true" : "false");
            properties.setProperty(PREF_SHOW_PWD, ShowPwd ? "true" : "false");
            properties.setProperty(PREF_TEXT_SCALE,  String.valueOf(TextScale));
            properties.storeToXML(os, "EncrypNotes properties");
            os.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
