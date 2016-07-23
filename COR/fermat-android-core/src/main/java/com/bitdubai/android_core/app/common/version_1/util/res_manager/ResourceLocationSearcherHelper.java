package com.bitdubai.android_core.app.common.version_1.util.res_manager;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

import com.bitdubai.android_core.app.common.version_1.connection_manager.FermatAppConnectionManager;
import com.bitdubai.fermat_android_api.core.ResourceSearcher;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.FermatDrawable;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.FermatView;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.SourceLocation;

/**
 * Created by Matias Furszyfer on 2016.06.07..
 */
public class ResourceLocationSearcherHelper {

    private static final String TAG = "ResourceLocationHelper";

    public static int obtainRes(int resType, Context context, int resourceId, SourceLocation sourceLocation, String publickKeyOwnerOfSource) {
        ResourceSearcher resourceSearcher = switchType(context, sourceLocation, publickKeyOwnerOfSource);
        if (resourceSearcher == null)
            throw new IllegalArgumentException(new StringBuilder().append("ResourceSearcher not found, App owner: ").append(publickKeyOwnerOfSource).append(", SourceLocation: ").append(sourceLocation).append(", resType: ").append(resType).append(", resourceId: ").append(resourceId).append(",\n Add in the appConnection this functionality").toString());
        return resourceSearcher.obtainRes(resType, context, resourceId);
    }

//    public static int obtainRes(Context context,int resourceId,SourceLocation sourceLocation,String publickKeyOwnerOfSource){
//        return switchType(context, sourceLocation, publickKeyOwnerOfSource).obtainRes(ResourceSearcher.UNKNOWN_TYPE, context, resourceId);
//    }

    public static View obtainView(Context context, FermatView fermatView) {
        return switchType(context, fermatView.getSourceLocation(), (fermatView.getOwner() != null) ? fermatView.getOwner().getOwnerAppPublicKey() : null).obtainView(context, fermatView);
    }

    public static Drawable obtainDrawable(Context context, FermatDrawable fermatDrawable) {
        return switchType(context, fermatDrawable.getSourceLocation(), (fermatDrawable.getOwner() != null) ? fermatDrawable.getOwner().getOwnerAppPublicKey() : null).obtainDrawable(context, fermatDrawable);
    }


    private static ResourceSearcher switchType(Context context, SourceLocation sourceLocation, String publickKeyOwnerOfSource) {
        ResourceSearcher resourceSearcher = null;
        switch (sourceLocation) {
            case FERMAT_FRAMEWORK:
                resourceSearcher = ResManager.getInstance();
                break;
            case DEVELOPER_RESOURCES:
                resourceSearcher = FermatAppConnectionManager.getFermatAppConnection(publickKeyOwnerOfSource, context).getResourceSearcher();
                break;
            case INTERNET_URL:
                Log.i(TAG, "Internet request drawable is not supported yet");
                break;
        }
        return resourceSearcher;
    }

}
