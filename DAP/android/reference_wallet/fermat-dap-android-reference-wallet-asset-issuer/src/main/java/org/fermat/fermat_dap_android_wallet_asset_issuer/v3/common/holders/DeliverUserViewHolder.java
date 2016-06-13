package org.fermat.fermat_dap_android_wallet_asset_issuer.v3.common.holders;

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
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.R;

import org.fermat.fermat_dap_android_wallet_asset_issuer.models.User;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces.AssetIssuerWalletSupAppModuleManager;

/**
 * Created by frank on 12/8/15.
 */
public class DeliverUserViewHolder extends FermatViewHolder {
    private AssetIssuerWalletSupAppModuleManager manager;
    private Context context;
    private Resources res;

    private FermatTextView userNameText;
    private ImageView userImage;
    private RelativeLayout selectedUser;
    private RelativeLayout deliverUserHeaderLayout;
    private FermatTextView letterText;


    /**
     * Constructor
     *
     * @param itemView
     */
    public DeliverUserViewHolder(View itemView, AssetIssuerWalletSupAppModuleManager manager, Context context) {
        super(itemView);
        this.manager = manager;
        this.context = context;
        res = itemView.getResources();

        userNameText = (FermatTextView) itemView.findViewById(R.id.userNameText);
        userImage = (ImageView) itemView.findViewById(R.id.userImage);
        selectedUser = (RelativeLayout) itemView.findViewById(R.id.selectedUser);
        deliverUserHeaderLayout = (RelativeLayout) itemView.findViewById(R.id.deliverUserHeaderLayout);
        letterText = (FermatTextView) itemView.findViewById(R.id.letterText);
    }

    public void bind(final User user) {
        userNameText.setText(user.getName());
        selectedUser.setVisibility(user.isSelected() ? View.VISIBLE : View.INVISIBLE);
        letterText.setText(user.getName().substring(0, 1).toUpperCase());
        deliverUserHeaderLayout.setVisibility(user.isFirst() ? View.VISIBLE : View.GONE);

        if (user.getImage() != null && user.getImage().length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(user.getImage(), 0, user.getImage().length);
            bitmap = Bitmap.createScaledBitmap(bitmap, 120, 120, true);
            userImage.setImageDrawable(ImagesUtils.getRoundedBitmap(context.getResources(), bitmap));
        }
    }
}
