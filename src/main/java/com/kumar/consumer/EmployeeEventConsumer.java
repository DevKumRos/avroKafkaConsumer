package com.kumar.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kumar.domain.generated.Employee;
import com.kumar.service.EmployeeEventService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EmployeeEventConsumer {

    private String TOPIC = "employee-events";
    private String PERSON_TOPIC = "person-events";

    @Autowired
    EmployeeEventService employeeEventService;

    @KafkaListener(topics= {"person-events"}, containerFactory = "kafkaListenerFactory")
    public void onMessageData(ConsumerRecord<Integer, String> consumerRecord) {
        log.info("Consume Message : {}", consumerRecord);
        employeeEventService.normalProcessEmployeeEventData(consumerRecord);
    }
    @KafkaListener(topics= {"employee-events"}, containerFactory = "employeeKafkaListenerFactory")
    public void onMessage(ConsumerRecord<Integer, Employee> consumerRecord) {
        log.info("Consume Message : {}", consumerRecord);
        try {
            employeeEventService.processEmployeeEventData(consumerRecord);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
