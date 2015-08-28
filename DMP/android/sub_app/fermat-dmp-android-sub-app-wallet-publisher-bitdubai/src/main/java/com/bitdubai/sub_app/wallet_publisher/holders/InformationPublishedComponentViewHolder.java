package com.bitdubai.sub_app.wallet_publisher.holders;

import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.sub_app.wallet_publisher.R;

/**
 * Created by Roberto
 * Updated by Francisco Vasquez
 *
 * @version 1.0
 */
public class InformationPublishedComponentViewHolder extends FermatViewHolder {

    /**
     * Represent the componentIcon
     */
    public ImageView componentIcon;

    /**
     * Represent the componentName
     */
    public FermatTextView componentName;

    /**
     * Represent the componentDescription
     */
    public FermatTextView componentDescription;

    /**
     * Represent the componentStatus
     */
    public FermatTextView componentStatus;

    /**
     * Constructor
     *
     * @param itemView
     */
    public InformationPublishedComponentViewHolder(View itemView) {
        super(itemView);
        componentIcon = (ImageView) itemView.findViewById(R.id.component_icon_image);
        componentName = (FermatTextView) itemView.findViewById(R.id.component_name);
        componentDescription = (FermatTextView) itemView.findViewById(R.id.component_description);
        componentStatus = (FermatTextView) itemView.findViewById(R.id.component_status);
    }
}