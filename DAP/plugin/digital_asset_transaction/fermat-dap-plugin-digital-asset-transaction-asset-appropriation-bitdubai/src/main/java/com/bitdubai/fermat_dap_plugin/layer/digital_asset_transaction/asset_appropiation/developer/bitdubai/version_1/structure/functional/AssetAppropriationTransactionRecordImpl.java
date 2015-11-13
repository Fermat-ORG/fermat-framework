package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_appropiation.developer.bitdubai.version_1.structure.functional;

import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.AppropriationStatus;
import com.bitdubai.fermat_dap_api.layer.all_definition.util.Validate;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_appropriation.interfaces.AssetAppropriationTransactionRecord;

import java.util.Date;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 12/11/15.
 */
public class AssetAppropriationTransactionRecordImpl implements AssetAppropriationTransactionRecord {

    //VARIABLE DECLARATION

    private String transactionId;

    private AppropriationStatus status;

    private DigitalAsset digitalAsset;

    private CryptoAddress addressTo;

    private String userWalletPublicKey;

    private long startTime;

    private long endTime;

    private String genesisTransaction;


    //CONSTRUCTORS

    public AssetAppropriationTransactionRecordImpl(String transactionId,
                                                   AppropriationStatus status,
                                                   DigitalAsset digitalAsset,
                                                   CryptoAddress addressTo,
                                                   String userWalletPublicKey,
                                                   long startTime,
                                                   long endTime,
                                                   String genesisTransaction) {
        this.transactionId = transactionId;
        this.status = status;
        this.digitalAsset = digitalAsset;
        this.addressTo = addressTo;
        this.userWalletPublicKey = userWalletPublicKey;
        this.startTime = startTime;
        this.endTime = endTime;
        this.genesisTransaction = genesisTransaction;
    }


    //PUBLIC METHODS

    //PRIVATE METHODS

    //GETTER AND SETTERS
    @Override
    public String transactionRecordId() {
        return transactionId;
    }

    @Override
    public String genesisTransaction() {
        return genesisTransaction;
    }

    @Override
    public AppropriationStatus status() {
        return status;
    }

    @Override
    public DigitalAsset digitalAsset() {
        return digitalAsset;
    }

    @Override
    public CryptoAddress addressTo() {
        return addressTo;
    }

    @Override
    public String userWalletPublicKey() {
        return userWalletPublicKey;
    }

    @Override
    public Date startTime() {
        return new Date(startTime);
    }

    @Override
    public Date endTime() {
        if (endTime == Validate.MAX_DATE) return null;
        return new Date(endTime);
    }

    //INNER CLASSES
}
