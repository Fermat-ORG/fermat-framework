package org.fermat.fermat_dap_android_wallet_asset_user.holders;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.R;

import org.fermat.fermat_dap_android_wallet_asset_user.models.User;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;

/**
 * Created by Jinmy Bohorquez on 02/18/16.
 */
public class AssetTransferSelectUsersHolder extends FermatViewHolder {
    private AssetUserWalletSubAppModuleManager manager;
    private Context context;
    private Resources res;

    private FermatTextView nameText;
    private ImageView selectUsersButton;
    private ImageView imageViewTransfer;


    /**
     * Constructor
     *
     * @param itemView
     */
    public AssetTransferSelectUsersHolder(View itemView, AssetUserWalletSubAppModuleManager manager, Context context) {
        super(itemView);
        this.manager = manager;
        this.context = context;
        res = itemView.getResources();

        nameText = (FermatTextView) itemView.findViewById(R.id.userNameTransfer);
        selectUsersButton = (ImageView) itemView.findViewById(R.id.selectUserTransferButton);
        imageViewTransfer = (ImageView) itemView.findViewById(R.id.imageView_user_tranfer_avatar);
    }

    public void bind(final User user) {
        nameText.setText(user.getName());
        if (user.isSelected()) {
            selectUsersButton.setImageDrawable(res.getDrawable(R.drawable.ic_deliver_user_remove));
        } else {
            selectUsersButton.setImageDrawable(res.getDrawable(R.drawable.ic_deliver_user_add));
        }

        selectUsersButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!user.isSelected()) {
                    user.setSelected(true);
                    selectUsersButton.setImageDrawable(res.getDrawable(R.drawable.ic_deliver_user_remove));
                } else {
                    user.setSelected(false);
                    selectUsersButton.setImageDrawable(res.getDrawable(R.drawable.ic_deliver_user_add));
                }
            }
        });

        if (user.getActorAssetUser().getProfileImage() != null && user.getActorAssetUser().getProfileImage().length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(user.getActorAssetUser().getProfileImage(), 0, user.getActorAssetUser().getProfileImage().length);
            bitmap = Bitmap.createScaledBitmap(bitmap, 120, 120, true);
            imageViewTransfer.setImageDrawable(ImagesUtils.getRoundedBitmap(context.getResources(), bitmap));
        }
    }
}
