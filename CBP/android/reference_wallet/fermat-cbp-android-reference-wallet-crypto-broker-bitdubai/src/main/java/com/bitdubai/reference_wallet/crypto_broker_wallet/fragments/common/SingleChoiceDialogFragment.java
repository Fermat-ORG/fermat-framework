package com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.common;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by nelson on 02/12/15.
 */
public class SingleChoiceDialogFragment<T> extends DialogFragment implements DialogInterface.OnClickListener {
    private String title;
    private List<T> choices;
    private View view;

    public static SingleChoiceDialogFragment getNewInstance(View callingView, String title, List choices) {
        SingleChoiceDialogFragment dialogFragment = new SingleChoiceDialogFragment();
        dialogFragment.view = callingView;
        dialogFragment.title = title;
        dialogFragment.choices = choices;

        return dialogFragment;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        ArrayAdapter<T> adapter = new ArrayAdapter<T>(getActivity(), android.R.layout.simple_list_item_1, choices);
        builder.setTitle(title).setSingleChoiceItems(adapter, 0, this);

        return builder.create();
    }

}
