El script cumple con la función de generar automáticamente las clases relacionadas con la base de
datos a partir de una serie de parámetros a definir.
Estas clases son:
DatabaseConstants (constantes con los nombres de tabla/ primary key / columnas)
DatabaseFactory (creación de la base de datos y las tablas)
DeveloperDatabaseFactory (métodos para consultar la base de datos creada desde la Developer SubApp)

Por favor respetar el template usando mayusculas y minusculas, un solo espacio entre las palabras
(para evitar inconvenientes).
También tener en cuenta que a la hora de crear cada una de las tablas:
* Posee un formato de map/list en donde cada uno de los pares o objetos están divididos por coma,
no olvidarla porque puede derivar en un problema de compilación.

Los parámetros a llenar son:

```groovy
String developerName = "Leon Acosta" // tu nombre
String developerMail = "laion.cj91@gmail.com" // tu correo
String pluginName = "Wallet Factory" // Nombre del plugin
String layerName = "Middleware" // nombre de la capa a la que pertenece el plugin
String packageRoot = "com.bitdubai.fermat_ccp_plugin" // package root con la plataforma a la que pertenece el plugin
String jdkVersion = "1.7"


List databaseTables = []

// con cada uno de estos add (<<) se agrega una nueva tabla

databaseTables << [
    name: "Project", // nombre de la tabla
    columns: [ // columnas
        ["ID", "STRING", "36", "true"], // nombre de la columna, tipo de dato, tamaño, si es primary o no
        ["Developer Public Key", "STRING", "100", "false"],
        ["Name", "STRING", "100", "false"],
        ["Wallet Type", "STRING", "100", "false"]
    ],
    firstKey:"ID" // index
]
```

Para la ejecución del mismo pueden utilizar el cliente groovy/la consola groovy o directamente de
forma online a través de alguna groovy console online.

Lo más sencillo es usar la consola online: http://groovyconsole.appspot.com/

