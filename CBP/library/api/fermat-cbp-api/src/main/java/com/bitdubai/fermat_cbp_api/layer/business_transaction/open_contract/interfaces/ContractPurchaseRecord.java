package com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.crypto.util.CryptoHasher;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.all_definition.contract.ContractClause;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ReferenceCurrency;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchase;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 27/11/15.
 */
public class ContractPurchaseRecord implements CustomerBrokerContractPurchase, Serializable {

    Collection<ContractClause> contractClauses;
    long dayTime;
    Currency merchandiseCurrency;
    float merchandiseAmount;
    long merchandiseDeliveryExpirationDate;
    String negotiationId;
    float paymentAmount;
    Currency paymentCurrency;
    long paymentExpirationDate;
    String publicKeyBroker;
    String publicKeyCustomer;
    ReferenceCurrency referenceCurrency;
    float referencePrice;
    ContractStatus status;
    String contractHash;
    Boolean nearExpirationDatetime;
    String cancelReason;

    /**
     * Represents the contract id/hash
     * @return
     */
    
    public String getContractId() {
        return contractHash;
    }

    @Override
    public String getNegotiatiotId() {
        return negotiationId;
    }

    /**
     * This method generate a hash that contains the all the encrypted contract in XML.
     * This hash is used as the contract Id
     * @return
     */
    public String generateContractHash(){
        this.contractHash=CryptoHasher.performSha256(this.toString());
        return this.contractHash;
    }
    
    public float getMerchandiseAmount() {
        return this.merchandiseAmount;
    }

    public Currency getMerchandiseCurrency() {
        return this.merchandiseCurrency;
    }

    public long getMerchandiseDeliveryExpirationDate() {
        return this.merchandiseDeliveryExpirationDate;
    }

    public String getNegotiationId(){
        return this.negotiationId;
    }

    public float getPaymentAmount() {
        return this.paymentAmount;
    }

    public Currency getPaymentCurrency() {
        return this.paymentCurrency;
    }

    public long getPaymentExpirationDate() {
        return this.paymentExpirationDate;
    }

    public String getPublicKeyBroker() {
        return this.publicKeyBroker;
    }

    @Override
    public Long getDateTime() {
        return this.dayTime;
    }

    public String getPublicKeyCustomer() {
        return this.publicKeyCustomer;
    }

    public ReferenceCurrency getReferenceCurrency() {
        return this.referenceCurrency;
    }

    public float getReferencePrice() {
        return this.referencePrice;
    }

    public ContractStatus getStatus() {
        return status;
    }

    @Override
    public Collection<ContractClause> getContractClause() {
        return this.contractClauses;
    }

    @Override
    public Boolean getNearExpirationDatetime() {
        return nearExpirationDatetime;
    }

    @Override
    public String getCancelReason() { return cancelReason;}

    public void setNearExpirationDatetime(Boolean nearExpirationDatetime) {
        this.nearExpirationDatetime = nearExpirationDatetime;
    }

    public void setContractClauses(Collection<ContractClause> contractClauses){
        this.contractClauses=contractClauses;
    }

    public void setDayTime(long dayTime){
        this.dayTime=dayTime;
    }

    public void setMerchandiseAmount(float merchandiseAmount) {
        this.merchandiseAmount=merchandiseAmount;
    }

    public void setMerchandiseCurrency(Currency merchandiseCurrency) {
        this.merchandiseCurrency=merchandiseCurrency;
    }

    public void setMerchandiseDeliveryExpirationDate(long merchandiseDeliveryExpirationDate) {
        this.merchandiseDeliveryExpirationDate=merchandiseDeliveryExpirationDate;
    }

    /**
     * The negotiationId is used to link this contract with the genesis negotiation.
     * Also, this negotiation id helps to create an unique contract hash (id) for this contract record.
     * @param negotiationId
     */
    public void setNegotiationId(String negotiationId){
        this.negotiationId=negotiationId;
    }

    public void setPaymentAmount(float paymentAmount) {
        this.paymentAmount=paymentAmount;
    }

    public void setPaymentCurrency(Currency paymentCurrency) {
        this.paymentCurrency=paymentCurrency;
    }

    public void setPaymentExpirationDate(long paymentExpirationDate) {
        this.paymentExpirationDate=paymentExpirationDate;
    }

    public void setPublicKeyBroker(String publicKeyBroker) {
        this.publicKeyBroker=publicKeyBroker;
    }

    public void setPublicKeyCustomer(String publicKeyCustomer) {
        this.publicKeyCustomer=publicKeyCustomer;
    }

    public void setReferencePrice(float referencePrice) {
        this.referencePrice=referencePrice;
    }

    public void setReferenceCurrency(ReferenceCurrency referenceCurrency) {
        this.referenceCurrency=referenceCurrency;
    }

    public void setStatus(ContractStatus status){
        this.status=status;
    }

    public void setCancelReason(String reason){
        this.cancelReason = reason;
    }
    /**
     * The string of the ContractSaleRecord will be used to generate a unique Hash.
     * This hash will be used as Id.
     * I generate an XML with the class structure.
     * @return
     */
    @Override
    public String toString() {
        return "ContractRecord{" +
                ", dayTime=" + dayTime +
                ", merchandiseCurrency=" + merchandiseCurrency +
                ", merchandiseAmount=" + merchandiseAmount +
                ", merchandiseDeliveryExpirationDate=" + merchandiseDeliveryExpirationDate +
                ", negotiationId='" + negotiationId + '\'' +
                ", paymentAmount=" + paymentAmount +
                ", paymentCurrency=" + paymentCurrency +
                ", paymentExpirationDate=" + paymentExpirationDate +
                ", publicKeyBroker='" + publicKeyBroker + '\'' +
                ", publicKeyCustomer='" + publicKeyCustomer + '\'' +
                ", referenceCurrency=" + referenceCurrency +
                ", status=" + status +
                ", contractHash='" + contractHash + '\'' +
                ", nearExpirationDatetime=" + nearExpirationDatetime +
                '}';
    }
}
