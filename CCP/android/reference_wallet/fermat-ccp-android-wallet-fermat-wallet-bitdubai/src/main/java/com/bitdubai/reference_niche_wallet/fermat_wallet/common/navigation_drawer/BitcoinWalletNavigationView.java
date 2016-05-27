package com.bitdubai.reference_niche_wallet.fermat_wallet.common.navigation_drawer;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.util.BitmapWorkerTask;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.mati.fermat_navigator.drawer.FermatNavigationViewPainter;

import com.squareup.picasso.Picasso;
import com.bitdubai.android_fermat_ccp_wallet_fermat.R;

/**
 * Created by natalia on 29/02/16.
 */
public class BitcoinWalletNavigationView extends FermatNavigationViewPainter {
//TODO: navigation   drawer tool implementation

    private Activity activity;
    public BitcoinWalletNavigationView(Activity activity, ActiveActorIdentityInformation intraUserLoginIdentity) {


        super(activity, intraUserLoginIdentity);

        this.activity = activity;
    }

    @Override
    protected View setUpHeaderScreen(LayoutInflater inflater, Activity activity, ActiveActorIdentityInformation intraUserLoginIdentity) {
        View view = inflater.inflate(R.layout.navigation_view_row_first, null, true);
        FermatTextView fermatTextView = (FermatTextView) view.findViewById(R.id.txt_name);
        try {

            ImageView imageView = (ImageView) view.findViewById(R.id.image_view_profile);
            if (intraUserLoginIdentity != null) {
                if (intraUserLoginIdentity.getImage() != null) {
                    if (intraUserLoginIdentity.getImage().length > 0) {

                        BitmapWorkerTask bitmapWorkerTask = new BitmapWorkerTask(imageView,activity.getResources(),0,false);
                        bitmapWorkerTask.execute(intraUserLoginIdentity.getImage());

                    } else
                        Picasso.with(activity).load(R.drawable.ic_profile_male).into(imageView); //default image by param
                }
                fermatTextView.setText(intraUserLoginIdentity.getAlias());
            }else{
                fermatTextView.setText("");
            }

            return view;
        }catch (OutOfMemoryError outOfMemoryError){
            Toast.makeText(activity, "Error: out of memory ", Toast.LENGTH_SHORT).show();
        }
        return view;
    }

    @Override
    protected FermatAdapter getNavigationAdapter() {
        try {
            return new BitcoinWalletNavigationViewAdapter(this.activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected Bitmap getbackgroundDrawable() {
        Bitmap drawable = null;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = true;
            options.inSampleSize = 5;
            drawable = BitmapFactory.decodeResource(
                    activity.getResources(), R.drawable.bg_drawer_body,options);
            //drawable = ContextCompat.getDrawable(activity.getApplicationContext(), R.drawable.bg_drawer_body);
        }catch (OutOfMemoryError error){
            error.printStackTrace();
        }
        return drawable;
    }


    @Override
    public View addNavigationViewHeader() {
        return null;
    }
}
