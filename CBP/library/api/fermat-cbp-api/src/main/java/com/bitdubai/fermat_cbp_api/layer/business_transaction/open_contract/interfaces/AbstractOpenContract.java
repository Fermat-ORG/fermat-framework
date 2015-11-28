package com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.interfaces;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.world.exceptions.CantGetIndexException;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ReferenceCurrency;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.enums.ContractType;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.exceptions.CantOpenContractException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.world.interfaces.FiatIndexManager;

import java.util.Collection;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 27/11/15.
 */
public abstract class AbstractOpenContract {


    /**
     * Represents the contract Type, this type will use to select the actors involved in the
     * contract generation and the network service communication
     */
    public ContractType contractType;

    /**
     * This method creates a ContractRecord with given Negotiation clauses.
     * @param negotiationClauses
     * @return
     * @throws InvalidParameterException
     */
    public ContractRecord createPurchaseContractRecord(Collection<Clause> negotiationClauses,
                                               CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation,
                                               FiatIndexManager fiatIndexManager)
            throws InvalidParameterException,
            CantGetIndexException {

        String brokerPublicKey=customerBrokerPurchaseNegotiation.getBrokerPublicKey();
        String customerPublicKey=customerBrokerPurchaseNegotiation.getCustomerPublicKey();
        String negotiationId=customerBrokerPurchaseNegotiation.getNegotiationId().toString();
        //TODO: I'm gonna set the dollar as reference currency for now, it can change in the future.
        float referencePrice = (float) fiatIndexManager.getCurrentIndex(FiatCurrency.US_DOLLAR).getPurchasePrice();
        ContractRecord contractRecord= createPurchaseContractRecordFromNegotiationClauses(negotiationClauses);
        contractRecord.setNegotiationId(negotiationId);
        contractRecord.setPublicKeyBroker(brokerPublicKey);
        contractRecord.setPublicKeyCustomer(customerPublicKey);
        //TODO: I'm gonna set the dollar as reference currency for now, it can change in the future.
        contractRecord.setReferenceCurrency(ReferenceCurrency.DOLLAR);
        contractRecord.setReferencePrice(referencePrice);
        contractRecord.setStatus(ContractStatus.CREATING_CONTRACT);
        //Sets the contractId (hash)
        contractRecord.generateContractHash();
        return contractRecord;

    }

    private ContractRecord createPurchaseContractRecordFromNegotiationClauses(
            Collection<Clause> negotiationClauses)
            throws InvalidParameterException {

        ContractRecord contractRecord=new ContractRecord();
        CurrencyType merchandiseCurrency;
        float merchandiseAmount;
        long merchandiseDeliveryExpirationDate;
        float paymentAmount;
        CurrencyType paymentCurrency;
        long paymentExpirationDate;
        String clauseValue;

        for(Clause clause : negotiationClauses){
            clauseValue=clause.getValue();
            switch (clause.getType()){

                case BROKER_CURRENCY:
                    merchandiseCurrency=CurrencyType.getByCode(clauseValue);
                    contractRecord.setMerchandiseCurrency(merchandiseCurrency);
                    break;
                case BROKER_CURRENCY_QUANTITY:
                    merchandiseAmount=parseToFloat(clauseValue);
                    contractRecord.setMerchandiseAmount(merchandiseAmount);
                    break;
                case BROKER_DATE_TIME_TO_DELIVER:
                    merchandiseDeliveryExpirationDate=parseToLong(clauseValue);
                    contractRecord.setMerchandiseDeliveryExpirationDate(merchandiseDeliveryExpirationDate);
                    break;
                case CUSTOMER_CURRENCY_QUANTITY:
                    paymentAmount=parseToLong(clauseValue);
                    contractRecord.setPaymentAmount(paymentAmount);
                    break;
                case CUSTOMER_CURRENCY:
                    paymentCurrency=CurrencyType.getByCode(clauseValue);
                    contractRecord.setPaymentCurrency(paymentCurrency);
                    break;
                case CUSTOMER_DATE_TIME_TO_DELIVER:
                    paymentExpirationDate=parseToLong(clauseValue);
                    contractRecord.setPaymentExpirationDate(paymentExpirationDate);
                    break;
            }
        }
        return contractRecord;
    }

    /**
     * This method open a new contract
     * @param negotiationId
     * @throws CantOpenContractException
     */
    public abstract void openContract(String negotiationId)throws CantOpenContractException;

    /**
     * This method parse a String object to a float object
     * @param stringValue
     * @return
     * @throws InvalidParameterException
     */
    public float parseToFloat(String stringValue) throws InvalidParameterException {
        if(stringValue==null){
            throw new InvalidParameterException("Cannot parse a null string value to float");
        }else{
            try{
                return Float.valueOf(stringValue);
            }catch (Exception exception){
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE,
                        FermatException.wrapException(exception),
                        "Paring String object to float",
                        "Cannot parse string value to float");
            }

        }
    }

    /**
     * This method parse a String object to a long object
     * @param stringValue
     * @return
     * @throws InvalidParameterException
     */
    public long parseToLong(String stringValue) throws InvalidParameterException {
        if(stringValue==null){
            throw new InvalidParameterException("Cannot parse a null string value to long");
        }else{
            try{
                return Long.valueOf(stringValue);
            }catch (Exception exception){
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE,
                        FermatException.wrapException(exception),
                        "Paring String object to long",
                        "Cannot parse string value to long");
            }

        }
    }

}
