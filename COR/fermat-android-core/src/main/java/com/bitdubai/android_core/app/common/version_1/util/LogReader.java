package com.bitdubai.android_core.app.common.version_1.util;

import android.content.Context;
import android.util.Log;

import com.bitdubai.android_core.app.common.version_1.util.mail.YourOwnSender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Matias Furszyfer on 2016.03.08..
 */
public class LogReader {


    private static final String TAG = LogReader.class.getCanonicalName();
    private static final String processId = Integer.toString(android.os.Process
            .myPid());

    public static StringBuilder getLog() {

        int lineNumber = 0;

        StringBuilder builder = new StringBuilder();


        try {
            String[] command = new String[] { "logcat", "-v", "threadtime" };

            Process process = Runtime.getRuntime().exec(command);

            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains(processId)) {
                    builder.append(line);
                    lineNumber++;
                    //Code here
                }
                if(lineNumber==500){
                    break;
                }
            }
        } catch (IOException ex) {
            Log.e(TAG, "getLog failed", ex);
        }

        return builder;
    }

    public static StringBuilder getLog(Context context,String mail) {

        int lineNumber = 0;

        StringBuilder builder = new StringBuilder();


        try {
            String[] command = new String[] { "logcat", "-v", "threadtime" };

            Process process = Runtime.getRuntime().exec(command);

            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            YourOwnSender yourOwnSender = new YourOwnSender(context);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains(processId)) {
                    builder.append(line);
                    lineNumber++;
                    //Code here
                }
                if(lineNumber==300){
                    yourOwnSender.send(mail,builder.toString());
                    lineNumber = 0;
                }
            }
        } catch (IOException ex) {
            Log.e(TAG, "getLog failed", ex);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return builder;
    }


}
