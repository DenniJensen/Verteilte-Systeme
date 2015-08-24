package de.berlin.fu.vs;

import java.util.HashMap;
import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import java.io.Serializable;

public abstract class AbstractWallet extends UntypedActor implements Serializable {
    
    // Used to join the network (a pre known participant/JoinEvent must be known)
    public static class ActionJoin implements Serializable {}
    
    // Returns some neighbors that might be used as known
    // and/or local neighbors
    public class ActionJoinAnswer implements Serializable {
        public final HashMap<String, ActorRef> someNeighbors = new HashMap<>();
    }
    
    // Used to push the state of my/a wallet to another participant
    public static class ActionStoreOrUpdate implements Serializable {
        public final AbstractWallet w;
        public ActionStoreOrUpdate(AbstractWallet w) {
            this.w = w;
        }
    }
    
    // May be used to delete a stored JoinEvent on another participant
    public static class ActionInvalidate implements Serializable {
        public final String name;
        public ActionInvalidate(String name) {
            this.name = name;
        }
    }
    
    // Used to send (positive amount) or retreive money (negative amount) 
    public static class ActionReceiveTransaction implements Serializable {
        public final int amount;
        public ActionReceiveTransaction(int amount) {
            this.amount = amount;
        }
    }
    
    // Used to search a JoinEvent by name, i.e. when we want to
    // perform a transaction on it
    public static class ActionSearchWalletReference implements Serializable {
        public final String name;
        public ActionSearchWalletReference(String name) {
            this.name = name;
        }
    }
    
    // Used to return a JoinEvent reference (akka-style string which can
    // be transformed to an ActorRef)
    public static class ActionSearchWalletReferenceAnswer implements Serializable {
        public final String address;
        public ActionSearchWalletReferenceAnswer(String address) {
            this.address = address;
        }
    }
    
    // Used to search a JoinEvent by name, i.e. the own wallet if we just
    // joined the network; If a receiving participant holds the stored JoinEvent,
    // he returns it, otherwise, he might use gossiping methods to go on 
    // with the search;
    // Note: You should also forward the sender (the participant who actually
    // searches for this JoinEvent, so that it can be returnd the direct way)
    public static class ActionSearchMyWallet implements Serializable {
        public final String name;
        public ActionSearchMyWallet(String name) {
            this.name = name;
        }
    }
    
    // Used to return a searched JoinEvent
    public static class ActionSearchMyWalletAnswer implements Serializable {
        public final AbstractWallet w;
        public ActionSearchMyWalletAnswer(AbstractWallet w) {
            this.w = w;
        }
    }
    
    // Constructor
    public AbstractWallet(String name) {
        this.name = name;
    }
    
    // Returns the name of this wallet, e.g. "Lieschen Müller"
    public String getName() {
        return this.name;
    }
    
    // Returns the akka-style address as String, which 
    // could be converted to an ActorRef object later
    public abstract String getAddress();
    
    // Performs housekeeping operations, e.g. pushes 
    // backedUpNeighbor-entries to other neighbors
    public abstract void leave();
    
    // The which receives Action objects
    public abstract void onReceive(Object message);
    
    // Holds references to neighbors that were in 
    // contact with this wallet during runtime;
    // The key corresponds to the JoinEvent's name
    public transient HashMap<String, ActorRef> knownNeighbors;
    
    // Holds references to neighbors this wallet 
    // synchronizes itself to (the JoinEvent object);
    // The key corresponds to the JoinEvent's name
    public transient HashMap<String, ActorRef> localNeighbors;
    
    // Holds all Wallets from network participants 
    // which synchronize their state (JoinEvent object)
    // with us;
    // The key corresponds to the JoinEvent's name
    public transient HashMap<String, AbstractWallet> backedUpNeighbors;
    
    // The name of this wallet (does never change, no 
    // duplicates in network assumed)
    public final String name;
    
    // The amount this wallet currently holds
    public int amount;
    
}