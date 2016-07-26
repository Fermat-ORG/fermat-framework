package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.currency_vault.CryptoVaultManager;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationType;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantUpdateCustomerBrokerPurchaseNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiationManager;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.NegotiationTransactionCustomerBrokerClosePluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.database.CustomerBrokerCloseNegotiationTransactionDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.exceptions.CantClosePurchaseNegotiationTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.exceptions.CantDetermineCryptoCurrencyException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.exceptions.CantReceiveConfirmNegotiationTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.exceptions.CantRegisterCustomerBrokerCloseNegotiationTransactionException;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentityManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.WalletManagerManager;

import java.util.UUID;

/**
 * Created by Yordin Alayn on 22.12.15.
 */
public class CustomerBrokerClosePurchaseNegotiationTransaction {

    /*Represent the Negotiation Purchase*/
    private CustomerBrokerPurchaseNegotiationManager customerBrokerPurchaseNegotiationManager;

    /*Represent the Transaction database DAO */
    private CustomerBrokerCloseNegotiationTransactionDatabaseDao customerBrokerCloseNegotiationTransactionDatabaseDao;

    /*Represent Address Book Manager*/
    private CryptoAddressBookManager cryptoAddressBookManager;

    /*Represent Vault Manager*/
    private CryptoVaultManager cryptoVaultManager;

    /*Represent Wallet Manager*/
    private WalletManagerManager walletManagerManager;

    /*Represent Negotiation Crypto Address*/
    private CustomerBrokerCloseNegotiationCryptoAddress negotiationCryptoAdreess;

    /*Represent the NegotiationTransactionCustomerBrokerClosePluginRoot*/
    private NegotiationTransactionCustomerBrokerClosePluginRoot pluginRoot;

    private IntraWalletUserIdentityManager intraWalletUserIdentityManager;

    public CustomerBrokerClosePurchaseNegotiationTransaction(
            CustomerBrokerPurchaseNegotiationManager customerBrokerPurchaseNegotiationManager,
            CustomerBrokerCloseNegotiationTransactionDatabaseDao customerBrokerCloseNegotiationTransactionDatabaseDao,
            CryptoAddressBookManager cryptoAddressBookManager,
            CryptoVaultManager cryptoVaultManager,
            WalletManagerManager walletManagerManager,
            NegotiationTransactionCustomerBrokerClosePluginRoot pluginRoot,
            IntraWalletUserIdentityManager intraWalletUserIdentityManager
    ) {
        this.customerBrokerPurchaseNegotiationManager = customerBrokerPurchaseNegotiationManager;
        this.customerBrokerCloseNegotiationTransactionDatabaseDao = customerBrokerCloseNegotiationTransactionDatabaseDao;
        this.cryptoAddressBookManager = cryptoAddressBookManager;
        this.cryptoVaultManager = cryptoVaultManager;
        this.walletManagerManager = walletManagerManager;
        this.pluginRoot = pluginRoot;
        this.intraWalletUserIdentityManager = intraWalletUserIdentityManager;
    }

