package com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.structure;

import java.util.List;

/**
 * Created by Alex on 11/13/2015.
 */
public class IndexMathHelper {

    public static double mean(List<Double> values) {
        double sum = 0;
        for (Double val : values) {
            sum += val;
        }
        return sum / values.size();
    }


    public static double standardDeviation(List<Double> values) {
        double sum = 0;
        double mean = mean(values);

        for (Double val : values) {
            sum += Math.pow(val - mean, 2);
        }
        return Math.sqrt(sum / values.size());
    }

}
