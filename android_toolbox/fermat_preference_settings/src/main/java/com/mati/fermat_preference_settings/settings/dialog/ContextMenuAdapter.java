package com.mati.fermat_preference_settings.settings.dialog;

import android.content.Context;
import android.view.View;
import android.widget.CompoundButton;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.mati.fermat_preference_settings.R;
import com.mati.fermat_preference_settings.settings.holders.SettingsTextPlusRadio;
import com.mati.fermat_preference_settings.settings.models.PreferenceSettingsTextPlusRadioItem;

import java.util.List;

/**
 * Created by mati on 2016.02.08..
 */
public class ContextMenuAdapter extends FermatAdapter<PreferenceSettingsTextPlusRadioItem,SettingsTextPlusRadio> {



    protected ContextMenuAdapter(Context context) {
        super(context);
    }

    protected ContextMenuAdapter(Context context, List<PreferenceSettingsTextPlusRadioItem> dataSet) {
        super(context, dataSet);
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
    protected void bindHolder(SettingsTextPlusRadio holder, PreferenceSettingsTextPlusRadioItem data, final int position) {
        holder.getRadio().setText(data.getText());
        holder.getRadio().setChecked(data.isRadioTouched());
        holder.getRadio().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    clearNotSelectedRadioButton(position);
                }
            }
        });
    }

    private void clearNotSelectedRadioButton(int positionSelected){
        for (int i = 0; i < getItemCount(); i++) {
            if(i!=positionSelected) getItem(i).setIsRadioTouched(false);
        }
    }

}
