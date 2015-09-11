Story: Crypto Broker Discovery

In order to buy merchandise from a Crypto Broker
  As a Crypto Customer
  I need to get select a Crypto Broker to Buy from
Using the Crypto Broker Community SubApp Android plugin
  And the Crypto Broker Community SubApp Module Java plugin
  And the Crypto Broker Actor Java plugin
  And the Crypto Customer Actor Java plugin
  And the Crypto Broker Network Service Java plugin
  And the Crypto Customer Network Service Java plugin

Scenario: Crypto Customer no ha tenido interacción con un Crypto Broker
  Dado que no he realizado ninguna Business Transactions con un Crypto Broker
  Cuando accedo a la lista de Crypto Brokers en la Crypto Broker Community SubApp
  Entonces el Crypto Broker Actor Java debe obtener una lista de Crypto Brokers a través del Crypto Broker Network Service
    Y el Crypto Broker Community SubApp Module debe obtener una lista de Crypto Brokers a través del Crypto Broker Actor Java
    Y el Crypto Broker Community SubApp debe mostrar la lista de Crypto Brokers ordenados por proximidad

Scenario: Crypto Customer ha tenido interacción con un Crypto Broker
  Dado que ya he realizado Business Transactions con un Crypto Broker
  Cuando accedo a la lista de Crypto Brokers en la Crypto Broker Community SubApp
  Entonces el Crypto Broker Actor Java debe obtener 2 listas de Crypto Brokers
    Y la primera lista debe ser de los Crypto Brokers con los que he establecido un contrato
    Y la segunda debe ser de todos los Crypto Brokers de la comunidad ordenados por proximidad
    Y en cada lista se debe visualizar la información publica de los Brokers

Scenario: Crypto Customer selecciona un Crypto Broker
  Dado que he visualizado la lista de Crypto Brokers en el Crypto Broker Community SubApp
  Cuando selecciono un Crypto Broker de la lista
       Y confirmo mi selección
Entonces el Crypto Broker Community SubApp Module envia al Crypto Broker seleccinado una solicitud para asociarse al Crypto Customer
       Y Crypto Broker Actor Java establece al Crypto Customer como un cliente para realizar Business Transactions

Scenario: Crypto Broker recibe una solicitud y la acepta
  Dado que el Crypto Broker Actor ha recibido una solicitud a través del Crypto Broker Network Service para asociarse a un Cryto Customer
     Y el Crypto Broker Community SubApp muestra la solicitud pasada por el Crypto Broker Actor
  Cuando selecciona aceptar la solicitud del Crypto Customer
Entonces el Crypto Broker Community SubApp Module envia al Crypto Customer la confirmación de la solicitud a través del Crypto Broker Network Service

Scenario: Crypto Customer recibe la notificación de aceptación del Crypto Broker
  Dado que el Crypto Customer Actor ha recibido la confirmación del Crypto Broker a través del Crypto Broker Network Service
     Y el Crypto Customer Community SubApp muestra la solicitud pasada por el Crypto Customer Actor
Entonces el Crypto Customer Actor Java debe establecer al Crypto Broker que acepto la solicitud como un Broker con el que puede realizar Business Transactions

Scenario: Crypto Broker recibe una solicitud y la rechaza
  Dado que el Crypto Broker Actor ha recibido una solicitud a través del Crypto Broker Network Service para asociarse a un Cryto Customer
     Y el Crypto Broker Community SubApp muestra la solicitud pasada por el Crypto Broker Actor
  Cuando selecciona rechazar la solicitud del Crypto Customer
Entonces el Crypto Broker Community SubApp elimina la solicitud para no mostrarla de nuevo al Broker


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
