package com.bitdubai.fermat_core;

import com.bitdubai.fermat_api.layer.all_definition.common.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantListReferencesException;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CyclicalRelationshipFoundException;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.VersionNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.common.utils.PluginVersionReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * The class <code>com.bitdubai.fermat_core.FermatPluginReferencesCalculator</code>
 * is responsible for the calculation of the needed references for the plugins.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 22/10/2015.
 */
public class FermatPluginReferencesCalculator {

    private final FermatSystemContext fermatSystemContext;

    public FermatPluginReferencesCalculator(final FermatSystemContext fermatSystemContext) {

        this.fermatSystemContext = fermatSystemContext;
    }

    /**
     * Throw the method <code>listReferencesByInstantiationOrder</code> we can get the list of plugin version references of
     * a plugin version ordered by instantiation need.
     *
     * @param pluginVersionReference data of the plugin version that we need to calculate the references.
     * @return a list of plugin version references ordered.
     * @throws CantListReferencesException if something goes wrong.
     */

    public final List<PluginVersionReference> listReferencesByInstantiationOrder(final PluginVersionReference pluginVersionReference) throws CantListReferencesException {

        final Map<PluginVersionReference, Integer> pluginLevels = new HashMap<>();

        try {
            setLevels(pluginVersionReference, 1, pluginLevels);

            return getPluginsInstantiationOrder(pluginLevels);

        } catch (VersionNotFoundException e) {

            throw new CantListReferencesException(
                    e,
                    pluginVersionReference.toString(),
                    "Plugin version not found."
            );
        } catch(CyclicalRelationshipFoundException e) {

            throw new CantListReferencesException(
                    e,
                    pluginVersionReference.toString(),
                    "Cyclical relationship found between the references of the requested plugin version."
            );
        } catch (Exception e) {

            throw new CantListReferencesException(
                    e,
                    pluginVersionReference.toString(),
                    "Unhandled error trying to get the order of the references of the given plugin version."
            );
        }
    }

