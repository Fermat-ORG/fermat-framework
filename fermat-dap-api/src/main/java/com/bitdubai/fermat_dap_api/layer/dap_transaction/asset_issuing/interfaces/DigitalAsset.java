package com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.interfaces;

import com.bitdubai.fermat_dap_api.CantSetObjectException;
import com.bitdubai.fermat_dap_api.ObjectNotSetException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 04/09/15.
 */
public interface DigitalAsset {

    String getDescription() throws ObjectNotSetException;

    //TODO: definir bien el return de este método
    void getGenesisAddress() throws ObjectNotSetException;

    long getGenesisAmount() throws ObjectNotSetException;

    String getGenesisTransaction() throws ObjectNotSetException;

    //TODO: definir bien el return de este método
    void getIdentityAssetIssuer() throws ObjectNotSetException;

    String getName() throws ObjectNotSetException;

    //TODO: definir bien el return de este método
    void getResources() throws ObjectNotSetException;


    void setDescription(String digitalAssetDescription) throws CantSetObjectException;

    //TODO: definir bien el argumento de este método
    void setGenesisAddress() throws CantSetObjectException;

    void setGenesisAmount(long digitalAssetGenesisAmount) throws CantSetObjectException;

    void setGenesisTransaction(String digitalAssetGenesisTransaction) throws CantSetObjectException;

    //TODO: definir bien el argumento de este método
    void setIdentityAssetIssuer() throws CantSetObjectException;

    void setName(String digitalAssetName) throws CantSetObjectException;

    //TODO: definir bien el return de este método
    void setResources() throws CantSetObjectException;


}
