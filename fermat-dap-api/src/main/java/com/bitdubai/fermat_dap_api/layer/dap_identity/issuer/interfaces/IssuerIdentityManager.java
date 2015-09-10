package com.bitdubai.fermat_dap_api.layer.dap_identity.issuer.interfaces;

import com.bitdubai.fermat_dap_api.layer.dap_identity.issuer.exceptions.CantCreateNewIssuerException;

import java.util.List;

/**
 * Created by Nerio on 07/09/15.
 */
public interface IssuerIdentityManager {

    List<IssuerIdentity> getIssuersFromCurrentDeviceUser() throws CantCreateNewIssuerException;

    IssuerIdentity createNewIssuer(String alias) throws CantCreateNewIssuerException;

}
