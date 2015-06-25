package com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure;

import android.content.Context;

import static org.fest.assertions.api.Assertions.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
//import android.test.mock.MockContext;

import  com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotOpenException;


import org.junit.Test;

import java.util.UUID;

/**
 * Created by mati on 24/06/15.
 */
public class ConstructionTest {

    //Context context = new MockContext();

    AndroidDatabaseTable androidDatabaseTable= new AndroidDatabaseTable(null,new AndroidDatabase(),"fermat");

    //@Test(expected=IllegalArgumentException.class)
    //public void CreateMessageSignature_EmptyMessage_ThrowIllegalArgumentException() {
        //AsymmectricCryptography.createMessageSignature(null, testPrivateKey);
    //}
    @Test
    public void open_androidDatabaseTable_objectIsNull() {

        assertNull(androidDatabase.database);

    }


}
