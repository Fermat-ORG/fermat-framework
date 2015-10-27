package com.bitdubai.fermat_core;

import com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes.AbstractAddon;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantInjectReferencesException;
import com.bitdubai.fermat_api.layer.all_definition.common.utils.AddonVersionReference;

import java.util.List;

/**
 * The class <code>com.bitdubai.fermat_core.FermatAddonReferencesInjector</code>
 * is the responsible for the injection of the addon references.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 26/10/2015.
 */
public class FermatAddonReferencesInjector {

    private final FermatSystemContext fermatSystemContext;

    public FermatAddonReferencesInjector(final FermatSystemContext fermatSystemContext) {

        this.fermatSystemContext = fermatSystemContext;
    }

    public final void injectReferences(final AddonVersionReference       avrToBeInjected   ,
                                       final List<AddonVersionReference> referencesToInject) throws CantInjectReferencesException {

        try {

            AbstractAddon abstractAddon = fermatSystemContext.getAddonVersion(avrToBeInjected);

            for (final AddonVersionReference avr : referencesToInject) {
                AbstractAddon addonToInject = fermatSystemContext.getAddonVersion(avr);

                if(addonToInject.isStarted())
                    abstractAddon.addAddonReference(avr, addonToInject);
                else
                    System.out.println("Addon Version Reference not started: "+avr.toString());
            }

        } catch(Exception e) {

        }
    }

}
