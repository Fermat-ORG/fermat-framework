package com.bitdubai.fermat_api.layer.all_definition.navigation_structure;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatFragment;

import java.io.Serializable;

/**
 * Created by rodrigo on 2015.07.17..
 */
public class Fragment implements FermatFragment,Serializable {

    String type;
    String fragmentBack;
    Object[] context;

    public void setBack(String fragmentBack)
    {
        this.fragmentBack = fragmentBack;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * Fragment interface implementation.
     */
    @Override
    public String getType() {
        return type;
    }

    public String getBack(){
        return this.fragmentBack;
    }



    public void setContext(Object[] context){
        this.context = context;
    }


    public Object[] getContext(){
        return this.context;

    }
}
