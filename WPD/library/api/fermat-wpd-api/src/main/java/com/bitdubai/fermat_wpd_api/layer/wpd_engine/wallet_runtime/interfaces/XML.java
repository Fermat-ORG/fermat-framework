package com.bitdubai.fermat_wpd_api.layer.wpd_engine.wallet_runtime.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatStructure;
import com.bitdubai.fermat_wpd_api.all_definition.AppNavigationStructure;

/**
 * Created by Matias Furszyfer on 2015.07.29..
 */
public interface XML {

    //TODO: cambiar las excepciones por otras del wallet runtime

    AppNavigationStructure getNavigationStructure(String navigationStructure) ;

    String parseNavigationStructureXml(AppNavigationStructure walletNavigationStructure);

    void setNavigationStructure(FermatStructure walletNavigationStructure) ;

}
