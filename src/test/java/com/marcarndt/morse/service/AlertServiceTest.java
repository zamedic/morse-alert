package com.marcarndt.morse.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.marcarndt.morse.data.AlertGroup;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Created by arndt on 2017/06/27.
 */
@RunWith(PowerMockRunner.class)
public class AlertServiceTest {

  @Mock
  MongoService mongoService;
  @Mock
  Datastore datastore;
  @Mock
  Query query;

  @InjectMocks
  AlertService alertService;


  /**
   * Sets up.
   */
  @Before
  public void setUp() {
    when(mongoService.getDatastore()).thenReturn(datastore);
    when(datastore.createQuery(AlertGroup.class)).thenReturn(query);

  }

  @Test
  public void setGroup() throws Exception {
    when(query.get()).thenReturn(null);
    ArgumentCaptor<AlertGroup> argument = ArgumentCaptor.forClass(AlertGroup.class);
    alertService.setGroup(1234);
    verify(datastore).save(argument.capture());
    assertEquals(1234, argument.getValue().getGroupId().longValue());
  }

  @Test
  public void sendAlert() throws Exception {
  }

}