package tennis;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TennisMatchTest {
    @Test
    public void testUpdatesScoreOnceForFirstPlayer() throws InvalidGameStateException {
        // given
        TennisMatch match = new TennisMatch(new Player(1L), new Player(2L));
        // when
        match = match.updateForOutcome(TurnOutcome.PlayerOneScores);
        // then
        Assertions.assertEquals(Point.FIFTEEN, match.getScore().playerOnePoints());
        Assertions.assertEquals(Point.LOVE, match.getScore().playerTwoPoints());
    }

    @Test
    public void testUpdatesScoreTwiceForFirstPlayer() throws InvalidGameStateException {
        // given
        TennisMatch match = new TennisMatch(new Player(1L), new Player(2L));
        // when
        match = match
                .updateForOutcome(TurnOutcome.PlayerOneScores)
                .updateForOutcome(TurnOutcome.PlayerOneScores);
        // then
        Assertions.assertEquals(Point.THIRTY, match.getScore().playerOnePoints());
        Assertions.assertEquals(Point.LOVE, match.getScore().playerTwoPoints());
    }

    @Test
    public void testUpdatesScoreTwiceForSecondPlayer() throws InvalidGameStateException {
        // given
        TennisMatch match = new TennisMatch(new Player(1L), new Player(2L));
        // when
        match = match
                .updateForOutcome(TurnOutcome.PlayerTwoScores)
                .updateForOutcome(TurnOutcome.PlayerTwoScores);
        // then
        Assertions.assertEquals(Point.LOVE, match.getScore().playerOnePoints());
        Assertions.assertEquals(Point.THIRTY, match.getScore().playerTwoPoints());
    }

    @Test
    public void testUpdatesScoreFullyForFirstPlayer() throws InvalidGameStateException {
        // given
        TennisMatch match = new TennisMatch(new Player(1L), new Player(2L));
        // when
        match = match
                .updateForOutcome(TurnOutcome.PlayerOneScores)
                .updateForOutcome(TurnOutcome.PlayerOneScores)
                .updateForOutcome(TurnOutcome.PlayerOneScores)
                .updateForOutcome(TurnOutcome.PlayerOneScores);
        // then
        Assertions.assertEquals(Point.FORTY, match.getScore().playerOnePoints());
        Assertions.assertEquals(Point.LOVE, match.getScore().playerTwoPoints());
    }

    @Test
    public void testUpdatesScoreOnceForSecondPlayer() throws InvalidGameStateException {
        // given
        TennisMatch match = new TennisMatch(new Player(1L), new Player(2L));
        // when
        match = match.updateForOutcome(TurnOutcome.PlayerTwoScores);
        // then
        Assertions.assertEquals(Point.LOVE, match.getScore().playerOnePoints());
        Assertions.assertEquals(Point.FIFTEEN, match.getScore().playerTwoPoints());
    }

    @Test
    public void testUpdatesScoreFullyForSecondPlayer() throws InvalidGameStateException {
        // given
        TennisMatch match = new TennisMatch(new Player(1L), new Player(2L));
        // when
        match = match
                .updateForOutcome(TurnOutcome.PlayerTwoScores)
                .updateForOutcome(TurnOutcome.PlayerTwoScores)
                .updateForOutcome(TurnOutcome.PlayerTwoScores)
                .updateForOutcome(TurnOutcome.PlayerTwoScores);
        // then
        Assertions.assertEquals(Point.LOVE, match.getScore().playerOnePoints());
        Assertions.assertEquals(Point.FORTY, match.getScore().playerTwoPoints());
    }

    @Test
    public void testUpdatesScoreFullyForBothPlayers() throws InvalidGameStateException {
        // given
        TennisMatch match = updateMatchToDeuceState(new TennisMatch(new Player(1L), new Player(2L)));
        // then
        Assertions.assertEquals(Point.FORTY, match.getScore().playerOnePoints());
        Assertions.assertEquals(Point.FORTY, match.getScore().playerTwoPoints());
    }

    @Test
    public void testPlayerOneWins() throws InvalidGameStateException {
        TennisMatch match = new TennisMatch(new Player(1L), new Player(2L))
                .updateForOutcome(TurnOutcome.PlayerOneScores)
                .updateForOutcome(TurnOutcome.PlayerOneScores)
                .updateForOutcome(TurnOutcome.PlayerOneScores)
                .updateForOutcome(TurnOutcome.PlayerOneScores);
        Assertions.assertEquals(MatchState.PLAYER_ONE_WON, match.matchState);
        Assertions.assertEquals(new Player(1L), match.getWinner().get());
    }

    @Test
    public void testPlayerTwoWins() throws InvalidGameStateException {
        TennisMatch match = new TennisMatch(new Player(1L), new Player(2L))
                .updateForOutcome(TurnOutcome.PlayerTwoScores)
                .updateForOutcome(TurnOutcome.PlayerTwoScores)
                .updateForOutcome(TurnOutcome.PlayerTwoScores)
                .updateForOutcome(TurnOutcome.PlayerTwoScores);
        Assertions.assertEquals(MatchState.PLAYER_TWO_WON, match.matchState);
        Assertions.assertEquals(new Player(2L), match.getWinner().get());
    }

    @Test
    public void testUpdatesScoreSoDeuceIsReached() throws InvalidGameStateException {
        // given
        TennisMatch match = new TennisMatch(new Player(1L), new Player(2L));
        Assertions.assertEquals(MatchState.ONGOING, match.matchState);
        // when
        match = updateMatchToDeuceState(match);
        // then
        Assertions.assertEquals(MatchState.DEUCE, match.matchState);
    }

    @Test
    public void testUpdatesScoreSoPlayerOneHasAdvantage() throws InvalidGameStateException {
        // given
        // when
        TennisMatch match = updateMatchToDeuceState(new TennisMatch(new Player(1L), new Player(2L)))
                .updateForOutcome(TurnOutcome.PlayerOneScores);
        // then
        Assertions.assertEquals(MatchState.ADVANTAGE_PLAYER_ONE, match.matchState);
    }

    @Test
    public void testUpdatesScoreSoPlayerTwoHasAdvantage() throws InvalidGameStateException {
        // given
        // when
        TennisMatch match = updateMatchToDeuceState(new TennisMatch(new Player(1L), new Player(2L))).updateForOutcome(TurnOutcome.PlayerTwoScores);
        // then
        Assertions.assertEquals(MatchState.ADVANTAGE_PLAYER_TWO, match.matchState);
    }

    @Test
    public void testUpdatesScoreSoPlayerOneWins() throws InvalidGameStateException {
        // given
        // when
        TennisMatch match = updateMatchToDeuceState(new TennisMatch(new Player(1L), new Player(2L)))
                .updateForOutcome(TurnOutcome.PlayerOneScores)
                .updateForOutcome(TurnOutcome.PlayerOneScores);
        // then
        Assertions.assertEquals(MatchState.PLAYER_ONE_WON, match.matchState);
    }

    @Test
    public void testUpdatesScoreSoPlayerTwoWins() throws InvalidGameStateException {
        // given
        // when
        TennisMatch match = updateMatchToDeuceState(new TennisMatch(new Player(1L), new Player(2L)))
                .updateForOutcome(TurnOutcome.PlayerTwoScores)
                .updateForOutcome(TurnOutcome.PlayerTwoScores);
        // then
        Assertions.assertEquals(MatchState.PLAYER_TWO_WON, match.matchState);
    }

    @Test
    public void testUpdatesScoreSoDeuceReachedFromPlayerOneAdvantage() throws InvalidGameStateException {
        // given
        // when
        TennisMatch match = updateMatchToDeuceState(new TennisMatch(new Player(1L), new Player(2L)))
                .updateForOutcome(TurnOutcome.PlayerOneScores)
                .updateForOutcome(TurnOutcome.PlayerTwoScores);
        // then
        Assertions.assertEquals(MatchState.DEUCE, match.matchState);
    }

    @Test
    public void testUpdatesScoreSoDeuceReachedFromPlayerTwoAdvantage() throws InvalidGameStateException {
        // given
        // when
        TennisMatch match = updateMatchToDeuceState(new TennisMatch(new Player(1L), new Player(2L)))
                .updateForOutcome(TurnOutcome.PlayerTwoScores)
                .updateForOutcome(TurnOutcome.PlayerOneScores);
        // then
        Assertions.assertEquals(MatchState.DEUCE, match.matchState);
    }

    @Test()
    public void testGoesToInvalidStateWhenPlayerOneAlreadyWon() {
        // then
        Assertions.assertThrows(InvalidGameStateException.class,
                () -> updateMatchToDeuceState(new TennisMatch(new Player(1L), new Player(2L)))
                        .updateForOutcome(TurnOutcome.PlayerOneScores)
                        .updateForOutcome(TurnOutcome.PlayerOneScores)
                        .updateForOutcome(TurnOutcome.PlayerOneScores));
    }

    @Test()
    public void testGoesToInvalidStateWhenPlayerTwoAlreadyWon() {
        // when
        Assertions.assertThrows(InvalidGameStateException.class,
                () -> updateMatchToDeuceState(new TennisMatch(new Player(1L), new Player(2L)))
                        .updateForOutcome(TurnOutcome.PlayerTwoScores)
                        .updateForOutcome(TurnOutcome.PlayerTwoScores)
                        .updateForOutcome(TurnOutcome.PlayerTwoScores));
    }

    private static TennisMatch updateMatchToDeuceState(TennisMatch match) throws InvalidGameStateException {
        return match
                .updateForOutcome(TurnOutcome.PlayerOneScores)
                .updateForOutcome(TurnOutcome.PlayerOneScores)
                .updateForOutcome(TurnOutcome.PlayerOneScores)
                .updateForOutcome(TurnOutcome.PlayerTwoScores)
                .updateForOutcome(TurnOutcome.PlayerTwoScores)
                .updateForOutcome(TurnOutcome.PlayerTwoScores);
    }
}
