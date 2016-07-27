package com.bitdubai.sub_app.crypto_broker_identity.common.adapters;

import android.app.Activity;
import android.text.SpannableString;
import android.view.View;
import android.widget.Filterable;

import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.TextUtils;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityInformation;
import com.bitdubai.sub_app.crypto_broker_identity.R;
import com.bitdubai.sub_app.crypto_broker_identity.common.holders.CryptoBrokerIdentityInfoViewHolder;
import com.bitdubai.sub_app.crypto_broker_identity.util.CryptoBrokerIdentityListFilter;

import java.util.ArrayList;

/**
 * Adapter para el RecyclerView del CryptoBrokerIdentityListFragment que muestra la lista de identidades de un broker
 *
 * @author Nelson Ramirez
 */
public class CryptoBrokerIdentityInfoAdapter
        extends FermatAdapter<CryptoBrokerIdentityInformation, CryptoBrokerIdentityInfoViewHolder>
        implements Filterable {

    private ErrorManager errorManager;
    private Activity activity;

    CryptoBrokerIdentityListFilter filter;

    public CryptoBrokerIdentityInfoAdapter(final Activity context,
                                           final ErrorManager errorManager,
                                           final ArrayList<CryptoBrokerIdentityInformation> dataSet) {

        super(context, dataSet);

        this.activity = context;
        this.errorManager = errorManager;
    }

    @Override
    protected void bindHolder(final CryptoBrokerIdentityInfoViewHolder holder,
                              final CryptoBrokerIdentityInformation data,
                              final int position) {

        filter = getFilter();

        SpannableString spannedText = TextUtils.getSpannedText(context.getResources(), R.color.spanned_text, data.getAlias(), filter.getConstraint());

        holder.setText(spannedText);
        holder.setImage(data.getProfileImage());

        if (data.isPublished()) {
            holder.setStatus("Visible");
        } else {
            holder.setStatus("Not visible");
        }

        holder.itemView.setMinimumHeight(800);
    }

    @Override
    protected CryptoBrokerIdentityInfoViewHolder createHolder(View itemView, int type) {
        return new CryptoBrokerIdentityInfoViewHolder(itemView, errorManager, activity);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.crypto_broker_identity_list_item;
    }

    @Override
    public CryptoBrokerIdentityListFilter getFilter() {
        if (filter == null)
            filter = new CryptoBrokerIdentityListFilter(dataSet, this);

        return filter;
    }
}
