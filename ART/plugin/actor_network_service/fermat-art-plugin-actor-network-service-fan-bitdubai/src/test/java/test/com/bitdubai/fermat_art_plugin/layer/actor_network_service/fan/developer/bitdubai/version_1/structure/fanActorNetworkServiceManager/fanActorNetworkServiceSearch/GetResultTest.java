package test.com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.structure.fanActorNetworkServiceManager.fanActorNetworkServiceSearch;

import com.bitdubai.fermat_art_api.layer.actor_network_service.exceptions.CantListArtistsException;
import com.bitdubai.fermat_art_api.layer.actor_network_service.interfaces.fan.util.FanExposingData;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.structure.FanActorNetworkServiceSearch;

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
 * Created by gianco on 27/04/16.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(FanActorNetworkServiceSearch.class)
public class GetResultTest {

    @Test
    public void getResultTest() throws CantListArtistsException {

        FanActorNetworkServiceSearch fanActorNetworkServiceSearch = PowerMockito.mock(FanActorNetworkServiceSearch.class);

        final List<FanExposingData> fanExposingData = new List<FanExposingData>() {
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
            public Iterator<FanExposingData> iterator() {
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
            public boolean add(FanExposingData fanExposingData) {
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
            public boolean addAll(Collection<? extends FanExposingData> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, Collection<? extends FanExposingData> c) {
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
            public FanExposingData get(int index) {
                return null;
            }

            @Override
            public FanExposingData set(int index, FanExposingData element) {
                return null;
            }

            @Override
            public void add(int index, FanExposingData element) {

            }

            @Override
            public FanExposingData remove(int index) {
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
            public ListIterator<FanExposingData> listIterator() {
                return null;
            }

            @Override
            public ListIterator<FanExposingData> listIterator(int index) {
                return null;
            }

            @Override
            public List<FanExposingData> subList(int fromIndex, int toIndex) {
                return null;
            }
        };

        when(fanActorNetworkServiceSearch.getResult()).thenReturn(fanExposingData);
    }
}
