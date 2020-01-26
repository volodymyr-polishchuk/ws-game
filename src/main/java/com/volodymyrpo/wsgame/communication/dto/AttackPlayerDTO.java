package com.volodymyrpo.wsgame.communication.dto;

import lombok.Data;

@Data
public class AttackPlayerDTO {

    private String aggressor;
    private String target;

    public AttackPlayerDTO(String aggressor, String target) {
        this.aggressor = aggressor;
        this.target = target;
    }

    public AttackPlayerDTO() {
    }
}
