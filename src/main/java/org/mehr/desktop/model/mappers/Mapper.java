package org.mehr.desktop.model.mappers;

public interface Mapper<T, V> {
    V map(T input);
}
