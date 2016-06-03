package com.bitdubai.reference_niche_wallet.fermat_wallet.common.holders;

import android.content.res.Resources;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bitdubai.android_fermat_ccp_wallet_fermat.R;
import com.bitdubai.fermat_android_api.ui.expandableRecicler.ChildViewHolder;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.TransactionState;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.interfaces.FermatWalletModuleTransaction;
import com.bitdubai.reference_niche_wallet.fermat_wallet.common.enums.ShowMoneyType;

import java.text.SimpleDateFormat;
import java.util.Locale;

import static com.bitdubai.reference_niche_wallet.fermat_wallet.common.utils.WalletUtils.formatBalanceString;

/**
 * Created by Matias Furszyfer on 28/10/15.
 */
public class TransactionViewHolder extends ChildViewHolder {

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
    public TransactionViewHolder(View itemView) {
        super(itemView);

        this.itemView = itemView;
        res = itemView.getResources();
        container_sub_item = (LinearLayout) itemView.findViewById(R.id.container_sub_item);

        txt_amount = (TextView) itemView.findViewById(R.id.txt_amount);
        txt_notes = (TextView) itemView.findViewById(R.id.txt_notes);
        txt_time = (TextView) itemView.findViewById(R.id.txt_time);
    }

    public void bind(FermatWalletModuleTransaction fermatWalletTransaction) {

        if (fermatWalletTransaction.getActorFromPublicKey() != null){
            txt_amount.setText(formatBalanceString(fermatWalletTransaction.getAmount(), ShowMoneyType.BITCOIN.getCode()) + " BTC");
            if(fermatWalletTransaction.getTransactionState().equals(TransactionState.REVERSED))
                txt_notes.setText((fermatWalletTransaction.getMemo()==null) ? "No information" : fermatWalletTransaction.getMemo() + "(Reversed)");
                else
                    txt_notes.setText((fermatWalletTransaction.getMemo()==null) ? "No information" : fermatWalletTransaction.getMemo());
            SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy HH:mm", Locale.US);
            txt_time.setText(sdf.format(fermatWalletTransaction.getTimestamp())+ " hs");
        }else{
            container_sub_item.setVisibility(View.GONE);
        }
    }

}
