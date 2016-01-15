package com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.bitcoin_vault.CryptoVaultManager;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.exceptions.CantCreateCustomerBrokerContractPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.exceptions.CantCreateCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.exceptions.CantCreateCustomerBrokerPurchaseNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantCreateCustomerBrokerSaleNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.exceptions.CantGetCustomerBrokerCloseNegotiationTransactionException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.exceptions.CantGetListCustomerBrokerCloseNegotiationTransactionException;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.interfaces.CustomerBrokerClose;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.interfaces.CustomerBrokerCloseManager;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.database.CustomerBrokerCloseNegotiationTransactionDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.exceptions.CantClosePurchaseNegotiationTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.exceptions.CantCloseSaleNegotiationTransactionException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.exceptions.CantRegisterCustomerBrokerCloseNegotiationTransactionException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.WalletManagerManager;

import java.util.List;
import java.util.UUID;

/**
 * Created by Yordin Alayn on 22.12.15.
 */
public class CustomerBrokerCloseManagerImpl implements CustomerBrokerCloseManager {

    /*Represent the Transaction database DAO */
    private CustomerBrokerCloseNegotiationTransactionDatabaseDao    customerBrokerCloseNegotiationTransactionDatabaseDao;

    /*Represent the Transaction Negotiation Purchase*/
    private CustomerBrokerClosePurchaseNegotiationTransaction       customerBrokerClosePurchaseNegotiationTransaction;

    /*Represent the Transaction Negotiation Sale*/
    private CustomerBrokerCloseSaleNegotiationTransaction           customerBrokerCloseSaleNegotiationTransaction;

    /*Represent the Negotiation Purchase*/
    private CustomerBrokerPurchaseNegotiationManager                customerBrokerPurchaseNegotiationManager;

    /*Represent the Negotiation Sale*/
    private CustomerBrokerSaleNegotiationManager                    customerBrokerSaleNegotiationManager;

    /*Represent Address Book Manager*/
    private CryptoAddressBookManager                                cryptoAddressBookManager;

    /*Represent Vault Manager*/
    private CryptoVaultManager                                      cryptoVaultManager;

    /*Represent Wallet Manager*/
    private WalletManagerManager                                    walletManagerManager;

    public CustomerBrokerCloseManagerImpl(
            CustomerBrokerCloseNegotiationTransactionDatabaseDao    customerBrokerCloseNegotiationTransactionDatabaseDao,
            CustomerBrokerPurchaseNegotiationManager                customerBrokerPurchaseNegotiationManager,
            CustomerBrokerSaleNegotiationManager                    customerBrokerSaleNegotiationManager,
            CryptoAddressBookManager                                cryptoAddressBookManager,
            CryptoVaultManager                                      cryptoVaultManager,
            WalletManagerManager                                    walletManagerManager
    ){
        this.customerBrokerCloseNegotiationTransactionDatabaseDao   = customerBrokerCloseNegotiationTransactionDatabaseDao;
        this.customerBrokerPurchaseNegotiationManager               = customerBrokerPurchaseNegotiationManager;
        this.customerBrokerSaleNegotiationManager                   = customerBrokerSaleNegotiationManager;
        this.cryptoAddressBookManager                               = cryptoAddressBookManager;
        this.cryptoVaultManager                                     = cryptoVaultManager;
        this.walletManagerManager                                   = walletManagerManager;
    }

    @Override
    public void createCustomerBrokerClosePurchaseNegotiationTranasction(CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation)
            throws CantCreateCustomerBrokerPurchaseNegotiationException {

        try {

            customerBrokerClosePurchaseNegotiationTransaction = new CustomerBrokerClosePurchaseNegotiationTransaction(
                    customerBrokerPurchaseNegotiationManager,
                    customerBrokerCloseNegotiationTransactionDatabaseDao,
                    cryptoAddressBookManager,
                    cryptoVaultManager,
                    walletManagerManager
            );
            customerBrokerClosePurchaseNegotiationTransaction.sendPurchaseNegotiationTranasction(customerBrokerPurchaseNegotiation);

        } catch (CantClosePurchaseNegotiationTransactionException e){
            throw new CantCreateCustomerBrokerPurchaseNegotiationException(e.getMessage(),e, CantCreateCustomerBrokerContractPurchaseException.DEFAULT_MESSAGE, "ERROR CREATE CUSTOMER BROKER CLOSE PURCHASE NEGOTIATION TRANSACTION, UNKNOWN FAILURE.");
        } catch (Exception e){
            throw new CantCreateCustomerBrokerPurchaseNegotiationException(e.getMessage(), FermatException.wrapException(e), CantCreateCustomerBrokerContractPurchaseException.DEFAULT_MESSAGE, "ERROR CREATE CUSTOMER BROKER CLOSE PURCHASE NEGOTIATION TRANSACTION, UNKNOWN FAILURE.");
        }

    }

