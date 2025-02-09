package ru.kpfu.servlets.zodiac.entity;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MoonPhaseAdvice {
    private Integer id;
    private String moonPhase;
    private String generalInfo;
    private String affairs;
    private String work;
    private String homeWork;
    private String money;
    private String loveRelationships;
    private String communication;
    private String travel;
    private String hair_care;
    private String beauty_self_care;
    private String health;
    private String nutrition;

    public MoonPhaseAdvice(Integer id, String moonPhase, String generalInfo, String affairs, String work, String homeWork, String money, String loveRelationships, String communication, String travel, String hair_care, String beauty_self_care, String health, String nutrition) {
        this.id = id;
        this.moonPhase = moonPhase;
        this.generalInfo = generalInfo;
        this.affairs = affairs;
        this.work = work;
        this.homeWork = homeWork;
        this.money = money;
        this.loveRelationships = loveRelationships;
        this.communication = communication;
        this.travel = travel;
        this.hair_care = hair_care;
        this.beauty_self_care = beauty_self_care;
        this.health = health;
        this.nutrition = nutrition;
    }
}
