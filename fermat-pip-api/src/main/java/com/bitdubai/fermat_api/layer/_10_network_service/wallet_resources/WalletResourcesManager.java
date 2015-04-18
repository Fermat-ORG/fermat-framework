package com.bitdubai.fermat_api.layer._10_network_service.wallet_resources;

import com.bitdubai.fermat_api.layer._10_network_service.CantCheckResourcesException;
import com.bitdubai.fermat_api.layer._10_network_service.CantGetResourcesException;
import com.bitdubai.fermat_api.layer._14_middleware.app_runtime.enums.Wallets;

/**
 * Created by loui on 18/02/15.
 */
public interface  WalletResourcesManager {


    public void checkResources() throws CantCheckResourcesException;

    public byte[] getImageResource() throws CantGetResourcesException;

    public String getLayoutResource(/*WalletType, Developer, version, publisher*/) throws CantGetResourcesException;

    public void setImageName(String name);

    public void setwalletType(Wallets type);

}
