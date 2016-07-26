package com.bitdubai.fermat_android_api.core;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.bitdubai.fermat_android_api.utils.DrawableUtils;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.FermatDrawable;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.FermatView;

/**
 * Created by Matias Furszfer on 2016.07.02..
 */
public class ResourceSearcher {

    public static final int DRAWABLE_TYPE = 1;
    public static final int VIEW_TYPE = 2;
    public static final int LAYOUT_TYPE = 3;
    public static final int UNKNOWN_TYPE = 4;

    public int obtainRes(int resType, Context context, int id) {
        int resId = 0;
        switch (resType) {
            case DRAWABLE_TYPE:
                resId = obtainResDrawable(context, id);
                break;
            case VIEW_TYPE:
                //bloqueado por ahora
                //resId = obtainResLayout(context, id);
                break;
            case LAYOUT_TYPE:
                resId = obtainResLayout(context, id);
                break;
        }
        return resId;
    }

    public Drawable obtainDrawable(Context context, FermatDrawable fermatDrawable) {
        int resId = obtainResDrawable(context, fermatDrawable.getId());
        return DrawableUtils.resToDrawable(context, resId);
    }


    public View obtainView(Context context, FermatView fermatView) {
        return null;
    }

    public int obtainResDrawable(Context context, int id) {
        return 0;
    }

    public int obtainResLayout(Context context, int id) {
        return 0;
    }

}
