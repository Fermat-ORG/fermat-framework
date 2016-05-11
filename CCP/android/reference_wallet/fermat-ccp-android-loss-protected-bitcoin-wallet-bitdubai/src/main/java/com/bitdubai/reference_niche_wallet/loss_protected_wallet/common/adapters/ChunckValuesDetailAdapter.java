package com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;

import com.bitdubai.android_fermat_ccp_loss_protected_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_bnk_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.interfaces.BitcoinLossProtectedWalletSpend;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWallet;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.enums.ShowMoneyType;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.holders.ChunckValuesDetailItemViewHolder;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.utils.onRefreshList;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.session.LossProtectedWalletSession;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import static com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.utils.WalletUtils.formatAmountString;
import static com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.utils.WalletUtils.formatBalanceString;
import static com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.utils.WalletUtils.formatExchangeRateString;
import static com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.utils.WalletUtils.showMoneyType;

/**
 * Created by root on 12/04/16.
 */
public class ChunckValuesDetailAdapter extends FermatAdapter<BitcoinLossProtectedWalletSpend,ChunckValuesDetailItemViewHolder> {
    private onRefreshList onRefresh;
    private LossProtectedWallet cryptoWallet;
    private LossProtectedWalletSession lossProtectedWalletSession;
    private Typeface tf;


    public ChunckValuesDetailAdapter(Context context, List<BitcoinLossProtectedWalletSpend> dataSet, LossProtectedWallet cryptoWallet, LossProtectedWalletSession lossProtectedWalletSession, onRefreshList onRefresh) {
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
        //Get date and set a FormatDate for Data value
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
        holder.getDate().setText(sdf.format(data.getTimestamp()));

        holder.getAmountBalance().setText(formatBalanceString(data.getAmount(),ShowMoneyType.BITCOIN.getCode())+" BTC");

        holder.getExchangeRate().setText("1 BTC = U$D "+data.getExchangeRate());





    }
}
