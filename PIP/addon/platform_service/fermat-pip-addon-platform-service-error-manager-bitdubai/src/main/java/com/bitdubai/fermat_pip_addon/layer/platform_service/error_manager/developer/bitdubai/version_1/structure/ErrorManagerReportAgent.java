package com.bitdubai.fermat_pip_addon.layer.platform_service.error_manager.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPlatformDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PlatformDatabaseSystem;
import com.bitdubai.fermat_pip_addon.layer.platform_service.error_manager.developer.bitdubai.version_1.exceptions.CantStartAgentException;
import com.bitdubai.fermat_pip_addon.layer.platform_service.error_manager.developer.bitdubai.version_1.interfaces.ConnectivityState;
import com.bitdubai.fermat_pip_addon.layer.platform_service.error_manager.developer.bitdubai.version_1.interfaces.ErrorAgent;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by ciencias on 4/3/15.
 * Modified by Federico Rodriguez on 29.4.15
 */


/**
 * Esta clase lo que hace es leer el arvhivo con las ultimas excepciones de cada Developer, y luego determina si en algun
 * caso hay necesidad de transmitir la informacion al Developer.
 * <p/>
 * Si la hay intenta la transmision y si lo logra elimina la informacion transmitida.
 * <p/>
 * <p/>
 * <p/>
 * * * * * * * * .
 */
public class ErrorManagerReportAgent implements ErrorAgent, DealsWithPlatformDatabaseSystem {

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    private PlatformDatabaseSystem platformDatabaseSystem;


    /**
     * ErrorAgent Member Variables.
     */
    private Thread agentThread;
    private ErrorReportAgent errorReportAgent;
    private ErrorManagerRegistry errorManagerRegistry;

    @Override
    public void start() throws CantStartAgentException {

        this.errorReportAgent = new ErrorReportAgent();

        this.errorReportAgent.setPlatformDatabaseSystem(this.platformDatabaseSystem);

        this.errorReportAgent.initialize();

        this.agentThread = new Thread(new ErrorReportAgent());
        this.agentThread.start();
    }

    @Override
    public void stop() {
        this.agentThread.interrupt();
    }

    @Override
    public void setPlatformDatabaseSystem(PlatformDatabaseSystem platformDatabaseSystem) {
        this.platformDatabaseSystem = platformDatabaseSystem;
    }

    public ErrorManagerRegistry getErrorManagerRegistry() {
        return errorManagerRegistry;
    }

    public void setErrorManagerRegistry(ErrorManagerRegistry errorManagerRegistry) {
        this.errorManagerRegistry = errorManagerRegistry;
    }

    private class ErrorReportAgent implements DealsWithPlatformDatabaseSystem, Runnable, ConnectivityState {

        private static final int SLEEP_TIME = 5000;

        //private ErrorManagerRegistry errorManagerRegistry;

        /*
        public ErrorManagerRegistry getErrorManagerRegistry() {
            return errorManagerRegistry;
        }

        public void setErrorManagerRegistry(ErrorManagerRegistry errorManagerRegistry) {
            this.errorManagerRegistry = errorManagerRegistry;
        }
        */

        /* Deals with the stored Exceptions */
        private List<ErrorManagerRegistry> listErrorManagerRegistryToSend;

        /**
         * DealsWithPluginDatabaseSystem Interface member variables.
         */
        private PlatformDatabaseSystem platformDatabaseSystem;

        /**
         * initialize the Agent.
         */
        private void initialize() {
            /* Update the List of Error Registries not sent */
            loadNotSentErrorRegistries();
        }


        /**
         * DealsWithPlatformDatabaseSystem interface implementation.
         */
        @Override
        public void setPlatformDatabaseSystem(PlatformDatabaseSystem platformDatabaseSystem) {
            this.platformDatabaseSystem = platformDatabaseSystem;
        }

        public PlatformDatabaseSystem getPlatformDatabaseSystem() {
            return this.platformDatabaseSystem;
        }

        /**
         * Runnable Interface implementation.
         */
        @Override
        public void run() {

            /**
             * Infinite loop.
             */
            while (true) {

                /**
                 * Sleep for a while.
                 */
                try {
                    Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException interruptedException) {
                    cleanResources();
                    return;
                }

                /**
                 * Now I do the main task.
                 */
                doTheMainTask();

                /**
                 * Check if I have been Interrupted.
                 */
                if (Thread.currentThread().isInterrupted()) {
                    cleanResources();
                    return;
                }
            }

        }

        private void doTheMainTask() {

            /* Update the List of Error Registries not sent */
            loadNotSentErrorRegistries();

            /* I check if WiFI connection is enabled and if the list of items to send has at least one item */
            if (wifiConnectionEnabled() && !listErrorManagerRegistryToSend.isEmpty()) {

                for (ErrorManagerRegistry emr : listErrorManagerRegistryToSend) {

                    if (sendExceptionToServer(emr)) {
                        emr.markErrorRegistryAsSent();
                    }

                }

                /* After all was sent the Agent goes to sleep for a while */
                try {
                    Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }


        private void loadNotSentErrorRegistries() {
            //Load the ErrorManagerRegistry list of objects not sent
            listErrorManagerRegistryToSend = errorManagerRegistry.getListOfErrorRegistryNotSent();
        }

        private boolean sendExceptionToServer(ErrorManagerRegistry emr) {
            /*
            * Construction of the URL for the POST
            */
            String url = "http://www.fermatwallet.com/devExceptionReport?";
            String parameters = "COMPONENT_TYPE=" + emr.getComponentType() + "&";
            parameters = parameters.concat("COMPONENT_NAME=" + emr.getComponentName() + " & ");
            parameters = parameters.concat("SEVERITY=" + emr.getSeverity() + " & ");
            parameters = parameters.concat("MESSAGE=" + emr.getExceptionMessage() + " &         ");
            parameters = parameters.concat("TIMESTAMP=" + String.valueOf(emr.getTimeStampMillis()));
            /**
             * Sends the exception to the server using an URL
             */
            boolean success = false;
            try {

                URL obj = new URL(url);
                HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

                //THE HEADER OF THE REQUEST
                con.setRequestMethod("POST");
                con.setRequestProperty("User-Agent", "Mozilla/5.0");
                con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

                String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";

                //POST REQUEST SEND
                con.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(parameters);
                wr.flush();
                wr.close();

                int responseCode = con.getResponseCode();
                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + parameters);
                System.out.println("Response Code : " + responseCode);

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream(), "UTF-8"));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                success = true;
            } catch (MalformedURLException e) {
                System.out.println("URL Error: " + e.getMessage());
            } catch (ProtocolException e) {
                System.out.println("Protocol Error: " + e.getMessage());
            } catch (IOException e) {
                System.out.println("Input-Output Error: " + e.getMessage());
            }
            return success;
        }


        private void cleanResources() {
            this.listErrorManagerRegistryToSend = null;
        }

        @Override
        public boolean wifiConnectionEnabled() {
            return false;
        }

    }
}
