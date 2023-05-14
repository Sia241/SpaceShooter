package exam.spaceshooter;

public interface ScoringVisitor {
    void visit(EnemyShip enemyShip);

    void visit(Asteroid asteroid1);

}