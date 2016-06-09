package org.fermat.fermat_dap_android_wallet_redeem_point.navigation_drawer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.engine.FermatApplicationCaller;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_api.layer.all_definition.enums.SubAppsPublicKeys;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.R;
import com.squareup.picasso.Picasso;

import org.fermat.fermat_dap_android_wallet_redeem_point.util.BitmapWorkerTask;
import org.fermat.fermat_dap_api.layer.all_definition.exceptions.CantGetIdentityRedeemPointException;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_redeem_point.interfaces.AssetRedeemPointWalletSubAppModule;

/**
 * Created by Matias Furszyfer on 2015.11.12..
 */
public class FragmentsCommons {

    public static View setUpHeaderScreen(LayoutInflater inflater,
                                         Context activity,
                                         ReferenceAppFermatSession<AssetRedeemPointWalletSubAppModule> redeemPointSession,
                                         final FermatApplicationCaller applicationsHelper) throws CantGetIdentityRedeemPointException {

        View view = inflater.inflate(R.layout.dap_navigation_drawer_redeem_point_wallet_header, null, true);
        try {
            ActiveActorIdentityInformation identityInformation = redeemPointSession.getModuleManager().getActiveAssetRedeemPointIdentity();

            ImageView imageView = (ImageView) view.findViewById(R.id.image_view_profile);
            if (identityInformation != null) {
                if (identityInformation.getImage() != null) {
                    if (identityInformation.getImage().length > 0) {
                        BitmapWorkerTask bitmapWorkerTask = new BitmapWorkerTask(imageView, activity.getResources(), false);
                        bitmapWorkerTask.execute(identityInformation.getImage());
                    } else
                        Picasso.with(activity).load(R.drawable.banner_redeem_point_wallet).into(imageView);
                }
                FermatTextView fermatTextView = (FermatTextView) view.findViewById(R.id.txt_name);
                fermatTextView.setText(identityInformation.getAlias());
            } else {
                Picasso.with(activity).load(R.drawable.banner_redeem_point_wallet).into(imageView);
                FermatTextView fermatTextView = (FermatTextView) view.findViewById(R.id.txt_name);
                fermatTextView.setText(R.string.dap_identity_alias_default_text);
            }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        applicationsHelper.openFermatApp(SubAppsPublicKeys.DAP_IDENTITY_REDEEM.getCode());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (OutOfMemoryError outOfMemoryError) {
            Toast.makeText(activity, "Error: out of memory ", Toast.LENGTH_SHORT).show();
        }
        return view;
    }
}
