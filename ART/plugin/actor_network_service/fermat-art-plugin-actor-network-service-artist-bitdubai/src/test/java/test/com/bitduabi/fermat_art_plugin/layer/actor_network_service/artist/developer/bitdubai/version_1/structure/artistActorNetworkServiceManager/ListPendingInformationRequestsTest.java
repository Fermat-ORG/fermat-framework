package test.com.bitduabi.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.structure.artistActorNetworkServiceManager;

import com.bitdubai.fermat_art_api.layer.actor_network_service.enums.RequestType;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantListPendingInformationRequestsException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.ArtArtistExtraData;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.util.ArtistExternalPlatformInformation;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.structure.ArtistActorNetworkServiceManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static org.mockito.Mockito.when;

/**
 * Created by gianco on 04/05/16.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(ArtistActorNetworkServiceManager.class)
public class ListPendingInformationRequestsTest {

    @Test
    public void listPendingInformationRequestsTest() throws CantListPendingInformationRequestsException {

        ArtistActorNetworkServiceManager artistActorNetworkServiceManager = PowerMockito.mock(ArtistActorNetworkServiceManager.class);

        RequestType requestType = null;

        List<ArtArtistExtraData<ArtistExternalPlatformInformation>> artistExternalData = new List<ArtArtistExtraData<ArtistExternalPlatformInformation>>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @Override
            public Iterator<ArtArtistExtraData<ArtistExternalPlatformInformation>> iterator() {
                return null;
            }

            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @Override
            public <T> T[] toArray(T[] a) {
                return null;
            }

            @Override
            public boolean add(ArtArtistExtraData<ArtistExternalPlatformInformation> artistExternalPlatformInformationArtArtistExtraData) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends ArtArtistExtraData<ArtistExternalPlatformInformation>> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, Collection<? extends ArtArtistExtraData<ArtistExternalPlatformInformation>> c) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public ArtArtistExtraData<ArtistExternalPlatformInformation> get(int index) {
                return null;
            }

            @Override
            public ArtArtistExtraData<ArtistExternalPlatformInformation> set(int index, ArtArtistExtraData<ArtistExternalPlatformInformation> element) {
                return null;
            }

            @Override
            public void add(int index, ArtArtistExtraData<ArtistExternalPlatformInformation> element) {

            }

            @Override
            public ArtArtistExtraData<ArtistExternalPlatformInformation> remove(int index) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @Override
            public ListIterator<ArtArtistExtraData<ArtistExternalPlatformInformation>> listIterator() {
                return null;
            }

            @Override
            public ListIterator<ArtArtistExtraData<ArtistExternalPlatformInformation>> listIterator(int index) {
                return null;
            }

            @Override
            public List<ArtArtistExtraData<ArtistExternalPlatformInformation>> subList(int fromIndex, int toIndex) {
                return null;
            }
        };

        when(artistActorNetworkServiceManager.listPendingInformationRequests(requestType)).thenReturn(artistExternalData);

    }

}
