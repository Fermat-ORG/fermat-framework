package com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.crypto.util.CryptoHasher;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_cbp_api.all_definition.contract.Contract;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ReferenceCurrency;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 27/11/15.
 */
public class ContractRecord implements Contract {

    CurrencyType merchandiseCurrency;
    float merchandiseAmount;
    long merchandiseDeliveryExpirationDate;
    String negotiationId;
    float paymentAmount;
    CurrencyType paymentCurrency;
    long paymentExpirationDate;
    String publicKeyBroker;
    String publicKeyCustomer;
    ReferenceCurrency referenceCurrency;
    float referencePrice;
    ContractStatus status;

    //TODO: change for contractId when the contractId is set as a String object
    String contractHash;
    /**
     * TODO: Change the method return when the contract change.
     * @return
     */
    @Override
    public String getContractId() {
        return contractHash;
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

    @Override
    public float getMerchandiseAmount() {
        return this.merchandiseAmount;
    }

    @Override
    public CurrencyType getMerchandiseCurrency() {
        return this.merchandiseCurrency;
    }

    @Override
    public long getMerchandiseDeliveryExpirationDate() {
        return this.merchandiseDeliveryExpirationDate;
    }

    public String getNegotiationId(){
        return this.negotiationId;
    }

    @Override
    public float getPaymentAmount() {
        return this.paymentAmount;
    }

    @Override
    public CurrencyType getPaymentCurrency() {
        return this.paymentCurrency;
    }

    @Override
    public long getPaymentExpirationDate() {
        return this.paymentExpirationDate;
    }

    @Override
    public String getPublicKeyBroker() {
        return this.publicKeyBroker;
    }

    @Override
    public String getPublicKeyCustomer() {
        return this.publicKeyCustomer;
    }

    @Override
    public ReferenceCurrency getReferenceCurrency() {
        return this.referenceCurrency;
    }

    @Override
    public float getReferencePrice() {
        return this.referencePrice;
    }

    @Override
    public ContractStatus getStatus() {
        return status;
    }

    public void setMerchandiseAmount(float merchandiseAmount) {
        this.merchandiseAmount=merchandiseAmount;
    }

    public void setMerchandiseCurrency(CurrencyType merchandiseCurrency) {
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

    public void setPaymentCurrency(CurrencyType paymentCurrency) {
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

    /**
     * The string of the ContractRecord will be used to generate a unique Hash.
     * This hash will be used as Id.
     * I generate an XML with the class structure.
     * @return
     */
    @Override
    public String toString() {
        return XMLParser.parseObject(this);
    }

}
