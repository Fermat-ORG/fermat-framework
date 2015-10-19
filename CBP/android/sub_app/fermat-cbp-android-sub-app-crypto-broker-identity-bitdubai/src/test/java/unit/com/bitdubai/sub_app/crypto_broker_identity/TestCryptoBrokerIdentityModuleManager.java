package unit.com.bitdubai.sub_app.crypto_broker_identity;

import com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_identity.exceptions.CantGetCryptoBrokerListException;
import com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_identity.exceptions.CouldNotCreateCryptoBrokerException;
import com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_identity.exceptions.CouldNotPublishCryptoBrokerException;
import com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_identity.exceptions.CouldNotUnPublishCryptoBrokerException;
import com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityInformation;
import com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityModuleManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nelson on 14/10/15.
 */
public class TestCryptoBrokerIdentityModuleManager implements CryptoBrokerIdentityModuleManager {
    public static final short CREATE_IDENTITY_RETURN_IDENTITY = 2;
    public static final short CREATE_IDENTITY_THROW_EXCEPTION = 3;
    public static final short PUBLISH_IDENTITY_THROW_EXCEPTION = 4;
    public static final short UNPUBLISH_IDENTITY_THROW_EXCEPTION = 5;
    public static final short GET_ALL_IDENTITIES_RETURN_LIST = 6;
    public static final short GET_ALL_IDENTITIES_THROW_EXCEPTION = 7;
    public static final short PUBLISH_IDENTITY_RUN_OK = 8;
    public static final short UNPUBLISH_IDENTITY_RUN_OK = 9;

    private short selectedAction = 0;

    public void setAction(short action) {
        selectedAction = action;
    }

    @Override
    public CryptoBrokerIdentityInformation createCryptoBrokerIdentity(final String cryptoBrokerName, final byte[] profileImage) throws CouldNotCreateCryptoBrokerException {
        if (selectedAction == CREATE_IDENTITY_RETURN_IDENTITY)
            return new TestCryptoBrokerIdentityInformation(cryptoBrokerName, profileImage, false);
        if (selectedAction == CREATE_IDENTITY_THROW_EXCEPTION)
            throw new CouldNotCreateCryptoBrokerException("messageTest", new Exception(), "contextTest", "possibleReasonTest");

        return null;
    }

    @Override
    public void publishCryptoBrokerIdentity(String cryptoBrokerPublicKey) throws CouldNotPublishCryptoBrokerException {
        if (selectedAction == PUBLISH_IDENTITY_THROW_EXCEPTION)
            throw new CouldNotPublishCryptoBrokerException("messageTest", new Exception(), "contextTest", "possibleReasonTest");
        if(selectedAction == PUBLISH_IDENTITY_RUN_OK)
            return;
    }

    @Override
    public void unPublishCryptoBrokerIdentity(String cryptoBrokerPublicKey) throws CouldNotUnPublishCryptoBrokerException {
        if (selectedAction == UNPUBLISH_IDENTITY_THROW_EXCEPTION)
            throw new CouldNotUnPublishCryptoBrokerException("messageTest", new Exception(), "contextTest", "possibleReasonTest");
        if(selectedAction == UNPUBLISH_IDENTITY_RUN_OK)
            return;
    }

    @Override
    public List<CryptoBrokerIdentityInformation> getAllCryptoBrokersIdentities(int max, int offset) throws CantGetCryptoBrokerListException {
        if (selectedAction == GET_ALL_IDENTITIES_RETURN_LIST) {
            ArrayList<CryptoBrokerIdentityInformation> list = new ArrayList<>();
            TestCryptoBrokerIdentityInformation identityInfo = new TestCryptoBrokerIdentityInformation("broker test", new byte[0], false);
            list.add(identityInfo);
            return list;
        }

        if (selectedAction == GET_ALL_IDENTITIES_THROW_EXCEPTION)
            throw new CantGetCryptoBrokerListException("messageTest", new Exception(), "contextTest", "possibleReasonTest");

        return null;
    }
}
