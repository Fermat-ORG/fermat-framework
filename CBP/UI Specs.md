# Crypto Broker Wallet

## Home

Actividad con dos Tabs:

* Lista de Cosas Pendientes que debo resolver (Tab 1)
    * Negociaciones (Contracts) y Ejecuciones (Deals) Pendientes que necesitan de mi interaccion
    * Esta lista va a estar ordenada de mas antiguo a mas nuevo
    * Cada Item pude tener el design de las fichas de fermat.org
    * Al pulsar sobre el item me dirige al detalle de la negociacion o ejecucion
    * Cada item de la lista debe tener un boton que te permita relizar acciones rapidas, dependiendo del estado del Deal o el Contract

* Stock (Tab 2)
    * Grafico de barras donde se muestra como un historico del stock dia a dia
    * El grafico se puede deslizar por dia, hacinedo que se seleccione una barra
    * Mostrar detalle de la barra seleccionada
    * Tener un spinner donde pueda selecciar la mercaderia que deseo revisar
    * Por defecto va a ser Crypto
    * En la grafica se muestre como una proyeccion del stock si se se cierran todos las negociaciones
    * Mostrar colores para el nivel de stock:
	    * Verde las barras que estan por encima del nivel minimo
	    * Rojo las barra que estan por debajo
	    * Otro color cuando estoy en un rango que es muy cercano al nivel

## Open Deals

Actividad que te muestra de todas los Deals abiertas

Se manejan dos listas:

* Wating for Your Response
* Wating for Customer Response

Estas listas van a estar ordenadas por antiguedad, las mas viejas aparecen primero

Cada item de la lista debe tener un boton que te permita realizar acciones rapidas, dependiendo del estado del Deal

## Deals History

Actividad que muestra de todas los Deals en cualquier estado

## Deal Details

Actividad que muestra el detalle de un Deal y permite realizar acciones sobre ese Deal

La informacion del Contract aparece como solo lectura, pero permite registrar pagos o envios

* El detalle de los Deals cerrados o cancelados no podra ser editable

## Open Contracts

Actividad que te muestra los Contracts abiertos

Se manejan dos listas:

* Wating for Your Response
* Wating for Customer Response

Estas listas van a estar ordenadas de mas antiguo al mas nuevo

## Contracts History

Actividad que muestra de todas los Contracts en cualquier estado

La lista de los contratos estaran ordenedas por antiguedad

## Contract Details

Actividad que muestra el detalle de un Contract y permite realizar acciones sobre ese Contract

Se puede editar informacion del Contract si esta esta en un estado que lo permita

* El detalle de los Contract cerrados o cancelados no podra ser editable

## Settings

Una actividad donde se permite editar

* Nivel de stock: se toma por defecto la cantidad del ultimo replenishment pero puede ser modificado
* Moneda de referencia: Moneda que voy a usar como referencia
* Tasas de referencia: tasas de referencia para las diferentes mercancias que manejo
* Tipos de Pago aceptados: pagos que pueden ser aceptados 
	* Crypto
	* Cash in hand
	* Cash delivery
	* Bank
* Informacion Publica: que informacion de mercado se desea publicar

## Sidebar

Navigation Drawer para acceder a las diferentes actividades de la Wallet:

* Home
* Open Deals
* Open Contracts
* Deals History
* Contracts History
* Contract Deals
* Settings


# Crypto Customer Wallet

Los Request, Contracts y Deals son indistintos a ojos del Customer, el lo tiene que ver todo como un mismo proceso,
No es necesario establecer una distincion al nivel de la UI entre estos elementos, estos pueden ser tratar como Negotiations

## Home

Actividad que muestra el balance actual de Crypto que tengo en la Wallet y una lista de Negotiations que esperan respuesta

* Ha de estar conformada por los siguientes elementos:
     * Balance actual de Crypto Currency en la Wallet
     * Lista de Negotiations que esperan respuesta por parte del customer
     * Lista de Negotiations que necesitan respuesta del broker

* La lista de Contracts y Deals ha de estar ordenadas del mas antiguo, al mas nuevo
* Lista con el resto de las cosas abiertas que esperan respuesta del broker

## Negotiations History

Actividad que muestra la lista de todos los Negotiations. Permite acceder a cada Request, Deal o Contract y ver el detalle de los mismos

## Contract Details

Actividad que muestra el detalle de un Contract y permite realizar acciones sobre ese Contract

* Se puede editar informacion del Contract si esta esta en un estado que lo permita
* El detalle de los Contract cerrados o cancelados no podra ser editable

## Deal Details

Actividad que muestra el detalle de un Deal y permite realizar acciones sobre ese Deal

* La informacion del Contract aparece como solo lectura, pero permite regitrar pagos o envios
* El detalle de los Deals cerrados no podra ser editable

## Brokers

Actividad para admnistrar los Broker con los que he establecido conexion

* Se muestra lista de brokers que he definido como contactos para esta wallet
* Estos contactos son un subconjunto de los Brokers con los que he establecido conexion en la SubApp Crypto Broker Community
* La lista han de aparecer primero aquellos con los que he realizado mas operaciones, es decir han sido mas activos
* Se ha de poder agregar un nuevo broker como contacto, tomando dicho broker de la lista de brokers con los que he establecido una conexion

## Sidebar

Navigation Drawer para acceder a las diferentes actividades de la Wallet:

* Home
* Negotiations History
* Brokers
