package com.strnad.rezervacnisystem.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

public class CasovySlot {
    private LocalDateTime start;
    private LocalDateTime end;

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }
}