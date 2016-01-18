package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.bitcoin_vault.CryptoVaultManager;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationType;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantUpdateCustomerBrokerPurchaseNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiationManager;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.database.CustomerBrokerCloseNegotiationTransactionDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.exceptions.CantClosePurchaseNegotiationTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.exceptions.CantDetermineCryptoCurrencyException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.exceptions.CantReceiveConfirmNegotiationTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.exceptions.CantRegisterCustomerBrokerCloseNegotiationTransactionException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.WalletManagerManager;

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

            System.out.print("\n\n**** 3) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE - PURCHASE NEGOTIATION - CUSTOMER BROKER CLOSE PURCHASE NEGOTIATION TRANSACTION. transactionId: " + transactionId + " ****\n");

            System.out.print("\n\n --- Negotiation Mock XML Date" +
                            "\n- NegotiationId = " + customerBrokerPurchaseNegotiation.getNegotiationId() +
                            "\n- CustomerPublicKey = " + customerBrokerPurchaseNegotiation.getCustomerPublicKey() +
                            "\n- BrokerPublicKey = " + customerBrokerPurchaseNegotiation.getCustomerPublicKey()
            );

            negotiationCryptoAdreess = new CustomerBrokerCloseNegotiationCryptoAddress(
                    this.cryptoAddressBookManager,
                    this.cryptoVaultManager,
                    this.walletManagerManager
            );

            if (negotiationCryptoAdreess.isCryptoCurrency(customerBrokerPurchaseNegotiation.getClauses(),ClauseType.CUSTOMER_PAYMENT_METHOD)){
                System.out.print("\n\n**** 3.1) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE - PURCHASE NEGOTIATION - CUSTOMER BROKER CLOSE PURCHASE NEGOTIATION TRANSACTION. IS CRYPTO CURRENCY ****\n");
                //ADD CRYPTO ADREESS OF THE CUSTOMER AT THE CLAUSES
                customerBrokerPurchaseNegotiation = negotiationCryptoAdreess.getNegotiationAddCryptoAdreess(customerBrokerPurchaseNegotiation);

                //SAVE CRYPTO ADREESS OF THE CUSTOMER
                this.customerBrokerPurchaseNegotiationManager.updateCustomerBrokerPurchaseNegotiation(customerBrokerPurchaseNegotiation);
            } else { System.out.print("\n\n**** 3.1) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE - PURCHASE NEGOTIATION - CUSTOMER BROKER CLOSE PURCHASE NEGOTIATION TRANSACTION. NOT IS CRYPTO CURRENCY ****\n"); }

            //CLOSE NEGOTIATION
            this.customerBrokerPurchaseNegotiationManager.closeNegotiation(customerBrokerPurchaseNegotiation);

            //CREATE NEGOTIATION TRANSATION
            this.customerBrokerCloseNegotiationTransactionDatabaseDao.createCustomerBrokerCloseNegotiationTransaction(
                    transactionId,
                    customerBrokerPurchaseNegotiation,
                    NegotiationType.PURCHASE,
                    NegotiationTransactionStatus.PENDING_SUBMIT
            );

        } catch (CantDetermineCryptoCurrencyException e) {
            throw new CantClosePurchaseNegotiationTransactionException(e.getMessage(),e, CantClosePurchaseNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR CREATE CUSTOMER BROKER PURCHASE NEGOTIATION, UNKNOWN FAILURE.");
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

            negotiationCryptoAdreess = new CustomerBrokerCloseNegotiationCryptoAddress(
                this.cryptoAddressBookManager,
                this.cryptoVaultManager,
                this.walletManagerManager
            );

            if (negotiationCryptoAdreess.isCryptoCurrency(customerBrokerPurchaseNegotiation.getClauses(),ClauseType.CUSTOMER_PAYMENT_METHOD)){
                //ADD CRYPTO ADREESS OF THE CUSTOMER AT THE CLAUSES
                customerBrokerPurchaseNegotiation = negotiationCryptoAdreess.getNegotiationAddCryptoAdreess(customerBrokerPurchaseNegotiation);

                //SAVE CRYPTO ADREESS OF THE CUSTOMER
                this.customerBrokerPurchaseNegotiationManager.updateCustomerBrokerPurchaseNegotiation(customerBrokerPurchaseNegotiation);
            }

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

    //UPDATE NEGOTIATION WITH CRYPTO ADDRESS OF THE BROKER IF PAYMENT IS CRYPTO.
    public void receivePurchaseConfirm(CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation) throws CantReceiveConfirmNegotiationTransactionException {

        try {

            negotiationCryptoAdreess = new CustomerBrokerCloseNegotiationCryptoAddress(
                    this.cryptoAddressBookManager,
                    this.cryptoVaultManager,
                    this.walletManagerManager
            );

            if(negotiationCryptoAdreess.isCryptoCurrency(customerBrokerPurchaseNegotiation.getClauses(), ClauseType.BROKER_PAYMENT_METHOD)) {

                //SAVE CRYPTO ADREESS OF THE CUSTOMER
                this.customerBrokerPurchaseNegotiationManager.updateCustomerBrokerPurchaseNegotiation(customerBrokerPurchaseNegotiation);

            }

        } catch (CantUpdateCustomerBrokerPurchaseNegotiationException e) {
            throw new CantReceiveConfirmNegotiationTransactionException(e.getMessage(),e, CantReceiveConfirmNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR RECEIVE CRYPTO ADDRESS IN CUSTOMER BROKER PURCHASE NEGOTIATION, UNKNOWN FAILURE.");
        } catch (Exception e){
            throw new CantReceiveConfirmNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), CantReceiveConfirmNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR RECEIVE CRYPTO ADDRESS IN CUSTOMER BROKER PURCHASE NEGOTIATION, UNKNOWN FAILURE.");
        }
    }
}