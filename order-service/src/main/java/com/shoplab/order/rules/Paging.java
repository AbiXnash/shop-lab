package com.shoplab.order.rules;

/** Pages are numbered from 1. */
public final class Paging {
    private Paging() {
    }

    /** The index of the first row on a page. */
    public static int offset(int page, int size) {
        if (page < 1 || size < 1) {
            throw new IllegalArgumentException("page and size start at 1");
        }
        return page * size;
    }
}
