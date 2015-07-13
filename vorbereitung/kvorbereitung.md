##Vorbreitung

###Einleitung

####Herausforderungen
* Heterogenität
  - Netzwerk
  - Hardware
  - OS
  - Programmier Sprache
* Offenheit
  - interfaces
  - einheitliche Kommunikation
  - heterogene Hardware
* Sicherheit
  - DoS angriffe
  - mobile code
* Skalierbarkeit
  - Hardware Kosten
  - Performance Kosten
  - Bottlenecks
* Fehlerbehandlung
  - finden
  - maskieren (retrnsmit, redundanz)
  - toleranzen
  - Wiederherstellen
  - Verfügbarkeit erreichen
* Nebenläufigkeit in Bezug auf Konstistenz
* QoS

**Def Distributet System**

Distributed System A distributed system is a collection of independent
components (computers) that appears to its users as a single, coherent system.

####Transparenz
verbergen von:

* Zugriff, verstecken von Representationen und wie zugegriffen wird
* Location, wo sind die Resourcen
* Migration, verschieben von Resourcen
* Relocation, verschieben der Resourcen bei Benutzung
* Replication, doppelte Speicherung
* Nebenläufigkeit, mehrere Nutzer nutzen die selben Resource
* Fehler

####Sklierbarkeit
* in der größe
* geologisch
* administration

**Probleme**

* zentralisierter Dienst
* zentralisierte Daten
* zentralisierter Algorithmus

**Dezentralisiert Algorithmen**

* keine globalen Abhängigkeiten
* Fehler einer Maschinen ruinieren nicht den Algo
* benötigt keiner globale Uhr

#####Techniken
* nur asynchrone Kommunikation verwenden, Code vom Server zum Client schicken
  (Javascript)
* verteilen der Komponenten (DNS für Subdomain)
* replizieren von Komponenten

####Eigenschaften von DS
* Performance
* Verteilte Abhängigkeiten (shared Informatio, shared Data)
* reliability (Redundanz)
* incremental growth (Skalierbarkeit)
* Teilen von Resourcen
* Kommunikation

####Klassifikation von DS
**Erste**

* Layer (vertikal arrangement)
* Tiers (horizontal Splitting)
* Brokerage of Web Services (service register to a Broker)

**Zweite**

* Layered Architektur
* Objekt Basierte Architektur (RPC)
* Daten centralisierte Architektur (Kommunikation over Data, shared Memory)
* Event Basierte Architektur (Kommunikation durch Event)

**Dritte**

* Interaction model (synchron, asynchron, messages ...)
* Failure model (node failure, link failure, message loss)
* Security model

####Chord for dezentralisiertes Architektur
* Aufbau Ring
* Jeder Knoten hällt die Elemente, die vor ihm auf dem Ring liegen zwischen
  seinem vorgänger Knoten und ihm.

#####Fingertables
In der Tabellen stehen die Knoten der Elemente von 2^i - 1 (i = 1, 2, 3,  ...)

###Modelle
####Interleaving model
**Definition**
A sequence of events seq = (e_i : 0 <= i <= m) is a computation of the system in
the interleaving model if there exists a sequence of global states (G_i : 0 <= i <= m + 1) such
that G 0 is an initial state and
G_i+1 = next (G_i , e_i ), for 0 <= i <= m.

gobal total order

####Happened Before
**Immediately precedes**

<_im : e <_im f <=> e immediately precedes f in the sequence of events at P_i .
Also: next(e) = f , and prev(f) = e whenever e <_im f

**Locally precedes**

irreflexive (and reflexive) and transitiv von <_im

**remotely precedes**

~>: e ~> f <=>

e is the send event of a message at process P_i and f is the receive event of
the same message at process P_j . Similarly, states s and t are related by ~> iff a message is sent
after state s that is received be the receiver, resulting in state t

**Def: happend before -> **

1. (e <_im f) oder (e ~> f) => (e -> f), and
2. es exist g : (e -> g) und (g -> f) => (e -> f)

**Def: Concurrency**

not (e -> f) und not (f -> e) => e || f

####Potential Causality
**Def**

Potential Causality. The potential causality relation is the smallest causality
relation on the event set that satisfies the following properties:

* If an event e potentially cuses another event f on the same process, then e
  -P-> f
* If e is the sending of a message and f is the corresponding receive event
  then e -P-> f
* if e -P-> g and g -P-> f then e -P-> f


Model | Basis | Type
---|---|---
Interleaving | Physical Time | total oder on all events
Happened Before | Logical order | total oder on each process
Potential causality | Causality | partial order on each process

###Uhrenalgorithmen
global order tracked by logische Uhr
happend before tracked by Vektor Uhr

####Logische Uhren
for All s,t element S : s -> t => C(s) < C(t)

