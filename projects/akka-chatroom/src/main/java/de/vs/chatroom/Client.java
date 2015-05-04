package de.vs.chatroom;

import akka.actor.*;
import com.typesafe.config.ConfigFactory;
import de.vs.chatroom.actors.ClientActor;
import de.vs.chatroom.common.ChatUtil;
import de.vs.chatroom.common.event.LoginEvent;
import de.vs.chatroom.common.event.LogoutEvent;
import de.vs.chatroom.common.event.MessageEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by goerickm  on 04.05.15.
 */
public class Client {

  private final String clientHost = "localhost";
  private final int clientPort = ChatUtil.getFreePort();


  private ActorSystem system;

  public static void main(String[] args) {
    if (args.length > 1) {
      if (args[0].equals("-u")) {
        new Client(args[1]);
      }
    } else {
      new Client("anonymous");
    }
  }

  private final String userName;
  private ActorRef client, server;

  public Client(String userName) {
    this.userName = userName;

    login();
    try {
      chat();
    } catch (IOException e) {
      e.printStackTrace();
    }
    logout();
    system.shutdown();
  }

  private void chat() throws IOException {
    boolean writing = true;
    while (writing) {
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      System.out.print(userName + ": ");
      String message = br.readLine();
      if (message.equals("/quit")) {
        writing = false;
      } else {
        server.tell(new MessageEvent(userName, message), client);
      }
    }
  }

  private void logout() {
    server.tell(new LogoutEvent(userName), client);
  }

  private void login() {
    system = ActorSystem.create(ChatUtil.CHAT_SERVICE_NAME, ConfigFactory.load().getConfig("ClientSys"));
    server= system.actorFor("akka://ServerSys@127.0.0.1:2552/user/serverActor");;
    client = system.actorOf(new Props(() -> {
      return new ClientActor(server);
    }));
    server.tell(new LoginEvent(userName), client);
  }
}
