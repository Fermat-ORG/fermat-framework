package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationType;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantUpdateCustomerBrokerPurchaseNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantGetListClauseException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.exceptions.CantCryptoAddressesNewException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.utils.CryptoVaultSelector;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.utils.WalletManagerSelector;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.database.CustomerBrokerCloseNegotiationTransactionDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.exceptions.CantClosePurchaseNegotiationTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.exceptions.CantNegotiationAddCryptoAdreessException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.exceptions.CantRegisterCustomerBrokerCloseNegotiationTransactionException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces.CryptoAddressRequest;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces.CryptoAddressesManager;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions.CantGetCryptoPaymentRegistryException;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.interfaces.CryptoPaymentManager;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.interfaces.CryptoPaymentRegistry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 22.12.15.
 */
public class CustomerBrokerClosePurchaseNegotiationTransaction {

    /*Represent the Negotiation Purchase*/
    private CustomerBrokerPurchaseNegotiationManager                customerBrokerPurchaseNegotiationManager;

    /*Represent the Transaction database DAO */
    private CustomerBrokerCloseNegotiationTransactionDatabaseDao    customerBrokerCloseNegotiationTransactionDatabaseDao;

    private CryptoPaymentManager                                    cryptoPaymentManager;

    public CustomerBrokerClosePurchaseNegotiationTransaction(
            CustomerBrokerPurchaseNegotiationManager                customerBrokerPurchaseNegotiationManager,
            CustomerBrokerCloseNegotiationTransactionDatabaseDao    customerBrokerCloseNegotiationTransactionDatabaseDao
    ){
        this.customerBrokerPurchaseNegotiationManager               = customerBrokerPurchaseNegotiationManager;
        this.customerBrokerCloseNegotiationTransactionDatabaseDao   = customerBrokerCloseNegotiationTransactionDatabaseDao;
    }

