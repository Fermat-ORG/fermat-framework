package com.mati.fermat_navigator.drawer;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.util.BitmapWorkerTask;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.mati.fermat_preference_settings.R;
import com.squareup.picasso.Picasso;


/**
 * Created by natalia on 26/02/16.
 */
public class FermatNavigationViewPainter implements com.bitdubai.fermat_android_api.engine.NavigationViewPainter {

    private final ActiveActorIdentityInformation intraUserLoginIdentity;
    private Activity activity;
    private int backgroundDrawable;

    public FermatNavigationViewPainter(Activity activity, ActiveActorIdentityInformation intraUserLoginIdentity,int backgroundDrawable) {

        this.activity = activity;
        this.intraUserLoginIdentity = intraUserLoginIdentity;
        this.backgroundDrawable = backgroundDrawable;
    }
    @Override
    public View addNavigationViewHeader(ActiveActorIdentityInformation intraUserLoginIdentity) {

            return this.setUpHeaderScreen(activity.getLayoutInflater(), activity, intraUserLoginIdentity);


    }

    @Override
    public FermatAdapter addNavigationViewAdapter() {
        try {
            FermatNavigatorDrawerAdapter navigationViewAdapter = new FermatNavigatorDrawerAdapter(activity);
            return navigationViewAdapter;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ViewGroup addNavigationViewBodyContainer(LayoutInflater layoutInflater,ViewGroup base) {
        RelativeLayout relativeLayout = (RelativeLayout) layoutInflater.inflate(R.layout.navigation_view_bottom,base,true);
        //base.setLayoutParams(new RelativeLayout.LayoutParams(activity,));
        return relativeLayout;
    }

    @Override
    public Bitmap addBodyBackground() {
        Bitmap drawable = null;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = true;
            options.inSampleSize = 5;
            drawable = BitmapFactory.decodeResource(
                    activity.getResources(),backgroundDrawable,options);

        }catch (OutOfMemoryError error){
            error.printStackTrace();
        }
        return drawable;
    }

    @Override
    public int addBodyBackgroundColor() {
        return 0;
    }

    @Override
    public RecyclerView.ItemDecoration addItemDecoration(){
        return null;
    }

    @Override
    public boolean hasBodyBackground() {
        return true;
    }

    @Override
    public boolean hasClickListener() {
        return true;
    }


    private View setUpHeaderScreen(LayoutInflater inflater,Activity activity,ActiveActorIdentityInformation intraUserLoginIdentity)  {
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


}
