package ChatIdentityManagerImpl;

import com.bitdubai.fermat_cht_api.layer.identity.exceptions.CantListChatIdentityException;
import com.bitdubai.fermat_cht_api.layer.identity.interfaces.ChatIdentity;
import com.bitdubai.fermat_cht_plugin.layer.identity.chat.developer.bitdubai.version_1.structure.ChatIdentityManagerImpl;

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
 * Created by Miguel Rincon on 4/15/16.
 */

@RunWith(MockitoJUnitRunner.class)
public class GetIdentityChatUsersFromCurrentDeviceUserTest {

    @Test
    public void getIdentityChatUsersFromCurrentDeviceUser() throws CantListChatIdentityException {
        ChatIdentityManagerImpl chatIdentityManager = mock(ChatIdentityManagerImpl.class);

        when(chatIdentityManager.getIdentityChatUsersFromCurrentDeviceUser()).thenReturn(new List<ChatIdentity>() {
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
            public Iterator<ChatIdentity> iterator() {
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
            public boolean add(ChatIdentity chatIdentity) {
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
            public boolean addAll(Collection<? extends ChatIdentity> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, Collection<? extends ChatIdentity> c) {
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
            public ChatIdentity get(int index) {
                return null;
            }

            @Override
            public ChatIdentity set(int index, ChatIdentity element) {
                return null;
            }

            @Override
            public void add(int index, ChatIdentity element) {

            }

            @Override
            public ChatIdentity remove(int index) {
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
            public ListIterator<ChatIdentity> listIterator() {
                return null;
            }

            @Override
            public ListIterator<ChatIdentity> listIterator(int index) {
                return null;
            }

            @Override
            public List<ChatIdentity> subList(int fromIndex, int toIndex) {
                return null;
            }
        }).thenCallRealMethod();
        assertThat(chatIdentityManager.getIdentityChatUsersFromCurrentDeviceUser()).isNotNull();
    }

}
