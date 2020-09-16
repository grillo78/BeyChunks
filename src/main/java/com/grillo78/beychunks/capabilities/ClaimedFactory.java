package com.grillo78.beychunks.capabilities;

import java.util.concurrent.Callable;

public class ClaimedFactory implements Callable<IClaimed> {
    @Override
    public IClaimed call() throws Exception {
        return new Claimed();
    }
}
