package com.example.demo.utilities.logging;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Locale;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LogMessage {
    @JsonIgnore
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss.SSSX", Locale.US);
    private String timestamp;
    private String thread;
    private String sourceHost;
    private String remoteHost;
    private String resource;
    private STATUS status;
    private Object[] arguments;
    private Object result;

    public LogMessage(long timestamp,
                      String thread,
                      String sourceHost,
                      String remoteHost,
                      String resource,
                      STATUS status,
                      Object[] arguments,
                      Object result) {
        this.timestamp = sdf.format(timestamp);
        this.thread = thread;
        this.sourceHost = sourceHost;
        this.remoteHost = remoteHost;
        this.resource = resource;
        this.status = status;
        this.arguments = arguments;
        this.result = result;
    }

    void setTimestamp(long timestamp) {
        this.timestamp = sdf.format(timestamp);
    }

    public enum STATUS {
        ENTER,
        EXIT,
        ERROR
    }
}
