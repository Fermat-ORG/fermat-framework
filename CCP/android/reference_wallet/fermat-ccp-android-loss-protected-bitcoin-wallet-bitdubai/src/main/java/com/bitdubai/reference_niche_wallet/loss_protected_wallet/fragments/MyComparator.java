package com.bitdubai.reference_niche_wallet.loss_protected_wallet.fragments;

import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWalletContact;

import java.util.Comparator;

class MyComparator implements Comparator<LossProtectedWalletContact> {

  public int compare(LossProtectedWalletContact strA, LossProtectedWalletContact strB) {
    return strA.getActorName().compareToIgnoreCase(strB.getActorName());

  }
}