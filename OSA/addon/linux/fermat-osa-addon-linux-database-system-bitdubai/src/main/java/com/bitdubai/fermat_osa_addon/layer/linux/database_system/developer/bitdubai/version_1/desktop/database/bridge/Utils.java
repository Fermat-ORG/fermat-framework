/*
* @#Utils.java - 2015
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_osa_addon.layer.linux.database_system.developer.bitdubai.version_1.desktop.database.bridge;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class <code>com.bitdubai.fermat_osa_addon.layer.linux.database_system.developer.bitdubai.version_1.desktop.database.bridge.Utils</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 10/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class Utils {


    public static String[] getColumnsNames(ResultSet rs){
        String[] columnNames = null;
        try{
            ResultSetMetaData rsmd = rs.getMetaData();
            List<String> lstColumnNames=new ArrayList<String>();
            for(int i=0;i<rsmd.getColumnCount();i++){
                lstColumnNames.add(rsmd.getColumnName(i));
            }
            columnNames= new String[lstColumnNames.size()];

            columnNames = lstColumnNames.toArray(columnNames);
        }catch(Exception e){
            e.printStackTrace();
        }
        return columnNames;
    }
}
