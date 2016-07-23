package com.bitdubai.sub_app.intra_user_community.holders;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.Views.SquareImageView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetActiveLoginIdentityException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserInformation;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.enums.ProfileStatus;
import com.bitdubai.sub_app.intra_user_community.R;
import com.bitdubai.sub_app.intra_user_community.common.popups.ConnectDialog;

/**
 * Created by natalia on 13/07/16.
 */
public class  AvailableActorsViewHolder extends FermatViewHolder {

    public SquareImageView thumbnail;
    public FermatTextView name;
    public ImageView connectionState;
    public FermatTextView row_connection_state;
    public ProgressBar progressBar;
    private TextView button_add;
    private Resources res;
    private TextView response;


    /**
     * Constructor
     *
     * @param itemView cast elements in layout
     * @param type     the view older type ID
     */
    public AvailableActorsViewHolder(View itemView, int type) {
        super(itemView, type);
        res = itemView.getResources();
        response = (TextView) itemView.findViewById(R.id.response);
        button_add = (TextView) itemView.findViewById(R.id.button_add);
        connectionState = (ImageView) itemView.findViewById(R.id.connection_state);
        row_connection_state = (FermatTextView) itemView.findViewById(R.id.connection_state_user);
        thumbnail = (SquareImageView) itemView.findViewById(R.id.profile_image);
        name = (FermatTextView) itemView.findViewById(R.id.community_name);
       // progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);


    }

    public void bind(IntraUserInformation data) {

        row_connection_state.setText((data.getState().equals(ProfileStatus.ONLINE)) ? "Online" : "offline");
        if(data.getState().equals(ProfileStatus.OFFLINE))
        {
            //button_add.setBackgroundColor(Color.RED);
            button_add.setVisibility(View.GONE);
            response.setText("OFFLINE");
            response.setTextColor(Color.RED);
            response.setVisibility(View.VISIBLE);
        }
        // else{
        //   button_add.setBackgroundColor(Color.parseColor("#21386D"));
        //  button_add.setVisibility(View.VISIBLE);
        //  }

        if (data.getConnectionState() != null) {

            switch (data.getConnectionState()) {
                case CONNECTED:
                        response.setVisibility(View.VISIBLE);
                        response.setText("IS A CONTACT");
                        response.setTextColor(Color.parseColor("#21386D"));
                        button_add.setVisibility(View.GONE);
                    break;
                case BLOCKED_LOCALLY:
                    break;
                case BLOCKED_REMOTELY:
                    break;
                case CANCELLED_LOCALLY:
                    break;
                case CANCELLED_REMOTELY:
                       //connectionState.setImageResource(R.drawable.icon_contact_no_conect);
                        response.setText("REQUEST CANCELLED");
                        response.setVisibility(View.VISIBLE);
                    response.setTextColor(Color.parseColor("#21386D"));
                        button_add.setVisibility(View.GONE);

                    break;
                case NO_CONNECTED:
                    if(data.getState().equals(ProfileStatus.OFFLINE))
                    {
                        //button_add.setBackgroundColor(Color.RED);
                        button_add.setVisibility(View.GONE);
                        response.setText("OFFLINE");
                        response.setTextColor(Color.RED);
                        response.setVisibility(View.VISIBLE);
                    }else
                    {
                        response.setText("");
                        response.setVisibility(View.GONE);
                        button_add.setVisibility(View.VISIBLE);
                    }


                    break;
                case DENIED_LOCALLY:
                    break;
                case DENIED_REMOTELY:

                        response.setText("DENIED BY CONTACT");
                    response.setTextColor(Color.parseColor("#21386D"));
                        response.setVisibility(View.VISIBLE);
                        button_add.setVisibility(View.GONE);

                    break;
                case DISCONNECTED_LOCALLY:
                    break;
                case DISCONNECTED_REMOTELY:

                        response.setText("IS NOT CONNECTED");
                    response.setTextColor(Color.parseColor("#21386D"));
                        //connectionState.setImageResource(R.drawable.icon_contact_no_conect);
                        response.setVisibility(View.VISIBLE);
                        button_add.setVisibility(View.GONE);

                    break;
                case ERROR:
                    break;
                case INTRA_USER_NOT_FOUND:
                    break;
                case PENDING_LOCALLY_ACCEPTANCE:
                    break;
                case PENDING_REMOTELY_ACCEPTANCE:

                        response.setText("REQUEST SENT");
                    response.setTextColor(Color.parseColor("#21386D"));
                        //connectionState.setImageResource(R.drawable.icon_contact_standby);
                        response.setVisibility(View.VISIBLE);
                        button_add.setVisibility(View.GONE);

                    break;
                default:
                    if (response.getVisibility() == View.VISIBLE)
                        response.setVisibility(View.GONE);
                        button_add.setVisibility(View.VISIBLE);
                    break;
            }


          name.setText(data.getName());
            byte[] profileImage = data.getProfileImage();
            if (profileImage != null && profileImage.length > 0) {
                //Bitmap bitmap = BitmapFactory.decodeByteArray(profileImage, 0, profileImage.length);
                //bitmap = Bitmap.createScaledBitmap(bitmap, 480, 480, true);
               thumbnail.setImageDrawable(  getImgDrawable(profileImage));
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
