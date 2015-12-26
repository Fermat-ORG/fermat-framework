![alt text](https://github.com/bitDubai/media-kit/blob/master/Readme%20Image/Fermat%20Logotype/Fermat_Logo_3D.png "Fermat
Logo")

<br><br>
## Introduction: Platform Chat
Se trata de una plataforma que agrega la funcionalidad de chat o mensajeria instantánea y que podrá ser integrada a cualquier actividad o transacción realizada dentro de Fermat. Cualquiera de los actores puede chatear uno con otro y puede estar asociado a cualquier tipo de objeto.<br>

## Funciones a manejar (Casos)

1. Mostrar Conexiones<br>
2. Mostrar Lista de Contactos<br>
3. Agregar Contacto<br>
4. Ver Detalle de Contacto<br>
5. Editar Contacto<br>
6. Borrar Contacto<br>
7. Mostrar Lista de Chats<br>
8. Enviar Mensaje<br>
9. Notificar Mensaje Visto<br>
10. Abrir Chat<br>
11. Listar mensajes de chats no vacíos<br>

## Sub-App

Los objetos que manejará la Sub-App para disparar acciones o mostrar infomracion son los sigueintes:<br>

1. ConnectionsList: Lista de conexiones.<br>
2. SelectConnectionButton: Botón para seleccionar conexión.<br>
3. MessagesList: Lista de acceso a mensajes.<br>
4. OpenChatButton: Botón para abrir chat.<br>
5. ChatForm: Entrada para escritura y envío de mensaje de texto.<br>
6. CheckMessageSymbol: Marca de mensaje leido.<br>
7. ContactsList: Lista para administrar contactos propios.<br>
8. AddContactButton: Botón para agregar contacto.<br>
9. EditContactButton: Botón para editar contacto.<br>
10. DeleteContactButton: Botón para eliminar contacto.<br>
11. ContactInfoForm: Form con la info del contacto (detalle).<br>
  
## Module

Mostrar Conexiones (Sub-App 1): Ejecuta un metodo para listar las conexiones.<br>
Seleccionar Conexión (Sub-App 2): Ejecuta un metodo solicitar la info de una conexion enviando como argumento su ID.<br>
Mostrar Lista de Contactos (Sub-App 7): Ejecuta un metodo para listar los contactos.<br>
Agregar Contacto (Sub-App 8): Ejecuta un metodo para listar las conexiones.<br>
Ver Detalle de Contacto (Sub-App 11): Ejecuta un metodo con el ID del contacto a detallar como argumento. <br>
Editar Contacto (Sub-App 9): Ejecuta un metodo con el ID del contacto a editar como argumento. <br>
Borrar Contacto (Sub-App 10): Ejecuta un metodo con el ID del contacto a eliminar como argumento. <br>
Mostrar Lista de Chats (Sub-App 3): Ejecuta un metodo para listar los chats enviando como argumento el UUID del objeto.<br>
Enviar Mensaje (Sub-App 5): Ejecuta un metodo para enviar el mensaje, sus argumentos serian aquellos que sirvan para alimentar la BD.<br>
Notificar Mensaje Visto (Sub-App 6): Ejecuta un metodo para validar el mensaje como leido enviando como argumento el UUID del objeto y el ID del chat. <br>
Abrir Chat (Sub-App 4): Ejecuta un metodo para la creacion o apertura de chat enviando el UUID del objeto como argumento.<br>

## Middleware

Tiene la siguiente base de datos:
### Base de Datos

#### Chats<br>
**Id Chat [integer]** <br>
**Id Objeto [integer]**<br>
**Local Actor Type [string]**<br>
**Local Actor Pub Key [string]**<br>
**Remote Actor Type [string]**<br>
**Remote Actor Pub Key [string]**<br>
**Chat Name [string]**<br>
**Status [integer]** : Invissible | Vissible<br>
**Creation Date [datetime]**<br>
**Last Message Date [timestamp]**<br>

#### Mensajes<br>
**Id Mensaje [integer]**<br>
**Id Chat [integer]**<br>
**MessageText [string]**<br>
**Status [integer]** : Created | Sent | Delivered | Read<br>
**Type [integer]** : Incomming | Outgoing<br>
**Message Date [timestamp]**<br>

#### Contactos<br>
**Id Contacto [integer]**<br>
**Remote Name [string]**<br>
**Alias [string]**<br>
**Remote Actor Type [string]**<br>
**Remote Actor Pub Key [string]**<br>
**Creation Date [datetime]**<br>

## Network Service
Esta funcionalidad cuenta con un NS que se encarga del envío y recepción de mensajes, y gestionar los estatus de los mismos. A su vez, se consumirán otros servicios relacionados a la consulta de las conexiones dentro de fermat entre los actores. 
### Protocolos

#### Creación del Chat

#### Envío de Mensaje

#### Notificación de Mensaje Visto


