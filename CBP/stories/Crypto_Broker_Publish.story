Scenario: Publicacion de Informacion del broker 
  Dado que como Crypto Broker deseo darme a conocer en la comunidad para ofercer mis servicios  
  Cuando decido publicar mi identidad como broker para ser descubierto por un Crypto Customer 
  Entonces voy a la Subapp del Crypto Broker 
    Y establesco que datos de mercado deseo mostrar a los Crypto Customers (moneda de referencia, mercaderia, montos maximos, metodos y monedas que acepta de pago)
    Y en la Subapp del Crypto Broker Selecciono la opcion de publicar mi informacion en la plataforma
    Y la SubApp del Crypto Broker le sede el control al Module de la Subapp del Cryto Broker
    Y el Module de la Subapp del Cryto Broker invoca al Plugin Crypto Broker Identity Wallet Linker
    Y el Plugin Crypto Broker Identity Wallet Linker registra los datos de mercado deseo mostrar con mi identidad 
    Y el Module de la Subapp del Cryto Broker invoca al Plugin Crypto Broker de la capa Actor
    Y el Plugin Crypto Broker de la capa Actor registra mi identidad
    Y el Plugin Crypto Broker de la capa Actor se encarga de activar mi identidad colocandome en linea 
    Y el Plugin Crypto Broker de la capa Actor le notifica el Module de la SubApp del Crypto Broker que estoy en linea 
    Y el Module de la SubApp del Crypto Broker hace que la SubApp del Crypto Customer me muestre en la lista de brokers que puede consultar el crypto customer 

........................  

Story: Crypto Broker Publish

In order to be discovered by Crypto Customers
  As a Crypto Broker
  I need to publish my Public Identity
  And my sale prices
Using the Crypto Broker SubApp Android
  And the Crypto Broker SubApp Module Java Plugin
  And the Crypto Broker Network Service Java Plugin
  And the Crypto Broker Agent Java Plugin
  And the Fiat Index World Java Plugin
  And the Market Index World Java Plugin

Scenario: Public Information Publish
  Given I have Crypto Broker Identity
  When I decide to publish my Crypto Broker Identity in the Crypto Broker SubApp
    And I select the "Fiat Money Type for Price Reference"
    And I select the "Stocked Fiat Money Types"
    And I select the "Stocked Market Money Types"
    And I select the "Fiat Money Types for Payment"
    And I select the "Market Money Types for Payment"
    And I specify the maximum "Fiat Money Amount" that I support per operation
    And I specify the maximum "Market Money Amount" that I support per operation
  Then the Crypto Broker SubApp obtains the exchange rates between my "Stocked Fiat Money" and the "Fiat Money Type for Price Reference" from the Fiat Index World
    And the Crypto Broker SubApp obtains the exchange rates between my "Stocked Market Money" and the "Fiat Money Type for Price Reference" from the Market Index World
    And the Crypto Broker SubApp should register this information in the Fermat Network through the Crypto Broker Network Service

Scenario: Business Transaction Public Information Update
  Given I have performed a "Business Transaction"
  When the "Business Transaction" is completed
    And the Crypto Broker Agent consolidates the data of the Business Transactions
  Then the Crypto Broker Agent should update the exchange rates between my "Stocked Fiat Money" and the "Fiat Money Type for Price Reference" in my Crypto Broker Public Identity
    And the Crypto Broker Agent should update the exchange rates between my "Stocked Fiat Money" and the "Fiat Money Type for Price Reference" in my Crypto Broker Public Identity
    And the Crypto Broker Agent should publish this update through the Crypto Broker Network Service

--------


Public Identity Crypto Broker Example:
- Photo
- Alias
- Market Money exchange rates
  - Bitcoin: 230 USD
  - Litecoin: 10 USD
- Fiat Money exchange rates
  - Bolivares: 630 x 1 USD
  - Pesos Argentino: 14 x 1 USD
  - Euros: 1,2 USD
- Market Money Accepted Payments
  - Bitcoin
  - Litecoin
  - Ethereum
