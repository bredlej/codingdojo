package tennis;

public enum Point {
    LOVE, FIFTEEN, THIRTY, FORTY;

    public Point next() throws InvalidGameStateException {
        switch (this) {
            case LOVE -> {
                return FIFTEEN;
            }
            case FIFTEEN -> {
                return THIRTY;
            }
            case THIRTY, FORTY -> {
                return FORTY;
            }
        }
        throw new InvalidGameStateException();
    }
}
