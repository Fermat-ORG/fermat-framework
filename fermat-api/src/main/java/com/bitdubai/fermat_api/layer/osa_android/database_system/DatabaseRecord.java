package com.bitdubai.fermat_api.layer.osa_android.database_system;

/**
 * <p>The abstract class <code>DatabaseRecord</code> is a interface
 * that define the methods to access the properties of the object Database Record.
 *
 * @author Natalia
 * @version 1.0.0
 * @since 25/03/2015.
 */

public interface DatabaseRecord {

    String getName();

    String getValue();

    boolean isChange();

    boolean isUseOfVariable();

}
