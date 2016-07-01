package com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces;

import android.view.View;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.SourceLocation;

/**
 * Created by Matias Furszyfer on 2016.06.14..
 */
public interface FrameworkHelpers {

    /**
     * Method to obtain res from framework,other app or internet
     * @return
     */
    int obtainRes(int id,SourceLocation sourceLocation,String appOwnerPublicKey);

    /**
     * Method to obtain View from framework, other app or internet
     * @return
     */
    View obtainClassView(int id,SourceLocation sourceLocation,String appOwnerPublicKey);

    /**
     * Method to obtain View from framework or internet
     * @param id
     * @param sourceLocation
     * @return
     */
    View obtainFrameworkOptionMenuClassViewAvailable(int id,SourceLocation sourceLocation);

    View obtainFrameworkOptionMenuClassViewAvailable(int id, SourceLocation sourceLocation, Object[] listeners);
}
