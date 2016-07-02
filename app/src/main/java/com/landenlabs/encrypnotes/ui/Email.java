package com.landenlabs.encrypnotes.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by Dennis Lang on 7/1/16.
 */
public class Email {

    public static void send(Activity activity, String emailTo, String emailSubject, String emailContent)
    {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        // emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{ emailTo});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, emailContent);

        // use below 2 commented lines if need to use BCC an CC feature in email
        boolean bcc = false;
        if (bcc) {
            String bccTo = "foo@gmail.com";
            emailIntent.putExtra(Intent.EXTRA_CC, new String[]{bccTo});
            emailIntent.putExtra(Intent.EXTRA_BCC, new String[]{bccTo});
        }

        // use below 3 commented lines if need to send attachment
        boolean attachment = false;
        if (attachment) {
            emailIntent.setType("image/jpeg");
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "My Picture");
            emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://sdcard/captureimage.png"));
        }

        // need this to prompts email client only
        emailIntent.setType("message/rfc822");
        emailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        activity.startActivity(Intent.createChooser(emailIntent, "Select an Email Client:"));
    }
}
