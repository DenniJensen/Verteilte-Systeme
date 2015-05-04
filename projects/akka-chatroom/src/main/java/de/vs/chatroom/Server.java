package de.vs.chatroom;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.ConfigFactory;
import de.vs.chatroom.actors.ServerActor;
import de.vs.chatroom.common.ChatUtil;

/**
 * Created by goerickm  on 04.05.15.
 */
public class Server {

  public static void main(String[] args) {

    new Server();
  }

  private ActorSystem system;

  public Server() {
    system = ActorSystem.create(ChatUtil.CHAT_SERVICE_NAME, ConfigFactory.load().getConfig("ServerSys"));
    ActorRef server = system.actorOf(new Props(ServerActor.class), "serverActor");
  }
}
