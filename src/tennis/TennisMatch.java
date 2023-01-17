package tennis;

import java.util.Optional;

public class TennisMatch {
    private final Player playerOne;
    private final Player playerTwo;
    private final Score score;
    public final MatchState matchState;

    public TennisMatch(Player playerOne, Player playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        score = new Score(Point.LOVE, Point.LOVE);
        matchState = MatchState.ONGOING;
    }

    private TennisMatch(Player playerOne, Player playerTwo, Score score, MatchState matchState) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.score = score;
        this.matchState = matchState;
    }

    public TennisMatch updateForOutcome(TurnOutcome turnOutcome) throws InvalidGameStateException {
        MatchState newState = matchState;
        Score newScore = updateScore(turnOutcome, score);
        switch (matchState) {
            case ONGOING -> {
                if (newScore.isDeuce()) {
                    newState = MatchState.DEUCE;
                } else {
                    if (score.playerOnePoints() == Point.FORTY && turnOutcome == TurnOutcome.PlayerOneScores) {
                        newState = MatchState.PLAYER_ONE_WON;
                    }
                    else if (score.playerTwoPoints() == Point.FORTY && turnOutcome == TurnOutcome.PlayerTwoScores) {
                        newState = MatchState.PLAYER_TWO_WON;
                    }
                }
            }
            case DEUCE -> newState = turnOutcome == TurnOutcome.PlayerOneScores ? MatchState.ADVANTAGE_PLAYER_ONE : MatchState.ADVANTAGE_PLAYER_TWO;
            case ADVANTAGE_PLAYER_ONE -> newState = turnOutcome == TurnOutcome.PlayerOneScores ? MatchState.PLAYER_ONE_WON : MatchState.DEUCE;
            case ADVANTAGE_PLAYER_TWO -> newState = turnOutcome == TurnOutcome.PlayerTwoScores ? MatchState.PLAYER_TWO_WON : MatchState.DEUCE;
            case PLAYER_ONE_WON, PLAYER_TWO_WON -> throw new InvalidGameStateException();
        }

        return new TennisMatch(playerOne, playerTwo, newScore, newState);
    }

    public Optional<Player> getWinner() {
        switch (matchState) {
            case PLAYER_ONE_WON -> {return Optional.of(playerOne);}
            case PLAYER_TWO_WON -> {
                return Optional.of(playerTwo);
            }
        }
        return Optional.empty();
    }
    private Score updateScore(TurnOutcome outcome, Score previousScore) throws InvalidGameStateException {
        switch (outcome) {
            case PlayerOneScores -> {
                return new Score(previousScore.playerOnePoints().next(), previousScore.playerTwoPoints());
            }
            case PlayerTwoScores -> {
                return new Score(previousScore.playerOnePoints(), previousScore.playerTwoPoints().next());
            }
        }
        throw new InvalidGameStateException();
    }

    public Score getScore() {
        return score;
    }
}
