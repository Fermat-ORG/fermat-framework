package com.bitdubai.fermat_core.layer.dmp_identity;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.dmp_identity.IdentitySubsystem;
import com.bitdubai.fermat_api.layer.dmp_identity.CantStartSubsystemException;
import com.bitdubai.fermat_core.layer.dmp_identity.designer.DesignerIdentitySubsystem;
import com.bitdubai.fermat_core.layer.dmp_identity.translator.TranslatorIdentitySubsystem;

/**
 * Created by natalia on 11/08/15.
 * Modified by Leon Acosta on 28/08/2015.
 */
public class IdentityLayer implements PlatformLayer {

    private Plugin mTranslatorIdentity;

    private Plugin mDesignerIdentity;

    public void start() throws CantStartLayerException {

        mDesignerIdentity = getPlugin(new DesignerIdentitySubsystem());

        mTranslatorIdentity = getPlugin(new TranslatorIdentitySubsystem());

    }

    private Plugin getPlugin(IdentitySubsystem identitySubsystem) throws CantStartLayerException {
        try {
            identitySubsystem.start();
            return identitySubsystem.getPlugin();
        } catch (CantStartSubsystemException e) {
            throw new CantStartLayerException();
        }
    }

    public Plugin getTranslatorIdentity() {
        return mTranslatorIdentity;
    }

    public Plugin getDesignerIdentity() {
        return mDesignerIdentity;
    }
}
