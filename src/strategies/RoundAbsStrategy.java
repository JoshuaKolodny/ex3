package strategies;

public class RoundAbsStrategy implements RoundStrategy {

    @Override
    public double roundBrightness(double brightness) {
        return Math.abs(brightness);
    }
}
