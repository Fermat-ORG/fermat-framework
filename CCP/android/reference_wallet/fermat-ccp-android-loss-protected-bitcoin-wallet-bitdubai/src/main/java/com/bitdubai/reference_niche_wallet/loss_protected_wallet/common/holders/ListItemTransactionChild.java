package com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.holders;

import android.view.View;
import android.widget.TextView;

import com.bitdubai.fermat_android_api.ui.expandableRecicler.ChildViewHolder;
import com.bitdubai.android_fermat_ccp_loss_protected_wallet_bitcoin.R;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.TransactionType;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWalletTransaction;

/**
 * Created by Gian Barboza on 16/05/16.
 */
public class ListItemTransactionChild extends ChildViewHolder {

    private TextView transaction_user;
    private TextView transaction_note;

    /**
     * ListItemTransactionChild constructor.
     *
     * @param itemView The {@link View} being hosted in this ViewHolder
     */
    public ListItemTransactionChild(View itemView) {
        super(itemView);
        transaction_user = (TextView) itemView.findViewById(R.id.transaction_user);
        transaction_note = (TextView) itemView.findViewById(R.id.transaction_note);
    }

  public void bind(LossProtectedWalletTransaction data){
      String contactName = "";
      //involved actor is not a wallet contact
      if(data.getInvolvedActor() != null)
      {
       contactName = data.getInvolvedActor().getName();
      }

      if (data.getTransactionType() == TransactionType.CREDIT)
        transaction_user.setText("From: "+contactName);
      else transaction_user.setText("To: "+contactName);


      transaction_note.setText("Note: "+data.getMemo());

  }
}
