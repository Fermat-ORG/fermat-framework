package org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.issuer_appropriation.developer.version_1.structure.functional;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.util.Validate;

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import org.fermat.fermat_dap_api.layer.all_definition.enums.AppropriationStatus;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.interfaces.AppropriationTransactionRecord;

import java.util.Date;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 12/11/15.
 */
public class IssuerAppropriationTransactionRecordImpl implements AppropriationTransactionRecord {

    //VARIABLE DECLARATION

    private String transactionId;

    private BlockchainNetworkType networkType;

    private AppropriationStatus status;

    private DigitalAssetMetadata digitalAssetMetadata;

    private String bitcoinWalletPublicKey;

    private String issuerWalletPublicKey;

    private CryptoAddress addressTo;

    private long startTime;

    private long endTime;

    private String genesisTransaction;


    //CONSTRUCTORS

    public IssuerAppropriationTransactionRecordImpl(String transactionId,
                                                    BlockchainNetworkType networkType,
                                                    AppropriationStatus status,
                                                    DigitalAssetMetadata digitalAssetMetadata,
                                                    String bitcoinWalletPublicKey,
                                                    String issuerWalletPublicKey,
                                                    CryptoAddress addressTo,
                                                    long startTime,
                                                    long endTime,
                                                    String genesisTransaction) {
        this.transactionId = transactionId;
        this.networkType = networkType;
        this.status = status;
        this.digitalAssetMetadata = digitalAssetMetadata;
        this.bitcoinWalletPublicKey = bitcoinWalletPublicKey;
        this.issuerWalletPublicKey = issuerWalletPublicKey;
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
                ", digitalAssetMetadata=" + digitalAssetMetadata +
                ", bitcoinWalletPublicKey=" + bitcoinWalletPublicKey +
                ", walletPublicKey='" + issuerWalletPublicKey + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", genesisTransaction='" + genesisTransaction + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IssuerAppropriationTransactionRecordImpl that = (IssuerAppropriationTransactionRecordImpl) o;

        if (startTime != that.startTime) return false;
        if (endTime != that.endTime) return false;
        if (!transactionId.equals(that.transactionId)) return false;
        if (status != that.status) return false;
        if (!bitcoinWalletPublicKey.equals(that.bitcoinWalletPublicKey)) return false;
        if (!issuerWalletPublicKey.equals(that.issuerWalletPublicKey)) return false;
        return genesisTransaction.equals(that.genesisTransaction);

    }

    @Override
    public int hashCode() {
        int result = transactionId.hashCode();
        result = 31 * result + status.hashCode();
        result = 31 * result + bitcoinWalletPublicKey.hashCode();
        result = 31 * result + issuerWalletPublicKey.hashCode();
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
    public BlockchainNetworkType networkType() {
        return networkType;
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
        return digitalAssetMetadata.getDigitalAsset();
    }

    @Override
    public DigitalAssetMetadata assetMetadata() {
        return digitalAssetMetadata;
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
    public String walletPublicKey() {
        return issuerWalletPublicKey;
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