    /**
     * Throw the method <code>orderPluginsByLevel</code> we can order the plugin by instantiation level.
     *
     * @param pluginLevels map that saves the plugins levels at the moment, the key is the plugin version reference, and the value is an integer indicating the level.
     *
     * @return a list of plugin version references ordered.
     */
    private List<PluginVersionReference> getPluginsInstantiationOrder(final Map<PluginVersionReference, Integer> pluginLevels) {

        List<Map.Entry<PluginVersionReference, Integer>> list = new LinkedList<>(pluginLevels.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<PluginVersionReference, Integer>>() {
            public int compare(Map.Entry<PluginVersionReference, Integer> o1, Map.Entry<PluginVersionReference, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        List<PluginVersionReference> orderedList = new ArrayList<>();

        for (Map.Entry<PluginVersionReference, Integer> entry : list)
            orderedList.add(entry.getKey());

        return orderedList;
    }

    /**
     * Throw the method <code>setLevels</code> we give to the plugin reference the level that it must have.
     *
     * @param prToCalc      plugin version which we are calculating.
     * @param lvlToAssign   level to assign to the plugin version that we are calculating..
     * @param pluginLevels  map that saves the plugins levels at the moment, the key is the plugin version reference, and the value is an integer indicating the level.
     *
     * @throws VersionNotFoundException            if we can't find a the plugin version of one of the references.
     * @throws CyclicalRelationshipFoundException  if we find a cyclical relationship between the references.
     */
    private void setLevels(final PluginVersionReference prToCalc,
                           final Integer lvlToAssign,
                           final Map<PluginVersionReference, Integer> pluginLevels) throws VersionNotFoundException,
            CyclicalRelationshipFoundException {

        Integer lvlToAssignToReferences = lvlToAssign + 1;

        AbstractPlugin abstractPlugin = fermatSystemContext.getPluginVersion(prToCalc);

        if(pluginLevels.containsKey(prToCalc)) {

            Integer actualLvl = pluginLevels.get(prToCalc);

            if (actualLvl < lvlToAssign) {
                pluginLevels.put(prToCalc, lvlToAssign);
                assignToReferences(abstractPlugin, lvlToAssignToReferences, pluginLevels);
            } else if (actualLvl.equals(lvlToAssign)) {
                assignToReferences(abstractPlugin, lvlToAssignToReferences, pluginLevels);
            } else {
                lvlToAssignToReferences = actualLvl + 1;
                assignToReferences(abstractPlugin, lvlToAssignToReferences, pluginLevels);
            }

        } else {
            pluginLevels.put(prToCalc, lvlToAssign);
            assignToReferences(abstractPlugin, lvlToAssignToReferences, pluginLevels);
        }

    }

    /**
     * Throw the method <code>setLevels</code> we give to the plugin reference the level that it must have.
     *
     * @param prToCalc      plugin version which we are calculating.
     * @param prList        list of references of the plugin version which we are calculating.
     * @param lvlToAssign   level to assign to the plugin version that we are calculating..
     * @param pluginLevels  map that saves the plugins levels at the moment, the key is the plugin version reference, and the value is an integer indicating the level.
     *
     * @throws VersionNotFoundException            if we can't find a the plugin version of one of the references.
     * @throws CyclicalRelationshipFoundException  if we find a cyclical relationship between the references.
     */
    private void setLevels(final PluginVersionReference prToCalc,
                           final List<PluginVersionReference> prList,
                           final Integer lvlToAssign,
                           final Map<PluginVersionReference, Integer> pluginLevels) throws VersionNotFoundException,
            CyclicalRelationshipFoundException {

        Integer lvlToAssignToReferences = lvlToAssign + 1;

        if(pluginLevels.containsKey(prToCalc)) {

            Integer actualLvl = pluginLevels.get(prToCalc);

            if (actualLvl < lvlToAssign) {
                pluginLevels.put(prToCalc, lvlToAssign);
                assignToReferences(prToCalc, prList, lvlToAssignToReferences, pluginLevels);
            } else if (actualLvl.equals(lvlToAssign)) {
                assignToReferences(prToCalc, prList, lvlToAssignToReferences, pluginLevels);
            } else {
                lvlToAssignToReferences = actualLvl + 1;
                assignToReferences(prToCalc, prList, lvlToAssignToReferences, pluginLevels);
            }

        } else {
            pluginLevels.put(prToCalc, lvlToAssign);
            assignToReferences(prToCalc, prList, lvlToAssignToReferences, pluginLevels);
        }

    }

    /**
     * Throw the method <code>assignLvlToReferences</code> we give to the reference the lvl that they must have.
     *
     * @param prToCalc      plugin version which we are calculating.
     * @param lvlToAssign   level to assign to the references.
     * @param pluginLevels map that saves the plugins levels at the moment, the key is the plugin version reference, and the value is an integer indicating the level.
     *
     * @throws VersionNotFoundException            if we can't find a the plugin version of one of the references.
     * @throws CyclicalRelationshipFoundException  if we find a cyclical relationship between the references.
     */

    private void assignToReferences(final AbstractPlugin prToCalc,
                                    final Integer lvlToAssign,
                                    final Map<PluginVersionReference, Integer> pluginLevels) throws VersionNotFoundException,
            CyclicalRelationshipFoundException {

        List<PluginVersionReference> refNeededList = prToCalc.getNeededPluginReferences();
        for (PluginVersionReference refNeeded : refNeededList) {
            List<PluginVersionReference> refNeededReferenceList = fermatSystemContext.getPluginVersion(refNeeded).getNeededPluginReferences();
            setLevels(refNeeded, refNeededReferenceList, lvlToAssign, pluginLevels);
        }
    }

    /**
     * Throw the method <code>assignLvlToReferences</code> we give to the reference the lvl that they must have.
     *
     * @param prToCalc      plugin version which we are calculating.
     * @param prList        list of references of the plugin version which we are calculating.
     * @param lvlToAssign   level to assign to the references.
     * @param pluginLevels  map that saves the plugins levels at the moment, the key is the plugin version reference, and the value is an integer indicating the level.
     *
     * @throws VersionNotFoundException            if we can't find a the plugin version of one of the references.
     * @throws CyclicalRelationshipFoundException  if we find a cyclical relationship between the references.
     */
    private void assignToReferences(final PluginVersionReference prToCalc,
                                    final List<PluginVersionReference> prList,
                                    final Integer lvlToAssign,
                                    final Map<PluginVersionReference, Integer> pluginLevels) throws VersionNotFoundException,
            CyclicalRelationshipFoundException {

        for (final PluginVersionReference refNeeded : prList) {
            List<PluginVersionReference> refNeededReferenceList = fermatSystemContext.getPluginVersion(refNeeded).getNeededPluginReferences();
            if (compareReferences(prToCalc, refNeeded, refNeededReferenceList))
                setLevels(refNeeded, refNeededReferenceList, lvlToAssign, pluginLevels);
        }
    }

    /**
     * Throw the method <code>compareReferences</code> you can check if there is a cyclical relationship between the plugin version and its references.
     *
     * @param referenceAnalyzing       reference that we're watching.
     * @param subReferenceAnalyzed     reference of the reference that we're watching.
     * @param subReferenceReferences   sub-references of that reference.
     *
     * @return boolean indicating if its all ok, only false is shown. if there is a cyclical relationship found is thrown an exception.
     *
     * @throws CyclicalRelationshipFoundException if exists a cyclical redundancy.
     */
    private boolean compareReferences(final PluginVersionReference       referenceAnalyzing    ,
                                      final PluginVersionReference subReferenceAnalyzed,
                                      final List<PluginVersionReference> subReferenceReferences) throws CyclicalRelationshipFoundException {

        for (final PluginVersionReference ref2 : subReferenceReferences) {
            if (referenceAnalyzing.equals(ref2))
                throw new CyclicalRelationshipFoundException(
                        "Comparing: " + referenceAnalyzing + "\n with: " + subReferenceAnalyzed,
                        "Cyclical relationship found."
                );
        }

        return false;
    }
}
