package unit.com.bitdubai.sub_app.crypto_broker_identity;

import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityInformation;

/**
 * Created by nelson on 14/10/15.
 */
public class TestCryptoBrokerIdentityInformation implements CryptoBrokerIdentityInformation {
    private String name;
    private byte[] img;
    private boolean isPublished;

    public TestCryptoBrokerIdentityInformation(String name, byte[] img, boolean isPublished) {
        this.name = name;
        this.img = img;
        this.isPublished = isPublished;
    }

    @Override
    public String getAlias() {
        return name;
    }

    @Override
    public String getPublicKey() {
        return "348u59rfjq3fj02quf029ujf0924u5nf09824";
    }

    @Override
    public byte[] getProfileImage() {
        return img;
    }

    @Override
    public boolean isPublished() {
        return isPublished;
    }
}
