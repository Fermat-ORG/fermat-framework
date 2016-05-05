package test.com.bitduabi.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.structure.artistActorNetworkServiceManager;

import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantListPendingConnectionRequestsException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.util.ArtistConnectionRequest;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.structure.ArtistActorNetworkServiceManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.doCallRealMethod;

/**
 * Created by gianco on 04/05/16.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(ArtistActorNetworkServiceManager.class)
public class ListCompletedConnectionsTest {

    @Test
    public void listCompletedConnectionsTest() throws CantListPendingConnectionRequestsException {

        ArtistActorNetworkServiceManager artistActorNetworkServiceManager = PowerMockito.mock(ArtistActorNetworkServiceManager.class);

        final List<ArtistConnectionRequest> artistConnectionRequest = new List<ArtistConnectionRequest>() {
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
            public Iterator<ArtistConnectionRequest> iterator() {
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
            public boolean add(ArtistConnectionRequest artistConnectionRequest) {
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
            public boolean addAll(Collection<? extends ArtistConnectionRequest> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, Collection<? extends ArtistConnectionRequest> c) {
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
            public ArtistConnectionRequest get(int index) {
                return null;
            }

            @Override
            public ArtistConnectionRequest set(int index, ArtistConnectionRequest element) {
                return null;
            }

            @Override
            public void add(int index, ArtistConnectionRequest element) {

            }

            @Override
            public ArtistConnectionRequest remove(int index) {
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
            public ListIterator<ArtistConnectionRequest> listIterator() {
                return null;
            }

            @Override
            public ListIterator<ArtistConnectionRequest> listIterator(int index) {
                return null;
            }

            @Override
            public List<ArtistConnectionRequest> subList(int fromIndex, int toIndex) {
                return null;
            }
        };

        when(artistActorNetworkServiceManager.listCompletedConnections()).thenReturn(artistConnectionRequest);

    }

}
