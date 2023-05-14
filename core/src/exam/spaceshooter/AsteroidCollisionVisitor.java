package exam.spaceshooter;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import java.util.List;
import java.util.ListIterator;

public class AsteroidCollisionVisitor implements CollisionVisitor {
    private final ListIterator<Asteroid> asteroid1List;
    private final List<Explosion> explosionList;
    private final Texture explosionTexture;
    public AsteroidCollisionVisitor(ListIterator<Asteroid> asteroidListIterator, List<Explosion> explosionList, Texture explosionTexture) {
        this.asteroid1List = asteroidListIterator;
        this.explosionList = explosionList;
        this.explosionTexture = explosionTexture;
    }
    @Override
    public void visit(EnemyShip enemyShip, Laser laser) {
    }
    @Override
    public void visit(Asteroid asteroid, Laser laser) {
            asteroid1List.remove();
            explosionList.add(new Explosion(explosionTexture, new Rectangle(asteroid.boundingBox), 0.7f));

    }



}
