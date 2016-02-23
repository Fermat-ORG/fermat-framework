package com.mati.fermat_preference_settings.settings.dialog;

import android.content.Context;
import android.view.View;

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
    protected void bindHolder(SettingsTextPlusRadio holder, PreferenceSettingsTextPlusRadioItem data, int position) {
        holder.getFermatTextView().setText(data.getText());
    }
}
