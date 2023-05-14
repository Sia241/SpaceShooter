package exam.spaceshooter;

public class ScoreCounter implements ScoringVisitor {
    private int score;



    @Override
    public void visit(EnemyShip enemyShip) {
        score += enemyShip.getPointValue();
    }

    @Override
    public void visit(Asteroid asteroid1) {
        score += asteroid1.getPointValue();
    }




    public int getScore() {
        return score;
    }
}
