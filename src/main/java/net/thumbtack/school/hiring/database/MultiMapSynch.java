package net.thumbtack.school.hiring.database;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

public class MultiMapSynch<K, V>{
    private static final ReentrantLock lock = new ReentrantLock();
    private final MultiValuedMap<K, V> map = new HashSetValuedHashMap<>();

    public void put(K k, V v) {
        try {
            lock.lock();
            map.put(k, v);
        } finally {
            lock.unlock();
        }
    }

    public Set<V> get(K k) {
        try {
            lock.lock();
            return new HashSet<>(map.get(k));
        } finally {
            lock.unlock();
        }
    }

    public void removeMapping(K k, V v) {
        try {
            lock.lock();
            map.removeMapping(k, v);
        } finally {
            lock.unlock();
        }
    }

    public void remove(K k) {
        try {
            lock.lock();
            map.remove(k);
        } finally {
            lock.unlock();
        }
    }

    public void clear() {
        try {
            lock.lock();
            map.clear();
        } finally {
            lock.unlock();
        }
    }

    public void replace(K k, V vOld, V vNew) {
        try {
            lock.lock();
            map.removeMapping(k, vOld);
            map.put(k, vNew);
        } finally {
            lock.unlock();
        }
    }

    public Set<K> keySet() {
        try {
            lock.lock();
            return map.keySet();
        } finally {
            lock.unlock();
        }
    }

    public Set<V> values() {
        try {
            lock.lock();
            return new HashSet<>(map.values());
        } finally {
            lock.unlock();
        }
    }
}
