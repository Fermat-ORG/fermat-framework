package test.com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.database.fanActorNetworkServiceDeveloperDatabaseFactory;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.database.FanActorNetworkServiceDeveloperDatabaseFactory;
import com.bitdubai.fermat_art_plugin.layer.actor_network_service.fan.developer.bitdubai.version_1.exceptions.CantInitializeDatabaseException;

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
 * Created by gianco on 02/05/16.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(FanActorNetworkServiceDeveloperDatabaseFactory.class)
public class GetDatabaseListTest {

    final DeveloperObjectFactory developerObjectFactory = null;

    @Test
    public void getDatabaseListTest() {

        FanActorNetworkServiceDeveloperDatabaseFactory fanActorNetworkServiceDeveloperDatabaseFactory = PowerMockito.mock(FanActorNetworkServiceDeveloperDatabaseFactory.class);
        List<DeveloperDatabase> DeveloperDatabase = new List<DeveloperDatabase>() {
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
            public Iterator<DeveloperDatabase> iterator() {
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
            public boolean add(DeveloperDatabase developerDatabase) {
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
            public boolean addAll(Collection<? extends DeveloperDatabase> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, Collection<? extends DeveloperDatabase> c) {
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
            public DeveloperDatabase get(int index) {
                return null;
            }

            @Override
            public DeveloperDatabase set(int index, DeveloperDatabase element) {
                return null;
            }

            @Override
            public void add(int index, DeveloperDatabase element) {

            }

            @Override
            public DeveloperDatabase remove(int index) {
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
            public ListIterator<DeveloperDatabase> listIterator() {
                return null;
            }

            @Override
            public ListIterator<DeveloperDatabase> listIterator(int index) {
                return null;
            }

            @Override
            public List<DeveloperDatabase> subList(int fromIndex, int toIndex) {
                return null;
            }
        };
        when(fanActorNetworkServiceDeveloperDatabaseFactory.getDatabaseList(developerObjectFactory)).thenReturn(DeveloperDatabase);
    }
}
