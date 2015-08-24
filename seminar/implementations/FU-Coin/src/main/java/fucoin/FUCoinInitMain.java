package fucoin;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class FUCoinInitMain {
  public static void main(String[] args) {
    Config config = ConfigFactory.load().getConfig("InitWalletSys");
    ActorSystem actorSystem = ActorSystem.create("FUcoin", config);
    Props props = new Props(() -> {
      return new Node("akka://FUcoin@127.0.0.1:2552/user/initNode");
    });
    ActorRef initNode = actorSystem.actorOf(props, "initNode");
  }
}
