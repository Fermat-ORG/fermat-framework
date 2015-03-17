Functionality for Version 1
===========================

# Kids Wallet

## En la Plataforma

### 1. Plugin Blockchain info

Vamos a utilizar Blockchain.info como motor bitcoin. 

* Key para acceder a la API de blockchain info debe ser una constante a nivel del Plugin.

#### a. Creacion de la billetera

Vamos a crear una billetera en los sistemas de Blockchain.info que sera la que luego utilizaremos para el resto de las operaciones.

* El plugin va a guardar un archivo que contiene la informacion no transaccional de la billetera.
* Entre esa inforamcion deberia estar la clave privada de la misma.
* Tambien el Id de la billetera en nuestro sistema Fermat DMP, probablemente como nombre del archivo.
* Si la billetera tiene un usuario y clave aqui es donde se deberia guardar.


#### b. Monitoreo de pagos entrantes

Vamos a implementar un mecanismo para monitorear los pagos entrantes a la billetera. Para esto vamos a hacer lo siguiente:

* Cuando el servicio del Plugin se inicia, este debe localizar en un archivo los ids de las billeteras creadas por el plugin, todas, sin importar quien este logeado.
* Por cada billetera que encuentre en ese archivo, debe instanciar una clase BlockchainInfoBitcoinWallet.
* La interfaz de esa clase debe proveer los metodos startMonitoringPayments y stopMonitoringPayments . Le debe ejecutar el de start.
* Internamente la clase pone a correr un thread que cada x segundos (10 por ej), consulta el balance y los pagos entrantes en la direccion de la billetera.(inicialmente solo en la direccion de la billetera)
* Como la billetera lleva un archivo propio con todos los pagos entrantes, sabe cual fue el ultimo pago detectado desde la ultima vez que corrio este proceso.
* El archivo tambien lleva el status de dicho pago, en el sentido de si se llego a disparar el evento que lo anunciaba o no.
* Entonces, si detecta llamando a la API de blockchain info nuevos pagos, los escribe en el archivo si son nuevos, sino los ignora. De va leyendo las transacciones de la API hasta que encuentra que ya esta leyendo transacciones que ya tiene grabadas (lee de la mas reciente a la mas vieja)
* Cuando termina con lo anterior, arranca con la segunda parte de su procedimiento que es recorrer las transacciones del archivo que no estan anunciadas y disparar un evento por cada una. (Loui: este evento debe ser el mismo que disparaba el bitcoin crypto network. asegurate que ya exista y que este diponible para que Natalia lo dispare)
* Cada evento que dispara sin errores, marca la transaccion como ya anunciada y graba el archivo en disco por cada marca que hace.
* Luego verifica si el proceso no esta marcado para terminar, si lo esta termina la ejecucion de ese thread. Si no, se duerme por el tiempo configurado para luego despertar de nuevo y volver a repetir el proceso.

#### c. Entraga de Balance

Hasta ahora no tenemos previsto de que este modulo entregue el balance de la billetera para esta version 1.

#### d. Envio de Pagos

El plugin debe poder enviar una determinada cantidad de crypto a una direccion y disparar los eventos que determinan los distintos niveles de progreso del pago.

* El plugin dispone de un proceso corriendo en un thread separado que monitorea una tabla de transacciones pendientes de enviar que es arrancado cuando el plugin recibe el start en la inicializacionde la plataforma.
* Esa tabla funciona como una cola esperando a ser procesada.
* Cada vez que se despierta, este proceso lee los registros de la tablay los procesa uno a uno.
* Cuando lo somete a la red bitcoin a traves de la API de blockchain info lo agrega a una tabla con el historico de transacciones enviadas y lo elimina de la tabla de pendientes.
* Ademas de someter los pagos salientes su segunda funcion es monitorearlos hasta que alcancen una cierta cantidad de confirmaciones.
* A medida que el pago va pasando por distintos estados, este proceso va disparando diferentes eventos y cambiando el estado de la transaccion en la base de datos.



### 2. IncomingExtraUserTransactionPluginRoot

Este plug in es uno de los que se entera cuando llego un pago a una cryptowallet, aunque no es el unico. Para saber si es el quien tiene que procesar esta transaccion hace lo siguiente:

* Se fija en el CryptoAddressBook si la direccion en la que fue recibido el pago corresponde a algun usuario interno conocido, a algun usuario del dispositivo, o a algun usuario externo conocido.
* Necesariamente tiene que pertenecer a alguno de los tres grupos, porque una billetera crypto tiene que registrar su direccion generica como un usuario externo NN en el cryptoaddressbook antes de entregarsela a alguien por primera vez.
* Entonces si la direccion es la entregada a un usuario externo conocido o NN este modulo toma el evento para procesarlo el mismo.
* En este caso, guarda la transaccion en un archivo en una carpeta donde caen las transacciones en un estado sin aplicar y termina la ejecicion de ese thread. El sistema de eventos va a considerar la transaccion entregada.
* El mismo plugin cuando la plataforma lo puso a correr, tiene un proceso que cada cierto tiempo se despierta y se fija si hay archivos en esa carpeta y los empieza a procesar.
* Por cada transaccion hace dos cosas: le ejecuta el metodo creditFiat a la Middleware Wallet para que acredite el balance de esa cuenta y graba la transaccion es su historico de transacciones. (Que es una base de datos, no un archivo)
* El monto en fiat de la transaccion lo saca de un plugin llamado CrytoIndex, cuyo trabajo es monitorear el precio de las distintas crypto currencies contra las distintas fiat currencies y llevar un historico cada N segundos. 
* De esta manera antes de hacer el credito este plugin le consulta cuanto correspondia en fiat la cantidad de crypto recibida al momento en que la transaccion entro en la red bitcoin.


