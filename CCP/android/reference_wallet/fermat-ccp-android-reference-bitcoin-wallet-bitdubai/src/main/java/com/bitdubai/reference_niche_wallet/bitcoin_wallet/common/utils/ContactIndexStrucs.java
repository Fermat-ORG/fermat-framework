package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils;

import java.util.LinkedHashMap;

/**
 * Created by nelson on 31/07/15.
 */
public abstract class ContactIndexStrucs {

    private static LinkedHashMap<String, Integer> indexTable;
    public static final String[] ALPHABET_INDEX ={"@", "#", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "M", "N", "Ñ", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    public static LinkedHashMap<String, Integer> getContactIndexTable() {
        if (indexTable == null) {
            indexTable = new LinkedHashMap<String, Integer>();
            indexTable.put("@", -1);
            indexTable.put("#", -1);
            indexTable.put("A", -1);
            indexTable.put("B", -1);
            indexTable.put("C", -1);
            indexTable.put("D", -1);
            indexTable.put("E", -1);
            indexTable.put("F", -1);
            indexTable.put("G", -1);
            indexTable.put("H", -1);
            indexTable.put("I", -1);
            indexTable.put("J", -1);
            indexTable.put("K", -1);
            indexTable.put("M", -1);
            indexTable.put("N", -1);
            indexTable.put("Ñ", -1);
            indexTable.put("O", -1);
            indexTable.put("P", -1);
            indexTable.put("Q", -1);
            indexTable.put("R", -1);
            indexTable.put("S", -1);
            indexTable.put("T", -1);
            indexTable.put("U", -1);
            indexTable.put("V", -1);
            indexTable.put("W", -1);
            indexTable.put("X", -1);
            indexTable.put("Y", -1);
            indexTable.put("Z", -1);
        }

        return indexTable;
    }
}
