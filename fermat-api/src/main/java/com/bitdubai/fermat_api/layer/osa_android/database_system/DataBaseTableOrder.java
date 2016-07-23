package com.bitdubai.fermat_api.layer.osa_android.database_system;

/**
 * <p>The abstract class <code>DataBaseTableOrder</code> is a interface
 * that define the methods to set and get the query filter order.
 *
 * @author Natalia
 * @version 1.0.0
 * @since 27/03/15.
 */

public interface DataBaseTableOrder {

    String getColumnName();

    DatabaseFilterOrder getDirection();

}
