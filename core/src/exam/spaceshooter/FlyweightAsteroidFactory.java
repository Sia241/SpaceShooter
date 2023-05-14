package exam.spaceshooter;

import static exam.spaceshooter.IGameObject.textureAtlas;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;
import java.util.Map;

public class FlyweightAsteroidFactory {
    private static final Map<String, FlyweightAsteroid> asteroidMap = new HashMap<>();

    public static FlyweightAsteroid getAsteroid(String textureRegionName) {
        if (!asteroidMap.containsKey(textureRegionName)) {
            TextureRegion asteroidTextureRegion = textureAtlas.findRegion(textureRegionName);

            asteroidMap.put(textureRegionName, new FlyweightAsteroid(asteroidTextureRegion));
        }
        return asteroidMap.get(textureRegionName);
    }
}