```
var c:integer initial 0
send event (s, send, t)
  t.c := s.c + 1
receive event (s, receive(u), t)
  t.c := max(s.c, u.c) + 1
internal event (s, internal, t)
  t.c := s.c + 1
```

####Vektor Uhren
for All s,t: s -> t => s.v < t.v

**VC1:**

```
P_j::
var v: array[1...N] of integer
initally (for All i, i ungleich j : v[i] = 0) und (v[j] = 1)
send event (s, send, t)
  t.v := s.v
  t.v[j] := s.v[j] + 1
receive event (s, receive(u), t)
  for i := 1 to N do
    t.v[i] := max(s.v[i], u.v[i])
  t.v[j] := t.v[j] + 1
internal event (s, internal, t)
  t.v := s.v
  t.v[j] := t.v[j] + 1
```

satisfy for All s,t: s.v < t.v <=> s -> t

**VC2:**

```
send same
receive event (s, receive(u), t)
  for i:= 1 to N do
    t.v[i] := max(s.v[i], u.v[i])
internal event (s, internal, t)
  t.v = s.v
```

satisfy for all s.p unglich t.p s -> t <=> s.v < t.v

**VC3:**

```
send event (s, send t)
  t.v[t.p] := s.v[t.p] + 1
receive event (s, receive(u), t)
  t.v := s.v
  t.v[u.p] := max(s.v[u.p], u.v[u.p])
internal event (s, internal, t)
  t.v[t.p] := s.v[t.p] + 1
```

#####Vergleich VC

x < y (for All k : 1 <= k <= N: x[k] <= y[k]) und es existiert ein j: 1 <= j <=
N : x[j] < y[j]

x <= y (x < y) oder (x=y)

####Uhren synchronisation


###Prozess / Threads

####Prozess

* execution of a Programm
* virtual processor for each programm, stored in "process table"
* virtual process on independent address space
* process switch expensive (CPU context, pointer, MMU, TLB)

####Threads
* several threads sharing CPU
* context has little memory information
* avoid blocking application
* switch fast
* on user level allo parallel computation
* I/O blocks threads

###Server

####finite state-machine mode

* only on thread
* examines request
* during wait tims, stored requests in table
* serves next request
* receives messages and acts/changes state

model | characteristics
--|--
single Thread | no paralellism, blocking system calls
multiple threads | paralellism, blocking system calls
finite state-machine | paralellism, no blocking system call

###Code migration

Machine A <- (data / code / processes) -> Machine B

1. code segment, instructions
2. resource segment, reference to external resources
3. execution segment, execution state of process

####Types

* weak mobility, transfer code 1 maube 3
* strong mobility, trnsfer 1 and 2, stop execution, transfer, resume
* cloned process, execution in paralell to orginal process

migration resource segement 2 is difficult

####process to resource binding

1. binding by idenifier: URL, FTP server
2. binding by value: libraries
3. binding by type: local device, monitor

####resorce to machine binding

1. unattached, easily movable (files)
2. fastened resources, difficult to move (database)
3. fixed resources, not movable (local device)

process to resource | resource to machine
--- | ---
| unattached | fastened | fixed
by identifier | MV | GR (or MV) | GR
by value | CP | GR (or CP) | GR
by type | RB | RB (GR,CP) | RB (GR)

MV = move, GR = global reference, CP = copy, RB = rebind to lovally available
resource

###RPC

##Leader Election
###Properties
* Safety: not two nodes declare themselves leader
  - for all i,j: i ungleich j : not (leader(i) und leader(j))
* liveness: every node eventually terminates
  - for all i : done(i) = true, it exist! i: leader(i) =true
* fairness: (for Resource allocation) requests are granted in the order they
  are issued

####LCR (LeLann Chang, Roberts)
* unidirectional communikation
* no message lost
* ring size unknown
* only leader produces output
* alg compares UIDs

```
State of each node (u, send, status)

u = UID
send = UID or NULL
status = {unkown, leader}

message generation:
send = current value of send to node i+1

state transition:
send := NULL

if incoming message is v then
  case
    v > u : send := v
    v = u : status := leader (leader = true)
    v < u : do nothing
  end case
```

#####Correctness
Let i_max index of process ith mac(UID)
Let u_max its UID

1. process i_max outputs "leader" after N rounds
2. no other process does have the same we clarify
3. After N rounds, status i_max = leader in addation
4. for 0 <= r <= N-1, after r rounds
   - send_i_max+r = u_max
   - find max UID at distance r from i_max, as it has to go once around. Show 4
     by induction on r and 3

#####Complexity
* time complexity is N rounds
* communication complexity O(N^2) messages in worst case
* not too expensive in time
* many messages

####HS (Hirschberg and Sinclair)

