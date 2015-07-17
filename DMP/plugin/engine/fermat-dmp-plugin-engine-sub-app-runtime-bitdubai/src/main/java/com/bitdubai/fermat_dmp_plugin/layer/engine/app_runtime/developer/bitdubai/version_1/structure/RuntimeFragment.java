package com.bitdubai.fermat_dmp_plugin.layer.engine.app_runtime.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.Fragment;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.Fragments;

/**
 * Created by ciencias on 2/14/15.
 */
public class RuntimeFragment implements Fragment {

    Fragments type;

    Fragments fragmentBack;

    Object[] context;

    public void setBack(Fragments fragmentBack)
    {
        this.fragmentBack = fragmentBack;
    }

    public void setType(Fragments type) {
        this.type = type;
    }

    /**
     * RuntimeFragment interface implementation.
     */
    @Override
    public Fragments getType() {
        return type;
    }

    public Fragments getBack(){

        return this.fragmentBack;
    }



    public void setContext(Object[] context){
        this.context = context;
    }


    public Object[] getContext(){
        return this.context;

    }


}
