package de.berlin.fu.vs;

import akka.actor.*;

import java.io.Serializable;

public class Wallet extends AbstractWallet {
    
    public static void main(String[] args) {
        // Create the 'helloakka' actor system
        final ActorSystem system = ActorSystem.create("FuCoin");

        // Create the 'greeter' actor
        final ActorRef greeter = system.actorOf(Props.create(Wallet.class, "walletname"), "wallet");

        // Create the "actor-in-a-box"
        final Inbox inbox = Inbox.create(system);
    }
    
    public Wallet(String name) {
        super(name);
    }

    public void onReceive(Object message) {
    }
    
    public String getAddress() {
        return "";
    }
    
    public void leave() {
    }
    
}