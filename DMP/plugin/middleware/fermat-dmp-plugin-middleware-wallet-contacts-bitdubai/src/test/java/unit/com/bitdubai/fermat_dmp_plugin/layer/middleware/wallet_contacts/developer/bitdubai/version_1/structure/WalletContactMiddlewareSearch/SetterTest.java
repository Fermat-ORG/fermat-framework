package unit.com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactMiddlewareSearch;


import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.database.WalletContactsMiddlewareDao;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactMiddlewareSearch;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.search.SearchField;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.search.SearchOrder;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;


import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;


/**
 * Created by natalia on 11/09/15.
 */
public class SetterTest {
    private WalletContactsMiddlewareDao walletContactsMiddlewareDao;



    @Mock
    private List<SearchField> mockSearchFields;

    @Mock
    private List<SearchOrder> mockSearchOrders;

    @Mock
    private SearchField mockSearchField;

    @Mock
    private SearchOrder mockSearchOrder;
    @Mock
    private PluginDatabaseSystem mockPluginDatabaseSystem;

    @Mock
    private ErrorManager mockErrorManager;


    private WalletContactMiddlewareSearch walletContactMiddlewareSearch;
    @Before
    public void SetUp() throws Exception {

        mockSearchFields = Arrays.asList(mockSearchField, mockSearchField);
        mockSearchOrders = Arrays.asList(mockSearchOrder, mockSearchOrder);

        walletContactsMiddlewareDao = new WalletContactsMiddlewareDao(mockPluginDatabaseSystem, UUID.randomUUID());

        walletContactMiddlewareSearch = new WalletContactMiddlewareSearch(mockErrorManager,walletContactsMiddlewareDao);
    }

    @Test
    public void setActorAliasTest() throws Exception {

        catchException(walletContactMiddlewareSearch).setActorAlias("alias", true);
        assertThat(caughtException()).isNull();

    }

    @Test
    public void setActorFirstNameTest() throws Exception {

        catchException(walletContactMiddlewareSearch).setActorFirstName("name", true);
        assertThat(caughtException()).isNull();

    }

    @Test
    public void setActorLastNameTest() throws Exception {

        catchException(walletContactMiddlewareSearch).setActorLastName("lastName", true);
        assertThat(caughtException()).isNull();

    }


}

