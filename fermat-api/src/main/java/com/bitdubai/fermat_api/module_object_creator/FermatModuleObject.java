package com.bitdubai.fermat_api.module_object_creator;

import java.io.Serializable;

/**
 * Created by mati on 2016.04.18..
 */
public class FermatModuleObject<O> implements FermatModuleObjectInterface, Serializable {

    int id;
    String name;


    public FermatModuleObject(Serializable creator) {
        creator(creator);
    }

    public FermatModuleObject(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public void creator(Serializable objectCreator) {

    }

    @Override
    public int describeContent() {
        return 0;
    }

    @Override
    public Serializable writeToSerializable() {
        return null;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("FermatModuleObject{")
                .append("id=").append(id)
                .append(", name='").append(name)
                .append('\'')
                .append('}').toString();
    }
}
