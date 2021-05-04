package com.kumar.domain.generated;

import org.bson.types.ObjectId;

import java.io.Serializable;
import java.sql.Timestamp;

public interface ConsumerMessageModel extends Serializable {

    ObjectId getId();
    String getWebRoomCode();
    String getWebRoomSessionCode();
    String getWebRoomServerCode();
    Integer getWebRoomEventId();
    Timestamp getEventTs();

    TopicTypeEnum getTopicType();
}
