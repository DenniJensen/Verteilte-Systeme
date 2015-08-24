package de.vs.chatroom.common.event;

/**
 * Event object for all 'normal' messages.
 *
 * Created by goerickm on 04.05.15.
 */
public class MessageEvent extends ChatEvent {

  private final String message;

  public MessageEvent(String userName, String message) {
    super(userName);
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
