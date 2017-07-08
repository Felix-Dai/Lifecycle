package com.dfl.lib.lifecycle.interface_;

/**
 * Created by felix.dai on 2017/4/20.
 */

public interface Filter<T> {
    boolean hit(T t);
    boolean deadWith(T t);

    Filter<Object> NONE = new Filter<Object>() {
        @Override
        public boolean hit(Object o) {
            return false;
        }

        @Override
        public boolean deadWith(Object o) {
            return false;
        }
    };

    Filter<Object> ALL = new Filter<Object>() {
        @Override
        public boolean hit(Object o) {
            return true;
        }

        @Override
        public boolean deadWith(Object o) {
            return false;
        }
    };
}
