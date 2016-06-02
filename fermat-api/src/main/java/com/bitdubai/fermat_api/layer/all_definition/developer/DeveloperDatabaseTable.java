package com.bitdubai.fermat_api.layer.all_definition.developer;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ciencias on 6/25/15.
 */
public interface DeveloperDatabaseTable extends Serializable {

    String getName();

    List<String> getFieldNames();

}
