package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.ObjectNotSetException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.broker_submit_online_merchandise.interfaces.BrokerSubmitOnlineMerchandiseManager;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantSubmitMerchandiseException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.ObjectChecker;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.exceptions.CantGetListCustomerBrokerContractSaleException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSale;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSaleManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.exceptions.CantGetListSaleNegotiationsException;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantGetListClauseException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetCryptoBrokerWalletSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CryptoBrokerWalletNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWallet;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletAssociatedSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletSetting;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.database.BrokerSubmitOnlineMerchandiseBusinessTransactionDao;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.exceptions.CantGetCryptoAddressException;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.exceptions.CantGetCryptoAmountException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 19/12/15.
 */
public class BrokerSubmitOnlineMerchandiseTransactionManager implements BrokerSubmitOnlineMerchandiseManager {

    /**
     * Represents the database Dao
     */
    BrokerSubmitOnlineMerchandiseBusinessTransactionDao brokerSubmitOnlineMerchandiseBusinessTransactionDao;

    /**
     * Represents the CustomerBrokerContractSaleManager
     */
    private CustomerBrokerContractSaleManager customerBrokerContractSaleManager;

    /**
     * Represents the CustomerBrokerSaleNegotiationManager
     */
    CustomerBrokerSaleNegotiationManager customerBrokerSaleNegotiationManager;

    /**
     * Represents the CryptoBrokerWalletManager
     */
    CryptoBrokerWalletManager cryptoBrokerWalletManager;

    ErrorManager errorManager;

    //TODO: included crypto broker wallet manager.
    public BrokerSubmitOnlineMerchandiseTransactionManager(
            BrokerSubmitOnlineMerchandiseBusinessTransactionDao brokerSubmitOnlineMerchandiseBusinessTransactionDao,
            CustomerBrokerContractSaleManager customerBrokerContractSaleManager,
            CustomerBrokerSaleNegotiationManager customerBrokerSaleNegotiationManager,
            CryptoBrokerWalletManager cryptoBrokerWalletManager,
            ErrorManager errorManager) {
        this.brokerSubmitOnlineMerchandiseBusinessTransactionDao = brokerSubmitOnlineMerchandiseBusinessTransactionDao;
        this.customerBrokerContractSaleManager = customerBrokerContractSaleManager;
        this.customerBrokerSaleNegotiationManager = customerBrokerSaleNegotiationManager;
        this.cryptoBrokerWalletManager = cryptoBrokerWalletManager;
        this.errorManager = errorManager;
    }

    /**
     * This method returns the crypto address from a CustomerBrokerPurchaseNegotiation
     * @param customerBrokerSaleNegotiation
     * @return
     * @throws CantGetCryptoAddressException
     */
    private String getBrokerCryptoAddressString(
            CustomerBrokerSaleNegotiation customerBrokerSaleNegotiation)
            throws CantGetCryptoAddressException {
        try{

            Collection<Clause> negotiationClauses=customerBrokerSaleNegotiation.getClauses();
            for(Clause clause : negotiationClauses){
                if(clause.getType().equals(ClauseType.CUSTOMER_CRYPTO_ADDRESS)){
                    return clause.getValue();
                }
            }
            throw new CantGetCryptoAddressException(
                    "The Negotiation clauses doesn't include the broker crypto address");
        } catch (CantGetListClauseException e) {
            throw new CantGetCryptoAddressException(
                    e,
                    "Getting the broker crypto address",
                    "Cannot get the clauses list");
        }

    }

