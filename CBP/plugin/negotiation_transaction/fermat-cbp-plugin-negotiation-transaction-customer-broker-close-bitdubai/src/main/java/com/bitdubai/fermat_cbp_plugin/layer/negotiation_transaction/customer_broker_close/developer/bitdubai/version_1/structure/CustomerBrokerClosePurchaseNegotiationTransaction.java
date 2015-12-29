package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.bitcoin_vault.CryptoVaultManager;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationType;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantUpdateCustomerBrokerPurchaseNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiationManager;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.database.CustomerBrokerCloseNegotiationTransactionDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.exceptions.CantClosePurchaseNegotiationTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.exceptions.CantRegisterCustomerBrokerCloseNegotiationTransactionException;
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

    /*Represent Negotiation Crypto Address*/
    private CustomerBrokerCloseNegotiationCryptoAddress             negotiationCryptoAdreess;

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
            negotiationCryptoAdreess = new CustomerBrokerCloseNegotiationCryptoAddress(
                this.cryptoAddressBookManager,
                this.cryptoVaultManager,
                this.walletManagerManager
            );
            purchaseNegotiation = negotiationCryptoAdreess.getNegotiationAddCryptoAdreess(customerBrokerPurchaseNegotiation);

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


            CustomerBrokerPurchaseNegotiation purchaseNegotiation;

            //ADD CRYPTO ADREESS OF THE CUSTOMER AT THE CLAUSES
            negotiationCryptoAdreess = new CustomerBrokerCloseNegotiationCryptoAddress(
                this.cryptoAddressBookManager,
                this.cryptoVaultManager,
                this.walletManagerManager
            );
            purchaseNegotiation = negotiationCryptoAdreess.getNegotiationAddCryptoAdreess(customerBrokerPurchaseNegotiation);

            //SAVE CRYPTO ADREESS OF THE CUSTOMER
            this.customerBrokerPurchaseNegotiationManager.updateCustomerBrokerPurchaseNegotiation(purchaseNegotiation);

            //CLOSE NEGOTIATION
            this.customerBrokerPurchaseNegotiationManager.closeNegotiation(purchaseNegotiation);

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

    public void receiveCryptoAddress(){

    }
}