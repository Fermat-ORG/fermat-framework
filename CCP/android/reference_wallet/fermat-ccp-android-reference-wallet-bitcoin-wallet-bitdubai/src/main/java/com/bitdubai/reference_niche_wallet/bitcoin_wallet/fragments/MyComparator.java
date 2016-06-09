package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments;

import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletWalletContact;

import java.util.Comparator;

class MyComparator implements Comparator<CryptoWalletWalletContact> {

  public int compare(CryptoWalletWalletContact strA, CryptoWalletWalletContact strB) {
    return strA.getActorName().compareToIgnoreCase(strB.getActorName());

  }
}