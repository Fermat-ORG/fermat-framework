package org.fermat.fermat_dap_android_wallet_asset_user.interfaces;

import android.view.View;

import org.fermat.fermat_dap_android_wallet_asset_user.models.DigitalAsset;

/**
 * Popup menu interface for menu in recycler per item.
 *
 * @author Francisco Vasquez
 * @version 1.0.
 */
public interface PopupMenu {

    void onMenuItemClickListener(View menuView, DigitalAsset project, int position);

}
