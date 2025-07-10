package com.example.demo.Kapcsolat;

public class KapcsolatDTO {
    private String targy1Id;
    private String targy2Id;

    public KapcsolatDTO() {}

    public KapcsolatDTO(String targy1Id, String targy2Id) {
        this.targy1Id = targy1Id;
        this.targy2Id = targy2Id;
    }

    public String getTargy1Id() {
        return targy1Id;
    }

    public void setTargy1Id(String targy1Id) {
        this.targy1Id = targy1Id;
    }

    public String getTargy2Id() {
        return targy2Id;
    }

    public void setTargy2Id(String targy2Id) {
        this.targy2Id = targy2Id;
    }
}

