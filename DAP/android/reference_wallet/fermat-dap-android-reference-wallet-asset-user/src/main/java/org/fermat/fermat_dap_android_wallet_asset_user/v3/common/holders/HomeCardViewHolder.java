package org.fermat.fermat_dap_android_wallet_asset_user.v3.common.holders;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_android_api.ui.util.BitmapWorkerTask;
import com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.R;

import org.fermat.fermat_dap_android_wallet_asset_user.v2.models.Asset;
import org.fermat.fermat_dap_api.layer.all_definition.util.DAPStandardFormats;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;

import static com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter.Currency.BITCOIN;
import static com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter.Currency.SATOSHI;

/**
 * Created by frank on 12/8/15.
 */
public class HomeCardViewHolder extends FermatViewHolder {
    private AssetUserWalletSubAppModuleManager manager;
    private Context context;

    public Resources res;

    public ImageView homeIssuerImage;
    public FermatTextView cardActorName;
    public FermatTextView cardTime;
    public ImageView cardConfirmedImage;
    public FermatTextView cardConfirmedText;
    public ImageView cardAssetImage;
    public FermatTextView cardAssetName;
    public FermatTextView cardExpDate;
    public ImageButton cardRedeemButton;
    public ImageButton cardTransferButton;
    public ImageButton cardAppropriateButton;
    public ImageButton cardSellButton;
    public ImageButton cardTransactionsButton;

    public View normalV3Asset;
    public View negotiationV3Asset;
    public FermatTextView negotiationAssetName;
    public FermatTextView v3NegotiationAssetPrice;

    public View acceptNegotiationButton;
    public View rejectNegotiationButton;

    /**
     * Constructor
     *
     * @param itemView
     */
    public HomeCardViewHolder(View itemView, AssetUserWalletSubAppModuleManager manager, Context context) {
        super(itemView);
        this.manager = manager;
        this.context = context;
        res = itemView.getResources();

        homeIssuerImage = (ImageView) itemView.findViewById(R.id.homeIssuerImage);
        cardActorName = (FermatTextView) itemView.findViewById(R.id.cardActorName);
        cardTime = (FermatTextView) itemView.findViewById(R.id.cardTime);
        cardConfirmedImage = (ImageView) itemView.findViewById(R.id.cardConfirmedImage);
        cardConfirmedText = (FermatTextView) itemView.findViewById(R.id.cardConfirmedText);
        cardAssetImage = (ImageView) itemView.findViewById(R.id.cardAssetImage);
        cardAssetName = (FermatTextView) itemView.findViewById(R.id.cardAssetName);
        cardExpDate = (FermatTextView) itemView.findViewById(R.id.cardExpDate);
        cardRedeemButton = (ImageButton) itemView.findViewById(R.id.cardRedeemButton);
        cardTransferButton = (ImageButton) itemView.findViewById(R.id.cardTransferButton);
        cardAppropriateButton = (ImageButton) itemView.findViewById(R.id.cardAppropriateButton);
        cardSellButton = (ImageButton) itemView.findViewById(R.id.cardSellButton);
        cardTransactionsButton = (ImageButton) itemView.findViewById(R.id.cardTransactionsButton);
        normalV3Asset = itemView.findViewById(R.id.normalV3Asset);
        negotiationV3Asset = itemView.findViewById(R.id.negotiationV3Asset);
        negotiationAssetName = (FermatTextView) itemView.findViewById(R.id.negotiationAssetName);
        v3NegotiationAssetPrice = (FermatTextView) itemView.findViewById(R.id.v3NegotiationAssetPrice);
        acceptNegotiationButton =  itemView.findViewById(R.id.v3AcceptNegotiationButton);
        rejectNegotiationButton =  itemView.findViewById(R.id.v3RejectNegotiationButton);
    }

