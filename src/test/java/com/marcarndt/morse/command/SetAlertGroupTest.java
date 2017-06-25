package com.marcarndt.morse.command;

import com.marcarndt.morse.MorseBot;
import com.marcarndt.morse.service.AlertService;
import com.marcarndt.morse.service.UserService;
import com.marcarndt.morse.telegrambots.api.objects.Chat;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Created by arndt on 2017/06/25.
 */
@RunWith(PowerMockRunner.class)
public class SetAlertGroupTest {

  /**
   * The Alert service.
   */
  @Mock
  private transient AlertService alertService;
  /**
   * The Morse bot.
   */
  @Mock
  private transient MorseBot morseBot;
  /**
   * The Chat.
   */
  @Mock
  private transient Chat chat;

  /**
   * The Set alert group.
   */
  @InjectMocks
  private transient SetAlertGroup setAlertGroup;

  /**
   * Gets role.
   *
   */
  @org.junit.Test
  public void getRole() {
    Assert.assertEquals(UserService.ADMIN, setAlertGroup.getRole());
  }

  /**
   * Perform command.
   *
   */
  @org.junit.Test
  public void performCommand()  {
    Mockito.when(chat.getId()).thenReturn(1234l);
    setAlertGroup.performCommand(morseBot, null, chat, null);
    Mockito.verify(alertService).setGroup(1234l);
    Mockito.verify(morseBot).sendMessage("Alert group has been set to this group", "1234");
  }

  /**
   * Gets command identifier.
   *
   */
  @org.junit.Test
  public void getCommandIdentifier() {
    Assert.assertEquals("setAlertGroup", setAlertGroup.getCommandIdentifier());
  }

  /**
   * Gets description.
   *
   */
  @org.junit.Test
  public void getDescription()  {
    Assert
        .assertEquals("Tells the bot to send alerts to this group", setAlertGroup.getDescription());
  }

}