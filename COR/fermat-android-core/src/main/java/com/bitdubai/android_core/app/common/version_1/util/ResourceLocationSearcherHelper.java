package com.bitdubai.android_core.app.common.version_1.util;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.bitdubai.android_core.app.common.version_1.connection_manager.FermatAppConnectionManager;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.SourceLocation;

/**
 * Created by Matias Furszyfer on 2016.06.07..
 */
public class ResourceLocationSearcherHelper {

    private static final String TAG = "ResourceLocationHelper";

    public static int obtainRes(Context activity,int resourceId,SourceLocation sourceLocation,String publickKeyOwnerOfSource){
        int resId = 0;
        switch (sourceLocation){
            case FERMAT_FRAMEWORK:
                resId = ResDrawableFrameworkHelper.obtainFrameworkAvailableResDrawable(activity,resourceId);
                break;
            case DEVELOPER_RESOURCES:
                resId = FermatAppConnectionManager.getFermatAppConnection(publickKeyOwnerOfSource,activity).getResource(resourceId);
                break;
            case INTERNET_URL:
                Log.i(TAG, "Internet request drawable is not supported yet");
                break;
        }
        return resId;
    }

    public static View obtainView(Context context,int resourceId,SourceLocation sourceLocation,String publickKeyOwnerOfSource){
        View view = null;
        switch (sourceLocation){
            case FERMAT_FRAMEWORK:
                break;
            case DEVELOPER_RESOURCES:
                view = FermatAppConnectionManager.getFermatAppConnection(publickKeyOwnerOfSource,context).getSharedView(context,resourceId);
                break;
            case INTERNET_URL:
                Log.i(TAG, "Internet request drawable is not supported yet");
                break;
        }
        return view;
    }

}
