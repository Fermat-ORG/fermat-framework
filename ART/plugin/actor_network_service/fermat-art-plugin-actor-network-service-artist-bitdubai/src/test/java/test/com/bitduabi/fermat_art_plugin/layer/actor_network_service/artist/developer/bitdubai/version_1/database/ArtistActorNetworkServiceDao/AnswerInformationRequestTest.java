package test.com.bitduabi.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.database.ArtistActorNetworkServiceDao;

import com.bitdubai.fermat_art_api.layer.actor_network_service.enums.ProtocolState;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.util.ArtistExternalPlatformInformation;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.database.ArtistActorNetworkServiceDao;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.exceptions.CantAnswerInformationRequestException;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.exceptions.CantFindRequestException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;
import java.util.UUID;

import static org.powermock.api.mockito.PowerMockito.doCallRealMethod;

/**
 * Created by gianco on 04/05/16.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(ArtistActorNetworkServiceDao.class)
public class AnswerInformationRequestTest {

    final UUID requestId = null;
    final long updateTime = 0;
    final List<ArtistExternalPlatformInformation> informationList = null;
    final ProtocolState state = null;

    @Test
    public void answerInformationRequestTest() throws CantAnswerInformationRequestException, CantFindRequestException {

        ArtistActorNetworkServiceDao artistActorNetworkServiceDao = PowerMockito.mock(ArtistActorNetworkServiceDao.class);

        doCallRealMethod().when(artistActorNetworkServiceDao).answerInformationRequest(requestId,
                                                                                        updateTime,
                                                                                        informationList,
                                                                                        state);
    }

}
