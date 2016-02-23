package com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantLoadEarningSettingsException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningsSettings;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.MatchingEngineManager;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.utils.WalletReference;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.database.MatchingEngineMiddlewareDao;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.exceptions.CantLoadOrCreateWalletReferenceException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

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

    private final MatchingEngineMiddlewareDao dao                   ;
    private final ErrorManager                errorManager          ;
    private final PluginVersionReference      pluginVersionReference;

    public MatchingEngineMiddlewareManager(final MatchingEngineMiddlewareDao dao                   ,
                                           final ErrorManager                errorManager          ,
                                           final PluginVersionReference      pluginVersionReference) {

        this.dao                    = dao                   ;
        this.errorManager           = errorManager          ;
        this.pluginVersionReference = pluginVersionReference;
    }

    @Override
    public EarningsSettings loadEarningsSettings(final WalletReference walletReference) throws CantLoadEarningSettingsException {

        try {

            dao.loadOrCreateWalletReference(walletReference);

            return new MatchingEngineMiddlewareEarningsSettings(
                    walletReference,
                    dao,
                    errorManager,
                    pluginVersionReference
            );

        } catch (CantLoadOrCreateWalletReferenceException cantLoadOrCreateWalletReferenceException) {

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, cantLoadOrCreateWalletReferenceException);
            throw new CantLoadEarningSettingsException(cantLoadOrCreateWalletReferenceException, "walletReference: "+walletReference, "Problem trying to load or create the wallet reference in database");
        } catch (final Exception e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantLoadEarningSettingsException(e, null, "Unhandled Exception.");
        }

    }
}
