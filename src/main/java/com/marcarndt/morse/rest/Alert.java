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

  @Inject
  AlertService alertService;

  @POST()
  @Consumes("test/plain")
  @ApiOperation(value = "Send a plain text alert message")
  public Response sendAlert(String body) {
    try {
      alertService.sendAlert(body);
    } catch (MorseBotException e) {
      return Response.serverError().entity(e.getMessage()).build();
    }
    return Response.ok().build();
  }

  @POST
  @Consumes("application/json")
  @ApiOperation(value = "Accepts a Slack Message")
  @Path("slack")
  public Response sendSlackMessage(String message) {
    try {
      alertService.sendAlert(message);
      return Response.ok().build();
    } catch (MorseBotException e) {
      return Response.serverError().entity(e.getMessage()).build();
    }
  }

  @GET
  public Response getStatus() {
    return Response.ok().entity("Listening").build();
  }

}
