package com.bitdubai.fermat_pip_plugin.layer.actor.developer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;

/**
 * Created by ciencias on 6/25/15.
 */
public class DeveloperActorDeveloperDatabase implements DeveloperDatabase {

    private String name;
    private String id;

    DeveloperActorDeveloperDatabase(String name,String id){
        this.name=name;
        this.id=id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getId() {
        return id;
    }
}
