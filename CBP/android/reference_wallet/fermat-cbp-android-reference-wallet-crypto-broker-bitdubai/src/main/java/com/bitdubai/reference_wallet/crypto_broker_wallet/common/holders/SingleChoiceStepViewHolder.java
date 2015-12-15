package com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders;

import android.app.Activity;
import android.app.FragmentManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nelsonalfo.testapplication.R;
import com.nelsonalfo.testapplication.activityNegotiationDetails.adapters.NegotiationDetailsAdapter;
import com.nelsonalfo.testapplication.activityNegotiationDetails.fragments.SingleChoiceDialogFragment;
import com.nelsonalfo.testapplication.fermat_api.ModuleManager;
import com.nelsonalfo.testapplication.fermat_api.fermat_interfaces.NegotiationStep;
import com.nelsonalfo.testapplication.fermat_api.fermat_interfaces.NegotiationStepStatus;

import java.util.List;


public class SingleChoiceStepViewHolder extends StepViewHolder
        implements View.OnClickListener, SingleChoiceDialogFragment.SelectedItem<String> {

    private List<String> dataList;
    private String selectedValue;
    private Activity activity;

    private Button buttonValue;
    private TextView descriptionTextView;


    public SingleChoiceStepViewHolder(NegotiationDetailsAdapter adapter, View viewItem, Activity activity) {
        super(viewItem, adapter);

        this.activity = activity;

        descriptionTextView = (TextView) viewItem.findViewById(R.id.description_text);
        buttonValue = (Button) viewItem.findViewById(R.id.single_choice_value);
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

        NegotiationStep step = adapter.getItem(itemPosition);
        ModuleManager.modifyNegotiationStepValues(step, stepStatus, selectedValue);
        adapter.notifyItemChanged(itemPosition);
    }
}
