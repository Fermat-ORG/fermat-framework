package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces;


/**
 * Created by Matias Furszyfer on 2015.09.28..
 */
public interface FermatCallback {

    /**
     * You have to parse the object to the type of View
     *
     * @param v View object
     */
    void onTouchView(Object v);

    int getCallBackId();

}
