package com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantListInputTransactionsException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantLoadEarningSettingsException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantRegisterEarningsSettingsException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.EarningsSettingsNotRegisteredException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningExtractor;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningExtractorManager;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningsPair;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningsSearch;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningsSettings;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.InputTransaction;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.MatchingEngineManager;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.utils.WalletReference;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.database.MatchingEngineMiddlewareDao;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.structure.earning_extraction.EarningExtractorManagerImpl;

import java.util.List;
import java.util.UUID;


/**
 * The class <code>com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.structure.MatchingEngineMiddlewareManager</code>
 * is the manager of the plug-in matching engine middleware of the cbp platform.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 09/02/2016.
 *
 * @author lnacosta
 * @version 1.0
 */
public final class MatchingEngineMiddlewareManager implements MatchingEngineManager {

    private final MatchingEngineMiddlewareDao dao;
    private final ErrorManager errorManager;
    private final PluginVersionReference pluginVersionReference;
    private final EarningExtractorManager earningExtractorManager;
    private List<InputTransaction> inputTransactions;

    public MatchingEngineMiddlewareManager(final MatchingEngineMiddlewareDao dao,
                                           final ErrorManager errorManager,
                                           final PluginVersionReference pluginVersionReference,
                                           CryptoBrokerWalletManager cryptoBrokerWalletManager) {

        this.dao = dao;
        this.errorManager = errorManager;
        this.pluginVersionReference = pluginVersionReference;
        earningExtractorManager = new EarningExtractorManagerImpl(cryptoBrokerWalletManager, dao);
    }

    @Override
    public EarningsSettings registerEarningsSettings(final WalletReference walletReference) throws CantRegisterEarningsSettingsException {


        try {

            dao.registerWalletReference(walletReference);

            return new MatchingEngineMiddlewareEarningsSettings(
                    walletReference,
                    dao,
                    errorManager,
                    pluginVersionReference
            );

        } catch (CantRegisterEarningsSettingsException e) {
            errorManager.reportUnexpectedPluginException(this.pluginVersionReference,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;

        } catch (final Exception e) {
            errorManager.reportUnexpectedPluginException(this.pluginVersionReference,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantRegisterEarningsSettingsException(e, null, "Unhandled Exception.");
        }

    }

    @Override
    public EarningsSettings loadEarningsSettings(final String walletPublicKey)
            throws CantLoadEarningSettingsException, EarningsSettingsNotRegisteredException {
        try {
            WalletReference walletReference = dao.loadWalletReference(walletPublicKey);
            return new MatchingEngineMiddlewareEarningsSettings(walletReference, dao, errorManager, pluginVersionReference);

        } catch (EarningsSettingsNotRegisteredException earningsSettingsNotRegisteredException) {
            throw earningsSettingsNotRegisteredException;   // I DO NOTHING HERE, MAYBE IS NOT REGISTERED

        } catch (CantLoadEarningSettingsException cantLoadEarningSettingsException) {
            errorManager.reportUnexpectedPluginException(this.pluginVersionReference,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantLoadEarningSettingsException);
            throw cantLoadEarningSettingsException;

        } catch (final Exception e) {
            errorManager.reportUnexpectedPluginException(this.pluginVersionReference,
                    UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantLoadEarningSettingsException(e, null, "Unhandled Exception.");
        }

    }

    @Override
    public EarningExtractorManager getEarningsExtractorManager() {
        return earningExtractorManager;
    }

    @Override
    public EarningsSearch getSearch(EarningsPair earningsPair) {
        return new MatchingEngineMiddlewareEarningsSearch(dao, earningsPair);
    }

    @Override
    public List<InputTransaction> listInputTransactions(UUID earningTransactionId) throws CantListInputTransactionsException {

        if (inputTransactions == null)
            inputTransactions = dao.listInputsTransactionByEarningTransaction(earningTransactionId);

        return inputTransactions;
    }

    public void addEarningsExtractor(EarningExtractor extractor){
        earningExtractorManager.addEarningExtractor(extractor);
    }
}
