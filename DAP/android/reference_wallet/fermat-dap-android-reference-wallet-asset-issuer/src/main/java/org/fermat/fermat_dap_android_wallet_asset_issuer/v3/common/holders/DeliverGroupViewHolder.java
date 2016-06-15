package org.fermat.fermat_dap_android_wallet_asset_issuer.v3.common.holders;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.R;

import org.fermat.fermat_dap_android_wallet_asset_issuer.models.Group;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces.AssetIssuerWalletSupAppModuleManager;

/**
 * Created by frank on 12/8/15.
 */
public class DeliverGroupViewHolder extends FermatViewHolder {
    private AssetIssuerWalletSupAppModuleManager manager;
    private Context context;
    private Resources res;

    private FermatTextView groupNameText;
    private ImageView groupImage;
    private RelativeLayout selectedGroup;
    private RelativeLayout deliverGroupHeaderLayout;
    private FermatTextView letterText;


    /**
     * Constructor
     *
     * @param itemView
     */
    public DeliverGroupViewHolder(View itemView, AssetIssuerWalletSupAppModuleManager manager, Context context) {
        super(itemView);
        this.manager = manager;
        this.context = context;
        res = itemView.getResources();

        groupNameText = (FermatTextView) itemView.findViewById(R.id.groupNameText);
        groupImage = (ImageView) itemView.findViewById(R.id.groupImage);
        selectedGroup = (RelativeLayout) itemView.findViewById(R.id.selectedGroup);
        deliverGroupHeaderLayout = (RelativeLayout) itemView.findViewById(R.id.deliverGroupHeaderLayout);
        letterText = (FermatTextView) itemView.findViewById(R.id.letterGroupText);
    }

    public void bind(final Group group) {
        groupNameText.setText(group.getName());
        selectedGroup.setVisibility(group.isSelected() ? View.VISIBLE : View.INVISIBLE);
        letterText.setText(group.getName().substring(0, 1).toUpperCase());
        deliverGroupHeaderLayout.setVisibility(group.isFirst() ? View.VISIBLE : View.GONE);

//        if (group.getActorAssetUserGroup().getProfileImage() != null && group.getActorAssetUserGroup().getProfileImage().length > 0) {
//            Bitmap bitmap = BitmapFactory.decodeByteArray(group.getActorAssetUserGroup().getProfileImage(), 0, group.getActorAssetUserGroup().getProfileImage().length);
//            bitmap = Bitmap.createScaledBitmap(bitmap, 120, 120, true);
//            groupImage.setImageDrawable(ImagesUtils.getRoundedBitmap(context.getResources(), bitmap));
//        }
    }
}
