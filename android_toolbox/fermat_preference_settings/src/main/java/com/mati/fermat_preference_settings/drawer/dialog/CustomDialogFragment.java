package com.mati.fermat_preference_settings.drawer.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Clelia López on 4/11/16
 */
public class CustomDialogFragment
        extends DialogFragment {

    /**
     * Attributes
     */
    private static DialogListener listener;
    private ArrayList<String> selectedItems;
    private static boolean itemSelectedList[];
    private static ArrayList<String> itemsCopy;
    private static Integer items;
    private static ArrayList<String> previousSelectedItems;
    private static String previousSelectedItem;
    private static String positiveButtonText;
    private static String negativeButtonText;
    private static String dialogTitle;
    private static String mode;

    private static Context context;


    public static CustomDialogFragment newInstance(Context contextFragment, Bundle data) {
        items = data.getInt("items");
        mode = data.getString("mode");
        positiveButtonText = data.getString("positive_button_text");
        negativeButtonText = data.getString("negative_button_text");
        dialogTitle = data.getString("title");
        if(mode.equalsIgnoreCase("multiple_options"))
            previousSelectedItems = data.getStringArrayList("previous_selected_options");
        else
            previousSelectedItem = data.getString("previous_selected_item");

        context = contextFragment;

        initialize();

        return new CustomDialogFragment();
    }

    private static void initialize() {
        itemsCopy = new ArrayList<>();
        String arrayTemporal[] = context.getResources().getStringArray(items);
        itemsCopy.addAll(Arrays.asList(arrayTemporal));

        itemSelectedList = new boolean[itemsCopy.size()];
        Arrays.fill(itemSelectedList, false);
    }

    /**
     * Observer
     */
    public interface DialogListener {
        void onDialogPositiveClick(DialogFragment dialog);
        void onDialogNegativeClick(DialogFragment dialog);
    }

    public static class CustomDialogListener {

        public void setListener(DialogListener externalListener) {
            listener = externalListener;
        }
    }

    private boolean[] getSelectedItemsList() {
        if(previousSelectedItems.size() > 0) {
            int position;
            for (String option : previousSelectedItems) {
                if((position = itemsCopy.indexOf(option)) != -1) {
                    itemSelectedList[position] = true;
                    selectedItems.add(option);
                }
            }
        }
        return itemSelectedList;
    }

    public int getSelectedItem() {
        return itemsCopy.indexOf(previousSelectedItem);
    }

    public ArrayList<String> getPreviousSelectedItems() {
        return selectedItems;
    }

    public String getPreviousSelectedItem() {
        return previousSelectedItem;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        selectedItems = new ArrayList<>();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(dialogTitle);
        switch (mode) {
            case "multiple_option":
                builder.setMultiChoiceItems(items, getSelectedItemsList(), new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        String current = itemsCopy.get(which);
                        if (isChecked)
                            selectedItems.add(current);
                        else if (selectedItems.contains(current))
                            selectedItems.remove(which);
                    }
                });
            break;
            case "single_option":
                builder.setSingleChoiceItems(items, getSelectedItem(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        previousSelectedItem = itemsCopy.get(which);
                    }
                });
            break;
        }

        builder.setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                if(listener != null)
                    listener.onDialogPositiveClick(CustomDialogFragment.this);
            }
        });

        builder.setNegativeButton(negativeButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                if(listener != null)
                    listener.onDialogNegativeClick(CustomDialogFragment.this);
            }
        });

        return builder.create();
    }
}
