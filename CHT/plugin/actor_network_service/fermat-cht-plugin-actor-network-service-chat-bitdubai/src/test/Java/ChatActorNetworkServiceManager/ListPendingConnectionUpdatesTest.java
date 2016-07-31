package ChatActorNetworkServiceManager;

import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.CantListPendingConnectionRequestsException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.utils.ChatConnectionRequest;
import com.bitdubai.fermat_cht_plugin.layer.actor_network_service.chat.developer.bitdubai.version_1.structure.ChatActorNetworkServiceManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Miguel on 4/14/16.
 */

@RunWith(MockitoJUnitRunner.class)
public class ListPendingConnectionUpdatesTest {

    @Test
    public void listPendingConnecionUpdates() throws CantListPendingConnectionRequestsException {
        ChatActorNetworkServiceManager chatActorNetworkServiceManager = mock(ChatActorNetworkServiceManager.class);

        when(chatActorNetworkServiceManager.listPendingConnectionUpdates()).thenReturn(new List<ChatConnectionRequest>() {
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
            public Iterator<ChatConnectionRequest> iterator() {
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
            public boolean add(ChatConnectionRequest chatConnectionRequest) {
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
            public boolean addAll(Collection<? extends ChatConnectionRequest> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, Collection<? extends ChatConnectionRequest> c) {
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
            public ChatConnectionRequest get(int index) {
                return null;
            }

            @Override
            public ChatConnectionRequest set(int index, ChatConnectionRequest element) {
                return null;
            }

            @Override
            public void add(int index, ChatConnectionRequest element) {

            }

            @Override
            public ChatConnectionRequest remove(int index) {
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
            public ListIterator<ChatConnectionRequest> listIterator() {
                return null;
            }

            @Override
            public ListIterator<ChatConnectionRequest> listIterator(int index) {
                return null;
            }

            @Override
            public List<ChatConnectionRequest> subList(int fromIndex, int toIndex) {
                return null;
            }
        }).thenCallRealMethod();
        assertThat(chatActorNetworkServiceManager.listPendingConnectionUpdates()).isNotNull();
    }

}
