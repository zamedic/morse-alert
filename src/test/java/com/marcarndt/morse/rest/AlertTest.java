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
    Response response = alert.sendDeliveryMessage("{\n"
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
    Mockito.verify(alertService).sendAlert("\uD83D\uDE9A<b>Chef Delivery</b>\n"
        + " Project: swarm\n"
        + "Change failed at the Acceptance stage.\n"
        + " <a href=\"https://autobot-wkbxttwhfapcogjk.us-east-1.opsworks-cm.io/e/default/#/organizations/Marc/projects/swarm/changes/e676e8ab-e243-4def-b9bf-12af06ac5305/status\">adding aws monitoring</a> \n"
        + "Change submitted by:zamedic\n",true);

    assertEquals(200,response.getStatus());
  }

  @Test
  public void getStatus() throws Exception {
    Response response = alert.getStatus();
    assertEquals(200,response.getStatus());
  }

}