package com.bitdubai.fermat_api.layer.all_definition.util;

import com.carrotsearch.sizeof.RamUsageEstimator;

/**
 * Created by jorge on 15-10-2015.
 */
public class ObjectSizeFetcher {

    public static String sizeOf(final Object object){

        //TODO: Lo comenté porque está tirando un outOfMemory en mi maquena que es poderosa, no quiero pensar en las maquinas de los demas. MAti
        return null;//RamUsageEstimator.humanSizeOf(object);
    }
}
