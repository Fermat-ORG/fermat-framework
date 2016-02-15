package com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.holders;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_android_api.ui.util.BitmapWorkerTask;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.R;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.models.Transaction;
import com.bitdubai.fermat_dap_api.layer.all_definition.util.DAPStandardFormats;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.TransactionType;

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
    private FermatTextView amountText;
    private FermatTextView dateText;
    private FermatTextView balanceTypeText;

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
        amountText = (FermatTextView) itemView.findViewById(R.id.amountText);
        balanceTypeText = (FermatTextView) itemView.findViewById(R.id.balanceTypeText);
        dateText = (FermatTextView) itemView.findViewById(R.id.dateText);
    }

    public void bind(final Transaction transaction) {
        byte[] img = (transaction.getImagePerson() == null) ? new byte[0] : transaction.getImagePerson();
        BitmapWorkerTask bitmapWorkerTask = new BitmapWorkerTask(actorImage, res, R.drawable.img_asset_without_image, false);
        bitmapWorkerTask.execute(img);

        actorNameText.setText(transaction.getUserName());
        typeByText.setText((transaction.getTransactionType() == TransactionType.CREDIT) ? "Recieved by" : "Sent by");
        String symbol;
        if (transaction.getTransactionType() == TransactionType.CREDIT) {
            symbol = "+ ";
            amountText.setTextColor(res.getColor(R.color.fab_material_green_900));
            balanceTypeText.setTextColor(res.getColor(R.color.fab_material_green_900));
        } else {
            symbol = "- ";
            amountText.setTextColor(res.getColor(R.color.fab_material_red_900));
            balanceTypeText.setTextColor(res.getColor(R.color.fab_material_red_900));
        }
        amountText.setText(DAPStandardFormats.BITCOIN_FORMAT.format(symbol + transaction.getAmount()));
        balanceTypeText.setText((transaction.getBalanceType() == BalanceType.AVAILABLE) ? "AVAILABLE" : "BOOK");
        dateText.setText(transaction.getFormattedDate());
    }
}
