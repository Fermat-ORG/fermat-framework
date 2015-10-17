package unit.com.bitdubai.sub_app.crypto_broker_identity;

import com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_identity.exceptions.CantGetCryptoBrokerListException;
import com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_identity.exceptions.CouldNotCreateCryptoBrokerException;
import com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_identity.exceptions.CouldNotPublishCryptoBrokerException;
import com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_identity.exceptions.CouldNotUnPublishCryptoBrokerException;
import com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityInformation;
import com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityModuleManager;
import com.bitdubai.sub_app.crypto_broker_identity.common.model.CryptoBrokerIdentityInformationImp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nelson on 14/10/15.
 */
public class TestCryptoBrokerIdentityModuleManager implements CryptoBrokerIdentityModuleManager {
    public static final short CREATE_IDENTITY_RETURN_IDENTITY = 2;
    public static final short CREATE_IDENTITY_THROW_EXCEPTION = 3;
    public static final short PUBLISH_IDENTITY_THROW_EXCEPTION = 4;
    public static final short GET_ALL_IDENTITIES_RETURN_LIST = 6;
    public static final short GET_ALL_IDENTITIES_THROW_EXCEPTION = 7;

    private short selectedAction = 0;

    public void setAction(short action) {
        selectedAction = action;
    }

    @Override
    public CryptoBrokerIdentityInformation createCryptoBrokerIdentity(final String cryptoBrokerName, final byte[] profileImage) throws CouldNotCreateCryptoBrokerException {
        if (selectedAction == CREATE_IDENTITY_RETURN_IDENTITY)
            return new TestCryptoBrokerIdentityInformation(cryptoBrokerName, profileImage);
        if (selectedAction == CREATE_IDENTITY_THROW_EXCEPTION)
            throw new CouldNotCreateCryptoBrokerException("messageTest", new Exception(), "contextTest", "possibleReasonTest");

        return null;
    }

    @Override
    public void publishCryptoBrokerIdentity(String cryptoBrokerPublicKey) throws CouldNotPublishCryptoBrokerException {
        if (selectedAction == PUBLISH_IDENTITY_THROW_EXCEPTION)
            throw new CouldNotPublishCryptoBrokerException("messageTest", new Exception(), "contextTest", "possibleReasonTest");
    }

    @Override
    public void unPublishCryptoBrokerIdentity(String cryptoBrokerPublicKey) throws CouldNotUnPublishCryptoBrokerException {

    }

    @Override
    public List<CryptoBrokerIdentityInformation> getAllCryptoBrokersIdentities(int max, int offset) throws CantGetCryptoBrokerListException {
        if(selectedAction == GET_ALL_IDENTITIES_RETURN_LIST) {
            ArrayList<CryptoBrokerIdentityInformation> list = new ArrayList<>();
            list.add(new TestCryptoBrokerIdentityInformation("broker test", new byte[0]));
            return list;
        }

        if (selectedAction == GET_ALL_IDENTITIES_THROW_EXCEPTION)
            throw new CantGetCryptoBrokerListException("messageTest", new Exception(), "contextTest", "possibleReasonTest");

        return null;
    }
}
