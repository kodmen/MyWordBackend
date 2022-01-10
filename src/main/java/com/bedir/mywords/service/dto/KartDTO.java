package com.bedir.mywords.service.dto;

public class KartDTO {
    public Long id;
    public String onYuz;
    public String arkaYuz;
    public Long desteId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOnYuz() {
        return onYuz;
    }

    public void setOnYuz(String onYuz) {
        this.onYuz = onYuz;
    }

    public String getArkaYuz() {
        return arkaYuz;
    }

    public void setArkaYuz(String arkaYuz) {
        this.arkaYuz = arkaYuz;
    }

    public Long getDesteId() {
        return desteId;
    }

    public void setDesteId(Long desteId) {
        this.desteId = desteId;
    }
}
