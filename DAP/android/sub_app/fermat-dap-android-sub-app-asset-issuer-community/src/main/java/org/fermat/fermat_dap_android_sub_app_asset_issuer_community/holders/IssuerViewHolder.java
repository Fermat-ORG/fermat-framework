package org.fermat.fermat_dap_android_sub_app_asset_issuer_community.holders;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.Views.SquareImageView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_dap_android_sub_app_asset_issuer_community_bitdubai.R;

/**
 * Created by francisco on 14/10/15.
 */
public class IssuerViewHolder extends FermatViewHolder {

    public CheckBox connect;
    public SquareImageView thumbnail;
    public FermatTextView name;
    public FermatTextView status;
    //public FermatTextView extendedPublicKey;
    public ImageView connectedStateConnected;
    public ImageView connectedStateWaiting;
    public ImageView connectedStateDenied;
    public ProgressBar progressBar;

    /**
     * Constructor
     *
     * @param itemView
     */
    public IssuerViewHolder(View itemView) {
        super(itemView);
        thumbnail = (SquareImageView) itemView.findViewById(R.id.profile_image);
        name = (FermatTextView) itemView.findViewById(R.id.community_name);
        connect = (CheckBox) itemView.findViewById(R.id.connect);

        status = (FermatTextView) itemView.findViewById(R.id.status);
        //extendedPublicKey = (FermatTextView) itemView.findViewById(R.id.extendedPublicKey);
        connectedStateConnected = (ImageView) itemView.findViewById(R.id.connection_state_connected);
        connectedStateWaiting = (ImageView) itemView.findViewById(R.id.connection_state_waiting);
        connectedStateDenied = (ImageView) itemView.findViewById(R.id.connection_state_denied);
        progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
    }
}
