package com.bitdubai.sub_app.wallet_manager.commons.model;

import android.content.res.Resources;
import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.ui.expandableRecicler.ChildViewHolder;
import com.bitdubai.fermat_android_api.ui.util.BitmapWorkerTaskWithRes;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledApp;
import com.bitdubai.fermat_dmp.wallet_manager.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Matias Furszyfer on 28/10/15.
 */
public class CommunityViewHolder extends ChildViewHolder {

    private Resources res;
    private View itemView;

    private ImageView imgBanner;
    BitmapWorkerTaskWithRes bitmapWorkerTask;



    /**
     * Public constructor for the custom child ViewHolder
     *
     * @param itemView the child ViewHolder's view
     */
    public CommunityViewHolder(View itemView) {
        super(itemView);

        this.itemView = itemView;
        res = itemView.getResources();
        imgBanner = (ImageView) itemView.findViewById(R.id.imgBanner);
    }

    public CommunityViewHolder bind(InstalledApp installedApp) {

        try {
            if (installedApp.getBannerRes() != 0) {
//            contactIcon.setImageDrawable(ImagesUtils.getRoundedBitmap(res,photo));
                bitmapWorkerTask = new BitmapWorkerTaskWithRes(imgBanner,res,installedApp.getBannerRes(),false);
                bitmapWorkerTask.execute(installedApp.getBannerRes());
            } else
                Picasso.with(imgBanner.getContext()).load(R.drawable.ic_profile_male).into(imgBanner);
        }catch (Exception e){
            Picasso.with(imgBanner.getContext()).load(R.drawable.ic_profile_male).into(imgBanner);

        }
        return this;
    }

    public void cancelTaskIfRunning(){
        try{
            bitmapWorkerTask.cancel(true);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public BitmapWorkerTaskWithRes getTask() {
        return bitmapWorkerTask;
    }
}