    public void bind(final Asset asset, View.OnClickListener onClickListenerRedeem,
                     View.OnClickListener onClickListenerTransfer, View.OnClickListener onClickListenerAppropriate,
                     View.OnClickListener onClickListenerSell, View.OnClickListener onClickListenerTransactions,
                     View.OnClickListener onClickAcceptNegotiation, View.OnClickListener onClickListenerRejectNegotiation) {

        Bitmap bitmap;
        if (asset.getActorImage() != null && asset.getActorImage().length > 0) {
            bitmap = BitmapFactory.decodeByteArray(asset.getActorImage(), 0, asset.getActorImage().length);
        } else {
            bitmap = BitmapFactory.decodeResource(res, R.drawable.img_detail_without_image);
        }
        bitmap = Bitmap.createScaledBitmap(bitmap, 45, 45, true);
        homeIssuerImage.setImageDrawable(ImagesUtils.getRoundedBitmap(res, bitmap));
        cardActorName.setText(asset.getActorName());
        cardTime.setText(asset.getFormattedDate());


        byte[] img = (asset.getImage() == null) ? new byte[0] : asset.getImage();
        BitmapWorkerTask bitmapWorkerTask = new BitmapWorkerTask(cardAssetImage, res, R.drawable.img_asset_without_image, false);
        bitmapWorkerTask.execute(img);

//  if negotiation
        if(asset.getAssetUserNegotiation() != null){

            homeIssuerImage.setImageDrawable(ImagesUtils.getRoundedBitmap(res, bitmap));
            cardActorName.setText(asset.getActorName());

            normalV3Asset.setVisibility(View.GONE);
            negotiationV3Asset.setVisibility(View.VISIBLE);
            negotiationAssetName.setText(asset.getName());
            v3NegotiationAssetPrice.setText(String.format("%s BTC", DAPStandardFormats.BITCOIN_FORMAT.format(
                    BitcoinConverter.convert(Double.valueOf(asset.getAssetUserNegotiation().getAmount()), SATOSHI, BITCOIN))));

            acceptNegotiationButton.setOnClickListener(onClickAcceptNegotiation);
            rejectNegotiationButton.setOnClickListener(onClickListenerRejectNegotiation);


        }else {

            int image = (asset.getStatus().equals(Asset.Status.CONFIRMED)) ? R.drawable.detail_check : R.drawable.detail_uncheck;
            cardConfirmedImage.setImageResource(image);
            cardConfirmedText.setText((asset.getStatus().equals(Asset.Status.CONFIRMED)) ? res.getString(R.string.card_confirmed) : res.getString(R.string.card_pending));

            cardAssetName.setText(asset.getName());
            cardExpDate.setText(asset.getFormattedExpDate());

            initActions(asset, onClickListenerRedeem, onClickListenerTransfer, onClickListenerAppropriate,
                    onClickListenerSell, onClickListenerTransactions);
        }

        int imageLocked = R.drawable.locked;
        if (asset.getAssetUserWalletTransaction().isLocked()){
            cardConfirmedImage.setImageResource(imageLocked);
            cardConfirmedText.setText(res.getString(R.string.card_locked));
            cardRedeemButton.setVisibility(View.GONE);
            cardTransferButton.setVisibility(View.GONE);
            cardAppropriateButton.setVisibility(View.GONE);
            cardSellButton.setVisibility(View.GONE);
        }
    }

    private void initActions(Asset asset, View.OnClickListener onClickListenerRedeem,
                             View.OnClickListener onClickListenerTransfer, View.OnClickListener onClickListenerAppropriate,
                             View.OnClickListener onClickListenerSell, View.OnClickListener onClickListenerTransactions) {
        if (asset.isRedeemable() && asset.getStatus().equals(Asset.Status.CONFIRMED)) {
            cardRedeemButton.setVisibility(View.VISIBLE);
            cardRedeemButton.setOnClickListener(onClickListenerRedeem);
        } else {
            cardRedeemButton.setVisibility(View.GONE);
        }

        if (asset.isTransferable() && asset.getStatus().equals(Asset.Status.CONFIRMED)) {
            cardTransferButton.setVisibility(View.VISIBLE);
            cardTransferButton.setOnClickListener(onClickListenerTransfer);
        } else {
            cardTransferButton.setVisibility(View.GONE);
        }

        if (asset.isSaleable() && asset.getStatus().equals(Asset.Status.CONFIRMED)) {
            cardSellButton.setVisibility(View.VISIBLE);
            cardSellButton.setOnClickListener(onClickListenerSell);
        } else {
            cardSellButton.setVisibility(View.GONE);
        }

        if (asset.getStatus().equals(Asset.Status.CONFIRMED)) {
            cardAppropriateButton.setVisibility(View.VISIBLE);
            cardAppropriateButton.setOnClickListener(onClickListenerAppropriate);
            cardTransactionsButton.setOnClickListener(onClickListenerTransactions);
        } else {
            cardAppropriateButton.setVisibility(View.GONE);
        }
    }

    public ImageButton getCardRedeemButton() {
        return cardRedeemButton;
    }
}
