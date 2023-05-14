package exam.spaceshooter;

public interface CollisionVisitor {
    void visit(EnemyShip enemyShip, Laser laser);
    void visit(Asteroid asteroid, Laser laser);
}
