package com.bitdubai.android_core.app.common.version_1.util.res_manager;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.bitdubai.fermat_android_api.core.ResourceSearcher;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.FermatDrawable;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.FermatView;

/**
 * Created by Matias Furszyfer on 2016.07.02..
 */
public class ResManager extends ResourceSearcher {


    private static volatile ResourceSearcher instance;

    public static ResourceSearcher getInstance() {
        if (instance == null) {
            instance = new ResManager();
        }
        return instance;
    }

    @Override
    public Drawable obtainDrawable(Context context, FermatDrawable fermatDrawable) {
        return ResDrawableFrameworkHelper.obtainDrawable(context, fermatDrawable);
    }

    @Override
    public View obtainView(Context context, FermatView fermatView) {
        return ResViewFrameworkHelper.obtainView(context, fermatView);
    }

    @Override
    public int obtainRes(int resType, Context context, int id) {
        int resId = 0;
        switch (resType) {
            case DRAWABLE_TYPE:
                resId = ResDrawableFrameworkHelper.obtainResDrawable(context, id);
                break;
            case VIEW_TYPE:
                break;
            case UNKNOWN_TYPE:
                break;
        }
        return resId;
    }
}
