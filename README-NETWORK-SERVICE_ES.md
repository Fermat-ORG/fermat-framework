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

* **Servicios de Red para Actores**, que son los encargados de gestionar las actividades reaccionadas con las identidades (Actores) que se encuentra dentro de la plataforma.

* **Servicios de Red** generales que son los encargados de gestionar lógicas de negocio u protocolos de transferencia de información.

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

#### Plug-in Issue Structure

La estructura inicial obligatoria es el siguiente: (nota: la palabra ISSUE no es parte del nombre)

##### ISSUE: '_Plugin Name_' - Plug-In

Esta es la raíz de la estructura de su problema y debe ser etiquetado como _SUPER ISSUE_. Está cerrado sólo cuando todos sus hijos y nietos están cerrados.

##### ISSUE: '_Plugin Name_' - Analysis

Esta es la raíz para el Análisis. Está cerrado cada vez que se lleven a cabo todos los análisis. Este Issue debe estar vinculado a la raíz de la estructura de tema.

1 - ISSUE: **'_ Plugin name_' - Caso de Usos**

Este es donde se especifica cada uno de los posibles casos de usos para cubrir el problema.

##### ISSUE: '_Plugin Name_' - Implementation

Esta es la raíz para la implemtación. Está cerrado cada vez que se lleven a cabo todos las implemntaciones. Este Issue debe estar vinculado a la raíz de la estructura de tema.

<br>
1 - ISSUE: **'_ Plugin name_' - Interfaces**

Este es donde se especifica cada uno de los posibles interfaces a implementar.

2 - ISSUE: **'_ Plugin name_' - Data Base**

Este es donde se especifica cada una de las componenetes de base de datos.

##### ISSUE: '_Plugin name_' - Pruebas

Esta es la raíz de pruebas. Está cerrado cada vez que se lleva a cabo todas las pruebas. Este ISSUE debe estar vinculado a la raíz de la estructura de tema.

* ISSUE: **'_ Plugin name_' - Pruebas - Pruebas Unitarias**

* ISSUE: **'_Plugin name_' - Pruebas - Pruebas de Integración**

##### ISSUE: '_Plugin name_' - QA
 
Esta es la raíz de control de calidad. Es cerrado cuando se pasan las pruebas de control de calidad. Este problema debe estar vinculado a la raíz de la estructura de tema.

Se espera que tenga aquí Issues hijos en la forma '_Plugin name_' QA - Bug Fix n, donde n es el número y el nombre de error.

##### ISSUE: '_Plugin name_' - Producción

Esta es la raíz de Producción. Se cierra cada vez que el plug-in alcanza producción. Se puede volver a abrir si se encuentran problemas de bugs en producción y cerrados de nuevo una vez que son solventados. Este Issue debe estar vinculado a la raíz de la estructura de tema.

Se espera que tenga aquí Issues hijos en la forma '_Plugin name_' Producción - Bug Fix n, donde n es el número y el nombre de error.

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

#### Convenciones para Nombres del Proyecto

El nombre de los proyectos siguen este patrón:

    fermat-[platform_name]-plugin-[network_service_type]-[name_of_the_project]-[org_name]

Where:                                                                               

- **platform_Name**: Se refiere a la plataforma en la que va a crear sus componentes.
- **network_service_type**: Se refiere al tipo de network service que se va a crear, Si es un para un actor o no.  
- **name_of_the_project**: Este es el nombre del proyecto.
- **org_name**: Este es el nombre de la empresa o organización promotora que está creando el proyecto, por ejemplo: ** bitdubai **.
 
He aquí un ejemplo:

    fermat-dap-plugin-actor-network-service-asset-issuer-bitdubai
    
    Dónde: **DAP ** es la plataforma, **plugin** es el tipo de componente, **actor-network-service** es el tipo de network service, **asset-issuer** es el nombre del proyecto y ** bitdubai ** es la organización responsable de los componentes de este proyecto. Esto significa que el proyecto es un plugin llamado Asset Issuer desarrollado  por BitDubai para la plataforma de la DAP.


