package de.vs.chatroom.common.event;

import java.io.Serializable;

/**
 * Global chatEvent Object.
 * Created by goerickm on 04.05.15.
 */
public class ChatEvent implements Serializable {

  private final String userName;

  public ChatEvent(String userName) {
    this.userName = userName;
  }

  public String getUserName() {
    return userName;
  }
}
