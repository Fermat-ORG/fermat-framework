Story: Crypto Customer Sale Negotiation

Como deseo poder Vender mercancia a un Crypto Customer
  Como un Crypto Broker
Necesito realizar una negociación de venta

Utilizando el Customer Broker Purchase Request Plugin
  Y Crypto Broker Network Service Plugin
  Y Crypto Broker Reference Wallet
  Y Crypto Customer Actor Plugin

Escenario: El Crypto Broker recibe una Solicitud de Negociacion de Compra
  Dado que recibo una "Buy Negotiation Request" desde un Crypto Customer
  Cuando Ya Decida en que Crypto Broker Reference Wallet procesare la solicitud de compra
  Entonces el Customer Broker Purchase Request Plugin recibira mediante el Crypto Broker Network Service Plugin la "Buy Negotiation Request"
    Y el Crypto Broker Wallet Module Plugin gestionara la informacion recibida por el Customer Broker Purchase Request plugin
    Y el Crypto Broker Wallet Module Plugin procesara la Crypto Broker Reference Wallet escogida por el Crypto Broker
    Y el Crypto Broker Wallet Module Plugin crear una "Sale Negotiation Contract" acordando el "Type of Merchandise" solicitada
Permutación:
========================================================================
|Type of Merchandise  |Sale Negotiation Contract                       |
========================================================================
|Crypto Money         |Customer Broker Crypto Money Purchase Contract  |
|Fiat Money           |Customer Broker Cash Money Purchase Contract    |
|                     |Customer Broker Bank Money Purchase Contract    |
========================================================================


Escenario: El Crypto Broker responde la solicitud de Negociacion de Compra
  Dado que recibo una solicitud para iniciar una "Buy Negotiation Contract" con un Crypto Customer a través del Crypto Customer Actor Plugin
     Y decido en cuál de mis Wallet colocare la "Buy Negotiation Contract" dependiendo del "Type of Merchandise" que el Crypto Customer está solicitando
  Cuando  creo un "Sale Negotiation Contract" en el Crypto Broker Reference Wallet
     Y coloque en el Crypto Broker Reference Wallet el precio para el "Type of Merchandise" que el Crypto Customer está solicitando
     Y coloque otra información como el "Payment Type" y la "Currency for the Payment"
  Entonces el Crypto Broker Wallet Module Plugin debería Actualizar el "Sale Negotiation Contract"
     Y el Crypto Broker Wallet Module Plugin debe solicitar al Customer Broker Purchase Request Plugin que envie un mensaje al Crypto Customer a través del Crypto Customer Network Service Plugin con la Información del "Contract"
     Y el Crypto Broker Wallet Module Plugin debe añadir a una lista de pendientes de las negociaciones de venta en el Crypto Broker Reference Wallet
     Y el Crypto Broker Wallet Module Plugin debe agregar un registro en el Log de transacciones de la "Sale Negotiation Contract"
Permutacion:
=================================================================================================================|
|Type of Merchandise  |Sale Negotiation Contract                       |Payment Type   |Currency for the Payment |
=================================================================================================================|
|Crypto Money         |Customer Broker Crypto Money Purchase Contract  |Bank           |Crypto Money             |
|Fiat Money           |Customer Broker Cash Money Purchase Contract    |Cash on HY     |Fiat Money               |
|                     |Customer Broker Bank Money Purchase Contract    |Crypto         |                         |
=================================================================================================================|

Escenario: El Crypto Broker posponer la solicitud de venta Negociación
   Dado que recibo una solicitud para iniciar una negociación de un cliente a través de la Crypto Customer Actor Plugin
     Y decido posponer la solicitud para iniciar la negociación
   Cuando  aplazo una "Sale Negotiation Contract" en el Crypto Broker Reference Wallet
   Entonces el Crypto Broker Wallet Module Plugin debería Actualizar la "Sale Negotiation Contract" y registrar que fue aplzada
     Y el Crypto Broker Wallet Module Plugin debe establecer la "Sale Negotiation Contract" en un estado de PAUSED
     Y el Crypto Broker Wallet Module Plugin debe agregar un registro en el Log de transacciones de la "Sale Negotiation Contract"

