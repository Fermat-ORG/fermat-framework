package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments;

import java.util.Comparator;

class MyComparator implements Comparator<String> {
  public int compare(String strA, String strB) {
    return strA.compareToIgnoreCase(strB);
  }
}