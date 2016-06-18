package org.fermat.fermat_dap_android_wallet_redeem_point.v3.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.util.BitmapWorkerTask;
import com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.R;

import org.fermat.fermat_dap_android_wallet_redeem_point.models.DigitalAsset;
import org.fermat.fermat_dap_android_wallet_redeem_point.v3.fragments.RedeemPointDetailFragment;

/**
 * Created by Jinmy Bohorquez on 25/04/16.
 */
public class RedeemDetailAdapter extends PagerAdapter {
    private DigitalAsset digitalAsset;
    private Context context;
    private LayoutInflater layoutInflater;
    private RedeemPointDetailFragment fragment;
    private ImageView redeemDetailItemImageView;
    private FermatTextView redeemDetailItemTextView;
    private Resources res;


    public RedeemDetailAdapter(RedeemPointDetailFragment fragment, Context context, DigitalAsset digitalAsset) {
        this.fragment = fragment;
        this.digitalAsset = digitalAsset;
        this.context = context;

    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view == (RelativeLayout) object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.dap_v3_wallet_asset_redeem_detail_item, container, false);
        redeemDetailItemImageView = (ImageView) item_view.findViewById(R.id.redeemDetailItemImageView);
        redeemDetailItemTextView = (FermatTextView) item_view.findViewById(R.id.redeemDetailItemTextView);
        res = item_view.getResources();


        if (position == 0) {
            byte[] img = (digitalAsset.getImageActorUserFrom() == null) ? new byte[0] : digitalAsset.getImageActorUserFrom();

            BitmapWorkerTask bitmapWorkerTask = new BitmapWorkerTask(redeemDetailItemImageView,
                    res, R.drawable.img_asset_without_image, false);
            bitmapWorkerTask.execute(img);

            redeemDetailItemTextView.setText(digitalAsset.getActorUserNameFrom());

        } else {
            byte[] img = (digitalAsset.getImage() == null) ? new byte[0] : digitalAsset.getImage();

            BitmapWorkerTask bitmapWorkerTask = new BitmapWorkerTask(redeemDetailItemImageView,
                    res, R.drawable.img_asset_without_image, false);
            bitmapWorkerTask.execute(img);

            redeemDetailItemTextView.setText("");
        }

        container.addView(item_view);
        return item_view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}
