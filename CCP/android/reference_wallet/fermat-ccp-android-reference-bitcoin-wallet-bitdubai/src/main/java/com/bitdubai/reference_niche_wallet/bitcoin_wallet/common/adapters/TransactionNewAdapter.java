package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FontType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetActiveLoginIdentityException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantGetActorTransactionHistoryException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantListTransactionsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.ActorTransactionSummary;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletTransaction;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;

import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.holders.TransactionItemViewHolder;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.ReferenceWalletSession;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.WalletUtils.formatBalanceString;

/**
 * Created on 22/08/15.
 * Adapter para el RecliclerView del MainFragment que muestra el catalogo de Wallets disponibles en el store
 *
 * @author Matias Furszyfer
 */
public class TransactionNewAdapter extends FermatAdapter<CryptoWalletTransaction, TransactionItemViewHolder> {


    CryptoWallet cryptoWallet;
    ReferenceWalletSession referenceWalletSession;

    protected TransactionNewAdapter(Context context) {
        super(context);
    }

    public TransactionNewAdapter(Context context, List<CryptoWalletTransaction> dataSet,CryptoWallet cryptoWallet,ReferenceWalletSession referenceWalletSession) {
        super(context, dataSet);
        this.cryptoWallet = cryptoWallet;
        this.referenceWalletSession =referenceWalletSession;
    }

    public void setOnClickListerAcceptButton(View.OnClickListener onClickListener){

    }

    public void setOnClickListerRefuseButton(View.OnClickListener onClickListener){

    }

    /**
     * Create a new holder instance
     *
     * @param itemView View object
     * @param type     int type
     * @return ViewHolder
     */
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
        return R.layout.receive_list_item_row;
    }

    /**
     * Bind ViewHolder
     *
     * @param holder   ViewHolder object
     * @param data     Object data to render
     * @param position position to render
     */
    @Override
    protected void bindHolder(final TransactionItemViewHolder holder, final CryptoWalletTransaction data, int position) {

        try {
            Bitmap bitmap= null;
            //if(data.getInvolvedActor().getPhoto()!=null){
                 bitmap  = BitmapFactory.decodeByteArray(data.getInvolvedActor().getPhoto(),0,data.getInvolvedActor().getPhoto().length);
                 holder.getContactIcon().setImageBitmap(bitmap);
           // }else{
                holder.getContactIcon().setImageResource(R.drawable.mati_profile);
           // }


            holder.getTxt_amount().setText(formatBalanceString(data.getAmount(), referenceWalletSession.getTypeAmount()));

            holder.getTxt_contactName().setText(data.getInvolvedActor().getName());//data.getContact().getActorName());

            holder.getTxt_notes().setText(data.getMemo());


            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
            holder.getTxt_time().setText(sdf.format(data.getTimestamp()));

            ActorTransactionSummary actorTransactionSummary = null;

            try{
                actorTransactionSummary = cryptoWallet.getActorTransactionHistory(BalanceType.getByCode(referenceWalletSession.getBalanceTypeSelected()), referenceWalletSession.getWalletSessionType().getWalletPublicKey(), data.getInvolvedActor().getActorPublicKey());

            } catch (CantGetActorTransactionHistoryException e) {
                e.printStackTrace();
            }

            if(actorTransactionSummary!=null) {
                holder.getTxt_total_number_transactions().setText(String.valueOf(actorTransactionSummary.getReceivedTransactionsNumber()));

                holder.getTxt_total_balance().setText(formatBalanceString(actorTransactionSummary.getReceivedAmount(), referenceWalletSession.getTypeAmount()));
            }



            holder.getImageView_see_all_transactions().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (holder.getListView_transactions().getVisibility() == View.VISIBLE) {
                        holder.getListView_transactions().setVisibility(View.GONE);
                    } else {
                        holder.getListView_transactions().setVisibility(View.VISIBLE);
                        List<CryptoWalletTransaction> lstCryptoTransactions = new ArrayList<CryptoWalletTransaction>();
                        try {
                            lstCryptoTransactions = cryptoWallet.listTransactionsByActor(
                                    BalanceType.valueOf(referenceWalletSession.getBalanceTypeSelected()),
                                    referenceWalletSession.getWalletSessionType().getWalletPublicKey(),
                                    data.getInvolvedActor().getActorPublicKey(),
                                    referenceWalletSession.getIntraUserModuleManager().getActiveIntraUserIdentity().getPublicKey(),
                                    50, 0
                            );
                            lstCryptoTransactions.add(data);
                        } catch (CantListTransactionsException e) {
                            e.printStackTrace();
                        } catch (CantGetActiveLoginIdentityException e) {
                            e.printStackTrace();
                        }
                        TransactionAdapter transactionAdapter = new TransactionAdapter(context, lstCryptoTransactions);
                        holder.getListView_transactions().setAdapter(transactionAdapter);
                        transactionAdapter.notifyDataSetChanged();
                    }


                }
            });

        }
        catch (Exception e) {
            e.printStackTrace();
        }



    }

