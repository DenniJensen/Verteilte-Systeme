package de.vs.chatroom.System;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.ConfigFactory;
import de.vs.chatroom.actors.ServerActor;

/**
 * Class to start the actor for the chatroom.
 *
 * Created by goerickm  on 04.05.15.
 */
public class Server {

  public Server(String configName, String actorName, String serviceName) {
    ActorSystem system = ActorSystem.create(serviceName, ConfigFactory.load().getConfig(configName));
    ActorRef server = system.actorOf(new Props(ServerActor.class), actorName);
  }
}
