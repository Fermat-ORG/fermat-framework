package org.fermat.fermat_dap_android_wallet_asset_issuer.v3.common.holders;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.R;

import org.fermat.fermat_dap_android_wallet_asset_issuer.models.Transaction;
import org.fermat.fermat_dap_api.layer.all_definition.util.DAPStandardFormats;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces.AssetIssuerWalletSupAppModuleManager;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.TransactionType;

import static com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter.Currency.BITCOIN;
import static com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter.Currency.SATOSHI;

/**
 * Created by frank on 12/8/15.
 */
public class TransactionsViewHolder extends FermatViewHolder {
    private AssetIssuerWalletSupAppModuleManager manager;
    private Context context;
    private Resources res;

    private ImageView actorImage;
    private FermatTextView actorNameText;
    private FermatTextView typeByText;
    private FermatTextView quantityText;
    private FermatTextView amountText;
    private FermatTextView dateText;
    private FermatTextView balanceTypeText;
    private FermatTextView memoText;

    /**
     * Constructor
     *
     * @param itemView
     */
    public TransactionsViewHolder(View itemView, AssetIssuerWalletSupAppModuleManager manager, Context context) {
        super(itemView);
        this.manager = manager;
        this.context = context;
        res = itemView.getResources();

        actorImage = (ImageView) itemView.findViewById(R.id.transactionActorImage);
        actorNameText = (FermatTextView) itemView.findViewById(R.id.transactionActorNameText);
        typeByText = (FermatTextView) itemView.findViewById(R.id.transactionTypeByText);
        dateText = (FermatTextView) itemView.findViewById(R.id.transactionDateText);
        quantityText = (FermatTextView) itemView.findViewById(R.id.transactionQuantityText);
        amountText = (FermatTextView) itemView.findViewById(R.id.transactionAmountText);
        balanceTypeText = (FermatTextView) itemView.findViewById(R.id.transactionBalanceTypeText);
        memoText = (FermatTextView) itemView.findViewById(R.id.transactionMemoText);
    }

    public void bind(final Transaction transaction) {
//        byte[] img = (transaction.getActorImage() == null) ? new byte[0] : transaction.getActorImage();
//        BitmapWorkerTask bitmapWorkerTask = new BitmapWorkerTask(actorImage, res, R.drawable.img_asset_without_image, false);
//        bitmapWorkerTask.execute(img);

        if (transaction.getActorImage() != null && transaction.getActorImage().length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(transaction.getActorImage(), 0, transaction.getActorImage().length);
            bitmap = Bitmap.createScaledBitmap(bitmap, 120, 120, true);
            actorImage.setImageDrawable(ImagesUtils.getRoundedBitmap(res, bitmap));
        }

//        Bitmap bitmap;
//        if (transaction.getActorImage() != null && transaction.getActorImage().length > 0) {
//            bitmap = BitmapFactory.decodeByteArray(transaction.getActorImage(), 0, transaction.getActorImage().length);
//        } else {
//            bitmap = BitmapFactory.decodeResource(res, R.drawable.img_detail_without_image);
//        }
//        bitmap = Bitmap.createScaledBitmap(bitmap, 45, 45, true);
//        actorImage.setImageDrawable(ImagesUtils.getRoundedBitmap(res, bitmap));

        actorNameText.setText(transaction.getActorName());
        typeByText.setText((transaction.getTransactionType() == TransactionType.CREDIT) ? "Received by" : "Sent to");

        String symbol;
        String confirmedStr = res.getString(R.string.dap_issuer_wallet_confirmed);
        String pendingStr = res.getString(R.string.dap_issuer_wallet_pending);
        if (transaction.getTransactionType() == TransactionType.CREDIT) {
            symbol = "+ ";
            amountText.setTextColor(res.getColor(R.color.fab_material_green_900));
            quantityText.setTextColor(res.getColor(R.color.fab_material_green_900));
            balanceTypeText.setText((transaction.getBalanceType() == BalanceType.AVAILABLE) ? confirmedStr : pendingStr);
        } else {
            symbol = "- ";
            amountText.setTextColor(res.getColor(R.color.fab_material_red_900));
            quantityText.setTextColor(res.getColor(R.color.fab_material_red_900));
            balanceTypeText.setText((transaction.getBalanceType() == BalanceType.AVAILABLE) ? pendingStr : confirmedStr);
        }

        double amount = BitcoinConverter.convert(transaction.getAmount(), SATOSHI, BITCOIN);
        amountText.setText(symbol + DAPStandardFormats.BITCOIN_FORMAT.format(amount) + " BTC");

        if (balanceTypeText.getText().toString().equals(pendingStr)) {
            balanceTypeText.setTextColor(res.getColor(R.color.dap_issuer_wallet_blue_text));
        } else {
            balanceTypeText.setTextColor(res.getColor(R.color.dap_issuer_wallet_black_text));
        }

        dateText.setText(transaction.getFormattedDate());
        if (transaction.getMemo() != null) memoText.setText(transaction.getMemo());
    }
}
