package com.bitdubai.android_core.app.common.version_1.util.mail;

import android.content.Context;

import org.acra.ACRA;
import org.acra.ACRAConstants;
import org.acra.ReportField;
import org.acra.collector.CrashReportData;
import org.acra.sender.ReportSender;
import org.acra.sender.ReportSenderException;

public class YourOwnSender implements ReportSender {

    // NB requires a no arg constructor.

    private final Context mContext;

    public YourOwnSender(Context ctx) {
        mContext = ctx;
    }

    @Override
    public void send(Context context, CrashReportData errorContent) throws ReportSenderException {

        final String subject = new StringBuilder().append(mContext.getPackageName()).append(" Crash Report").toString();
        final String body = buildBody(errorContent);

//        final Intent emailIntent = new Intent(android.content.Intent.ACTION_SENDTO);
//        emailIntent.setData(Uri.fromParts("mailto", ACRA.getConfig().mailTo(), null));
//        emailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
//        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, body);
//        mContext.startActivity(emailIntent);

        GMailSender gMailSender = new GMailSender("fermatmatiasreport@gmail.com", "fermat123");
        try {
            gMailSender.sendMail("error report", body, "matiasfurszyfer@gmail.com", "matiasfurszyfer@gmail.com");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String buildBody(CrashReportData errorContent) {
        ReportField[] fields = ACRA.getConfig().customReportContent();
        if (fields.length == 0) {
            fields = ACRAConstants.DEFAULT_MAIL_REPORT_FIELDS;
        }

        final StringBuilder builder = new StringBuilder();
        for (ReportField field : fields) {
            builder.append(field.toString()).append("=");
            builder.append(errorContent.get(field));
            builder.append('\n');
        }
        return builder.toString();
    }

    public void send(String userTo, String body) throws Exception {
        if (userTo == null || userTo.equals("")) throw new IllegalArgumentException();
        GMailSender gMailSender = new GMailSender("fermatmatiasreport@gmail.com", "fermat123");
        gMailSender.sendMail("error report", body, userTo, userTo);
    }

    public void sendPrivateKey(String userTo, String body) throws Exception {
        if (userTo == null || userTo.equals("")) throw new IllegalArgumentException();
        GMailSender gMailSender = new GMailSender("fermatmatiasreport@gmail.com", "fermat123");
        gMailSender.sendMailPrivateKey("Private Key", body, userTo, userTo);
    }
}