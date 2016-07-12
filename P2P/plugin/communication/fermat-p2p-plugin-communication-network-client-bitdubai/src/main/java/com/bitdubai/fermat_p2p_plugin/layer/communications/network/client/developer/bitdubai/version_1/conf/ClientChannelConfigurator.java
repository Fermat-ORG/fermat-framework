package com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.conf;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.HeadersAttName;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.context.ClientContext;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.context.ClientContextItem;

import org.glassfish.tyrus.ext.extension.deflate.PerMessageDeflateExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.websocket.ClientEndpointConfig;
import javax.websocket.Extension;

/**
 * The Class <code>ClientChannelConfigurator</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 07/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ClientChannelConfigurator extends ClientEndpointConfig.Configurator {

    @Override
    public void beforeRequest(Map<String, List<String>> headers) {

        Object clientIdentity = ClientContext.get(ClientContextItem.CLIENT_IDENTITY);

        if (clientIdentity != null) {
            List<String> values = new ArrayList<>();
            values.add(((ECCKeyPair) clientIdentity).getPublicKey());
            headers.put(HeadersAttName.CPKI_ATT_HEADER_NAME, values);
        }
    }

    public List<Extension> getNegotiatedExtensions(List<Extension> installed, List<Extension> requested) {
        installed.add(new PerMessageDeflateExtension());
        return installed;
    }

}