    //PROCESS THE UPDATE PURCHASE NEGOTIATION TRANSACTION
    public void sendPurchaseNegotiationTranasction(CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation) throws CantClosePurchaseNegotiationTransactionException {

        try {

            UUID transactionId = UUID.randomUUID();

            CustomerBrokerPurchaseNegotiation purchaseNegotiation;

            //ADD CRYPTO ADREESS OF THE CUSTOMER AT THE CLAUSES
            purchaseNegotiation = getNegotiationAddCryptoAdreess(customerBrokerPurchaseNegotiation);

            //SAVE CRYPTO ADREESS OF THE CUSTOMER
            this.customerBrokerPurchaseNegotiationManager.updateCustomerBrokerPurchaseNegotiation(purchaseNegotiation);

            //CLOSE NEGOTIATION
            this.customerBrokerPurchaseNegotiationManager.closeNegotiation(purchaseNegotiation);

            //CREATE NEGOTIATION TRANSATION
            this.customerBrokerCloseNegotiationTransactionDatabaseDao.createCustomerBrokerCloseNegotiationTransaction(
                    transactionId,
                    customerBrokerPurchaseNegotiation,
                    NegotiationType.PURCHASE,
                    NegotiationTransactionStatus.PENDING_SUBMIT
            );

        } catch (CantUpdateCustomerBrokerPurchaseNegotiationException e) {
            throw new CantClosePurchaseNegotiationTransactionException(e.getMessage(),e, CantClosePurchaseNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR CREATE CUSTOMER BROKER PURCHASE NEGOTIATION, UNKNOWN FAILURE.");
        } catch (CantRegisterCustomerBrokerCloseNegotiationTransactionException e) {
            throw new CantClosePurchaseNegotiationTransactionException(e.getMessage(),e, CantClosePurchaseNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR REGISTER CUSTOMER BROKER PURCHASE NEGOTIATION TRANSACTION, UNKNOWN FAILURE.");
        } catch (Exception e){
            throw new CantClosePurchaseNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), CantClosePurchaseNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR PROCESS CUSTOMER BROKER PURCHASE NEGOTIATION, UNKNOWN FAILURE.");
        }

    }

    //PROCESS THE UPDATE PURCHASE NEGOTIATION TRANSACTION
    public void receivePurchaseNegotiationTranasction(UUID transactionId, CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation) throws CantClosePurchaseNegotiationTransactionException {

        try {


//            saveCryptoAdreess(customerBrokerPurchaseNegotiation);

            //CLOSE NEGOTIATION
            this.customerBrokerPurchaseNegotiationManager.closeNegotiation(customerBrokerPurchaseNegotiation);

            //CREATE NEGOTIATION TRANSATION
            this.customerBrokerCloseNegotiationTransactionDatabaseDao.createCustomerBrokerCloseNegotiationTransaction(
                    transactionId,
                    customerBrokerPurchaseNegotiation,
                    NegotiationType.PURCHASE,
                    NegotiationTransactionStatus.PENDING_SUBMIT_CONFIRM
            );

        } catch (CantUpdateCustomerBrokerPurchaseNegotiationException e) {
            throw new CantClosePurchaseNegotiationTransactionException(e.getMessage(),e, CantClosePurchaseNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR CREATE CUSTOMER BROKER PURCHASE NEGOTIATION, UNKNOWN FAILURE.");
        } catch (CantRegisterCustomerBrokerCloseNegotiationTransactionException e) {
            throw new CantClosePurchaseNegotiationTransactionException(e.getMessage(),e, CantClosePurchaseNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR REGISTER CUSTOMER BROKER PURCHASE NEGOTIATION TRANSACTION, UNKNOWN FAILURE.");
        } catch (Exception e){
            throw new CantClosePurchaseNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), CantClosePurchaseNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR PROCESS CUSTOMER BROKER PURCHASE NEGOTIATION, UNKNOWN FAILURE.");
        }

    }

    private CustomerBrokerPurchaseNegotiation getNegotiationAddCryptoAdreess(CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation) throws CantNegotiationAddCryptoAdreessException{

        CustomerBrokerPurchaseNegotiation purchaseNegotiation = customerBrokerPurchaseNegotiation;

        try {

            if (isCryptoCurrency(customerBrokerPurchaseNegotiation.getClauses())) {

                Collection<Clause> negotiationClauses;

                negotiationClauses = addCryptoAdreess(customerBrokerPurchaseNegotiation.getClauses());

                purchaseNegotiation = new CustomerBrokerPurchaseNegotiationImpl(
                        customerBrokerPurchaseNegotiation.getNegotiationId(),
                        customerBrokerPurchaseNegotiation.getCustomerPublicKey(),
                        customerBrokerPurchaseNegotiation.getBrokerPublicKey(),
                        customerBrokerPurchaseNegotiation.getStartDate(),
                        customerBrokerPurchaseNegotiation.getNegotiationExpirationDate(),
                        customerBrokerPurchaseNegotiation.getStatus(),
                        negotiationClauses
                );

            }

        } catch (CantGetListClauseException e){
            throw new CantNegotiationAddCryptoAdreessException(e.getMessage(),e, CantClosePurchaseNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR ADD CRYPTO ADREESS AN PURCHASE NEGOTIATION, UNKNOWN FAILURE.");
        } catch (Exception e){
            throw new CantNegotiationAddCryptoAdreessException(e.getMessage(), FermatException.wrapException(e), CantClosePurchaseNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR ADD CRYPTO ADREESS AN PURCHASE NEGOTIATION, UNKNOWN FAILURE.");
        }

        return purchaseNegotiation;

    }

    private Collection<Clause> addCryptoAdreess(Collection<Clause> negotiationClauses){

        Collection<Clause> negotiationClausesNew = new ArrayList<>();

        for(Clause clause : negotiationClauses){
            if(clause.getType() == ClauseType.CUSTOMER_CRYPTO_ADDRESS){
                negotiationClausesNew.add(
                    addClause(clause,cryptoAdreessActor(null,null,null,null))
                );
            }else{
                negotiationClausesNew.add(
                    addClause(clause,clause.getValue())
                );
            }
        }

        return negotiationClausesNew;

    }

    private Clause addClause(Clause clause, String value){

        Clause clauseNew = new CustomerBrokerNegotiationClauseImpl(
            clause.getClauseId(),
            clause.getType(),
            value,
            clause.getStatus(),
            clause.getProposedBy(),
            clause.getIndexOrder()
        );

        return clauseNew;

    }

    private String cryptoAdreessActor(final CryptoAddressesManager cryptoAddressesManager,
                                      final CryptoAddressBookManager cryptoAddressBookManager,
                                      final CryptoVaultSelector cryptoVaultSelector,
                                      final WalletManagerSelector walletManagerSelector) {

        CryptoAddress cryptoAdreess = null;

        String adreess = null;

        try {

            CryptoAddressRequest request = null;
            CustomerBrokerCloseCryptoAddress customerBrokerCloseCryptoAddress = new CustomerBrokerCloseCryptoAddress(
                    cryptoAddressesManager,
                    cryptoAddressBookManager,
                    cryptoVaultSelector,
                    walletManagerSelector
            );

            cryptoAdreess = customerBrokerCloseCryptoAddress.CryptoAddressesNew(request);

            adreess = cryptoAdreess.getAddress();

        } catch (CantCryptoAddressesNewException e){

        }

        return adreess;

    }

    private boolean isCryptoCurrency(Collection<Clause> negotiationClauses){

        for(Clause clause : negotiationClauses){
            if(clause.getType().equals(ClauseType.CUSTOMER_PAYMENT_METHOD)){
                if (clause.getValue().equals(CurrencyType.CRYPTO_MONEY)){
                    return true;
                }
            }
        }

        return false;

    }

}