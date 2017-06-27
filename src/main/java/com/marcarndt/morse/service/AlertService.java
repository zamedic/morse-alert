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
  private transient MongoService mongoService;

  /**
   * The Morse bot.
   */
  @Inject
  private transient MorseBot morseBot;

  /**
   * Sets group.
   *
   * @param groupId the group id
   */
  public void setGroup(final long groupId) {
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
  public void sendAlert(final String message) throws MorseBotException {
    sendAlert(message,false);
  }

  /**
   * Send alert message.
   *
   * @param message message to send
   * @param html true if you are including basic html markup
   * @throws MorseBotException when the alert group has not been set
   */
  public void sendAlert(final String message, final boolean html) throws MorseBotException {
    final AlertGroup alertGroup = mongoService.getDatastore().createQuery(AlertGroup.class).get();
    if (alertGroup == null) {
      throw new MorseBotException("Alert group has not been set.");
    }
    morseBot.sendMessage(message,alertGroup.getGroupId().toString(),html);
  }
}
