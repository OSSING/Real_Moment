package com.project.Real_Moment.domain.enumuration;

public enum Gender {
    MAN("남"),
    WOMAN("여");

    private final String gender;

    private Gender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    // 문자열을 받아와 알맞는 상수 반환
    public static Gender getConstant(String text) {
        for (Gender gender : Gender.values()) {
            if (gender.getGender().equalsIgnoreCase(text)) {
                return gender;
            }
        }

        throw new IllegalArgumentException("해당하는 문자열을 찾을 수 없습니다. : " + text);
    }

    // 상수 문자열을 받아와 알맞는 문자열로 변환
    public static String getDescription(String text) {
        for (Gender gender : Gender.values()) {
            if (gender.name().equalsIgnoreCase(text)) {
                return gender.getGender();
            }
        }

        throw new IllegalArgumentException("해당하는 Enum 상수를 찾을 수 없습니다. : " + text);
    }
}
