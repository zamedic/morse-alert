package com.marcarndt.morse.command;

import com.marcarndt.morse.MorseBot;
import com.marcarndt.morse.service.AlertService;
import com.marcarndt.morse.service.UserService;
import com.marcarndt.morse.telegrambots.api.objects.Chat;
import com.marcarndt.morse.telegrambots.api.objects.User;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Created by arndt on 2017/06/25.
 */
@Stateless
public class SetAlertGroup extends BaseCommand {

  /**
   * The Alert service.
   */
  @Inject
  AlertService alertService;

  public String getRole() {
    return UserService.ADMIN;
  }

  protected String performCommand(MorseBot morseBot, User user, Chat chat, String[] strings) {
    alertService.setGroup(chat.getId());
    morseBot.sendMessage("Alert group has been set to this group", chat.getId().toString());
    return null;
  }

  public String getCommandIdentifier() {
    return "setAlertGroup";
  }

  public String getDescription() {
    return "Tells the bot to send alerts to this group";
  }
}
