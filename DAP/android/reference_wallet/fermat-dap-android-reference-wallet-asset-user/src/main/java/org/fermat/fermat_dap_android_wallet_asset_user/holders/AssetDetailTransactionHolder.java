package org.fermat.fermat_dap_android_wallet_asset_user.holders;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_android_api.ui.util.BitmapWorkerTask;
import com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.R;

import org.fermat.fermat_dap_android_wallet_asset_user.models.Transaction;
import org.fermat.fermat_dap_api.layer.all_definition.util.DAPStandardFormats;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.TransactionType;

import static com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter.Currency.BITCOIN;
import static com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter.Currency.SATOSHI;

/**
 * Created by frank on 12/8/15.
 */
public class AssetDetailTransactionHolder extends FermatViewHolder {
    private AssetUserWalletSubAppModuleManager manager;
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
    public AssetDetailTransactionHolder(View itemView, AssetUserWalletSubAppModuleManager manager, Context context) {
        super(itemView);
        this.manager = manager;
        this.context = context;
        res = itemView.getResources();

        actorImage = (ImageView) itemView.findViewById(R.id.actorImage);
        actorNameText = (FermatTextView) itemView.findViewById(R.id.actorNameText);
        typeByText = (FermatTextView) itemView.findViewById(R.id.typeByText);
        dateText = (FermatTextView) itemView.findViewById(R.id.dateText);
        quantityText = (FermatTextView) itemView.findViewById(R.id.quantityText);
        amountText = (FermatTextView) itemView.findViewById(R.id.amountText);
        balanceTypeText = (FermatTextView) itemView.findViewById(R.id.balanceTypeText);
        dateText = (FermatTextView) itemView.findViewById(R.id.dateText);
        memoText = (FermatTextView) itemView.findViewById(R.id.memoText);
    }

    public void bind(final Transaction transaction) {
        byte[] img = (transaction.getActorImage() == null) ? new byte[0] : transaction.getActorImage();
        BitmapWorkerTask bitmapWorkerTask = new BitmapWorkerTask(actorImage, res, R.drawable.img_asset_without_image, false);
        bitmapWorkerTask.execute(img);

        actorNameText.setText(transaction.getActorName());
        typeByText.setText((transaction.getTransactionType() == TransactionType.CREDIT) ? "Received by" : "Sent to");

        String symbol;
        String confirmedStr = res.getString(R.string.dap_user_wallet_confirmed);
        String pendingStr = res.getString(R.string.dap_user_wallet_pending);
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
            balanceTypeText.setTextColor(res.getColor(R.color.dap_user_wallet_blue_text));
        } else {
            balanceTypeText.setTextColor(res.getColor(R.color.dap_user_wallet_black_text));
        }

        dateText.setText(transaction.getFormattedDate());
        if (transaction.getMemo() != null) memoText.setText(transaction.getMemo());
    }
}
