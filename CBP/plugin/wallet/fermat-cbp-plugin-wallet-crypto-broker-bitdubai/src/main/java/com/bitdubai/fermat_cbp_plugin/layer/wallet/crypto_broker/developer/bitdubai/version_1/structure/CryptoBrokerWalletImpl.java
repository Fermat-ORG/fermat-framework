package com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_cbp_api.all_definition.wallet.StockBalance;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantCreateCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetCryptoBrokerWalletSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetStockCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CryptoBrokerWalletNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWallet;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWalletSetting;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.database.CryptoBrokerWalletDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.exceptions.CantCreateNewCryptoBrokerWalletException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

/**
 * Created by jorge on 26-10-2015.
 * Modified by Yordin Alayn 27.10.15
 * Modified by Franklin Marcano 30.11.2015
 */
public class CryptoBrokerWalletImpl implements CryptoBrokerWallet{
    public static final String PATH_DIRECTORY = "cryptobrokerwallet-swap/";
    private static final String CRYPTO_BROKER_WALLET_FILE_NAME = "walletsIds";
    private Database database;
    private Map<String, UUID> wallet= new HashMap<>();
    private UUID pluginId;
    private ErrorManager errorManager;
    private PluginDatabaseSystem pluginDatabaseSystem;
    private PluginFileSystem pluginFileSystem;

    public CryptoBrokerWalletImpl(ErrorManager errorManager, PluginDatabaseSystem pluginDatabaseSystem, PluginFileSystem pluginFileSystem, UUID pluginId) {
        this.errorManager = errorManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
    }

    @Override
    public StockBalance getStockBalance() throws CantGetStockCryptoBrokerWalletException {
        return new StockBalanceImpl(database,pluginId, pluginFileSystem);
    }

    @Override
    public CryptoBrokerWalletSetting getCryptoWalletSetting() throws CantGetCryptoBrokerWalletSettingException {
        return null;
    }

    public void initialize(UUID walletId) throws CryptoBrokerWalletNotFoundException {
        if (walletId == null)
            throw new CryptoBrokerWalletNotFoundException("InternalId is null", null, "Parameter walletId is null", "loadWallet didn't find the asociated id");

        try {
            database = this.pluginDatabaseSystem.openDatabase(this.pluginId, walletId.toString());
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            throw new CryptoBrokerWalletNotFoundException("I can't open database", cantOpenDatabaseException, "WalletId: " + walletId.toString(), "");
        } catch (DatabaseNotFoundException databaseNotFoundException) {
            throw new CryptoBrokerWalletNotFoundException("Database does not exists", databaseNotFoundException, "WalletId: " + walletId.toString(), "");
        } catch (Exception exception) {
            throw new CryptoBrokerWalletNotFoundException(CryptoBrokerWalletNotFoundException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    public UUID create(String walletId) throws CantCreateCryptoBrokerWalletException {
        try {
            // TODO: Until the Wallet MAnager create the wallets, we will use this internal id
            //       We need to change this in the near future
            UUID internalWalletId = UUID.randomUUID();
            createWalletDatabase(internalWalletId);
            PluginTextFile walletFile = createWalletFile();
            loadWalletMap(walletFile);
            wallet.put(walletId, internalWalletId);
            persistWallet(walletFile);
            return internalWalletId;
        } catch (CantCreateCryptoBrokerWalletException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CantCreateCryptoBrokerWalletException(CantCreateCryptoBrokerWalletException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    private PluginTextFile  createWalletFile() throws CantCreateCryptoBrokerWalletException
    {
        try {
            return pluginFileSystem.getTextFile(pluginId, "", CRYPTO_BROKER_WALLET_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
        } catch (CantCreateFileException cantCreateFileException) {
            throw new CantCreateCryptoBrokerWalletException("File could not be created (?)", cantCreateFileException, "File Name: " + CRYPTO_BROKER_WALLET_FILE_NAME, "");
        } catch (FileNotFoundException e) {
            throw new CantCreateCryptoBrokerWalletException("File could not be found", e, "File Name: " + CRYPTO_BROKER_WALLET_FILE_NAME, "");
        }
    }

    private void loadWalletMap(final PluginTextFile loadWalletMap) throws CantCreateNewCryptoBrokerWalletException
    {
        try {
            loadWalletMap.loadFromMedia();
            String[] stringWallet = loadWalletMap.getContent().split(";", -1);

            for (String stringWalletId : stringWallet) {

                if (!stringWalletId.equals("")) {
                    String[] idPair = stringWalletId.split(",", -1);
                    wallet.put(idPair[0], UUID.fromString(idPair[1]));
                }
            }
        } catch (CantLoadFileException exception) {
            throw new CantCreateNewCryptoBrokerWalletException("Can't load file content from media", exception, "", "");
        }
    }

    private void createWalletDatabase(final UUID internalWalletId) throws CantCreateNewCryptoBrokerWalletException
    {
        try {
            CryptoBrokerWalletDatabaseFactory databaseFactory = new CryptoBrokerWalletDatabaseFactory();
            databaseFactory.setPluginDatabaseSystem(pluginDatabaseSystem);
            database = databaseFactory.createDatabase(this.pluginId, internalWalletId);
        } catch (CantCreateDatabaseException cantCreateDatabaseException) {
            throw new CantCreateNewCryptoBrokerWalletException("Database could not be created", cantCreateDatabaseException, "internalWalletId: " + internalWalletId.toString(), "");
        }
    }

    private void persistWallet(final PluginTextFile pluginTextFile) throws CantCreateNewCryptoBrokerWalletException
    {
        StringBuilder stringBuilder = new StringBuilder(wallet.size() * 72);
        Iterator iterator = wallet.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry) iterator.next();

            stringBuilder
                    .append(pair.getKey().toString())
                    .append(",")
                    .append(pair.getValue().toString())
                    .append(";");

            iterator.remove();
        }

        pluginTextFile.setContent(stringBuilder.toString());

        try {
            pluginTextFile.persistToMedia();
        } catch (CantPersistFileException cantPersistFileException) {
            throw new CantCreateNewCryptoBrokerWalletException("Could not persist in file", cantPersistFileException, "stringBuilder: " + stringBuilder.toString(), "");
        }
    }

}
