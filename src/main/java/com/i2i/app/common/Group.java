package com.i2i.app.common;

/**
 * Enum representing different groups of subjects.
 */
public enum Group {
    COMPUTER_SCIENCE(1),
    BIOLOGY(2),
    COMMERCE(3);

    private final int groupId;

    /**
     * Constructor to initialize groupId for each enum constant.
     *
     * @param groupId the unique identifier for the group.
     */
    Group(int groupId) {
        this.groupId = groupId;
    }

    /**
     * Returns the unique identifier of the group.
     *
     * @return the group ID.
     */
    public int getGroupId() {
        return groupId;
    }

    /**
     * Retrieves the Group instance based on the groupName.
     *
     * @param groupName the name of the group.
     * @return the corresponding Group instance, or null if not found.
     */
    public static Group getGroupInstance(String groupName) {
        for (Group group : Group.values()) {
            if (group.name().equalsIgnoreCase(groupName)) {
                return group;
            }
        }
        return null;
    }
}