    /**
     * This method returns a crypto amount (long) from a CustomerBrokerPurchaseNegotiation
     * @return
     */
    private long getAmount(
            CustomerBrokerSaleNegotiation customerBrokerSaleNegotiation) throws
            CantGetCryptoAmountException {
        try{
            long cryptoAmount;
            Collection<Clause> negotiationClauses=customerBrokerSaleNegotiation.getClauses();
            for(Clause clause : negotiationClauses){
                if(clause.getType().equals(ClauseType.BROKER_CURRENCY_QUANTITY)){
                    cryptoAmount=parseToLong(clause.getValue());
                    return cryptoAmount;
                }
            }
            throw new CantGetCryptoAmountException(
                    "The Negotiation clauses doesn't include the broker crypto amount");
        } catch (CantGetListClauseException e) {
            throw new CantGetCryptoAmountException(
                    e,
                    "Getting the broker crypto amount",
                    "Cannot get the clauses list");
        } catch (InvalidParameterException e) {
            throw new CantGetCryptoAmountException(
                    e,
                    "Getting the broker crypto amount",
                    "There is an error parsing a String to long.");
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
                        "Parsing String object to long",
                        "Cannot parse "+stringValue+" string value to long");
            }

        }
    }

    /**
     * This method returns a CustomerBrokerPurchaseNegotiation by negotiationId.
     * @param negotiationId
     * @return
     */
    private CustomerBrokerSaleNegotiation getCustomerBrokerSaleNegotiation(
            String negotiationId) throws
            CantGetListSaleNegotiationsException {
        UUID negotiationUUID=UUID.fromString(negotiationId);
        CustomerBrokerSaleNegotiation customerBrokerSaleNegotiation=
                customerBrokerSaleNegotiationManager.getNegotiationsByNegotiationId(
                        negotiationUUID);
        return customerBrokerSaleNegotiation;
    }

    /**
     * This method creates the Broker Submit Online Merchandise Business Transaction
     * @param cbpWalletPublicKey
     * @param walletPublicKey
     * @param contractHash
     * @throws CantSubmitMerchandiseException
     */
    @Override
    public void submitMerchandise(
            BigDecimal referencePrice,
            String cbpWalletPublicKey,
            String walletPublicKey,
            String contractHash)
            throws CantSubmitMerchandiseException {
        try {
            //Checking the arguments
            Object[] arguments={referencePrice, cbpWalletPublicKey, walletPublicKey, contractHash};
            ObjectChecker.checkArguments(arguments);
            CustomerBrokerContractSale customerBrokerContractSale=
                    this.customerBrokerContractSaleManager.getCustomerBrokerContractSaleForContractId(
                            contractHash);
            CustomerBrokerSaleNegotiation customerBrokerSaleNegotiation=
                    getCustomerBrokerSaleNegotiation(
                            customerBrokerContractSale.getNegotiatiotId());
            String customerCryptoAddress=getBrokerCryptoAddressString(
                    customerBrokerSaleNegotiation
            );
            long cryptoAmount= getAmount(customerBrokerSaleNegotiation);
            this.brokerSubmitOnlineMerchandiseBusinessTransactionDao.persistContractInDatabase(
                    customerBrokerContractSale,
                    customerCryptoAddress,
                    walletPublicKey,
                    cryptoAmount,
                    cbpWalletPublicKey,
                    referencePrice);
        } catch (CantGetListCustomerBrokerContractSaleException e) {
            throw new CantSubmitMerchandiseException(e,
                    "Creating Broker Submit Online Merchandise Business Transaction",
                    "Cannot get the CustomerBrokerContractSale");
        } catch (CantGetListSaleNegotiationsException e) {
            throw new CantSubmitMerchandiseException(e,
                    "Creating Broker Submit Online Merchandise Business Transaction",
                    "Cannot get the CustomerBrokerSaleNegotiation list");
        } catch (CantGetCryptoAmountException e) {
            throw new CantSubmitMerchandiseException(e,
                    "Creating Broker Submit Online Merchandise Business Transaction",
                    "Cannot get the Crypto amount");
        } catch (CantGetCryptoAddressException e) {
            throw new CantSubmitMerchandiseException(e,
                    "Creating Broker Submit Online Merchandise Business Transaction",
                    "Cannot get the Customer Crypto Address");
        } catch (CantInsertRecordException e) {
            throw new CantSubmitMerchandiseException(e,
                    "Creating Broker Submit Online Merchandise Business Transaction",
                    "Cannot insert a record in database");
        } catch (ObjectNotSetException e) {
            throw new CantSubmitMerchandiseException(e,
                    "Creating Broker Submit Online Merchandise Business Transaction",
                    "Invalid input to this manager");
        }
    }

    /**
     * This method send a payment according the contract clauses.
     * In this case, this method submit merchandise and not requires the cbpWalletPublicKey,
     * this public key can be obtained from the crypto broker wallet
     * @param referencePrice
     * @param cbpWalletPublicKey
     * @param contractHash
     * @throws CantSubmitMerchandiseException
     */
    @Override
    public void submitMerchandise(
            BigDecimal referencePrice,
            String cbpWalletPublicKey,
            String contractHash) throws CantSubmitMerchandiseException {
        try{
            //Checking the arguments
            Object[] arguments={referencePrice, cbpWalletPublicKey, contractHash};
            ObjectChecker.checkArguments(arguments);
            CryptoBrokerWallet cryptoBrokerWallet=cryptoBrokerWalletManager.loadCryptoBrokerWallet(
                    cbpWalletPublicKey);
            CryptoBrokerWalletSetting cryptoBrokerWalletSetting=
                    cryptoBrokerWallet.getCryptoWalletSetting();
            List<CryptoBrokerWalletAssociatedSetting> cryptoBrokerWalletAssociatedSettingList =
                    cryptoBrokerWalletSetting.getCryptoBrokerWalletAssociatedSettings();
            if(cryptoBrokerWalletAssociatedSettingList.isEmpty()){
                //Cannot handle this situation, throw an exception
                throw new CantSubmitMerchandiseException(
                        "Cannot get the crypto Wallet Associates Setting because the list is null");
            }
            boolean isCryptoWalletSets=false;
            String cryptoWalletPublicKey="WalletNotSet";
            for(CryptoBrokerWalletAssociatedSetting cryptoBrokerWalletAssociatedSetting :
                cryptoBrokerWalletAssociatedSettingList){
                CurrencyType currencyType=cryptoBrokerWalletAssociatedSetting.getCurrencyType();
                //System.out.println("Currency type: "+currencyType);
                switch (currencyType){
                    case CRYPTO_MONEY:
                        cryptoWalletPublicKey=cryptoBrokerWalletAssociatedSetting.getWalletPublicKey();
                        //System.out.println("Found wallet public key: "+cryptoWalletPublicKey);
                        isCryptoWalletSets=true;
                        break;
                    default:
                        continue;
                }
                if(isCryptoWalletSets){
                    break;
                }
            }
            if(!isCryptoWalletSets){
                /**
                 * In this case the crypto wallet is not set in Crypto Broker wallet, I cannot
                 * handle this situation.
                 */
                throw new CantSubmitMerchandiseException("There is not crypto wallet associated in " +
                        "Crypto Broker Wallet Settings");
            }
            //Overload the original method
            submitMerchandise(
                    referencePrice,
                    cbpWalletPublicKey,
                    cryptoWalletPublicKey,
                    contractHash);
        } catch (CryptoBrokerWalletNotFoundException e) {
            throw new CantSubmitMerchandiseException(e,
                    "Creating Broker Submit Online Merchandise Business Transaction",
                    "Cannot get the crypto broker wallet");
        } catch (CantGetCryptoBrokerWalletSettingException e) {
            throw new CantSubmitMerchandiseException(e,
                    "Creating Broker Submit Online Merchandise Business Transaction",
                    "Cannot get the Crypto Wallet Setting");
        } catch (ObjectNotSetException e) {
            throw new CantSubmitMerchandiseException(e,
                    "Creating Broker Submit Online Merchandise Business Transaction",
                    "Invalid input to this manager");
        }
    }

    /**
     * This method returns the actual transaction status
     * @param contractHash
     * @return
     * @throws UnexpectedResultReturnedFromDatabaseException
     */
    @Override
    public ContractTransactionStatus getContractTransactionStatus(String contractHash)
            throws UnexpectedResultReturnedFromDatabaseException {
        try{
            ObjectChecker.checkArgument(contractHash, "The contractHash argument is null");
            return this.brokerSubmitOnlineMerchandiseBusinessTransactionDao.getContractTransactionStatus(
                    contractHash);
        } catch (ObjectNotSetException e) {
            errorManager.reportUnexpectedPluginException(
                    Plugins.BROKER_SUBMIT_ONLINE_MERCHANDISE,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,
                    e);
            throw new UnexpectedResultReturnedFromDatabaseException(
                    "Cannot check a null contractHash/Id");
        }
    }
}
