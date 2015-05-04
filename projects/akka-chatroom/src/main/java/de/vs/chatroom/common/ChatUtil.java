package de.vs.chatroom.common;

import java.util.Random;

/**
 * Created by goerickm on 04.05.15.
 */
public class ChatUtil {

  public static final String CHAT_SERVICE_NAME = "ServerSys";
  public static final String CHAT_SERVICE_HOST = "localhost";
  public static final int CHAT_SERVICE_PORT = 9999;

  public static int getFreePort() {
    return new Random().nextInt(10000) + 10000;
  }
}
