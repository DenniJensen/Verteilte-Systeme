package de.vs.chatroom.common;

/**
 * Config Object for the clients
 *
 * Created by goerickm on 06.05.15.
 */
public class ClientConfig {

  private final String userName;
  private final String configName;
  private final String serverAddress;
  private final int serverPort;
  private final String serverActor;
  private final String serviceName;

  public ClientConfig(String userName, String configName, String serverAddress, int serverPort, String serverActor, String serviceName) {
    this.userName = userName;
    this.configName = configName;
    this.serverAddress = serverAddress;
    this.serverPort = serverPort;
    this.serverActor = serverActor;
    this.serviceName = serviceName;
  }

  public String getUserName() {
    return userName;
  }

  public String getConfigName() {
    return configName;
  }

  public String getServerAddress() {
    return serverAddress;
  }
  public int getServerPort() {
    return serverPort;
  }

  public String getServerActor() {
    return serverActor;
  }

  public String getServiceName() {
    return serviceName;
  }
}
