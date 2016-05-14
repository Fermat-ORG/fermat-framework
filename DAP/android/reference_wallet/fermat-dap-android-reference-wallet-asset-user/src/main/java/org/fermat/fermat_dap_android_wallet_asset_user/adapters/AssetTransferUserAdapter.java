package org.fermat.fermat_dap_android_wallet_asset_user.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.R;

import org.fermat.fermat_dap_android_wallet_asset_user.holders.AssetTransferUserHolder;
import org.fermat.fermat_dap_android_wallet_asset_user.models.User;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;

import java.util.List;

/**
 * Created by Jinmy Bohorquez on 18/2/16.
 */
public class AssetTransferUserAdapter extends FermatAdapter<User, AssetTransferUserHolder> {

    private AssetUserWalletSubAppModuleManager manager;

    public AssetTransferUserAdapter(Context context, List<User> users, AssetUserWalletSubAppModuleManager manager) {
        super(context, users);
        this.manager = manager;
    }

    @Override
    protected AssetTransferUserHolder createHolder(View itemView, int type) {
        return new AssetTransferUserHolder(itemView, manager, context);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.dap_v3_wallet_asset_user_asset_transfer_select_user_item;
    }

    @Override
    protected void bindHolder(final AssetTransferUserHolder holder, final User user, int position) {
        holder.nameText.setText(user.getName());
        if (user.isSelected()) {
           holder.userContent.setVisibility(View.VISIBLE);
        } else {
            holder.userContent.setVisibility(View.INVISIBLE);
        }

//        holder.selectUsersButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                if (!user.isSelected()) {
//                    user.setSelected(true);
//                    holder.selectUsersButton.setImageDrawable(res.getDrawable(R.drawable.ic_deliver_user_remove));
//                } else {
//                    user.setSelected(false);
//                    holder.selectUsersButton.setImageDrawable(res.getDrawable(R.drawable.ic_deliver_user_add));
//                }
//            }
//        });

        if (user.getActorAssetUser().getProfileImage() != null && user.getActorAssetUser().getProfileImage().length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(user.getActorAssetUser().getProfileImage(), 0, user.getActorAssetUser().getProfileImage().length);
            bitmap = Bitmap.createScaledBitmap(bitmap, 120, 120, true);
            holder.imageViewTransfer.setImageDrawable(ImagesUtils.getRoundedBitmap(context.getResources(), bitmap));
        }
    }
}
