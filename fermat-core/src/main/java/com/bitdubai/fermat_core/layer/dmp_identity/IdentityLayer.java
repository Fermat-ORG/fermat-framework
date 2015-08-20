package com.bitdubai.fermat_core.layer.dmp_identity;


import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.dmp_identity.IdentitySubsystem;
import com.bitdubai.fermat_api.layer.dmp_identity.CantStartSubsystemException;
import com.bitdubai.fermat_core.layer.dmp_identity.intra_user.IntraUserSubsystem;
import com.bitdubai.fermat_core.layer.dmp_identity.designer.DesignerIdentitySubsystem;
import com.bitdubai.fermat_core.layer.pip_Identity.developer.DeveloperIdentitySubsystem;
import com.bitdubai.fermat_core.layer.dmp_identity.publisher.PublisherIdentitySubsystem;
import com.bitdubai.fermat_core.layer.dmp_identity.translator.TranslatorIdentitySubsystem;

/**
 * Created by natalia on 11/08/15.
 */
public class IdentityLayer implements PlatformLayer {


    private Plugin intraUser;

    private Plugin mpublisherIdentity;

    private Plugin mtranslatorIdentity;

    private Plugin mdesignerIdentity;

    public Plugin getPublisherIdentity() {
        return mpublisherIdentity;
    }

    public Plugin getTranslatorIdentity() {
        return mtranslatorIdentity;
    }

    public Plugin getDesignerIdentity() {
        return mdesignerIdentity;
    }

    public Plugin getIntraUser() {
        return intraUser;
    }


    public void start() throws CantStartLayerException {


        /**
         * Let's start the Intra User Subsystem;
         */
        IdentitySubsystem intraUserSubsystem = new IntraUserSubsystem();

        try {
            intraUserSubsystem.start();
            intraUser = intraUserSubsystem.getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartSubsystemException: " + e.getMessage());

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
