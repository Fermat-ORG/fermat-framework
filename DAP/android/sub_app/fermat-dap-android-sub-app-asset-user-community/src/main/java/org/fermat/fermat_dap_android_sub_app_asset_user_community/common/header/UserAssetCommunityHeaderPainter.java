package org.fermat.fermat_dap_android_sub_app_asset_user_community.common.header;

import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.engine.HeaderViewPainter;

/**
 * Created by Nerio on 07/03/16.
 */
public class UserAssetCommunityHeaderPainter implements HeaderViewPainter {

    @Override
    public void addExpandableHeader(ViewGroup viewGroup) {
        UserAssetCommunityHeaderFactory.constructHeader(viewGroup);
    }
}