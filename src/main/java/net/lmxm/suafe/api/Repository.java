package net.lmxm.suafe.api;

public final class Repository {
    private String name;

    public Repository(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
