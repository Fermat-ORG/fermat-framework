package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.bitcoin_vault.CryptoVaultManager;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationType;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantUpdateCustomerBrokerSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiationManager;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.database.CustomerBrokerCloseNegotiationTransactionDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.exceptions.CantCloseSaleNegotiationTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.exceptions.CantReceiveConfirmNegotiationTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.exceptions.CantRegisterCustomerBrokerCloseNegotiationTransactionException;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentityManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.WalletManagerManager;

import java.util.UUID;

/**
 * Created by Yordin Alayn on 22.12.15.
 */
public class CustomerBrokerCloseSaleNegotiationTransaction {

    /*Represent the Negotiation Sale*/
    private CustomerBrokerSaleNegotiationManager                    customerBrokerSaleNegotiationManager;

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

    /*Represent the Error Manager*/
    private ErrorManager                                            errorManager;

    /*Represent the Plugins Version*/
    private PluginVersionReference                                  pluginVersionReference;

    private IntraWalletUserIdentityManager intraWalletUserIdentityManager;

    public CustomerBrokerCloseSaleNegotiationTransaction(
            CustomerBrokerSaleNegotiationManager                    customerBrokerSaleNegotiationManager,
            CustomerBrokerCloseNegotiationTransactionDatabaseDao    customerBrokerCloseNegotiationTransactionDatabaseDao,
            CryptoAddressBookManager                                cryptoAddressBookManager,
            CryptoVaultManager                                      cryptoVaultManager,
            WalletManagerManager                                    walletManagerManager,
            ErrorManager                                            errorManager,
            PluginVersionReference                                  pluginVersionReference,
            IntraWalletUserIdentityManager intraWalletUserIdentityManager
    ){
            this.customerBrokerSaleNegotiationManager                   = customerBrokerSaleNegotiationManager;
            this.customerBrokerCloseNegotiationTransactionDatabaseDao   = customerBrokerCloseNegotiationTransactionDatabaseDao;
            this.cryptoAddressBookManager                               = cryptoAddressBookManager;
            this.cryptoVaultManager                                     = cryptoVaultManager;
            this.walletManagerManager                                   = walletManagerManager;
            this.errorManager                                           = errorManager;
            this.pluginVersionReference                                 = pluginVersionReference;
            this.intraWalletUserIdentityManager = intraWalletUserIdentityManager;
    }

