package de.vs.chatroom;

import de.vs.chatroom.System.Server;

/**
 * Sample server starter.
 *
 * Created by goerickm  on 06.05.15.
 */
public class Server1 {

  public static void main(String[] args) {
    new Server("ServerSys", "serverActor", "Chatroom");
  }
}
