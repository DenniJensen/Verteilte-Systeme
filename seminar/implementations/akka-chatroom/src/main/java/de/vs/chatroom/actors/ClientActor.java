package de.vs.chatroom.actors;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import de.vs.chatroom.common.event.MessageEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Implementation of the client actor.
 *
 * Created by goerickm on 04.05.15.
 */
public class ClientActor extends UntypedActor {

  private final ActorRef remote;
  private final String userName;

  public ClientActor(ActorRef inActor, String userName) {
    this.remote = inActor;
    this.userName = userName;
  }

  @Override
  public void onReceive(Object message) throws Exception {

    if (message instanceof MessageEvent){
      remote.tell(new MessageEvent(userName, countOccurRegex(((MessageEvent) message).getMessage(), "DSS") + " occur of DSS are found."), null);
      System.out.println(((MessageEvent) message).getUserName() + ": " + ((MessageEvent) message).getMessage());
    } else {
        unhandled(message);
    }
  }

  /**
   * For Task 3 count DSS occur.
   * @param msg String
   * @param regex String
   * @return int of all regex found in msg
   */
  private int countOccurRegex(String msg, String regex) {
    int count = 0;
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(msg);
    while (matcher.find()) {
      count++;
    }
    return count;
  }
}
