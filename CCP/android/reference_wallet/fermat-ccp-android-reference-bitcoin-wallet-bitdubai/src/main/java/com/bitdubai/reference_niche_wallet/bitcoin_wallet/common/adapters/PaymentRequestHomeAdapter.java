package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.util.MemoryUtils;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.PaymentRequest;

import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.holders.PaymentHomeItemViewHolder;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.ReferenceWalletSession;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import static com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.WalletUtils.formatBalanceString;

/**
 * Created by Matias Furszyfer on 2015.09.30..
 */
public class PaymentRequestHomeAdapter extends FermatAdapter<PaymentRequest, PaymentHomeItemViewHolder> implements View.OnClickListener {

    CryptoWallet cryptoWallet;
    ReferenceWalletSession referenceWalletSession;

    Typeface tf;
    protected PaymentRequestHomeAdapter(Context context) {
        super(context);
    }

    public PaymentRequestHomeAdapter(Context context, List<PaymentRequest> dataSet, CryptoWallet cryptoWallet, ReferenceWalletSession referenceWalletSession) {
        super(context, dataSet);
        this.cryptoWallet = cryptoWallet;
        this.referenceWalletSession =referenceWalletSession;

        tf = Typeface.createFromAsset(context.getAssets(), "fonts/roboto.ttf");
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
    protected PaymentHomeItemViewHolder createHolder(View itemView, int type) {
        return new PaymentHomeItemViewHolder(itemView);
    }

    /**
     * Get custom layout to use it.
     *
     * @return int Layout Resource id: Example: R.layout.row_item
     */
    @Override
    protected int getCardViewResource() {
        return R.layout.home_request_row;
    }

    /**
     * Bind ViewHolder
     *
     * @param holder   ViewHolder object
     * @param data     Object data to render
     * @param position position to render
     */
    @Override
    protected void bindHolder(PaymentHomeItemViewHolder holder, PaymentRequest data, int position) {

        holder.getTxt_color_type().setBackgroundColor(paintColorRequestType(data.getType()));


        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;

        //Bitmap bm = BitmapFactory.decodeResource((context.getResources(), R.drawable.mati_profile)), options);


        Bitmap bitmap = MemoryUtils.decodeSampledBitmapFromResource(context.getResources(), R.drawable.mati_profile, holder.getContactIcon().getMaxWidth(), holder.getContactIcon().getMaxHeight());
        holder.getContactIcon().setImageBitmap(bitmap);

        //holder.getContactIcon().setImageBitmap(ImagesUtils.getRoundedShape(BitmapFactory.decodeResource(context.getResources(), R.drawable.mati_profile)));
        //imageView_Item.setImageBitmap(getRoundedShape(BitmapFactory.decodeByteArray(checkBoxListItem.getIntraUserIdentity().getProfileImage(), 0, checkBoxListItem.getIntraUserIdentity().getProfileImage().length)));

        holder.getTxt_amount().setText(formatBalanceString(data.getAmount(), referenceWalletSession.getTypeAmount()));
        holder.getTxt_amount().setTypeface(tf);

        holder.getTxt_contactName().setText("unknown");//data.getContact().getActorName());
        holder.getTxt_contactName().setTypeface(tf);

        holder.getTxt_notes().setText(data.getReason());
        holder.getTxt_notes().setTypeface(tf);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        holder.getTxt_time().setText(data.getDate());
        holder.getTxt_time().setTypeface(tf);

        holder.getBtn_accept_request().setOnClickListener(this);

        holder.getBtn_refuse_request().setOnClickListener(this);


    }

    private int paintColorRequestType(int type){
        return Color.parseColor((type==PaymentRequest.RECEIVE_PAYMENT) ? "#6563a4" : "#6563a4");
    }



    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.btn_accept_request){
            Toast.makeText(context,"aceptado request",Toast.LENGTH_SHORT).show();
        }else if (view.getId() == R.id.btn_refuse_request){
            Toast.makeText(context,"denegado request",Toast.LENGTH_SHORT).show();
        }

    }
}
