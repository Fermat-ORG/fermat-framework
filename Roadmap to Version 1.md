Functionality for Version 1
===========================

# Kids Wallet

## En la Plataforma

### 1. Plugin Blockchain info

Vamos a utilizar Blockchain.info como motor bitcoin. 

* Key para acceder a la API de blockchain info debe ser una constante a nivel del Plugin.
* El plugin maneja un archivo con una lista de ids de las billeteras creadas.
* Cada vez que crea una billetera, genera un nuevo Id y lo guarda en el archivo.
* Cuando el Plugin arranca en el metodo start, debe leer el mismo archivo e instanciar la clase BlockchainInfoWallet por cada id que tenga ahi y ponerla a correr.
* Cuando el Plugin se detiene o pone en pausa debe ejecutar el stop de las billeteras que mantiene instanciadas.


#### a. Creacion de la billetera

Vamos a crear una billetera en los sistemas de Blockchain.info que sera la que luego utilizaremos para el resto de las operaciones.

##### clase BlockchainInfoWallet

* Cada billetera guarda un archivo binario con la siguiente informacion:
* La clave privada de la misma.
* Tambien el Id de la billetera en nuestro sistema Fermat DMP, probablemente como nombre del archivo.
* Si existiera alguna informacion a nivel de la billetera aqui es donde debiera guardarse.


* Esta clse en el metodo start de su interface intenta abrir una conexion con la base de datos y leer cada una de las tablas que esta base de datos debe contener para su correcto funcionamiento.
* Si la base de datos no existe entonces la crea.
* Si alguna de las tablas necesarias no existe, entonces la crea, verificando una a la vez.
* Luego poner a correr los distintos procesos que se explican mas abajo.


* Las tablas necesarias son: INCOMING_CRYPTO, OUTGOING_CRYPTO, CRYPTO_ADDRESSES


* INCOMING_CRYPTO guarda las transacciones que llegan a la billetera, en cualquiera de sus direcciones.


* Campos para la tabla INCOMING_CRYPTO: 
* String TRX_HASH: Es el equivalente al Id de la Transaccion y se obtiene de la API.
* LongInt AMOUNT : Cantidad de crypto recibida en Satoshis (es la unidad de medcion de bitcoin sin decimales)
* String CRYPTO_ADDRESS_TO : La crypto address donde se reciben los fondos.
* String CRYPTO_ADDRESS_FROM : La crypto address desde donde provienen los fondos.
* Int STATUS :  Se corresponde con el valor entero del enum IncomingCryptoStatus que declara los diferentes status (falta ponerle los valores al enum)
* Int CURRENT_CONFIRMATIONS: Guarda la cantidad de confirmaciones obtenidas desde la API
* Int PREVIOUS_CONFIRMATIONS: Es la lectura anterior. 


* OUTGOING_CRYPTO guarda las transaccones que salen de la billetera desde cualquiera de sus direcciones.
* ADDRESSES guarda las direcciones generadas para esta billetera.

##### Clave Privada

* Por ahora vamos a usar una llave privada fija inventada y luego vamos a investigar cual es el metodo ideal para crear una nueva por billetera.
* Ahora mismo la llave privada sera un UUID generado al momento en que la billetera se crea.

##### Pagos entrantes

* Para resolver la funcionalidad de pagos entrantes vamos a tener los siguientes procesos que seran ejecutados al momento en que cada billetera reciba la orden de arrancar en su metodo start.
* Osea que este conjunto de procesos correra por cada billetera blockchain que exista.


#### b. Monitoreo de pagos entrantes

Vamos a implementar un mecanismo para monitorear los pagos entrantes a la billetera. Para esto vamos a hacer lo siguiente:

* Cuando el servicio del Plugin se inicia, este debe localizar en un archivo los ids de las billeteras creadas por el plugin, todas, sin importar quien este logeado.
* Por cada billetera que encuentre en ese archivo, debe instanciar una clase BlockchainInfoBitcoinWallet.
* La interfaz de esa clase debe proveer los metodos startMonitoringPayments y stopMonitoringPayments . Le debe ejecutar el de start.

##### clase IncomingCryptoMonitorAgent

* La clase pone a correr un thread que se conecta a la api de blockchain explicada aca:
* https://blockchain.info/api/api_websocket
* y va a ejecutar el metodo para recibir transacciones sin confirmar :
* Subscribing to an Address  {"op":"addr_sub", "addr":"$bitcoin_address"}
* Cada vez que reciba una transaccion la va a guardar en la tabla de transacciones en la base de datos.
* Dicha tabla tiene que tener un campo IdWallet para ser multiwallet, CryptoAddress, Amount como longinteger , Timestamp con la fecha en formato unix, estado que va a describir el estado de procesamiento de la transaccion, "confirmaciones" la cantidad de confirmaciones en la red bitcoin y quizas algo mas
* Se debe guardar en un estado JUST_RECEIVED
* Este proceso verifica un flag de vez en cuando para ver si debe terminar su tarea.

