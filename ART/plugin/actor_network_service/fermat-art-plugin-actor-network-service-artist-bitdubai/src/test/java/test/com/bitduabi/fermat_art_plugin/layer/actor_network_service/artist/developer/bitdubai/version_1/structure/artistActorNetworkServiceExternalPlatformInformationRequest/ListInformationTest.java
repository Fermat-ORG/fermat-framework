package test.com.bitduabi.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.structure.artistActorNetworkServiceExternalPlatformInformationRequest;

import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.util.ArtistExternalPlatformInformation;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.structure.ArtistActorNetworkServiceExternalPlatformInformationRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static org.mockito.Mockito.when;

/**
 * Created by gianco on 03/05/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class ListInformationTest {


    @Test
    public void listInformationTest() {
        ArtistActorNetworkServiceExternalPlatformInformationRequest artistActorNetworkServiceExternalPlatformInformationRequest = PowerMockito.mock(ArtistActorNetworkServiceExternalPlatformInformationRequest.class);

        List<ArtistExternalPlatformInformation> artistExternalPlatformInformation = new List<ArtistExternalPlatformInformation>() {
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
            public Iterator<ArtistExternalPlatformInformation> iterator() {
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
            public boolean add(ArtistExternalPlatformInformation artistExternalPlatformInformation) {
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
            public boolean addAll(Collection<? extends ArtistExternalPlatformInformation> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, Collection<? extends ArtistExternalPlatformInformation> c) {
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
            public ArtistExternalPlatformInformation get(int index) {
                return null;
            }

            @Override
            public ArtistExternalPlatformInformation set(int index, ArtistExternalPlatformInformation element) {
                return null;
            }

            @Override
            public void add(int index, ArtistExternalPlatformInformation element) {

            }

            @Override
            public ArtistExternalPlatformInformation remove(int index) {
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
            public ListIterator<ArtistExternalPlatformInformation> listIterator() {
                return null;
            }

            @Override
            public ListIterator<ArtistExternalPlatformInformation> listIterator(int index) {
                return null;
            }

            @Override
            public List<ArtistExternalPlatformInformation> subList(int fromIndex, int toIndex) {
                return null;
            }
        };

        when(artistActorNetworkServiceExternalPlatformInformationRequest.listInformation()).thenReturn(artistExternalPlatformInformation);

    }
}
