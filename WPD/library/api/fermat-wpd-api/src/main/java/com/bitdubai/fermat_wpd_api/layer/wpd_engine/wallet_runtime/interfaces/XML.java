package com.bitdubai.fermat_wpd_api.layer.wpd_engine.wallet_runtime.interfaces;

import com.bitdubai.fermat_wpd_api.all_definition.WalletNavigationStructure;

/**
 * Created by Matias Furszyfer on 2015.07.29..
 */
public interface XML {

    //TODO: cambiar las excepciones por otras del wallet runtime

    WalletNavigationStructure getNavigationStructure(String navigationStructure) ;

    String parseNavigationStructureXml(WalletNavigationStructure walletNavigationStructure);

    void setNavigationStructureXml(WalletNavigationStructure walletNavigationStructure) ;

}
