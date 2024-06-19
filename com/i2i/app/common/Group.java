package com.i2i.app.common;

public enum Group {
    COMPUTER SCIENCE, BIOLOGY, COMMERCE;

    public int getGroupId() {
        switch (this) {
        case COMPUTERSCIENCE:
            return 1;
        case BIOLOGY:
            return 2;
        case COMMERCE:
            return 3;
        default:
            return 0;
        }
    }

	public static Group getGroupInstance(String groupName) {
        switch (groupName.toUpperCase()) {
            case "COMPUTER SCIENCE":
                return COMPUTERSCIENCE;
            case "BIOLOGY":
                return BIOLOGY;
            case "COMMERCE":
                return COMMERCE;
            default:
                return null;
        }
    }
}
