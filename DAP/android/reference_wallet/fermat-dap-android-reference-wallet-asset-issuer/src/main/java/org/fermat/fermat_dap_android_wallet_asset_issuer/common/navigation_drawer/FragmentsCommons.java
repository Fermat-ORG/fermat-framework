package org.fermat.fermat_dap_android_wallet_asset_issuer.common.navigation_drawer;

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
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.R;
import com.squareup.picasso.Picasso;

import org.fermat.fermat_dap_android_wallet_asset_issuer.util.BitmapWorkerTask;
import org.fermat.fermat_dap_api.layer.all_definition.exceptions.CantGetIdentityAssetIssuerException;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces.AssetIssuerWalletSupAppModuleManager;

/**
 * Created by Matias Furszyfer on 2015.11.12..
 */
public class FragmentsCommons {

    public static View setUpHeaderScreen(LayoutInflater inflater,
                                         Context activity,
                                         ReferenceAppFermatSession<AssetIssuerWalletSupAppModuleManager> assetIssuerSession,
                                         final FermatApplicationCaller applicationsHelper) throws CantGetIdentityAssetIssuerException {

        View view = inflater.inflate(R.layout.dap_navigation_drawer_issuer_wallet_header, null, true);
        try {
            ActiveActorIdentityInformation identityInformation = assetIssuerSession.getModuleManager().getActiveAssetIssuerIdentity();

            ImageView imageView = (ImageView) view.findViewById(R.id.image_view_profile);
            if (identityInformation != null) {
                if (identityInformation.getImage() != null) {
                    if (identityInformation.getImage().length > 0) {
                        //BitmapFactory.Options options = new BitmapFactory.Options();
                        //options.inScaled = true;
                        //options.inSampleSize = 2;
//                        imageView.setImageBitmap((BitmapFactory.decodeByteArray(identityAssetIssuer.getImage(), 0, identityAssetIssuer.getImage().length)));

                        BitmapWorkerTask bitmapWorkerTask = new BitmapWorkerTask(imageView, activity.getResources(), false);
                        bitmapWorkerTask.execute(identityInformation.getImage());

                        //Bitmap bitmap = BitmapFactory.decodeByteArray(intraUserLoginIdentity.getProfileImage(), 0, intraUserLoginIdentity.getProfileImage().length, options);
                        //options.inBitmap = bitmap;
                        //Bitmap convertedBitmap = convert(bitmap, Bitmap.Config.ARGB_8888);
                        //         Bitmap converted = bitmap.copy(Bitmap.Config.RGB_565, true);
                        //bitmap = Bitmap.createScaledBitmap(bitmap,imageView.getMaxWidth(),imageView.getMaxHeight(),true);
                        //imageView.setImageBitmap(bitmap);
                    } else
                        Picasso.with(activity).load(R.drawable.banner_asset_issuer_wallet).into(imageView);
                }
                FermatTextView fermatTextView = (FermatTextView) view.findViewById(R.id.txt_name);
                fermatTextView.setText(identityInformation.getAlias());
            } else {
                Picasso.with(activity).load(R.drawable.banner_asset_issuer_wallet).into(imageView);
                FermatTextView fermatTextView = (FermatTextView) view.findViewById(R.id.txt_name);
                fermatTextView.setText(R.string.dap_identity_alias_default_text);
            }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        applicationsHelper.openFermatApp(SubAppsPublicKeys.DAP_IDENTITY_ISSUER.getCode());
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
