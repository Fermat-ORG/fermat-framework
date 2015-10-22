package com.bitdubai.sub_app.crypto_customer_identity.common.adapters;

import android.content.Context;
import android.text.SpannableString;
import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;

import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.TextUtils;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_customer_identity.interfaces.CryptoCustomerIdentityInformation;
import com.bitdubai.sub_app.crypto_customer_identity.R;
import com.bitdubai.sub_app.crypto_customer_identity.common.holders.CryptoCustomerIdentityInfoViewHolder;
import com.bitdubai.sub_app.crypto_customer_identity.util.CryptoCustomerIdentityListFilter;

import java.util.ArrayList;

/**
 * Adapter para el RecyclerView del CryptoBrokerIdentityListFragment que muestra la lista de identidades de un customer
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
    protected void bindHolder(final CryptoCustomerIdentityInfoViewHolder holder, final CryptoCustomerIdentityInformation data, final int position) {
        filter = (CryptoCustomerIdentityListFilter) getFilter();

        SpannableString spannedText = TextUtils.getSpannedText(
                context.getResources(),
                R.color.spanned_text,
                data.getAlias(),
                filter.getConstraint());

        holder.setText(spannedText);
        holder.setImage(data.getProfileImage());
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
    public Filter getFilter() {
        if (filter == null)
            filter = new CryptoCustomerIdentityListFilter(dataSet, this);

        return filter;
    }
}