### 3. CryptoIndex Plugin

Este plug in consulta, en su primera version, el precio de bitcoin en algun indice publico y lo va guardando minuto aminuto. Para esta primera version, solo llevara Bitcoin vs Dollar.

* Hay que investigar cual es el indice con mayor sentido para utilizar.
* Lleva su informacion historica en una base de datos propia.
* Implementa un metodo para devolver el valor fiat de una determinada cantidad de crypto en un cierto momento en el tiempo.
* Si no dispone de esa informacion, porque estuvo apagado durante ese tiempo, devolveria un promedio.
* En un futuro cercano, si cuando arranca encuentra huecos en su historico, recuperaria esa informacion de otros peers a traves de un network service para tal fin.



* Cuando ocurre un evento de Incomming Crypto o algo asi, el handler del Plugin lo escucha y le ejecuta el metodo 

### 4. Plugin Middleware Wallet

Vamos a utilizar este plug-in para llevar la informacion de cuantos dolares tiene el niño en su billetera. No hace falta que implementemos toda la funcionalidad final, pero si lo necesario para llevar la cuenta de sus dolares.

#### a. Pago entrante

* La billetera tiene un metodo para recibir un credito por parte de alguno de los modulos que administras las transacciones que ocurren. Es de esa forma que un pago entra a este modulo.
* Para esta primera version vamos a recibir el pago en la unica cuenta que la billetera maneja. A futuro esto no va a ser asi.
* Como parte de la recepcion del pago, la wallet va a guardar la relacion entre crypto y fiat en un CryptoChunk en una base de datos propia de la wallet.

#### b. Consulta de Balance

* La bileltera provee un metodo para consultar el balance de sus cuentas. 
* Tambien provee la informacion respecto al balance disponible, para la cual tiene que preguntarle a CryptoIndex Plugin cual es la cotizacion actual y en base a eso calcular cuando sepuede gastar utilizando la informacion de lso CryptoChunks como base.

#### c. Pago saliente


### 5. BankNotesMiddlewarePluginRoot

Este plugin maneja la abstraccion de tener una billetera representada por los billetes y monedas que conforman el balance de la misma.Sabe de deonominaciones.
En esta primera version solo va a manejar dolares.

* De manera generica, los billetes estan en contenedores, luego la billetera puede interpretar cada contenedor como mejor le parezca para representarlo al usuario de una u otra manera.
* Uno de los contenedores, el primero, es el contenedor por defecto, y es donde cae el balance nuevo, por ejemplo cuando un pago es recibido.
* El modulo expone en su interfaz los metodos necesarios para que el cliente que consume sus servicios cambie los billetes de un contenedor a otro sin restricciones, las reglas, las define el cliente ante el usuario final.
* Cada vez que el fragmento se va a descargar, actualiza la posicion de los billetes en este modulo para que queden grabada (posicion, escala, rotacion).
* El plugin tambien es informado cuando se pasan los billetes de un contenedor a otro, asi lo persiste.
* El plugin escucha el evento Cambio de Balance de la MiddlewareWallet y ajusta la cantidad de billetes acorde al cambio. (Loui este evento no lo tenemos)





## En la billetera

### 1. Codigo QR de la billetera. 

En el tab me, cuando se toque en la foto se debe reemplazar por el codigo QR de la billetera bitcoin. Cuando se toque de nuevo debe volver la foto. La imagen del codigo de la billetera se obtiene originalmente de la pagina de blockchain.info, hay un URL para obtenerla. 

Al momento que se crea la billetera, el plug-in BlockchainInfo debe traer la imagen de esa billetera y guardarla en un archivo binario en la memoria externa del celular. Cuando el fragmento necesite mostrarla se lo debe pedir a este plugin, donde la billetera debe tener un metodo que no existe ahora para devolver la imagen.



### 2. El tab Balance

El tab balance en esta billetera se basa en la representacion grafica de los billetes de dolar. El fragmento que lo permite se basa en un plugin diseñado para manejar esta abstraccion.

* La posicion, escala, rotacion de cada billete en el espacio de la pantalla, el fragmento la consulta al plugin BankNotesMiddlewarePluginRoot . 



### 3. El tab Contacts

El tab contacts esta soportado dentro de la plataforma por el plugin WalletContacts. 

* Muestra los contactos asociados a esta billetera.