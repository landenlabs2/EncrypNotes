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

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

/**
 * Simple wrapper class to manage EncrypNote preferences.
 * 
 * @author Dennis Lang
 * @see http://landenlabs.com
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
    public String DefaultPwd = "";
    
    private static final String PREF_PARANOID = "paranoid";
    private static final String PREF_INVERT_COLOR = "invertColor";
    private static final String PREF_GLOBAL_PWD = "globalPwd";
    private static final String PREF_TEXT_SCALE = "textScale";
    private static final String PREF_SHOW_PAT = "showPat";
    private static final String PREF_SHOW_PWD = "showPwd";
    private static final String PREF_DEFAULT_PWD = "defaultPwd";
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

            if (properties.getProperty(PREF_DEFAULT_PWD) != null)
                DefaultPwd = properties.getProperty(PREF_DEFAULT_PWD);
            
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
