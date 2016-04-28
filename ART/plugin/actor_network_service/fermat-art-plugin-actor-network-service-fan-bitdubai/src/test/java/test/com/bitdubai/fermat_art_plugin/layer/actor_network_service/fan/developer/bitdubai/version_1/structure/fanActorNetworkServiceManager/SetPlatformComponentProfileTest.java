package test.com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.structure.fanActorNetworkServiceManager;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantExposeIdentityException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.fan.util.FanExposingData;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.structure.FanActorNetworkServiceManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.powermock.api.mockito.PowerMockito.doCallRealMethod;

/**
 * Created by gianco on 27/04/16.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(FanActorNetworkServiceManager.class)
public class SetPlatformComponentProfileTest {

    @Test
    public void setPlatformComponentProfileTest (){

        FanActorNetworkServiceManager fanActorNetworkServiceManager = PowerMockito.mock(FanActorNetworkServiceManager.class);
        final PlatformComponentProfile platformComponentProfile = new PlatformComponentProfile() {
            @Override
            public String getIdentityPublicKey() {
                return null;
            }

            @Override
            public String getAlias() {
                return null;
            }

            @Override
            public String getName() {
                return null;
            }

            @Override
            public Location getLocation() {
                return null;
            }

            @Override
            public PlatformComponentType getPlatformComponentType() {
                return null;
            }

            @Override
            public NetworkServiceType getNetworkServiceType() {
                return null;
            }

            @Override
            public String getCommunicationCloudClientIdentity() {
                return null;
            }

            @Override
            public String getExtraData() {
                return null;
            }

            @Override
            public String toJson() {
                return null;
            }

            @Override
            public PlatformComponentProfile fromJson(String json) {
                return null;
            }
        };

        doCallRealMethod().when(fanActorNetworkServiceManager).setPlatformComponentProfile(platformComponentProfile);

    }

}
