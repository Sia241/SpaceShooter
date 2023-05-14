package exam.spaceshooter;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public interface IGameObject {
    static TextureAtlas  textureAtlas = new TextureAtlas("images.atlas");;
    void Update();
    void Draw(SpriteBatch spriteBatch);
}
