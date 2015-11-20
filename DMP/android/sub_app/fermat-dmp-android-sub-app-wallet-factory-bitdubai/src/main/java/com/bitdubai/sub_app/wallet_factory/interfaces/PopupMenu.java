package com.bitdubai.sub_app.wallet_factory.interfaces;

import android.view.View;

/**
 * Popup menu interface for menu in recycler per item.
 *
 * @author Francisco Vasquez
 * @version 1.0.
 */
public interface PopupMenu {

    void onMenuItemClickListener(View menuView, Object project, int position);

}
