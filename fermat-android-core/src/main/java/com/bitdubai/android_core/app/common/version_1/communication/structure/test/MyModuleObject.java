package com.bitdubai.android_core.app.common.version_1.communication.structure.test;

import android.os.Bundle;

/**
 * Created by mati on 2016.04.20..
 */
public class MyModuleObject extends AbstractModuleObject{

    private final String INTERVAL_ID_KEY = "ID";
    private final String INTERVAL_NAME_KEY = "NAME";
    private final String INTERVAL_AGE_KEY = "AGE";


    private String id;
    private String name;
    private int age;

    public MyModuleObject(String id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }


    @Override
    protected void contructFromInstanceData(Bundle instanceData) {
        id = instanceData.getString(INTERVAL_ID_KEY);
        name = instanceData.getString(INTERVAL_NAME_KEY);
        age = instanceData.getInt(INTERVAL_AGE_KEY);

    }

    @Override
    protected void writeInstanceData(Bundle instanceData) {
        instanceData.putString(INTERVAL_ID_KEY,id);
        instanceData.putString(INTERVAL_NAME_KEY,name);
        instanceData.putInt(INTERVAL_AGE_KEY,age);
    }
}
