package com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseRecord;

/**
 * This class define methods to access the properties of the object Database Record
 * <p/>
 * Created by Natalia on 25/03/2015.
 * Modified by Leon Acosta (laion.cj91@gmail.com) on 03/02/2016.
 *
 * @author Natalia
 * @version 1.0.0
 * @since 25/03/2015.
 */

public class AndroidRecord implements DatabaseRecord {

    /**
     * DatabaseRecord Interface member variables.
     */
    private final String recordName;
    private final String recordValue;
    private final boolean recordChange;
    private final boolean useOfVariable;

    public AndroidRecord(final String recordName,
                         final String recordValue,
                         final boolean recordChange) {

        this.recordName = recordName;
        this.recordValue = recordValue;
        this.recordChange = recordChange;
        this.useOfVariable = false;
    }

    /**
     * DatabaseRecord interface implementation.
     */
    @Override
    public String getName() {
        return this.recordName;
    }

    @Override
    public String getValue() {
        return this.recordValue;
    }

    @Override
    public boolean isChange() {
        return this.recordChange;
    }

    @Override
    public boolean isUseOfVariable() {
        return useOfVariable;
    }

    @Override
    public String toString() {
        return new StringBuilder().append(recordName).append("=").append(recordValue).toString();
    }

}
