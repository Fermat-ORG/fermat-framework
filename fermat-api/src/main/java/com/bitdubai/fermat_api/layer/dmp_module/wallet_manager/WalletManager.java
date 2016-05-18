package com.bitdubai.fermat_api.layer.dmp_module.wallet_manager;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.CantCreateNewWalletException;
import com.bitdubai.fermat_api.layer.dmp_module.AppManager;
import com.bitdubai.fermat_api.layer.dmp_module.DesktopManagerSettings;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.structure.WalletManagerModuleInstalledWallet;
import com.bitdubai.fermat_api.layer.modules.ModuleSettingsImpl;

import java.util.List;

/**
 * Created by ciencias on 25.01.15.
 */
public interface WalletManager extends AppManager,ModuleSettingsImpl<DesktopManagerSettings> {


    void loadUserWallets (String deviceUserPublicKey) throws CantLoadWalletsException;

    void createDefaultWallets (String deviceUserPublicKey) throws CantCreateDefaultWalletsException;
    
    void enableWallet() throws CantEnableWalletException;

    WalletManagerModuleInstalledWallet getInstalledWallet(String walletPublicKey) throws CantCreateNewWalletException;
    InstalledWallet getInstalledWalletFromPlatformIdentifier(String platformIdentifier) throws CantCreateNewWalletException, InvalidParameterException;

    /**
     * This method create a new Intra User Identity
     *
     * @param alias
     * @param profileImage
     * @return
     * @throws WalletCreateNewIntraUserIdentityException
     */

     void createNewIntraWalletUser(String alias, String phrase, byte[] profileImage) throws WalletCreateNewIntraUserIdentityException;


    /**
     * This method returns if exists a Intra User Identity
     * @return
     * @throws CantGetIfIntraWalletUsersExistsException
     */
    boolean hasIntraUserIdentity() throws CantGetIfIntraWalletUsersExistsException;

    List<InstalledWallet> getInstalledWallets()throws Exception;



}