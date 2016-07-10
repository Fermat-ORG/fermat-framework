package com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;

import com.bitdubai.android_fermat_ccp_loss_protected_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.interfaces.BitcoinLossProtectedWalletSpend;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantListCryptoWalletIntraUserIdentityException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantGetCryptoLossProtectedWalletException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantListLossProtectedTransactionsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWalletIntraUserIdentity;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWalletTransaction;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.enums.ShowMoneyType;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.holders.ChunckValuesHistoryItemViewHolder;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.utils.WalletUtils;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.utils.onRefreshList;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.session.SessionConstant;


import java.util.List;
import java.util.UUID;

/**
 * Created by Matias Furszyfer on 2015.09.30..
 */
public class ChunckValuesHistoryAdapter extends FermatAdapter<LossProtectedWalletTransaction, ChunckValuesHistoryItemViewHolder>  {

    private onRefreshList onRefreshList;
    // private View.OnClickListener mOnClickListener;
    LossProtectedWallet manager;
    ReferenceAppFermatSession<LossProtectedWallet> lossProtectedWalletSession;
    Typeface tf;
    private ShowMoneyType typeAmountSelected = ShowMoneyType.BITCOIN;
    /**
     * DATA
     * **/
    private List<BitcoinLossProtectedWalletSpend> listBitcoinLossProtectedWalletSpend;
    private LossProtectedWalletTransaction transaction;
    protected ChunckValuesHistoryAdapter(Context context) {
        super(context);
    }

    public ChunckValuesHistoryAdapter(Context context, List<LossProtectedWalletTransaction> dataSet, LossProtectedWallet manager, ReferenceAppFermatSession<LossProtectedWallet> lossProtectedWalletSession, onRefreshList onRefresh) {
        super(context, dataSet);
        this.manager = manager;
        this.lossProtectedWalletSession =lossProtectedWalletSession;
        //this.mOnClickListener = onClickListener;
        this.onRefreshList = onRefresh;
        tf = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");

        if(lossProtectedWalletSession.getData(SessionConstant.TYPE_BALANCE_SELECTED) != null)
            typeAmountSelected = (ShowMoneyType)lossProtectedWalletSession.getData(SessionConstant.TYPE_AMOUNT_SELECTED);
        else
            lossProtectedWalletSession.setData(SessionConstant.TYPE_AMOUNT_SELECTED, typeAmountSelected);
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
        final int MAX_DECIMAL_EXCHANGE_RATE_AMOUNT = 2;
        final int MIN_DECIMAL_EXCHANGE_RATE_AMOUNT = 2;
        final int MAX_DECIMAL_FOR_BALANCE_TRANSACTION = 8;
        final int MIN_DECIMAL_FOR_BALANCE_TRANSACTION = 2;

        ActiveActorIdentityInformation intraUserLoginIdentity = null;
        try {
            intraUserLoginIdentity =  manager.getSelectedActorIdentity();

        } catch (CantGetSelectedActorIdentityException e) {
            e.printStackTrace();
        } catch (ActorIdentityNotSelectedException e) {
            e.printStackTrace();
        }
        String intraUserPk = null;
        if (intraUserLoginIdentity != null) {
            intraUserPk = intraUserLoginIdentity.getPublicKey();
        }

        //Get transaction data
        try {
            transaction = manager.getTransaction(
                    data.getTransactionId(),
                    lossProtectedWalletSession.getAppPublicKey(),
                    intraUserPk);
        } catch (CantListLossProtectedTransactionsException e) {
            e.printStackTrace();
        }

        final int percentage = getSpendingPercentage(transaction);

        holder.getTxt_amount().setText(
                WalletUtils.formatBalanceStringWithDecimalEntry(
                        data.getAmount(),
                        MAX_DECIMAL_FOR_BALANCE_TRANSACTION,
                        MIN_DECIMAL_FOR_BALANCE_TRANSACTION,
                        typeAmountSelected.getCode()) + "  (" + percentage + "% Spend)");
        holder.getTxt_amount().setTypeface(tf);

        if ((double)lossProtectedWalletSession.getData(SessionConstant.ACTUAL_EXCHANGE_RATE) >= data.getExchangeRate())
            holder.getTxt_amount().setTextColor(Color.parseColor("#7FBA00"));
        else
            holder.getTxt_amount().setTextColor(Color.parseColor("#FF0000"));

        holder.getTxt_exchange_rate().setText("Exchange Rate: 1 BTC = "+
                WalletUtils.formatAmountStringWithDecimalEntry(
                        data.getExchangeRate(),
                        MAX_DECIMAL_EXCHANGE_RATE_AMOUNT,
                        MIN_DECIMAL_EXCHANGE_RATE_AMOUNT));

    }

    private double getTotalSpent(UUID transactionId){

        double spendingAmount = 0;
        try {

            listBitcoinLossProtectedWalletSpend = manager.listSpendingBlocksValue(
                    lossProtectedWalletSession.getAppPublicKey(),
                    transactionId);

            for (BitcoinLossProtectedWalletSpend spendingData : listBitcoinLossProtectedWalletSpend) {
                if (spendingData.getAmount() != 0){
                    spendingAmount += Double.parseDouble(WalletUtils.formatBalanceString(spendingData.getAmount(), ShowMoneyType.BITCOIN.getCode()));
                }
            }
            return spendingAmount;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    private int getSpendingPercentage(LossProtectedWalletTransaction transaction){


        double spendingAmount = 0,totalAmount = 0;
        int totalSpendingPercentage = 0;
        try {
            //call spending list
            spendingAmount = getTotalSpent(transaction.getTransactionId());

            totalAmount = Double.parseDouble(WalletUtils.formatBalanceString(transaction.getAmount(), ShowMoneyType.BITCOIN.getCode()));

            totalSpendingPercentage = (int) ((spendingAmount * 100)/totalAmount);

            return totalSpendingPercentage;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }



}
