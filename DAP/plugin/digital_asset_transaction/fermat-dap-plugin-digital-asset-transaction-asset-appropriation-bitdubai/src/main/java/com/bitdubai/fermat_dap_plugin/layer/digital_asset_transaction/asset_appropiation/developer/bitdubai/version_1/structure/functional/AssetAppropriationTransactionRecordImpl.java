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

    private String bitcoinWalletPublicKey;

    private String userWalletPublicKey;

    private CryptoAddress addressTo;

    private long startTime;

    private long endTime;

    private String genesisTransaction;


    //CONSTRUCTORS

    public AssetAppropriationTransactionRecordImpl(String transactionId,
                                                   AppropriationStatus status,
                                                   DigitalAsset digitalAsset,
                                                   String bitcoinWalletPublicKey,
                                                   String userWalletPublicKey,
                                                   CryptoAddress addressTo,
                                                   long startTime,
                                                   long endTime,
                                                   String genesisTransaction) {
        this.transactionId = transactionId;
        this.status = status;
        this.digitalAsset = digitalAsset;
        this.bitcoinWalletPublicKey = bitcoinWalletPublicKey;
        this.userWalletPublicKey = userWalletPublicKey;
        this.addressTo = addressTo;
        this.startTime = startTime;
        this.endTime = endTime;
        this.genesisTransaction = genesisTransaction;
    }


    //PUBLIC METHODS

    @Override
    public String toString() {
        return "AssetAppropriationTransactionRecordImpl{" +
                "transactionId='" + transactionId + '\'' +
                ", status=" + status +
                ", digitalAsset=" + digitalAsset +
                ", bitcoinWalletPublicKey=" + bitcoinWalletPublicKey +
                ", userWalletPublicKey='" + userWalletPublicKey + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", genesisTransaction='" + genesisTransaction + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AssetAppropriationTransactionRecordImpl that = (AssetAppropriationTransactionRecordImpl) o;

        if (startTime != that.startTime) return false;
        if (endTime != that.endTime) return false;
        if (!transactionId.equals(that.transactionId)) return false;
        if (status != that.status) return false;
        if (!digitalAsset.equals(that.digitalAsset)) return false;
        if (!bitcoinWalletPublicKey.equals(that.bitcoinWalletPublicKey)) return false;
        if (!userWalletPublicKey.equals(that.userWalletPublicKey)) return false;
        return genesisTransaction.equals(that.genesisTransaction);

    }

    @Override
    public int hashCode() {
        int result = transactionId.hashCode();
        result = 31 * result + status.hashCode();
        result = 31 * result + digitalAsset.hashCode();
        result = 31 * result + bitcoinWalletPublicKey.hashCode();
        result = 31 * result + userWalletPublicKey.hashCode();
        result = 31 * result + (int) (startTime ^ (startTime >>> 32));
        result = 31 * result + (int) (endTime ^ (endTime >>> 32));
        result = 31 * result + genesisTransaction.hashCode();
        return result;
    }

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
    public String btcWalletPublicKey() {
        return bitcoinWalletPublicKey;
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
