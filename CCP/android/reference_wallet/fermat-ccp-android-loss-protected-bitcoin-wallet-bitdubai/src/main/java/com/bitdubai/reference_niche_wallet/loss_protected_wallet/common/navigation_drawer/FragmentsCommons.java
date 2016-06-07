package com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.navigation_drawer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_loss_protected_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.engine.FermatApplicationCaller;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.transformation.CircleTransform;
import com.bitdubai.fermat_api.layer.all_definition.enums.SubAppsPublicKeys;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetActiveLoginIdentityException;

import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWallet;
import com.squareup.picasso.Picasso;

/**
 * Created by Matias Furszyfer on 2015.11.12..
 */
public class FragmentsCommons {


    public static View setUpHeaderScreen(LayoutInflater inflater,Context activity,ReferenceAppFermatSession<LossProtectedWallet> lossWalletSession,final FermatApplicationCaller applicationsHelper) throws CantGetActiveLoginIdentityException {
        View view = inflater.inflate(R.layout.loss_navigation_view_row_first, null, true);
        FermatTextView fermatTextView = (FermatTextView) view.findViewById(R.id.txt_name);
        try {
            ActiveActorIdentityInformation identityModule = lossWalletSession.getModuleManager().getSelectedActorIdentity();
            ImageView imageView = (ImageView) view.findViewById(R.id.image_view_photo);
            if (identityModule != null) {
                if (identityModule.getImage() != null) {
                    if (identityModule.getImage().length > 0) {
                           imageView.setImageDrawable(ImagesUtils.getRoundedBitmap(activity.getResources(),identityModule.getImage()));

                    } else
                        Picasso.with(activity).load(R.drawable.profile_image_male_lossp).transform(new CircleTransform()).into(imageView); //default image by param
                }
                fermatTextView.setText(identityModule.getAlias());
            }else{
                fermatTextView.setText("");
            }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        applicationsHelper.openFermatApp(SubAppsPublicKeys.CCP_IDENTITY.getCode());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            return view;
        }catch (OutOfMemoryError outOfMemoryError){
            outOfMemoryError.printStackTrace();
            Toast.makeText(activity,"Error: out of memory ",Toast.LENGTH_SHORT).show();

        } catch (CantGetSelectedActorIdentityException e) {
            e.printStackTrace();
        } catch (ActorIdentityNotSelectedException e) {
            e.printStackTrace();
        }
        return view;
    }

    private static Bitmap convert(Bitmap bitmap, Bitmap.Config config) {
        Bitmap convertedBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), config);
        Canvas canvas = new Canvas(convertedBitmap);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return convertedBitmap;
    }
}
