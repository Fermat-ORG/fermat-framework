package com.bitdubai.fermat_api.layer.ccp_engine.wallet_runtime;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.WalletNavigationStructure;
import com.bitdubai.fermat_api.layer.ccp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectNavigationStructureException;
import com.bitdubai.fermat_api.layer.ccp_middleware.wallet_factory.exceptions.CantSetWalletFactoryProjectNavigationStructureException;

/**
 * Created by Matias Furszyfer on 2015.07.29..
 */
public interface XML {

    //TODO: cambiar las excepciones por otras del wallet runtime

    public WalletNavigationStructure getNavigationStructure(String navigationStructure) ;

    public String parseNavigationStructureXml(WalletNavigationStructure walletNavigationStructure);

    public void setNavigationStructureXml(WalletNavigationStructure walletNavigationStructure) ;

}
