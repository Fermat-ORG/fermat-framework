package com.bitdubai.fermat_api.layer.all_definition.navigation_structure;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatFragment;

import java.io.Serializable;

/**
 * Created by Matias Furszyfer on 2015.07.17..
 */
public class Fragment implements FermatFragment,Serializable {

    /**
     * Fragment type for FragmentFactory
     */
    String type;
    /**
     * Fragment back if the user press the back button
     */
    String fragmentBack;
    /**
     * This field will be used in case of a comboApp, when a developer want to reuse fragments from another app he have to know the app publicKey
     */
    private String pulickKeyFragmentFrom;

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

    public String getPulickKeyFragmentFrom() {
        return pulickKeyFragmentFrom;
    }

    public void setFragmentFromApp(String appPublicKey) {
        this.pulickKeyFragmentFrom = appPublicKey;
    }
}
