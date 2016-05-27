package com.bitdubai.fermat_api.layer.all_definition.developer;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ciencias on 6/25/15.
 */
public interface DeveloperDatabaseTableRecord extends Serializable {

    List<String> getValues(); //TODO: Este quizas no sea el mejor modo de devolver los valores
}
