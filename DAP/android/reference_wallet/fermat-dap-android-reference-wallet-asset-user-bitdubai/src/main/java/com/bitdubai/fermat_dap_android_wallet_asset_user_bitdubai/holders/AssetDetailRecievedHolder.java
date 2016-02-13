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

/**
 * Created by frank on 12/8/15.
 */
public class AssetDetailRecievedHolder extends FermatViewHolder {
    private AssetUserWalletSubAppModuleManager manager;
    private Context context;
    private Resources res;

    private ImageView imgPersonRecieved;
    private FermatTextView recievedUserNameText;
    private FermatTextView recievedByNameText;
    private FermatTextView recievedAmountText;
    private FermatTextView recievedDateText;

    /**
     * Constructor
     *
     * @param itemView
     */
    public AssetDetailRecievedHolder(View itemView, AssetUserWalletSubAppModuleManager manager, Context context) {
        super(itemView);
        this.manager = manager;
        this.context = context;
        res = itemView.getResources();

        imgPersonRecieved = (ImageView) itemView.findViewById(R.id.img_person_recieved);
        recievedUserNameText = (FermatTextView) itemView.findViewById(R.id.recievedUserNameText);
        recievedByNameText = (FermatTextView) itemView.findViewById(R.id.recievedByNameText);
        recievedAmountText = (FermatTextView) itemView.findViewById(R.id.recievedAmountText);
        recievedDateText = (FermatTextView) itemView.findViewById(R.id.recievedDateText);
    }

    public void bind(final Transaction transaction) {
        byte[] img = (transaction.getImagePerson() == null) ? new byte[0] : transaction.getImagePerson();
        BitmapWorkerTask bitmapWorkerTask = new BitmapWorkerTask(imgPersonRecieved, res, R.drawable.img_asset_without_image, false);
        bitmapWorkerTask.execute(img);

        recievedUserNameText.setText(transaction.getUserName());
        recievedByNameText.setText(transaction.getTransactionUserName());
        recievedAmountText.setText(DAPStandardFormats.BITCOIN_FORMAT.format(transaction.getAmount()));
        recievedDateText.setText(transaction.getFormattedDate());
    }
}
