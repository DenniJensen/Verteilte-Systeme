package de.vs.chatroom.common.event;

/**
 * Login event object.
 *
 * Created by goerickm on 04.05.15.
 */
public class LoginEvent extends ChatEvent {

  public LoginEvent(String userName) {
    super(userName);
  }
}
