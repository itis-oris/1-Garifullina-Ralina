package ru.kpfu.servlets.zodiac.entity;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Zodiac {
    private Integer signId;
    private String signName;
    private String strongSide;
    private String weakness;
    private String info;
    private String imagePath;

    public Zodiac(Integer signId, String signName, String strongSide, String weakness, String info, String imagePath) {
        this.signId = signId;
        this.signName = signName;
        this.strongSide = strongSide;
        this.weakness = weakness;
        this.info = info;
        this.imagePath = imagePath;
    }
}

