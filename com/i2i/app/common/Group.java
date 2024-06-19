package com.i2i.app.common;

/**
 * Enum representing different groups of subjects.
 */
public enum Group {
    COMPUTERSCIENCE(1),
    BIOLOGY(2),
    COMMERCE(3);

    private final int groupId;

    /**
     * Constructor to initialize groupId and groupName for each enum constant.
     *
     * @param groupId   the unique identifier for the group.
     * @param groupName the name of the group.
     */
    Group(int groupId) {
        this.groupId = groupId;
    }

    public int getGroupId() {
        return groupId;
    }

	public static Group fromString(String groupName) {
        for (Group group : Group.values()) {
            if (group.groupName.equalsIgnoreCase(groupName)) {
                return group;
            }
        }
        return null;
    }
}
