package unit.com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.ContactsFragment;

import static org.mockito.Mockito.*;
import static org.fest.assertions.api.Assertions.*;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

/**
 * Created by jorgegonzalez on 2015.06.28..
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
*/
public class ExampleTest {

    private static final String CONTACTS_FRAGMENT_TAG = "CONTACTSFRAGMENT";
/*
    @Mock
    private Platform mockPlatform = mock(Platform.class);
    @Mock
    private CryptoWalletManager mockCryptoWalletManager = mock(CryptoWalletManager.class);
    @Mock
    private CryptoWallet mockCryptoWallet = mock(CryptoWallet.class);

    private FragmentActivity testActivity;
    private ContactsFragment testFragment;

    @Ignore
    @Test
    public void ExampleTest() throws Exception{
        when(mockPlatform.getCryptoWalletManager()).thenReturn(mockCryptoWalletManager);
        when(mockCryptoWalletManager.getCryptoWallet()).thenReturn(mockCryptoWallet);
        when(mockCryptoWallet.listWalletContacts(any(UUID.class))).thenThrow(new CantGetAllWalletContactsException());

        ActivityController controller = Robolectric.buildActivity(FragmentActivity.class).create();

        testActivity = (FragmentActivity) controller.get();

        testFragment = ContactsFragment.newInstance(0, mockPlatform);

        FragmentManager manager = testActivity.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(testFragment, CONTACTS_FRAGMENT_TAG);
        transaction.commit();

        controller.start();
        controller.resume();
        controller.visible();

        assertThat(testFragment).isNotNull();

    }
*/
}