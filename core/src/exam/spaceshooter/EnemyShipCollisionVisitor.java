package exam.spaceshooter;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import java.util.List;

public class EnemyShipCollisionVisitor implements CollisionVisitor {
    private final List<EnemyShip> enemyShipList;
    private final List<Explosion> explosionList;
    private final Texture explosionTexture;

    public EnemyShipCollisionVisitor(List<EnemyShip> enemyShipList, List<Explosion> explosionList, Texture explosionTexture) {
        this.enemyShipList = enemyShipList;
        this.explosionList = explosionList;
        this.explosionTexture = explosionTexture;
    }

    @Override
    public void visit(EnemyShip enemyShip, Laser laser) {
            enemyShipList.remove(enemyShip);
            explosionList.add(new Explosion(explosionTexture, new Rectangle(enemyShip.boundingBox), 0.7f));


    }

    @Override
    public void visit(Asteroid asteroid, Laser laser) {
    }

}