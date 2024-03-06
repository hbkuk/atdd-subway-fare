package nextstep.core.subway.pathFinder.application;

import org.springframework.stereotype.Component;

@Component
public class FareCalculator {

    public static final int BASE_FARE = 1250;

    public static final int STANDARD_DISTANCE = 10;
    public static final int STANDARD_ADDITIONAL_DISTANCE = 5;

    public static final int LONG_DISTANCE = 50;
    public static final int LONG_ADDITIONAL_DISTANCE = 8;

    public static final int ADDITIONAL_FARE_PER_KM = 100;

    public int calculateFare(int distance) {
        int fare = BASE_FARE;

        if (distance > STANDARD_DISTANCE && distance <= LONG_DISTANCE) {
            fare += calculateOverFare(distance - STANDARD_DISTANCE, STANDARD_ADDITIONAL_DISTANCE);
        }
        if (distance > LONG_DISTANCE) {
            fare += calculateOverFare(LONG_DISTANCE - STANDARD_DISTANCE, STANDARD_ADDITIONAL_DISTANCE);
            fare += calculateOverFare(distance - LONG_DISTANCE, LONG_ADDITIONAL_DISTANCE);
        }
        return fare;
    }

    private int calculateOverFare(int distance, int additionalDistance) {
        return (int) (((double) ((distance - 1) / additionalDistance) + 1) * ADDITIONAL_FARE_PER_KM);
    }
}
