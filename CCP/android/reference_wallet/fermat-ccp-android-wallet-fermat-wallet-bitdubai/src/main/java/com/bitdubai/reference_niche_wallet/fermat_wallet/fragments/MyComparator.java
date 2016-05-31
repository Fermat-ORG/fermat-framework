package com.bitdubai.reference_niche_wallet.fermat_wallet.fragments;




import com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.interfaces.FermatWalletWalletContact;

import java.util.Comparator;

class MyComparator implements Comparator<FermatWalletWalletContact> {

  public int compare(FermatWalletWalletContact strA, FermatWalletWalletContact strB) {
    return strA.getActorName().compareToIgnoreCase(strB.getActorName());

  }
}