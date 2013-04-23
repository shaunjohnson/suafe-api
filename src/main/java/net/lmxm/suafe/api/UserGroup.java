package net.lmxm.suafe.api;

public final class UserGroup {
    private final String name;

    protected UserGroup(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
