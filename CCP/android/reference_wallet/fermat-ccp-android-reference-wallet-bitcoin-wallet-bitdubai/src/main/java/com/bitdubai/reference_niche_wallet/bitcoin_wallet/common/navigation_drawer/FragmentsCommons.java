package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.navigation_drawer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.engine.FermatApplicationCaller;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_api.layer.all_definition.enums.SubAppsPublicKeys;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetActiveLoginIdentityException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.BitmapWorkerTask;
import com.squareup.picasso.Picasso;

/**
 * Created by Matias Furszyfer on 2015.11.12..
 */
public class FragmentsCommons {


        public static View setUpHeaderScreen(LayoutInflater inflater,Context activity,ReferenceAppFermatSession<CryptoWallet> referenceWalletSession,final FermatApplicationCaller applicationsHelper) throws CantGetActiveLoginIdentityException {

            View view = inflater.inflate(R.layout.navigation_view_row_first, null, true);
            FermatTextView fermatTextView = (FermatTextView) view.findViewById(R.id.txt_name);
            try {
               ActiveActorIdentityInformation identityInformation= referenceWalletSession.getModuleManager().getSelectedActorIdentity();
                ImageView imageView = (ImageView) view.findViewById(R.id.image_view_profile);
                if (identityInformation != null) {
                    if (identityInformation.getImage() != null) {
                        if (identityInformation.getImage().length > 0) {

                            BitmapWorkerTask bitmapWorkerTask = new BitmapWorkerTask(imageView,activity.getResources(),false);
                            bitmapWorkerTask.execute(identityInformation.getImage());

                        } else
                            Picasso.with(activity).load(R.drawable.ic_profile_male).into(imageView);
                    }
                    fermatTextView.setText(identityInformation.getAlias());
                }else{
                    fermatTextView.setText("Loading..");
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
