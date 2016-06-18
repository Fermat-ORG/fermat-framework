package org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.agent;

import com.bitdubai.fermat_api.Agent;
import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.enums.VaultType;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.exceptions.CantRegisterCryptoAddressBookRecordException;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookRecord;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.exceptions.CantGetActiveRedeemPointAddressesException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.exceptions.CantGetActiveRedeemPointsException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by rodrigo on 1/8/16.
 * Will calculate if there are any unregistered cryptoAddress in the address book for any redeem point,
 * and if found, register them. Once completed it will stop.
 */
public class RedeemerAddressesMonitorAgent implements Agent {
    /**
     * RedeemerAddressesMonitorAgent class member variables
     */
    private CryptoAddressBookManager cryptoAddressBookManager;
    private AssetVaultManager assetVaultManager;
    private AtomicBoolean running;
    private String issuerPublicKey;
    private MonitorAgent monitorAgent;

    /**
     * constructor
     *
     * @param cryptoAddressBookManager
     * @param assetVaultManager
     */
    public RedeemerAddressesMonitorAgent(CryptoAddressBookManager cryptoAddressBookManager, AssetVaultManager assetVaultManager, String issuerPublicKey) {
        this.cryptoAddressBookManager = cryptoAddressBookManager;
        this.assetVaultManager = assetVaultManager;
        this.issuerPublicKey = issuerPublicKey;
    }

    @Override
    public void start() throws CantStartAgentException {
        /**
         * I will get the list of Redeem Points in the Asset vault.
         */
        List<String> redeemers;
        try {
            redeemers = assetVaultManager.getActiveRedeemPoints();
        } catch (CantGetActiveRedeemPointsException e) {
            throw new CantStartAgentException(e, "Error getting active redeem points public keys", "Asset vault issue");
        }

        /**
         * I will get the list of CryptoAddress already registed in the address book to Redeem Points.
         */
        List<CryptoAddress> addressBookCryptoAddresses = new ArrayList<>();
        try {
            List<CryptoAddressBookRecord> cryptoAddressBookRecords = cryptoAddressBookManager.listCryptoAddressBookRecordsByDeliveredToActorType(Actors.DAP_ASSET_REDEEM_POINT);
            for (CryptoAddressBookRecord cryptoAddressBookRecord : cryptoAddressBookRecords) {
                addressBookCryptoAddresses.add(cryptoAddressBookRecord.getCryptoAddress());
            }
        } catch (CantRegisterCryptoAddressBookRecordException e) {
            throw new CantStartAgentException(e, "Error getting Addresses from Address book", "Crypto Address Book issue");
        }

        /**
         * instantiate the monitor agent and run it.
         */
        monitorAgent = new MonitorAgent(this.cryptoAddressBookManager, this.assetVaultManager, redeemers, addressBookCryptoAddresses, issuerPublicKey);
        Thread agent = new Thread(monitorAgent);
        agent.start();
    }

    @Override
    public void stop() {
        monitorAgent.keysGenerated.set(true);
    }


    /**
     * private class monitor Agent
     */
    private class MonitorAgent implements Runnable {
        private CryptoAddressBookManager cryptoAddressBookManager;
        private AssetVaultManager assetVaultManager;
        private List<String> redeemers; //the list of public Keys of redeemers configured in the asset vault
        private List<CryptoAddress> addressBookCryptoAddresses; //the list of cryptoAddresses stored in the address book registered to RedeemPoints
        private String issuerPublicKey;
        private AtomicBoolean keysGenerated;
        private final int SLEEP_TIME = 10000; //10 segundos


        /**
         * private class constructor
         *
         * @param cryptoAddressBookManager
         * @param assetVaultManager
         */
        public MonitorAgent(CryptoAddressBookManager cryptoAddressBookManager, AssetVaultManager assetVaultManager, List<String> redeemers, List<CryptoAddress> addressBookCryptoAddresses, String issuerPublicKey) {
            this.cryptoAddressBookManager = cryptoAddressBookManager;
            this.assetVaultManager = assetVaultManager;
            this.redeemers = redeemers;
            this.addressBookCryptoAddresses = addressBookCryptoAddresses;
            this.issuerPublicKey = issuerPublicKey;

            keysGenerated = new AtomicBoolean(true);

        }

        @Override
        public void run() {
            do {
                doTheMainTask();
                try {
                    Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (!keysGenerated.get());
        }

        /**
         * Main method
         */
        private void doTheMainTask() {
            /**
             * for each redeem Point that has keys in the asset vault I get all the generated addresses.
             **/
            for (String redeemPointPublicKey : redeemers) {
                try {
                    List<CryptoAddress> redeemPointCryptoAddresses;
                    redeemPointCryptoAddresses = assetVaultManager.getActiveRedeemPointAddresses(redeemPointPublicKey);

                    /**
                     * if I didn't get any keys, then I'll mark keysGenerated as false.
                     */
                    if (redeemPointCryptoAddresses.size() == 0)
                        this.keysGenerated.set(false);

                    /**
                     * I remove all the keys that are already registered.
                     */
                    redeemPointCryptoAddresses.removeAll(addressBookCryptoAddresses);

                    /**
                     * I will register the new keys
                     */
                    for (CryptoAddress cryptoAddress : redeemPointCryptoAddresses) {
                        registerAddressInCryptoBook(cryptoAddress, redeemPointPublicKey);
                    }
                } catch (CantGetActiveRedeemPointAddressesException e) {
                    e.printStackTrace();
                } catch (CantRegisterCryptoAddressBookRecordException e) {
                    e.printStackTrace();
                }

            }
        }

        /**
         * registers the given key in the address book.
         *
         * @param cryptoAddress
         * @param redeempointPublicKey
         * @throws CantRegisterCryptoAddressBookRecordException
         */
        private void registerAddressInCryptoBook(CryptoAddress cryptoAddress, String redeempointPublicKey) throws CantRegisterCryptoAddressBookRecordException {
            cryptoAddressBookManager.registerCryptoAddress(cryptoAddress, issuerPublicKey, Actors.DAP_ASSET_ISSUER, redeempointPublicKey, Actors.DAP_ASSET_REDEEM_POINT, Platforms.DIGITAL_ASSET_PLATFORM, VaultType.WATCH_ONLY_VAULT, "WatchOnlyVault", "", ReferenceWallet.BASIC_WALLET_BITCOIN_WALLET);
            this.keysGenerated.set(true);
        }
    }
}
