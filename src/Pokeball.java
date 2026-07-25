
public enum Pokeball {
    POKE_BALL(0.5),
    GREAT_BALL(0.75),
    ULTRA_BALL(0.85),
    MASTER_BALL(1.0);

    private final double catchRate;

    Pokeball(double catchRate) {
        this.catchRate = catchRate;
    }

    public double getCatchRate() {
        return catchRate;
    }
}
