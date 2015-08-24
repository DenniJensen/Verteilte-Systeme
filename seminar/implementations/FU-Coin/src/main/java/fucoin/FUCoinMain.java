package fucoin;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class FUCoinMain {
  public static void main(String[] args) {
    String knownAddress = "akka://FUcoin@127.0.0.1:2552/user/initNode";
    Config config = ConfigFactory.load().getConfig("WalletSys");
    ActorSystem actorSystem = ActorSystem.create("FUcoin", config);
    Props props = new Props(Node.class);
    ActorRef node = actorSystem.actorOf(props);
    ActorRef test = actorSystem.actorFor(knownAddress);
    test.tell("join", node);
  }
}
