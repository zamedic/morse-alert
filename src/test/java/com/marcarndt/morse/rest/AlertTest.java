package com.marcarndt.morse.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.marcarndt.morse.MorseBotException;
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

  /**
   * Alert Service.
   */
  @Mock
  private transient AlertService alertService;

  /**
   * Alert.
   */
  @InjectMocks
  private transient Alert alert;


  /**
   * Send alert.
   *
   * @throws Exception the exception
   */
  @Test
  public void sendAlert() {
    final Response response = alert.sendAlert("test text message");
    try {
      Mockito.verify(alertService).sendAlert("test text message");
    } catch (MorseBotException e) {
      fail("Should not throw an error");
    }
    assertEquals("Resoibse code should be success", 200, response.getStatus());
  }

  /**
   * Send slack message.
   *
   * @throws Exception the exception
   */
  @Test
  public void sendSlackMessage()  {
    final Response response = alert.sendDeliveryMessage("{\n"
        + "  \"username\": \"Chef Automate\",\n"
        + "  \"icon_url\": \"https://delivery-assets.chef.io/img/cheficon48x48.png\",\n"
        + "  \"text\": \"<https://autobot-wkbxttwhfapcogjk.us-east-1.opsworks-cm.io/e/default/#/organizations/Marc/projects/swarm/changes/e676e8ab-e243-4def-b9bf-12af06ac5305/status|adding aws monitoring>\\nChange failed at the Acceptance stage.\",\n"
        + "  \"attachments\": [\n"
        + "    {\n"
        + "      \"fallback\": \"<https://autobot-wkbxttwhfapcogjk.us-east-1.opsworks-cm.io/e/default/#/organizations/Marc/projects/swarm/changes/e676e8ab-e243-4def-b9bf-12af06ac5305/status|adding aws monitoring>\\nChange failed at the Acceptance stage.\",\n"
        + "      \"color\": \"#D00000\",\n"
        + "      \"fields\": [\n"
        + "        {\n"
        + "          \"title\": \"Project:\",\n"
        + "          \"value\": \"swarm\"\n"
        + "        },\n"
        + "        {\n"
        + "          \"title\": \"Change submitted by:\",\n"
        + "          \"value\": \"zamedic\",\n"
        + "          \"short\": \"true\"\n"
        + "        }\n"
        + "      ]\n"
        + "    }\n"
        + "  ]\n"
        + "}");
    try {
      Mockito.verify(alertService).sendAlert("\uD83D\uDE9A<b>Chef Delivery</b>\n"
          + " Project: swarm\n"
          + "⁉Change failed at the Acceptance stage.\n"
          + " <a href=\"https://autobot-wkbxttwhfapcogjk.us-east-1.opsworks-cm.io/e/default/#/organizations/Marc/projects/swarm/changes/e676e8ab-e243-4def-b9bf-12af06ac5305/status\">adding aws monitoring</a> \n"
          + "Change submitted by:zamedic\n", true);
    } catch (MorseBotException e) {
      fail("Should not throw an error");
    }

    assertEquals("response code should be success", 200, response.getStatus());
  }

  /**
   * Gets status.
   *
   * @throws Exception the exception
   */
  @Test
  public void getStatus()  {
    final Response response = alert.getStatus();
    assertEquals("resonse code should be successs", 200, response.getStatus());
  }

}