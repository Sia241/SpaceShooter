package exam.spaceshooter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Random;

public class SpaceShooterGame extends Game {

	SpriteBatch batch;
	BitmapFont font;
	GameScreen gamescreen;
	ShapeRenderer shapeRenderer;
	StartScreen test;
	public static Random random = new Random();

	@Override
	public void create() {
    test = new StartScreen();
		/*gamescreen = new GameScreen();
		shapeRenderer = new ShapeRenderer();
		font = new BitmapFont();
		setScreen(new TitleScreen(this));*/
		//	setScreen(gamescreen);
		setScreen(test);

	}


	@Override
	public void dispose() {
	//	batch.dispose();
		//shapeRenderer.dispose();
		//font.dispose();
		test.dispose();
	}

	@Override
	public void render() {

		super.render();

	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}
}