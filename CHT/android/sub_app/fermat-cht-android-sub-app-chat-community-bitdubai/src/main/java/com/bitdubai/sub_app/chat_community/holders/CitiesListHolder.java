package com.bitdubai.sub_app.chat_community.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.sub_app.chat_community.R;

/**
 * CitiesListHolder
 *
 * @author Roy on 21/06/16.
 * @version 1.0
 */
public class CitiesListHolder extends FermatViewHolder {

    public TextView city;
    public TextView state;

    /**
     * Constructor
     *
     * @param itemView cast ui elements
     */
    public CitiesListHolder(View itemView) {
        super(itemView);
        city = (TextView) itemView.findViewById(R.id.country_search);
        state = (TextView) itemView.findViewById(R.id.state_search);
    }
}
