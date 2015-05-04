package de.vs.chatroom.actors;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import de.vs.chatroom.common.event.MessageEvent;

/**
 * Created by goerickm on 04.05.15.
 */
public class ClientActor extends UntypedActor {


  LoggingAdapter log = Logging.getLogger(getContext().system(), this);
  private ActorRef remote;

  public ClientActor(ActorRef inActor) {
    remote = inActor;
  }

  @Override
  public void onReceive(Object message) throws Exception {

    if (message instanceof String) {
      log.info("Sending message to server - message# Hi there");
      remote.tell("Hi there", getSelf());
    }else if (message instanceof MessageEvent){
      System.out.println(((MessageEvent) message).getUserName() + " send: " + ((MessageEvent) message).getMessage());
      } else {
        log.info("Message received from Server -> " + message);
      }


  }
}
