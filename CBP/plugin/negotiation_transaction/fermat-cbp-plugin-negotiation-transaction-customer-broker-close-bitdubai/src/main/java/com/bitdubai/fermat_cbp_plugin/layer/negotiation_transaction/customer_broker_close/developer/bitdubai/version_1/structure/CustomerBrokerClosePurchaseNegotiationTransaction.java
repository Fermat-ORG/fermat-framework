package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.bitcoin_vault.CryptoVaultManager;
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
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.interfaces.CustomerBrokerCloseCryptoAddressRequest;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.utils.CryptoVaultSelector;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.utils.WalletManagerSelector;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.database.CustomerBrokerCloseNegotiationTransactionDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.exceptions.CantClosePurchaseNegotiationTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.exceptions.CantNegotiationAddCryptoAdreessException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.exceptions.CantRegisterCustomerBrokerCloseNegotiationTransactionException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces.CryptoAddressRequest;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.WalletManagerManager;

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

    /*Represent Address Book Manager*/
    private CryptoAddressBookManager                                cryptoAddressBookManager;

    /*Represent Vault Manager*/
    private CryptoVaultManager                                      cryptoVaultManager;

    /*Represent Wallet Manager*/
    private WalletManagerManager                                    walletManagerManager;

    public CustomerBrokerClosePurchaseNegotiationTransaction(
            CustomerBrokerPurchaseNegotiationManager                customerBrokerPurchaseNegotiationManager,
            CustomerBrokerCloseNegotiationTransactionDatabaseDao    customerBrokerCloseNegotiationTransactionDatabaseDao,
            CryptoAddressBookManager                                cryptoAddressBookManager,
            CryptoVaultManager                                      cryptoVaultManager,
            WalletManagerManager                                    walletManagerManager
    ){
        this.customerBrokerPurchaseNegotiationManager               = customerBrokerPurchaseNegotiationManager;
        this.customerBrokerCloseNegotiationTransactionDatabaseDao   = customerBrokerCloseNegotiationTransactionDatabaseDao;
        this.cryptoAddressBookManager                               = cryptoAddressBookManager;
        this.cryptoVaultManager                                     = cryptoVaultManager;
        this.walletManagerManager                                   = walletManagerManager;
    }

    //PROCESS THE UPDATE PURCHASE NEGOTIATION TRANSACTION
    public void sendPurchaseNegotiationTranasction(CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation) throws CantClosePurchaseNegotiationTransactionException {

        try {

            UUID transactionId = UUID.randomUUID();

            CustomerBrokerPurchaseNegotiation purchaseNegotiation;

            //ADD CRYPTO ADREESS OF THE CUSTOMER AT THE CLAUSES
            //
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

    //UPDATE THE NEGOTIATION FOR ADD NEW CRYPTO ADDRESS
    private CustomerBrokerPurchaseNegotiation getNegotiationAddCryptoAdreess(CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation) throws CantNegotiationAddCryptoAdreessException{

        CustomerBrokerPurchaseNegotiation purchaseNegotiation = customerBrokerPurchaseNegotiation;

        try {

            if (isCryptoCurrency(customerBrokerPurchaseNegotiation.getClauses())) {


                CustomerBrokerCloseCryptoAddressRequest request             = getRequest(purchaseNegotiation);
                Collection<Clause>                      negotiationClauses  = addCryptoAdreess(customerBrokerPurchaseNegotiation.getClauses(), request);

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

    //ADD NEW CRYPTO ADDRESS A THE CLAUSES
    private Collection<Clause> addCryptoAdreess(Collection<Clause> negotiationClauses, CustomerBrokerCloseCryptoAddressRequest request){

        Collection<Clause> negotiationClausesNew = new ArrayList<>();

        for(Clause clause : negotiationClauses){
            if(clause.getType() == ClauseType.CUSTOMER_CRYPTO_ADDRESS){
                negotiationClausesNew.add(
                    addClause(clause,cryptoAdreessActor(request))
                );
            }else{
                negotiationClausesNew.add(
                    addClause(clause,clause.getValue())
                );
            }
        }

        return negotiationClausesNew;

    }

    //ADD VALUES A THE CLAUSES
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

    //RETURN REQUEST THE CRYPTO ADDRESS
    private CustomerBrokerCloseCryptoAddressRequest getRequest(CustomerBrokerPurchaseNegotiation purchaseNegotiation){

        CustomerBrokerCloseCryptoAddressRequest request = new CustomerBrokerCloseCryptoAddressRequestImpl(
                Actors.CBP_CRYPTO_BROKER,
                Actors.CBP_CRYPTO_CUSTOMER,
                purchaseNegotiation.getBrokerPublicKey(),
                purchaseNegotiation.getCustomerPublicKey(),
                CryptoCurrency.BITCOIN,
                BlockchainNetworkType.TEST
        );

        return request;

    }

    //GENERATE AND REGISTER THE NEW CRYPTO ADDRESS OF THE ACTOR
    private String cryptoAdreessActor(CustomerBrokerCloseCryptoAddressRequest request) {

        CryptoVaultSelector     cryptoVaultSelector     = new CryptoVaultSelector(this.cryptoVaultManager);
        WalletManagerSelector   walletManagerSelector   = new WalletManagerSelector(this.walletManagerManager);
        String                  adreess                 = null;
        CryptoAddress           cryptoAdreess;

        try {

            CustomerBrokerCloseCryptoAddress customerBrokerCloseCryptoAddress = new CustomerBrokerCloseCryptoAddress(
                    this.cryptoAddressBookManager,
                    cryptoVaultSelector,
                    walletManagerSelector
            );

            cryptoAdreess = customerBrokerCloseCryptoAddress.CryptoAddressesNew(request);

            adreess = cryptoAdreess.getAddress();

        } catch (CantCryptoAddressesNewException e){

        }

        return adreess;

    }

    //CHECK IF IS CRYPTO CURRENCY
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