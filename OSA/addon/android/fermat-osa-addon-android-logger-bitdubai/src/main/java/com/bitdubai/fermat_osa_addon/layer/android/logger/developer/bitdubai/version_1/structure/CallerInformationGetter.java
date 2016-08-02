package com.bitdubai.fermat_osa_addon.layer.android.logger.developer.bitdubai.version_1.structure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by rodrigo on 2015.06.25..
 */
public final class CallerInformationGetter {


    /**
     * returns information of the thread.
     * @return
     */
    public final List<String> getCurrentThreadInformation() {
        List<String> list = new ArrayList<String>();
        list.add("Thread name:" + Thread.currentThread().getName());
        list.add("Thread state:" + Thread.currentThread().getState().toString());

        return list;
    }


    /**
     * returns information of the class and method
     * @return
     */
    public final List<String> getCurrentMethodInformation() {
        List<String> list = new ArrayList<String>();

        StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
        StackTraceElement e = stacktrace[5];//maybe this number needs to be corrected

        list.add("Class name:" + e.getClassName());
        try {
             Class c = Class.forName(e.getClassName());
            list.add("Package: " + c.getPackage().getName());
        } catch (ClassNotFoundException e1) {
            /**
             * If I couldn't get the class, then I won't show that info.
             */
        }
        list.add("File name: " + e.getFileName());

        list.add("Method:" + e.toString());
        return list;
    }

}
