# Database Classes Generation
* The Script generates automatically the basic classes related with database with a few parameters to define.
* This are the classes:
 * Package database:
    * DatabaseConstants (class with the constants of the names of the tables, columns, primary key)
    * DatabaseFactory (factory for the creation of database)
    * DeveloperDatabaseFactory (methods for consulting database from the developer Sub-App)
 * Package exceptions:
    * CantInitializeDatabaseException (Exception when database cant initialize).
 
* Please respect the template using Upper and Lower case, only one space between the words.

* When you define each one of the tables, you must respect the format too.
* The table definition haves a format of maps and list, where each one of the objects or pairs are divided by "," (the pair is a "key:value").
* If you don't follow that, maybe not compile or the result wouldn't be the expected one.

* Parameters to fill:
```groovy
String developerName = "Leon Acosta" // your name
String developerMail = "laion.cj91@gmail.com" // your mail
String pluginName = "Wallet Factory" // name of the plugin
String layerName = "Middleware" // name of the layer to which the plugin belongs
String packageRoot = "com.bitdubai.fermat_ccp_plugin" // package root of the platform to which the plugin belongs
String jdkVersion = "1.7" // jdk version used


List databaseTables = [] // table list definition

// with each one of "<<" you add a new table.

databaseTables << [
    name: "Project", // name of the table
    columns: [ // columns of the table
        ["ID", "STRING", "36", "true"], // name of the column, data type, size, if is primary or not
        ["Developer Public Key", "STRING", "100", "false"],
        ["Name", "STRING", "100", "false"],
        ["Wallet Type", "STRING", "100", "false"]
    ],
    firstKey:"ID" // first index
]
```

For the execution of the script you can use the groovy client or the groovy console.

In this case, and to evade installing the client, you can use the groovy console online: [Groovy Console](http://groovyconsole.appspot.com/)