Escenario: El Crypto Broker proporciona información adicional a una negociación de venta
   Dado que he actualizado una "Sale Negotiation Contract" en el Crypto Broker Reference Wallet
     Y ha sido revisado por el Customer
     Y el Crypto Customer ha proporcionado información adicional
   Cuando  proporciono información adicional para el "Sale Negotiation Contract"
   Entonces el Crypto Broker Wallet Module Plugin actualizara con la nueva información el "Sale Negotiation Contract"
     Y el Crypto Broker Wallet Module Plugin debe solicitar al Customer Broker Purchase Request Plugin que envie un mensaje al Crypto Customer a través del Crypto Customer Network Service Plugin con la información actualizada del Contrato al Crypto Customer
     Y el Crypto Broker Reference Wallet Android debe ser visible la informacion dentro del Crypto Broker Reference Wallet
     Y el Crypto Broker Wallet Module Plugin debe agregar un registro en el Log de transacciones de la "Sale Negotiation Contract"

Escenario: El Crypto Broker Cancela la solicitud de venta Negociación
   Dado que en el "Sale Negotiation Contract" surge algun inconveniente que amerita cancelar el contrato
   Cuando  cancelo un "Sale Negotiation Contract" en el Crypto Broker Reference Wallet
   Entonces el Crypto Broker Wallet Module Plugin debería Actualizar la "Sale Negotiation Contract" y registrar que fue Cancelada
     Y el Crypto Broker Wallet Module Plugin debe establecer la "Sale Negotiation Contract" en un estado de Cancelado
     Y el Crypto Broker Wallet Module Plugin no debe permirtir ningun cambio o adicion de informacion despues de esto
     Y el Crypto Broker Wallet Module Plugin debe agregar un registro en el Log de transacciones de la "Sale Negotiation Contract"

--Posible--
Escenario: El Crypto Broker Confirma los datos de Negociacion con el Crypto Customer
  Dado que acuerdo con el Crypto Customer la Fecha de pago
    Y acuerdo la Fecha de Entrega
    Y Otros datos relevantes para la "Sale Negotiation Contract"
  Cuando  se indican estos datos en el "Sale Negotiation Contract"
  Entonces el Crypto Broker Wallet Module Plugin debe actualizar el "Sale Negotiation Contract" con el registro de la Fecha de Pago, la Fecha de Entrega y los otros datos
  Y el Crypto Broker Reference Wallet Android debe ser visible la informacion dentro del Crypto Broker Reference Wallet
  Y el Crypto Broker Wallet Module Plugin debe agregar un registro en el Log de transacciones de la "Sale Negotiation Contract"

Escenario: El Crypto Customer acepta los términos de la "Compra Negociación del Contrato"
   Dado que he actualizado una "Sale Negotiation Contract" en el Crypto Broker Reference Wallet
     Y ha sido revisada y actualizada por el Crypto Customer
   Cuando  El Crypto Customer acepta los términos de la "Sale Negotiation Contract"
   Entonces el Crypto Broker Wallet Module Plugin debe marcar el "Sale Negotiation Contract" como ACEPTADO
     Y el Crypto Broker Wallet Module Plugin debería crear una "Business Transaction", según el "Type of Merchandise" y el "Type of Execution" acorDado en la "Sale Negotiation Contract"
     Y el Crypto Broker Reference Wallet Android debe hacer visible el "Business Transaction" dentro de la Crypto Broker Reference Wallet como continuación de la Negociación
     Y el Crypto Broker Wallet Module Plugin debe agregar un registro en el Log de transacciones de la "Sale Negotiation Contract"
Permutacion:
|Type of Merchandise  |Buy Negotiation Contract               |Type of Execution  |Business Transaction  |
|Market Money         |Market Money Buy Negotiation Contract  |                   |                      |
|Fiat Money           |Fiat Money Buy Negotiation Contract    |                   |                      |
