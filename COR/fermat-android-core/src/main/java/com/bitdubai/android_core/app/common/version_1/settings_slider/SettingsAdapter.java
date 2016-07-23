package com.bitdubai.android_core.app.common.version_1.settings_slider;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;

import com.bitdubai.fermat.R;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapterImproved;
import com.bitdubai.fermat_api.layer.all_definition.enums.FontType;

import java.util.List;

/**
 * Created by Matias Furszyfer on 2016.02.22..
 */
public class SettingsAdapter extends FermatAdapterImproved<SettingsItem, SettingsHolder> {


    private final Typeface tf;
    private SettingsCallback settingsCallback;

    public SettingsAdapter(Context context) {
        super(context);
        tf = Typeface.createFromAsset(context.getAssets(), "fonts/CaviarDreams.ttf");
    }

    public SettingsAdapter(Context context, List<SettingsItem> dataSet, SettingsCallback settingsCallback) {
        super(context, dataSet);
        this.settingsCallback = settingsCallback;
        tf = Typeface.createFromAsset(context.getAssets(), "fonts/CaviarDreams.ttf");
    }


    @Override
    protected SettingsHolder createHolder(View itemView, int type) {
        SettingsHolder fermatAppHolder = new SettingsHolder(itemView, type);
        return fermatAppHolder;
    }

    @Override
    protected int getCardViewResource(int type) {
        return R.layout.reveal_botom_item;
    }


    @Override
    protected void bindHolder(final SettingsHolder holder, final SettingsItem data, final int position) {

        holder.getText().setText(data.getText());
        holder.getText().setFont(FontType.CAVIAR_DREAMS);
        holder.getSubText().setText(data.getSubText());
        holder.getImg().setBackgroundResource(data.getImgRes());

        holder.getImg().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingsCallback.onItemClickListener(view, data, position, holder.getSubText());
            }
        });

    }


    public void setClickCallback(SettingsCallback settingsCallback) {
        this.settingsCallback = settingsCallback;
    }

}