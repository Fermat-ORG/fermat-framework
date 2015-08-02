package com.bitdubai.fermat_api.layer.dmp_module.wallet_factory.interfaces;

import com.bitdubai.fermat_api.layer.dmp_module.wallet_factory.exceptions.CantGetAvailableDevelopersException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_factory.exceptions.CantGetAvailableProjectsException;

import java.util.List;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.dmp_module.wallet_factory.interfaces.WalletFactoryManager</code>
 * indicates the functionality of a WalletFactoryManager
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 31/07/15.
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface WalletFactoryManager {

    WalletFactoryDeveloper getLoggedDeveloper();

    List<WalletFactoryDeveloper> getAvailableDevelopers() throws CantGetAvailableDevelopersException;

    List<String> getAvailableProjects() throws CantGetAvailableProjectsException;

}
