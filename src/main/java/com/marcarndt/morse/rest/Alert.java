package com.marcarndt.morse.rest;

import com.marcarndt.morse.MorseBotException;
import com.marcarndt.morse.service.AlertService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.inject.Inject;
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
   * @param message the message
   * @return the response
   */
  @POST
  @Consumes("application/json")
  @ApiOperation(value = "Accepts a Slack Message")
  @Path("slack")
  public Response sendSlackMessage(final String message) {
    try {
      alertService.sendAlert(message);
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
