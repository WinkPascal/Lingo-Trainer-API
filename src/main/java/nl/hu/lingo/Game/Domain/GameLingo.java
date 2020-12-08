package nl.hu.lingo.Game.Domain;

import nl.hu.lingo.Game.Persistence.GameRepositoryPostgress;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameLingo implements Game {
    private int id;
    private String userName;
    List<Round> rounds;

    public GameLingo(int id, String userName, List<Round> rounds){
        this.id = id;
        this.userName = userName;
        this.rounds = rounds;
    }

    @Override
    public List<Round> getRounds() {
        return null;
    }

    @Override
    public List<String> nextMove(Try currentTry) {
        return null;
    }

    @Override
    public int endGame() {
        return 0;
    }
}
