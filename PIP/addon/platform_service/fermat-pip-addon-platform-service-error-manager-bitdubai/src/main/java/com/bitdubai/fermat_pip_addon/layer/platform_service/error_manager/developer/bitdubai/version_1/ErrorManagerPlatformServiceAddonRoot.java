package com.bitdubai.fermat_pip_addon.layer.platform_service.error_manager.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractAddon;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.PlatformComponents;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_pip_addon.layer.platform_service.error_manager.developer.bitdubai.version_1.functional.ErrorReport;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedAddonsExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPlatformExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedWalletExceptionSeverity;

/**
 * Throw this addon you can report an unexpected error in the platform.
 *
 * For now, the only functionality of the addon is report in log all problems, but in the near future, the idea is to save all the errors and send to a bitDubai server to be properly controlled.
 *
 * Created by lnacosta (laion.cj91@gmail.com) on 26/10/2015.
 */
public final class ErrorManagerPlatformServiceAddonRoot extends AbstractAddon implements ErrorManager {

    /**
     * Constructor without parameters.
     */
    public ErrorManagerPlatformServiceAddonRoot() {
        super(new AddonVersionReference(new Version()));
    }

    /**
     * ErrorManager Interface implementation.
     */
    @Override
    public final void reportUnexpectedPlatformException(final PlatformComponents                  exceptionSource                    ,
                                                  final UnexpectedPlatformExceptionSeverity unexpectedPlatformExceptionSeverity,
                                                  final Exception                           exception                          ) {

        processException(exceptionSource.name(), unexpectedPlatformExceptionSeverity.name(), exception);
    }

    @Override
    public final void reportUnexpectedPluginException(final Plugins                           exceptionSource                  ,
                                                final UnexpectedPluginExceptionSeverity unexpectedPluginExceptionSeverity,
                                                final Exception                         exception                        ) {

        processException(exceptionSource.toString(), unexpectedPluginExceptionSeverity.toString(), exception);
    }

    @Override
    public final void reportUnexpectedPluginException(final PluginVersionReference            exceptionSource                  ,
                                                final UnexpectedPluginExceptionSeverity unexpectedPluginExceptionSeverity,
                                                final Exception                         exception                        ) {

        processException(exceptionSource.toString(), unexpectedPluginExceptionSeverity.toString(), exception);
    }

    @Override
    public final void reportUnexpectedWalletException(final Wallets                           exceptionSource                  ,
                                                      final UnexpectedWalletExceptionSeverity unexpectedWalletExceptionSeverity,
                                                      final Exception                         exception                        ) {

        processException(exceptionSource.toString(), unexpectedWalletExceptionSeverity.toString(),exception);
    }

    @Override
    public final void reportUnexpectedAddonsException(final Addons                            exceptionSource                  ,
                                                      final UnexpectedAddonsExceptionSeverity unexpectedAddonsExceptionSeverity,
                                                      final Exception                         exception                        ) {

        processException(exceptionSource.toString(), unexpectedAddonsExceptionSeverity.toString(), exception);
    }

    @Override
    public final void reportUnexpectedAddonsException(final AddonVersionReference             exceptionSource                  ,
                                                      final UnexpectedPluginExceptionSeverity unexpectedPluginExceptionSeverity,
                                                      final Exception                         exception                        ) {

        processException(exceptionSource.toString(), unexpectedPluginExceptionSeverity.toString(), exception);
    }

    @Override
    public final void reportUnexpectedSubAppException(final SubApps                           exceptionSource                  ,
                                                      final UnexpectedSubAppExceptionSeverity unexpectedSubAppExceptionSeverity,
                                                      final Exception                         exception                        ) {

        processException(exceptionSource.toString(), unexpectedSubAppExceptionSeverity.toString(), exception);
    }

    @Override
    public final void reportUnexpectedUIException(final UISource                      exceptionSource                  ,
                                                  final UnexpectedUIExceptionSeverity unexpectedAddonsExceptionSeverity,
                                                  final Exception                     exception                        ) {

        processException(exceptionSource.toString(), unexpectedAddonsExceptionSeverity.toString(), exception);
    }

    @Override
    public final void reportUnexpectedEventException(final FermatEvent exceptionSource,
                                                     final Exception   exception      ) {

        processException(exceptionSource.toString(), "Unknow", exception);
    }

    private void processException(final String source, final String severity, final Exception exception){
        printErrorReport(source, severity, FermatException.wrapException(exception));
    }

    private void printErrorReport(final String source, final String severity, final FermatException exception){
        System.err.println(new ErrorReport(source, severity, exception).generateReport());
    }

}
