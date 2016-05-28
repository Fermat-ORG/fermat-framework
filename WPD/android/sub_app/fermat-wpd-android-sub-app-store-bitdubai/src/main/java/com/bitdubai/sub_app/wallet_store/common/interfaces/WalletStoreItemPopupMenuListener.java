package com.bitdubai.sub_app.wallet_store.common.interfaces;

import android.view.View;

import com.bitdubai.sub_app.wallet_store.common.models.WalletStoreListItem;

/**
 * Popup menu interface for menu in recycler per item.
 *
 * @author Francisco Vasquez
 * @version 1.0.
 */
public interface WalletStoreItemPopupMenuListener {

    void onMenuItemClickListener(View menuView, WalletStoreListItem item, int position);

}
