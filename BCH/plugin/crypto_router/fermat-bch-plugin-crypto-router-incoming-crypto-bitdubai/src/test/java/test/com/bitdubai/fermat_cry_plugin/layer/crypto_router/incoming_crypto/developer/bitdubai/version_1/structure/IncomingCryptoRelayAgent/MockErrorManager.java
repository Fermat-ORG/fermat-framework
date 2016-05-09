package test.com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.structure.IncomingCryptoRelayAgent;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.PlatformComponents;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedAddonsExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPlatformExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;

/**
 * Created by franklin on 04/08/15.
 */
public class MockErrorManager implements ErrorManager {
    private String reportedException;

    public String getReportedException(){
        return reportedException;
    }


    @Override
    public void reportUnexpectedPlatformException(PlatformComponents exceptionSource, UnexpectedPlatformExceptionSeverity unexpectedPlatformExceptionSeverity, Exception exception) {
        this.reportedException = exception.toString();
    }

    @Override
    public void reportUnexpectedPluginException(PluginVersionReference exceptionSource, UnexpectedPluginExceptionSeverity unexpectedPluginExceptionSeverity, Exception exception) {
        this.reportedException = exception.toString();

    }

    @Override
    public void reportUnexpectedAddonsException(AddonVersionReference exceptionSource, UnexpectedPluginExceptionSeverity unexpectedPluginExceptionSeverity, Exception exception) {
        this.reportedException = exception.toString();

    }

    @Override
    public void reportUnexpectedPluginException(Plugins exceptionSource, UnexpectedPluginExceptionSeverity unexpectedPluginExceptionSeverity, Exception exception) {
        this.reportedException = exception.toString();
    }

    @Override
    public void reportUnexpectedWalletException(Wallets exceptionSource, UnexpectedWalletExceptionSeverity unexpectedWalletExceptionSeverity, Exception exception) {
        this.reportedException = exception.toString();
    }

    @Override
    public void reportUnexpectedAddonsException(Addons exceptionSource, UnexpectedAddonsExceptionSeverity unexpectedAddonsExceptionSeverity, Exception exception) {
        this.reportedException = exception.toString();
    }

    @Override
    public void reportUnexpectedSubAppException(SubApps exceptionSource, UnexpectedSubAppExceptionSeverity unexpectedAddonsExceptionSeverity, Exception exception) {
        this.reportedException = exception.toString();
    }

    @Override
    public void reportUnexpectedUIException(UISource exceptionSource, UnexpectedUIExceptionSeverity unexpectedAddonsExceptionSeverity, Exception exception) {

    }

    @Override
    public void reportUnexpectedEventException(FermatEvent exceptionSource, Exception exception) {

    }
}
