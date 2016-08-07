package com.bitdubai.android_core.app.common.version_1.builders.toolbar;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.android_core.app.FermatActivity;
import com.bitdubai.fermat.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;

import java.lang.ref.WeakReference;

/**
 * //todo: mejorar...
 * Created by mati on 03/08/16.
 */
public class ToolbarBuilder {

    private static final String TAG = "ToolbarBuilder";
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
        else Log.e(TAG,"error title container");
        if (txtTitle==null)txtTitle = (FermatTextView) toolabarContainer.findViewById(R.id.txt_title);
        else Log.e(TAG,"error txtTitle");
        txtTitle.setText(title);
        if (typeface!=null) txtTitle.setTypeface(typeface);
        txtTitle.setTextSize(textSize);
        if (titleColor != null && !titleColor.equals("")) txtTitle.setTextColor(Color.parseColor(titleColor));
        return toolabarContainer;
    }

    public void clearToolbarViews(){
        if (toolabarContainer!=null) {
            try {
                mToolbar.get().removeView(toolabarContainer);
            }catch (Exception e){
                //nothing
            }
            if (toolabarContainer!=null)toolabarContainer.removeAllViewsInLayout();
            toolabarContainer = null;
            txtTitle = null;
        }

    }

    public void clear(){
        clearToolbarViews();
        mToolbar.clear();
        fermatActivity.clear();
    }


    public void setTextTitle(String textTitle) {
        txtTitle.setText(textTitle);
    }

    public void setTypeface(Typeface typeface) {
        if (typeface!=null)
            txtTitle.setTypeface(typeface);
    }

    public void setTextSize(float textSize) {
        txtTitle.setTextSize(textSize);
    }

    public void setTextColor(String textColor) {
        if (textColor != null && !textColor.equals("")) txtTitle.setTextColor(Color.parseColor(textColor));
    }

    public void invalidate() {
        txtTitle.invalidate();
    }

    public void setToolbar(Toolbar toolbar) {
        this.mToolbar.clear();
        this.mToolbar = new WeakReference<Toolbar>(toolbar);
    }
}