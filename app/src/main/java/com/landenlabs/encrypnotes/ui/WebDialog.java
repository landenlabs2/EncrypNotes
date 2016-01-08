package com.landenlabs.encrypnotes.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.MailTo;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.landenlabs.encrypnotes.R;

public class WebDialog {
    public static String HTML_CENTER_BOX = "<div style='min-height:128px;'><table height='100%%' width='100%%'><tr valign='middle'><td style='border: 2px solid; border-radius: 25px;'><center>%s</center></table></div>";
    
    public static void show(final Context activity, String ... htmlStr) {

        String fullHtmlStr = htmlStr[0];
        if (htmlStr.length > 1) {
            String fmt = htmlStr[0];
            String arg = htmlStr[1];
            fullHtmlStr = String.format(fmt, arg);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("");
        
        WebView wv = new WebView(activity);
        wv.setBackgroundColor(Color.TRANSPARENT);
        wv.setBackgroundResource(R.drawable.about_bg);
       

        // String htmlStr = String.format(LoadData("about.html"), getPackageInfo().versionName,
        //         Doc.CRYPTO_MODE);
        wv.loadData(fullHtmlStr, "text/html; charset=utf-8", "utf-8");
        wv.setMinimumHeight(128 * 2);

        wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if(url.startsWith("mailto:")){
                    MailTo mt = MailTo.parse(url);
                    Intent emailIntent = UiUtil.newEmailIntent(activity, mt.getTo(), mt.getSubject(), mt.getBody(), mt.getCc());
                    activity.startActivity(emailIntent);
                    view.reload();
                    return true;
                } else{
                    // Open link in external browser.
                    Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    activity.startActivity(webIntent);
                }
                return true;
            }
        });

        builder.setView(wv);
        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}
