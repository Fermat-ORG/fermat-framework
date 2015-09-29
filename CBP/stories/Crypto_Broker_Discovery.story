Historia: Crypto Broker Discovery

Para comprar mercancía a un Crypto Broker
  Como un Crypto Customer
  Necesito poder seleccionar un Crypto Broker a quien comprarle
Usando el Crypto Broker Community SubApp Android
  Y el Crypto Broker Community SubApp Module Plugin
  Y el Crypto Broker Actor Plugin
  Y el Crypto Customer Actor Plugin
  Y el Crypto Broker Network Service Plugin
  Y el Crypto Customer Network Service Plugin

Escenario: Crypto Customer no ha tenido interacción con un Crypto Broker
  Dado que no he realizado ninguna Business Transactions con un Crypto Broker
  Cuando accedo a la lista de Crypto Brokers en la Crypto Broker Community SubApp Module Plugin
  Entonces el Crypto Broker Actor Plugin debe obtener una lista de Crypto Brokers a través del Crypto Broker Network Service Plugin
    Y el Crypto Broker Community SubApp Module Plugin debe obtener una lista de Crypto Brokers a través del Crypto Broker Actor Java
    Y el Crypto Broker Community SubApp Module Plugin debe mostrar la lista de Crypto Brokers ordenados por proximidad

Escenario: Crypto Customer ha tenido interacción con un Crypto Broker
  Dado que ya he realizado Business Transactions con un Crypto Broker
  Cuando accedo a la lista de Crypto Brokers en la Crypto Broker Community SubApp Module Plugin
  Entonces el Crypto Broker Actor Plugin debe obtener 2 listas de Crypto Brokers
    Y la primera lista debe ser de los Crypto Brokers con los que he establecido un contrato
    Y la segunda debe ser de todos los Crypto Brokers de la comunidad ordenados por proximidad
    Y en cada lista se debe visualizar la información publica de los Brokers

Escenario: Crypto Customer selecciona un Crypto Broker
  Dado que he visualizado la lista de Crypto Brokers en el Crypto Broker Community SubApp Android
  Cuando selecciono un Crypto Broker de la lista
       Y confirmo mi selección
Entonces el Crypto Broker Community SubApp Module Plugin envia al Crypto Broker seleccinado una solicitud para asociarse al Crypto Customer
       Y Crypto Broker Actor Plugin establece al Crypto Customer como un cliente para realizar Business Transactions

Escenario: Crypto Broker recibe una solicitud y la acepta
  Dado que el Crypto Broker Actor Plugin ha recibido una solicitud a través del Crypto Broker Network Service Plugin para asociarse a un Cryto Customer
     Y el Crypto Broker Community SubApp Android muestra la solicitud pasada por el Crypto Broker Actor Plugin
  Cuando selecciona aceptar la solicitud del Crypto Customer
Entonces el Crypto Broker Community SubApp Module Plugin envia al Crypto Customer la confirmación de la solicitud a través del Crypto Broker Network Service Plugin

Escenario: Crypto Customer recibe la notificación de aceptación del Crypto Broker
  Dado que el Crypto Customer Actor Plugin ha recibido la confirmación del Crypto Broker a través del Crypto Broker Network Service Plugin
     Y el Crypto Customer Community SubApp Android muestra la solicitud pasada por el Crypto Customer Actor Plugin
Entonces el Crypto Customer Actor Plugin debe establecer al Crypto Broker que acepto la solicitud como un Broker con el que puede realizar Business Transactions

Escenario: Crypto Broker recibe una solicitud y la rechaza
  Dado que el Crypto Broker Actor ha recibido una solicitud a través del Crypto Broker Network Service Plugin para asociarse a un Cryto Customer
     Y el Crypto Broker Community SubApp Android muestra la solicitud pasada por el Crypto Broker Actor Plugin
  Cuando selecciona rechazar la solicitud del Crypto Customer
Entonces el Crypto Broker Community SubApp Module Plugin elimina la solicitud para no mostrarla de nuevo al Broker


Ejemplo de la identidad publica Identidad de un Crypto Broker:
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
