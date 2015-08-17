package com.bitdubai.fermat_core.layer.pip_identity;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_core.layer.pip_identity.designer.DesignerIdentitySubsystem;
import com.bitdubai.fermat_core.layer.pip_identity.publisher.PublisherIdentitySubsystem;
import com.bitdubai.fermat_core.layer.pip_identity.translator.TranslatorIdentitySubsystem;
import com.bitdubai.fermat_pip_api.layer.pip_identity.IdentitySubsystem;
import com.bitdubai.fermat_pip_api.layer.pip_identity.CantStartSubsystemException;
import com.bitdubai.fermat_core.layer.pip_identity.developer.DeveloperIdentitySubsystem;

/**
 * The interface <code>com.bitdubai.fermat_core.layer.pip_identity.IdentityLayer</code>
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 09/07/15.
 *
 * @version 1.0
 */
public class IdentityLayer implements PlatformLayer {

    private Plugin mpublisherIdentity;

    private Plugin mdeveloperIdentity;

    private Plugin mtranslatorIdentity;

    private Plugin mdesignerIdentity;

    public Plugin getPublisherIdentity() {
        return mpublisherIdentity;
    }

    public Plugin getDeveloperIdentity() {
        return mdeveloperIdentity;
    }

    public Plugin getTranslatorIdentity() {
        return mtranslatorIdentity;
    }

    public Plugin getDesignerIdentity() {
        return mdesignerIdentity;
    }

    @Override
    public void start() throws CantStartLayerException {

        /**
         * Start the Developer identity plugin
         */
        IdentitySubsystem developerIdentitySubsystem = new DeveloperIdentitySubsystem();
        try {
            developerIdentitySubsystem.start();
            mdeveloperIdentity = (developerIdentitySubsystem).getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartActorLayerException: " + e.getMessage());

            /**
             * Since this is the only implementation, if this does not start, then the layer can't start either.
             */
            throw new CantStartLayerException();
        }

        /**
         * Start the Publisher identity plugin
         */
        IdentitySubsystem publisherIdentitySubsystem = new PublisherIdentitySubsystem();
        try {
            publisherIdentitySubsystem.start();
            mpublisherIdentity = (publisherIdentitySubsystem).getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartActorLayerException: " + e.getMessage());

            /**
             * Since this is the only implementation, if this does not start, then the layer can't start either.
             */
            throw new CantStartLayerException();
        }

        /**
         * Start the translator identity plugin
         */
        TranslatorIdentitySubsystem developerTransalatorSubsystem = new TranslatorIdentitySubsystem();
        try {
            developerTransalatorSubsystem.start();
            mtranslatorIdentity = (developerTransalatorSubsystem).getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartActorLayerException: " + e.getMessage());

            /**
             * Since this is the only implementation, if this does not start, then the layer can't start either.
             */
            throw new CantStartLayerException();
        }

        /**
         * Start the designer identity plugin
         */
        DesignerIdentitySubsystem developerDesignerSubsystem = new DesignerIdentitySubsystem();
        try {
            developerDesignerSubsystem.start();
            mdesignerIdentity = (developerDesignerSubsystem).getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartActorLayerException: " + e.getMessage());

            /**
             * Since this is the only implementation, if this does not start, then the layer can't start either.
             */
            throw new CantStartLayerException();
        }
    }
}
