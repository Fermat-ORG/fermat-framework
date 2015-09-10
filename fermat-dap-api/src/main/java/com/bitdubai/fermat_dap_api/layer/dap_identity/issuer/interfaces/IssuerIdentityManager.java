package com.bitdubai.fermat_dap_api.layer.dap_identity.issuer.interfaces;

import com.bitdubai.fermat_dap_api.layer.dap_identity.issuer.exceptions.CantCreateNewIssuerException;

import java.util.List;

/**
 * Created by Nerio on 07/09/15.
 */
public interface IssuerIdentityManager {

    List<IssuerActor> getIssuersFromCurrentDeviceUser() throws CantCreateNewIssuerException;

    IssuerActor createNewIssuer(String alias) throws CantCreateNewIssuerException;

}
