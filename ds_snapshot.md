##Chandy Lamport Snapshot

####Messages

All messages inherit from follow class

```java
class Event {
  boolen marker
  ref leader

  public Event(marker, leader)
}
```

#####Marker message

```java
class MarkerEvent inherit Event {

  public MarkerEvent(leader) {
    super(true, leader)
  }
}
```

#####Update state message

```java
class UpdateStateMessage inherit Event {
  Event event

  public UpdateStateMessage(leader, event) {
    super(true, leader)
  }
}
```

###Leader

####State save

We recieve the state of a node and all messages that are send before the state
is saved

```java
class State
Object node; // state of node
List recMsg // recieved messages not in state
```

####Start

```java
s // List of states
add leader to s
send marker message to all known nodes
```

####Receive state

```java
receive state message
add or update state to s
```

####Receive update message

```java
search for sender in s
if (found(sender)) {
  update s list of received message for sender
} else {
  add sender to s
}
```

###Node

```java
boolean marker = false
ref leader = null
```

####Receive messages m

```java
if (marker) {
  if (!m.marker) {
    send update message to leader
  }
} else {
  if (m.marker) {
    marker = true
    leader = m.leader
    send state message to leader
    send marker message to all known nodes
  }
}
process m
```