```
each process has states with componets
  u , type UID initially i’s UID
  send +, containing NULL or (UID, flag {in, out}, hopcount) initially (i’s UID, out, 1)
  send−, as send+
  status element {unknown, leader}, initially unknown
  phase element N initially 0

message generation:
  send current value of send+ to process i+1
  send current value of send− to process i−1

state transitions:
  send+ := NULL
  send− := NULL

if message from i−1 is (v, out, h) then
 v > u and h > 1 : send+ := (v, out, h-1)
 v > u and h = 1 : send− := (v, in, 1)
 v = u : status := leader
if message from i+1 is (v, out, h) then
  v > u and h > 1 : send− := (v, out, h-1)
  v > u and h = 1 : send+ := (v, in, 1)
  v = u : status := leader
```

HS weiter

```
if message from i=1 is (v, in, 1 ) and v ungleich= u then
  send+ =(v, in, 1)
if message from i+1 is (v, in, 1) and v ungleich u then
 send− = (v, in, 1)
if both messages from i−1 and i+1 are (u, in, 1) then
phase++,
  send+ = (u, out, 2^phase )
  send− = (u, out, 2^phase )
```

#####Complexity
* total number of phases 1 + log N aufgerundet
* total number of messages at most 8N (1 + log N aufgerundet) etwa O(N log N)
  with constant factor of approx 8
* time  complexity most 3N if N is power of 2, otherwise 5N

##Mutal Exclusion
* access of shared resources
* required atomic operations
* asumes no link or mode(process) failures

**types of algs**
* time-stamp based
* token based
* quorum-based

###Alg Satisfy
**Safety**

Two process must not have permission to access the critical section in
concurrent states (nothing bad will happen)

**Liveness**
Every request is eventually granted (something good will happen)

**Fairness**
Request must be granted in the order they are issued

**Remark**
The best and expensive algorithm is centralized

###Time Stamp based
####Lamport's Alg
* each process has a logical clock and a queue
* complexity 3(N-1) messages

```
P_i:
var v: vector clock
    q: array [1..N] of int initially unendlich

request:
  q[i]:= V[i]
  send(q[i]) to all processes
release:
  q[i]:= unendlich
  send(q[i]) to all processes
receive(n)
  q[n.p]:= u.q[u.p]
  if event(u) = request then
    send ack to u.p
  end
```

####Ricard & Agrawala
* complexity 2(N-1) messages
* combines ack and release

```
P_i:
  var pendingQ : list of pro ids initially NaN
  myts : intinitially unendlich
  numOK: intinitially 0
request
  myts := logical clock
  send request with my myts to all other process
  num OK := 0 :
receive (u , request )
  if (u.myts < myts) then
    send OK to u.p
  else
    append (pendingQ, u.p);
receive (u, OK)
  numOK++
  if (numOK = N−1) then
    enterCS ;
release :
  myts = unendlich
  for j element pendingQ do
    send OK t o j
  pendingQ := NULL;
```

###Token based
####Centralized Alg

* Coordinator, adminitrator of token
* client
* process holdin token can access cs
* safefty and livenes guaranteed by coordinator

####decentralized

```
P i (client)
  var v : array [1, .., N] of int initially for All j v[j] = 0; (clocks, no of requests)
  inCS : boolean initially false;
  havetoken : boolean initially false except for P0

request
  v[i]++;
  send(request, v) to all processes(including myself)

receive(request, u)
  v:=max(v, u.v)
  if havetoken then
    append (token.reqlist, u);
    if not inCS then checkreq();
    else append(myreqlist, u);

receive (u, token);
  inCS := true;
  havetoken := true;
  append (token.reqlist, {u|u element myreqlist) and (u > token.regdone)}
  myreqlist := NULL;

receive(u) //other message
v:= max(v, u.v);

release
  inCS := false;
  checkreg();
```

* N+2 messages for one access to CS
*  O(N) messages


###Quorum Based
**metrics**

* Quorum size, smaller quorum needs less messages
* Availability
* load on the buisiest node

###Consistency and Consensus ,distributed commit
* operation is performed by a group or none of the group
* reliable multicast: operatoins = delivery of a message
* distributed transactions operations = executions of a transaction
* user coordinator

* one-phase commit: node sends update to all other node who write the update.
  If a message gets lost, or node fails and revovers the data in the system is
  no longer consistens
* two-phase commit: Jim gray (1978)
* distributed transaction involves several processors each on a different
  machine 2 phases wit each 2 steps(one message forth and back):
  1. coordinator --> vote request all prticipants
  2. prticipnt --> vote commit coordintor
  3. if all commit coordintor --> global commit all participants else coordinator
     --> global abort all participants
  4. code
```
if commit
  participant loclly commits
else
  participant loclly aborts
```

###Snapshot(Chandy-Lamport)
* create global distributed state
```pseudo
send marker msg to all other nodes P_j
record local state
record all messaes on channel P_i − P_j
until marker msg is received from P_j
```
