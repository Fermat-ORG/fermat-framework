package com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantAssociatePairException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantDisassociatePairException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantListEarningsPairsException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantUpdatePairException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.PairAlreadyAssociatedException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.PairNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningsPair;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningsSettings;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.utils.WalletReference;
import com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.database.MatchingEngineMiddlewareDao;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.List;
import java.util.UUID;

/**
 * The class <code>com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.structure.MatchingEngineMiddlewareEarningsSettings</code>
 * contains all the methods that interact with the earnings settings.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 09/02/2016.
 *
 * @author lnacosta
 * @version 1.0
 */
public final class MatchingEngineMiddlewareEarningsSettings implements EarningsSettings {

    private final WalletReference             walletReference       ;
    private final MatchingEngineMiddlewareDao dao                   ;
    private final ErrorManager                errorManager          ;
    private final PluginVersionReference      pluginVersionReference;

    public MatchingEngineMiddlewareEarningsSettings(final WalletReference             walletReference       ,
                                                    final MatchingEngineMiddlewareDao dao                   ,
                                                    final ErrorManager                errorManager          ,
                                                    final PluginVersionReference      pluginVersionReference) {

        this.walletReference        = walletReference       ;
        this.dao                    = dao                   ;
        this.errorManager           = errorManager          ;
        this.pluginVersionReference = pluginVersionReference;
    }

    @Override
    public EarningsPair registerPair(final Currency        earningCurrency       ,
                                     final Currency        linkedCurrency        ,
                                     final WalletReference earningWalletReference) throws CantAssociatePairException     ,
                                                                                          PairAlreadyAssociatedException {

        try {

            final UUID newId = UUID.randomUUID();

            return dao.registerEarningsPair(
                    newId,
                    earningCurrency       ,
                    linkedCurrency        ,
                    earningWalletReference,
                    walletReference
            );

        } catch (final CantAssociatePairException | PairAlreadyAssociatedException e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (final Exception e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantAssociatePairException(e, null, "Unhandled Exception.");
        }
    }

    @Override
    public void associateEarningsPair(final UUID earningsPairID) throws CantAssociatePairException,
                                                                        PairNotFoundException     {

        try {

            dao.associateEarningsPair(earningsPairID);

        } catch (final CantAssociatePairException | PairNotFoundException e) {

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (final Exception e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantAssociatePairException(e, null, "Unhandled Exception.");
        }
    }

    @Override
    public void disassociateEarningsPair(final UUID earningsPairID) throws CantDisassociatePairException,
                                                                           PairNotFoundException        {

        try {

            dao.disassociateEarningsPair(earningsPairID);

        } catch (final CantDisassociatePairException | PairNotFoundException e) {

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (final Exception e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantDisassociatePairException(e, null, "Unhandled Exception.");
        }
    }

    @Override
    public void updateEarningsPair(final UUID            earningsPairID ,
                                   final WalletReference walletReference) throws CantUpdatePairException,
                                                                                 PairNotFoundException  {

        try {

            dao.updateEarningsPair(
                    earningsPairID ,
                    walletReference
            );

        } catch (final CantUpdatePairException | PairNotFoundException e) {

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (final Exception e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantUpdatePairException(e, null, "Unhandled Exception.");
        }
    }

    @Override
    public List<EarningsPair> listEarningPairs() throws CantListEarningsPairsException {

        try {

            return dao.listEarningPairs(
                    this.walletReference
            );

        } catch (final CantListEarningsPairsException e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw e;
        } catch (final Exception e){

            errorManager.reportUnexpectedPluginException(this.pluginVersionReference, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantListEarningsPairsException(e, null, "Unhandled Exception.");
        }
    }

}
