package cpu_scheduling;

public class ComparisonResult {

    private String algorithm;
    private double avgTAT;
    private double avgWT;

    public ComparisonResult(
            String algorithm,
            double avgTAT,
            double avgWT) {

        this.algorithm = algorithm;
        this.avgTAT = avgTAT;
        this.avgWT = avgWT;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public double getAvgTAT() {
        return avgTAT;
    }

    public double getAvgWT() {
        return avgWT;
    }
}