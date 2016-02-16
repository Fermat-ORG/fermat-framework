package com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters;

import android.content.Context;
import android.widget.CompoundButton;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders.SingleCheckableItemViewHolder;

import java.util.List;

/**
 * Created by nelson on 30/12/15.
 */
public abstract class SingleCheckableItemAdapter<OBJECT, VIEW_HOLDER extends SingleCheckableItemViewHolder>
        extends FermatAdapter<OBJECT, VIEW_HOLDER> {

    protected OnCheckboxClickedListener<OBJECT> deleteButtonListener;

    protected SingleCheckableItemAdapter(Context context) {
        super(context);
    }

    protected SingleCheckableItemAdapter(Context context, List<OBJECT> dataSet) {
        super(context, dataSet);
    }

    public void setCheckboxListener(OnCheckboxClickedListener<OBJECT> listener) {
        deleteButtonListener = listener;
    }

    public interface OnCheckboxClickedListener<T> {
        void checkedChanged(boolean isChecked, T data, int position);
    }

    @Override
    protected void bindHolder(VIEW_HOLDER holder, final OBJECT data, final int position) {
        holder.bind(data);

        if (deleteButtonListener != null) {
            holder.getCheckBox().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    deleteButtonListener.checkedChanged(isChecked, data, position);
                }
            });
        }
    }
}
