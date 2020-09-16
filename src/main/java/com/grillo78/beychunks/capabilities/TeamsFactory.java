package com.grillo78.beychunks.capabilities;

import java.util.concurrent.Callable;

public class TeamsFactory implements Callable<ITeams> {
    @Override
    public ITeams call() throws Exception {
        return new Teams();
    }
}
