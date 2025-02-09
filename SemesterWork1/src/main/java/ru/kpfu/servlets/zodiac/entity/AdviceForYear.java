package ru.kpfu.servlets.zodiac.entity;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AdviceForYear {
    private Integer adviceForYearId;
    private Integer zodiacSignId;
    private Integer adviceYear;
    private String adviceText;

    public AdviceForYear(Integer adviceForYearId, Integer zodiacSignId, Integer adviceYear, String adviceText) {
        this.adviceForYearId = adviceForYearId;
        this.zodiacSignId = zodiacSignId;
        this.adviceYear = adviceYear;
        this.adviceText = adviceText;
    }
}
