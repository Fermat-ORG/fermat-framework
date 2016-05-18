package test.com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.tokenlyArtistIdentityPluginRoot;

import com.bitdubai.fermat_tky_api.layer.identity.artist.exceptions.CantListArtistIdentitiesException;
import com.bitdubai.fermat_tky_api.layer.identity.artist.interfaces.Artist;
import com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.TokenlyArtistIdentityPluginRoot;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static org.mockito.Mockito.when;

/**
 * Created by gianco on 09/05/16.
 */
public class ListIdentitiesFromCurrentDeviceUserTest {


    @Test
    public void listIdentitiesFromCurrentDeviceUserTest() throws CantListArtistIdentitiesException {
        TokenlyArtistIdentityPluginRoot tokenlyArtistIdentityPluginRoot = Mockito.mock(TokenlyArtistIdentityPluginRoot.class);

        List<Artist> artists = new List<Artist>() {
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
            public Iterator<Artist> iterator() {
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
            public boolean add(Artist artist) {
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
            public boolean addAll(Collection<? extends Artist> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, Collection<? extends Artist> c) {
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
            public Artist get(int index) {
                return null;
            }

            @Override
            public Artist set(int index, Artist element) {
                return null;
            }

            @Override
            public void add(int index, Artist element) {

            }

            @Override
            public Artist remove(int index) {
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
            public ListIterator<Artist> listIterator() {
                return null;
            }

            @Override
            public ListIterator<Artist> listIterator(int index) {
                return null;
            }

            @Override
            public List<Artist> subList(int fromIndex, int toIndex) {
                return null;
            }
        };

        when(tokenlyArtistIdentityPluginRoot.listIdentitiesFromCurrentDeviceUser()).thenReturn(artists);

    }

}
