package com.example.joo.scribblesonthebook.Provider;

import com.squareup.otto.Bus;

/**
 * Created by Joo on 2016-06-29.
 */
public class BusProvider {
    private static Bus instance;

    private BusProvider() {}

    public static Bus getInstance() {
        if (instance == null) {
            instance = new Bus();
        }
        return instance;
    }
}
