package com.strnad.rezervacnisystem.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "rezervace")
public class Rezervace {
    @Id
    private String id;
    private String uzivatelId;
    private String kurtId;
    private LocalDateTime cas;

    public Rezervace() {
    }

    public Rezervace(String id, String uzivatelId, String kurtId, LocalDateTime cas) {
        this.id = id;
        this.uzivatelId = uzivatelId;
        this.kurtId = kurtId;
        this.cas = cas;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUzivatelId() {
        return uzivatelId;
    }

    public void setUzivatelId(String uzivatelId) {
        this.uzivatelId = uzivatelId;
    }

    public String getKurtId() {
        return kurtId;
    }

    public void setKurtId(String kurtId) {
        this.kurtId = kurtId;
    }

    public LocalDateTime getCas() {
        return cas;
    }

    public void setCas(LocalDateTime cas) {
        this.cas = cas;
    }
}
