package org.fermat.fermat_dap_android_wallet_asset_user.v3.common.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.util.BitmapWorkerTask;
import com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.R;

import org.fermat.fermat_dap_android_wallet_asset_user.v2.models.Asset;
import org.fermat.fermat_dap_android_wallet_asset_user.v3.common.filters.HomeCardAdapterFilter;
import org.fermat.fermat_dap_android_wallet_asset_user.v3.common.holders.HomeCardViewHolder;
import org.fermat.fermat_dap_android_wallet_asset_user.v3.fragments.HomeCardFragment;
import org.fermat.fermat_dap_api.layer.all_definition.util.DAPStandardFormats;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;

import java.util.List;

import static com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter.Currency.BITCOIN;
import static com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter.Currency.SATOSHI;

/**
 * Created by frank on 12/8/15.
 */
public class HomeCardAdapter extends FermatAdapter<Asset, HomeCardViewHolder> implements Filterable {

    private AssetUserWalletSubAppModuleManager manager;
    private ReferenceAppFermatSession<AssetUserWalletSubAppModuleManager> assetUserSession;
    private HomeCardFragment fragment;


    public HomeCardAdapter(HomeCardFragment fragment, Context context, List<Asset> digitalAssets, AssetUserWalletSubAppModuleManager manager,
                           ReferenceAppFermatSession<AssetUserWalletSubAppModuleManager> appSession) {
        super(context, digitalAssets);
        this.fragment = fragment;
        this.manager = manager;
        this.dataSet = digitalAssets;
        this.assetUserSession = appSession;
    }

    @Override
    protected HomeCardViewHolder createHolder(View itemView, int type) {
        return new HomeCardViewHolder(itemView, manager, context);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.dap_v3_wallet_asset_user_card;
    }

    @Override
    protected void bindHolder(HomeCardViewHolder holder, Asset data, int position) {
        bind(holder, data);
    }

    @Override
    public Filter getFilter() {
        return new HomeCardAdapterFilter(this.dataSet, this);
    }

    public void bind(HomeCardViewHolder holder, final Asset asset) {

        Bitmap bitmap;
        if (asset.getActorImage() != null && asset.getActorImage().length > 0) {
            bitmap = BitmapFactory.decodeByteArray(asset.getActorImage(), 0, asset.getActorImage().length);
        } else {
            bitmap = BitmapFactory.decodeResource(holder.res, R.drawable.img_detail_without_image);
        }
        bitmap = Bitmap.createScaledBitmap(bitmap, 45, 45, true);
        holder.homeIssuerImage.setImageDrawable(ImagesUtils.getRoundedBitmap(holder.res, bitmap));
        holder.cardActorName.setText(asset.getActorName());
        holder.cardTime.setText(asset.getFormattedDate());


        byte[] img = (asset.getImage() == null) ? new byte[0] : asset.getImage();
        BitmapWorkerTask bitmapWorkerTask = new BitmapWorkerTask(holder.cardAssetImage, holder.res, R.drawable.img_asset_without_image, false);
        bitmapWorkerTask.execute(img);

//  if negotiation
        if (asset.getAssetUserNegotiation() != null) {

            holder.homeIssuerImage.setImageDrawable(ImagesUtils.getRoundedBitmap(holder.res, bitmap));
            holder.cardActorName.setText(asset.getActorName());

            holder.normalV3Asset.setVisibility(View.GONE);
            holder.negotiationV3Asset.setVisibility(View.VISIBLE);
            holder.negotiationAssetName.setText(asset.getName());
            holder.v3NegotiationAssetPrice.setText(String.format("%s BTC", DAPStandardFormats.BITCOIN_FORMAT.format(
                    BitcoinConverter.convert(Double.valueOf(asset.getAssetUserNegotiation().getAmount()), SATOSHI, BITCOIN))));

            holder.acceptNegotiationButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    assetUserSession.setData("asset_data", asset);
                    fragment.doAcceptNegotiation();
                }
            });
            holder.rejectNegotiationButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    assetUserSession.setData("asset_data", asset);
                    fragment.doRejectNegotiation();
                }
            });


        } else {

            int image = (asset.getStatus().equals(Asset.Status.CONFIRMED)) ? R.drawable.detail_check : R.drawable.detail_uncheck;
            holder.cardConfirmedImage.setImageResource(image);
            holder.cardConfirmedText.setText((asset.getStatus().equals(Asset.Status.CONFIRMED)) ?
                    holder.res.getString(R.string.card_confirmed) : holder.res.getString(R.string.card_pending));
            holder.negotiationV3Asset.setVisibility(View.GONE);
            holder.normalV3Asset.setVisibility(View.VISIBLE);
            holder.cardAssetName.setText(asset.getName());
            holder.cardExpDate.setText(asset.getFormattedExpDate());

            initActions(holder, asset);

        }

        int imageLocked = R.drawable.locked;
        if (asset.getAssetUserWalletTransaction() != null && asset.getAssetUserWalletTransaction().isLocked()) {
            holder.cardConfirmedImage.setImageResource(imageLocked);
            holder.cardConfirmedText.setText(holder.res.getString(R.string.card_locked));
            holder.cardRedeemButton.setVisibility(View.GONE);
            holder.cardTransferButton.setVisibility(View.GONE);
            holder.cardAppropriateButton.setVisibility(View.GONE);
            holder.cardSellButton.setVisibility(View.GONE);
        }


    }

    private void initActions(HomeCardViewHolder holder, final Asset asset) {

        holder.cardRedeemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assetUserSession.setData("asset_data", asset);
                fragment.doRedeem();
            }
        });


        holder.cardTransferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assetUserSession.setData("asset_data", asset);
                fragment.doTransfer();
            }
        });


        holder.cardSellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assetUserSession.setData("asset_data", asset);
                fragment.doSell();
            }
        });

        holder.cardAppropriateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assetUserSession.setData("asset_data", asset);
                fragment.doAppropiate();
            }
        });

        holder.cardTransactionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assetUserSession.setData("asset_data", asset);
                fragment.doTransaction();
            }
        });

        if (asset.isRedeemable() && asset.getStatus().equals(Asset.Status.CONFIRMED)) {
            holder.cardRedeemButton.setVisibility(View.VISIBLE);
        } else {
            holder.cardRedeemButton.setVisibility(View.GONE);
        }
        if (asset.isTransferable() && asset.getStatus().equals(Asset.Status.CONFIRMED)) {
            holder.cardTransferButton.setVisibility(View.VISIBLE);
        } else {
            holder.cardTransferButton.setVisibility(View.GONE);
        }
        if (asset.isSaleable() && asset.getStatus().equals(Asset.Status.CONFIRMED)) {
            holder.cardSellButton.setVisibility(View.VISIBLE);
        } else {
            holder.cardSellButton.setVisibility(View.GONE);
        }
        if (asset.getStatus().equals(Asset.Status.CONFIRMED)) {
            holder.cardAppropriateButton.setVisibility(View.VISIBLE);
        } else {
            holder.cardAppropriateButton.setVisibility(View.GONE);
        }
    }
}
