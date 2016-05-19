package test.com.bitduabi.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.database.ArtistActorNetworkServiceDao;

import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.database.ArtistActorNetworkServiceDao;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.exceptions.CantConfirmInformationRequestException;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.exceptions.InformationRequestNotFoundException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.UUID;

import static org.powermock.api.mockito.PowerMockito.doCallRealMethod;

/**
 * Created by gianco on 04/05/16.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(ArtistActorNetworkServiceDao.class)
public class ConfirmInformationRequestTest {

    final UUID requestId = null;

    @Test
    public void confirmInformationRequestTest() throws CantConfirmInformationRequestException, InformationRequestNotFoundException {

        ArtistActorNetworkServiceDao artistActorNetworkServiceDao = PowerMockito.mock(ArtistActorNetworkServiceDao.class);

        doCallRealMethod().when(artistActorNetworkServiceDao).confirmInformationRequest(requestId);

    }

}
