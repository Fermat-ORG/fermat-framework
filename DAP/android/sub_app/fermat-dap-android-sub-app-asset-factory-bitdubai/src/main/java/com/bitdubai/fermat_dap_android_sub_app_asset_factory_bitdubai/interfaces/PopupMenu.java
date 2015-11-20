package com.bitdubai.fermat_dap_android_sub_app_asset_factory_bitdubai.interfaces;

import android.view.View;

import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces.AssetFactory;

/**
 * Popup menu interface for menu in recycler per item.
 *
 * @author Francisco Vasquez
 * @version 1.0.
 */
public interface PopupMenu {

    void onMenuItemClickListener(View menuView, AssetFactory project, int position);

}
