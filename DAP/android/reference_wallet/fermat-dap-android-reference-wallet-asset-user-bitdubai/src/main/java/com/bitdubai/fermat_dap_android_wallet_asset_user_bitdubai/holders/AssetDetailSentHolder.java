package com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.holders;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_android_api.ui.util.BitmapWorkerTask;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.R;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.models.RedeemPoint;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.models.Transaction;
import com.bitdubai.fermat_dap_api.layer.all_definition.util.DAPStandardFormats;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;

import java.util.Date;

/**
 * Created by frank on 12/8/15.
 */
public class AssetDetailSentHolder extends FermatViewHolder {
    private AssetUserWalletSubAppModuleManager manager;
    private Context context;
    private Resources res;

    private ImageView imgPersonSent;
    private FermatTextView sentUserNameText;
    private FermatTextView sentByNameText;
    private FermatTextView sentAmountText;
    private FermatTextView sentDateText;

    /**
     * Constructor
     *
     * @param itemView
     */
    public AssetDetailSentHolder(View itemView, AssetUserWalletSubAppModuleManager manager, Context context) {
        super(itemView);
        this.manager = manager;
        this.context = context;
        res = itemView.getResources();

        imgPersonSent = (ImageView) itemView.findViewById(R.id.img_person_sent);
        sentUserNameText = (FermatTextView) itemView.findViewById(R.id.sentUserNameText);
        sentByNameText = (FermatTextView) itemView.findViewById(R.id.sentByNameText);
        sentAmountText = (FermatTextView) itemView.findViewById(R.id.sentAmountText);
        sentDateText = (FermatTextView) itemView.findViewById(R.id.sentDateText);
    }

    public void bind(final Transaction transaction) {
        byte[] img = (transaction.getImagePerson() == null) ? new byte[0] : transaction.getImagePerson();
        BitmapWorkerTask bitmapWorkerTask = new BitmapWorkerTask(imgPersonSent, res, R.drawable.img_asset_without_image, false);
        bitmapWorkerTask.execute(img);

        sentUserNameText.setText(transaction.getUserName());
        sentByNameText.setText(transaction.getTransactionUserName());
        sentAmountText.setText(DAPStandardFormats.BITCOIN_FORMAT.format(transaction.getAmount()));
        sentDateText.setText(transaction.getFormattedDate());
    }
}
