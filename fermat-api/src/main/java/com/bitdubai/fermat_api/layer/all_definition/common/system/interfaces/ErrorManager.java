package com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedAddonsExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPlatformExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.PlatformComponents;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;


/**
 * Created by ciencias on 05.02.15.
 * Modified by lnacosta (laion.cj91@gmail.com) on 26/10/2015.
 */
public interface ErrorManager extends FermatManager {

    void reportUnexpectedPlatformException(PlatformComponents exceptionSource, UnexpectedPlatformExceptionSeverity unexpectedPlatformExceptionSeverity, Exception exception);

    void reportUnexpectedPluginException(Plugins exceptionSource, UnexpectedPluginExceptionSeverity unexpectedPluginExceptionSeverity, Exception exception);
    void reportUnexpectedPluginException(Plugins exceptionSource, Platforms platform, UnexpectedPluginExceptionSeverity unexpectedPluginExceptionSeverity, Exception exception,final String[] mailTo);

    void reportUnexpectedWalletException(Wallets exceptionSource, UnexpectedWalletExceptionSeverity unexpectedWalletExceptionSeverity, Exception exception);

    void reportUnexpectedAddonsException(Addons exceptionSource, UnexpectedAddonsExceptionSeverity unexpectedAddonsExceptionSeverity, Exception exception);

    void reportUnexpectedSubAppException(SubApps exceptionSource, UnexpectedSubAppExceptionSeverity unexpectedAddonsExceptionSeverity, Exception exception);

    void reportUnexpectedUIException(UISource exceptionSource, UnexpectedUIExceptionSeverity unexpectedAddonsExceptionSeverity, Exception exception);

    /**
     * Throw the method <code>reportUnexpectedPluginException</code> you can report an unexpected error in a plugin.
     *
     * @param exceptionSource    plugin version reference indicating in where the error was produced.
     * @param exceptionSeverity  severity of the exception, priority stuff.
     * @param exception          exception reported.
     */
    void reportUnexpectedPluginException(final PluginVersionReference            exceptionSource  ,
                                         final UnexpectedPluginExceptionSeverity exceptionSeverity,
                                         final Exception                         exception        );

    /**
     * Throw the method <code>reportUnexpectedAddonsException</code> you can report an unexpected error in an addon.
     *
     * @param exceptionSource    addon version reference indicating in where the error was produced.
     * @param exceptionSeverity  severity of the exception, priority stuff.
     * @param exception          exception reported.
     */
    void reportUnexpectedAddonsException(final AddonVersionReference             exceptionSource  ,
                                         final UnexpectedAddonsExceptionSeverity exceptionSeverity,
                                         final Exception                         exception        );

    /**
     * Throw the method <code>reportUnexpectedEventException</code> you can report an unexpected error in an event.
     * The only who reports this type of exceptions is now the event monitor, and he doesn't know the severity of
     * an exception, for that reason the severity is always "UNKNOWN".
     *
     * @param exceptionSource  event who throw the exception.
     * @param exception        exception reported.
     */
    void reportUnexpectedEventException(final FermatEvent exceptionSource,
                                        final Exception   exception      );

    void enabledErrorReport(boolean isErrorReportEnabled);
}