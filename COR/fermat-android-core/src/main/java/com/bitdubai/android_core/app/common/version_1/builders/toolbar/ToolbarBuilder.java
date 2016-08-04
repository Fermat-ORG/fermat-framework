package com.bitdubai.android_core.app.common.version_1.builders.toolbar;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.android_core.app.FermatActivity;
import com.bitdubai.fermat.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;

import java.lang.ref.WeakReference;

/**
 * Created by mati on 03/08/16.
 */
public class ToolbarBuilder {

    private WeakReference<FermatActivity> fermatActivity;
    private WeakReference<Toolbar> mToolbar;

    private ViewGroup toolabarContainer;
    private FermatTextView txtTitle;


    public ToolbarBuilder(FermatActivity fermatActivity,Toolbar mToolbar) {
        this.mToolbar = new WeakReference<Toolbar>(mToolbar);
        this.fermatActivity = new WeakReference<FermatActivity>(fermatActivity);
    }


    public View buildTitle(String title, Typeface typeface, float textSize, String titleColor){
        if (toolabarContainer==null)toolabarContainer = (ViewGroup) fermatActivity.get().getLayoutInflater().inflate(R.layout.text_view, null);
        if (txtTitle==null)txtTitle = (FermatTextView) toolabarContainer.findViewById(R.id.txt_title);
        txtTitle.setText(title);
        txtTitle.setTypeface(typeface);
        txtTitle.setTextSize(textSize);
        if (titleColor != null) txtTitle.setTextColor(Color.parseColor(titleColor));
        return toolabarContainer;
    }

    public void clear(){
        txtTitle = null;
        toolabarContainer.removeAllViewsInLayout();
        toolabarContainer = null;
        mToolbar.clear();
        fermatActivity.clear();
    }



}
