package com.bitdubai.fermat_osa_addon.layer.android.logger.developer.bitdubai.version_1.structure;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rodrigo on 2015.06.25..
 */
public final class CallerInformationGetter {


    /**
     * returns information of the thread.
     *
     * @return
     */
    public final List<String> getCurrentThreadInformation() {
        List<String> list = new ArrayList<String>();
        list.add(new StringBuilder().append("Thread name:").append(Thread.currentThread().getName()).toString());
        list.add(new StringBuilder().append("Thread state:").append(Thread.currentThread().getState().toString()).toString());

        return list;
    }


    /**
     * returns information of the class and method
     *
     * @return
     */
    public final List<String> getCurrentMethodInformation() {
        List<String> list = new ArrayList<String>();

        StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
        StackTraceElement e = stacktrace[5];//maybe this number needs to be corrected

        list.add(new StringBuilder().append("Class name:").append(e.getClassName()).toString());
        try {
            Class c = Class.forName(e.getClassName());
            list.add(new StringBuilder().append("Package: ").append(c.getPackage().getName()).toString());
        } catch (ClassNotFoundException e1) {
            /**
             * If I couldn't get the class, then I won't show that info.
             */
        }
        list.add(new StringBuilder().append("File name: ").append(e.getFileName()).toString());

        list.add(new StringBuilder().append("Method:").append(e.toString()).toString());
        return list;
    }

}
