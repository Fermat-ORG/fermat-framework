![alt text](https://github.com/bitDubai/media-kit/blob/master/Readme%20Image/Fermat%20Logotype/Fermat_Logo_3D.png "Fermat Logo")

#--draft--

# Fermat Protocol

The Fermat Protocol is an open standard meaning that can be freely adopted, implemented and extended. The protocol defines how both Fermat nodes and clients should behave.  

We expect the protocol to evolve through time to address miss behaviours and mitigate attacks. The protocol may be adapted in response to proposed improvements and market feedback but all changes must be decided by consensus of its users.

<br>
## Fermat Protocol Version 1.0

The Fermat Protocol spans through Fermat Nodes and Fermat Clients. It defines the internal rules on each of them and also the rules of engagement between each other.

### Fermat P2P Protocol

#### Network Discovery

##### New Fermat Node

When a new node boots up for the first time, it must discover other Fermat nodes on the network in order to participate. To start this process, a new node must discover at least one existing node on the network and connect to it. The geographic location of other nodes is relevant; the Fermat network topology is geographically defined. But for the purpose of bootstraping, any existing Fermat node can be selected at random.

A bootstrapping node that knows nothing of the network must be given the IP address of at least one Fermat node, after which it can establish connections through further introductions. The Fermat Node reference implementation contains the IPs and port numbers of several different stable Fermat Node seeds.

Any of these IPs can be used to connect to one node just for introductions, using it as a seed. After the initial seed node is used to form introductions, the bootstrapping node will disconnect from it and use the newly discovered peers.

##### Old Fermat Node

To connect to a known peer, nodes establish a TCP connection, usually to port 9444 (the port generally known as the one used by Fermat), or an alternative port if one is provided. Upon establishing a connection, the node will start a "handshake".

##### Node to Node Dialogue 1: Node to Node Connection

| Node A        | Node B         | Notes  |
|:-------------:|:-------------:|:-----|
| Version       |               | Node A sends _Version_  message to Node B.|
|               | Version ACK   | Node B answers with _Version ACK_  message to Node A.|



##### Message Definitions

###### Message: Version

| Field        | Size          | Example  | Notes  |
|:-------------:|-------------:|:---------|:-------|
| Version       |               | FP2P v.1.0.0 | A constant that defines the Fermat P2P Protocol version the client node "speaks".|
| Implementation   |               | bitDubai v.1.0.0 | A constant that defines the name and version of the implementation the client node is running.|
| Your Address  |               | 123.32.45.31 | The IP address of the remote node as seen from this node.|
| My Address    |               | 111.44.15.1  | The IP address of the local node, as discovered by the local node.|


<br>
## Blockchain

Fermat's blockchain inherits many of the characteristics of the bitcoin blockchain. The data structure is an ordered, back-linked list of blocks of transactions. In our case all the transactions are coinbase transactions, meaning they are transactions where new fermats are issued by the protocol. Blocks are linked "back," each referring to the previous block in the chain. 

Each block within the blockchain:

* Is identified by a hash, generated using the SHA256 cryptographic hash algorithm on the header of the block. 
* References a previous block, known as the parent block, through the "previous block hash" field in the block header. 

## Mining and Consensus

Mining is the process by which new fermats are added to the token supply. Mining also serves to the main purpose of the Fermat Network: enable devices to communicate between each other without going through trusted third parties. Miners provide bandwidh to the Fermat network in exchange for the opportunity to be rewarded fermats.

Miners inter-connect devices and acts as a bridge relaying everything from one device to the other. A new block, containing transactions that occurred since the last block, is "mined" every 1 hour, thereby adding those transactions to the blockchain. Transactions that become part of a block and added to the blockchain are considered "confirmed," which allows the new owners of fermats to spend the fermats they received in those transactions.

Miners receive two types of rewards for mining: new tokens created with each new block, and subscription fees from all the network clients that use that node as a home. To earn this reward, the miners compete to sell bandwith to network clients, i.e. being their home node. Network Clients are free to choose which node to use as their home and at some point they pay in fermats to these nodes for their services. Fermat proof of work consist on a node proving that has received payments for being the home node. 

The amount of newly created fermats that can be added to a block decreases approximately every two years. It starts at 10,000 fermat per block and halves by 2 every 2 years. Based on this formula, fermat mining rewards decrease exponentially until when all fermats (350,000,000 million) have been issued. After that, no new fermats will be issued.




