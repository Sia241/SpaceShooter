package exam.spaceshooter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameOverScreen extends StartScreen implements Screen {

    private SpriteBatch batch;
    protected Stage stage;
    private Viewport viewport;
    private OrthographicCamera camera;
    private Skin skin;

    BitmapFont font,font1;

    public GameOverScreen()
    {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(400, 400, camera); // change the viewport size to 200x200
        viewport.apply();

        camera.position.set(camera.viewportWidth / 4, camera.viewportHeight / 4, 0);
        camera.update();

        stage = new Stage(viewport, batch);

        Gdx.graphics.setWindowedMode(500,500);
    }

    @Override
    public void show() {
        //Stage should controll input:
        Gdx.input.setInputProcessor(stage);

        //Create Table
        Table mainTable = new Table();
        //Set table to fill stage
        mainTable.setFillParent(true);
        //Set alignment of contents in the table.
        mainTable.center();

        //Create an image widget with the image you want to display
        Image gameOverImage = new Image(new Texture("C:\\Users\\hp\\Desktop\\exam\\assets\\logo.png"));

        //Create a label to display the game over message

        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("EdgeOfTheGalaxyRegular-OVEa6.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter1 = new FreeTypeFontGenerator.FreeTypeFontParameter();

        fontParameter.size = 40;
        fontParameter.borderWidth = 3f;
        fontParameter.color = new Color(1, 1, 1, 0.3f);
        fontParameter.borderColor = new Color(0, 0, 0, 0.3f);

        fontParameter1.size = 80;
        fontParameter1.borderWidth = 3f;
        fontParameter1.color = new Color(234, 1, 1, 0.3f);
        fontParameter1.borderColor = new Color(255, 0, 0, 0.3f);

        font = fontGenerator.generateFont(fontParameter);
        font1 = fontGenerator.generateFont(fontParameter1);

        style.font = font;
        style1.font = font1;

        //Create buttons
        TextButton  mainGameOver = new TextButton("Game Over !",style1 );

        TextButton  mainMenuButton = new TextButton("Tap to replay",style );
        TextButton exitButton = new TextButton("Exit", style );

        //Add listeners to the button
        mainMenuButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new StartScreen());
            }
        });

        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });



        //Add buttons to table
        mainTable.add( mainGameOver);
        mainTable.row();
        mainTable.add(mainMenuButton);
        mainTable.row();
        mainTable.row();
        mainTable.add(exitButton);
        mainTable.setSize(250, 250);


        //Add table to stage
        stage.addActor(mainTable);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.1f, .12f, .16f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose(){

    }
}
