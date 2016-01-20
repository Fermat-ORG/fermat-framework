package com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders.SingleDeletableItemViewHolder;

import java.util.List;

/**
 * Created by nelson on 30/12/15.
 */
public abstract class SingleDeletableItemAdapter<OBJECT, VIEW_HOLDER extends SingleDeletableItemViewHolder>
        extends FermatAdapter<OBJECT, VIEW_HOLDER> {

    protected OnDeleteButtonClickedListener deleteButtonListener;

    protected SingleDeletableItemAdapter(Context context) {
        super(context);
    }

    protected SingleDeletableItemAdapter(Context context, List dataSet) {
        super(context, dataSet);
    }

    public void setDeleteButtonListener(OnDeleteButtonClickedListener listener) {
        deleteButtonListener = listener;
    }

    public interface OnDeleteButtonClickedListener<T> {
        void deleteButtonClicked(T data, int position);
    }

    @Override
    protected void bindHolder(VIEW_HOLDER holder, final OBJECT data, final int position) {
        holder.bind(data);

        if (deleteButtonListener != null) {
            holder.getCloseButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteButtonListener.deleteButtonClicked(data, position);
                }
            });
        }
    }
}
