package com.mati.fermat_preference_settings.drawer;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapterImproved;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatActivity;
import com.mati.fermat_preference_settings.R;
import com.mati.fermat_preference_settings.drawer.dialog.CustomDialogFragment;
import com.mati.fermat_preference_settings.drawer.dialog.SettingsDialog;
import com.mati.fermat_preference_settings.drawer.holders.SettingEditTextViewHolder;
import com.mati.fermat_preference_settings.drawer.holders.SettingLinkTextViewHolder;
import com.mati.fermat_preference_settings.drawer.holders.SettingSwitchViewHolder;
import com.mati.fermat_preference_settings.drawer.holders.SettingTextOpenDialogViewHolder;
import com.mati.fermat_preference_settings.drawer.holders.SettingsTextPlusRadioHolder;
import com.mati.fermat_preference_settings.drawer.interfaces.DialogCallback;
import com.mati.fermat_preference_settings.drawer.interfaces.PreferenceSettingsItem;
import com.mati.fermat_preference_settings.drawer.listeners.OnClickListenerSettings;
import com.mati.fermat_preference_settings.drawer.models.PreferenceSettingsEditText;
import com.mati.fermat_preference_settings.drawer.models.PreferenceSettingsLinkText;
import com.mati.fermat_preference_settings.drawer.models.PreferenceSettingsOpenDialogText;
import com.mati.fermat_preference_settings.drawer.models.PreferenceSettingsSwithItem;
import com.mati.fermat_preference_settings.drawer.models.PreferenceSettingsTextPlusRadioItem;

import java.lang.ref.WeakReference;
import java.util.LinkedHashMap;
import java.util.List;


/**
 * Created by mati on 2016.02.08..
 */
public class FermatSettingsAdapter extends FermatAdapterImproved<PreferenceSettingsItem, FermatViewHolder> implements DialogCallback {

    final int SWITH_TYPE = 1;
    final int OPEN_DIALOG_TEXT_TYPE = 2;
    final int EDIT_TEXT_TYPE = 3;
    static final int TEXT_PLUS_RADIO_TYPE = 4;
    static final int TEXT_PLUS_LINK_TYPE = 5;

    WeakReference<FermatPreferenceFragment> fragmentWeakReference;
    private String previousSelectedItem;
    private LinkedHashMap<String,String> defaultItems;
    private final FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();


    protected FermatSettingsAdapter(Activity context) {
        super(context);
    }

    protected FermatSettingsAdapter(Activity context,FermatPreferenceFragment fermatPreferenceFragment ,List<PreferenceSettingsItem> dataSet) {
        super(context, dataSet);
        this.defaultItems = new LinkedHashMap<>();
        this.fragmentWeakReference = new WeakReference<>(fermatPreferenceFragment);
    }

    @Override
    protected FermatViewHolder createHolder(View itemView, int type) {
        FermatViewHolder fermatViewHolder = null;
        switch (type){
            case SWITH_TYPE:
                fermatViewHolder = new SettingSwitchViewHolder(itemView,type);
                break;
            case OPEN_DIALOG_TEXT_TYPE:
                fermatViewHolder = new SettingTextOpenDialogViewHolder(itemView,type);
                break;
            case EDIT_TEXT_TYPE:
                fermatViewHolder = new SettingEditTextViewHolder(itemView,type);
                break;
            case TEXT_PLUS_RADIO_TYPE:
                fermatViewHolder = new SettingsTextPlusRadioHolder(itemView,type);
                break;
            case TEXT_PLUS_LINK_TYPE:
                fermatViewHolder = new SettingLinkTextViewHolder(itemView,type);
                break;
        }
        return fermatViewHolder;
    }

    @Override
    protected int getCardViewResource(int type) {
        int ret = 0;
        switch (type){
            case SWITH_TYPE:
                ret = R.layout.preference_settings_switch_row;
                break;
            case OPEN_DIALOG_TEXT_TYPE:
                ret = R.layout.preference_settings_dialog_row;
                break;
            case EDIT_TEXT_TYPE:
                ret = R.layout.preference_settings_edit_text_row;
                break;
            case TEXT_PLUS_LINK_TYPE:
                ret = R.layout.preference_settings_link_text_row;
                break;
        }
        return ret;
    }

