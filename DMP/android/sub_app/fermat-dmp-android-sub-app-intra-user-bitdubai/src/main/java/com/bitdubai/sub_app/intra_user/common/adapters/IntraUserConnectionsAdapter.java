package com.bitdubai.sub_app.intra_user.common.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_store.enums.InstallationStatus;
import com.bitdubai.sub_app.intra_user.common.UtilsFuncs;
import com.bitdubai.sub_app.intra_user.common.Views.RoundedDrawable;
import com.bitdubai.sub_app.intra_user.common.models.IntraUserConnectionListItem;
import com.intra_user.bitdubai.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Matias Furszyfer on 2015.08.31..
 */
public class IntraUserConnectionsAdapter extends FermatAdapter<IntraUserConnectionListItem, IntraUserConnectionsAdapter.IntraUserItemViewHolder> {


    protected IntraUserConnectionsAdapter(Context context) {
        super(context);
    }

    public IntraUserConnectionsAdapter(Context context, ArrayList<IntraUserConnectionListItem> dataSet) {
        super(context, dataSet);
    }

    /**
     * Create a new holder instance
     *
     * @param itemView View object
     * @param type     int type
     * @return ViewHolder
     */
    @Override
    protected IntraUserItemViewHolder createHolder(View itemView, int type) {
        //preguntar esto del type
        return new IntraUserItemViewHolder(itemView);
    }

    /**
     * Get custom layout to use it.
     *
     * @return int Layout Resource id: Example: R.layout.row_item
     */
    @Override
    protected int getCardViewResource() {
        return R.layout.intra_user_connection_item;
    }

    /**
     * Bind ViewHolder
     *
     * @param holder   ViewHolder object
     * @param data     Object data to render
     * @param position position to render
     */
    @Override
    protected void bindHolder(IntraUserItemViewHolder holder, IntraUserConnectionListItem data, int position) {
        holder.txtView_profile_name.setText(data.getName());
        if(data.getProfileImage()!=null){
            //holder.imageView_profile_connection.setImageDrawable(new RoundedDrawable(BitmapFactory.decodeByteArray(data.getProfileImage(), 0, data.getProfileImage().length), holder.imageView_profile_connection));
            holder.imageView_profile_connection.setImageBitmap(getRoundedShape(BitmapFactory.decodeByteArray(data.getProfileImage(), 0, data.getProfileImage().length)));

        }else{
            holder.imageView_profile_connection.setImageBitmap(getRoundedShape(BitmapFactory.decodeByteArray(data.getProfileImage(), 0, data.getProfileImage().length)));
            //holder.imageView_profile_connection.setImageDrawable(new BitmapDrawable(BitmapFactory.decodeByteArray(data.getProfileImage(), 0, data.getProfileImage().length), holder.imageView_profile_connection));

        }
        holder.txtView_profile_phrase.setText(data.getProfilePhrase());
        holder.txtView_profile_status.setText(data.getConnectionStatus());
    }

    class IntraUserItemViewHolder extends FermatViewHolder {
        ImageView imageView_profile_connection;
        FermatTextView txtView_profile_name;
        FermatTextView txtView_profile_phrase;
        FermatTextView txtView_profile_status;

        protected IntraUserItemViewHolder(View itemView) {
            super(itemView);

            imageView_profile_connection = (ImageView) itemView.findViewById(R.id.imageView_profile_connection);
            txtView_profile_name = (FermatTextView) itemView.findViewById(R.id.txtView_profile_name);
            txtView_profile_phrase = (FermatTextView) itemView.findViewById(R.id.txtView_profile_phrase);
            txtView_profile_status = (FermatTextView) itemView.findViewById(R.id.txtView_profile_status);
        }
    }

    public Bitmap getRoundedShape(Bitmap scaleBitmapImage) {
        int targetWidth = 50;
        int targetHeight = 50;
        Bitmap targetBitmap = Bitmap.createBitmap(targetWidth,
                targetHeight,Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(targetBitmap);
        Path path = new Path();
        path.addCircle(((float) targetWidth - 1) / 2,
                ((float) targetHeight - 1) / 2,
                (Math.min(((float) targetWidth),
                        ((float) targetHeight)) / 2),
                Path.Direction.CCW);

        canvas.clipPath(path);
        Bitmap sourceBitmap = scaleBitmapImage;
        canvas.drawBitmap(sourceBitmap,
                new Rect(0, 0, sourceBitmap.getWidth(),
                        sourceBitmap.getHeight()),
                new Rect(0, 0, targetWidth, targetHeight), null);
        return targetBitmap;
    }

}
