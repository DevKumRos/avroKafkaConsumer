package com.kumar.domain.generated;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;

@Data
@Document(collection = "WebRoomEvent")
@NoArgsConstructor
public class WebRoomEvent implements ConsumerMessageModel {

    private ObjectId id;

    private String webRoomCode;
    private String webRoomSessionCode;
    private String webRoomServer;
    private String webRoomServerCode;

    private Long userAccountId;
    private Long eventUserAccountId;
    private Integer webRoomEventId;

    private Long resourceId;
    private String resourceName;
    private String participantEmail;

    private String audioSyncCode;
    private String audioBridgePlatformId;
    private String audioBridgeId;
    private String audioBridgeConfId;
    private String audioBridgePartId;
    private String audioDate;
    private String audioAdditionalAttribute;

    private Integer isModerator;
    private String clientType;
    private Integer maxConcurrentWebParticipant;

    private String pexipConfId;
    private String pexipPartId;

    private Timestamp eventTs;

/*    @Override
// TBD -  do  we really  need this override?
    public String toString() {
        return "WebRoomEvent( " +
                "webRoomCode [" + webRoomCode + "], webRoomSessionCode [" + webRoomSessionCode +
                "], created [" + created + "], lastmodified [" + lastmodified +
                "])";
    }*/

    @Override
    public TopicTypeEnum getTopicType() {
        return TopicTypeEnum.WEBROOMEVENT;
    }
}
