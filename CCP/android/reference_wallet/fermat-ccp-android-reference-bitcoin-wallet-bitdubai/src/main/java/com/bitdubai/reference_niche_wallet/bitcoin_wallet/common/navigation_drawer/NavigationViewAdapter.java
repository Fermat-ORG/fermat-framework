package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.navigation_drawer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.view.View;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.util.MemoryUtils;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserLoginIdentity;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.PaymentRequest;

import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.holders.PaymentHomeItemViewHolder;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.ReferenceWalletSession;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import static com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.WalletUtils.formatBalanceString;

/**
 * Created by Matias Furszyfer on 2015.09.30..
 */
public class NavigationViewAdapter extends FermatAdapter<MenuItem, NavigationItemMenuViewHolder> {


    private IntraUserLoginIdentity intraUserLoginIdentity;
    Typeface tf;
    protected NavigationViewAdapter(Context context) {
        super(context);
    }

    public NavigationViewAdapter(Context context, List<MenuItem> dataSet,IntraUserLoginIdentity intraUserLoginIdentity) {
        super(context, dataSet);
        tf = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
        this.intraUserLoginIdentity = intraUserLoginIdentity;
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
    protected NavigationItemMenuViewHolder createHolder(View itemView, int type) {
        return new NavigationItemMenuViewHolder(itemView);
    }

    /**
     * Get custom layout to use it.
     *
     * @return int Layout Resource id: Example: R.layout.row_item
     */
    @Override
    protected int getCardViewResource() {
        return R.layout.navigation_row;
    }

    /**
     * Bind ViewHolder
     *
     * @param holder   ViewHolder object
     * @param data     Object data to render
     * @param position position to render
     */
    @Override
    protected void bindHolder(NavigationItemMenuViewHolder holder, MenuItem data, int position) {

        try {

            holder.getLabel().setText(data.getLabel());


            switch (position) {
                case 1:
                    holder.getIcon().setImageResource(R.drawable.btn_drawer_home_normal);
                    break;
                case 2:
                    holder.getIcon().setImageResource(R.drawable.btn_drawer_profile_normal);
                    break;
                case 3:
                    holder.getIcon().setImageResource(R.drawable.btn_drawer_request_normal);
                    break;
                case 4:
                    holder.getIcon().setImageResource(R.drawable.btn_drawer_settings_normal);

                    break;
                case 5:
                    holder.getIcon().setImageResource(R.drawable.btn_drawer_logout_normal);
                    break;
                case 0:
                    if(intraUserLoginIdentity.getProfileImage()!=null) {
                        //Bitmap bitmap = BitmapFactory.decodeByteArray(intraUserLoginIdentity.getProfileImage(), 0, intraUserLoginIdentity.getProfileImage().length);
                        //holder.getIcon().setImageBitmap(bitmap);
                    }else{
                        //Picasso.with(context).load(R.drawable.profile_image2).into(holder.getIcon());
                    }

                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
