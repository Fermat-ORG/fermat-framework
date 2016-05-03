package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.holders;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.ui.expandableRecicler.ParentViewHolder;
import com.bitdubai.fermat_android_api.ui.transformation.CircleTransform;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletTransaction;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.enums.ShowMoneyType;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.BitmapWorkerTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Locale;

import static com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.WalletUtils.formatBalanceString;

/**
 * Created by nelson on 21/10/15.
 */
public class GrouperViewHolder extends ParentViewHolder {

    private static final float INITIAL_POSITION = 0.0f;
    private static final float ROTATED_POSITION = 180f;
    private static final float PIVOT_VALUE = 0.5f;
    private static final long DEFAULT_ROTATE_DURATION_MS = 200;
    private static final boolean HONEYCOMB_AND_ABOVE = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    public ColorDrawable background;
    public ImageView mArrowExpandImageView;
    Resources res;
    private View itemView;
    private ImageView contactIcon;
    private TextView txt_contactName;
    private TextView txt_amount;
    private TextView txt_notes;
    private TextView txt_time;
    private TextView txt_total_number_transactions;
    private TextView txt_total_balance;

    /**
     * Public constructor for the CustomViewHolder.
     *
     * @param itemView the view of the parent item. Find/modify views using this.
     */
    public GrouperViewHolder(View itemView,Resources res) {
        super(itemView);

        this.res = res;
        this.itemView = itemView;

        contactIcon = (ImageView) itemView.findViewById(R.id.profile_Image);
        txt_contactName = (TextView) itemView.findViewById(R.id.txt_contactName);
        txt_amount = (TextView) itemView.findViewById(R.id.txt_amount);
        txt_notes = (TextView) itemView.findViewById(R.id.txt_notes);
        txt_time = (TextView) itemView.findViewById(R.id.txt_time);

        txt_total_balance = (TextView) itemView.findViewById(R.id.txt_total_balance);
        txt_total_number_transactions = (TextView) itemView.findViewById(R.id.txt_total_number_transactions);
        mArrowExpandImageView = (ImageView) itemView.findViewById(R.id.cbw_arrow);
    }

    /**
     * Set the data to the views
     *
     */
    public void bind(int childCount,CryptoWalletTransaction cryptoWalletTransaction) {

        byte[] photo = null;
        String contactName = "Uninformed";

        //involved actor is not a wallet contact
        if(cryptoWalletTransaction.getInvolvedActor() != null)
            {
                photo = cryptoWalletTransaction.getInvolvedActor().getPhoto();
                contactName = cryptoWalletTransaction.getInvolvedActor().getName();
            }else{
            if (cryptoWalletTransaction.getActorFromType().equals(Actors.DEVICE_USER)){
                contactName = "Device User Transaction";
            }
        }

        //TODO Ver porque se cae cuando el contacto tiene algunos bytes
        try {
            if (photo != null) {
//            contactIcon.setImageDrawable(ImagesUtils.getRoundedBitmap(res,photo));
                BitmapWorkerTask bitmapWorkerTask = new BitmapWorkerTask(contactIcon,res,true);
                bitmapWorkerTask.execute(photo);
            } else
                Picasso.with(contactIcon.getContext()).load(R.drawable.ic_profile_male).transform(new CircleTransform()).into(contactIcon);
        }catch (Exception e){
            Picasso.with(contactIcon.getContext()).load(R.drawable.ic_profile_male).transform(new CircleTransform()).into(contactIcon);

        }

        txt_contactName.setText(contactName);
        txt_amount.setText(formatBalanceString(cryptoWalletTransaction.getAmount(), ShowMoneyType.BITCOIN.getCode())+ " btc");

        txt_notes.setText(cryptoWalletTransaction.getMemo());

        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy HH:mm", Locale.US);
        txt_time.setText(sdf.format(cryptoWalletTransaction.getTimestamp()) + " hs");
        txt_total_number_transactions.setText(String.valueOf(childCount)+ " records");

        //TODO me falta el total
        //txt_total_balance.setText();
    }



    @Override
    @SuppressLint("NewApi")
    public void setExpanded(boolean expanded) {
        super.setExpanded(expanded);
        if (!HONEYCOMB_AND_ABOVE) {
            return;
        }

        if (expanded) {
            mArrowExpandImageView.setRotation(ROTATED_POSITION);
        } else {
            mArrowExpandImageView.setRotation(INITIAL_POSITION);
        }
    }

    @Override
    public void onExpansionToggled(boolean wasExpanded) {
        super.onExpansionToggled(wasExpanded);

        //mNumberTextView.setVisibility(wasExpanded ? View.VISIBLE : View.GONE);

        if (!HONEYCOMB_AND_ABOVE) {
            return;
        }

        RotateAnimation rotateAnimation = new RotateAnimation(
                ROTATED_POSITION, INITIAL_POSITION,
                RotateAnimation.RELATIVE_TO_SELF, PIVOT_VALUE,
                RotateAnimation.RELATIVE_TO_SELF, PIVOT_VALUE);

        rotateAnimation.setDuration(DEFAULT_ROTATE_DURATION_MS);
        rotateAnimation.setFillAfter(true);

        mArrowExpandImageView.startAnimation(rotateAnimation);
    }

    private Drawable getImgDrawable(byte[] customerImg) {
        if (customerImg != null && customerImg.length > 0)
            return ImagesUtils.getRoundedBitmap(res, customerImg);

        return ImagesUtils.getRoundedBitmap(res, R.drawable.ic_profile_male);
    }
}
