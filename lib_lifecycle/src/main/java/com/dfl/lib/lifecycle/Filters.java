package com.dfl.lib.lifecycle;

import com.dfl.lib.lifecycle.interface_.Filter;

import java.lang.ref.WeakReference;

/**
 * Created by felix.dai on 2017/4/20.
 */

public class Filters {

    public static Filter<Object> none() {
        return Filter.NONE;
    }

    public static Filter<Object> all() {
        return Filter.ALL;
    }

    public static <T> Filter<? super T> instance(T t) {
        return new InstanceFilter<>(t);
    }

    public static <T> Filter<? super T> type(Class<? extends T> clazz) {
        return new TypeFilter<>(clazz);
    }

    private static class InstanceFilter<T> implements Filter<T> {
        private WeakReference<T> reference;

        InstanceFilter(T t) {
            this.reference = new WeakReference<T>(t);
        }

        @Override
        public boolean hit(T t) {
            return reference != null && reference.get() == t;
        }

        @Override
        public boolean deadWith(T t) {
            T old = reference.get();
            return old == null || old == t;
        }
    }

    private static class TypeFilter<T> implements Filter<T> {
        private Class<? extends T> clazz;

        TypeFilter(Class<? extends T> clazz) {
            this.clazz = clazz;
        }

        @Override
        public boolean hit(T t) {
            return clazz != null && clazz.isInstance(t);
        }

        @Override
        public boolean deadWith(T t) {
            return false;
        }
    }
}
