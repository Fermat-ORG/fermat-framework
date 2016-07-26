package com.bitdubai.fermat_android_api.layer.definition.wallet.utils;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Matias Furszyfer on 2016.04.15..
 */
public class AndroidExternalAppsIntentHelper {

    public static void sendMail(Context context, String[] mail, String subject, String body) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
//        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"recipient@example.com"});
//        i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
//        i.putExtra(Intent.EXTRA_TEXT   , "body of email");
        i.putExtra(Intent.EXTRA_EMAIL, mail);
        i.putExtra(Intent.EXTRA_SUBJECT, subject);
        i.putExtra(Intent.EXTRA_TEXT, body);
        try {
            context.startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }


}