##### clase IncomingCryptoCatchUpAgent

* Internamente la clase pone ademas pone a correr otro thread que hace lo siguiente:
* Cuando arranca por primera vez consulta en su propia base de datos la ultima transaccion registrada y procesada (estado ANNOUNCED) y la recuerda en memoria.
* Usando la API de blockchain info consulta el listado de transacciones para esta billetera.
* Esto esta explicado en https://blockchain.info/api/blockchain_api   
* La funcion que se usa es https://blockchain.info/rawaddr/$bitcoin_address
* Se va pidiendo la informacion por paginas, pidiendo tantas paginas como sea necesario hasta tener todas las transacciones que se produjeron mientras el dispositivo estaba apagado o la plataforma no estaba corriendo.
* Una vez que se tienen todas las transacciones en memoria se pasa al siguiente paso:
* Una por una se las agrega en la tabla de transacciones en el estado JUST_RECEIVED
* Se debe tener cuidado de que la transaccion no exista realmente en la tabla de transacciones al momento de ser insertada.

##### clase IncomingCryptoAnnouncerAgent

* Por ultimo la clase crea un tercer thread que es el que se encarga de procesar las transacciones en estado JUST_RECEIVED, una por una
* Este thread se despierta cada cierto tiempo (1 seg) y lee la transaccion mas vieja en estado JUST_RECEIVED
* Actualiza primero el estado de la transaccion como TO_BE_ANNOUNCED
* Luego dispara el evento apropiado anunciando al resto de los plugins de la plataforma que la transaccion fue recibida. 
* Si el disparo del evento fue exitoso, actualiza el estado a ANNOUNCED.
* Si el disparo del evento fallo, el estado debe actualizarse como ANNOUNCING_FAILED
* Verifica si el flag de terminar el proceso esta encendido y si lo esta termina, y si no se duerme para leugo arrancar de nuevo.

* Mas adelante, veremos si hace falta crear otro proceso que recupere las tranasacciones que quedaron en estado intermedio.

 el tiempo configurado para luego despertar de nuevo y volver a repetir el proceso.
 
##### clase TransactionAgeingMonitorAgent

* Esta clase se encarga de monitorear la evolucion en el tiempo de las transacciones de la billetera.
* Se despierta cada 7 minutos que es el tiempo promedio que le lleva a la red bitcoin procesar otro bloque de transacciones.
* Tiene definido una constante con valor 6 que es el maximo de confirmaciones que le interesa a la clase monitorear.
* Se conecta al api y solicita de manera paginada todas las transacciones de una cierta direccion
* La intencion es ver cuales de todas las transacciones en la base de datos que tienen menos de 7 confirmaciones, cambio su cantidad actual por otra.
* Las que ya pasaron mas de 6 confirmaciones en la base de datos las ignora.
* Para eso usa el mismo metoro https://blockchain.info/rawaddr/$bitcoin_address para obtener la informacion de las mismas.
* Con la lista de las transacciones en base de datos va buscando en el array recibido de la API
* Si la cantidad de confirmaciones cambio hace lo siguiente:
* Graba en el campo PREVIOUS_CONFIRMATIONS el valor que actualmente esta en CURRENT_CONFIRMATIONS y en este ultimo lo que recibe de la API
* Hace lo mismo con cada una de las transacciones que tenian menos de 7 confirmaciones y se vuelve a dormir 10 minutos luego de ver si debe parar.
 
 
##### clase TransactionAgeingAnnouncerAgent

* Esta clase se despierta cada un tiempo configurado en una constante de 1 minutos.
* Lee la tabla de transacciones filtrandola de la siguietne manera:
* Los registros deben tener PREVIOUS_CONFIRMATIONS < 7 OR CURRENT_CONFIRMATIONS < 7
* Si los dos campos anteriores son iguales, ignora esos registro porque no hubieron cambios.
* Si CURRENT_CONFIRMATIONS > PREVIOUS_CONFIRMATIONS entonces se debe disparar un evento de transaction ageing que ya voy a definir.
* Si  CURRENT_CONFIRMATIONS < PREVIOUS_CONFIRMATIONS entonces se debe disparar un evento de transaction rolled back ya voy a definir.
* Verifica si hay que terminar de correr, y si no, se duerme para luego empezar de nuevo.



#### c. Entraga de Balance

Hasta ahora no tenemos previsto de que este modulo entregue el balance de la billetera para esta version 1.

#### d. Envio de Pagos


*** Falta definir esto ***


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