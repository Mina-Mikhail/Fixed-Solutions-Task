package com.mina_mikhail.fixed_solutions_task.utils.display_message;

import androidx.annotation.StringRes;

public class Message {

  private int type;
  @StringRes
  private int messageResourceId;
  private String messageText;

  public Message(int type, int messageResourceId) {
    this.type = type;
    this.messageResourceId = messageResourceId;
    this.messageText = null;
  }

  public Message(int type, String messageText) {
    this.type = type;
    this.messageText = messageText;
    this.messageResourceId = -1;
  }

  public int getType() {
    return type;
  }

  public int getMessageResourceId() {
    return messageResourceId;
  }

  public String getMessageText() {
    return messageText;
  }
}
