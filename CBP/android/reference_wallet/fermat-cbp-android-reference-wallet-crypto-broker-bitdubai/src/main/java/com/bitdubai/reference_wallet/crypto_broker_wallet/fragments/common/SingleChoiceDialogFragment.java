package com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.common;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.bitdubai.reference_wallet.crypto_broker_wallet.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nelson on 07/12/15.
 */
public class SingleChoiceDialogFragment<T> extends DialogFragment {
    private static final int NO_ITEM_INDEX = -1;
    private String title;
    private List<T> choices;
    private SelectedItem<T> selectedItemListener;
    private int selectedItemIndex;

    public interface SelectedItem<T> {
        void getSelectedItem(T selectedItem);
    }

    public void configure(String title, List<T> choices, T selectedItem, SelectedItem<T> listener) {
        this.title = title;
        this.selectedItemListener = listener;

        this.choices = (choices == null) ? new ArrayList<T>() : choices;
        int index = this.choices.indexOf(selectedItem);
        this.selectedItemIndex = index == NO_ITEM_INDEX ? 0 : index;

    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        ArrayAdapter<T> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.select_dialog_singlechoice, choices);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(title);

        builder.setSingleChoiceItems(adapter, selectedItemIndex, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                selectedItemIndex = which;
            }
        });

        builder.setPositiveButton(getResources().getString(R.string.ok).toUpperCase(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int buttonId) {
                T selectedItem = choices.get(selectedItemIndex);
                selectedItemListener.getSelectedItem(selectedItem);
            }
        });

        builder.setNegativeButton(getResources().getString(R.string.cancel).toUpperCase(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int buttonId) {
                T selectedItem = choices.get(selectedItemIndex);
                selectedItemListener.getSelectedItem(selectedItem);
            }
        });

        return builder.create();
    }

}
