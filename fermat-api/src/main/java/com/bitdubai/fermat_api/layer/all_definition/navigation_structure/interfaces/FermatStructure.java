package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.AppStructureType;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Matias Furszyfer on 2016.01.06..
 */
public interface FermatStructure extends Serializable {


    /**
     * App public identifier
     *
     * @return String
     */
    String getPublicKey();

    /**
     * Platform of the App
     * //todo: think if this field is necessary, because a platform is something in a higher level.
     *
     * @return
     */
    Platforms getPlatform();

    /**
     * Identifier to know what kind of app is, if we re use fragments, re use modules, combine both or only look for new fragments.
     *
     * @return
     */
    AppStructureType getAppStructureType();

    /**
     *
     */
    List<String> getAppsKeyConsumed();

    /**
     * Return an specific Activity object from this class
     *
     * @param activities
     * @return
     */
    Activity getActivity(Activities activities);

    /**
     * Return the start activity from the structure
     *
     * @return
     * @throws IllegalAccessException
     * @throws InvalidParameterException
     */
    Activity getStartActivity() throws IllegalAccessException, InvalidParameterException;

    /**
     * Return the last activity used
     *
     * @return
     * @throws InvalidParameterException
     */
    Activity getLastActivity() throws InvalidParameterException;

    /**
     * Change the start activity for another
     *
     * @param activityCode
     * @throws IllegalArgumentException
     * @throws InvalidParameterException
     */
    void changeActualStartActivity(String activityCode) throws IllegalArgumentException, InvalidParameterException;

    /**
     * Clear params
     */
    void clear();
}
