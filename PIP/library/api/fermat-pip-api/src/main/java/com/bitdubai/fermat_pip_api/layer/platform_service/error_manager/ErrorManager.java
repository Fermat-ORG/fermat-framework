package com.bitdubai.fermat_pip_api.layer.platform_service.error_manager;

import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.PlatformComponents;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;


/**
 * Created by ciencias on 05.02.15.
 */
public interface ErrorManager {

    public void reportUnexpectedPlatformException(PlatformComponents exceptionSource, UnexpectedPlatformExceptionSeverity unexpectedPlatformExceptionSeverity, Exception exception);

    public void reportUnexpectedPluginException(Plugins exceptionSource, com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity unexpectedPluginExceptionSeverity, Exception exception);

    public void reportUnexpectedWalletException(Wallets exceptionSource, com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedWalletExceptionSeverity unexpectedWalletExceptionSeverity, Exception exception);

    public void reportUnexpectedAddonsException(Addons exceptionSource, com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedAddonsExceptionSeverity unexpectedAddonsExceptionSeverity, Exception exception);

    public void reportUnexpectedSubAppException(SubApps exceptionSource, com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedSubAppExceptionSeverity unexpectedAddonsExceptionSeverity, Exception exception);

    public void reportUnexpectedUIException(UISource exceptionSource, com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedUIExceptionSeverity unexpectedAddonsExceptionSeverity, Exception exception);

    public void reportUnexpectedEventException(FermatEvent exceptionSource, Exception exception);

}