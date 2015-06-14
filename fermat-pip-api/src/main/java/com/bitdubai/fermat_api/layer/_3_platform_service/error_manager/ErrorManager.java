package com.bitdubai.fermat_api.layer._3_platform_service.error_manager;

import com.bitdubai.fermat_api.layer._15_middleware.app_runtime.enums.Wallets;
import com.bitdubai.fermat_api.layer._1_definition.enums.Addons;
import com.bitdubai.fermat_api.layer._1_definition.enums.PlatformComponents;
import com.bitdubai.fermat_api.layer._1_definition.enums.Plugins;

/**
 * Created by ciencias on 05.02.15.
 */
public interface ErrorManager {


    public void reportUnexpectedPlatformException(PlatformComponents exceptionSource, UnexpectedPlatformExceptionSeverity unexpectedPlatformExceptionSeverity, Exception exception);

    public void reportUnexpectedPluginException(Plugins exceptionSource, UnexpectedPluginExceptionSeverity unexpectedPluginExceptionSeverity, Exception exception);

    public void reportUnexpectedWalletException(Wallets exceptionSource, UnexpectedWalletExceptionSeverity unexpectedWalletExceptionSeverity, Exception exception);

    public void reportUnexpectedAddonsException(Addons exceptionSource, UnexpectedAddonsExceptionSeverity unexpectedAddonsExceptionSeverity, Exception exception);
}