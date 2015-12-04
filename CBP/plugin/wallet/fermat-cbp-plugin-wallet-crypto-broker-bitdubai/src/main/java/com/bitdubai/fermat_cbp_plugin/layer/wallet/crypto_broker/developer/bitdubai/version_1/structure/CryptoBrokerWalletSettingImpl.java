package com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetCryptoBrokerWalletSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantSaveCryptoBrokerWalletSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletAssociatedSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletProviderSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletSettingSpread;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.database.CryptoBrokerWalletDatabaseDao;

import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 03/12/15.
 */
public class CryptoBrokerWalletSettingImpl implements CryptoBrokerWalletSetting {
    //TODO: Documentar y manejo de excepciones
    private Database database;
    private CryptoBrokerWalletDatabaseDao cryptoBrokerWalletDatabaseDao;
    UUID plugin;
    PluginFileSystem pluginFileSystem;

    /**
     * Constructor.
     */
    public CryptoBrokerWalletSettingImpl(final Database database, final UUID plugin, final PluginFileSystem pluginFileSystem){
        this.database = database;
        this.plugin = plugin;
        this.pluginFileSystem = pluginFileSystem;

        cryptoBrokerWalletDatabaseDao = new CryptoBrokerWalletDatabaseDao(this.database);
        cryptoBrokerWalletDatabaseDao.setPlugin(this.plugin);
        cryptoBrokerWalletDatabaseDao.setPluginFileSystem(this.pluginFileSystem);
    }

    @Override
    public void saveCryptoBrokerWalletSpreadSetting(CryptoBrokerWalletSettingSpread cryptoBrokerWalletSettingSpread) throws CantSaveCryptoBrokerWalletSettingException {
        cryptoBrokerWalletDatabaseDao.saveCryptoBrokerWalletSpreadSetting(cryptoBrokerWalletSettingSpread);
    }

    @Override
    public CryptoBrokerWalletSettingSpread getCryptoBrokerWalletSpreadSetting(UUID id) throws CantGetCryptoBrokerWalletSettingException {
        return cryptoBrokerWalletDatabaseDao.getCryptoBrokerWalletSpreadSetting(id);
    }

    @Override
    public void saveCryptoBrokerWalletAssociatedSetting(CryptoBrokerWalletAssociatedSetting cryptoBrokerWalletAssociatedSetting) throws CantSaveCryptoBrokerWalletSettingException {
        cryptoBrokerWalletDatabaseDao.saveCryptoBrokerWalletAssociatedSetting(cryptoBrokerWalletAssociatedSetting);
    }

    @Override
    public List<CryptoBrokerWalletAssociatedSetting> getCryptoBrokerWalletAssociatedSettings() throws CantGetCryptoBrokerWalletSettingException {
        return cryptoBrokerWalletDatabaseDao.getCryptoBrokerWalletAssociatedSettings();
    }

    @Override
    public void saveCryptoBrokerWalletProviderSetting(CryptoBrokerWalletProviderSetting cryptoBrokerWalletProviderSetting) throws CantSaveCryptoBrokerWalletSettingException {
        cryptoBrokerWalletDatabaseDao.saveCryptoBrokerWalletProviderSetting(cryptoBrokerWalletProviderSetting);
    }

    @Override
    public List<CryptoBrokerWalletProviderSetting> getCryptoBrokerWalletProviderSettings() throws CantGetCryptoBrokerWalletSettingException {
        return cryptoBrokerWalletDatabaseDao.getCryptoBrokerWalletProviderSettings();
    }
}
