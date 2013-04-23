package net.lmxm.suafe.api;

public final class User {
    private String name;

    private String alias;

    protected User(final String name, final String alias) {
        this.name = name;
        this.alias = alias;
    }

    public String getName() {
        return name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(final String alias) {
        this.alias = alias;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
