package org.fermat.fermat_dap_android_wallet_redeem_point.v3.holders;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.R;

import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_redeem_point.interfaces.AssetRedeemPointWalletSubAppModule;


/**
 * Created by Jinmy Bohorquez on 19/04/16.
 */
public class RedeemCardViewHolder extends FermatViewHolder {

    private AssetRedeemPointWalletSubAppModule manager;
    private Context context;
    public Resources res;

    public ImageView cardAssetImage;
    public FermatTextView cardAssetName;
    public FermatTextView cardTime;
    public ImageView cardActorUserImage;
    public FermatTextView cardActorName;
    public ImageView cardConfirmedImage;
    public ImageView cardStatusImage;


    public ImageButton cardDeliverButton;
    public ImageButton cardAcceptButton;
    public ImageButton cardRejectButton;

    public View redeemNegotiationV3Asset;
    public View confirmedV3Asset;

    public View actionButtons;


    public RedeemCardViewHolder(View itemView, AssetRedeemPointWalletSubAppModule manager, Context context) {
        super(itemView);
        this.manager = manager;
        this.context = context;
        res = itemView.getResources();

        cardAssetImage = (ImageView) itemView.findViewById(R.id.cardAssetImage);
        cardAssetName = (FermatTextView) itemView.findViewById(R.id.cardAssetName);
        cardTime = (FermatTextView) itemView.findViewById(R.id.cardTime);
        cardActorUserImage = (ImageView) itemView.findViewById(R.id.cardActorUserImage);
        cardActorName = (FermatTextView) itemView.findViewById(R.id.cardActorName);
        cardConfirmedImage = (ImageView) itemView.findViewById(R.id.cardConfirmedImage);
        cardStatusImage = (ImageView) itemView.findViewById(R.id.cardStatusImage);
        cardDeliverButton = (ImageButton) itemView.findViewById(R.id.cardDeliverButton);
        cardAcceptButton = (ImageButton) itemView.findViewById(R.id.cardAcceptButton);
        cardRejectButton = (ImageButton) itemView.findViewById(R.id.cardRejectButton);
        redeemNegotiationV3Asset = itemView.findViewById(R.id.redeemPendingV3Asset);
        confirmedV3Asset = itemView.findViewById(R.id.confirmedV3Asset);
        actionButtons = itemView.findViewById(R.id.actionButtons);

    }


}
