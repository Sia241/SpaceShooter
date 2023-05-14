package exam.spaceshooter;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class FlyweightEnemyShip {
    private TextureRegion enemyShipTextureRegion;
    private static final int pointValue = 100;

    public FlyweightEnemyShip(TextureRegion enemyShipTextureRegion) {
        this.enemyShipTextureRegion = enemyShipTextureRegion;
    }

    public TextureRegion getEnemyShipTextureRegion() {
        return enemyShipTextureRegion;
    }

    public int getPointValue() {
        return pointValue;
    }
}
