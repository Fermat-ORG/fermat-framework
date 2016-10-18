package com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.android_fermat_ccp_loss_protected_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedPaymentRequest;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWallet;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.holders.LoadingMoreViewHolder;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.holders.PaymentHistoryItemViewHolder;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.utils.onRefreshList;

import java.util.List;

/**
 * Created by Matias Furszyfer on 2015.09.30..
 * updated by Andres Abreu aabreu1 2016.09.08..
 */
public class PaymentRequestHistoryAdapter  extends FermatAdapter<LossProtectedPaymentRequest, FermatViewHolder>  {

    private onRefreshList onRefreshList;
    LossProtectedWallet lossProtectedWallet;
    //LossProtectedWalletSettings lossProtectedWalletSettings;
    ReferenceAppFermatSession<LossProtectedWallet> referenceWalletSession;
    //Typeface tf;

    boolean lossProtectedEnabled;
   // BlockchainNetworkType blockchainNetworkType;
   // private String feeLevel = "NORMAL";

    private Context context;

    public static final int DATA_ITEM = 1;
    public static final int LOADING_ITEM = 2;
    private boolean loadingData = true;

    /*protected PaymentRequestHistoryAdapter(Context context) {
        super(context);
    }
    */
    public PaymentRequestHistoryAdapter(Context context, List<LossProtectedPaymentRequest> dataSet, LossProtectedWallet wallet, ReferenceAppFermatSession<LossProtectedWallet> referenceWalletSession,onRefreshList onRefresh) {
        super(context, dataSet);
        this.lossProtectedWallet = wallet;
        this.referenceWalletSession = referenceWalletSession;
        this.onRefreshList = onRefresh;
        this.context = context;
        //tf = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
    }

    /**
     * Create a new holder instance
     *
     * @param itemView View object
     * @param type     int type
     * @return ViewHolder
     */
    @Override
    protected FermatViewHolder createHolder(View itemView, int type) {
        if (type == DATA_ITEM)
            return new PaymentHistoryItemViewHolder(itemView, type, context, lossProtectedWallet, referenceWalletSession, this);
        if (type == LOADING_ITEM)
            return new LoadingMoreViewHolder(itemView, type);
        return null;
    }

    @Override
    public FermatViewHolder onCreateViewHolder(ViewGroup viewGroup, int type) {
        return createHolder(LayoutInflater.from(context).inflate(getCardViewResource(type), viewGroup, false), type);
    }

    /**
     * Get custom layout to use it.
     *
     * @return int Layout Resource id: Example: R.layout.row_item
     */

    protected int getCardViewResource(int type) {
            if (type == DATA_ITEM)
                return R.layout.loss_history_request_row;
            if (type == LOADING_ITEM)
                return R.layout.loading_more_list_item;
            return 0;
    }

        @Override
        protected int getCardViewResource() { return 0 ; }

    @Override
    public void onBindViewHolder(FermatViewHolder holder, int position) {
        if (holder instanceof PaymentHistoryItemViewHolder)
            super.onBindViewHolder(holder, position);

        else if (holder instanceof LoadingMoreViewHolder) {
            final LoadingMoreViewHolder loadingMoreViewHolder = (LoadingMoreViewHolder) holder;
            loadingMoreViewHolder.progressBar.setVisibility(loadingData ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * Bind ViewHolder
     *
     * @param holder   ViewHolder object
     * @param data     Object data to render
     * @param position position to render
     */
    @Override
    protected void bindHolder(FermatViewHolder holder, LossProtectedPaymentRequest data, int position) {
        final PaymentHistoryItemViewHolder paymentHistoryItemViewHolder = (PaymentHistoryItemViewHolder) holder;
        paymentHistoryItemViewHolder.bind(data);
    }

    @Override
    public int getItemViewType(int position) {
        return position == super.getItemCount() ? LOADING_ITEM : DATA_ITEM;
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + 1;
    }

    public boolean isLoadingData() {
        return loadingData;
    }

    public void setLoadingData(boolean loadingData) {
        this.loadingData = loadingData;
    }

    public void refresh()
    {
        onRefreshList.onRefresh();
    }


}
