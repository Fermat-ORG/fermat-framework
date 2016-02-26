package com.mati.fermat_preference_settings.settings.dialog;

import android.content.Context;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.mati.fermat_preference_settings.R;
import com.mati.fermat_preference_settings.settings.holders.SettingsTextPlusRadio;
import com.mati.fermat_preference_settings.settings.interfaces.DialogCallback;
import com.mati.fermat_preference_settings.settings.models.PreferenceSettingsTextPlusRadioItem;

import java.util.List;

/**
 * Created by mati on 2016.02.08..
 */
public class ContextMenuAdapter extends FermatAdapter<PreferenceSettingsTextPlusRadioItem,SettingsTextPlusRadio> {

    private DialogCallback callBack;

    protected ContextMenuAdapter(Context context) {
        super(context);
    }

    protected ContextMenuAdapter(Context context,DialogCallback dialogCallback, List<PreferenceSettingsTextPlusRadioItem> dataSet) {
        super(context, dataSet);
        this.callBack = dialogCallback;
    }

    @Override
    protected SettingsTextPlusRadio createHolder(View itemView, int type) {
        return new SettingsTextPlusRadio(itemView,type);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.context_menu_row;
    }

    @Override
    protected void bindHolder(SettingsTextPlusRadio holder, final PreferenceSettingsTextPlusRadioItem data, final int position) {
        holder.getRadio().setText(data.getText());
        holder.getRadio().setChecked(data.isRadioTouched());

        holder.getRadio().setOnClickListener(new CompoundButton.OnClickListener() {

            @Override
            public void onClick(View view) {
                clearNotSelectedRadioButton(position);
                 callBack.optionSelected(data,position);
            }
        });
    }

    private void clearNotSelectedRadioButton(int positionSelected){
        for (int i = 0; i < getItemCount(); i++) {
            if(i!=positionSelected) getItem(i).setIsRadioTouched(false);
        }
    }

}
