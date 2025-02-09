package ru.kpfu.servlets.zodiac.entity;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ZodiacLunarSignAdvice {
    private Integer id;
    private String zodiacSign;
    private String generalInfo;
    private String possibilities;
    private String prohibitions;
    private String mood;
    private String initiatives;
    private String workBusiness;
    private String finances;
    private String loveFamily;
    private String health;
    private String creativityEducation;

    public ZodiacLunarSignAdvice(Integer id, String zodiacSign, String generalInfo, String possibilities, String prohibitions, String mood, String initiatives, String workBusiness, String finances, String loveFamily, String health, String creativityEducation) {
        this.id = id;
        this.zodiacSign = zodiacSign;
        this.generalInfo = generalInfo;
        this.possibilities = possibilities;
        this.prohibitions = prohibitions;
        this.mood = mood;
        this.initiatives = initiatives;
        this.workBusiness = workBusiness;
        this.finances = finances;
        this.loveFamily = loveFamily;
        this.health = health;
        this.creativityEducation = creativityEducation;
    }
}
