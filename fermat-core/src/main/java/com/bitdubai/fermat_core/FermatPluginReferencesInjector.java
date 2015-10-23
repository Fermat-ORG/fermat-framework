package com.bitdubai.fermat_core;

import com.bitdubai.fermat_api.layer.all_definition.common.utils.PluginReference;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The class <code>com.bitdubai.fermat_core.FermatPluginReferencesInjector</code>
 * is responsible for the injection of the needed references for the plugins.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 22/10/2015.
 */
public class FermatPluginReferencesInjector {

    private final Map<PluginReference, Integer> pluginLevels;

    private final FermatSystemContext fermatSystemContext;

    public FermatPluginReferencesInjector(final FermatSystemContext fermatSystemContext) {

        this.pluginLevels        = new HashMap<>();

        this.fermatSystemContext = fermatSystemContext;
    }
/*
    private void setLevels(PluginReference prToCalc, Integer lvlToAssign) {

        Integer lvlToAssignToReferences = lvlToAssign + 1;

        if(pluginLevels.containsKey(prToCalc)) {

            Integer actualLvl = pluginLevels.get(prToCalc);

            if (actualLvl < lvlToAssign) {
                pluginLevels.put(prToCalc, lvlToAssign);
                assignToReferences(prToCalc, lvlToAssignToReferences);
            } else if (actualLvl.equals(lvlToAssign)) {
                assignToReferences(prToCalc, lvlToAssignToReferences);
            } else {
                lvlToAssignToReferences = actualLvl + 1;
                assignToReferences(prToCalc, lvlToAssignToReferences);
            }

        } else {
            pluginLevels.put(prToCalc, lvlToAssign);
            assignToReferences(prToCalc, lvlToAssignToReferences);
        }

    }

    private void setLevels(PluginReference prToCalc, List<PluginReference> pluginReferenceList, Integer lvlToAssign) {

        Integer lvlToAssignToReferences = lvlToAssign + 1;

        if(pluginLevels.containsKey(prToCalc)) {

            Integer actualLvl = pluginLevels.get(prToCalc);

            if (actualLvl < lvlToAssign) {
                pluginLevels.put(prToCalc, lvlToAssign);
                assignToReferences(prToCalc, pluginReferenceList, lvlToAssignToReferences);
            } else if (actualLvl.equals(lvlToAssign)) {
                assignToReferences(prToCalc, pluginReferenceList, lvlToAssignToReferences);
            } else {
                lvlToAssignToReferences = actualLvl + 1;
                assignToReferences(prToCalc, pluginReferenceList, lvlToAssignToReferences);
            }

        } else {
            pluginLevels.put(prToCalc, lvlToAssign);
            assignToReferences(prToCalc, pluginReferenceList, lvlToAssignToReferences);
        }

    }

    private void assignToReferences(PluginReference prToCalc, Integer lvlToAssign) {
        List<PluginReference> refNeededList = pluginReferenceListMap.get(prToCalc);
        for (PluginReference refNeeded : refNeededList) {
            List<PluginReference> refNeededReferenceList = pluginReferenceListMap.get(refNeeded);
            setLevels(refNeeded, refNeededReferenceList, lvlToAssign);
        }
    }
    private void assignToReferences(PluginReference prToCalc, List<PluginReference> pluginReferenceList, Integer lvlToAssign) {
        for (PluginReference refNeeded : pluginReferenceList) {
            List<PluginReference> refNeededReferenceList = pluginReferenceListMap.get(refNeeded);
            if (compareReferences(prToCalc, refNeeded, refNeededReferenceList))
                setLevels(refNeeded, refNeededReferenceList, lvlToAssign);
            else
                System.out.println("Cyclic reference found.");
        }
    }

    // check looking for cyclic references
    private boolean compareReferences(PluginReference pr1, PluginReference pr2, List<PluginReference> pluginReferenceList) {
        for (PluginReference ref2 : pluginReferenceList) {
            if(pr1.equals(ref2)) {
                System.out.println("Cyclic reference found between.- "+pr1 + " and " + pr2);
                return false;
            }
        }
        return true;
    }*/
}
