package com.bitdubai.fermat_api.layer._11_network_service.wallet_resources;

import com.bitdubai.fermat_api.layer._11_network_service.CantCheckResourcesException;
import com.bitdubai.fermat_api.layer._11_network_service.CantGetResourcesException;
import com.bitdubai.fermat_api.layer._15_middleware.app_runtime.enums.Wallets;

/**
 *
 *  <p>The abstract class <code>com.bitdubai.fermat_api.layer._11_network_service.wallet_resources.WalletResourcesManager/code> is a interface
 *     that define the methods to retrieve wallets resource files.
 *
 *
 *  @author  Loui
 *  @version 1.0.0
 *  @since   18/02/15.
 * */
public interface  WalletResourcesManager {


    public void checkResources() throws CantCheckResourcesException;

    public byte[] getImageResource(String imageName) throws CantGetResourcesException;

    public String getLayoutResource(String layoutName) throws CantGetResourcesException;

    public void setwalletType(Wallets type);

}
