package com.marcarndt.morse.rest;

import com.marcarndt.morse.MorseBotException;
import com.marcarndt.morse.service.AlertService;
import com.vdurmont.emoji.Emoji;
import com.vdurmont.emoji.EmojiManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.io.StringReader;
import java.util.StringTokenizer;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Created by arndt on 2017/06/24.
 */
@Api(value = "Alert Service")
@Path("alert")
public class Alert {

  /**
   * Logger.
   */
  private static final Logger LOG = Logger.getLogger(Alert.class.getName());
  /**
   * Alert Service.
   */
  @Inject
  private transient AlertService alertService;

  /**
   * Send alert response.
   *
   * @param body the body
   * @return the response
   */
  @POST()
  @Consumes("test/plain")
  @ApiOperation(value = "Send a plain text alert message")
  public Response sendAlert(final String body) {
    try {
      alertService.sendAlert(body);
    } catch (MorseBotException e) {
      return Response.serverError().entity(e.getMessage()).build();
    }
    return Response.ok().build();
  }

  /**
   * Send slack message response.
   *
   * @param body the message
   * @return the response
   */
  @POST
  @Consumes("application/json")
  @ApiOperation(value = "Accepts a Slack Message")
  @Path("delivery")
  public Response sendDeliveryMessage(final String body) {
    try {

      LOG.info(body);

      final JsonReader jsonReader = Json.createReader(new StringReader(body));
      final JsonObject jsonObject = jsonReader.readObject();
      final String text = jsonObject.getString("text");
      final StringTokenizer stringTokenizer = new StringTokenizer(text, "\n");
      String line1 = stringTokenizer.nextToken();
      final String message = stringTokenizer.nextToken();
      line1 = line1.replace("<", "");
      line1 = line1.replace(">", "");
      final StringTokenizer tokenizerPipe = new StringTokenizer(line1, "|");
      final String url = tokenizerPipe.nextToken();
      final String description = tokenizerPipe.nextToken();

      final JsonArray jsonArray = jsonObject.getJsonArray("attachments");
      final JsonObject arrayValue = (JsonObject) jsonArray.get(0);

      final JsonArray fields = arrayValue.getJsonArray("fields");

      final StringBuilder fieldSting = new StringBuilder();

      String project = "";

      for (final JsonValue field : fields) {
        final JsonObject jsonField = (JsonObject) field;
        final String title = jsonField.getString("title");
        final String value = jsonField.getString("value");

        if ("Project:".equals(title)) {
          project = value;
        } else {
          fieldSting.append(title).append(value).append('\n');
        }
      }
      Emoji emoji = EmojiManager.getForAlias("interrobang");

      if ("Verify Passed. Change is ready for review.".equals(message)) {
        emoji = EmojiManager.getForAlias("eyes");
      } else if ("Change Approved!".equals(message)) {
        emoji = EmojiManager.getForAlias("heavy_check_mark");
      } else if ("Acceptance Passed. Change is ready for delivery.".equals(message)) {
        emoji = EmojiManager.getForAlias("package");
      } else if ("Change Delivered!".equals(message)) {
        emoji = EmojiManager.getForAlias("heavy_check_mark");
      } else if ("Delivered stage has completed for this change.".equals(message)) {
        emoji = EmojiManager.getForAlias("thumbsup");
      }

      final StringBuilder stringBuilder = new StringBuilder(100);
      stringBuilder.append(EmojiManager.getForAlias("truck").getUnicode())
          .append("<b>Chef Delivery</b>\n Project: ").append(project).append('\n')
          .append(emoji.getUnicode()).append(message).append("\n <a href=\"").append(url)
          .append("\">")
          .append(description).append("</a> \n").append(fieldSting.toString());

      alertService.sendAlert(stringBuilder.toString(), true);
      return Response.ok().build();
    } catch (MorseBotException e) {
      return Response.serverError().entity(e.getMessage()).build();
    }
  }

  /**
   * Gets status.
   *
   * @return the status
   */
  @GET
  public Response getStatus() {
    return Response.ok().entity("Listening").build();
  }

}
