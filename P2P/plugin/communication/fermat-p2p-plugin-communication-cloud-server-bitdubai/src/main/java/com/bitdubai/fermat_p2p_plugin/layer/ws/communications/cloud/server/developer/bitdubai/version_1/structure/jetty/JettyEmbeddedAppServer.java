/*
 * @#JettyEmbeddedAppServer.java - 2016
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty;

import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.WsCommunicationsServerCloudMainRunner;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.vpn.VpnWebSocketServlet;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.vpn.WebSocketVpnServerChannel;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.webservices.ApplicationResources;

import org.apache.commons.lang.ClassUtils;
import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;
import org.jboss.resteasy.plugins.server.servlet.HttpServlet30Dispatcher;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.websocket.DeploymentException;
import javax.websocket.server.ServerContainer;
import javax.websocket.server.ServerEndpointConfig;

/**
 * The class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.JettyEmbeddedAppServer</code>
 * is the application web server to deploy the web socket server</p>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 08/01/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class JettyEmbeddedAppServer {

    /**
     * Represent the logger instance
     */
    private static Logger LOG = Logger.getLogger(ClassUtils.getShortClassName(JettyEmbeddedAppServer.class));

    /**
     * Represent the DEFAULT_PORT value (9090)
     */
    public static final int DEFAULT_PORT = 9090;

    /**
     * Represent the DEFAULT_CONTEXT_PATH value (/fermat)
     */
    public static final String DEFAULT_CONTEXT_PATH = "/fermat";

    /**
     * Represent the JettyEmbeddedAppServer instance
     */
    private static JettyEmbeddedAppServer instance;

    /**
     * Represent the server instance
     */
    private Server server;

    /**
     * Represent the web socket server container instance
     */
    private ServerContainer wsServerContainer;

    /**
     * Represent the ServletContextHandler instance
     */
    private ServletContextHandler servletContextHandler;

    /**
     * Represent the ServerConnector instance
     */
    private ServerConnector serverConnector;

    /**
     * Constructor
     */
    private JettyEmbeddedAppServer(){
        super();
    }

    /**
     * Initialize and configure the server instance
     *
     * @throws IOException
     * @throws DeploymentException
     * @throws ServletException
     */
    private void initialize() throws IOException, DeploymentException, ServletException {

        LOG.info("Initializing the internal Server");

        /*
         * Create and configure the server
         */
        this.server = new Server();
        this.serverConnector = new ServerConnector(server);
        this.serverConnector.setPort(JettyEmbeddedAppServer.DEFAULT_PORT);
        this.server.addConnector(serverConnector);

        /*
         * Setup the basic application "context" for this application at "/fermat"
         */
        this.servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        this.servletContextHandler.setContextPath(JettyEmbeddedAppServer.DEFAULT_CONTEXT_PATH);
        this.servletContextHandler.setClassLoader(JettyEmbeddedAppServer.class.getClassLoader());
        this.server.setHandler(servletContextHandler);

        /*
         * Initialize restful service layer
         */
        ServletHolder restfulServiceServletHolder = new ServletHolder(new HttpServlet30Dispatcher());
        restfulServiceServletHolder.setInitParameter("javax.ws.rs.Application", ApplicationResources.class.getName());
        restfulServiceServletHolder.setInitParameter("resteasy.use.builtin.providers", "true");
        servletContextHandler.addServlet(restfulServiceServletHolder, "/*");

        /*
         * Initialize javax.websocket layer
         */
        this.wsServerContainer = WebSocketServerContainerInitializer.configureContext(servletContextHandler);

        /*
         * Add WebSocket endpoint to javax.websocket layer
         */
        this.wsServerContainer.addEndpoint(WebSocketCloudServerChannel.class);
        this.wsServerContainer.addEndpoint(WebSocketVpnServerChannel.class);


        //this.server.dump(System.err);

    }

    /**
     * Start the server instance
     *
     * @throws Exception
     */
    public void start() throws Exception {

        this.initialize();
        LOG.info("Starting the internal server");
        this.server.start();

        LOG.info("Server URI = " + this.server.getURI());
        this.server.join();

    }

    /**
     * Deploy a new vpn web socket
     *
     * @return path
     */
    @Deprecated
    public String deployNewVpnWebSocket() throws Exception {

        // Add a websocket to a specific path spec
        String id = UUID.randomUUID().toString();
        String path =  "/" + id + "/*";
        System.out.println("Deploy a new ws path = " + path);
        ServletHolder servletHolder = new ServletHolder("vpn_"+id, VpnWebSocketServlet.class);
        servletHolder.setInitOrder(1);
        instance.servletContextHandler.addServlet(servletHolder, path);
        return path;
    }

    /**
     * Deploy a new vpn web socket
     *
     * @return path
     */
    @Deprecated
    public String deployNewJavaxVpnWebSocket() throws Exception {

        // Add a websocket to a specific path spec
        String id = UUID.randomUUID().toString();
        String path = "/" + id + "/vpn/";
        System.out.println("Deploy a new ws path = " + path);
        ServerEndpointConfig serverEndpointConfig = ServerEndpointConfig.Builder.create(WebSocketVpnServerChannel.class, path).build();
        instance.wsServerContainer.addEndpoint(serverEndpointConfig);

        return path;
    }


    /**
     * Get the instance value
     *
     * @return instance current value
     */
    public static JettyEmbeddedAppServer getInstance() {

        if (instance == null){
            instance = new JettyEmbeddedAppServer();
        }

        return instance;
    }

    /**
     * Get the server value
     *
     * @return server current value
     */
    public Server getServer() {
        return server;
    }




    public static void main(String[] args)
    {
        try {

            JettyEmbeddedAppServer.getInstance().start();

         /*   JettyEmbeddedAppServer.getInstance().deployNewJavaxVpnWebSocket();

            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        JettyEmbeddedAppServer.getInstance().deployNewJavaxVpnWebSocket();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, 5000);

            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        JettyEmbeddedAppServer.getInstance().deployNewJavaxVpnWebSocket();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, 10000);


            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    try {

                        org.eclipse.jetty.util.Attributes attributes = JettyEmbeddedAppServer.getInstance().servletContextHandler.getAttributes();

                        Enumeration<String> enumeration = attributes.getAttributeNames();
                        while (enumeration.hasMoreElements()){
                            System.out.println("name = " +enumeration.nextElement());
                        }

                        ServletMapping[] servletMappings = instance.servletContextHandler.getServletHandler().getServletMappings();

                        for (int j = 0; j < servletMappings.length; j++) {
                            ServletMapping servletMapping = servletMappings[j];
                            System.out.println("servletMapping = " + servletMapping.getPathSpecs());
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, 15000);



           new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    try {

                       System.out.println(" ------------------------------------------------------- ");
                       ServletMapping[] servletMappings2 = JettyEmbeddedAppServer.getInstance().servletContextHandler.getServletHandler().getServletMappings();
                       String vpn = servletMappings2[1].getServletName();

                       System.out.println("vpn = " + vpn);
                       ServletHolder servletHolder =  JettyEmbeddedAppServer.getInstance().servletContextHandler.getServletHandler().getServlet(vpn);
                       System.out.println("getContextPath = " + servletHolder.getContextPath());
                       VpnWebSocketServlet wpnWebSocketServlet = (VpnWebSocketServlet) servletHolder.getServlet();
                       System.out.println("getPrivateKey = " + wpnWebSocketServlet.getVpnInstance().getVpnServerIdentity().getPrivateKey());

                       servletHolder.getServlet().destroy();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, 15000); */

            //JettyEmbeddedAppServer.getInstance().getServer().join();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
