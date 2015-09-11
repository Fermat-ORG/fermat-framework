package com.bitdubai.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces;

import com.bitdubai.fermat_dap_api.layer.dap_identity.issuer.exceptions.CantCreateNewIssuerException;

import java.util.List;

/**
 * Created by Nerio on 07/09/15.
 */
public interface IdentityAssetIssuerManager {

    List<IdentityAssetIssuer> getIdentityAssetIssuersFromCurrentDeviceUser() throws CantCreateNewIssuerException;

    IdentityAssetIssuer createNewIdentityAssetIssuer(String alias) throws CantCreateNewIssuerException;

}
