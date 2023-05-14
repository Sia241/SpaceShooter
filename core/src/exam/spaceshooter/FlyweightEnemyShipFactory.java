package exam.spaceshooter;



import static exam.spaceshooter.IGameObject.textureAtlas;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;
import java.util.Map;

public class FlyweightEnemyShipFactory {
    private static final Map<String, FlyweightEnemyShip> enemyShipMap = new HashMap<>();

    public static FlyweightEnemyShip getEnemyShip(String textureRegionName) {
        if (!enemyShipMap.containsKey(textureRegionName)) {
            TextureRegion enemyShipTextureRegion = textureAtlas.findRegion(textureRegionName);
            enemyShipMap.put(textureRegionName, new FlyweightEnemyShip(enemyShipTextureRegion));
        }

        return enemyShipMap.get(textureRegionName);
    }
}