    @Override
    protected void bindHolder(FermatViewHolder holder, PreferenceSettingsItem data, final int position) {

        switch (holder.getHolderType()){
            case SWITH_TYPE:
                SettingSwitchViewHolder settingSwitchViewHolder = (SettingSwitchViewHolder) holder;
                final PreferenceSettingsSwithItem preferenceSettingsSwithItem = (PreferenceSettingsSwithItem) data;
                settingSwitchViewHolder.getSettings_switch().setChecked(preferenceSettingsSwithItem.getSwitchChecked());
                settingSwitchViewHolder.getTextView().setText(preferenceSettingsSwithItem.getText());
               // settingSwitchViewHolder.getSettings_switch().setOnClickListener(new OnClickListenerSettings(this, preferenceSettingsSwithItem, position));
                settingSwitchViewHolder.getSettings_switch().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    getCallback().optionChanged(preferenceSettingsSwithItem, position, isChecked);
                }
            });
                break;
            case OPEN_DIALOG_TEXT_TYPE:
                SettingTextOpenDialogViewHolder settingTextOpenDialogViewHolder = (SettingTextOpenDialogViewHolder) holder;
                final PreferenceSettingsOpenDialogText preferenceSettingsOpenDialogText = (PreferenceSettingsOpenDialogText) data;
                defaultItems.put(preferenceSettingsOpenDialogText.getText(), preferenceSettingsOpenDialogText.getDialogData().getString("previous_selected_item"));
                settingTextOpenDialogViewHolder.getTextView().setText(preferenceSettingsOpenDialogText.getText());
                CustomDialogFragment.CustomDialogListener listener = new CustomDialogFragment.CustomDialogListener();
                listener.setListener(new CustomDialogFragment.DialogListener() {
                    @Override
                    public void onDialogPositiveClick(DialogFragment dialog) {
                        CustomDialogFragment customDialogFragment = (CustomDialogFragment) dialog;
                        previousSelectedItem = customDialogFragment.getPreviousSelectedItem();
                        getCallback().dialogOptionSelected(previousSelectedItem, 0);
                    }

                    @Override
                    public void onDialogNegativeClick(DialogFragment dialog) {
                        // no-op
                    }
                });

                settingTextOpenDialogViewHolder.getTextView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FermatTextView textView = (FermatTextView) view;
                        Bundle data = preferenceSettingsOpenDialogText.getDialogData();
                        String defaultItem = defaultItems.get(textView.getText().toString());
                        if(defaultItem != null) {
                            data.putString("previous_selected_item", defaultItem);
                            defaultItems.put(textView.getText().toString(), null);
                        } else
                            data.putString("previous_selected_item", previousSelectedItem);

                        CustomDialogFragment.newInstance(context, data).show(fragmentManager, "CustomFragmentDialog");
                    }
                });
            break;
            case EDIT_TEXT_TYPE:
                SettingEditTextViewHolder settingEditTextViewHolder = (SettingEditTextViewHolder) holder;
                final PreferenceSettingsEditText preferenceSettingsEditText = (PreferenceSettingsEditText) data;
                settingEditTextViewHolder.getTextView().setText(preferenceSettingsEditText.getTitleText());
                settingEditTextViewHolder.getSettingsEditText().setHint(preferenceSettingsEditText.getEditHint());
                settingEditTextViewHolder.getSettingsEditText().setText(preferenceSettingsEditText.getEditText());
                //settingEditTextViewHolder.getSettingsEditText().setOnClickListener(new OnClickListenerSettings(this, preferenceSettingsEditText, position));

                settingEditTextViewHolder.getSettingsEditText().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getCallback().optionSelected(preferenceSettingsEditText, position);
                    }
                });
                settingEditTextViewHolder.getSettingsEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus) {
                            getCallback().optionSelected(preferenceSettingsEditText, position);
                        }
                    }
                });


                break;
            case TEXT_PLUS_RADIO_TYPE:
                SettingsTextPlusRadioHolder settingsTextPlusRadioHolder = (SettingsTextPlusRadioHolder) holder;
                final PreferenceSettingsTextPlusRadioItem preferenceSettingsTextPlusRadioItem = (PreferenceSettingsTextPlusRadioItem) data;
                settingsTextPlusRadioHolder.getRadio().setChecked(preferenceSettingsTextPlusRadioItem.isRadioTouched());
                settingsTextPlusRadioHolder.getRadio().setText(preferenceSettingsTextPlusRadioItem.getText());
                break;

            case TEXT_PLUS_LINK_TYPE:
                SettingLinkTextViewHolder settingLinkTextViewHolder = (SettingLinkTextViewHolder) holder;
                final PreferenceSettingsLinkText preferenceSettingsLinkText = (PreferenceSettingsLinkText) data;
                settingLinkTextViewHolder.getTextView().setText(preferenceSettingsLinkText.getTitleText());
               //settingLinkTextViewHolder.getSettingsEditText().setText(preferenceSettingsLinkText.getTitleText());

                settingLinkTextViewHolder.getTextView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getCallback().optionSelected(preferenceSettingsLinkText, position);
                    }
                });


                break;
        }
    }


    @Override
    public int getItemViewType(int position) {
        PreferenceSettingsItem item = dataSet.get(position);
        if (item instanceof PreferenceSettingsSwithItem) {
            return SWITH_TYPE;
        } else if (item instanceof PreferenceSettingsOpenDialogText) {
            return OPEN_DIALOG_TEXT_TYPE;
        } else if(item instanceof PreferenceSettingsEditText){
            return EDIT_TEXT_TYPE;
        } else if (item instanceof PreferenceSettingsTextPlusRadioItem){
            return TEXT_PLUS_RADIO_TYPE;
        } else if (item instanceof PreferenceSettingsLinkText){
            return TEXT_PLUS_LINK_TYPE;
        }
        return -1;
    }

    private DialogCallback getCallback(){
        return this;
    }

    @Override
    public void optionSelected(PreferenceSettingsItem preferenceSettingsItem, int position) {
        fragmentWeakReference.get().onSettingsTouched(preferenceSettingsItem, position);
    }

    @Override
    public void dialogOptionSelected(String item, int position) {
        fragmentWeakReference.get().dialogOptionSelected(item, position);
    }

    @Override
    public void optionChanged(PreferenceSettingsItem preferenceSettingsItem, int position, boolean isChecked) {
        fragmentWeakReference.get().onSettingsChanged(preferenceSettingsItem, position, isChecked);
    }

    public void clear(){
        fragmentWeakReference.clear();
    }
}
