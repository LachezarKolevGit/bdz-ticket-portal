package bg.tusofia.vvps.ticketsystem.traincarriage;

public enum TrainCarriageType {
    CLASS_A(1.5),
    CLASS_B(1),
    SLEEPER(2);

    private double multiplier;

    TrainCarriageType(double multiplier) {
        this.multiplier = multiplier;
    }

    public double getMultiplier() {
        return multiplier;
    }
}
