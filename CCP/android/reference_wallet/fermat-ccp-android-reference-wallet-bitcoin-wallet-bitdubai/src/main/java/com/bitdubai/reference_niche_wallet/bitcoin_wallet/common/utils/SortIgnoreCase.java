package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils;

import java.util.Comparator;

/**
 * Created by nelson on 28/07/15.
 */
public class SortIgnoreCase implements Comparator<String> {
    public int compare(String s1, String s2) {
        return s1.compareToIgnoreCase(s2);
    }
}
