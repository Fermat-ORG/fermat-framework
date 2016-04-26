package com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_loss_protected_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.enums.CryptoPaymentState;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedPaymentRequest;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWalletTransaction;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.holders.ChunckValuesHistoryItemViewHolder;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.holders.PaymentHistoryItemViewHolder;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.utils.onRefreshList;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.session.LossProtectedWalletSession;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import static com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.utils.WalletUtils.formatBalanceString;
import static com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.utils.WalletUtils.showMessage;

/**
 * Created by Matias Furszyfer on 2015.09.30..
 */
public class ChunckValuesHistoryAdapter extends FermatAdapter<LossProtectedWalletTransaction, ChunckValuesHistoryItemViewHolder>  {

    private onRefreshList onRefreshList;
    // private View.OnClickListener mOnClickListener;
    LossProtectedWallet cryptoWallet;
    LossProtectedWalletSession lossProtectedWalletSession;
    Typeface tf;
    protected ChunckValuesHistoryAdapter(Context context) {
        super(context);
    }

    public ChunckValuesHistoryAdapter(Context context, List<LossProtectedWalletTransaction> dataSet, LossProtectedWallet cryptoWallet, LossProtectedWalletSession lossProtectedWalletSession, onRefreshList onRefresh) {
        super(context, dataSet);
        this.cryptoWallet = cryptoWallet;
        this.lossProtectedWalletSession =lossProtectedWalletSession;
        //this.mOnClickListener = onClickListener;
        this.onRefreshList = onRefresh;
        tf = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
    }



    /**
     * Create a new holder instance
     *
     * @param itemView View object
     * @param type     int type
     * @return ViewHolder
     */
    @Override
    protected ChunckValuesHistoryItemViewHolder createHolder(View itemView, int type) {
        return new ChunckValuesHistoryItemViewHolder(itemView);
    }

    /**
     * Get custom layout to use it.
     *
     * @return int Layout Resource id: Example: R.layout.row_item
     */
    @Override
    protected int getCardViewResource() {
        return R.layout.chunck_values_history_row;
    }

    /**
     * Bind ViewHolder
     *
     * @param holder   ViewHolder object
     * @param data     Object data to render
     * @param position position to render
     */
    @Override
    protected void bindHolder(final ChunckValuesHistoryItemViewHolder holder, final LossProtectedWalletTransaction data, int position) {


        holder.getTxt_amount().setText(formatBalanceString(data.getAmount(), lossProtectedWalletSession.getTypeAmount()));
        holder.getTxt_amount().setTypeface(tf) ;

        if (data.getExchangeRate() <= lossProtectedWalletSession.getActualExchangeRate()){
            holder.getTxt_amount().setTextColor(Color.parseColor("#FF0000"));
        }else {
            holder.getTxt_amount().setTextColor(Color.parseColor("#7FBA00"));
        }


        holder.getTxt_exchange_rate().setText("Exchange Rate: 1 BTC = " + data.getExchangeRate());


    }



}
