package com.bitdubai.reference_niche_wallet.fermat_wallet.common.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.android_fermat_ccp_wallet_fermat.R;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.expandableRecicler.ExpandableRecyclerAdapter;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.interfaces.FermatWalletModuleTransaction;
import com.bitdubai.reference_niche_wallet.fermat_wallet.common.holders.GrouperViewHolder;
import com.bitdubai.reference_niche_wallet.fermat_wallet.common.holders.TransactionViewHolder;
import com.bitdubai.reference_niche_wallet.fermat_wallet.common.models.GrouperItem;

import java.util.List;


public class ReceivetransactionsAdapter
        extends FermatAdapter<FermatWalletModuleTransaction, TransactionViewHolder> {

    private LayoutInflater mInflater;

    private Resources res;

    /**
     * Public primary constructor.
     *
     * @param context        the activity context where the RecyclerView is going to be displayed
     * @param dataSet the list of FermatWalletModuleTransaction to be displayed in the RecyclerView
     */
    public ReceivetransactionsAdapter(Context context, List<FermatWalletModuleTransaction> dataSet, Resources res) {
        super(context,dataSet);
        mInflater = LayoutInflater.from(context);
        this.res = res;
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.fermat_wallet_transaction_item_row;
    }

    @Override
    protected TransactionViewHolder createHolder(View itemView, int type) {
        return null;
    }

    @Override
    protected void bindHolder(TransactionViewHolder holder, FermatWalletModuleTransaction data, int position) {

    }
}