package com.bitdubai.sub_app.crypto_customer_identity.common.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.SpannableString;
import android.view.View;
import android.widget.Filterable;

import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.TextUtils;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.interfaces.CryptoCustomerIdentityInformation;
import com.bitdubai.sub_app.crypto_customer_identity.R;
import com.bitdubai.sub_app.crypto_customer_identity.common.holders.CryptoCustomerIdentityInfoViewHolder;
import com.bitdubai.sub_app.crypto_customer_identity.util.CryptoCustomerIdentityListFilter;

import java.util.List;

/**
 * Adapter para el RecyclerView del CryptoBrokerIdentityListFragment que muestra la lista de identidades de un customer
 *
 * @author Nelson Ramirez
 */
public class CryptoCustomerIdentityInfoAdapter
        extends FermatAdapter<CryptoCustomerIdentityInformation, CryptoCustomerIdentityInfoViewHolder>
        implements Filterable {

    CryptoCustomerIdentityListFilter filter;

    public CryptoCustomerIdentityInfoAdapter(Context context, List<CryptoCustomerIdentityInformation> dataSet) {
        super(context, dataSet);
    }

    @Override
    protected void bindHolder(final CryptoCustomerIdentityInfoViewHolder holder,
                              final CryptoCustomerIdentityInformation data,
                              final int position) {

        filter = getFilter();

        SpannableString spannedText = TextUtils.getSpannedText(
                context.getResources(),
                R.color.spanned_text,
                data.getAlias(),
                filter.getConstraint());

        holder.setText(spannedText);

        byte[] profileImage = data.getProfileImage();
        Bitmap imageBitmap = BitmapFactory.decodeByteArray(profileImage, 0, profileImage.length);

        RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), imageBitmap);
        roundedDrawable.setCornerRadius(150);
        holder.getImage().setImageDrawable(roundedDrawable);


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
    public CryptoCustomerIdentityListFilter getFilter() {
        if (filter == null)
            filter = new CryptoCustomerIdentityListFilter(dataSet, this);

        return filter;
    }
}
