package com.bitdubai.fermat_wpd_api.layer.wpd_engine.wallet_runtime.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.WalletNavigationStructure;

/**
 * Created by Matias Furszyfer on 2015.07.29..
 */
public interface XML {

    //TODO: cambiar las excepciones por otras del wallet runtime

    public WalletNavigationStructure getNavigationStructure(String navigationStructure) ;

    public String parseNavigationStructureXml(WalletNavigationStructure walletNavigationStructure);

    public void setNavigationStructureXml(WalletNavigationStructure walletNavigationStructure) ;

}
