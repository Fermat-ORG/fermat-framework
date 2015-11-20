package com.bitdubai.fermat_dmp_android_clone_reference_nich_wallet.common.adapters;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_dmp_android_clone_reference_nich_wallet.R;
import com.bitdubai.fermat_dmp_android_clone_reference_nich_wallet.common.Views.EntryItem;
import com.bitdubai.fermat_dmp_android_clone_reference_nich_wallet.common.Views.Item;
import com.bitdubai.fermat_dmp_android_clone_reference_nich_wallet.common.Views.SectionItem;
import com.bitdubai.fermat_dmp_android_clone_reference_nich_wallet.common.enums.ShowMoneyType;
import com.bitdubai.fermat_dmp_android_clone_reference_nich_wallet.common.utils.WalletUtils;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Matias Furszyfer on 2015.08.28..
 */
public class TransactionAdapter extends FermatAdapter<Item, TransactionAdapter.TransactionItemViewHolder> {

    private final int HEADER_ROW=0;
    private final int TRANSACTION_ROW=1;


    private int typeOfItemToInflate=HEADER_ROW;

    public TransactionAdapter(Context context, ArrayList<Item> dataSet) {
        super(context, dataSet);
    }

    @Override
    protected TransactionItemViewHolder createHolder(View itemView, int type) {
        return new TransactionItemViewHolder(itemView);
    }

    /**
     * Get custom layout to use it.
     *
     * @return int Layout Resource id: Example: R.layout.row_item
     */
    @Override
    protected int getCardViewResource() {
        return R.layout.wallets_bitcoin_fragment_transactions_list_items2;
    }


    /**
     * Bind ViewHolder
     *
     * @param holder   ViewHolder object
     * @param data     Object data to render
     * @param position position to render
     */
    @Override
    protected void bindHolder(TransactionItemViewHolder holder, Item data, int position) {
        if(data.isSection()){
            typeOfItemToInflate = HEADER_ROW;
            holder.sectionView.setText(((SectionItem)data).getTitle());
            holder.sectionView.setVisibility(View.VISIBLE);
            holder.linear_layout_transaction.setVisibility(View.GONE);
            //sectionView.setText(si.getTitle());
        }else{
            EntryItem entryItem = (EntryItem) data;
            holder.textView_contact_name.setText(entryItem.cryptoWalletTransaction.getInvolvedActor().getName());
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
            holder.txtView_time.setText(sdf.format(entryItem.cryptoWalletTransaction.getTimestamp()));
            holder.txtView_amount.setText(WalletUtils.formatBalanceString(entryItem.cryptoWalletTransaction.getAmount(), ShowMoneyType.BITCOIN.getCode()));

            byte[] image = entryItem.cryptoWalletTransaction.getInvolvedActor().getPhoto();
            if(image!=null){

            }else{
                //RoundedDrawable roundedDrawable = new RoundedDrawable(BitmapFactory.decodeByteArray(image, 0, image.length), holder.imageView_contact);
                holder.imageView_contact.setImageResource(R.drawable.person12);
            }
        }






    }

    class TransactionItemViewHolder extends FermatViewHolder implements View.OnClickListener {

        /**
         * for header row
         */
        TextView sectionView;

        /**
         * for section row
         */
        ImageView imageView_contact;
        FermatTextView textView_contact_name;
        FermatTextView txtView_time;
        FermatTextView txtView_amount;
        FermatTextView textView_type;
        LinearLayout linear_layout_transaction;

        protected TransactionItemViewHolder(View itemView) {
            super(itemView);

            sectionView = (TextView) itemView.findViewById(R.id.textView_header);
            linear_layout_transaction = (LinearLayout) itemView.findViewById(R.id.linear_layout_transaction);
            imageView_contact = (ImageView) itemView.findViewById(R.id.imageView_contact);
            textView_contact_name = (FermatTextView) itemView.findViewById(R.id.textView_contact_name);
            txtView_time = (FermatTextView) itemView.findViewById(R.id.textView_time);
            txtView_amount = (FermatTextView) itemView.findViewById(R.id.textView_amount);
            textView_type = (FermatTextView) itemView.findViewById(R.id.textView_type);

            //this.itemView.setOnClickListener(this);
            //txtView_amount.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //TODO: TESTING...
            //int layoutPosition = getLayoutPosition();
            //int adapterPosition = getAdapterPosition();

//            if (view.getId() == R.id.wallet_installation_status) {
//                Toast.makeText(context,
//                        "Click en txtView_amount. LayoutPosition = " + layoutPosition + " - AdapterPosition = " + adapterPosition,
//                        Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(context,
//                        "Click en itemView. LayoutPosition = " + layoutPosition + " - AdapterPosition = " + adapterPosition,
//                        Toast.LENGTH_SHORT).show();
//            }
        }
    }
}
