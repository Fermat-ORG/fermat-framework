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
import java.util.List;


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
    private static String itemsList[];
    private static List<String> previousSelectedItems;
    private static String previousSelectedItem;
    private static String positiveButtonText;
    private static String negativeButtonText;
    private static String dialogTitle;
    private static String mode;

    private static boolean withArray = false;

    private static Context context;


    public static CustomDialogFragment newInstance(Context contextFragment, Bundle data) {
        try {
            items = data.getInt("items");
            if(items == 0) {
                itemsList = data.getStringArray("items_array");
                withArray = true;
            }
            mode = data.getString("mode");
            positiveButtonText = data.getString("positive_button_text");
            negativeButtonText = data.getString("negative_button_text");
            dialogTitle = data.getString("title");
            if(mode.equalsIgnoreCase("multiple_options"))
                previousSelectedItems = data.getStringArrayList("previous_selected_items");
            else
                previousSelectedItem = data.getString("previous_selected_item");

            context = contextFragment;

            if(!isValid())
                throw new NullPointerException(getException());

            initialize();
        } catch (Exception e) {
            throw new NullPointerException(getException());
        }
        return new CustomDialogFragment();
    }

    private static void initialize() {
        itemsCopy = new ArrayList<>();
        String arrayTemporal[];
        if(withArray) {
            itemsCopy.addAll(Arrays.asList(itemsList));
        } else {
            arrayTemporal = context.getResources().getStringArray(items);
            itemsCopy.addAll(Arrays.asList(arrayTemporal));
        }

        itemSelectedList = new boolean[itemsCopy.size()];
        Arrays.fill(itemSelectedList, false);
    }

    private static boolean isValid() {
        return  (items != null || itemsList != null) &&
                mode != null &&
                (mode.equals("multiple_options") || mode.equals("single_option")) &&
                positiveButtonText != null &&
                negativeButtonText != null &&
                dialogTitle != null;
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
        if(previousSelectedItems != null) {
            if(previousSelectedItems.size() > 0) {
                int position;
                for (String option : previousSelectedItems) {
                    if((position = itemsCopy.indexOf(option)) != -1) {
                        itemSelectedList[position] = true;
                        selectedItems.add(option);
                    }
                }
            }
        }

        return itemSelectedList;
    }

    private static String getException() {
        return "\nBundle parameters are incorrect. Please check they are spelled exactly as follow:\n" +
                "items\n" +
                "mode\n" +
                "positive_button_text\n" +
                "negative_button_text\n" +
                "title\n" +
                "multiple_options\n" +
                "single_option\n" +
                "previous_selected_item or previous_selected_items\n" +
                "and the array of items (if used is not null)";
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
            case "multiple_options":
                if(withArray) {
                    builder.setMultiChoiceItems(itemsList, getSelectedItemsList(), new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                            String current = itemsCopy.get(which);
                            if (isChecked)
                                selectedItems.add(current);
                            else if (selectedItems.contains(current))
                                selectedItems.remove(current);
                        }
                    });
                } else {
                    builder.setMultiChoiceItems(items, getSelectedItemsList(), new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                            String current = itemsCopy.get(which);
                            if (isChecked)
                                selectedItems.add(current);
                            else if (selectedItems.contains(current))
                                selectedItems.remove(current);
                        }
                    });
                }
                break;
            case "single_option":
                if(withArray) {
                    builder.setSingleChoiceItems(itemsList, getSelectedItem(), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            previousSelectedItem = itemsCopy.get(which);
                        }
                    });
                } else {
                    builder.setSingleChoiceItems(items, getSelectedItem(), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            previousSelectedItem = itemsCopy.get(which);
                        }
                    });
                }
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
