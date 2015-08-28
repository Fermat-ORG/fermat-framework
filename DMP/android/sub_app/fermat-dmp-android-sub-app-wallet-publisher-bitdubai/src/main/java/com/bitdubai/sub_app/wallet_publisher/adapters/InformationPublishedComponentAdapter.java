/*
 * @#InformationPublishedComponentAdapter.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.sub_app.wallet_publisher.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_publisher.interfaces.InformationPublishedComponent;
import com.bitdubai.sub_app.wallet_publisher.R;
import com.bitdubai.sub_app.wallet_publisher.holders.InformationPublishedComponentViewHolder;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

/**
 * The Class <code>com.bitdubai.sub_app.wallet_publisher.adapters.InformationPublishedComponentAdapter</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 27/08/15.
 * Updated by Francisco Vasquez - (fvasquezjatar@gmail.com) on 27/08/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class InformationPublishedComponentAdapter extends FermatAdapter<InformationPublishedComponent, InformationPublishedComponentViewHolder> {

    /**
     * Constructor whit parameter
     *
     * @param context
     */
    public InformationPublishedComponentAdapter(Context context) {
        super(context);
    }

    /**
     * Constructor whit parameters
     *
     * @param context
     * @param dataSet
     */
    public InformationPublishedComponentAdapter(Context context, ArrayList<InformationPublishedComponent> dataSet) {
        super(context, dataSet);
    }

    /**
     * (non-javadoc)
     *
     * @see FermatAdapter#createHolder(View, int)
     */
    @Override
    protected InformationPublishedComponentViewHolder createHolder(View itemView, int type) {
        return new InformationPublishedComponentViewHolder(itemView);
    }

    /**
     * (non-javadoc)
     *
     * @see FermatAdapter#getCardViewResource()
     */
    @Override
    protected int getCardViewResource() {
        return R.layout.information_published_component_item;
    }

    /**
     * (non-javadoc)
     *
     * @see FermatAdapter#bindHolder(FermatViewHolder, Object, int)
     */
    @Override
    protected void bindHolder(InformationPublishedComponentViewHolder holder, InformationPublishedComponent data, int position) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data.getIconImg().getData());
        holder.componentIcon.setImageDrawable(Drawable.createFromStream(inputStream, "walletIcon"));
        holder.componentName.setText(data.getWalletFactoryProjectName());
        holder.componentDescription.setText(data.getDescriptions());
        holder.componentStatus.setText(data.getStatus().toString());
    }

}
