package exam.spaceshooter;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class FlyweightAsteroid {
    private TextureRegion asteroidTextureRegion;
    private float width,height;

    public FlyweightAsteroid(TextureRegion asteroidTextureRegion) {
        this.asteroidTextureRegion = asteroidTextureRegion;
        System.out.println("Hello 1, i am in FlyweightAsteroid constructor, why makantla3ch " +width+" "+height+" and"+asteroidTextureRegion.toString()+"rock1");

        if( asteroidTextureRegion.toString().equals("rock1")){
            this.width =4;
            this.height=5;
        } else if (asteroidTextureRegion.toString().equals("rock2")) {
            this.width =6;
            this.height=7;
        } else if (asteroidTextureRegion.toString().equals("rock3")) {
            this.width =7;
            this.height=8;
        }


    }

    public TextureRegion getTextureRegion() {
        return asteroidTextureRegion;
    }

    public float getWidth() {
        return this.width;
    }

    public float getHeight() {
        return this.height;
    }
}
