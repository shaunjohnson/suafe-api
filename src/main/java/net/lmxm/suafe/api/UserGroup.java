package net.lmxm.suafe.api;

public final class UserGroup {
    private String name;

    protected UserGroup(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    protected void setName(final String name) {
        this.name = name;
    }
}
