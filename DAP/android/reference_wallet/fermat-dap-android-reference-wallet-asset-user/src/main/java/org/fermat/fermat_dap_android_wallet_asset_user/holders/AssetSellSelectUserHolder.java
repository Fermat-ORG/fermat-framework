package org.fermat.fermat_dap_android_wallet_asset_user.holders;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.R;

import org.fermat.fermat_dap_android_wallet_asset_user.models.User;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;

/**
 * Jinmy Bohorquez 15/02/2016.
 */
public class AssetSellSelectUserHolder extends FermatViewHolder {
    private AssetUserWalletSubAppModuleManager manager;
    private Context context;
    private Resources res;

    private FermatTextView nameText;
    private ImageView imageViewUser;
    public RelativeLayout userLayout;


    /**
     * Constructor
     *
     * @param itemView
     */
    public AssetSellSelectUserHolder(View itemView, AssetUserWalletSubAppModuleManager manager, Context context) {
        super(itemView);
        this.manager = manager;
        this.context = context;
        res = itemView.getResources();

        nameText = (FermatTextView) itemView.findViewById(R.id.userName);
        imageViewUser = (ImageView) itemView.findViewById(R.id.imageView_user_sell_avatar);
        userLayout = (RelativeLayout) itemView.findViewById(R.id.selectedUserEffectSell);


    }

    public void bind(final User user) {
        nameText.setText(user.getName());
        if (user.getActorAssetUser().getProfileImage() != null && user.getActorAssetUser().getProfileImage().length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(user.getActorAssetUser().getProfileImage(), 0, user.getActorAssetUser().getProfileImage().length);
            bitmap = Bitmap.createScaledBitmap(bitmap, 120, 120, true);
            imageViewUser.setImageDrawable(ImagesUtils.getRoundedBitmap(context.getResources(), bitmap));
        }
    }
}
