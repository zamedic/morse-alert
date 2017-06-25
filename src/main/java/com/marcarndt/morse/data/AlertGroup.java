package com.marcarndt.morse.data;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * Created by arndt on 2017/06/25.
 */
@Entity
public class AlertGroup {

  /**
   * The Group id.
   */
  Long groupId;
  @Id
  private ObjectId objectId;

  /**
   * Gets group id.
   *
   * @return the group id
   */
  public Long getGroupId() {
    return groupId;
  }

  /**
   * Sets group id.
   *
   * @param groupId the group id
   */
  public void setGroupId(Long groupId) {
    this.groupId = groupId;
  }

  /**
   * Gets object id.
   *
   * @return the object id
   */
  public ObjectId getObjectId() {
    return objectId;
  }

  /**
   * Sets object id.
   *
   * @param objectId the object id
   */
  public void setObjectId(ObjectId objectId) {
    this.objectId = objectId;
  }
}
