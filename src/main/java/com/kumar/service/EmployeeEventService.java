package com.kumar.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.kumar.domain.generated.Employee;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmployeeEventService {

    public void processEmployeeEventData(ConsumerRecord<Integer, Employee> consumerRecord) throws JsonMappingException, JsonProcessingException {
        log.info("Employee Event is {}", consumerRecord.value());

    }

    public void normalProcessEmployeeEventData(ConsumerRecord<Integer, String> consumerRecord) {
        log.info("Employee Event is {}", consumerRecord.value());
    }
}
