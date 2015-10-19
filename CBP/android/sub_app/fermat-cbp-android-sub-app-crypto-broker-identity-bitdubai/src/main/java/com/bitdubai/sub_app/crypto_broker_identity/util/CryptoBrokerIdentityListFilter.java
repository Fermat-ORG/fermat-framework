package com.bitdubai.sub_app.crypto_broker_identity.util;

import android.widget.Filter;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityInformation;
import com.bitdubai.sub_app.crypto_broker_identity.common.adapters.CryptoBrokerIdentityInfoAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Filter Class for the Items in the list of {@link CryptoBrokerIdentityInformation}
 */
public class CryptoBrokerIdentityListFilter extends Filter {
    private List<CryptoBrokerIdentityInformation> listOfIdentities;
    private CryptoBrokerIdentityInfoAdapter adapter;

    public CryptoBrokerIdentityListFilter(List<CryptoBrokerIdentityInformation> listOfIdentities, CryptoBrokerIdentityInfoAdapter adapter) {
        this.listOfIdentities = listOfIdentities;
        this.adapter = adapter;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults result = new FilterResults();

        if (constraint != null && constraint.toString().length() > 0) {
            String constraintStr = constraint.toString().toLowerCase(Locale.getDefault());
            ArrayList<CryptoBrokerIdentityInformation> filterItems = new ArrayList<>();

            synchronized (this) {
                for (CryptoBrokerIdentityInformation item : listOfIdentities) {
                    String alias = item.getAlias().toLowerCase(Locale.getDefault());
                    if (alias.contains(constraintStr))
                        filterItems.add(item);
                }
                result.count = filterItems.size();
                result.values = filterItems;
            }

        } else {
            synchronized (this) {
                result.count = listOfIdentities.size();
                result.values = listOfIdentities;
            }
        }
        return result;
    }


    @Override
    @SuppressWarnings("unchecked")
    protected void publishResults(CharSequence constraint, FilterResults results) {
        ArrayList<CryptoBrokerIdentityInformation> filteredList = (ArrayList<CryptoBrokerIdentityInformation>) results.values;
        adapter.changeDataSet(filteredList);
    }

}
