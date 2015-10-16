package com.bitdubai.fermat_api.layer.all_definition.util;

import java.lang.instrument.Instrumentation;

/**
 * Created by jorge on 15-10-2015.
 */
public class ObjectSizeFetcher {
    private static Instrumentation instrumentation;

    public static void premain(String args, Instrumentation inst) {
        instrumentation = inst;
    }

    public static long getObjectSize(Object o) {
        return instrumentation.getObjectSize(o);
    }
}
