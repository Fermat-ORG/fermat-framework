package com.bitdubai.sub_app.wallet_factory.callbacks;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatCallback;

/**
 * Created by Matias Furszyfer on 2015.09.28..
 */
public class FactoryCallback implements FermatCallback {

    /**
     * UUID toString
     */
    private String id;


    /**
     * You have to parse the object to the type of View
     *
     * @param v View object
     */
    @Override
    public void onTouchView(Object v) {

    }

    @Override
    public int getCallBackId() {
        return 0;
    }
}
