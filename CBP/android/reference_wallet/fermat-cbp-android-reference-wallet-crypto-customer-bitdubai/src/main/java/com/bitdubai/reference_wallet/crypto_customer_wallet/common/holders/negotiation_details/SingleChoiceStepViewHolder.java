package com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders.negotiation_details;

import android.app.Activity;
import android.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStepStatus;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.NegotiationStep;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.models.StepItemGetter;
import com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.common.SingleChoiceDialogFragment;

import java.util.List;


public class SingleChoiceStepViewHolder extends StepViewHolder
        implements View.OnClickListener, SingleChoiceDialogFragment.SelectedItem<String> {

    private CryptoBrokerWalletManager walletManager;
    private List<String> dataList;
    private String selectedValue;

    private Activity activity;
    private RecyclerView.Adapter adapter;

    private Button buttonValue;
    private TextView descriptionTextView;


    public SingleChoiceStepViewHolder(RecyclerView.Adapter adapter, View viewItem, Activity activity, CryptoBrokerWalletManager walletManager) {
        super(viewItem, (StepItemGetter) adapter);

        this.adapter = adapter;
        this.activity = activity;
        this.walletManager = walletManager;

        descriptionTextView = (TextView) viewItem.findViewById(R.id.ccw_description_text);
        buttonValue = (Button) viewItem.findViewById(R.id.ccw_single_choice_value);
        buttonValue.setOnClickListener(this);
    }

    public void bind(int stepNumber, int titleRes, int descriptionRes, String value, List<String> options) {
        super.bind(stepNumber);

        this.selectedValue = value;
        this.dataList = options;

        titleTextView.setText(titleRes);
        buttonValue.setText(selectedValue);
        descriptionTextView.setText(descriptionRes);
    }

    @Override
    public void onClick(View view) {
        if (dataList == null)
            return;
        if (dataList.isEmpty())
            return;

        SingleChoiceDialogFragment<String> dialog = new SingleChoiceDialogFragment<>();

        dialog.configure("Select an Item", dataList, selectedValue, this);

        FragmentManager fragmentManager = activity.getFragmentManager();
        dialog.show(fragmentManager, "SingleChoiceDialog");

    }

    @Override
    public void getSelectedItem(String selectedItem) {

        if (selectedItem != null) {
            if (!selectedItem.equals(selectedValue)) {
                valuesHasChanged = true;
            }

            selectedValue = selectedItem;
            buttonValue.setText(selectedItem);
        }
    }


    @Override
    public void setStatus(NegotiationStepStatus clauseStatus) {
        super.setStatus(clauseStatus);

        switch (clauseStatus) {
            case ACCEPTED:
                descriptionTextView.setTextColor(getColor(R.color.card_title_color_status_accepted));
                break;
            case CHANGED:
                descriptionTextView.setTextColor(getColor(R.color.card_title_color_status_changed));
                break;
            case CONFIRM:
                descriptionTextView.setTextColor(getColor(R.color.card_title_color_status_confirm));
                break;
        }
    }

    @Override
    protected void modifyData(NegotiationStepStatus stepStatus) {
        super.modifyData(stepStatus);

        NegotiationStep step = stepItemGetter.getItem(itemPosition);
        walletManager.modifyNegotiationStepValues(step, stepStatus, selectedValue);
        adapter.notifyItemChanged(itemPosition);
    }
}
