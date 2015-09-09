Story: Crypto Customer Sale Negotiation

Como deseo poder Vender mercancia a un Crypto Customer
  Como un Crypto Broker
  Debería realizar una negociación de venta

Utilizo Y el plugin Crypto Broker Wallet
  Y el plugin Crypto Broker Wallet Module
  Y el plugin Crypto Customer Actor
  Y el plugin Crypto Broker Network Service
  Y el plugin Crypto Customer Network Service

Escenario: El Crypto Broker recibe una Solicitud de Negociacion de Compra
  Dado que recibo una "Buy Negotiation Request" desde un Crypto Customer
  Cuando Ya Decida en que Crypto Broker Wallet procesare la solicitud de compra
  Entonces la Crypto Broker Wallet debera crear una "Sale Negotiation Contract" acordando el "Type of Merchandise" solicitada
Permutacion:
|Type of Merchandise  |Sale Negotiation Contract              |
|Market Money         |Market Money Buy Negotiation Contract  |
|Fiat Money           |Fiat Money Buy Negotiation Contract    |

Escenario: El Crypto Broker responde la solicitud de Negociacion de Compra
  Dado que recibo una solicitud para iniciar una "Buy Negotiation Contract" con un Crypto Customer a través del plugin Crypto Customer Actor
    Y decido en cuál de mis Wallet colocare la "Buy Negotiation Contract" dependiendo del "Type of Merchandise" que el Crypto Customer está solicitando
  Cuando creo un "Sale Negotiation Contract" en el Crypto Broker Wallet
     Y coloque en el Crypto Broker Wallet el precio para el "Type of Merchandise" que el Crypto Customer está solicitando
     Y coloque otra información como el "Payment Type" y la "Currency for the Payment"
  Entonces el  Crypto Broker Wallet debería Actualizar el "Sale Negotiation Contract"
     Y debe enviar un mensaje al Crypto Customer a través del plugin Crypto Customer Network Service con la Información del "Contract"
     Y añadir a una lista de pendientes de las negociaciones de venta en el Crypto Broker Wallet
     Y debe agregar un registro en el Log de transacciones de la "Sale Negotiation Contract"
Permutacion:
|Type of Merchandise  |Sale Negotiation Contract              |Payment Type   |Currency for the Payment
|Market Money         |Market Money Buy Negotiation Contract  |Bank           |Market Money
|Fiat Money           |Fiat Money Buy Negotiation Contract    |Cash on Hand   |Fiat Money
|                     |                                       |Crypto         |

Escenario: El Crypto Broker posponer la solicitud de venta Negociación
   Dado que recibo una solicitud para iniciar una negociación de un cliente a través de la Crypto Crypto Plugin Cliente Actor
     Y decido posponer la solicitud para iniciar la negociación
   Cuando aplazo una "Sale Negotiation Contract" en el Crypto Broker Wallet
   Entonces el Crypto Broker Wallet debería Actualizar la "Sale Negotiation Contract" y registrar que fue aplzada
     Y establecer la "Sale Negotiation Contract" en un estado de PAUSED
     Y debe agregar un registro en el Log de transacciones de la "Sale Negotiation Contract"

Escenario: El Crypto Broker proporciona información adicional a una negociación de venta
   Dado que he actualizado una "Sale Negotiation Contract" en el Crypto Broker Wallet
     Y ha sido revisado por el Cliente Customer
     Y el Crypto Customer ha proporcionado información adicional
   Cuando proporciono información adicional para el "Sale Negotiation Contract"
   Entonces el "Sale Negotiation Contract" debe actualizarse con la nueva información
     Y debe enviar un mensaje a través del plugin Crypto Customer Network Service con la información actualizada del Contrato al Crypto Customer
     Y debe ser visible dentro del Crypto Broker Wallet
     Y debe agregar un registro en el Log de transacciones de la "Sale Negotiation Contract"

Escenario: El Crypto Broker Cancela la solicitud de venta Negociación
   Dado que en el "Sale Negotiation Contract" surge algun inconveniente que amerita cancelar el contrato
   Cuando cancelo un "Sale Negotiation Contract" en el Crypto Broker Wallet
   Entonces el Crypto Broker Wallet debería Actualizar la "Sale Negotiation Contract" y registrar que fue Cancelada
     Y establecer la "Sale Negotiation Contract" en un estado de Cancelado
     Y no permirtir ningun cambio o adicion de informacion despues de esto
     Y debe agregar un registro en el Log de transacciones de la "Sale Negotiation Contract"

--Posible--
Escenario: El Crypto Broker Confirma los datos de Negociacion con el Crypto Customer
  Dado que acuerdo con el Crypto Customer la Fecha de pago
    Y acuerdo la Fecha de Entrega
    Y Otros datos relevantes para la "Sale Negotiation Contract"
  Cuando se indican estos datos en el "Sale Negotiation Contract"
  Entonces "Sale Negotiation Contract" debe actualizarce con el registro de la Fecha de Pago, la Fecha de Entrega y los otros datos
  Y debe ser visible dentro del Crypto Broker Wallet
  Y debe agregar un registro en el Log de transacciones de la "Sale Negotiation Contract"

Escenario: El Crypto Cliente acepta los términos de la "Compra Negociación del Contrato"
   Dado que he actualizado una "Sale Negotiation Contract" en el Crypto Broker Wallet
     Y ha sido revisada y actualizada por el Crypto Customer
   Cuando El Crypto Customer acepta los términos de la "Sale Negotiation Contract"
   Entonces el "Sale Negotiation Contract" debe ser marcado como ACEPTADO
     Y el Crypto Broker Wallet debería crear una "Business Transaction", según el "Type of Merchandise" y el "Type of Execution" acordado en la "Sale Negotiation Contract"
     Y "Business Transaction" debe ser visible dentro de la Crypto Broker Wallet como continuación de la Negociación
     Y debe agregar un registro en el Log de transacciones de la "Sale Negotiation Contract"
Permutacion:
|Type of Merchandise  |Buy Negotiation Contract               |Type of Execution  |Business Transaction  |
|Market Money         |Market Money Buy Negotiation Contract  |                   |                      |
|Fiat Money           |Fiat Money Buy Negotiation Contract    |                   |                      |
