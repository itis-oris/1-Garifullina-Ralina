package ru.kpfu.servlets.zodiac.entity;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ZodiacCompatibility {
    private Integer zodiacCompatibilityId;
    private Integer signIdMan;
    private Integer signIdWoman;
    private String percentInfo;
    private String loveInfo;
    private String familyInfo;
    private String friendsInfo;
    private String workInfo;

    public ZodiacCompatibility(Integer zodiacCompatibilityId, Integer signIdMan, Integer signIdWoman, String percentInfo, String loveInfo, String familyInfo, String friendsInfo, String workInfo) {
        this.zodiacCompatibilityId = zodiacCompatibilityId;
        this.signIdMan = signIdMan;
        this.signIdWoman = signIdWoman;
        this.percentInfo = percentInfo;
        this.loveInfo = loveInfo;
        this.familyInfo = familyInfo;
        this.friendsInfo = friendsInfo;
        this.workInfo = workInfo;
    }
}