//    public class TransactionAdapter extends ArrayAdapter<CryptoWalletTransaction> {
//
//        public TransactionAdapter(Context context, List<CryptoWalletTransaction> lstTransactions) {
//            super(context, 0, lstTransactions);
//
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            // Get the data item for this position
//            CryptoWalletTransaction cryptoWalletTransaction = getItem(position);
//            // Check if an existing view is being reused, otherwise inflate the view
//            if (convertView == null) {
//                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view_transactions_expandable_list_row, parent, false);
//            }
//
//            FermatTextView txt_amount = (FermatTextView) convertView.findViewById( R.id.txt_amount);
//
//            txt_amount.setText(formatBalanceString(getItem(position).getAmount(), referenceWalletSession.getTypeAmount()));
//
//            FermatTextView txt_date = (FermatTextView) convertView.findViewById(R.id.txt_date);
//
//            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
//            txt_date.setText(sdf.format(getItem(position).getTimestamp()));
//
//            return convertView;
//        }
//    }


    public class TransactionAdapter extends BaseAdapter {

        private final Context context;
        /**
         * Aquí guardaremos todos los rectángulos que queremos
         * representar en nuestro ListView. Es recomendable usar sistemas
         * con acceso directo por posición, como el ArrayList, un Vector, un Array...
         */
        private List<CryptoWalletTransaction> lstTransactions;

        private int position;

        /**
         * El constructor
         * @param lstTransactions
         */
        public TransactionAdapter(Context context,List<CryptoWalletTransaction> lstTransactions) {
            super();
            this.lstTransactions = lstTransactions;
            this.context=context;

            //Cada vez que cambiamos los elementos debemos noficarlo
           // notifyDataSetChanged();
        }

        /**
         * Este método simplemente nos devuelve el número de
         * elementos de nuestro ListView. Evidentemente es el tamaño del arraylist
         */
        @Override
        public int getCount() {
            return lstTransactions.size();
        }

        /**
         * Este método nos devuele el elemento de una posición determinada.
         * El elemento es el Rectángulo, así que...
         */
        @Override
        public Object getItem(int position) {
            return lstTransactions.get(position);
        }

        /**
         * Aquí tenemos que devolver el ID del elemento. Del ELEMENTO, no del View.
         * Por lo general esto no se usa, así que return 0 y listo.
         */
        @Override
        public long getItemId(int arg0)
        {
            return arg0;
        }

        /**
         * El método más complicado. Aquí tenemos que devolver el View a representar.
         * En este método nos pasan 3 valores. El primero es la posición del elemento,
         * el segundo es el View a utilizar que será uno que ya no esté visible y que
         * lo reutilizaremos, y el último es el ViewGroup, es en nuestro caso, el ListView.
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

//            CryptoWalletTransaction cryptoWalletTransaction = (CryptoWalletTransaction)this.getItem(position);
//            // Check if an existing view is being reused, otherwise inflate the view
//            if (convertView == null) {
//                convertView = LayoutInflater.from(context).inflate(R.layout.list_view_transactions_expandable_list_row, parent, false);
//            }
//
//                FermatTextView txt_amount = (FermatTextView) convertView.findViewById(R.id.txt_amount);
//
//                txt_amount.setText(formatBalanceString(cryptoWalletTransaction.getAmount(), referenceWalletSession.getTypeAmount()));
//
//                FermatTextView txt_date = (FermatTextView) convertView.findViewById(R.id.txt_date);
//
//                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
//                txt_date.setText(sdf.format(cryptoWalletTransaction.getTimestamp()));
//
//
//            return convertView;

            ViewHolder holder;
            if(convertView==null){
                convertView = LayoutInflater.from(context).inflate(R.layout.list_view_transactions_expandable_list_row, parent, false);
                holder = new ViewHolder();
                holder.txt_amount = (FermatTextView) convertView.findViewById(R.id.txt_amount);
                holder.txt_date = (FermatTextView) convertView.findViewById(R.id.txt_date);

                holder.txt_amount.setFont(FontType.ROBOTO_REGULAR);
                holder.txt_date.setFont(FontType.ROBOTO_REGULAR);

                convertView.setTag(holder);
            }else{
                holder = (ViewHolder)  convertView.getTag();
            }

            //holder.txt_amount.setTypeface(font);
            //holder.txt_date.setTypeface(font);

            CryptoWalletTransaction cryptoWalletTransaction = (CryptoWalletTransaction)this.getItem(position);
            holder.txt_amount.setText(formatBalanceString(cryptoWalletTransaction.getAmount(), referenceWalletSession.getTypeAmount()));
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
            holder.txt_date.setText(sdf.format(cryptoWalletTransaction.getTimestamp()));

            holder.txt_amount.setFont(FontType.ROBOTO_REGULAR);
            holder.txt_date.setFont(FontType.ROBOTO_REGULAR);

            convertView.setTag(holder);

            return convertView;



        }

        private class ViewHolder{
            FermatTextView txt_amount;
            FermatTextView txt_date;

            public ViewHolder(){}
        }

    }
}
