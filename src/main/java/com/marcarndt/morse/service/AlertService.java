package com.marcarndt.morse.service;

import com.marcarndt.morse.MorseBot;
import com.marcarndt.morse.MorseBotException;
import com.marcarndt.morse.data.AlertGroup;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Created by arndt on 2017/06/25.
 */
@Stateless
public class AlertService {

  /**
   * The Mongo service.
   */
  @Inject
  MongoService mongoService;

  /**
   * The Morse bot.
   */
  @Inject
  MorseBot morseBot;

  /**
   * Sets group.
   *
   * @param groupId the group id
   */
  public void setGroup(long groupId) {
    AlertGroup alertGroup = mongoService.getDatastore().createQuery(AlertGroup.class).get();
    if (alertGroup == null) {
      alertGroup = new AlertGroup();
    }
    alertGroup.setGroupId(groupId);
    mongoService.getDatastore().save(alertGroup);

  }

  /**
   * Send alert.
   *
   * @param message the message
   * @throws MorseBotException the morse bot exception
   */
  public void sendAlert(String message) throws MorseBotException {
    sendAlert(message,false);
  }

  public void sendAlert(String message, boolean html) throws MorseBotException {
    AlertGroup alertGroup = mongoService.getDatastore().createQuery(AlertGroup.class).get();
    if(alertGroup == null){
      throw new MorseBotException("Alert group has not been set.");
    }
    morseBot.sendMessage(message,alertGroup.getGroupId().toString(),html);
  }
}
