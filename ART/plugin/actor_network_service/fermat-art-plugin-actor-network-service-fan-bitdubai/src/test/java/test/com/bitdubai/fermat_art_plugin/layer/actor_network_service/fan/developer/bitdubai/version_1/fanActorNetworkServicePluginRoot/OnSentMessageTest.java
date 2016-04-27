package test.com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.fanActorNetworkServicePluginRoot;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.FanActorNetworkServicePluginRoot;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.enums.MessageTypes;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.exceptions.CantHandleNewMessagesException;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.messages.InformationMessage;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.messages.NetworkServiceMessage;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.messages.RequestMessage;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.interfaces.NetworkService;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageType;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.MessagesTypes;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.sun.org.apache.bcel.internal.classfile.Code;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by gianco on 25/04/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class OnSentMessageTest{

    private NetworkServiceMessage mockNetworkServiceMessage;
    private FanActorNetworkServicePluginRoot mockFanActorNetworkServicePluginRoot;
    private OnSentMessageTest mockOnSentMessageTest;
    private FermatMessage mockFermatMessage;
    private String mockCode;
    private MessageTypes mockMessageTypes;

    @Before
    public void beforeEachTest() {
        mockFanActorNetworkServicePluginRoot = Mockito.mock(FanActorNetworkServicePluginRoot.class);
    }

    @Test
    public void onSentMessageTest() throws InvalidParameterException {

        System.out.print("no da errores");

        assertThat(mockFanActorNetworkServicePluginRoot).isNotNull();


    }
}
