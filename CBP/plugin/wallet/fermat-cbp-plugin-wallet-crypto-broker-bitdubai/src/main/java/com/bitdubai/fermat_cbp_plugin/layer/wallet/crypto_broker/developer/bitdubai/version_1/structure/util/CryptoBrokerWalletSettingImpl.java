package com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure.util;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantClearCryptoBrokerWalletSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetCryptoBrokerWalletSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantSaveCryptoBrokerWalletSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletAssociatedSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletProviderSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletSettingSpread;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.database.CryptoBrokerWalletDatabaseDao;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

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
    ErrorManager errorManager;
    /**
     * Constructor.
     */
    public CryptoBrokerWalletSettingImpl(final Database database, final UUID plugin, final PluginFileSystem pluginFileSystem, ErrorManager errorManager){
        this.database = database;
        this.plugin = plugin;
        this.pluginFileSystem = pluginFileSystem;
        this.errorManager = errorManager;
        cryptoBrokerWalletDatabaseDao = new CryptoBrokerWalletDatabaseDao(this.database, this.errorManager);
        cryptoBrokerWalletDatabaseDao.setPlugin(this.plugin);
        cryptoBrokerWalletDatabaseDao.setPluginFileSystem(this.pluginFileSystem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveCryptoBrokerWalletSpreadSetting(CryptoBrokerWalletSettingSpread cryptoBrokerWalletSettingSpread) throws CantSaveCryptoBrokerWalletSettingException {
            cryptoBrokerWalletDatabaseDao.saveCryptoBrokerWalletSpreadSetting(cryptoBrokerWalletSettingSpread);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearCryptoBrokerWalletSpreadSetting() throws CantClearCryptoBrokerWalletSettingException {
            cryptoBrokerWalletDatabaseDao.clearCryptoBrokerWalletSpreadSetting();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CryptoBrokerWalletSettingSpread getCryptoBrokerWalletSpreadSetting() throws CantGetCryptoBrokerWalletSettingException {
        return cryptoBrokerWalletDatabaseDao.getCryptoBrokerWalletSpreadSetting();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveCryptoBrokerWalletAssociatedSetting(CryptoBrokerWalletAssociatedSetting cryptoBrokerWalletAssociatedSetting) throws CantSaveCryptoBrokerWalletSettingException {
         cryptoBrokerWalletDatabaseDao.saveCryptoBrokerWalletAssociatedSetting(cryptoBrokerWalletAssociatedSetting);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearCryptoBrokerWalletAssociatedSetting(Platforms platform) throws CantClearCryptoBrokerWalletSettingException {
         cryptoBrokerWalletDatabaseDao.clearCryptoBrokerWalletAssociatedSetting(platform);
    }



    /**
     * {@inheritDoc}
     */
    @Override
    public List<CryptoBrokerWalletAssociatedSetting> getCryptoBrokerWalletAssociatedSettings() throws CantGetCryptoBrokerWalletSettingException {
        return cryptoBrokerWalletDatabaseDao.getCryptoBrokerWalletAssociatedSettings();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveCryptoBrokerWalletProviderSetting(CryptoBrokerWalletProviderSetting cryptoBrokerWalletProviderSetting) throws CantSaveCryptoBrokerWalletSettingException {
        cryptoBrokerWalletDatabaseDao.saveCryptoBrokerWalletProviderSetting(cryptoBrokerWalletProviderSetting);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearCryptoBrokerWalletProviderSetting() throws CantClearCryptoBrokerWalletSettingException {
        cryptoBrokerWalletDatabaseDao.clearCryptoBrokerWalletProviderSetting();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CryptoBrokerWalletProviderSetting> getCryptoBrokerWalletProviderSettings() throws CantGetCryptoBrokerWalletSettingException {
        return cryptoBrokerWalletDatabaseDao.getCryptoBrokerWalletProviderSettings();
    }
}