    //PROCESS THE UPDATE PURCHASE NEGOTIATION TRANSACTION
    public void sendPurchaseNegotiationTranasction(CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation) throws CantClosePurchaseNegotiationTransactionException {

        try {

            UUID transactionId = UUID.randomUUID();

            System.out.println(new StringBuilder().append("**** 3) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE - PURCHASE NEGOTIATION - CUSTOMER BROKER CLOSE PURCHASE NEGOTIATION TRANSACTION. transactionId: ").append(transactionId).append(" ****").toString());

            System.out.println(new StringBuilder()
                            .append(" --- Negotiation Mock XML Date")
                            .append("- NegotiationId = ").append(customerBrokerPurchaseNegotiation.getNegotiationId())
                            .append("- CustomerPublicKey = ").append(customerBrokerPurchaseNegotiation.getCustomerPublicKey())
                            .append("- BrokerPublicKey = ").append(customerBrokerPurchaseNegotiation.getCustomerPublicKey())
                            .append("- Status = ").append(customerBrokerPurchaseNegotiation.getStatus()).toString()
            );

            String changeClause = "";
            for (final Clause value : customerBrokerPurchaseNegotiation.getClauses()) {
                changeClause = new StringBuilder().append(changeClause).append("\n  - Type = ").append(value.getType()).append(". Value = ").append(value.getValue()).append(". Status = ").append(value.getStatus()).toString();
            }

            System.out.println(new StringBuilder().append(" - Clauses = \n").append(changeClause).toString());

            negotiationCryptoAdreess = new CustomerBrokerCloseNegotiationCryptoAddress(
                    cryptoAddressBookManager,
                    cryptoVaultManager,
                    walletManagerManager,
                    pluginRoot,
                    intraWalletUserIdentityManager
            );

            if (negotiationCryptoAdreess.isCryptoCurrency(customerBrokerPurchaseNegotiation.getClauses(), ClauseType.BROKER_PAYMENT_METHOD)) {

                //ADD CRYPTO ADREESS OF THE CUSTOMER AT THE CLAUSES
                System.out.println("**** 3.1) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE - PURCHASE NEGOTIATION - CUSTOMER BROKER CLOSE PURCHASE NEGOTIATION TRANSACTION. IS CRYPTO CURRENCY ****");
                customerBrokerPurchaseNegotiation = negotiationCryptoAdreess.getNegotiationAddCryptoAdreess(customerBrokerPurchaseNegotiation);

            } else {
                System.out.println("**** 3.1) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE - PURCHASE NEGOTIATION - CUSTOMER BROKER CLOSE PURCHASE NEGOTIATION TRANSACTION. NOT IS CRYPTO CURRENCY ****");
            }

            //UPDATE NEGOTIATION
            System.out.println("**** 3.2) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE - PURCHASE NEGOTIATION - CUSTOMER BROKER CLOSE PURCHASE NEGOTIATION TRANSACTION. UPDATE NEGOTIATION ****");
            this.customerBrokerPurchaseNegotiationManager.updateCustomerBrokerPurchaseNegotiation(customerBrokerPurchaseNegotiation);

            //CREATE NEGOTIATION TRANSATION
            System.out.println("**** 3.3) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE - PURCHASE NEGOTIATION - CUSTOMER BROKER CLOSE PURCHASE NEGOTIATION TRANSACTION. CLOSE NEGOTIATION ****");
            this.customerBrokerCloseNegotiationTransactionDatabaseDao.createCustomerBrokerCloseNegotiationTransaction(
                    transactionId,
                    customerBrokerPurchaseNegotiation,
                    NegotiationType.PURCHASE,
                    NegotiationTransactionStatus.PENDING_SUBMIT
            );

        } catch (CantDetermineCryptoCurrencyException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantClosePurchaseNegotiationTransactionException(e.getMessage(), e, CantClosePurchaseNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR REGISTER CUSTOMER BROKER PURCHASE NEGOTIATION TRANSACTION, IN CUSTOMER BROKER CLOSE, NEGOTIATION TRANSACTION. CANT DETERMINE CRYPTO CURRENCY");
        } catch (CantUpdateCustomerBrokerPurchaseNegotiationException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantClosePurchaseNegotiationTransactionException(e.getMessage(), e, CantClosePurchaseNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR REGISTER CUSTOMER BROKER PURCHASE NEGOTIATION TR*ANSACTION, IN CUSTOMER BROKER PURCHASE NEGOTIATION, UPDATE.");
        } catch (CantRegisterCustomerBrokerCloseNegotiationTransactionException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantClosePurchaseNegotiationTransactionException(e.getMessage(), e, CantClosePurchaseNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR REGISTER CUSTOMER BROKER PURCHASE NEGOTIATION TRANSACTION, IN CUSTOMER BROKER PURCHASE NEGOTIATION, CLOSE.");
        } catch (Exception e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantClosePurchaseNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), CantClosePurchaseNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR PROCESS CUSTOMER BROKER PURCHASE NEGOTIATION, UNKNOWN FAILURE.");
        }

    }

    //PROCESS THE UPDATE PURCHASE NEGOTIATION TRANSACTION
    public void receivePurchaseNegotiationTranasction(UUID transactionId, CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation) throws CantClosePurchaseNegotiationTransactionException {

        try {

            System.out.print(new StringBuilder().append("\n\n**** 21) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE - PURCHASE NEGOTIATION - CUSTOMER BROKER CLOSE PURCHASE NEGOTIATION TRANSACTION. transactionId: ").append(transactionId).append(" ****\n").toString());

            System.out.print(new StringBuilder()
                            .append("\n\n --- Negotiation Mock XML Date")
                            .append("\n- NegotiationId = ").append(customerBrokerPurchaseNegotiation.getNegotiationId())
                            .append("\n- CustomerPublicKey = ").append(customerBrokerPurchaseNegotiation.getCustomerPublicKey())
                            .append("\n- BrokerPublicKey = ").append(customerBrokerPurchaseNegotiation.getCustomerPublicKey())
                            .append("\n- Status = ").append(customerBrokerPurchaseNegotiation.getStatus()).toString()
            );

            String changeClause = "";
            for (final Clause value : customerBrokerPurchaseNegotiation.getClauses()) {
                changeClause = new StringBuilder().append(changeClause).append("\n  - Type = ").append(value.getType()).append(". Value = ").append(value.getValue()).append(". Status = ").append(value.getStatus()).toString();
            }
            System.out.println(new StringBuilder().append(" - Clauses = \n").append(changeClause).toString());

            negotiationCryptoAdreess = new CustomerBrokerCloseNegotiationCryptoAddress(
                    cryptoAddressBookManager,
                    cryptoVaultManager,
                    walletManagerManager,
                    pluginRoot,
                    intraWalletUserIdentityManager
            );

            if (negotiationCryptoAdreess.isCryptoCurrency(customerBrokerPurchaseNegotiation.getClauses(), ClauseType.BROKER_PAYMENT_METHOD)) {

                System.out.print("\n\n**** 21.1) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE - PURCHASE NEGOTIATION - CUSTOMER BROKER CLOSE PURCHASE NEGOTIATION TRANSACTION. IS CRYPTO CURRENCY ****\n");
                //ADD CRYPTO ADREESS OF THE CUSTOMER AT THE CLAUSES
                customerBrokerPurchaseNegotiation = negotiationCryptoAdreess.getNegotiationAddCryptoAdreess(customerBrokerPurchaseNegotiation);

                System.out.print("\n\n**** 21.2) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE - PURCHASE NEGOTIATION - CUSTOMER BROKER CLOSE PURCHASE NEGOTIATION TRANSACTION. UPDATE NEGOTIATION ****\n");
                //SAVE CRYPTO ADREESS OF THE CUSTOMER
                this.customerBrokerPurchaseNegotiationManager.updateCustomerBrokerPurchaseNegotiation(customerBrokerPurchaseNegotiation);

            }

            if (negotiationCryptoAdreess.isCryptoCurrency(customerBrokerPurchaseNegotiation.getClauses(), ClauseType.CUSTOMER_PAYMENT_METHOD)) {
                //SAVE CRYPTO ADREESS OF THE CUSTOMER
                this.customerBrokerPurchaseNegotiationManager.updateCustomerBrokerPurchaseNegotiation(customerBrokerPurchaseNegotiation);
            }

            System.out.print("\n\n**** 21.3) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE - PURCHASE NEGOTIATION - CUSTOMER BROKER CLOSE PURCHASE NEGOTIATION TRANSACTION. CLOSE NEGOTIATION ****\n");

            //CLOSE NEGOTIATION
            customerBrokerPurchaseNegotiationManager.waitForClosing(customerBrokerPurchaseNegotiation);

            //CREATE NEGOTIATION TRANSATION
            customerBrokerCloseNegotiationTransactionDatabaseDao.createCustomerBrokerCloseNegotiationTransaction(
                    transactionId,
                    customerBrokerPurchaseNegotiation,
                    NegotiationType.PURCHASE,
                    NegotiationTransactionStatus.PENDING_SUBMIT_CONFIRM
            );

        } catch (CantUpdateCustomerBrokerPurchaseNegotiationException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantClosePurchaseNegotiationTransactionException(e.getMessage(), e, CantClosePurchaseNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR CREATE CUSTOMER BROKER PURCHASE NEGOTIATION, UNKNOWN FAILURE.");
        } catch (CantRegisterCustomerBrokerCloseNegotiationTransactionException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantClosePurchaseNegotiationTransactionException(e.getMessage(), e, CantClosePurchaseNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR REGISTER CUSTOMER BROKER PURCHASE NEGOTIATION TRANSACTION, UNKNOWN FAILURE.");
        } catch (Exception e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantClosePurchaseNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), CantClosePurchaseNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR PROCESS CUSTOMER BROKER PURCHASE NEGOTIATION, UNKNOWN FAILURE.");
        }
    }

    //UPDATE NEGOTIATION WITH CRYPTO ADDRESS OF THE BROKER IF PAYMENT IS CRYPTO.
    public void receivePurchaseConfirm(CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation) throws CantReceiveConfirmNegotiationTransactionException {

        try {

            System.out.print("\n\n**** 28.1) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE - AGENT - RECEIVE CONFIRM PURCHASE  ****\n");
            negotiationCryptoAdreess = new CustomerBrokerCloseNegotiationCryptoAddress(
                    cryptoAddressBookManager,
                    cryptoVaultManager,
                    walletManagerManager,
                    pluginRoot,
                    intraWalletUserIdentityManager
            );

            String changeClause = "";
            for (final Clause value : customerBrokerPurchaseNegotiation.getClauses()) {
                changeClause = new StringBuilder().append(changeClause).append("\n  - Type = ").append(value.getType()).append(". Value = ").append(value.getValue()).append(". Status = ").append(value.getStatus()).toString();
            }
            System.out.println(new StringBuilder().append(" - Clauses = \n").append(changeClause).toString());

            for (Clause item : customerBrokerPurchaseNegotiation.getClauses()) {
                System.out.print(new StringBuilder().append("\n- ").append(item.getType()).append(" = ").append(item.getValue()).append("\n").toString());
            }

            System.out.print("\n\n**** 29) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE - PURCHASE NEGOTIATION - CUSTOMER BROKER CLOSE PURCHASE NEGOTIATION****\n");

            //UPDATE NEGOTIAITON
            this.customerBrokerPurchaseNegotiationManager.updateCustomerBrokerPurchaseNegotiation(customerBrokerPurchaseNegotiation);

            //CLOSE NEGOTIATION
            customerBrokerPurchaseNegotiationManager.waitForClosing(customerBrokerPurchaseNegotiation);

        } catch (CantUpdateCustomerBrokerPurchaseNegotiationException e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantReceiveConfirmNegotiationTransactionException(e.getMessage(), e, CantReceiveConfirmNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR RECEIVE CRYPTO ADDRESS IN CUSTOMER BROKER PURCHASE NEGOTIATION, UNKNOWN FAILURE.");
        } catch (Exception e) {
            pluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantReceiveConfirmNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), CantReceiveConfirmNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR RECEIVE CRYPTO ADDRESS IN CUSTOMER BROKER PURCHASE NEGOTIATION, UNKNOWN FAILURE.");
        }
    }
}