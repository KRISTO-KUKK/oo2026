package ee.kristo.klassikomplekt.entity;

public enum SportEvent {
    M100("100 m", true, false, 25.4347, 18.0, 1.81),
    LONG_JUMP("Kaugushupe", false, true, 0.14354, 220.0, 1.4),
    SHOT_PUT("Kuulitauge", false, false, 51.39, 1.5, 1.05),
    HIGH_JUMP("Korgushupe", false, true, 0.8465, 75.0, 1.42),
    M400("400 m", true, false, 1.53775, 82.0, 1.81),
    M110_HURDLES("110 m tokked", true, false, 5.74352, 28.5, 1.92),
    DISCUS_THROW("Kettaheide", false, false, 12.91, 4.0, 1.1),
    POLE_VAULT("Teivashupe", false, true, 0.2797, 100.0, 1.35),
    JAVELIN_THROW("Odavise", false, false, 10.14, 7.0, 1.08),
    M1500("1500 m", true, false, 0.03768, 480.0, 1.85);

    private final String displayName;
    private final boolean trackEvent;
    private final boolean metersToCentimeters;
    private final double coefficientA;
    private final double coefficientB;
    private final double coefficientC;

    SportEvent(
            String displayName,
            boolean trackEvent,
            boolean metersToCentimeters,
            double coefficientA,
            double coefficientB,
            double coefficientC
    ) {
        this.displayName = displayName;
        this.trackEvent = trackEvent;
        this.metersToCentimeters = metersToCentimeters;
        this.coefficientA = coefficientA;
        this.coefficientB = coefficientB;
        this.coefficientC = coefficientC;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getUnit() {
        return trackEvent ? "sekundid" : "meetrid";
    }

    public int calculatePoints(double performance) {
        if (performance <= 0) {
            throw new RuntimeException("Tulemus peab olema positiivne number.");
        }

        double scoringPerformance = metersToCentimeters ? performance * 100.0 : performance;
        double base = trackEvent ? coefficientB - scoringPerformance : scoringPerformance - coefficientB;

        if (base <= 0) {
            return 0;
        }

        return (int) Math.floor(coefficientA * Math.pow(base, coefficientC));
    }
}
