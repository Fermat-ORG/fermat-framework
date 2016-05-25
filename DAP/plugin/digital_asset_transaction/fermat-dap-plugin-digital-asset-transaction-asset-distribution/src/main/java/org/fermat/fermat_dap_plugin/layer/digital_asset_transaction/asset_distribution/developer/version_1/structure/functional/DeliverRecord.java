package org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.version_1.structure.functional;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import org.fermat.fermat_dap_api.layer.all_definition.enums.DistributionStatus;

import java.util.Date;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 22/12/15.
 */
public class DeliverRecord {

    //VARIABLE DECLARATION
    private String transactionId;
    private String genesisTransaction;
    private DigitalAssetMetadata digitalAssetMetadata;
    private String actorAssetUser;
    private Date startTime;
    private Date timeOut;
    private DistributionStatus state;
    private String genesisTransactionSent;
    private long attemptNumber;
    private BlockchainNetworkType networkType;

    //CONSTRUCTORS


    public DeliverRecord() {
    }

    public DeliverRecord(String transactionId, String genesisTransaction, DigitalAssetMetadata digitalAssetMetadata, String actorAssetUser, Date startTime, Date timeOut, DistributionStatus state, String genesisTransactionSent, long attemptNumber, BlockchainNetworkType networkType) {
        this.transactionId = transactionId;
        this.genesisTransaction = genesisTransaction;
        this.digitalAssetMetadata = digitalAssetMetadata;
        this.actorAssetUser = actorAssetUser;
        this.startTime = startTime;
        this.timeOut = timeOut;
        this.state = state;
        this.genesisTransactionSent = genesisTransactionSent;
        this.attemptNumber = attemptNumber;
        this.networkType = networkType;
    }

//PUBLIC METHODS

    @Override
    public String toString() {
        return "DeliverRecord{" +
                "transactionId='" + transactionId + '\'' +
                ", genesisTransaction='" + genesisTransaction + '\'' +
                ", digitalAssetMetadata=" + digitalAssetMetadata +
                ", actorAssetUser=" + actorAssetUser +
                ", startTime=" + startTime +
                ", timeOut=" + timeOut +
                ", state=" + state +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeliverRecord that = (DeliverRecord) o;

        if (!getTransactionId().equals(that.getTransactionId())) return false;
        if (!getGenesisTransaction().equals(that.getGenesisTransaction())) return false;
        if (!getDigitalAssetMetadata().equals(that.getDigitalAssetMetadata())) return false;
        if (!getActorAssetUserPublicKey().equals(that.getActorAssetUserPublicKey())) return false;
        if (!getStartTime().equals(that.getStartTime())) return false;
        if (!getTimeOut().equals(that.getTimeOut())) return false;
        return getState() == that.getState();

    }

    @Override
    public int hashCode() {
        int result = getTransactionId().hashCode();
        result = 31 * result + getGenesisTransaction().hashCode();
        result = 31 * result + getDigitalAssetMetadata().hashCode();
        result = 31 * result + getActorAssetUserPublicKey().hashCode();
        result = 31 * result + getStartTime().hashCode();
        result = 31 * result + getTimeOut().hashCode();
        result = 31 * result + getState().hashCode();
        return result;
    }

    //PRIVATE METHODS

    //GETTER AND SETTERS

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getGenesisTransaction() {
        return genesisTransaction;
    }

    public void setGenesisTransaction(String genesisTransaction) {
        this.genesisTransaction = genesisTransaction;
    }

    public DigitalAssetMetadata getDigitalAssetMetadata() {
        return digitalAssetMetadata;
    }

    public void setDigitalAssetMetadata(DigitalAssetMetadata digitalAssetMetadata) {
        this.digitalAssetMetadata = digitalAssetMetadata;
    }

    public String getActorAssetUserPublicKey() {
        return actorAssetUser;
    }

    public void setActorAssetUserPublicKey(String actorAssetUser) {
        this.actorAssetUser = actorAssetUser;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Date timeOut) {
        this.timeOut = timeOut;
    }

    public DistributionStatus getState() {
        return state;
    }

    public void setState(DistributionStatus state) {
        this.state = state;
    }

    public String getGenesisTransactionSent() {
        return genesisTransactionSent;
    }

    public void setGenesisTransactionSent(String genesisTransactionSent) {
        this.genesisTransactionSent = genesisTransactionSent;
    }

    public long getAttemptNumber() {
        return attemptNumber;
    }

    public void setAttemptNumber(long attemptNumber) {
        this.attemptNumber = attemptNumber;
    }

    public BlockchainNetworkType getNetworkType() {
        return networkType;
    }

    public void setNetworkType(BlockchainNetworkType networkType) {
        this.networkType = networkType;
    }

    //INNER CLASSES
}
