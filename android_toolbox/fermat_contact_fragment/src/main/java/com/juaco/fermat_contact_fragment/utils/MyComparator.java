package com.juaco.fermat_contact_fragment.utils;

import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletWalletContact;

import java.util.Comparator;

public class MyComparator implements Comparator<CryptoWalletWalletContact> {

  public int compare(CryptoWalletWalletContact strA, CryptoWalletWalletContact strB) {
    return strA.getActorName().compareToIgnoreCase(strB.getActorName());

  }
}