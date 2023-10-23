package net.thumbtack.school.functional;

@FunctionalInterface
public interface MyFunction<K, T> {
    K apply(T arg);
}
