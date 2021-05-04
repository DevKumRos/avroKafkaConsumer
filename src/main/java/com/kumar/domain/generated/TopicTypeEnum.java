package com.kumar.domain.generated;

public enum TopicTypeEnum {

    WEBROOMEVENT("WEBROOMEVENT"),
    WEBROOMSESSION("WEBROOMSESSION"),
    WEBROOMHEARTBEAT("WEBROOMHEARTBEAT"),
    UNKNOWN("UNKNOWN"),
    ;

    private final String name;

    TopicTypeEnum(String name) {
        this.name = name;
    }

    public static TopicTypeEnum getTopicTypeEnum(String topic) {
        TopicTypeEnum topicTypeEnum;
        if (topic.toUpperCase().contains(TopicTypeEnum.WEBROOMEVENT.getName()))
            topicTypeEnum = TopicTypeEnum.WEBROOMEVENT;
        else if (topic.toUpperCase().contains(TopicTypeEnum.WEBROOMHEARTBEAT.getName()))
            topicTypeEnum = TopicTypeEnum.WEBROOMHEARTBEAT;
        else
            topicTypeEnum = TopicTypeEnum.UNKNOWN;
        return topicTypeEnum;
    }

    public String getName() {
        return this.name;
    }
}
