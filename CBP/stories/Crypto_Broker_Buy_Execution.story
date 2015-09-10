Historia: Crypto Broker Buy Execution

Como deseo poder finalizar la compra establecida en la negociación con un Crypto Broker
 Como un Crypto Customer

Utilizando la Crypto Broker Wallet SubApp
  Y el plugin Receive Crypto Crypto Transaction
  Y el plugin Send Crypto Crypto Transaction
  Y el plugin Give Cash On Hand Fiat Cash Transaction
  Y el plugin Send Cash Delivery Fiat Cash Transaction
  Y el plugin Crypto Broker Market Crypto Wallet
  Y el plugin Crypto Broker Fiat Cash Wallet
  Y el plugin Crypto Customer Market Money Sale
  Y el plugin Crypto Customer Fiat Money Sale
  Y el plugin Receive Fiat Cash On Hand Fiat Cash Transaction
  Y el plugin Receive Fiat Cash Delivery Fiat Cash Transaction
  Y el plugin Receive Offline Fiat Bank Transfer Fiat Bank Transaction

Escenario: Crypto Customer envia pago al Crypto Broker utilizando Market Crypto.
  Dado que ya se cerro una negociacion con el Crypto Broker
    Y se establecio que el pago va a ser realizado con Market Crypto
  Cuando yo envie el pago a traves de la Crypto Customer Wallet
  Entonces la Crypto Customer Wallet debe registrar el envio usando el plugin Send Crypto Crypto Transaction
    Y el plugin Crypto Customer Market Crypto Wallet debe debitar el monto del balance
    Y el "Buy Negotiation Contract plugin" debe recibir una notificacion de la transaccion
    Y el "Buy Negotiation Contract plugin" debe actualizar su estado como PENDIENTE_ENTREGA
    Y se registra esta actualizacion en el Log Transaccional del "Buy Negotiation Contract plugin"
Contratos:
|!Buy Negotation Contract plugin          |
|Crypto Broker Market Money Buy Contract  |
|Crypto Broker Fiat Money Buy Contract    |

Escenario: Crypto Customer envia pago al Crypto Broker(?????)
  Dado que ya se cerro una negociacion con el Crypto Broker
    Y se establecio que el pago va ser realizado con Fiat Bank
  Cuando yo notifique que realice una transferencia para el pago en la cuenta especificada en la negociacion
  Entonces el crypto broker debe recibir la notificacion de transferencia

Escenario: Crypto Customer recibe Market Crypto del Crypto Broker.
  Dado que ya se cerro una negociacion con el Crypto Broker
    Y se establecio que la mercancia es Market Crypto
    Y el Crypto Broker envio la mercancia
  Cuando el Receive Crypto Crypto Transaction registre una transferencia a la direccion de la operacion
  Entonces Receive Crypto Crypto Transaction debe notificar a la  Crypto Customer Market Crypto Wallet de la transaccion
    Y la Crypto Customer Market Crypto Wallet debe registrar la transaccion como un credito
    Y la Crypto Customer Market Crypto Wallet debe actualizar su balance
    Y el plugin Crypto Broker Market Money Buy Contract debe recibir la notificacion de la transaccion
    Y el plugin Crypto Broker Market Money Buy Contract verifica que el monto de la transaccion sea el monto acordado
    Y el plugin Crypto Broker Market Money Buy Contract actualiza su estado como COMPLETADO
    Y se registra esta actualizacion en el Log Transaccional del Crypto Broker Market Money Buy Contract
    Y se envia una notificacion de estado COMPLETADO de la negociacion a traves del Crypto Broker Network Service

Escenario: Crypto Customer recibe notificacion de entrega de Fiat Cash
  Dado que ya se cerro una Negociacion
    Y que he efectuado el pago acordado
    Y que el Crypto Broker me ha entregado el Fiat Cash segun lo establecido en la Negociacion
  Cuando el Crypto Customer Network Service reciba un mensaje con una actualizacion del estado del Crypto Broker Fiat Money Buy Contract a ENTREGADO
  Entonces el Crypto Broker Fiat Money Buy Contract debe registrar esta actualizacion de estado a ENTREGADO
    Y se registra esta actualizacion en el Log Transaccional del Crypto Broker Fiat Money Buy Contract

Escenario: Crypto Customer recibe notificacion de transferencia Offline de Fiat Bank
  Dado que ya se cerro una Negociacion
    Y que he efectuado el pago acordado
    Y que el Crypto Broker ha realizado la transferencia Fiat Bank segun lo acordado en la Negociacion
  Cuando el Crypto Customer Network Service reciba un mensaje con una actualizacion del estado del Crypto Broker Fiat Money Buy Contract a ENTREGADO
  Entonces el Crypto Broker Fiat Money Buy Contract debe registrar esta actualizacion de estado a ENTREGADO
    Y se registra esta actualizacion en el Log Transaccional del Crypto Broker Fiat Money Buy Contract

Escenario: Crypto Customer marca como COMPLETADO una Negociacion
  Dado que ya se cerro una Negociacion
    Y el estado de la misma es ENTREGADO
  Cuando yo confirme la entrega en la Crypto Customer Wallet
  Entonces el "Buy Negotation Contract plugin" debe registrar la actualizacion de estado a COMPLETADO
    Y se registra esta actualizacion en el Log Transaccional del "Buy Negotation Contract plugin"
    Y se envia una notificacion de estado COMPLETADO de la Negociacion a traves del Crypto Broker Network Service
Contratos:
|!Buy Negotation Contract plugin          |
|Crypto Broker Market Money Buy Contract  |
|Crypto Broker Fiat Money Buy Contract    |

Escenario: Negociacion pagada excede Fecha de Entrega de mercaderia
  Dado que ya se cerro una negociacion con el Crypto Broker
    Y el estado de la Negociacion es PENDIENTE_ENTREGA
  Cuando la fecha ha pasado la Fecha de Entrega de Mercaderia establecida en la Negociacion
  Entonces el "Buy Negotation Contract plugin" debe cambiar el estado a PAGO_SIN_ENTREGA
    Y se registra esta actualizacion en el Log Transaccional del "Buy Negotation Contract plugin"
    Y se envia una notificacion de estado PAGO_SIN_ENTREGA a traves de la Crypto Broker Network Service
|!Buy Negotation Contract plugin          |
|Crypto Broker Market Money Buy Contract  |
|Crypto Broker Fiat Money Buy Contract    |
