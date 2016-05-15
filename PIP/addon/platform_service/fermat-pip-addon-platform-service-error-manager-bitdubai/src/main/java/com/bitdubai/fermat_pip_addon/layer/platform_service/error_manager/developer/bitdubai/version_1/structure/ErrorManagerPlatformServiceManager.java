package com.bitdubai.fermat_pip_addon.layer.platform_service.error_manager.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
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
import com.bitdubai.fermat_api.layer.osa_android.hardware.HardwareManager;
import com.bitdubai.fermat_pip_addon.layer.platform_service.error_manager.developer.bitdubai.version_1.functional.ErrorReport;
import com.bitdubai.fermat_pip_addon.layer.platform_service.error_manager.developer.bitdubai.version_1.util.GMailSender;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedAddonsExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPlatformExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


/**
 * The class <code>com.bitdubai.fermat_pip_addon.layer.platform_service.error_manager.developer.bitdubai.version_1.structure.ErrorManagerPlatformServiceManager</code>
 * implements Error Manager interface and contains all the functionality to manage errors in fermat platform.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/11/2015.
 */
public final class ErrorManagerPlatformServiceManager implements ErrorManager {


    private final HardwareManager hardwareManager;
    GMailSender gMailSender = new GMailSender("fermatmatiasreport@gmail.com","fermat123");

    private boolean isErrorReport;

    public ErrorManagerPlatformServiceManager(HardwareManager hardwareManager) {
        this.hardwareManager = hardwareManager;
        this.isErrorReport = false;
    }

    /**
     * ErrorManager Interface implementation.
     */
    @Override
    public final void reportUnexpectedPlatformException(final PlatformComponents exceptionSource                    ,
                                                        final UnexpectedPlatformExceptionSeverity unexpectedPlatformExceptionSeverity,
                                                        final Exception                           exception                          ) {

        processException(exceptionSource.name(), unexpectedPlatformExceptionSeverity.name(), exception);

    }

    @Override
    public void reportUnexpectedPluginException(Plugins exceptionSource, UnexpectedPluginExceptionSeverity unexpectedPluginExceptionSeverity, Exception exception) {
        processException(exceptionSource.toString(), unexpectedPluginExceptionSeverity.toString(), exception);
    }

    @Override
    public final void reportUnexpectedPluginException(final Plugins exceptionSource,
                                                      final Platforms platform,
                                                      final UnexpectedPluginExceptionSeverity unexpectedPluginExceptionSeverity,
                                                      final Exception exception,
                                                      final String[] mailTo) {

        String msgException = processException(exceptionSource.toString(), unexpectedPluginExceptionSeverity.toString(), exception);
        if(isErrorReport) sendReport(mailTo, msgException);

    }

    @Override
    public final void reportUnexpectedPluginException(final PluginVersionReference exceptionSource                  ,
                                                      final UnexpectedPluginExceptionSeverity unexpectedPluginExceptionSeverity,
                                                      final Exception                         exception                        ) {

        processException(exceptionSource.toString(), unexpectedPluginExceptionSeverity.toString(), exception);
    }

    @Override
    public final void reportUnexpectedWalletException(final Wallets exceptionSource                  ,
                                                      final UnexpectedWalletExceptionSeverity unexpectedWalletExceptionSeverity,
                                                      final Exception                         exception                        ) {

        processException(exceptionSource.toString(), unexpectedWalletExceptionSeverity.toString(),exception);
    }

    @Override
    public final void reportUnexpectedAddonsException(final Addons exceptionSource                  ,
                                                      final UnexpectedAddonsExceptionSeverity unexpectedAddonsExceptionSeverity,
                                                      final Exception                         exception                        ) {

        processException(exceptionSource.toString(), unexpectedAddonsExceptionSeverity.toString(), exception);
    }

    @Override
    public final void reportUnexpectedAddonsException(final AddonVersionReference exceptionSource                  ,
                                                      final UnexpectedAddonsExceptionSeverity unexpectedPluginExceptionSeverity,
                                                      final Exception                         exception                        ) {

        processException(exceptionSource.toString(), unexpectedPluginExceptionSeverity.toString(), exception);
    }

    @Override
    public final void reportUnexpectedSubAppException(final SubApps exceptionSource                  ,
                                                      final UnexpectedSubAppExceptionSeverity unexpectedSubAppExceptionSeverity,
                                                      final Exception                         exception                        ) {

        processException(exceptionSource.toString(), unexpectedSubAppExceptionSeverity.toString(), exception);
    }

    @Override
    public final void reportUnexpectedUIException(final UISource exceptionSource                  ,
                                                  final UnexpectedUIExceptionSeverity unexpectedAddonsExceptionSeverity,
                                                  final Exception                     exception                        ) {

        processException(exceptionSource.toString(), unexpectedAddonsExceptionSeverity.toString(), exception);
    }

    @Override
    public final void reportUnexpectedEventException(final FermatEvent exceptionSource,
                                                     final Exception   exception      ) {

        processException(exceptionSource.toString(), "Unknow", exception);
    }

    @Override
    public void enabledErrorReport(boolean isErrorReportEnabled) {
        this.isErrorReport = isErrorReportEnabled;
    }

    private String processException(final String source, final String severity, final Exception exception){
        return printErrorReport(source, severity, FermatException.wrapException(exception));
    }

    private String printErrorReport(final String source, final String severity, final FermatException exception){
        String report =  new ErrorReport(source, severity, exception).generateReport();
        System.err.println(report);
        return report;
        //saveToFile(report);

    }


    private void sendReport(String[] mailTo,String body){
        try {
            StringBuilder strToSend = new StringBuilder();
            strToSend.append("Error report\n");
            if(hardwareManager!=null) {
                strToSend.append("Device info:").append("\n")
                        .append("OS: " + hardwareManager.getOperativeSystem().toString()).append("\n")
                        .append("Device: " + hardwareManager.getDevice()).append("\n")
                        .append("Model: " + hardwareManager.getModel()).append("\n")
                        .append("OS Version: " + hardwareManager.getOSVersion()).append("\n");
            }
            strToSend.append("Execption: \n\n" + body);
            gMailSender.sendMail("error report", strToSend.toString(), "fermatmatiasreport@gmail.com", mailTo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveToFile(String report){
        try {
            File file = new File("/data/data/org.fermat/errorReport.txt");

            // if file doesnt exists, then create it
            if (!file.exists()) {
               file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(report);
            bw.close();

            System.out.println("error manager report done");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }






}
