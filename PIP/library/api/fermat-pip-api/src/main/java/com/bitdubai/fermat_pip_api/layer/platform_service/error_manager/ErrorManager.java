package com.bitdubai.fermat_pip_api.layer.platform_service.error_manager;

import com.bitdubai.fermat_api.layer.all_definition.common.interfaces.FermatAddonManager;
import com.bitdubai.fermat_api.layer.all_definition.common.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.common.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.PlatformComponents;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;

/**
 * Created by ciencias on 05.02.15.
 * Modified by lnacosta (laion.cj91@gmail.com) on 26/10/2015.
 */
public interface ErrorManager extends FermatAddonManager {

    void reportUnexpectedPlatformException(PlatformComponents exceptionSource, UnexpectedPlatformExceptionSeverity unexpectedPlatformExceptionSeverity, Exception exception);

    void reportUnexpectedPluginException(Plugins exceptionSource, UnexpectedPluginExceptionSeverity unexpectedPluginExceptionSeverity, Exception exception);

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
                                         final UnexpectedPluginExceptionSeverity exceptionSeverity,
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

}