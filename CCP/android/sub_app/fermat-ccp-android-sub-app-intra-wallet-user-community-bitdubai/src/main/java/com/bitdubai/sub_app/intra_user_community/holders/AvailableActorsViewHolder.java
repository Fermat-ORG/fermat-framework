package com.bitdubai.sub_app.intra_user_community.holders;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.Views.SquareImageView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserInformation;
import com.bitdubai.sub_app.intra_user_community.R;

/**
 * Created by natalia on 13/07/16.
 */
public class AvailableActorsViewHolder extends FermatViewHolder {

    public SquareImageView thumbnail;
    public FermatTextView name;
    public ImageView connectionState;
    public FermatTextView row_connection_state;
    public ProgressBar progressBar;

    private Resources res;


    /**
     * Constructor
     *
     * @param itemView cast elements in layout
     * @param type     the view older type ID
     */
    public AvailableActorsViewHolder(View itemView, int type) {
        super(itemView, type);
        res = itemView.getResources();

        connectionState = (ImageView) itemView.findViewById(R.id.connection_state);
        row_connection_state = (FermatTextView) itemView.findViewById(R.id.connection_state_user);
        thumbnail = (SquareImageView) itemView.findViewById(R.id.profile_image);
        name = (FermatTextView) itemView.findViewById(R.id.community_name);
       // progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);

    }

    public void bind(IntraUserInformation data) {
        if (data.getConnectionState() != null) {

            switch (data.getConnectionState()) {
                case CONNECTED:
                    if (connectionState.getVisibility() == View.GONE)
                        connectionState.setVisibility(View.VISIBLE);
                    break;
                case BLOCKED_LOCALLY:
                    break;
                case BLOCKED_REMOTELY:
                    break;
                case CANCELLED_LOCALLY:
                    break;
                case CANCELLED_REMOTELY:
                    if (connectionState.getVisibility() == View.GONE){
                        connectionState.setImageResource(R.drawable.icon_contact_no_conect);
                        connectionState.setVisibility(View.VISIBLE);
                    }
                    break;
                case NO_CONNECTED:
                    break;
                case DENIED_LOCALLY:
                    break;
                case DENIED_REMOTELY:
                    if (connectionState.getVisibility() == View.GONE){
                        connectionState.setImageResource(R.drawable.icon_contact_no_conect);
                        connectionState.setVisibility(View.VISIBLE);
                    }
                    break;
                case DISCONNECTED_LOCALLY:
                    break;
                case DISCONNECTED_REMOTELY:
                    if (connectionState.getVisibility() == View.GONE){
                        connectionState.setImageResource(R.drawable.icon_contact_no_conect);
                        connectionState.setVisibility(View.VISIBLE);
                    }
                    break;
                case ERROR:
                    break;
                case INTRA_USER_NOT_FOUND:
                    break;
                case PENDING_LOCALLY_ACCEPTANCE:
                    break;
                case PENDING_REMOTELY_ACCEPTANCE:
                    if (connectionState.getVisibility() == View.GONE){
                        connectionState.setImageResource(R.drawable.icon_contact_standby);
                        connectionState.setVisibility(View.VISIBLE);
                    }
                    break;
                default:
                    if (connectionState.getVisibility() == View.VISIBLE)
                        connectionState.setVisibility(View.GONE);
                    break;
            }
            row_connection_state.setText(data.getState());
            if(data.getState().equals("Offline"))
               row_connection_state.setTextColor(Color.RED);
            else
               row_connection_state.setTextColor(Color.WHITE);
          name.setText(data.getName());
            byte[] profileImage = data.getProfileImage();
            if (profileImage != null && profileImage.length > 0) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(profileImage, 0, profileImage.length);
                bitmap = Bitmap.createScaledBitmap(bitmap, 480, 480, true);
               thumbnail.setImageBitmap(bitmap);
            }else{
                thumbnail.setVisibility(View.GONE);

            }
        } else {
            connectionState.setVisibility(View.INVISIBLE);

        }


    }

    private Drawable getImgDrawable(byte[] customerImg) {
        if (customerImg != null && customerImg.length > 0)
            return ImagesUtils.getRoundedBitmap(res, customerImg);

        return ImagesUtils.getRoundedBitmap(res, R.drawable.ic_profile_male);
    }
}
