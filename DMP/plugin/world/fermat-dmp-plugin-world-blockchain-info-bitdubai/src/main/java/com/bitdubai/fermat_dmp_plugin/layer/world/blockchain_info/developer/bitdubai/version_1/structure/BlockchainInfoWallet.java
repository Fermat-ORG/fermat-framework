package com.bitdubai.fermat_ccp_plugin.layer.world.blockchain_info.developer.bitdubai.version_1.structure;


import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.layer.ccp_world.Agent;
import com.bitdubai.fermat_api.layer.ccp_world.CryptoWallet;
import com.bitdubai.fermat_api.layer.ccp_world.DealsWithWalletIdentity;
import com.bitdubai.fermat_api.layer.ccp_world.wallet.exceptions.CantStartWallet;
import com.bitdubai.fermat_ccp_plugin.layer.world.blockchain_info.developer.bitdubai.version_1.interfaces.DealsWithBlockchainInfoApi;
import com.bitdubai.fermat_api.layer.ccp_world.wallet.exceptions.CantGetAddressBalanceException;
import com.bitdubai.fermat_api.layer.ccp_world.wallet.exceptions.CantGetAddressesException;
import com.bitdubai.fermat_api.layer.ccp_world.wallet.exceptions.CantGetNewAddressException;
import com.bitdubai.fermat_api.layer.ccp_world.wallet.exceptions.CantGetWalletBalanceException;
import com.bitdubai.fermat_api.layer.ccp_world.wallet.exceptions.CantSendCryptoException;
import com.bitdubai.fermat_api.layer.ccp_world.wallet.exceptions.CantStartAgentException;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;

import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;

import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_ccp_plugin.layer.world.blockchain_info.developer.bitdubai.version_1.structure.api_v_1.APIException;
import com.bitdubai.fermat_ccp_plugin.layer.world.blockchain_info.developer.bitdubai.version_1.structure.api_v_1.wallet.Address;
import com.bitdubai.fermat_ccp_plugin.layer.world.blockchain_info.developer.bitdubai.version_1.structure.api_v_1.wallet.Wallet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by ciencias on 3/19/15.
 * Reviewed by leon on 4/27/15
 */
public class BlockchainInfoWallet implements CryptoWallet, DealsWithBlockchainInfoApi, DealsWithErrors, DealsWithEvents, DealsWithPluginDatabaseSystem, DealsWithPluginFileSystem, DealsWithPluginIdentity, DealsWithWalletIdentity {


    /**
     * DealsWithBlockchainInfoApi Interface member variables.
     */
    private String apiKey = "";

    /**
     * DealsWithError Interface member variables.
     */
    ErrorManager errorManager;

    /**
     * DealWithEvents Interface member variables.
     */
    EventManager eventManager;

    /**
     * DealsWithPluginDatabaseSystem Interface member variable
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithPluginFileSystem Interface member variable
     */
    PluginFileSystem pluginFileSystem;

    /**
     * DealsWithPluginIdentity Interface member variable
     */
    private UUID pluginId;

    /**
     * DealsWithWalletIdentity Interface member variable
     */
    private UUID walletId;

    /**
     * CryptoWallet Interface member variables.
     */

    private Wallet refWallet;

    Agent monitor;
    Agent catchUpAgent;
    Agent cryptoAnnouncerAgent;
    Agent transactionAgeingMonitorAgent;

    /**
     * Constructor.
     */

    public BlockchainInfoWallet(String apiKey, ErrorManager errorManager, EventManager eventManager, UUID pluginId,UUID walletId){
        this.apiKey = apiKey;
        this.errorManager = errorManager;
        this.eventManager = eventManager;
        this.pluginId = pluginId;
        this.walletId = walletId;
    }

