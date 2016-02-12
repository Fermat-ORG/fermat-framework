package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1;

import junit.framework.TestCase;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.client.developer.bitdubai.version_1.WsCommunicationsCloudClientChannelManagerChannelPluginRootTest</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 02/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WsCommunicationsCloudClientChannelManagerChannelPluginRootTest extends TestCase {

    public void testStart() throws Exception {

        WsCommunicationsCloudClientPluginRoot wsCommunicationsCloudClientPluginRoot = new WsCommunicationsCloudClientPluginRoot();
        wsCommunicationsCloudClientPluginRoot.start();
    }
}