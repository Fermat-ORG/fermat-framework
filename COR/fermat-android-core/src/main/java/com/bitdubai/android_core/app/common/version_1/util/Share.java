package com.bitdubai.android_core.app.common.version_1.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ShareCompat;

/**
 * Created by MAtias Furszyfer on 2016.02.29..
 */
public class Share {

    public void shareText(Activity activity, String shareText) {
        Intent shareIntent = ShareCompat.IntentBuilder.from(activity)
                .setType("text/plain")
                .setText(shareText)
                .getIntent();
// Avoid ActivityNotFoundException
        // if (intent.resolveActivity(getPackageManager()) != null) {
        activity.startActivity(shareIntent);
        //}
    }


    public void shareMedia(Activity activity, Uri uriToImage) {
        Intent shareIntent = ShareCompat.IntentBuilder.from(activity)
                .setType("image/png")
                .setStream(uriToImage)
                .getIntent();
// Avoid ActivityNotFoundException
        // if (intent.resolveActivity(getPackageManager()) != null) {
        activity.startActivity(shareIntent);
        //}
    }


    public void sendMail(Activity activity, String[] emailTo, String text) {
        Intent shareIntent = ShareCompat.IntentBuilder.from(activity)
                .setType("text/plain")
                .addEmailTo(emailTo)
                .setText(text)
                .getIntent();
// Avoid ActivityNotFoundException
        // if (intent.resolveActivity(getPackageManager()) != null) {
        activity.startActivity(shareIntent);
        //}
    }

    public void chooserIntent(Activity activity) {
        Intent shareIntent = ShareCompat.IntentBuilder.from(activity)
                .setType("text/plain")
                .createChooserIntent();
// Avoid ActivityNotFoundException
        // if (intent.resolveActivity(getPackageManager()) != null) {
        activity.startActivity(shareIntent);
        //}
    }


}
