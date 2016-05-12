package test.com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.structure.fanActorNetworkServiceManager;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantExposeIdentityException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantRequestConnectionException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.fan.util.FanConnectionInformation;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.fan.util.FanExposingData;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.structure.FanActorNetworkServiceManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.UUID;

import static org.powermock.api.mockito.PowerMockito.doCallRealMethod;

/**
 * Created by gianco on 27/04/16.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(FanActorNetworkServiceManager.class)
public class RequestConnectionTest {

//    @Mock
    private UUID   connectionId        ;
//    @Mock
    private String senderPublicKey     ;
//    @Mock
    private PlatformComponentType senderActorType     ;
//    @Mock
    private String senderAlias         ;
//    @Mock
    private byte[] senderImage         ;
//    @Mock
    private String destinationPublicKey;
//    @Mock
    private PlatformComponentType destinationActorType;
//    @Mock
    private long   sendingTime         ;



    @Test
    public void requestConnectionTest () throws CantRequestConnectionException {

        FanActorNetworkServiceManager fanActorNetworkServiceManager = PowerMockito.mock(FanActorNetworkServiceManager.class);
        final FanConnectionInformation fanConnectionInformation = new FanConnectionInformation(connectionId,
                                                                                                senderPublicKey,
                                                                                                senderActorType,
                                                                                                senderAlias,
                                                                                                senderImage,
                                                                                                destinationPublicKey,
                                                                                                destinationActorType,
                                                                                                sendingTime);

        doCallRealMethod().when(fanActorNetworkServiceManager).requestConnection(fanConnectionInformation);

    }
}
