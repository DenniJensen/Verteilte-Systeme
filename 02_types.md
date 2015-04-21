##Types of distributed systems
1. Computing (cluster computing)
2. distributed information systems (databases)
3. distributed pervasive system
  - small, wireless adhoc, no administration
    (home autmation, health systems, sensor networks)

###System models
1. Physical models
2. Architectural models
3. Fundamental models (Formal models, mathematical models)

###Architectual models, patterns, styles
1. Layers
  * layers(hardware, OS, Middleware, Appl.)
  * tiers (2-tier: client-server, 3-tier: user-appl.-database)
  * brokerage of webservices

  - how to split software into components?
    - software architecture
  - how build a system out of the components?
    - system architecture
  - Middelware can help create *distribution transparency*

2. Object-based Architectures
  - interaction between components
  - remote procedure call (rpc)
  - can be client-server system

3. Data-centered architectures
  - data is key element
  - communication over data, distributed DB
  - web-system mostly data centric

4. Event-based Architectures
  - processes communicate through events
  - publish-subscribe systems
  - **loose coupling** (publisher and subscriber need not to know each other)
    (decoupled in sapce)
  - **scalablity** - sometimes better than client-server (parallelt operation,
    message caching,..)

###System architectures
1. centralised architecture
  Client ----------------------------> t
          \request /response
  Server ----------------------------> t

  1. unreliable network/node
  possible solutions
  - retransuit
  - can request be repeated without harm?
    * 'transfer 1000 Euro'
    => request is **idempotent**
    Hence, use reliable systems more problems

  2. server is clien to another server
    - a lot of waiting
    - does not scale well

2. decentralised architectures
  - verticale distribution (multi-tiered systems)
    different logic on different machines
  - horizontal distribution (replicated client or server operating on different
    data)
=> overlay-network hides physical structure by adding a logical structur

####Structured P2P architectures
- moste poular technique users distributed hash tables(DHT), key-value pairs
- randomly create 128 bit oder 160 bit key for data & nodes
  Two or more identical keys are very unlikely

#####Chord system arranges item in the ring
- data item k is assigned to node with smalles identifier di >= k
  i.e. item
    - 0 belongs to node 1
    - 2 to 4