#### ¿Qué hay dentro de un proyecto de componete Network Service?

Un proyecto de componentes de Network Service en Fermat tiene la siguiente estructura básica (Etiquetas: **+** carpeta, **>** paquete, **-** archivo):

    + fermat-[platform_name]-plugin-[network_service_type]-[name_of_the_project]-[org_name]
      - .gitignore
      - build.gradle
      - proguard-rules.pro
      + src
        + main
          + java
            > com.bitdubai.[project_type].[name_of_the_project]
              > structure
                > version_1
                  > structure
                    > communications
                    > database
                    > event_handlers
                    > exceptions
                - Developer.java
        + test
          + java
            > unit.com.bitdubai.[project_type].[name_of_the_project]

Donde:

- Dentro de la carpeta `src/main/java` se encuentra el paquete donde colocar los archivos de Java (clases, intefaces, enumeraciones ..)
- Dentro de la carpeta `test` va el código que se utiliza para hacer pruebas unitarias en las funcionalidades que usted está en desarrollo en la carpeta `src`.
- Las Pruebas unitarias se crean dentro del paquete `unit.com.bitdubai.[project_type].[name_of_the_project]` en el directorio `test/java`
- El archivo `build.gradle` es donde se definen las dependencias del proyecto con otras de las plataformas o con bibliotecas de terceros, pero no por defecto (las bibliotecas de soporte, por ejemplo).
- El archivo `Proguard-rules.pro` configura la herramienta Proguard. (para más información ver [este enlace] (http://developer.android.com/guide/developing/tools/proguard.html)). ** NOTA: ** * no configuramos este archivo en el momento, por lo tanto, está vacía *

### Añadir su proyecto el archivo settings.gradle

Al principio, cuando se crea el proyecto, no será reconocido como tal en la estructura de dependencias del proyecto raíz (Fermat) y se mostrará como un directorio más. Por lo que su proyecto debe ser incluido en la estructura de dependencias, es necesario añadir las siguientes líneas en el archivo `settings.gradle` que se encuentra en la carpeta de la plataforma en la que vas a trabajar:

<br>
```Gradle
include ':fermat-[platform_name]-plugin-[network_service_type]-[name_of_the_project]-[org_name]'
project(':fermat-[platform_name]-plugin-[network_service_type]-[name_of_the_project]-[org_name]').projectDir = new File('platform_name/plugin/project_type/fermat-[platform_name]-plugin-[network_service_type]-[name_of_the_project]-[org_name]')
```

<br>
He aquí un ejemplo de una parte del archivo `settings.gradle` para la plataforma DAP (`fermat/DAP/settings.gradle`):
<br>
```Gradle
...

// actor network service
include ':fermat-dap-plugin-actor-network-service-asset-user-bitdubai'
project(':fermat-dap-plugin-actor-network-service-asset-user-bitdubai').projectDir = new File('DAP/plugin/actor_network_service/fermat-dap-plugin-actor-network-service-asset-user-bitdubai')

include ':fermat-dap-plugin-actor-network-service-redeem-point-bitdubai'
project(':fermat-dap-plugin-actor-network-service-redeem-point-bitdubai').projectDir = new File('DAP/plugin/actor_network_service/fermat-dap-plugin-actor-network-service-redeem-point-bitdubai')

include ':fermat-dap-plugin-actor-network-service-asset-issuer-bitdubai'
project(':fermat-dap-plugin-actor-network-service-asset-issuer-bitdubai').projectDir = new File('DAP/plugin/actor_network_service/fermat-dap-plugin-actor-network-service-asset-issuer-bitdubai')

// network-service
include ':fermat-dap-plugin-network-service-asset-transmission-bitdubai'
project(':fermat-dap-plugin-network-service-asset-transmission-bitdubai').projectDir = new File('DAP/plugin/network_service/fermat-dap-plugin-network-service-asset-transmission-bitdubai')

...

```

<br>


## Part IV: Implementación

<br>



<br><br><br><br><br><br><br>

