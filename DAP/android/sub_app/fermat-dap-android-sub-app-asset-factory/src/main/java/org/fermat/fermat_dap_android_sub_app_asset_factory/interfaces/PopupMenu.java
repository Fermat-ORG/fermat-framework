package org.fermat.fermat_dap_android_sub_app_asset_factory.interfaces;

import android.view.View;

import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces.AssetFactory;

/**
 * Popup menu interface for menu in recycler per item.
 *
 * @author Francisco Vasquez
 * @version 1.0.
 */
public interface PopupMenu {

    void onMenuItemClickListener(View menuView, AssetFactory project, int position);

}
