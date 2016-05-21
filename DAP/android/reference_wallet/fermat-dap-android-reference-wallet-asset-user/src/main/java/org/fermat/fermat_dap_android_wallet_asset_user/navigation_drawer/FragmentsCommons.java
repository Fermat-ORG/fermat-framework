package org.fermat.fermat_dap_android_wallet_asset_user.navigation_drawer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.R;
import com.squareup.picasso.Picasso;

import org.fermat.fermat_dap_android_wallet_asset_user.util.BitmapWorkerTask;
import org.fermat.fermat_dap_api.layer.all_definition.exceptions.CantGetIdentityAssetUserException;

/**
 * Created by Matias Furszyfer on 2015.11.12..
 */
public class FragmentsCommons {


    public static View setUpHeaderScreen(LayoutInflater inflater, Context activity, ActiveActorIdentityInformation identityAssetUser) throws CantGetIdentityAssetUserException {

        View view = inflater.inflate(R.layout.dap_navigation_drawer_user_wallet_header, null, true);

        try {
            ImageView imageView = (ImageView) view.findViewById(R.id.image_view_profile);
            if (identityAssetUser != null) {
                if (identityAssetUser.getImage() != null) {
                    if (identityAssetUser.getImage().length > 0) {
                        //BitmapFactory.Options options = new BitmapFactory.Options();
                        //options.inScaled = true;
                        //options.inSampleSize = 2;
                        BitmapWorkerTask bitmapWorkerTask = new BitmapWorkerTask(imageView, activity.getResources(), false);
                        bitmapWorkerTask.execute(identityAssetUser.getImage());
                        //Bitmap bitmap = BitmapFactory.decodeByteArray(intraUserLoginIdentity.getProfileImage(), 0, intraUserLoginIdentity.getProfileImage().length, options);
                        //options.inBitmap = bitmap;
                        //Bitmap convertedBitmap = convert(bitmap, Bitmap.Config.ARGB_8888);
                        //         Bitmap converted = bitmap.copy(Bitmap.Config.RGB_565, true);
                        //bitmap = Bitmap.createScaledBitmap(bitmap,imageView.getMaxWidth(),imageView.getMaxHeight(),true);
                        //imageView.setImageBitmap(bitmap);
                    } else
                        Picasso.with(activity).load(R.drawable.asset_user_identity).into(imageView);
                }
                FermatTextView fermatTextView = (FermatTextView) view.findViewById(R.id.txt_name);
                fermatTextView.setText(identityAssetUser.getAlias());
            } else {
                Picasso.with(activity).load(R.drawable.asset_user_identity).into(imageView);
                FermatTextView fermatTextView = (FermatTextView) view.findViewById(R.id.txt_name);
                fermatTextView.setText(R.string.dap_identity_alias_default_text);
            }

            return view;
        } catch (OutOfMemoryError outOfMemoryError) {
            Toast.makeText(activity, "Error: out of memory ", Toast.LENGTH_SHORT).show();
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