- Fiat Money Accepted Payments
  - Forines Hungaros
  - Dolares Estadounidenses
  - Pesos Colombianos

--------

Cada ves que se crea una identidad de criptobroker el criptobroker actor va a registrar dicha actividad como en linea, si la identidad esta activa, y eso es lo que le va a permitir a los customers encontrar al broker y enviarles solicitudes de coneccion que el broker puede aceptar o rechazar.

la activacion y en consecuencia publicacion de mi identidad como crypto broker lo hace el plugin crypto broker de la capa actor, y el usaurio es el que selecciona cuando activarla. dentro de la data que va a mostrar se encuentra 

En su forma pura, la informacion de moneda de referencia, mercaderia y montos maximos, metodos y monedas que acepta de pago, no forman parte de la identidad de un actor 

Cada ves que el usuario cree una identidad, el plugin actor crypto broker va a tener una referencia a cada una de las bases de datos que use para registrar toda la data del actor, ademas de la identidad  

hay que identificar a donde va cada una de esa data complementaria, de la cual el plugin crypto broker de la capa actor tiene una referencia

un broker puede o no agregar toda la informacion de negocio complementaria

El broker lo que hace es publicaf que esta en linea, el cliente cuando ve la list ade broker en linea no es que manualmente tiene que decirle nada, automaticamente el sistema de cliente puede le puede pedir a cada uno de los brokers del listado cual es su informacion de mercado

el problema de conocer la informacion de mercado del broker esta en que puedes tener multiples wallets para una misma identidad y no sabrias de cual wallet tomar los valores de mercado

para completar la informacion de mercado, cuando como customer yo selecciono a un broker, el dispositivo del customer tiene que solicitarle la informacion el dispositivo del broker 

primero te aparece la que yo soy un broker en la lista de brokers 

si un broker quiere mostrar informacion de mercado, como las tasas a las que vende, y la mercaderia que ofrece, se puede desarrollar un plugin que se dedique a establecer las relaciones entre las wallets y las identidades, es decir, que identidades van contra que wallets por defecto, para que cuando se de el caso de que un crypto broker configure que desea mostrar mas informacion que solo su alias y foto, ese plugin va a leer las distintas wallets asociadas a la identidad del broker y alli va a deducir que productos ofrece en base a esas wallets, y va a deducir las tasas de cambio en base a las operaciones que se llevan en esas wallets, por lo que tu puedes mostrar diferentes tasas para las wallets que manejen difente tipo de mercaderia, asi como las diferentes mercaderias que manejas, todo esto configurado por el broker cuando desea publicar

........................
lo que entedi del video hasta ahora es que ese plugin, que mas bien parece un agente por todo que tiene que hacer

es que a nivel del publish, el broker selecciona que info de mercado quiere mostrar (monedas que vende, tasas de referencia) y cuando le da a publicar, se tiene que registrar en ese plugin que informacion desea mostrar el broker, para que al momento del discovery, ese plugin tome en consideracion esas preferencias del broker para que obtenga la info que necesita

por lo que en el crypto broker actor no se van a guardar las referencias a las tablas donde se cuentre esa informacion (o pudiera hacerlo [preguntar a luis]), sino que solo va a registrar lo que el ya sabe, que es la identidad del broker, la otra info complementaria la puede registrar el plugin de relacion wallet-identidad

si el broker indico en el proceso de publis que ademas de mi identidad (foto + alias) deseo mostrar las monedas con las que negocio, yo agrego un registro en ese plugin indicando que este broker desea mostrar las monedas con las que negocia

cuando el customer entre en el proceso de discovery, al seleccionar al broker, la subapp debe llamar su modulo para qu este invoque al plugin de las relaciones y verifique si para la identidad del broker existen preferncias registradas, de tal forma que si existen, el plugin ejecute la logica para obtener esa informacion

en el caso de este ejemplo que pongo seria obtener las monedas que usa como mercaderia el broker, por lo que tendria que verificar las wallets asociadas a la identidad del broker y obtener la lista de mecarderia que ofrece, eso significa asignar ese valor que es una lista a la preferencia


........................  




