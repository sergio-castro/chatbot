
package org.chatbot.bot;


public abstract class Identifiable<T> {

    private final T id;

    protected Identifiable(T id) {
        this.id = id;
    }

    public T get() {
        return id;
    }

    @Override
    public String toString() {
        return id.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Identifiable command = (Identifiable) o;
        return java.util.Objects.equals(id, command.id);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id);
    }

}
