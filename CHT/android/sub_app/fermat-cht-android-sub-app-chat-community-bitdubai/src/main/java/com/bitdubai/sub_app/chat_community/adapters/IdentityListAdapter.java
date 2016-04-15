package com.bitdubai.sub_app.chat_community.adapters;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem;
import com.bitdubai.sub_app.chat_community.R;
import com.bitdubai.sub_app.chat_community.holders.IdentityListDrawerHolder;

import java.util.List;

/**
 * IdentityListAdapter
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 13/04/16.
 * @version 1.0
 */

public class IdentityListAdapter extends FermatAdapter<MenuItem, IdentityListDrawerHolder> {

    protected IdentityListAdapter(Context context, List<MenuItem> dataSet) {
        super(context, dataSet);
    }

    @Override
    protected IdentityListDrawerHolder createHolder(View itemView, int type) {
        return null;
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.cht_comm_navigation_drawer_identities_item;
    }

    @Override
    protected void bindHolder(IdentityListDrawerHolder holder, MenuItem data, int position) {
    }
}
