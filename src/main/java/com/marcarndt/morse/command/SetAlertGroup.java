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
  private transient AlertService alertService;

  /**
   * Admin Role.
   *
   * @return Admin
   */
  public String getRole() {
    return UserService.ADMIN;
  }

  /**
   * Sets the alert group to the group the group that called the opperation.
   * @param morseBot morse bot
   * @param user calling user
   * @param chat calling chat
   * @param strings parameters - ignored by the operation
   * @return null
   */
  protected String performCommand(final MorseBot morseBot, final User user, final Chat chat, final String[] strings) {
    alertService.setGroup(chat.getId());
    morseBot.sendMessage("Alert group has been set to this group", chat.getId().toString());
    return null;
  }

  /**
   * setAlertGroup.
   *
   * @return setAlertGroup.
   */
  public String getCommandIdentifier() {
    return "setAlertGroup";
  }

  /**
   * Operation description.
   *
   * @return description
   */
  public String getDescription() {
    return "Tells the bot to send alerts to this group";
  }
}
