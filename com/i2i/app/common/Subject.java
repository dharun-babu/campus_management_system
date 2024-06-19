package com.i2i.app.common;

/**
 * Enum representing different subjects.
 */
public enum Subject {
    COMPUTERSCIENCE("Computer Science"),
    BIOLOGY("Biology"),
    COMMERCE("Commerce"),
    TAMIL("Tamil"),
    ENGLISH("English"),
    MATHS("Maths"),
    SCIENCE("Science"),
    SOCIALSCIENCE("Social Science");

    private final String displayName;

    /**
     * <p> Constructor to initialize the display name of the subject.</p>
     *
     * @param displayName  the display name of the subject.
     */
    Subject(String displayName) {
        this.displayName = displayName;
    }

	public String getDisplayName() {
        return displayName;
    }

	public static Subject getSubjectInstance(String subjectName) {
       for (Subject subject : Subject.values()) {
           if (subject.displayName.equalsIgnoreCase(subjectName)) {
               return subject;
           }
       }
       return null;
    }

	public boolean isSubject() {
        switch (this) {
            case COMPUTER_SCIENCE:
            case BIOLOGY:
            case COMMERCE:
            case TAMIL:
            case ENGLISH:
            case MATHS:
            case SCIENCE:
            case SOCIAL_SCIENCE:
                return true;
            default:
                return false;
        }
    }
}