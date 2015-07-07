package com.landenlabs.encrypnotes;

import com.landenlabs.encrypnotes.R;
import com.landenlabs.encrypnotes.R.id;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class UiSplashScreen {
    
    final Activity m_context;
    private ImageView m_splashImg;
    private TextView m_splashTxt;
    private AnimatorSet m_splashAnimatSet;
    
    public UiSplashScreen(Activity context) {
        m_context = context;
    }
    
    /**
     * Show animated splash screen.
     */
    public void show() {
        final int fadeinMsec = 2000;
        final int fadeOutMsec = 1000;

        m_splashImg = (ImageView) m_context.findViewById(R.id.overlay_logo);
        m_splashImg.setVisibility(View.VISIBLE);
        m_splashTxt = (TextView) m_context.findViewById(R.id.overlay_text);
        m_splashTxt.setVisibility(View.VISIBLE);
        ObjectAnimator fadeImgIn = ObjectAnimator.ofFloat(m_splashImg, "alpha", 0f, 1f);
        fadeImgIn.setDuration(fadeinMsec);

        ObjectAnimator fadeTxtIn = ObjectAnimator.ofFloat(m_splashTxt, "alpha", 0f, 1f);
        fadeTxtIn.setDuration(fadeinMsec);
        
        ObjectAnimator fadeImgOut = ObjectAnimator.ofFloat(m_splashImg, "alpha", 1f, 0f);
        fadeImgOut.setDuration(fadeOutMsec);
        
        ObjectAnimator fadeTxtOut = ObjectAnimator.ofFloat(m_splashTxt, "alpha", 1f, 0f);
        fadeTxtOut.setDuration(fadeOutMsec);

        m_splashAnimatSet = new AnimatorSet();
        m_splashAnimatSet.playSequentially(fadeImgIn, fadeTxtIn, fadeImgOut, fadeTxtOut);
        m_splashAnimatSet.start();
    }

    /**
     * Hide splash screen.
     */
    public void hide() {
        if (m_splashAnimatSet != null)
            m_splashAnimatSet.end(); // possible threading issue.

        if (m_splashImg != null) {
            m_splashImg.setVisibility(View.INVISIBLE);
            m_splashImg = null;
            m_splashTxt.setVisibility(View.INVISIBLE);
            m_splashTxt = null;
        }
    }
  
}
