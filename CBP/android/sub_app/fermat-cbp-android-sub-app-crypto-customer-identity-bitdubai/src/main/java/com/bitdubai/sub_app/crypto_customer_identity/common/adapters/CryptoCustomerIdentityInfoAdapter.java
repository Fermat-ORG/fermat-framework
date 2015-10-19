package com.bitdubai.sub_app.crypto_customer_identity.common.adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_customer_identity.interfaces.CryptoCustomerIdentityInformation;
import com.bitdubai.sub_app.crypto_customer_identity.R;
import com.bitdubai.sub_app.crypto_customer_identity.common.holders.CryptoCustomerIdentityInfoViewHolder;
import com.bitdubai.sub_app.crypto_customer_identity.common.model.CryptoCustomerIdentityInformationImp;
import com.bitdubai.sub_app.crypto_customer_identity.util.CryptoCustomerIdentityListFilter;

import java.util.ArrayList;

/**
 * Created on 22/08/15.
 * Adapter para el RecliclerView del CryptoCustomerIdentityListFragment que muestra el catalogo de Wallets disponibles en el store
 *
 * @author Nelson Ramirez
 */
public class CryptoCustomerIdentityInfoAdapter
        extends FermatAdapter<CryptoCustomerIdentityInformation, CryptoCustomerIdentityInfoViewHolder>
        implements Filterable {

    CryptoCustomerIdentityListFilter filter;

    public CryptoCustomerIdentityInfoAdapter(Context context, ArrayList<CryptoCustomerIdentityInformation> dataSet) {
        super(context, dataSet);
    }

    @Override
    protected CryptoCustomerIdentityInfoViewHolder createHolder(View itemView, int type) {
        return new CryptoCustomerIdentityInfoViewHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.crypto_customer_identity_list_item;
    }

    @Override
    protected void bindHolder(final CryptoCustomerIdentityInfoViewHolder holder, final CryptoCustomerIdentityInformation data, final int position) {
        holder.getIdentityName().setText(data.getAlias());

        if (data instanceof CryptoCustomerIdentityInformationImp) {
            CryptoCustomerIdentityInformationImp dataImp = (CryptoCustomerIdentityInformationImp) data;
            holder.getIdentityImage().setImageResource(dataImp.getProfileImageDrawableId());
        } else {
            byte[] profileImage = data.getProfileImage();
            holder.getIdentityImage().setImageBitmap(BitmapFactory.decodeByteArray(profileImage, 0, profileImage.length));
        }
    }

    @Override
    public Filter getFilter() {
        if (filter == null)
            filter = new CryptoCustomerIdentityListFilter(dataSet, this);

        return filter;
    }
}
