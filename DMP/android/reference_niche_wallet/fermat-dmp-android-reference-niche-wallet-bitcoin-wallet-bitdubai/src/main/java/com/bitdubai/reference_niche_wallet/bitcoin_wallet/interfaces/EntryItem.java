package com.bitdubai.reference_niche_wallet.bitcoin_wallet.interfaces;

public class EntryItem implements Item{
 
 public final String title;
 public final String subtitle;
 
 public EntryItem(String title, String subtitle) {
  this.title = title;
  this.subtitle = subtitle;
 }
  
 @Override
 public boolean isSection() {
  return false;
 }
 
}