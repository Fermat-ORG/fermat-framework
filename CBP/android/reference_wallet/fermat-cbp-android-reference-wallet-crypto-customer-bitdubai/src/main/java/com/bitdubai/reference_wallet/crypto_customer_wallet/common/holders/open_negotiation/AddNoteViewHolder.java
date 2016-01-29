package com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders.open_negotiation;

import android.view.View;

import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;

/**
 * Created by Yordin Alayn on 29.01.16.
 */
public class AddNoteViewHolder extends FermatViewHolder implements View.OnClickListener {

    OnNoteButtonButtonsClickListener listener;

    public AddNoteViewHolder(View itemView){
        super(itemView);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ccw_open_negotiation_cancel_note) {
            listener.onCancelNoteButtonClicked();
        } else if (view.getId() == R.id.ccw_open_negotiation_save_note) {
            listener.onSaveNoteButtonClicked();
        }
    }

    public void setListener(OnNoteButtonButtonsClickListener listener){
        this.listener = listener;
    }

    public interface OnNoteButtonButtonsClickListener {
        void onCancelNoteButtonClicked();

        void onSaveNoteButtonClicked();
    }
}
