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

  @Mock
  AlertService alertService;
  @Mock
  MorseBot morseBot;
  @Mock
  Chat chat;

  @InjectMocks
  SetAlertGroup setAlertGroup;

  @org.junit.Test
  public void getRole() throws Exception {
    Assert.assertEquals(UserService.ADMIN, setAlertGroup.getRole());
  }

  @org.junit.Test
  public void performCommand() throws Exception {
    Mockito.when(chat.getId()).thenReturn(1234l);
    setAlertGroup.performCommand(morseBot, null, chat, null);
    Mockito.verify(alertService).setGroup(1234l);
    Mockito.verify(morseBot).sendMessage("Alert group has been set to this group", "1234");
  }

  @org.junit.Test
  public void getCommandIdentifier() throws Exception {
    Assert.assertEquals("setAlertGroup", setAlertGroup.getCommandIdentifier());
  }

  @org.junit.Test
  public void getDescription() throws Exception {
    Assert
        .assertEquals("Tells the bot to send alerts to this group", setAlertGroup.getDescription());
  }

}