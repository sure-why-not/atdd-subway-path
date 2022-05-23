package wooteco.subway.domain;

import org.jgrapht.graph.DefaultWeightedEdge;

public class ShortestPathEdge extends DefaultWeightedEdge {

    private final Line line;
    private final int distance;

    public ShortestPathEdge(final Line line, final int distance) {
        this.line = line;
        this.distance = distance;
    }

    public Line getLine() {
        return line;
    }

    @Override
    protected double getWeight() {
        return distance;
    }

    @Override
    public String toString() {
        return "ShortestPathEdge{" +
                "line=" + line +
                ", distance=" + distance +
                '}';
    }
}