    @Override
    public void createCustomerBrokerCloseSaleNegotiationTranasction(CustomerBrokerSaleNegotiation customerBrokerSaleNegotiation)
            throws CantCreateCustomerBrokerSaleNegotiationException {

        try {

            customerBrokerCloseSaleNegotiationTransaction = new CustomerBrokerCloseSaleNegotiationTransaction(
                    customerBrokerSaleNegotiationManager,
                    customerBrokerCloseNegotiationTransactionDatabaseDao,
                    cryptoAddressBookManager,
                    cryptoVaultManager,
                    walletManagerManager
            );
            customerBrokerCloseSaleNegotiationTransaction.sendSaleNegotiationTranasction(customerBrokerSaleNegotiation);

        } catch (CantCloseSaleNegotiationTransactionException e){
            throw new CantCreateCustomerBrokerSaleNegotiationException(e.getMessage(),e, CantCreateCustomerBrokerContractSaleException.DEFAULT_MESSAGE, "ERROR CREATE CUSTOMER BROKER CLOSE PURCHASE NEGOTIATION TRANSACTION, UNKNOWN FAILURE.");
        } catch (Exception e){
            throw new CantCreateCustomerBrokerSaleNegotiationException(e.getMessage(), FermatException.wrapException(e), CantCreateCustomerBrokerContractSaleException.DEFAULT_MESSAGE, "ERROR CREATE CUSTOMER BROKER CLOSE PURCHASE NEGOTIATION TRANSACTION, UNKNOWN FAILURE.");
        }

    }

    @Override
    public CustomerBrokerClose getCustomerBrokerCloseNegotiationTranasction(UUID transactionId)
            throws CantGetCustomerBrokerCloseNegotiationTransactionException{

        try {

            return customerBrokerCloseNegotiationTransactionDatabaseDao.getRegisterCustomerBrokerCloseNegotiationTranasction(transactionId);

        } catch (CantRegisterCustomerBrokerCloseNegotiationTransactionException e){
            throw new CantGetCustomerBrokerCloseNegotiationTransactionException(e.getMessage(), e, CantGetCustomerBrokerCloseNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR GET CUSTOMER BROKER CLOSE NEGOTIATION TRANSACTION, UNKNOWN FAILURE.");
        } catch (Exception e){
            throw new CantGetCustomerBrokerCloseNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), CantGetCustomerBrokerCloseNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR GET CUSTOMER BROKER CLOSE NEGOTIATION TRANSACTION, UNKNOWN FAILURE.");
        }

    }

    @Override
    public List<CustomerBrokerClose> getAllCustomerBrokerCloseNegotiationTranasction()
            throws CantGetListCustomerBrokerCloseNegotiationTransactionException{

        try{

           return customerBrokerCloseNegotiationTransactionDatabaseDao.getAllRegisterCustomerBrokerCloseNegotiationTranasction();

        } catch (CantRegisterCustomerBrokerCloseNegotiationTransactionException e){
            throw new CantGetListCustomerBrokerCloseNegotiationTransactionException(e.getMessage(), e, CantGetListCustomerBrokerCloseNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR GET LIST CUSTOMER BROKER CLOSE NEGOTIATION TRANSACTION, UNKNOWN FAILURE.");
        } catch (Exception e){
            throw new CantGetListCustomerBrokerCloseNegotiationTransactionException(e.getMessage(), FermatException.wrapException(e), CantGetListCustomerBrokerCloseNegotiationTransactionException.DEFAULT_MESSAGE, "ERROR GET LIST CUSTOMER BROKER CLOSE NEGOTIATION TRANSACTION, UNKNOWN FAILURE.");
        }

    }
}
