package com.bitdubai.fermat_osa_addon.layer.desktop.database_system.developer.bitdubai.version_1.desktop.database.bridge;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mati
 */
public class Utils {

    public static String[] getColumnsNames(ResultSet rs) {
        String[] columnNames = null;
        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            List<String> lstColumnNames = new ArrayList<String>();
            for (int i = 0; i < rsmd.getColumnCount(); i++) {
                lstColumnNames.add(rsmd.getColumnName(i));
            }
            columnNames = new String[lstColumnNames.size()];

            columnNames = lstColumnNames.toArray(columnNames);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return columnNames;
    }


}
