package unit.com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;

import com.bitdubai.fermat_api.layer.dmp_identity.designer.exceptions.CantSingMessageException;
import com.bitdubai.fermat_api.layer.dmp_identity.designer.interfaces.DesignerIdentity;

/**
 * Created by rodrigo on 9/10/15.
 */
public class identityTest implements DesignerIdentity {
    String alias;
    String publicKey;

    public identityTest() {
    }

    @Override
    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    @Override
    public String getPublicKey() {
        return publicKey;
    }

    @Override
    public String createMessageSignature(String mensage) throws CantSingMessageException {
        return alias;
    }
}