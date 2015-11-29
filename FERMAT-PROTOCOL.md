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

## Mining

Mining is the process by which new fermats are added to the token supply. Mining also serves to the main purpose of the Fermat Network: enable devices to communicate between each other without going through trusted third parties. Miners provide bandwidh to the Fermat network in exchange for the opportunity to be rewarded fermats.

Miners inter-connect devices and acts as a bridge relaying everything from one device to the other. A new block, containing transactions that occurred since the last block, is "mined" every 1 hour, thereby adding those transactions to the blockchain. Transactions that become part of a block and added to the blockchain are considered "confirmed," which allows the new owners of fermats to spend the fermats they received in those transactions.

Miners receive two types of rewards for mining: new tokens created with each new block, and subscription fees from all the network clients that use that node as a home. To earn this reward, the miners compete to sell incoming bandwith to network clients, i.e. being their home node. Network Clients are free to choose which node to use as their home and at some point they pay in fermats to these nodes for their services. Fermat proof of work consist on nodes proving that have received payments for being home node. 

The amount of newly created fermats that can be added to a block decreases approximately every two years. It starts at 10,000 fermat per block and halves by 2 every 2 years. Based on this formula, fermat mining rewards decrease exponentially until when all fermats (350,000,000 million) have been issued. After that, no new fermats will be issued.

## Decentralized Consensus

The Fermat blockchain is not created by a central authority, but is assembled independently by every node in the network. Fermat defines a set of rules that defines which coinbase transactions are going to be added to the blockchain. As Fermat outsources the transaction processing features of the bitcoin network, it is easier for Fermat to arrive at a consensus. 

### Proof of Work

This are the rules to be followed:

* Each node scans the bitcoin blockchain for the last 6 block counting from the chain head - 6. 
* They search for fermat transactions and add all the ones that are payments to nodes in their node catalog.
* If they are between the 25% of the nodes that:

a. Received the biggest amount payed by adding all payments.
b. Have been payed by the biggest number of different transactions.
c. The sum of the bitcoin mining fees is the greatest.

Then they are allowed to propose themselves as canditates to receive new fermats by participating in the following race.

### Race to the Blockchain

All qualifying nodes creates a coinbase transaction racing between each other to be incorporated first by a bitcoin miner into the bitcoin blockchain. The three first 10 % of valid transactions to be incorporated at the bitcoin blockchain will be the ones recorded by every Fermat Node on the Fermat blockchain by adding them on a new block.

The sum of the amounts of all these transactions must not exceed the amount of fermats per block allowed by the Fermat Protocol.

As every node is reading confirmed bitcoin transactions and they all share a syncronized copy of the node catalogue, the Proof of Work algorithm should give exactly the same result to every node in the network. This means every node knows how many nodes are going to be part of the race, and how many fermats they should add on their own coinbase transaction in order for the 10% of all these nodes not to exceed the amount of fermats per block.

### Rationale

By using the fees payed by network clients as proof of works we discorage dishonest nodes to lie to the rest of the network about the work done. Of course a node operators can create fake fees to quilify for the race, but this will be at a cost and they are not guarantee that they will win the race. In fact to have better chances to win the race they will have to invest in bitcoin miners fees in order to be included first, again without any guarantee of being among the first 10%.

