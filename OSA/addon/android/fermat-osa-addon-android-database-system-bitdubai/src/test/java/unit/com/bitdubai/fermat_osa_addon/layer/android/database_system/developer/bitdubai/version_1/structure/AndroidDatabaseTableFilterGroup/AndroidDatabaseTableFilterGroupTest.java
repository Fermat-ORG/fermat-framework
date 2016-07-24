package unit.com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidDatabaseTableFilterGroup;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOperator;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilterGroup;
import com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidDatabaseTableFilter;
import com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidDatabaseTableFilterGroup;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by angel on 28/7/15.
 */
public class AndroidDatabaseTableFilterGroupTest {

    AndroidDatabaseTableFilterGroup FilterGroup_1;
    AndroidDatabaseTableFilterGroup FilterGroup_2;

    List<DatabaseTableFilter> filters_1;
    List<DatabaseTableFilterGroup> subGroups_1;

    List<DatabaseTableFilter> filters_2;
    List<DatabaseTableFilterGroup> subGroups_2;

    @Before
    public void setUpVariable1() {
        FilterGroup_1 = new AndroidDatabaseTableFilterGroup();

        filters_1 = new ArrayList<DatabaseTableFilter>();
        subGroups_1 = new ArrayList<DatabaseTableFilterGroup>();

        filters_1.add(new AndroidDatabaseTableFilter());
        filters_1.add(new AndroidDatabaseTableFilter());
        filters_1.add(new AndroidDatabaseTableFilter());
        subGroups_1.add(new AndroidDatabaseTableFilterGroup());
        subGroups_1.add(new AndroidDatabaseTableFilterGroup());
        subGroups_1.add(new AndroidDatabaseTableFilterGroup());

        filters_2 = new ArrayList<DatabaseTableFilter>();
        subGroups_2 = new ArrayList<DatabaseTableFilterGroup>();

        filters_2.add(new AndroidDatabaseTableFilter());
        subGroups_2.add(new AndroidDatabaseTableFilterGroup());


        //FilterGroup_1.setFilters(filters_1);
        //FilterGroup_1.setSubGroups(subGroups_1);
        //FilterGroup_1.setOperator(DatabaseFilterOperator.AND);
    }

    @Test
    public void Variables_AreEquals() {
        assertThat(FilterGroup_1.getSubGroups()).isEqualTo(subGroups_1);
        assertThat(FilterGroup_1.getFilters()).isEqualTo(filters_1);
        assertThat(FilterGroup_1.getOperator()).isEqualTo(DatabaseFilterOperator.AND);
    }

    @Test
    public void Variables_NotEquals() {
        assertThat(FilterGroup_1.getSubGroups()).isNotEqualTo(subGroups_2);
        assertThat(FilterGroup_1.getFilters()).isNotEqualTo(filters_2);
        assertThat(FilterGroup_1.getOperator()).isNotEqualTo(DatabaseFilterOperator.OR);
    }
}
