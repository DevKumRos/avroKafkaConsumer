package com.kumar.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kumar.domain.generated.TopicTypeEnum;
import com.kumar.domain.generated.WebRoomEvent;
import com.kumar.domain.generated.WebRoomEventAvro;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.Schema;
import org.apache.avro.reflect.ReflectData;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.bson.types.ObjectId;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class WevRoomEventConsumer {
    private String TOPIC = "Web-Room-Events";

    @KafkaListener(topics= {"Web-Room-Events2"}, containerFactory = "webRoomKafkaListenerFactory")
    public void onMessage(ConsumerRecord<String, WebRoomEventAvro> consumerRecord) throws JsonProcessingException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        log.info("Consume Message : {}", consumerRecord);
        log.info("WebRoomEventAvro Event is {}", consumerRecord.value());
        WebRoomEventAvro webRoomEventAvro = consumerRecord.value();
        WebRoomEvent webRoomEvent = (WebRoomEvent) getResponseInstanceFromToAvro(webRoomEventAvro, WebRoomEvent.class);
       // WebRoomEvent webRoomEvent = (WebRoomEvent)convertAvroClassToNormalClass(webRoomEventAvro, WebRoomEvent.class);
        log.info("WebRoomEvent is {}, Type: {}", webRoomEvent, webRoomEvent.getTopicType());
    }

    private static Object getResponseInstanceFromToAvro(Object webRoomEventAvro, Class<?> targetClassName) {
        final Schema schema = ReflectData.get().getSchema(targetClassName.getClass());
        Object targetObject = BeanUtils.instantiateClass(targetClassName);
        WebRoomEventAvro roomEventAvro = (WebRoomEventAvro) webRoomEventAvro;
        List<String> removedCastErrorProp = Arrays.asList("id", "eventTs");
        roomEventAvro.getSchema().getFields().forEach(d -> {
            if(!removedCastErrorProp.contains(d.name().trim())) {
                PropertyAccessorFactory.forDirectFieldAccess(targetObject).setPropertyValue(d.name(),
                        roomEventAvro.get(d.name()) == null ? roomEventAvro.get(d.name()) :
                                roomEventAvro.get(d.name()).toString());
            } else if("eventTs".equalsIgnoreCase(d.name().trim())) {
                 long eventTsData = new Long(roomEventAvro.get(d.name()).toString());
                 Timestamp eventTs = new Timestamp(eventTsData);
                 PropertyAccessorFactory.forDirectFieldAccess(targetObject).setPropertyValue(d.name(),eventTs);

            }else if("id".equalsIgnoreCase(d.name().trim()) && roomEventAvro.get(d.name()) != null) {
                ObjectId objId = new ObjectId(roomEventAvro.get(d.name()).toString());
                PropertyAccessorFactory.forDirectFieldAccess(targetObject).setPropertyValue(d.name(),objId);
            }
        });
        return  targetObject;
    }


}
