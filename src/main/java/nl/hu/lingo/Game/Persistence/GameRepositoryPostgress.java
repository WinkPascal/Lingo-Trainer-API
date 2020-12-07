package nl.hu.lingo.Game.Persistence;

import nl.hu.lingo.Game.Domain.Game;
import nl.hu.lingo.Game.Domain.GameRepository;

public class GameRepositoryPostgress implements GameRepository {

    @Override
    public Game getGameById(int id) {
        return null;
    }

    @Override
    public void saveGame(Game game) {

    }
}
