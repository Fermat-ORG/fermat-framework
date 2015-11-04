package unit.com.bitdubai.sub_app.crypto_broker_identity.util;

import android.app.Activity;
import android.widget.Filter;

import com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityInformation;
import com.bitdubai.sub_app.crypto_broker_identity.BuildConfig;
import com.bitdubai.sub_app.crypto_broker_identity.common.adapters.CryptoBrokerIdentityInfoAdapter;
import com.bitdubai.sub_app.crypto_broker_identity.util.CryptoBrokerIdentityListFilter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

import unit.com.bitdubai.sub_app.crypto_broker_identity.TestCryptoBrokerIdentityInformation;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.filter;

/**
 * Created by nelson on 17/10/15.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class CryptoBrokerIdentityListFilterTest {

    private CryptoBrokerIdentityInfoAdapter adapter;

    @Before
    public void setUp() {
        Activity activity = Robolectric.setupActivity(Activity.class);
        adapter = new CryptoBrokerIdentityInfoAdapter(activity);
    }

    @Test
    public void filterEmptyList_NoResultsToProcess() {
        ArrayList<CryptoBrokerIdentityInformation> list = new ArrayList<>();
        CryptoBrokerIdentityListFilter listFilter = new CryptoBrokerIdentityListFilter(list, adapter);

        listFilter.filter("test");
        Robolectric.flushBackgroundThreadScheduler();

        assertThat(adapter.getItemCount()).isEqualTo(0);
    }

    @Test
    public void filterListWithData_GetNoMatch(){
        ArrayList<CryptoBrokerIdentityInformation> list = getTestData();
        CryptoBrokerIdentityListFilter listFilter = new CryptoBrokerIdentityListFilter(list, adapter);

        listFilter.filter("<");
        Robolectric.flushBackgroundThreadScheduler();

        assertThat(adapter.getItemCount()).isEqualTo(0);
    }

    @Test
    public void filterListWithData_GetOneMatch(){
        ArrayList<CryptoBrokerIdentityInformation> list = getTestData();
        CryptoBrokerIdentityListFilter listFilter = new CryptoBrokerIdentityListFilter(list, adapter);

        listFilter.filter("xx");
        Robolectric.flushBackgroundThreadScheduler();

        assertThat(adapter.getItemCount()).isEqualTo(1);
    }

    @Test
    public void filterListWithData_GetMultipleMatch(){
        ArrayList<CryptoBrokerIdentityInformation> list = getTestData();
        CryptoBrokerIdentityListFilter listFilter = new CryptoBrokerIdentityListFilter(list, adapter);

        listFilter.filter("test");
        Robolectric.flushBackgroundThreadScheduler();

        assertThat(adapter.getItemCount()).isEqualTo(4);
    }

    @Test
    public void filterListWithData_enterEmptyText_GetMultipleMatch(){
        ArrayList<CryptoBrokerIdentityInformation> list = getTestData();
        CryptoBrokerIdentityListFilter listFilter = new CryptoBrokerIdentityListFilter(list, adapter);

        listFilter.filter("");
        Robolectric.flushBackgroundThreadScheduler();

        assertThat(adapter.getItemCount()).isEqualTo(5);
    }


    private ArrayList<CryptoBrokerIdentityInformation> getTestData() {
        ArrayList<CryptoBrokerIdentityInformation> list = new ArrayList<>();

        list.add(new TestCryptoBrokerIdentityInformation("Test", new byte[0], false));
        list.add(new TestCryptoBrokerIdentityInformation("Test1", new byte[0], false));
        list.add(new TestCryptoBrokerIdentityInformation("Test 2", new byte[0], false));
        list.add(new TestCryptoBrokerIdentityInformation("xxTest", new byte[0], false));
        list.add(new TestCryptoBrokerIdentityInformation("HOLA", new byte[0], false));

        return list;
    }
}