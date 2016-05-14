package org.fermat.fermat_dap_android_sub_app_asset_issuer_identity.common.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_dap_android_sub_app_asset_issuer_identity_bitdubai.R;

import org.fermat.fermat_dap_android_sub_app_asset_issuer_identity.common.holders.IssuerIdentityViewHolder;
import org.fermat.fermat_dap_android_sub_app_asset_issuer_identity.util.UtilsFuncs;
import org.fermat.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuer;

import java.util.ArrayList;

/**
 * Created on 22/08/15.
 * Adapter para el RecliclerView del CryptoBrokerIdentityListFragment que muestra el catalogo de Wallets disponibles en el store
 *
 * @author Nelson Ramirez
 */
public class IssuerIdentityAdapter extends FermatAdapter<IdentityAssetIssuer, IssuerIdentityViewHolder> {


    public IssuerIdentityAdapter(Context context, ArrayList<IdentityAssetIssuer> dataSet) {
        super(context, dataSet);
    }

    @Override
    protected IssuerIdentityViewHolder createHolder(View itemView, int type) {
        return new IssuerIdentityViewHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.dap_issuer_identity_list_item;
    }

    @Override
    protected void bindHolder(final IssuerIdentityViewHolder holder, final IdentityAssetIssuer data, final int position) {
        holder.getIdentityName().setText(data.getAlias());

        byte[] profileImage = data.getImage();
        Bitmap imageBitmap = profileImage == null ?
                BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_profile_male) :
                BitmapFactory.decodeByteArray(profileImage, 0, profileImage.length);

        Bitmap roundedBitmap = UtilsFuncs.getRoundedShape(imageBitmap);
        holder.getIdentityImage().setImageBitmap(roundedBitmap);
    }
}
