package com.i2i.app.common;

public enum Subject {
    COMPUTER_SCIENCE, BIOLOGY, COMMERCE, TAMIL, ENGLISH, MATHS, SCIENCE, SOCIAL_SCIENCE;

    public boolean isSubject() {
        switch (this) {
            case COMPUTER_SCIENCE:
            case SOCIAL_SCIENCE:
            case SCIENCE:
            case BIOLOGY:
            case COMMERCE:
            case TAMIL:
            case ENGLISH:
            case MATHS: 
                return true;
            default:
                return false;
        }
    }

    public static Subject getSubjectInstance(String subjectName) {
        switch (subjectName) {
            case "COMPUTER SCIENCE":
                return COMPUTER_SCIENCE;
            case "BIOLOGY":
                return BIOLOGY;
            case "COMMERCE":
                return COMMERCE;
            case "TAMIL":
                return TAMIL;
            case "ENGLISH":
                return ENGLISH;
            case "MATHS":
                return MATHS;
            case "SCIENCE":
                return SCIENCE;
            case "SOCIAL SCIENCE":
                return SOCIAL_SCIENCE;
            default:
                return null;
        }
    }
}
