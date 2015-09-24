package com.bitdubai.sub_app.wallet_factory.interfaces;

import android.view.View;

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProject;

/**
 * Popup menu interface for menu in recycler per item.
 *
 * @author Francisco Vasquez
 * @version 1.0.
 */
public interface PopupMenu {

    void onMenuItemClickListener(View menuView, WalletFactoryProject project, int position);

}
