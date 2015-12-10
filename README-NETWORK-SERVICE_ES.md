![alt text](https://github.com/bitDubai/media-kit/blob/master/Readme%20Image/Fermat%20Logotype/Fermat_Logo_3D.png "Fermat Logo")

<br><br>

# NETWORK SERVICES

## Introducción

Comunicarse es una necesidad vital entre sistemas para ampliar sus funcionalidades e interacción entre dispositivos diferentes. En este caso Fermat no es la excepción. La comunicación es importante para la transmisión de datos y envío de información relevante.

Para satisfacer con esta importante necesidad se ha definido como parte de la solución los Servicios de Red. 


Los Servicios de Red entran en la categoría de Plug-in de los tipos de componentes definidos que podemos encontrar dentro del Sistema Fermat. Este tipo de Plug-in esta especializado a una tarea bien definida dentro de la arquitectura, el mismo actúa como un intermediario entre la capa de comunicación y los otros componentes de la plataforma, y de esta manera fungir como un servicio para los otros componentes.  

Como los Servicios de Red son los intermediarios, y son los que únicamente interactúan directamente con la capa de comunicaciones, ellos son los encargados de encapsular todas las tareas y lógica necesaria para esta interacción, y entre estas tareas básicas encontramos las siguientes:

 * Solicitar a la capa de comunicaciones el registro de otros componentes con el Servidor de la Nube (Cloud Server).
 * Solicitar a la capa de comunicaciones el descubrimiento y búsqueda de otros componentes registrados, y que están ejecutandose en otro dispositivo.
 * Solicitar a la capa de comunicaciones el establecer un nuevo canal de comunicaciones con otro componente especifico y poder enviar y recibir mensajes.
 * Mantener una administración de los canales establecidos.   
 * Recibir y almacenar los mensajes entrantes.
 * Enviar y almacenar los mensajes salientes.

Los Servicios de Red también tienen  la responsabilidad de definir un protocolo de comunicaciones basado en mensajes para la internación con otros Servicios de Red que se ejecuten en otro dispositivo corriendo el Sistema de Fermat. Un punto importante a tener en consideración en cuanto a los Servicios de Red, es que los mismos solo pueden tener interacción o comunicación  con otro Servicio de Red de su mismo tipo que se ejecute en otro dispositivo, para que así los mismos envíen y reciban mensajes de acuerdo a su protocolo definido y puedan entenderse.

<br>
## Part I: Conceptos

### Network Services

Es un servicio de red que pretende definir el protocolo y comportamiento para la comunicación entre los componentes de una plataforma que se ejecuta dentro del Fermat Framework. 

### Conexiones

Son los canales establecidos por la capa de comunicaciones para que los servicio de red envíen y reciban sus mensajes.

### Menssajes

Está definido como la información que el emisor envía al receptor a través de un canal de comunicación determinado, y el mismo tiene un sentido unico.

<br>

## Part II: Flujo de Trabajo

Esta sección le ayudará a entender el flujo de trabajo necesarios a seguir para implementar un Servicio de Red del Sistema Fermat.
<br>
### Organizarse
---------------------

#### Issues

Es obligatorio que se cree un conjunto inicial de GitHub Issues antes de continuar más con el flujo de trabajo. Esto le mostrará al resto de los equipos que alguien está trabajando en esta funcionalidad y evitar conflictos de trabajo desde el principio. También se enganchará el líder del equipo en su flujo de trabajo y que le permita orientar y asesorar a usted cuando sea necesario.

Una jerarquía básica de GitHub Issues se crean como un primer paso. Los temas están vinculados uno a otro con sólo colocar un enlace en el primer comentario.

##### Convenio de denominación de nombre

Cuando nos referimos a '_Plugin name_' lo que esperamos es la siguiente información:

* Plataforma o Super nombre de Capa - 3 caracteres.
* Nombre de la capa
* Nombre del Plug-in 

Todos ellos separados por "-".

##### Vincular a Issues padres

Los Issues que deben ser enlazados a su padre deben tener en su primera línea "Parent:" + enlace http al Issues padre.

##### Etiquetado el líder del equipo

Los jefes de equipo etiquetados en la segunda línea con el fin de pedirles que asignar el Issue a usted y al mismo tiempo de suscribirse a cualquier actualización del tema. Esto ayuda a los líderes de equipo para seguir los acontecimientos del Issue y proporcionar asistencia u orientación al ver algo mal. El formato sugerido es:

"@ líder de usuario Nombre del equipo, por favor asigne este tema para mí."

<br>
#### Plug-in Estructura de la emisión

La estructura inicial obligatoria es el siguiente: (nota: la palabra ISSUE no es parte del nombre)

<br>
##### ISSUE: '_Plugin name_' - Plug-In

Esta es la raíz de la estructura de su problema y debe ser etiquetado como _SUPER ISSUE_. Está cerrado sólo cuando todos sus hijos y nietos están cerrados.

<br>
##### ISSUE: '_Plugin name_' - Análisis

Esta es la raíz para el Análisis. Está cerrado cada vez que se lleven a cabo todos los análisis. Este problema debe estar vinculado a la raíz de la estructura de tema.

<br>
1 - ISSUE: ** '_ Plugin name_' - Caso de Usos **

Este es donde se especifica cada uno de los posibles casos de usos para cubrir el problema.

<br>

<br>
##### ISSUE: '_Plugin name_' - Pruebas

Esta es la raíz de pruebas. Está cerrado cada vez que se lleva a cabo todas las pruebas. Este ISSUE debe estar vinculado a la raíz de la estructura de tema.

* ISSUE: ** '_ Plugin name_' - Pruebas - Pruebas Unitarias **

* ISSUE: '_Plugin name_' - Pruebas - Pruebas de Integración
 
 
<br>
##### ISSUE: '_Plugin name_' - QA
 

Esta es la raíz de control de calidad. Es cerrado cuando se pasan las pruebas de control de calidad. Este problema debe estar vinculado a la raíz de la estructura de tema.

Se espera que tenga aquí Issues hijos en la forma '_Plugin name_' QA - Bug Fix n, donde n es el número y el nombre de error.

<br>
##### ISSUE: '_Plugin name_' - Producción

Esta es la raíz de Producción. Se cierra cada vez que el plug-in alcanza producción. Se puede volver a abrir si se encuentran problemas de bugs en producción y cerrados de nuevo una vez que son solventados. Este Issue debe estar vinculado a la raíz de la estructura de tema.

Se espera que tenga aquí Issues hijos en la forma '_Plugin name_' Producción - Bug Fix n, donde n es el número y el nombre de error.

<br>

### Estructura Proyectos
----------------------

Los componentes de los Servicios de Red pueden estar agrupados y distribuidos de la siguiente manera.

#### ¿Dónde poner sus proyectos
 
Cada vez que usted desea crear una nueva carpeta, SubApp o de escritorio, debe crear el proyecto que llevará a cabo los componentes GUI en cualquiera de los tres directorios que se muestran a continuación siguiendo esta estructura:
 
    + PLATFORM_NAME (3 Character)
      + plugin
        + actor_network_services
          - fermat-platformname-plugin-actor-network-service-name_1
          - fermat-platformname-plugin-actor-network-service-name_2
        + network_services
          - fermat-platformname-plugin-network-service-name_1
          - fermat-platformname-plugin-network-service-name_2
          - fermat-platformname-plugin-network-service-name_3

Donde:

- ** PLATFORM_NAME **: Se refiere a la plataforma en la que vas a crear sus componentes.
- ** plugin: Se refiere directorio o paguete donde van los componentes de tipo plugin.
- ** actor_network_services **: Se refiere directorio o paguete donde van los network services especializado para los actores.
- ** ** network_services: Se refiere directorio o paguete donde van los network services de tipo generales.

Aqui un ejemplo:

 + DAP
  + plugin
    + actor
    + actor_network_services
      - fermat-dap-plugin-actor-network-service-asset-issuer-bitdubai
      - fermat-dap-plugin-actor-network-service-asset-user-bitdubai
      - fermat-dap-plugin-actor-network-service-redeem-point-bitdubai
    + digital_asset_transaction
    + identity
    + middleware
    + network_services
      - fermat-dap-plugin-network-service-asset-transmission-bitdubai
    + sub_app_module
    + wallet
    + wallet_module

Como podemos observar en la estructura de la plataforma "Digital Asset Plataform (DAP)" cuenta con tres Servicio de Red de tipo Actor y un  Servicio de Red general para una tarea en especifico.

#### Project Names Conventions

The name of the projects follow this pattern:

    fermat-[platform_name]-[client_type]-[project_type]-[name_of_the_project]-[org_name]

Where:                                                                               

- **platform_Name**: Refers to the platform where you will create your components.
- **client_type**: Refers to the device where the client is going to create, either Android, IPhone, a web or desktop application, etc. At the moment the client we are using is Android, therefore the name of this folder is “android”.
- **project_type**: Refers to the type of project you’re goint to create GUI components for. They could be **desktop**, **reference-wallet** or **sub-app**.  
- **name_of_the_project**: This is the name of the project. For example: if your project is named **Crypto Broker Community** then you have to name it **crypto-broker-community**.
- **org_name**: This is the name of the developer organization o company that is creating the project, for example: **bitdubai**.  
 
Here’s an example:

    fermat-cbp-android-sub-app-crypto-broker-community-bitdubai

Where: **cbp** is the platform, **android** is the device, **sub-app** is the type of project, **crypto-broker-community** es is the name of the project and **bitdubai** is the organization responsible for the components of this project. This means that the project is a SubApp called Crypto Broker Community developed for Android devices and created by BitDubai for the CBP platform.


#### What's Inside an Android GUI Components Project

A GUI component project for Android in Fermat has the following basic structure (Label: **+** folder, **>** package, **-** file):

    + fermat-[platform_name]-[client_type]-[project_type]-[name_of_the_project]-[org_name]
      - .gitignore
      - build.gradle
      - proguard-rules.pro
      + src
        + main
          + java
            > com.bitdubai.[project_type].[name_of_the_project]
              > fragmentFactory
              > fragments
              > preference_settings
              > session
          + res
            + drawable
            + layout
            + menu
            + values
        + test
          + java
            > unit.com.bitdubai.[project_type].[name_of_the_project]

Where:

- Everything that goes in the `src` folder are files and resources you will need to develop your Wallet/SubApp/Desktop in Android.
- Inside `src/main/java` you will find the package where you will place java files (classes, intefaces, enums..) with your Android code. It has the following basic packages: **fragmentFactory**, **fragments**, **preference_settings** and **session**. Each one of them explained in detail later on in this README.
- Inside `src/main/res` there are `xml` files found that represent *layouts, menus, colors, strings and sizes* as well as image files and others that represent visual resources with which you’re going to interact the java classes that have an Android logic.
- Everything that goes in the `test` folder is code that is used to make Unit Testing on the funtionalities you’re developing in `src`.
- The Unit Test are created inside the package `unit.com.bitdubai.[project_type].[name_of_the_project]` in `test/java`
- The file `build.gradle` is where you define the dependencies of the project with others of the platforms or with third party libraries and those that Android offers but not as default (the Support Libraries for example). Also the minimal version of the OS is defined where the app is going to run like the SDK Android version that is going to be used among other things (for more information see [this link](http://developer.android.com/tools/building/configuring-gradle.html))
- The file `proguard-rules.pro` configures the Proguard tool. (for more information see  [this link](http://developer.android.com/guide/developing/tools/proguard.html)). **NOTE:** *we do not configure this file at the moment, therefore it is empty*

### Add to your project the file settings.gradle

At the beginning when you create your Android project to develop your Wallet/SubApp/Desktop, it won’t be recognized as such in the dependencies structure of the root project (Fermat) and it will show like one more directory. So your project is included in dependencies structure it is necessary to add the following lines in the file `settings.gradle` that is found in the folder of the platform where you’re going to work:

```Gradle
include ':fermat-[platform_name]-[client_type]-[project_type]-[name_of_the_project]-[org_name]'
project(':fermat-[platform_name]-[client_type]-[project_type]-[name_of_the_project]-[org_name]').projectDir = new File('platform_name/client_type/project_type/fermat-[platform_name]-[client_type]-[project_type]-[name_of_the_project]-[org_name]')
```
Here's an example of part of the file `settings.gradle` of the CBP platform (`fermat/CBP/settings.gradle`):

```Gradle
...

//Desktop
include ':fermat-cbp-android-desktop-sub-app-manager-bitdubai'
project(':fermat-cbp-android-desktop-sub-app-manager-bitdubai').projectDir = new File('CBP/android/desktop/fermat-cbp-android-desktop-sub-app-manager-bitdubai')
include ':fermat-cbp-android-desktop-wallet-manager-bitdubai'
project(':fermat-cbp-android-desktop-wallet-manager-bitdubai').projectDir = new File('CBP/android/desktop/fermat-cbp-android-desktop-wallet-manager-bitdubai')

//Reference Wallet
include ':fermat-cbp-android-reference-wallet-crypto-broker-bitdubai'
project(':fermat-cbp-android-reference-wallet-crypto-broker-bitdubai').projectDir = new File('CBP/android/reference_wallet/fermat-cbp-android-reference-wallet-crypto-broker-bitdubai')
include ':fermat-cbp-android-reference-wallet-crypto-customer-bitdubai'
project(':fermat-cbp-android-reference-wallet-crypto-customer-bitdubai').projectDir = new File('CBP/android/reference_wallet/fermat-cbp-android-reference-wallet-crypto-customer-bitdubai')

//Sub App
include ':fermat-cbp-android-sub-app-crypto-broker-community-bitdubai'
project(':fermat-cbp-android-sub-app-crypto-broker-community-bitdubai').projectDir = new File('CBP/android/sub_app/fermat-cbp-android-sub-app-crypto-broker-community-bitdubai')
include ':fermat-cbp-android-sub-app-crypto-broker-identity-bitdubai'
project(':fermat-cbp-android-sub-app-crypto-broker-identity-bitdubai').projectDir = new File('CBP/android/sub_app/fermat-cbp-android-sub-app-crypto-broker-identity-bitdubai')
include ':fermat-cbp-android-sub-app-crypto-customer-community-bitdubai'
project(':fermat-cbp-android-sub-app-crypto-customer-community-bitdubai').projectDir = new File('CBP/android/sub_app/fermat-cbp-android-sub-app-crypto-customer-community-bitdubai')
include ':fermat-cbp-android-sub-app-crypto-customer-identity-bitdubai'
project(':fermat-cbp-android-sub-app-crypto-customer-identity-bitdubai').projectDir = new File('CBP/android/sub_app/fermat-cbp-android-sub-app-crypto-customer-identity-bitdubai')
include ':fermat-cbp-android-sub-app-customers-bitdubai'
project(':fermat-cbp-android-sub-app-customers-bitdubai').projectDir = new File('CBP/android/sub_app/fermat-cbp-android-sub-app-customers-bitdubai')

//PLUG-INS
...

```

<br>



## Part IV: Implementación

<br>



<br><br><br><br><br><br><br>

