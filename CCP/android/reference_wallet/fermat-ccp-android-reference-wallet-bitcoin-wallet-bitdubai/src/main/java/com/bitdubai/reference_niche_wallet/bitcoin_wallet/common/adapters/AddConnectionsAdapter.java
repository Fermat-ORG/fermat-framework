package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.view.View;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserInformation;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletIntraUserActor;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.holders.IntraUserInfoViewHolder;

import java.util.ArrayList;

/**
 * Created on 22/08/15.
 * Adapter para el RecliclerView del CryptoBrokerIdentityListFragment que muestra el catalogo de Wallets disponibles en el store
 *
 * @author Nelson Ramirez
 */
public class AddConnectionsAdapter extends FermatAdapter<CryptoWalletIntraUserActor, IntraUserInfoViewHolder> {


    public AddConnectionsAdapter(Context context, ArrayList<CryptoWalletIntraUserActor> dataSet) {
        super(context, dataSet);
    }

    @Override
    protected IntraUserInfoViewHolder createHolder(View itemView, int type) {
        return new IntraUserInfoViewHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.intra_user_information_list_item;
    }

    @Override
    protected void bindHolder(final IntraUserInfoViewHolder holder, final CryptoWalletIntraUserActor data, final int position) {
        holder.getName().setText(data.getAlias());
        RoundedBitmapDrawable roundedBitmap = null;
        byte[] profileImage = data.getProfileImage();
        if(profileImage!=null){
            if(profileImage.length>0){
                roundedBitmap = ImagesUtils.getRoundedBitmap(context.getResources(), profileImage);
            }else {
                roundedBitmap = ImagesUtils.getRoundedBitmap(context.getResources(), R.drawable.profile_image);
            }
        }else {
            roundedBitmap = ImagesUtils.getRoundedBitmap(context.getResources(), R.drawable.profile_image);
        }

        holder.getThumbnail().setImageBitmap(roundedBitmap.getBitmap());
    }
}
