package exam.spaceshooter;

public interface ScoreVisitable {
    public void accept(ScoringVisitor visitor);

}