    //PROCESS THE NEW SALE NEGOTIATION TRANSACTION
    public void sendSaleNegotiationTranasction(CustomerBrokerSaleNegotiation customerBrokerSaleNegotiation) throws CantCloseSaleNegotiationTransactionException{

        try {

            UUID transactionId = UUID.randomUUID();

            System.out.print("\n**** 3) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE - SALE NEGOTIATION - CUSTOMER BROKER CLOSE SALE NEGOTIATION TRANSACTION. transactionId: " + transactionId + " ****\n");

            System.out.print("\n --- Negotiation Mock XML Date" +
                            "\n- NegotiationId = " + customerBrokerSaleNegotiation.getNegotiationId() +
                            "\n- CustomerPublicKey = " + customerBrokerSaleNegotiation.getCustomerPublicKey() +
                            "\n- BrokerPublicKey = " + customerBrokerSaleNegotiation.getCustomerPublicKey() +
                            "\n- Status " + customerBrokerSaleNegotiation.getStatus()
            );
            String changeClause = "";
            for (final Clause value : customerBrokerSaleNegotiation.getClauses()) {
                changeClause = changeClause +"\n  - Type = "+value.getType()+". Value = "+value.getValue()+". Status = "+value.getStatus();
            }
            System.out.println(" - Clauses = \n" + changeClause);

            negotiationCryptoAdreess = new CustomerBrokerCloseNegotiationCryptoAddress(
                this.cryptoAddressBookManager,
                this.cryptoVaultManager,
                this.walletManagerManager,
                this.errorManager,
                this.pluginVersionReference,intraWalletUserIdentityManager
            );

            if (negotiationCryptoAdreess.isCryptoCurrency(customerBrokerSaleNegotiation.getClauses(),ClauseType.CUSTOMER_PAYMENT_METHOD)) {

                System.out.print("\n**** 3.1) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE - SALE NEGOTIATION - CUSTOMER BROKER CLOSE SALE NEGOTIATION TRANSACTION. IS CRYPTO CURRENCY ****\n");
                //ADD CRYPTO ADREESS OF THE CUSTOMER AT THE CLAUSES
                customerBrokerSaleNegotiation = negotiationCryptoAdreess.getNegotiationAddCryptoAdreess(customerBrokerSaleNegotiation);

                //SAVE CRYPTO ADREESS OF THE CUSTOMER
                this.customerBrokerSaleNegotiationManager.updateCustomerBrokerSaleNegotiation(customerBrokerSaleNegotiation);

            } else { System.out.print("\n**** 3.1) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE - SALE NEGOTIATION - CUSTOMER BROKER CLOSE SALE NEGOTIATION TRANSACTION. NOT IS CRYPTO CURRENCY ****\n"); }

            //CREATE NEGOTIATION TRANSATION
            customerBrokerCloseNegotiationTransactionDatabaseDao.createCustomerBrokerCloseNegotiationTransaction(
                transactionId,
                customerBrokerSaleNegotiation,
                NegotiationType.SALE,
                NegotiationTransactionStatus.PENDING_SUBMIT
            );

        } catch (CantUpdateCustomerBrokerSaleException e) {
            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
            throw new CantCloseSaleNegotiationTransactionException(e.getMessage(),e, CantCloseSaleNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR CREATE CUSTOMER BROKER SALE NEGOTIATION, UNKNOWN FAILURE.");
        } catch (CantRegisterCustomerBrokerCloseNegotiationTransactionException e) {
            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
            throw new CantCloseSaleNegotiationTransactionException(e.getMessage(),e, CantCloseSaleNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR REGISTER CUSTOMER BROKER SALE NEGOTIATION TRANSACTION, UNKNOWN FAILURE.");
        } catch (Exception e){
            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
            throw new CantCloseSaleNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), CantCloseSaleNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR PROCESS CUSTOMER BROKER SALE NEGOTIATION, UNKNOWN FAILURE.");
        }

    }

    public void receiveSaleNegotiationTranasction(UUID transactionId, CustomerBrokerSaleNegotiation customerBrokerSaleNegotiation)  throws CantCloseSaleNegotiationTransactionException{
        try {

            negotiationCryptoAdreess = new CustomerBrokerCloseNegotiationCryptoAddress(
                this.cryptoAddressBookManager,
                this.cryptoVaultManager,
                this.walletManagerManager,
                this.errorManager,
                this.pluginVersionReference,intraWalletUserIdentityManager
            );

            System.out.print("\n --- Negotiation Mock XML Date" +
                            "\n- NegotiationId = " + customerBrokerSaleNegotiation.getNegotiationId() +
                            "\n- CustomerPublicKey = " + customerBrokerSaleNegotiation.getCustomerPublicKey() +
                            "\n- BrokerPublicKey = " + customerBrokerSaleNegotiation.getCustomerPublicKey() +
                            "\n- Status " + customerBrokerSaleNegotiation.getStatus()
            );

            String changeClause = "";
            for (final Clause value : customerBrokerSaleNegotiation.getClauses()) {
                changeClause = changeClause +"\n  - Type = "+value.getType()+". Value = "+value.getValue()+". Status = "+value.getStatus();
            }
            System.out.println(" - Clauses = \n" + changeClause);

            if (negotiationCryptoAdreess.isCryptoCurrency(customerBrokerSaleNegotiation.getClauses(),ClauseType.CUSTOMER_PAYMENT_METHOD)) {

                //ADD CRYPTO ADREESS OF THE CUSTOMER AT THE CLAUSES
                customerBrokerSaleNegotiation = negotiationCryptoAdreess.getNegotiationAddCryptoAdreess(customerBrokerSaleNegotiation);

                //SAVE CRYPTO ADREESS OF THE CUSTOMER
                this.customerBrokerSaleNegotiationManager.updateCustomerBrokerSaleNegotiation(customerBrokerSaleNegotiation);

            }

            if (negotiationCryptoAdreess.isCryptoCurrency(customerBrokerSaleNegotiation.getClauses(),ClauseType.BROKER_PAYMENT_METHOD)) {
                //SAVE CRYPTO ADREESS OF THE CUSTOMER
                this.customerBrokerSaleNegotiationManager.updateCustomerBrokerSaleNegotiation(customerBrokerSaleNegotiation);
            }

            //CLOSE NEGOTIATION
            this.customerBrokerSaleNegotiationManager.closeNegotiation(customerBrokerSaleNegotiation);

            //CREATE NEGOTIATION TRANSATION
            this.customerBrokerCloseNegotiationTransactionDatabaseDao.createCustomerBrokerCloseNegotiationTransaction(
                transactionId,
                customerBrokerSaleNegotiation,
                NegotiationType.SALE,
                NegotiationTransactionStatus.PENDING_SUBMIT_CONFIRM
            );

        } catch (CantUpdateCustomerBrokerSaleException e) {
            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
            throw new CantCloseSaleNegotiationTransactionException(e.getMessage(),e, CantCloseSaleNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR CREATE CUSTOMER BROKER SALE NEGOTIATION, UNKNOWN FAILURE.");
        } catch (CantRegisterCustomerBrokerCloseNegotiationTransactionException e) {
            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
            throw new CantCloseSaleNegotiationTransactionException(e.getMessage(),e, CantCloseSaleNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR REGISTER CUSTOMER BROKER SALE NEGOTIATION TRANSACTION, UNKNOWN FAILURE.");
        } catch (Exception e){
            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
            throw new CantCloseSaleNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), CantCloseSaleNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR PROCESS CUSTOMER BROKER SALE NEGOTIATION, UNKNOWN FAILURE.");
        }
    }

    //UPDATE NEGOTIATION WITH CRYPTO ADDRESS OF THE CUSTOMER IF PAYMENT IS CRYPTO.
    public void receiveSaleConfirm(CustomerBrokerSaleNegotiation customerBrokerSaleNegotiation) throws CantReceiveConfirmNegotiationTransactionException {

        try {

            System.out.print("\n**** 28.1) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE - AGENT - RECEIVE CONFIRM PURCHASE  ****\n");
            negotiationCryptoAdreess = new CustomerBrokerCloseNegotiationCryptoAddress(
                this.cryptoAddressBookManager,
                this.cryptoVaultManager,
                this.walletManagerManager,
                this.errorManager,
                this.pluginVersionReference,intraWalletUserIdentityManager
            );

            String changeClause = "";
            for (final Clause value : customerBrokerSaleNegotiation.getClauses()) {
                changeClause = changeClause +"\n  - Type = "+value.getType()+". Value = "+value.getValue()+". Status = "+value.getStatus();
            }
            System.out.println(" - Clauses = \n" + changeClause);

            if(negotiationCryptoAdreess.isCryptoCurrency(customerBrokerSaleNegotiation.getClauses(), ClauseType.BROKER_PAYMENT_METHOD)) {

                //SAVE CRYPTO ADREESS OF THE CUSTOMER
                this.customerBrokerSaleNegotiationManager.updateCustomerBrokerSaleNegotiation(customerBrokerSaleNegotiation);

            }

            System.out.print("\n\n**** 29) MOCK NEGOTIATION TRANSACTION - CUSTOMER BROKER CLOSE - SALE NEGOTIATION - CUSTOMER BROKER CLOSE PURCHASE NEGOTIATION ****\n");

            //CLOSE NEGOTIATION
            this.customerBrokerSaleNegotiationManager.closeNegotiation(customerBrokerSaleNegotiation);

        } catch (CantUpdateCustomerBrokerSaleException e) {
            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
            throw new CantReceiveConfirmNegotiationTransactionException(e.getMessage(), e, CantReceiveConfirmNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR RECEIVE CUSTOMER BROKER SALE NEGOTIATION, UNKNOWN FAILURE.");
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
            throw new CantReceiveConfirmNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), CantReceiveConfirmNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR RECEIVE CUSTOMER BROKER SALE NEGOTIATION, UNKNOWN FAILURE.");
        }
    }
}
