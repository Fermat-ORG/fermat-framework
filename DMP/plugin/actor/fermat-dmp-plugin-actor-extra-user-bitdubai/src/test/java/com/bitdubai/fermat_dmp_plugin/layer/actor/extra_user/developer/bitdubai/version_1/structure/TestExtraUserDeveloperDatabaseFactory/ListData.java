package com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.TestExtraUserDeveloperDatabaseFactory;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;

import java.util.List;

/**
 * Created by francisco on 17/07/15.
 */
public class ListData {
    public List<DeveloperDatabase> getDatabaseList () {
        List<DeveloperDatabase> list = new java.util.ArrayList<DeveloperDatabase> ();
        list.add (new DeveloperDatabase () {
                      @Override
                      public String getName() {
                          return null;
                      }
                      @Override
                      public String getId() {
                          return null;
                      }
                  }
        );
        return list;
    }
   public List<DeveloperDatabaseTable> getDatabaseTableList () {
        List<DeveloperDatabaseTable> list = new java.util.ArrayList<DeveloperDatabaseTable> ();
        list.add (new DeveloperDatabaseTable () {
                      @Override
                      public String getName() {
                          return null;
                      }
                      @Override
                      public List<String> getFieldNames() {
                          return null;
                      }
                  }
        );
        return list;
    }
    public DeveloperDatabase getDatabase () {

        return new DeveloperDatabase () {
            @Override
            public String getName() {
                return null;
            }
            @Override
            public String getId() {
                return null;
            }
        };
    }

}