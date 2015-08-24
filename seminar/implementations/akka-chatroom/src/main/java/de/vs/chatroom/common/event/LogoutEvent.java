package de.vs.chatroom.common.event;

/**
 * Logout event object.
 *
 * Created by goerickm on 04.05.15.
 */
public class LogoutEvent extends ChatEvent {

  public LogoutEvent(String userName) {
    super(userName);
  }
}
