package com.bitdubai.fermat_api.layer.dmp_engine.wallet_runtime;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Wallet;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectNavigationStructureException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantSetWalletFactoryProjectNavigationStructureException;

/**
 * Created by Matias Furszyfer on 2015.07.29..
 */
public interface XML {

    //TODO: cambiar las excepciones por otras del wallet runtime

    public Wallet getNavigationStructure(String navigationStructure) throws CantGetWalletFactoryProjectNavigationStructureException;

    public String getNavigationStructureXml(Wallet wallet) throws CantGetWalletFactoryProjectNavigationStructureException;

    public void setNavigationStructureXml(Wallet wallet) throws CantSetWalletFactoryProjectNavigationStructureException;

}
