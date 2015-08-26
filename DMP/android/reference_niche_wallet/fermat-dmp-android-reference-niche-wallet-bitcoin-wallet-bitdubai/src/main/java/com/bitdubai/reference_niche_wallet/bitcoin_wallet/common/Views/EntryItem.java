package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.Views;

import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.CryptoWalletTransaction;

public class EntryItem implements Item{
 
 //public final String title;
 //public final String subtitle;
 public final CryptoWalletTransaction cryptoWalletTransaction;
 
 public EntryItem(/*String title, String subtitle,*/CryptoWalletTransaction cryptoWalletTransaction) {
  //this.title = title;
  //this.subtitle = subtitle;
  this.cryptoWalletTransaction=cryptoWalletTransaction;
 }
  
 @Override
 public boolean isSection() {
  return false;
 }
 
}