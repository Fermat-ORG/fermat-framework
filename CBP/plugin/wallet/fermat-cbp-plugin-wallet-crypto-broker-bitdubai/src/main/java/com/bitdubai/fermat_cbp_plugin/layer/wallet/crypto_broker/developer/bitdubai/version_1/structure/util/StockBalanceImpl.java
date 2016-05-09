package com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure.util;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.all_definition.constants.CBPBroadcasterConstants;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_cbp_api.all_definition.wallet.StockBalance;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantAddCreditCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantAddDebitCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetAvailableBalanceCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetBookedBalanceCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerStockTransactionRecord;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWalletBalanceRecord;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.database.CryptoBrokerWalletDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.exceptions.CantAddCreditException;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.exceptions.CantAddDebitException;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.exceptions.CantGetBalanceRecordException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 30/11/15.
 */
public class StockBalanceImpl implements StockBalance {
    //TODO: Documentar y manejo de excepciones
    private Database database;
    private CryptoBrokerWalletDatabaseDao cryptoBrokerWalletDatabaseDao;
    UUID plugin;
    PluginFileSystem pluginFileSystem;
    ErrorManager errorManager;
    private Broadcaster broadcaster;

    /**
     * Constructor for StockBalanceImpl
     *
     * @param database
     * @param plugin
     * @param pluginFileSystem
     */
    public StockBalanceImpl(final Database database, final UUID plugin, final PluginFileSystem pluginFileSystem, ErrorManager errorManager,Broadcaster broadcaster) {
        this.database = database;
        this.plugin = plugin;
        this.pluginFileSystem = pluginFileSystem;
        this.errorManager = errorManager;
        this.broadcaster=broadcaster;
        cryptoBrokerWalletDatabaseDao = new CryptoBrokerWalletDatabaseDao(this.database, errorManager);
        cryptoBrokerWalletDatabaseDao.setPlugin(this.plugin);
        cryptoBrokerWalletDatabaseDao.setPluginFileSystem(this.pluginFileSystem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float getBookedBalance(Currency merchandise) throws CantGetBookedBalanceCryptoBrokerWalletException, CantStartPluginException {
        try {
            return cryptoBrokerWalletDatabaseDao.getBookedBalance(merchandise);
        }catch (CantGetBookedBalanceCryptoBrokerWalletException e){
        this.errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_BROKER_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        throw new CantGetBookedBalanceCryptoBrokerWalletException(CantGetBookedBalanceCryptoBrokerWalletException.DEFAULT_MESSAGE, e, null, null);
        }
        catch(Exception e) {
            this.errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_BROKER_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, e, null, null);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float getAvailableBalance(Currency merchandise) throws CantGetAvailableBalanceCryptoBrokerWalletException, CantStartPluginException {
       try{
        return cryptoBrokerWalletDatabaseDao.geAvailableBalance(merchandise);
       }catch(CantGetAvailableBalanceCryptoBrokerWalletException e) {
           this.errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_BROKER_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
           throw new CantGetAvailableBalanceCryptoBrokerWalletException(CantGetAvailableBalanceCryptoBrokerWalletException.DEFAULT_MESSAGE, e, null, null);
       }
       catch(Exception e) {
           this.errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_BROKER_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
           throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, e, null, null);
       }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float getAvailableBalanceFrozen(Currency merchandise) throws CantGetAvailableBalanceCryptoBrokerWalletException, CantStartPluginException {
        try{
        return cryptoBrokerWalletDatabaseDao.getAvailableBalanceFrozen(merchandise);
        }catch(CantGetAvailableBalanceCryptoBrokerWalletException e) {
            this.errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_BROKER_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetAvailableBalanceCryptoBrokerWalletException(CantGetAvailableBalanceCryptoBrokerWalletException.DEFAULT_MESSAGE, e, null, null);
        }
        catch(Exception e) {
            this.errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_BROKER_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, e, null, null);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CryptoBrokerWalletBalanceRecord> getCryptoBrokerWalletBalanceBook() throws CantGetBookedBalanceCryptoBrokerWalletException, CantStartPluginException {
        List<CryptoBrokerWalletBalanceRecord> cryptoBrokerWalletBalanceRecords = null;
        try {
            cryptoBrokerWalletBalanceRecords = cryptoBrokerWalletDatabaseDao.getAvailableBalanceByMerchandise();
        } catch (CantCalculateBalanceException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_BROKER_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, e, null, null);
        }
        catch (CantGetBalanceRecordException e) {
            this.errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_BROKER_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, e, null, null);

        } catch (Exception exception) {
            this.errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_BROKER_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, exception, null, null);
        }
        return cryptoBrokerWalletBalanceRecords;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CryptoBrokerWalletBalanceRecord> getCryptoBrokerWalletBalanceAvailable() throws CantGetBookedBalanceCryptoBrokerWalletException, CantStartPluginException {
        List<CryptoBrokerWalletBalanceRecord> cryptoBrokerWalletBalanceRecords = null;
        try {
            cryptoBrokerWalletBalanceRecords = cryptoBrokerWalletDatabaseDao.getBookBalanceByMerchandise();
        } catch (CantCalculateBalanceException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_BROKER_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, e, null, null);
        }
        catch (CantGetBalanceRecordException e) {
            this.errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_BROKER_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, e, null, null);

        } catch (Exception exception) {
            this.errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_BROKER_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, exception, null, null);
        }
        return cryptoBrokerWalletBalanceRecords;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CryptoBrokerWalletBalanceRecord> getCryptoBrokerWalletBalanceBookFrozen() throws CantGetBookedBalanceCryptoBrokerWalletException, CantCalculateBalanceException, CantStartPluginException {
        List<CryptoBrokerWalletBalanceRecord> cryptoBrokerWalletBalanceRecords = null;
        try {
            cryptoBrokerWalletBalanceRecords = cryptoBrokerWalletDatabaseDao.getBookBalanceByMerchandiseFrozen();
        } catch (CantCalculateBalanceException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_BROKER_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantCalculateBalanceException("Cant Calculate Balance Exception.", FermatException.wrapException(e), null, null);

        }

        catch (Exception exception) {
            this.errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_BROKER_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, exception, null, null);
        }
        return cryptoBrokerWalletBalanceRecords;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CryptoBrokerWalletBalanceRecord> getCryptoBrokerWalletBalanceAvailableFrozen() throws CantGetBookedBalanceCryptoBrokerWalletException, CantStartPluginException, CantCalculateBalanceException {
        List<CryptoBrokerWalletBalanceRecord> cryptoBrokerWalletBalanceRecords = null;
        try {
            cryptoBrokerWalletBalanceRecords = cryptoBrokerWalletDatabaseDao.getAvailableBalanceByMerchandiseFrozen();
        } catch (CantCalculateBalanceException e) {
            errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_BROKER_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantCalculateBalanceException("CAnt Calculate Balance Exception.", FermatException.wrapException(e), null, null);

        }

        catch (Exception exception) {
            this.errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_BROKER_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,exception);
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, exception, null, null);
        }
        return cryptoBrokerWalletBalanceRecords;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debit(CryptoBrokerStockTransactionRecord cryptoBrokerStockTransactionRecord, BalanceType balanceType) throws CantAddDebitCryptoBrokerWalletException, CantStartPluginException {
        try {
            cryptoBrokerWalletDatabaseDao.addDebit(cryptoBrokerStockTransactionRecord, balanceType);
            broadcaster.publish(BroadcasterType.UPDATE_VIEW, CBPBroadcasterConstants.CBW_OPERATION_DEBIT_OR_CREDIT_UPDATE_VIEW);
        } catch (CantAddDebitException e) {
            this.errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_BROKER_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, e, null, null);
        }
        catch (Exception exception) {
            this.errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_BROKER_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,exception);
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, exception, null, null);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void credit(CryptoBrokerStockTransactionRecord cryptoBrokerStockTransactionRecord, BalanceType balanceType) throws CantAddCreditCryptoBrokerWalletException, CantStartPluginException {
        try {
            cryptoBrokerWalletDatabaseDao.addCredit(cryptoBrokerStockTransactionRecord, balanceType);
            broadcaster.publish(BroadcasterType.UPDATE_VIEW,CBPBroadcasterConstants.CBW_OPERATION_DEBIT_OR_CREDIT_UPDATE_VIEW);
        } catch (CantAddCreditException e) {
            this.errorManager.reportUnexpectedPluginException(Plugins.CRYPTO_BROKER_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, e, null, null);

        }
    }
}
