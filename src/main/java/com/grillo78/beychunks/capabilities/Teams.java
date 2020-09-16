package com.grillo78.beychunks.capabilities;

import java.util.ArrayList;
import java.util.List;

public class Teams implements ITeams {

    public List<Team> teams = new ArrayList<>();

    @Override
    public List<Team> getTeams() {
        return teams;
    }

    @Override
    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }
}
