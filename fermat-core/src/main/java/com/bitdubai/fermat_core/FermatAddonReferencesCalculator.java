package com.bitdubai.fermat_core;

import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantListReferencesException;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CyclicalRelationshipFoundException;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.VersionNotFoundException;

/**
 * The class <code>com.bitdubai.fermat_core.FermatAddonReferencesCalculator</code>
 * is responsible for the calculation of the needed references for the addons.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 22/10/2015.
 */
public class FermatAddonReferencesCalculator {

    private final FermatSystemContext fermatSystemContext;

    public FermatAddonReferencesCalculator(final FermatSystemContext fermatSystemContext) {

        this.fermatSystemContext = fermatSystemContext;
    }

    /**
     * Throw the method <code>listReferencesByInstantiationOrder</code> we can get the list of addon version references of
     * a plugin version ordered by instantiation need.
     *
     * @param addonVersionReference data of the addon version that we need to calculate the references.
     *
     * @return a list of addon version references ordered.
     *
     * @throws CantListReferencesException if something goes wrong.
     */

/*    public final List<AddonVersionReference> listReferencesByInstantiationOrder(final AddonVersionReference addonVersionReference) throws CantListReferencesException {

        final Map<AddonVersionReference, Integer> addonLevels = new HashMap<>();

        try {
            setLevels(addonVersionReference, 1, addonLevels);

            return getAddonsInstantiationOrder(addonLevels);

        } catch (VersionNotFoundException e) {

            throw new CantListReferencesException(
                    e,
                    addonVersionReference.toString(),
                    "Addon version not found."
            );
        } catch(CyclicalRelationshipFoundException e) {

            throw new CantListReferencesException(
                    e,
                    addonVersionReference.toString(),
                    "Cyclical relationship found between the references of the requested addon version."
            );
        } catch (Exception e) {

            throw new CantListReferencesException(
                    e,
                    addonVersionReference.toString(),
                    "Unhandled error trying to get the order of the references of the given addon version."
            );
        }
    }

    *//**
     * Throw the method <code>getAddonsInstantiationOrder</code> we can order the addon by instantiation level.
     *
     * @param addonLevels map that saves the addons levels at the moment, the key is the addon version reference, and the value is an integer indicating the level.
     *
     * @return a list of addon version references ordered.
     *//*
    private List<AddonVersionReference> getAddonsInstantiationOrder(final Map<AddonVersionReference, Integer> addonLevels) {

        List<Map.Entry<AddonVersionReference, Integer>> list = new LinkedList<>(addonLevels.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<AddonVersionReference, Integer>>() {
            public int compare(Map.Entry<AddonVersionReference, Integer> o1, Map.Entry<AddonVersionReference, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        List<AddonVersionReference> orderedList = new ArrayList<>();

        for (Map.Entry<AddonVersionReference, Integer> entry : list)
            orderedList.add(entry.getKey());

        return orderedList;
    }

    *//**
     * Throw the method <code>setLevels</code> we give to the addon reference the level that it must have.
     *
     * @param arToCalc      addon version which we are calculating.
     * @param lvlToAssign   level to assign to the addon version that we are calculating..
     * @param addonLevels   map that saves the addons levels at the moment, the key is the addon version reference, and the value is an integer indicating the level.
     *
     * @throws VersionNotFoundException            if we can't find a the addon version of one of the references.
     * @throws CyclicalRelationshipFoundException  if we find a cyclical relationship between the references.
     *//*
    private void setLevels(final AddonVersionReference               arToCalc   ,
                           final Integer                             lvlToAssign,
                           final Map<AddonVersionReference, Integer> addonLevels) throws VersionNotFoundException           ,
                                                                                         CyclicalRelationshipFoundException {

        Integer lvlToAssignToReferences = lvlToAssign + 1;

        AbstractAddon abstractAddon = fermatSystemContext.getAddonVersion(arToCalc);

        if(addonLevels.containsKey(arToCalc)) {

            Integer actualLvl = addonLevels.get(arToCalc);

            if (actualLvl < lvlToAssign) {
                addonLevels.put(arToCalc, lvlToAssign);
                assignToReferences(abstractAddon, lvlToAssignToReferences, addonLevels);
            } else if (actualLvl.equals(lvlToAssign)) {
                assignToReferences(abstractAddon, lvlToAssignToReferences, addonLevels);
            } else {
                lvlToAssignToReferences = actualLvl + 1;
                assignToReferences(abstractAddon, lvlToAssignToReferences, addonLevels);
            }

        } else {
            addonLevels.put(arToCalc, lvlToAssign);
            assignToReferences(abstractAddon, lvlToAssignToReferences, addonLevels);
        }

    }

    *//**
     * Throw the method <code>setLevels</code> we give to the addon reference the level that it must have.
     *
     * @param arToCalc      addon version which we are calculating.
     * @param arList        list of references of the addon version which we are calculating.
     * @param lvlToAssign   level to assign to the addon version that we are calculating..
     * @param addonLevels   map that saves the addons levels at the moment, the key is the addon version reference, and the value is an integer indicating the level.
     *
     * @throws VersionNotFoundException            if we can't find a the addon version of one of the references.
     * @throws CyclicalRelationshipFoundException  if we find a cyclical relationship between the references.
     *//*
    private void setLevels(final AddonVersionReference               arToCalc    ,
                           final List<AddonVersionReference>         arList      ,
                           final Integer                             lvlToAssign ,
                           final Map<AddonVersionReference, Integer> addonLevels) throws VersionNotFoundException           ,
                                                                                         CyclicalRelationshipFoundException {

        Integer lvlToAssignToReferences = lvlToAssign + 1;

        if(addonLevels.containsKey(arToCalc)) {

            Integer actualLvl = addonLevels.get(arToCalc);

            if (actualLvl < lvlToAssign) {
                addonLevels.put(arToCalc, lvlToAssign);
                assignToReferences(arToCalc, arList, lvlToAssignToReferences, addonLevels);
            } else if (actualLvl.equals(lvlToAssign)) {
                assignToReferences(arToCalc, arList, lvlToAssignToReferences, addonLevels);
            } else {
                lvlToAssignToReferences = actualLvl + 1;
                assignToReferences(arToCalc, arList, lvlToAssignToReferences, addonLevels);
            }

        } else {
            addonLevels.put(arToCalc, lvlToAssign);
            assignToReferences(arToCalc, arList, lvlToAssignToReferences, addonLevels);
        }

    }

    *//**
     * Throw the method <code>assignLvlToReferences</code> we give to the reference the lvl that they must have.
     *
     * @param arToCalc      addon version which we are calculating.
     * @param lvlToAssign   level to assign to the references.
     * @param addonLevels   map that saves the addons levels at the moment, the key is the addon version reference, and the value is an integer indicating the level.
     *
     * @throws VersionNotFoundException            if we can't find a the addon version of one of the references.
     * @throws CyclicalRelationshipFoundException  if we find a cyclical relationship between the references.
     *//*

    private void assignToReferences(final AbstractAddon                       arToCalc   ,
                                    final Integer                             lvlToAssign,
                                    final Map<AddonVersionReference, Integer> addonLevels) throws VersionNotFoundException           ,
                                                                                                  CyclicalRelationshipFoundException {

        List<AddonVersionReference> refNeededList = arToCalc.getNeededAddonReferences();
        for (AddonVersionReference refNeeded : refNeededList) {
            List<AddonVersionReference> refNeededReferenceList = fermatSystemContext.getAddonVersion(refNeeded).getNeededAddonReferences();
            setLevels(refNeeded, refNeededReferenceList, lvlToAssign, addonLevels);
        }
    }

    *//**
     * Throw the method <code>assignLvlToReferences</code> we give to the reference the lvl that they must have.
     *
     * @param arToCalc      addon version which we are calculating.
     * @param arList        list of references of the addon version which we are calculating.
     * @param lvlToAssign   level to assign to the references.
     * @param addonLevels   map that saves the addons levels at the moment, the key is the addon version reference, and the value is an integer indicating the level.
     *
     * @throws VersionNotFoundException            if we can't find a the addon version of one of the references.
     * @throws CyclicalRelationshipFoundException  if we find a cyclical relationship between the references.
     *//*
    private void assignToReferences(final AddonVersionReference               arToCalc   ,
                                    final List<AddonVersionReference>         arList     ,
                                    final Integer                             lvlToAssign,
                                    final Map<AddonVersionReference, Integer> addonLevels) throws VersionNotFoundException           ,
                                                                                                  CyclicalRelationshipFoundException {

        for (final AddonVersionReference refNeeded : arList) {
            List<AddonVersionReference> refNeededReferenceList = fermatSystemContext.getAddonVersion(refNeeded).getNeededAddonReferences();
            if (compareReferences(arToCalc, refNeeded, refNeededReferenceList))
                setLevels(refNeeded, refNeededReferenceList, lvlToAssign, addonLevels);
        }
    }

    *//**
     * Throw the method <code>compareReferences</code> you can check if there is a cyclical relationship between the addon version and its references.
     *
     * @param referenceAnalyzing       reference that we're watching.
     * @param subReferenceAnalyzed     reference of the reference that we're watching.
     * @param subReferenceReferences   sub-references of that reference.
     *
     * @return boolean indicating if its all ok, only false is shown. if there is a cyclical relationship found is thrown an exception.
     *
     * @throws CyclicalRelationshipFoundException if exists a cyclical redundancy.
     *//*
    private boolean compareReferences(final AddonVersionReference       referenceAnalyzing    ,
                                      final AddonVersionReference       subReferenceAnalyzed  ,
                                      final List<AddonVersionReference> subReferenceReferences) throws CyclicalRelationshipFoundException {

        for (final AddonVersionReference ref2 : subReferenceReferences) {
            if (referenceAnalyzing.equals(ref2))
                throw new CyclicalRelationshipFoundException(
                        "Comparing: " + referenceAnalyzing + "\n with: " + subReferenceAnalyzed,
                        "Cyclical relationship found."
                );
        }

        return false;
    }*/
}
