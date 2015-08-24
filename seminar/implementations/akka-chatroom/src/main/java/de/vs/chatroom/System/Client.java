package de.vs.chatroom.System;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.ConfigFactory;
import de.vs.chatroom.actors.ClientActor;
import de.vs.chatroom.common.ClientConfig;
import de.vs.chatroom.common.event.LoginEvent;
import de.vs.chatroom.common.event.LogoutEvent;
import de.vs.chatroom.common.event.MessageEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Client implementation.
 * Created by goerickm  on 04.05.15.
 */
public class Client {

  private ActorSystem system;

  private final ClientConfig clientConfig;
  private ActorRef client, server;

  public Client(ClientConfig clientConfig) {
    this.clientConfig = clientConfig;

    login();
    try {
      chat();
    } catch (IOException e) {
      e.printStackTrace();
    }
    logout();
    system.shutdown();
  }

  /**
   * Read from console and send it to server.
   * @throws IOException
   */
  private void chat() throws IOException {
    boolean writing = true;
    System.out.println("You logged in.");
    while (writing) {
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      String message = br.readLine();
      if (message.equals("\\quit")) {
        writing = false;
        System.out.println("You logged out.");
      } else {
        server.tell(new MessageEvent(clientConfig.getUserName(), message), client);
      }
    }
  }

  /**
   * Unregister client from server.
   */
  private void logout() {
    server.tell(new LogoutEvent(clientConfig.getUserName()), client);
  }

  /**
   * Creates Actor references of server and own for the communication
   */
  private void login() {
    system = ActorSystem.create(clientConfig.getServiceName(), ConfigFactory.load().getConfig(clientConfig.getConfigName()));
    server= system.actorFor("akka://" + clientConfig.getServiceName() + "@" + clientConfig.getServerAddress() + ":" + clientConfig.getServerPort() + "/user/" + clientConfig.getServerActor());
    client = system.actorOf(new Props(() -> {
      return new ClientActor(server, clientConfig.getUserName());
    }));
    server.tell(new LoginEvent(clientConfig.getUserName()), client);
  }
}
