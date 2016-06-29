package com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;

import com.bitdubai.android_fermat_ccp_loss_protected_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.interfaces.BitcoinLossProtectedWalletSpend;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWallet;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.enums.ShowMoneyType;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.holders.ChunckValuesDetailItemViewHolder;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.utils.WalletUtils;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.utils.onRefreshList;


import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import static com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.utils.WalletUtils.formatBalanceStringWithDecimalEntry;

/**
 * Created by root on 12/04/16.
 */
public class ChunckValuesDetailAdapter extends FermatAdapter<BitcoinLossProtectedWalletSpend,ChunckValuesDetailItemViewHolder> {
    private onRefreshList onRefresh;
    private LossProtectedWallet cryptoWallet;
    private ReferenceAppFermatSession<LossProtectedWallet> lossProtectedWalletSession;
    private Typeface tf;


    public ChunckValuesDetailAdapter(Context context, List<BitcoinLossProtectedWalletSpend> dataSet, LossProtectedWallet cryptoWallet, ReferenceAppFermatSession<LossProtectedWallet> lossProtectedWalletSession, onRefreshList onRefresh) {
        super(context, dataSet);
        this.cryptoWallet = cryptoWallet;
        this.lossProtectedWalletSession = lossProtectedWalletSession;
        this.onRefresh = onRefresh;
        tf = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
    }

    @Override
    protected ChunckValuesDetailItemViewHolder createHolder(View itemView, int type) {
        return new ChunckValuesDetailItemViewHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {return R.layout.chunck_detail_row;}

    @Override
    protected void bindHolder(ChunckValuesDetailItemViewHolder holder, BitcoinLossProtectedWalletSpend data, int position) {

        final int MAX_DECIMAL_EXCHANGE_RATE_AMOUNT = 2;
        final int MIN_DECIMAL_EXCHANGE_RATE_AMOUNT = 2;
        final int MAX_DECIMAL_FOR_BALANCE_TRANSACTION = 8;
        final int MIN_DECIMAL_FOR_BALANCE_TRANSACTION = 2;
        //Get date and set a FormatDate for Data value
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy hh:mm a ", Locale.US);
        holder.getDate().setText(sdf.format(data.getTimestamp()));

        holder.getAmountBalance().setText(
                formatBalanceStringWithDecimalEntry(
                        data.getAmount(),
                        MAX_DECIMAL_FOR_BALANCE_TRANSACTION,
                        MIN_DECIMAL_FOR_BALANCE_TRANSACTION,
                        ShowMoneyType.BITCOIN.getCode())+" BTC");

        holder.getExchangeRate().setText("1 BTC = U$D "+
                WalletUtils.formatAmountStringWithDecimalEntry(
                        data.getExchangeRate(),
                        MAX_DECIMAL_EXCHANGE_RATE_AMOUNT,
                        MIN_DECIMAL_EXCHANGE_RATE_AMOUNT));





    }
}
