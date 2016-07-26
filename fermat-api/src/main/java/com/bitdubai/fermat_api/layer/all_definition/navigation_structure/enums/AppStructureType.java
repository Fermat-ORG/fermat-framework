package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums;

/**
 * Created by Matias Furszyfer on 2016.06.02..
 */
public enum AppStructureType {


    // Reference Apps use one module and creates his own fragments
    REFERENCE,
    // Niche Apps re use fragments from another app speciali.. in one area
    NICHE,
    // Combo Apps type 1 re use modules from another App
    COMBO_TYPE_1,
    // Combo Apps type 2 re use fragments from another App
    COMBO_TYPE_2,
    // Combo Apps type 3 re use fragments from another App and include his own fragments
    COMBO_TYPE_3;


}
