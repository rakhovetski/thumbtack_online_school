package net.thumbtack.school.concurrent;

import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TwelveTask<T, V> {
    private static final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Map<T, V> map;

    public TwelveTask(Map<T, V> map) {
        this.map = map;
    }

    public void clear() {
        lock.writeLock().lock();
        try {
            map.clear();
        } finally {
            lock.writeLock().unlock();
        }
    }

    public int size() {
        lock.readLock().lock();
        try {
            return map.size();
        } finally {
            lock.readLock().unlock();
        }
    }

    public Object put(T key, V value) {
        lock.writeLock().lock();
        try {
            map.put(key, value);
        } finally {
            lock.writeLock().unlock();
        }
        return key;
    }

    public Object get(T key) {
        lock.readLock().lock();
        try {
            return map.get(key);
        } finally {
            lock.readLock().unlock();
        }
    }

    public Object remove(T key) {
        lock.writeLock().lock();
        try {
            return map.remove(key);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public boolean remove(T key, V value) {
        lock.writeLock().lock();
        try {
            return map.remove(key, value);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public Object replace(T key, V value) {
        lock.writeLock().lock();
        try {
            return map.replace(key, value);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public boolean isEmpty() {
        lock.readLock().lock();
        try {
            return map.isEmpty();
        } finally {
            lock.readLock().unlock();
        }
    }

    public boolean containsKey(T key) {
        lock.readLock().unlock();
        try {
            return map.containsKey(key);
        } finally {
            lock.readLock().unlock();
        }
    }

    public boolean containsValue(V value) {
        lock.readLock().unlock();
        try {
            return map.containsValue(value);
        } finally {
            lock.readLock().unlock();
        }
    }
}
