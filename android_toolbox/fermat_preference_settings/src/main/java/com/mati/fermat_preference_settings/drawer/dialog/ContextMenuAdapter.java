package com.mati.fermat_preference_settings.drawer.dialog;

import android.content.Context;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.mati.fermat_preference_settings.R;
import com.mati.fermat_preference_settings.drawer.holders.SettingsTextPlusRadioHolder;
import com.mati.fermat_preference_settings.drawer.interfaces.DialogCallback;
import com.mati.fermat_preference_settings.drawer.models.PreferenceSettingsTextPlusRadioItem;

import java.util.List;

/**
 * Created by mati on 2016.02.08..
 */
public class ContextMenuAdapter extends FermatAdapter<PreferenceSettingsTextPlusRadioItem,SettingsTextPlusRadioHolder> {

    private DialogCallback callBack;
    private static RadioButton lastChecked = null;
    private static int lastCheckedPos = 0;

    protected ContextMenuAdapter(Context context) {
        super(context);
    }

    protected ContextMenuAdapter(Context context,DialogCallback dialogCallback, List<PreferenceSettingsTextPlusRadioItem> dataSet) {
        super(context, dataSet);
        this.callBack = dialogCallback;
    }

    @Override
    protected SettingsTextPlusRadioHolder createHolder(View itemView, int type) {
        return new SettingsTextPlusRadioHolder(itemView,type);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.context_menu_row;
    }

    @Override
    protected void bindHolder(SettingsTextPlusRadioHolder holder, final PreferenceSettingsTextPlusRadioItem data, final int position) {
        holder.getRadio().setText(data.getText());
        holder.getRadio().setChecked(data.isRadioTouched());
        holder.getRadio().setTag(new Integer(position));

        //for default check in first item
        if(position == 0 && data.isRadioTouched() && holder.getRadio().isChecked()) {
            lastChecked = holder.getRadio();
            lastCheckedPos = 0;
        }

        holder.getRadio().setOnClickListener(new CompoundButton.OnClickListener() {

            @Override
            public void onClick(View view) {
                 //getItem(position).setIsRadioTouched(true);
                 //clearNotSelectedRadioButton(position,view);
                 callBack.optionSelected(data,position);


                RadioButton cb = (RadioButton)view;
                int clickedPos = ((Integer)cb.getTag()).intValue();

                if(cb.isChecked())
                {
                    if(lastChecked != null)
                    {
                        lastChecked.setChecked(false);
                        getItem(lastCheckedPos).setIsRadioTouched(false);
                    }

                    lastChecked = cb;
                    lastCheckedPos = clickedPos;
                }
                else
                    lastChecked = null;

                getItem(clickedPos).setIsRadioTouched(cb.isChecked());
            }
        });
    }

    private void clearNotSelectedRadioButton(int positionSelected,View view){
        for (int i = 0; i < getItemCount(); i++) {
            if(i!=positionSelected){
                getItem(positionSelected).setIsRadioTouched(false);
                ((RadioButton)view).setChecked(false);
            }
            else {
                ((RadioButton)view).setChecked(true);
            }
        }
    }

}
