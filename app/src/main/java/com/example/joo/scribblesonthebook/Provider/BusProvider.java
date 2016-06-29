package com.example.joo.scribblesonthebook.Provider;

import com.squareup.otto.Bus;

/**
 * Created by Joo on 2016-06-29.
 */
public class BusProvider {
    private static final Bus BUS = new Bus();

    public static Bus getInstance() {
        return BUS;
    }

    private BusProvider() {
        // No instances.
    }
}
