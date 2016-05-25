package org.fermat.fermat_dap_android_wallet_asset_issuer.holders;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.R;

import org.fermat.fermat_dap_android_wallet_asset_issuer.models.Group;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces.AssetIssuerWalletSupAppModuleManager;

/**
 * Created by frank on 12/8/15.
 */
public class AssetDeliverySelectGroupsHolder extends FermatViewHolder {
    private AssetIssuerWalletSupAppModuleManager manager;
    private Context context;
    private Resources res;

    private FermatTextView nameText;
    private ImageView selectGroupButton;
    private ImageView imageViewGroup;

    /**
     * Constructor
     *
     * @param itemView
     */
    public AssetDeliverySelectGroupsHolder(View itemView, AssetIssuerWalletSupAppModuleManager manager, Context context) {
        super(itemView);
        this.manager = manager;
        this.context = context;
        res = itemView.getResources();

        nameText = (FermatTextView) itemView.findViewById(R.id.groupName);
        selectGroupButton = (ImageView) itemView.findViewById(R.id.selectGroupButton);
        imageViewGroup = (ImageView) itemView.findViewById(R.id.imageView_group_avatar);
    }

    public void bind(final Group group) {
        int users = group.getUsers().size();
        nameText.setText(group.getName() + " (" + users + " " + (users == 1 ? "user" : "users") + ")");
        if (group.isSelected()) {
            selectGroupButton.setImageDrawable(res.getDrawable(R.drawable.ic_deliver_user_remove));
        } else {
            selectGroupButton.setImageDrawable(res.getDrawable(R.drawable.ic_deliver_user_add));
        }

        selectGroupButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!group.isSelected()) {
                    group.setSelected(true);
                    selectGroupButton.setImageDrawable(res.getDrawable(R.drawable.ic_deliver_user_remove));
                } else {
                    group.setSelected(false);
                    selectGroupButton.setImageDrawable(res.getDrawable(R.drawable.ic_deliver_user_add));
                }
            }
        });

        imageViewGroup.setImageDrawable(ImagesUtils.getRoundedBitmap(context.getResources(), R.drawable.ic_group_image));
    }
}
