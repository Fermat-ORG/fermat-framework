package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces;

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectVersionException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectVersionsException;

import java.util.List;

/**
 * Created by eze on 2015.07.14..
 */
public interface WalletFactoryProject {

    public String getProjectName();

    public List<String> getVersions() throws CantGetWalletFactoryProjectVersionsException;

    public WalletFactoryProjectVersion getVersion(String version) throws CantGetWalletFactoryProjectVersionException;

}