    @Override
    public void start() throws CantStartWallet {

        /**
         * I will try to fill wallet's info..
         * walletGuid - password , reference of wallet in blockchaininfo
         */

        try{
            //get wallet property file: guid and password
            PluginTextFile layoutFile = pluginFileSystem.getTextFile(pluginId, pluginId.toString(), this.walletId.toString(), FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);

            String[] walletData = layoutFile.getContent().split(";");

            if(walletData.length >= 3){
                String walletGuid = walletData[0];
                String password = walletData[3];
                this.refWallet = getReferenceWallet(password, walletGuid);
            } else {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BLOCKCHAIN_INFO_WORLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, new Exception("Archivo incorrecto o mal confeccionado."));
                throw new CantStartWallet();
            }
        } catch (FileNotFoundException fileNotFoundException) {
            /**
             * The file not exists or cannot be opened  . I can not handle this situation.
             */
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BLOCKCHAIN_INFO_WORLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, fileNotFoundException);
            throw new CantStartWallet();
        }  catch (CantCreateFileException cantCreateFileException) {
            /**
             * This really should never happen. But if it does...
             */
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BLOCKCHAIN_INFO_WORLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateFileException);
            throw new CantStartWallet();
        }

        /**
         * I will start the Monitor Agent.
         */
        this.monitor = new BlockchainInfoIncomingCryptoMonitorAgent(this.errorManager, this.pluginDatabaseSystem, this.pluginId, this.walletId);

        try {
            this.monitor.start();
        } catch (CantStartAgentException cantStartAgentException) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BLOCKCHAIN_INFO_WORLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantStartAgentException);
            throw new CantStartWallet();
        }

        /**
         * I will start the CatchUp Agent.
         */
        this.catchUpAgent = new BlockchainInfoIncomingCryptoCatchUpAgent(this.apiKey, this.errorManager, this.pluginDatabaseSystem, this.pluginId, this.refWallet, this.walletId);

        try {
            this.catchUpAgent.start();
        } catch (CantStartAgentException cantStartAgentException) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BLOCKCHAIN_INFO_WORLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantStartAgentException);
            throw new CantStartWallet();
        }

        /**
         * I will start the CryptoAnnouncer Agent.
         */
        this.cryptoAnnouncerAgent = new BlockchainInfoIncomingCryptoAnnouncerAgent(this.errorManager, this.pluginDatabaseSystem, this.pluginId, this.walletId);

        try {
            this.cryptoAnnouncerAgent.start();
        } catch (CantStartAgentException cantStartAgentException) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BLOCKCHAIN_INFO_WORLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantStartAgentException);
            throw new CantStartWallet();
        }

        /**
         * I will start the TransactionAgeingMonitor Agent.
         */
        this.transactionAgeingMonitorAgent = new BlockchainInfoTransactionAgeingMonitorAgent(this.apiKey, this.errorManager, this.pluginDatabaseSystem, this.pluginId, this.walletId);

        try {
            this.transactionAgeingMonitorAgent.start();
        } catch (CantStartAgentException cantStartAgentException) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BLOCKCHAIN_INFO_WORLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantStartAgentException);
            throw new CantStartWallet();
        }
    }

    @Override
    public void stop() {
        monitor.stop();
        catchUpAgent.stop();
        cryptoAnnouncerAgent.stop();
    }


    public Wallet getReferenceWallet(String password, String walletGuid){
        Wallet wallet = new Wallet(walletGuid, password);
        wallet.setApiCode(this.apiKey);
        return wallet;
    }

    public void setReferenceWallet(Wallet wallet){
        this.refWallet = wallet;
    }


    /**
     * CryptoWallet Interface implementation.
     */

    @Override
    public long getWalletBalance(CryptoCurrency cryptoCurrency) throws CantGetWalletBalanceException{
        long balance;
        try{
            balance = refWallet.getBalance();
        } catch (IOException|APIException e) {
            throw new CantGetWalletBalanceException();
        }
        return balance;
    }

    @Override
    public long getAddressBalance(CryptoAddress cryptoAddress) throws  CantGetAddressBalanceException{

        long addressBalance;
        try{
            // if cryptoAddress is null, it takes the first address in wallet that find
            String address;
            if (cryptoAddress != null){
                address = cryptoAddress.getAddress();
            } else {
                List<Address> walletAddresses = refWallet.listAddresses(0);
                Address firstAddress = walletAddresses.get(0);
                address = firstAddress.getAddress();
            }
            // get an address from your wallet and include only transactions with up to 3
            // confirmations in the balance
            Address addr = refWallet.getAddress(address, 3);
            addressBalance =  addr.getBalance();

        } catch (IOException|APIException e)
        {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BLOCKCHAIN_INFO_WORLD, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetAddressBalanceException();
        }
        return addressBalance;
    }

    @Override
    public List<CryptoAddress> getAddresses() throws CantGetAddressesException{

        List<CryptoAddress> addresses = new ArrayList<>();
        try{
            // list all active addresses from your wallet
            List<Address> listAddresses = refWallet.listAddresses(0);
            for (int i = 0;  i < listAddresses.size(); i++){
                CryptoAddress addr = new CryptoAddress();
                addr.setAddress(listAddresses.get(i).getAddress());
                addr.setCryptoCurrency(CryptoCurrency.BITCOIN);
                addresses.add(addr);
            }

        } catch (IOException|APIException e)
        {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BLOCKCHAIN_INFO_WORLD, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetAddressesException();
        }
        return addresses;
    }

    @Override
    public CryptoAddress getNewAddress() throws CantGetNewAddressException{

        CryptoAddress address;
        try{
            // create an address for the wallet
            Address bcaddress = refWallet.newAddress(null);

            address = new CryptoAddress();
            address.setAddress(bcaddress.getAddress());
            address.setCryptoCurrency(CryptoCurrency.BITCOIN);

        } catch (IOException|APIException e)
        {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BLOCKCHAIN_INFO_WORLD, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetNewAddressException();
        }
        return address;
    }

    @Override
    public void sendCrypto (CryptoAddress cryptoAddressFrom, CryptoCurrency cryptoCurrency, long amount, CryptoAddress cryptoAddressTo) throws CantSendCryptoException {

        // send 0.2 bitcoins with a custom fee of 0.01 BTC and a note
        // public notes require a minimum transaction size of 0.005 BTC
        try{
            // if cryptoadddressfrom not exists use the primary address of the wallet
            String addressFrom = null;

            if (cryptoAddressFrom != null){
                addressFrom = cryptoAddressFrom.getAddress();
            }
            // PaymentResponse payment = que hago con esto?
            refWallet.send(cryptoAddressTo.getAddress(), amount, addressFrom,null, "");

        }
        catch (IOException|APIException e)
        {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BLOCKCHAIN_INFO_WORLD, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantSendCryptoException();
        }
    }

    /**
     * DealsWithBlockchainInfoApi Interface implementation.
     */
    @Override
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * DealWithErrors Interface implementation.
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /**
     * DealWithEvents Interface implementation.
     */
    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    /**
     * DealsWithPluginDatabaseSystem Interface implementation.
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * DealsWithPluginFileSystem Interface implementation.
     */
    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }

    /**
     * DealsWithPluginIdentity Interface implementation.
     */
    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    /**
     * DealsWithWalletIdentity Interface implementation.
     */
    @Override
    public void setWalletId(UUID walletId) {
        this.walletId = walletId;
    }
}
