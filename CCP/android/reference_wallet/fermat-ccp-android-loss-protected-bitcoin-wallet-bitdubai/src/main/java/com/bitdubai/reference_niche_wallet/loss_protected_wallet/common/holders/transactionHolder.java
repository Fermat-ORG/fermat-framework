package com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.holders;

import android.content.res.Resources;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bitdubai.android_fermat_ccp_loss_protected_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.ui.expandableRecicler.ChildViewHolder;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.TransactionState;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletTransaction;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWalletTransaction;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.enums.ShowMoneyType;

import java.text.SimpleDateFormat;
import java.util.Locale;

import static com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.utils.WalletUtils.formatBalanceString;

/**
 * Created by Joaquin Carrasquero on 04/05/16.
 */
public class transactionHolder extends ChildViewHolder {
    private final LinearLayout container_sub_item;
    private Resources res;
    private View itemView;

    private TextView txt_amount;
    private TextView txt_notes;
    private TextView txt_time;



    /**
     * Public constructor for the custom child ViewHolder
     *
     * @param itemView the child ViewHolder's view
     */
    public transactionHolder(View itemView) {
        super(itemView);

        this.itemView = itemView;
        res = itemView.getResources();
        container_sub_item = (LinearLayout) itemView.findViewById(R.id.container_sub_item3);

        txt_amount = (TextView) itemView.findViewById(R.id.txt_amount3);
        txt_notes = (TextView) itemView.findViewById(R.id.txt_notes3);
        txt_time = (TextView) itemView.findViewById(R.id.txt_time3);
    }

    public void bind(LossProtectedWalletTransaction lossProtectedWalletTransaction) {

        if (lossProtectedWalletTransaction.getActorFromPublicKey() != null){
            txt_amount.setText(formatBalanceString(lossProtectedWalletTransaction.getAmount(), ShowMoneyType.BITCOIN.getCode())+ " btc");

            if(lossProtectedWalletTransaction.getTransactionState().equals(TransactionState.REVERSED))
                txt_notes.setText((lossProtectedWalletTransaction.getMemo()==null) ? "No information" : lossProtectedWalletTransaction.getMemo() + "(Reversed)");
            else
                txt_notes.setText((lossProtectedWalletTransaction.getMemo()==null) ? "No information" : lossProtectedWalletTransaction.getMemo());
            SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy HH:mm", Locale.US);
            txt_time.setText(sdf.format(lossProtectedWalletTransaction.getTimestamp())+ " hs");
        }else{
            container_sub_item.setVisibility(View.GONE);
        }
    }
}

