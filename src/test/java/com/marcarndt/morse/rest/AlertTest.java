package com.marcarndt.morse.rest;

import static org.junit.Assert.*;

import com.marcarndt.morse.service.AlertService;
import javax.ws.rs.core.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Created by arndt on 2017/06/25.
 */
@RunWith(PowerMockRunner.class)
public class AlertTest {

  @Mock
  AlertService alertService;

  @InjectMocks
  Alert alert;


  @Test
  public void sendAlert() throws Exception {
    Response response = alert.sendAlert("test text message");
    Mockito.verify(alertService).sendAlert("test text message");
    assertEquals(200,response.getStatus());
  }

  @Test
  public void sendSlackMessage() throws Exception {
    Response response = alert.sendSlackMessage("test slack message");
    Mockito.verify(alertService).sendAlert("test slack message");
    assertEquals(200,response.getStatus());
  }

  @Test
  public void getStatus() throws Exception {
    Response response = alert.getStatus();
    assertEquals(200,response.getStatus());
  }

}