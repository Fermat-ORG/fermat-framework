package com.bitdubai.sub_app.crypto_customer_identity.util;

import android.view.View;
import android.widget.Filter;

import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityInformation;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.interfaces.CryptoCustomerIdentityInformation;
import com.bitdubai.sub_app.crypto_customer_identity.common.adapters.CryptoCustomerIdentityInfoAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Filter Class for the Items in the list of {@link CryptoBrokerIdentityInformation}
 */
public class CryptoCustomerIdentityListFilter extends Filter {
    private List<CryptoCustomerIdentityInformation> listOfIdentities;
    private CryptoCustomerIdentityInfoAdapter adapter;
    private String constraint;
    private View viewToShow;
    private View viewToHide;

    public CryptoCustomerIdentityListFilter(List<CryptoCustomerIdentityInformation> listOfIdentities, CryptoCustomerIdentityInfoAdapter adapter) {
        this.listOfIdentities = listOfIdentities;
        this.adapter = adapter;
        constraint = "";

        viewToShow = viewToHide = null;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults result = new FilterResults();

        this.constraint = (constraint == null) ? "" : constraint.toString();

        if (constraint != null && constraint.length() > 0) {
            String constraintStr = constraint.toString().toLowerCase(Locale.getDefault());
            ArrayList<CryptoCustomerIdentityInformation> filterItems = new ArrayList<>();

            synchronized (this) {
                for (CryptoCustomerIdentityInformation item : listOfIdentities) {
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
        if (results.count == 0) {
            switchViewsVisibility(true);
        } else {
            switchViewsVisibility(false);

            ArrayList<CryptoCustomerIdentityInformation> filteredList = (ArrayList<CryptoCustomerIdentityInformation>) results.values;
            adapter.changeDataSet(filteredList);
        }
    }

    /**
     * @return the string used as to apply the filter
     */
    public String getConstraint() {
        return constraint;
    }

    /**
     * Set the views that are going to be shown and hided when there is no match for the filter
     *
     * @param viewToShow the view to be shown (can be a layout with a text of "No Matches")
     * @param viewToHide the view to be hided (should be the view where the adapter is set)
     */
    public void setNoMatchViews(View viewToShow, View viewToHide) {
        this.viewToShow = viewToShow;
        this.viewToHide = viewToHide;
    }

    private void switchViewsVisibility(boolean show) {
        if (viewToShow != null && show)
            viewToShow.setVisibility(View.VISIBLE);

        if (viewToHide != null && show)
            viewToHide.setVisibility(View.GONE);

        if (viewToShow != null && !show)
            viewToShow.setVisibility(View.GONE);

        if (viewToHide != null && !show)
            viewToHide.setVisibility(View.VISIBLE);
    }
}
