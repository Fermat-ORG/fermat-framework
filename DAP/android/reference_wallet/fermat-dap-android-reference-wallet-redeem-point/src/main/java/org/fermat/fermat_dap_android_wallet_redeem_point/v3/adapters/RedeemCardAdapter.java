package org.fermat.fermat_dap_android_wallet_redeem_point.v3.adapters;

import android.content.Context;
import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.util.BitmapWorkerTask;
import com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.R;

import org.fermat.fermat_dap_android_wallet_redeem_point.models.DigitalAsset;
import org.fermat.fermat_dap_android_wallet_redeem_point.v3.filters.RedeemHomeCardAdapterFilter;
import org.fermat.fermat_dap_android_wallet_redeem_point.v3.fragments.RedeemHomeCardFragment;
import org.fermat.fermat_dap_android_wallet_redeem_point.v3.holders.RedeemCardViewHolder;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_redeem_point.RedeemPointSettings;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_redeem_point.interfaces.AssetRedeemPointWalletSubAppModule;

import java.util.List;

/**
 * Created by Jinmy Bohorquez on 19/04/16.
 */
public class RedeemCardAdapter extends FermatAdapter<DigitalAsset, RedeemCardViewHolder> implements Filterable {

    ReferenceAppFermatSession<AssetRedeemPointWalletSubAppModule> assetRedeemSession;
    private RedeemHomeCardFragment fragment;
    private List<DigitalAsset> allDigitalAssets;


    public RedeemCardAdapter(RedeemHomeCardFragment fragment, Context context, List<DigitalAsset> digitalAssets, ReferenceAppFermatSession<AssetRedeemPointWalletSubAppModule> assetRedeemSession) {
        super(context, digitalAssets);
        this.fragment = fragment;
        this.dataSet = digitalAssets;
        this.assetRedeemSession = assetRedeemSession;
        this.allDigitalAssets = digitalAssets;
    }

    @Override
    protected RedeemCardViewHolder createHolder(View itemView, int type) {
        return new RedeemCardViewHolder(itemView, assetRedeemSession.getModuleManager(), context);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.dap_v3_wallet_asset_redeem_point_home_card_item;
    }

    @Override
    protected void bindHolder(RedeemCardViewHolder holder, DigitalAsset data, int position) {
        bind(holder, data);
    }

    @Override
    public Filter getFilter() {
        return new RedeemHomeCardAdapterFilter(this.allDigitalAssets, this);
    }

    public void bind(RedeemCardViewHolder holder, final DigitalAsset asset) {
        RedeemPointSettings settings = null;
        Boolean assetNotificationEnabled = false;
        try {
            settings = assetRedeemSession.getModuleManager().getSettingsManager().loadAndGetSettings(assetRedeemSession.getAppPublicKey());
            assetNotificationEnabled = settings.getAssetNotificationEnabled();
        } catch (Exception e) {
            settings = null;
        }
        byte[] imgAsset = (asset.getImage() == null) ? new byte[0] : asset.getImage();
        BitmapWorkerTask bitmapWorkerTaskAsset = new BitmapWorkerTask(holder.cardAssetImage, holder.res, R.drawable.img_asset_without_image, true);
        bitmapWorkerTaskAsset.execute(imgAsset);

        holder.cardAssetName.setText(asset.getName());
        holder.cardTime.setText(asset.getFormattedDate());

        byte[] img = (asset.getImageActorUserFrom() == null) ? new byte[0] : asset.getImageActorUserFrom(); /*modificar modelo Digital Asset*/
        BitmapWorkerTask bitmapWorkerTask = new BitmapWorkerTask(holder.cardActorUserImage,
                holder.res, R.drawable.img_asset_without_image, false);
        bitmapWorkerTask.execute(img);

        holder.cardActorName.setText(asset.getActorUserNameFrom());

        holder.cardActorUserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assetRedeemSession.setData("asset_data", asset);
                fragment.activityChange();

            }
        });


        if (asset.getStatus() == DigitalAsset.Status.PENDING && assetNotificationEnabled) {

            holder.actionButtons.setVisibility(View.VISIBLE);
            holder.confirmedV3Asset.setVisibility(View.GONE);
            holder.cardStatusImage.setVisibility(View.VISIBLE);

            holder.cardAcceptButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    assetRedeemSession.setData("asset_data", asset);
                    fragment.doAcceptAsset();
                }
            });
            holder.cardRejectButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    assetRedeemSession.setData("asset_data", asset);
                    fragment.doRejectAsset();
                }
            });

        } else if (asset.getStatus() == DigitalAsset.Status.PENDING && !assetNotificationEnabled) {
            holder.actionButtons.setVisibility(View.GONE);
            holder.confirmedV3Asset.setVisibility(View.GONE);
            holder.cardStatusImage.setVisibility(View.VISIBLE);

        } else if (asset.getStatus() == DigitalAsset.Status.CONFIRMED) {
            holder.cardStatusImage.setVisibility(View.GONE);
            holder.actionButtons.setVisibility(View.GONE);
            holder.confirmedV3Asset.setVisibility(View.VISIBLE);

        }
    }
}
