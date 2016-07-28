package com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders;

import android.view.View;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatCheckBox;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.models.EarningsWizardData;

/**
 * Created by nelson on 31/12/15.
 */
public class EarningsWizardViewHolder extends FermatViewHolder {

    private final FermatTextView subTitle;
    private FermatTextView title;
    private FermatCheckBox checkbox;


    public EarningsWizardViewHolder(View itemView) {
        super(itemView);

        title = (FermatTextView) itemView.findViewById(R.id.cbw_title);
        subTitle = (FermatTextView) itemView.findViewById(R.id.cbw_sub_title);
        checkbox = (FermatCheckBox) itemView.findViewById(R.id.cbw_checkbox_view);
    }

    public void bind(EarningsWizardData data) {
//                title.setText(String.format("%s / %s", data.getLinkedCurrency().getCode(), data.getEarningCurrency().getCode()));
        title.setText(new StringBuilder().append(data.getLinkedCurrency().getCode()).append(" / ").append(data.getEarningCurrency().getCode()).toString());

        checkbox.setChecked(data.isChecked());

        if (data.getWalletName() != null) {
            subTitle.setText(data.getWalletName());
            subTitle.setTextColor(subTitle.getResources().getColor(R.color.cbw_wizard_color));
        } else {
            subTitle.setText(R.string.cbw_earning_wizard_item_msg);
            subTitle.setTextColor(subTitle.getResources().getColor(R.color.dark_grey));
        }
    }


}
