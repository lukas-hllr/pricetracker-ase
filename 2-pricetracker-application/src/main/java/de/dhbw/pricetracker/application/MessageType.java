package de.dhbw.pricetracker.application;

public enum MessageType {
    INFO(37),
    WARN(33),
    ERROR(31),
    SUCCESS(32),
    REQUEST(36);
    public final int value;
    private MessageType(int value){
        this.value = value;
    }
}
