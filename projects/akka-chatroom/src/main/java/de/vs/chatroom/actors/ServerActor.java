package de.vs.chatroom.actors;

import akka.actor.ActorRef;
import akka.actor.PoisonPill;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import de.vs.chatroom.common.event.LoginEvent;
import de.vs.chatroom.common.event.LogoutEvent;
import de.vs.chatroom.common.event.MessageEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by goerickm on 04.05.15.
 */
public class ServerActor extends UntypedActor {

  LoggingAdapter log = Logging.getLogger(getContext().system(), this);

  private final Map<String, ActorRef> sessions = new HashMap<String,ActorRef>();

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
      if (!sessions.containsKey(((LoginEvent) message).getUserName())) {
        sessions.put(((LoginEvent) message).getUserName(), getSender());
        serverOut(((LoginEvent) message).getUserName() + " logged in!");
        broadcast(((LoginEvent) message).getUserName(), "just logged in!");
      } else {
        getSender().tell(new MessageEvent("Server", "Login name is used!"));
      }
    } else if (message instanceof LogoutEvent) {
      broadcast(((LogoutEvent) message).getUserName(), "just logged out!");
      serverOut(((LogoutEvent) message).getUserName() + " logged out!");
      sessions.remove(((LogoutEvent) message).getUserName());
    } else {
      unhandled(message);
    }
  }

  private void serverOut(String message) {
    System.out.println(message);
  }


  private void broadcast(String sender, String message) {
    for (Map.Entry<String, ActorRef> entry : sessions.entrySet()) {
      if (!entry.getKey().equals(sender)) {
        entry.getValue().tell(new MessageEvent(sender, message));
      }
    }
  }
}
