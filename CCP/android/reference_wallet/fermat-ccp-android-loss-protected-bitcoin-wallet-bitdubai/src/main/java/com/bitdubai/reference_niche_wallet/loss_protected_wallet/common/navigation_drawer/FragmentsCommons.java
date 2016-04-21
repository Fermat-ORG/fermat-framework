package com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.navigation_drawer;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_loss_protected_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetActiveLoginIdentityException;

/**
 * Created by Matias Furszyfer on 2015.11.12..
 */
public class FragmentsCommons {


    public static View setUpHeaderScreen(LayoutInflater inflater,Context activity,ActiveActorIdentityInformation intraUserLoginIdentity) throws CantGetActiveLoginIdentityException {
        View view = inflater.inflate(R.layout.navigation_view_row_first, null, true);
        FermatTextView fermatTextView = (FermatTextView) view.findViewById(R.id.txt_name);
        try {
            Bitmap bitmap = null;
            //ImageView imageView = (ImageView) view.findViewById(R.id.image_view_photo);
            if (intraUserLoginIdentity != null) {
                if (intraUserLoginIdentity.getImage() != null) {
                    if (intraUserLoginIdentity.getImage().length > 0) {
                        //BitmapFactory.Options options = new BitmapFactory.Options();
                        //options.inScaled = true;
                        //options.inSampleSize = 2;
                        bitmap = BitmapFactory.decodeByteArray(intraUserLoginIdentity.getImage(), 0, intraUserLoginIdentity.getImage().length);
//                        BitmapWorkerTask bitmapWorkerTask = new BitmapWorkerTask(imageView,activity.getResources(),false);
//                        bitmapWorkerTask.execute(intraUserLoginIdentity.getImage());

                        //Bitmap bitmap = BitmapFactory.decodeByteArray(intraUserLoginIdentity.getProfileImage(), 0, intraUserLoginIdentity.getProfileImage().length, options);
                        //options.inBitmap = bitmap;
                        //Bitmap convertedBitmap = convert(bitmap, Bitmap.Config.ARGB_8888);
               //         Bitmap converted = bitmap.copy(Bitmap.Config.RGB_565, true);
                        //bitmap = Bitmap.createScaledBitmap(bitmap,imageView.getMaxWidth(),imageView.getMaxHeight(),true);
                        //imageView.setImageBitmap(bitmap);
                    } else
                        //Picasso.with(activity).load(R.drawable.ic_profile_male).into(imageView);
                        bitmap = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.ic_profile_male);
                }
                //imageView.setImageDrawable(ImagesUtils.getRoundedBitmap(Resources.getSystem(), bitmap));
                fermatTextView.setText(intraUserLoginIdentity.getAlias());
            }else{
                fermatTextView.setText("");
            }

            return view;
        }catch (OutOfMemoryError outOfMemoryError){
            Toast.makeText(activity,"Error: out of memory ",Toast.LENGTH_SHORT).show();
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
