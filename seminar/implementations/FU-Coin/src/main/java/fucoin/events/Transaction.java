package fucoin.events;

import java.io.Serializable;

public class Transaction implements Serializable {
  private final int value;
  private final String source;
  private final String target;

  public Transaction(int value, String source, String target) {
    this.value = value;
    this.source = source;
    this.target = target;
  }

  public int getValue() {
    return value;
  }

  public String getSource() {
    return source;
  }

  public String getTarget() {
    return target;
  }
}
