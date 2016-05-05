package test.com.bitduabi.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.structure.artistActorNetworkServiceSearch;

import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantListArtistsException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.artist.util.ArtistExposingData;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.artist.developer.bitdubai.version_1.structure.ArtistActorNetworkServiceSearch;

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

/**
 * Created by gianco on 04/05/16.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(ArtistActorNetworkServiceSearch.class)
public class GetResultTest {

    @Test
    public void getResultTest() throws CantListArtistsException {

        ArtistActorNetworkServiceSearch artistActorNetworkServiceSearch = PowerMockito.mock(ArtistActorNetworkServiceSearch.class);

        List<ArtistExposingData> artistExposingData = new List<ArtistExposingData>() {
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
            public Iterator<ArtistExposingData> iterator() {
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
            public boolean add(ArtistExposingData artistExposingData) {
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
            public boolean addAll(Collection<? extends ArtistExposingData> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, Collection<? extends ArtistExposingData> c) {
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
            public boolean equals(Object o) {
                return false;
            }

            @Override
            public int hashCode() {
                return 0;
            }

            @Override
            public ArtistExposingData get(int index) {
                return null;
            }

            @Override
            public ArtistExposingData set(int index, ArtistExposingData element) {
                return null;
            }

            @Override
            public void add(int index, ArtistExposingData element) {

            }

            @Override
            public ArtistExposingData remove(int index) {
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
            public ListIterator<ArtistExposingData> listIterator() {
                return null;
            }

            @Override
            public ListIterator<ArtistExposingData> listIterator(int index) {
                return null;
            }

            @Override
            public List<ArtistExposingData> subList(int fromIndex, int toIndex) {
                return null;
            }
        };

        when(artistActorNetworkServiceSearch.getResult()).thenReturn(artistExposingData);


    }

    @Test
    public void resultTest() throws CantListArtistsException {

        ArtistActorNetworkServiceSearch artistActorNetworkServiceSearch = PowerMockito.mock(ArtistActorNetworkServiceSearch.class);

        PlatformComponentType actorTypeToLookFor = null;
        List<ArtistExposingData> artistExposingData = new List<ArtistExposingData>() {
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
            public Iterator<ArtistExposingData> iterator() {
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
            public boolean add(ArtistExposingData artistExposingData) {
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
            public boolean addAll(Collection<? extends ArtistExposingData> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, Collection<? extends ArtistExposingData> c) {
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
            public boolean equals(Object o) {
                return false;
            }

            @Override
            public int hashCode() {
                return 0;
            }

            @Override
            public ArtistExposingData get(int index) {
                return null;
            }

            @Override
            public ArtistExposingData set(int index, ArtistExposingData element) {
                return null;
            }

            @Override
            public void add(int index, ArtistExposingData element) {

            }

            @Override
            public ArtistExposingData remove(int index) {
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
            public ListIterator<ArtistExposingData> listIterator() {
                return null;
            }

            @Override
            public ListIterator<ArtistExposingData> listIterator(int index) {
                return null;
            }

            @Override
            public List<ArtistExposingData> subList(int fromIndex, int toIndex) {
                return null;
            }
        };

        when(artistActorNetworkServiceSearch.getResult(actorTypeToLookFor)).thenReturn(artistExposingData);


    }

}
