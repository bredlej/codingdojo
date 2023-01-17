package tennis;

public record Score(Point playerOnePoints, Point playerTwoPoints) {
    boolean isDeuce() {
        return playerOnePoints == Point.FORTY && playerTwoPoints == Point.FORTY;
    }

    public Score update(TurnOutcome turnOutcome) throws InvalidGameStateException {
        switch (turnOutcome) {
            case PlayerOneScores -> {
                return new Score(playerOnePoints().next(), playerTwoPoints());
            }
            case PlayerTwoScores -> {
                return new Score(playerOnePoints(), playerTwoPoints().next());
            }
        }
        throw new InvalidGameStateException();
    }
}
