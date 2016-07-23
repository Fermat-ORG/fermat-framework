package com.bitdubai.fermat_core;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractAddon;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantAssignOsContextException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantAssignReferenceException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantListNeededReferencesException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.IncompatibleOsContextException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.IncompatibleReferenceException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.VersionNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantPauseAddonException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantResumeAddonException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartAddonException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStopAddonException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.UnexpectedServiceStatusException;

import java.util.List;

/**
 * The class <code>com.bitdubai.fermat_core.FermatAddonManager</code>
 * centralizes all service actions of the addons in fermat.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/10/2015.
 */
public final class FermatAddonManager {

    private final FermatSystemContext systemContext;

    public FermatAddonManager(final FermatSystemContext systemContext) {

        this.systemContext = systemContext;
    }

    public final FermatManager startAddonAndReferences(final AddonVersionReference addonVersionReference) throws CantStartAddonException,
            VersionNotFoundException {

        try {

            final AbstractAddon abstractAddon = systemContext.getAddonVersion(addonVersionReference);

            if (abstractAddon.isStarted())
                return abstractAddon.getManager();

            final List<AddonVersionReference> neededAddons = abstractAddon.getNeededAddons();

            for (final AddonVersionReference avr : neededAddons) {
                FermatManager reference = startAddonAndReferences(avr);

                abstractAddon.assignAddonReference(avr, reference);
            }

            startAddon(abstractAddon);

            return abstractAddon.getManager();
        } catch (CantListNeededReferencesException e) {

            throw new CantStartAddonException(e, addonVersionReference.toString3(), "Error listing references for the addon.");
        } catch (CantAssignReferenceException |
                IncompatibleReferenceException e) {

            throw new CantStartAddonException(e, addonVersionReference.toString3(), "Error assigning references for the addon.");
        }
    }

    public final void startAddonAndReferences(final AbstractAddon abstractAddon) throws CantStartAddonException {

        try {

            if (!abstractAddon.isStarted()) {

                final List<AddonVersionReference> neededAddons = abstractAddon.getNeededAddons();

                for (final AddonVersionReference avr : neededAddons) {
                    FermatManager reference = startAddonAndReferences(avr);

                    abstractAddon.assignAddonReference(avr, reference);
                }

                startAddon(abstractAddon);

            }
        } catch (CantListNeededReferencesException e) {

            throw new CantStartAddonException(e, abstractAddon.getAddonVersionReference().toString3(), "Error listing references for the addon.");
        } catch (VersionNotFoundException e) {

            throw new CantStartAddonException(e, abstractAddon.getAddonVersionReference().toString3(), "Error trying to find a reference for the addon.");
        } catch (CantAssignReferenceException |
                IncompatibleReferenceException e) {

            throw new CantStartAddonException(e, abstractAddon.getAddonVersionReference().toString3(), "Error assigning references for the addon.");
        }
    }

    public final void startAddon(final AddonVersionReference addonVersionReference) throws CantStartAddonException,
            VersionNotFoundException {

        AbstractAddon abstractAddon = systemContext.getAddonVersion(addonVersionReference);

        startAddon(abstractAddon);

    }

    public final void startAddon(final AbstractAddon abstractAddon) throws CantStartAddonException {

        if (abstractAddon.isStarted())
            return;

        try {
            if (abstractAddon.isDealsWithOsContext())
                abstractAddon.assignOsContext(systemContext.getOsContext());

            abstractAddon.start();
        } catch (com.bitdubai.fermat_api.CantStartPluginException e) {

            throw new CantStartAddonException(
                    e,
                    abstractAddon.getAddonVersionReference().toString3(),
                    "There was a captured problem during the addon start."
            );
        } catch (CantAssignOsContextException |
                IncompatibleOsContextException e) {

            throw new CantStartAddonException(
                    e,
                    abstractAddon.getAddonVersionReference().toString3(),
                    "There was a problem trying to assign the os context to the addon."
            );
        } catch (Exception e) {

            throw new CantStartAddonException(
                    e,
                    abstractAddon.getAddonVersionReference().toString3(),
                    "Unhandled exception trying to start the addon."
            );
        }

    }

    public final void stopAddon(final AddonVersionReference addonVersionReference) throws CantStopAddonException,
            VersionNotFoundException,
            UnexpectedServiceStatusException {

        AbstractAddon abstractAddon = systemContext.getAddonVersion(addonVersionReference);

        stopAddon(abstractAddon);

    }

    public final void stopAddon(final AbstractAddon abstractAddon) throws CantStopAddonException,
            UnexpectedServiceStatusException {

        if (!abstractAddon.isStarted()) {
            throw new UnexpectedServiceStatusException(
                    new StringBuilder().append("Service Status: ").append(abstractAddon.getStatus()).append(" || ").append(abstractAddon.getAddonVersionReference().toString()).toString(),
                    "The addon cannot be stopped because is not started."
            );
        }

        try {

            abstractAddon.stop();

        } catch (Exception e) {
            throw new CantStopAddonException(e, abstractAddon.toString(), "Unhandled exception trying to stop the addon.");
        }
    }

    public final void pauseAddon(final AddonVersionReference addonVersionReference) throws CantPauseAddonException,
            VersionNotFoundException,
            UnexpectedServiceStatusException {

        AbstractAddon abstractAddon = systemContext.getAddonVersion(addonVersionReference);

        pauseAddon(abstractAddon);

    }

    public final void pauseAddon(final AbstractAddon abstractAddon) throws CantPauseAddonException,
            UnexpectedServiceStatusException {

        if (!abstractAddon.isStarted()) {
            throw new UnexpectedServiceStatusException(
                    new StringBuilder().append("Service Status: ").append(abstractAddon.getStatus()).append(" || ").append(abstractAddon.getAddonVersionReference().toString()).toString(),
                    "The addon cannot be paused because is not started."
            );
        }

        try {

            abstractAddon.pause();

        } catch (Exception e) {
            throw new CantPauseAddonException(e, abstractAddon.toString(), "Unhandled exception trying to pause the addon.");
        }
    }

    public final void resumeAddon(final AddonVersionReference addonVersionReference) throws CantResumeAddonException,
            VersionNotFoundException,
            UnexpectedServiceStatusException {

        AbstractAddon abstractAddon = systemContext.getAddonVersion(addonVersionReference);

        resumeAddon(abstractAddon);

    }

    public final void resumeAddon(final AbstractAddon abstractAddon) throws CantResumeAddonException,
            UnexpectedServiceStatusException {

        if (!abstractAddon.isPaused()) {
            throw new UnexpectedServiceStatusException(
                    new StringBuilder().append("Service Status: ").append(abstractAddon.getStatus()).append(" || ").append(abstractAddon.getAddonVersionReference().toString()).toString(),
                    "The addon cannot be resumed because is not paused."
            );
        }

        try {

            abstractAddon.pause();

        } catch (Exception e) {
            throw new CantResumeAddonException(e, abstractAddon.toString(), "Unhandled exception trying to resume the addon.");
        }
    }
}
