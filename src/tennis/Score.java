package tennis;

public record Score(Point playerOnePoints, Point playerTwoPoints) {
    boolean isDeuce() {
        return playerOnePoints == Point.FORTY && playerTwoPoints == Point.FORTY;
    }
}
