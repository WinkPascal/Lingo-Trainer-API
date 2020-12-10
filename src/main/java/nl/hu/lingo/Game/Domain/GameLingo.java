package nl.hu.lingo.Game.Domain;

import java.util.List;

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
        Round round = getCurrentRound();
        round.IsCorrect(currentTry);
        return null;
    }
    private Round getCurrentRound(){
        int highestId= 0;
        Round newestRound = null;
        for (Round round : rounds) {
            int roundId  = round.getId();
            if(highestId < roundId){
                newestRound = round;
            }
        }
        return newestRound;
    }



    @Override
    public int endGame() {
        return 0;
    }
}
