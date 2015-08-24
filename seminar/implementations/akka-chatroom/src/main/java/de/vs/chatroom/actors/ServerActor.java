package de.vs.chatroom.actors;

import akka.actor.ActorRef;
import akka.actor.PoisonPill;
import akka.actor.UntypedActor;
import de.vs.chatroom.common.event.LoginEvent;
import de.vs.chatroom.common.event.LogoutEvent;
import de.vs.chatroom.common.event.MessageEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * Server actor to handle all messages.
 *
 * Created by goerickm on 04.05.15.
 */
public class ServerActor extends UntypedActor {

  private final Map<String, ActorRef> sessions = new HashMap<>();

  public ServerActor() {
  }

  @Override
  public void onReceive(Object message) throws Exception {

    if (message instanceof PoisonPill) {
      getContext().system().shutdown();
    } else if (message instanceof MessageEvent) {
      broadcast(((MessageEvent) message).getUserName(), ((MessageEvent) message).getMessage());
      serverOut(((MessageEvent) message).getUserName() + " send: " + ((MessageEvent) message).getMessage());
    } else if (message instanceof LoginEvent) {
      String name = ((LoginEvent) message).getUserName();
      if (!sessions.containsKey(name)) {
        sessions.put(name, getSender());
        serverOut(name + " logged in!");
        broadcast(name, "just logged in!");
      } else {
        getSender().tell(new MessageEvent("Server", "Login name \"" + name + "\" already used!"));
      }
    } else if (message instanceof LogoutEvent) {
      String name = ((LogoutEvent) message).getUserName();
      broadcast(name, "just logged out!");
      serverOut(name + " logged out!");
      sessions.remove(name);
    } else {
      unhandled(message);
    }
  }

  /**
   * For logging write message to System out
   * @param message String
   */
  private void serverOut(String message) {
    System.out.println(message);
  }


  /**
   * Send message to all expect sender of message.
   * @param sender String
   * @param message String
   */
  private void broadcast(String sender, String message) {
    sessions.entrySet().stream().filter(entry -> !entry.getKey().equals(sender)).forEach(entry -> entry.getValue().tell(new MessageEvent(sender, message)));
  }
}
