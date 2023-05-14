package exam.spaceshooter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


public class GameScreen implements Screen {
    private ThreadPoolExecutor threadPoolExecutor;

    private boolean gameOver = false;
    private static FlyweightAsteroidFactory asteroidFactory = new FlyweightAsteroidFactory();

    //screen
    private Camera camera;
    private Viewport viewport;

    //graphics
    protected SpriteBatch batch;
    private TextureAtlas textureAtlas;
    private Texture explosionTexture;

    private float backgroundHeight; //height of background in World units
    private TextureRegion[] backgrounds;
   //This is done for playerSip
      static TextureRegion playerShipTextureRegion, playerShieldTextureRegion,
            enemyShipTextureRegion, enemyShieldTextureRegion,
            playerLaserTextureRegion, enemyLaserTextureRegion,asteroidTexture;

    //timing
    private float[] backgroundOffsets = {0,0,0,0};
    private float backgroundMaxScrollingSpeed;
    private float timeBetweenEnemySpawns = 1f;

    private float timeBetweenAsteroidSpawns = 1f;
    private float enemySpawnTimer = 0;

    private float asteroidSpawnTimer = 0;

    //game objects
    private PlayerShip playerShip ;

    private LinkedList<EnemyShip> enemyShipList;
    private LinkedList<Asteroid> asteroid1List;
    private LinkedList<Laser> playerLaserList;
    private LinkedList<Laser> enemyLaserList;
    private LinkedList<Explosion> explosionList;

    //world parameters
    private final float WORLD_WIDTH = 72;
    private final float WORLD_HEIGHT = 128;


    private int score = 0;

    //Heads-Up Display
    BitmapFont font;
    float hudVerticalMargin, hudLeftX, hudRightX, hudCentreX, hudRow1Y, hudRow2Y, hudSectionWidth;

    //Asteroids



    // Constructor for GameScreen class
    GameScreen() {

        threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);

        // Create a new camera and viewport
        camera = new OrthographicCamera();
        viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

        // Load the texture atlas
        textureAtlas = new TextureAtlas("images.atlas");

        // Set up an array of backgrounds using TextureRegions from the texture atlas
        backgrounds = new TextureRegion[4];
        backgrounds[0] = textureAtlas.findRegion("Starscape00");
        backgrounds[1] = textureAtlas.findRegion("Starscape01");
        backgrounds[2] = textureAtlas.findRegion("Starscape02");
        backgrounds[3] = textureAtlas.findRegion("Starscape03");

        // Set background height and maximum scrolling speed
        backgroundHeight = WORLD_HEIGHT * 3;
        backgroundMaxScrollingSpeed = (float) (WORLD_HEIGHT) / 4;

        // Load texture regions for player and enemy ships, and lasers
        playerShipTextureRegion = textureAtlas.findRegion("player_ship");//This is done for playerSip
        enemyShipTextureRegion = textureAtlas.findRegion("enemy1");
        playerLaserTextureRegion = textureAtlas.findRegion("player_laser");
        enemyLaserTextureRegion = textureAtlas.findRegion("enemy_laser");
       asteroidTexture = textureAtlas.findRegion("rock2");

        // Load explosion texture
        explosionTexture = new Texture("explosion.png");

        // Create player ship object with specified parameters
        playerShip = new PlayerShip();

        // Create empty lists for enemy ships, player lasers, enemy lasers, and explosions
        enemyShipList = new LinkedList<>();
        playerLaserList = new LinkedList<>();
        enemyLaserList = new LinkedList<>();
        explosionList = new LinkedList<>();
        asteroid1List = new LinkedList<>();





        prepareHUD();

        // Create a new sprite batch
        batch = new SpriteBatch();

        //Asteroids

        Gdx.graphics.setWindowedMode(550,720);

    }

    private void prepareHUD() {
        //Create a BitmapFont from our font file
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("EdgeOfTheGalaxyRegular-OVEa6.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        fontParameter.size = 72;
        fontParameter.borderWidth = 3.6f;
        fontParameter.color = new Color(1, 1, 1, 0.3f);
        fontParameter.borderColor = new Color(0, 0, 0, 0.3f);

        font = fontGenerator.generateFont(fontParameter);

        //scale the font to fit world
        font.getData().setScale(0.08f);

        //calculate hud margins, etc.
        hudVerticalMargin = font.getCapHeight() / 2;
        hudLeftX = hudVerticalMargin;
        hudRightX = WORLD_WIDTH * 2 / 3 - hudLeftX;
        hudCentreX = WORLD_WIDTH / 3;
        hudRow1Y = WORLD_HEIGHT - hudVerticalMargin;
        hudRow2Y = hudRow1Y - hudVerticalMargin - font.getCapHeight();
        hudSectionWidth = WORLD_WIDTH / 3;
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float deltaTime) {
        //Asteroids
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Begin the batch operation for efficient rendering of multiple objects at once
        batch.begin();

        // Detect user input and update game state accordingly
        detectInput(deltaTime);

        // Update the state of the player's ship based on the elapsed time
        playerShip.update(deltaTime);

        // Render a scrolling background for the game
        renderBackground(deltaTime);

        CountDownLatch latch = new CountDownLatch(2);

        threadPoolExecutor.execute(() -> {
            // Code to spawn enemy ships
            spawnEnemyShips(deltaTime);
            latch.countDown();
        });

        threadPoolExecutor.execute(() -> {
            // Code to spawn asteroids
            spawnAsteroids(deltaTime);
            latch.countDown();
        });

        try {
            // Wait for all submitted tasks to complete
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Loop through each enemy ship in the list, update its state and render it
        ListIterator<EnemyShip> enemyShipListIterator = enemyShipList.listIterator();
        while (enemyShipListIterator.hasNext()) {
            EnemyShip enemyShip = enemyShipListIterator.next();
            // Move the enemy ship based on its current state and the elapsed time
            moveEnemy((EnemyShip) enemyShip, deltaTime);
            // Update the state of the enemy ship based on the elapsed time
            enemyShip.update(deltaTime);
            // Render the enemy ship using the batch operation
            enemyShip.draw(batch);
        }

        //Asteroids
        ListIterator<Asteroid> asteroid1Iterator = asteroid1List.listIterator();
        while (asteroid1Iterator.hasNext()) {
            Asteroid asteroid = asteroid1Iterator.next();
            asteroid.updatee(deltaTime);
            asteroid.draw(batch);
        }

        // Render the player's ship using the batch operation
        playerShip.draw(batch);

        // Render lasers on the screen based on the elapsed time and player input
        renderLasers(deltaTime);

        // Detect collisions between lasers and ships
        detectCollisions();

        // Update and render explosions on the screen based on the elapsed time
        updateAndRenderExplosions(deltaTime);

        //hud rendering
        updateAndRenderHUD();

        //Game Over

        // End the batch operation
        batch.end();
    }



    private void updateAndRenderHUD() {
        //render top row labels
        font.draw(batch, "Score", hudLeftX, hudRow1Y, hudSectionWidth, Align.left, false);
        font.draw(batch, "Lives", hudRightX, hudRow1Y, hudSectionWidth, Align.right, false);
        //render second row values
        font.draw(batch, String.format(Locale.getDefault(), "%06d", score), hudLeftX, hudRow2Y, hudSectionWidth, Align.left, false);
      //  font.draw(batch, String.format(Locale.getDefault(), "%02d", playerShip.shield), hudCentreX, hudRow2Y, hudSectionWidth, Align.center, false);
        font.draw(batch, String.format(Locale.getDefault(), "%02d", playerShip.getLives()), hudRightX, hudRow2Y, hudSectionWidth, Align.right, false);
    }

    private void spawnEnemyShips(float deltaTime) {
        enemySpawnTimer += deltaTime;
        if (enemySpawnTimer > timeBetweenEnemySpawns) {
            float xCentre = SpaceShooterGame.random.nextFloat() * (WORLD_WIDTH - 10) + 5;
            String[] enemyTypes = {"enemy1", "enemy2", "enemy"};
            String randomEnemyType = enemyTypes[SpaceShooterGame.random.nextInt(enemyTypes.length)];
            enemyShipList.add(new EnemyShip(xCentre, WORLD_HEIGHT , 10, 10, 40, 48, randomEnemyType , enemyLaserTextureRegion));
            enemySpawnTimer -= timeBetweenEnemySpawns;
           System.out.println("Hello, i just finished the spawnEnemyShip method");
        }
    }




    private void spawnAsteroids(float deltaTime) {
        asteroidSpawnTimer += deltaTime;

        if (asteroidSpawnTimer > timeBetweenAsteroidSpawns) {
            // get a random asteroid type
            String[] asteroidTypes = {"rock1", "rock2", "rock3"};
            String randomAsteroidType = asteroidTypes[SpaceShooterGame.random.nextInt(asteroidTypes.length)];

            // get the flyweight asteroid object from the factory
            FlyweightAsteroid asteroid = asteroidFactory.getAsteroid(randomAsteroidType);

            // set the initial position for the asteroid
            Vector2 position = new Vector2(MathUtils.random(-WORLD_WIDTH/2, WORLD_WIDTH/2),
                    MathUtils.random(Gdx.graphics.getHeight() / 2, Gdx.graphics.getHeight()));

            // add the asteroid to the list
            asteroid1List.add(new Asteroid(asteroid.getTextureRegion(),asteroid.getWidth(),asteroid.getHeight()));

            asteroidSpawnTimer -= timeBetweenAsteroidSpawns;
        }
    }


    private void renderLasers(float deltaTime) {
        //create new lasers
        //player lasers
        if (playerShip.canFireLaser()) { //check if the player can fire a laser
            Laser[] lasers = playerShip.fireLasers(); //if yes, fire the laser
            playerLaserList.addAll(Arrays.asList(lasers)); //add the new laser to the player laser list
        }
        //enemy lasers
        ListIterator<EnemyShip> enemyShipListIterator = enemyShipList.listIterator(); //create an iterator for the enemy ship list
        while (enemyShipListIterator.hasNext()) { //iterate through each enemy ship
            EnemyShip enemyShip = enemyShipListIterator.next();
            if (enemyShip.canFireLaser()) { //check if the enemy ship can fire a laser
                Laser[] lasers = enemyShip.fireLasers(); //if yes, fire the laser
                enemyLaserList.addAll(Arrays.asList(lasers)); //add the new laser to the enemy laser list
            }
        }
        //draw lasers
        //remove old lasers
        ListIterator<Laser> iterator = playerLaserList.listIterator(); //create an iterator for the player laser list
        while (iterator.hasNext()) { //iterate through each player laser
            Laser laser = iterator.next();
            laser.draw(batch); //draw the laser on the screen
            laser.boundingBox.y += laser.movementSpeed * deltaTime; //move the laser downwards based on its movement speed and delta time
            if (laser.boundingBox.y > WORLD_HEIGHT) { //check if the laser is off the screen
                iterator.remove(); //if yes, remove it from the player laser list
            }
        }
        iterator = enemyLaserList.listIterator(); //reset the iterator for the enemy laser list
        while (iterator.hasNext()) { //iterate through each enemy laser
            Laser laser = iterator.next();
            laser.draw(batch); //draw the laser on the screen
            laser.boundingBox.y -= laser.movementSpeed * deltaTime; //move the laser upwards based on its movement speed and delta time
            if (laser.boundingBox.y + laser.boundingBox.height < 0) { //check if the laser is off the screen
                iterator.remove(); //if yes, remove it from the enemy laser list
            }
        }
    }


    private void detectInput(float deltaTime) {
        Invoker invoker = new Invoker();
        invoker.addCommand("Left",new MoveLeftCommand((PlayerShip) playerShip, deltaTime));
        invoker.addCommand("Right",new MoveRightCommand((PlayerShip) playerShip, deltaTime));
        invoker.addCommand("Up",new MoveUpCommand((PlayerShip) playerShip, deltaTime));
        invoker.addCommand("Down",new MoveDownCommand((PlayerShip) playerShip, deltaTime));
        invoker.addCommand("MoveToNewPosition",new MoveToPositionCommand((PlayerShip) playerShip, deltaTime,viewport,Gdx.input));


        // keyboard input
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            invoker.invoquer("Left");
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            invoker.invoquer("Right");
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            invoker.invoquer("Up");
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            invoker.invoquer("Down");
        }

        // touch input (also mouse)
        if (Gdx.input.isTouched()) {
            invoker.invoquer("MoveToNewPosition");
        }
        }


    private void moveEnemy(EnemyShip enemyShip, float deltaTime) {
        List<ICommand> commands = new ArrayList<>();
        float xMove = enemyShip.getDirectionVector().x * enemyShip.movementSpeed * deltaTime;
        float yMove = enemyShip.getDirectionVector().y * enemyShip.movementSpeed * deltaTime;
        commands.add(new MoveCommand(enemyShip, xMove, yMove,deltaTime));
        for (ICommand command : commands) {
            command.execute();
        }
    }

    private void detectCollisions() {
        ScoreCounter scoreCounter = new ScoreCounter();
        //for each player laser, check whether it intersects an enemy ship
        ListIterator<Laser> laserListIterator = playerLaserList.listIterator();
        while (laserListIterator.hasNext()) {
            Laser laser = laserListIterator.next();
            ListIterator<EnemyShip> enemyShipListIterator = enemyShipList.listIterator();
            while (enemyShipListIterator.hasNext()) {
                EnemyShip enemyShip = enemyShipListIterator.next();

                if (enemyShip.intersects(laser.boundingBox)) {
                    //contact with enemy ship
                    if (enemyShip.intersects(laser.boundingBox)) {
                        enemyShip.accept(new EnemyShipCollisionVisitor(enemyShipList,explosionList,explosionTexture), laser);


                        enemyShip.accept(scoreCounter); // call accept method

                        score = score+scoreCounter.getScore();
                    }
                    laserListIterator.remove();
                    break;
                }
            }
        }
        ListIterator<Asteroid> asteroidListIterator = asteroid1List.listIterator();
        while (asteroidListIterator.hasNext()) {
            Asteroid asteroid = asteroidListIterator.next();
            laserListIterator = playerLaserList.listIterator();
            while (laserListIterator.hasNext()) {
                Laser laser = laserListIterator.next();
                if (asteroid.collidesWith(laser)) {
                    // Handle collision between asteroid and playerShip's laser
                    asteroid.accept(new AsteroidCollisionVisitor(asteroidListIterator, explosionList, explosionTexture), laser);

                    asteroid.accept(scoreCounter); // call accept method

                    score = score + scoreCounter.getScore();
                    laserListIterator.remove();
                    break;
                }
            }

            if (asteroid.collidesWith((PlayerShip) playerShip)) {
                //Asteroid -- PlayerShip
                if (playerShip.intersects(asteroid.boundingBox)) {
                    //contact with player ship
                    explosionList.add(
                            new Explosion(explosionTexture,
                                    new Rectangle(playerShip.boundingBox),
                                    1.6f));

                }
            }

        }



        //for each enemy laser, check whether it intersects the player ship
        laserListIterator = enemyLaserList.listIterator();
        while (laserListIterator.hasNext()) {
            Laser laser = laserListIterator.next();
            if (playerShip.intersects(laser.boundingBox)) {
                //contact with player ship
                    explosionList.add(
                            new Explosion(explosionTexture,
                                    new Rectangle(playerShip.boundingBox),
                                    1.6f));

                int lives = playerShip.getLives()-1;
                playerShip.setLives(lives);
                if(playerShip.getLives()==0){
                    ((Game)Gdx.app.getApplicationListener()).setScreen(new GameOverScreen());
                }
                    laserListIterator.remove();


            }



        }
      // laserListIterator = playerLaserList.listIterator();
        //for each player laser, check whether it intersects an asteroid
        // for each asteroid, check whether it intersects a player laser


    }

    private void updateAndRenderExplosions(float deltaTime) {
        ListIterator<Explosion> explosionListIterator = explosionList.listIterator();
        while (explosionListIterator.hasNext()) {
            Explosion explosion = explosionListIterator.next();
            explosion.update(deltaTime);
            if (explosion.isFinished()) {
                explosionListIterator.remove();
            } else {
                explosion.draw(batch);
            }
        }
    }

    private void renderBackground(float deltaTime) {

        //update position of background images
        backgroundOffsets[0] += deltaTime * backgroundMaxScrollingSpeed / 8;
        backgroundOffsets[1] += deltaTime * backgroundMaxScrollingSpeed / 4;
        backgroundOffsets[2] += deltaTime * backgroundMaxScrollingSpeed / 2;
        backgroundOffsets[3] += deltaTime * backgroundMaxScrollingSpeed;

        //draw each background layer
        for (int layer = 0; layer < backgroundOffsets.length; layer++) {
            if (backgroundOffsets[layer] > WORLD_HEIGHT) {
            //    Gdx.app.log("BJG","Reset layer "+ layer);
                backgroundOffsets[layer] = 0;
            }
            batch.draw(backgrounds[layer],
                    0,
                    -backgroundOffsets[layer],
                    WORLD_WIDTH, WORLD_HEIGHT);
            batch.draw(backgrounds[layer],
                    0,
                    -backgroundOffsets[layer]+WORLD_HEIGHT,
                    WORLD_WIDTH, WORLD_HEIGHT);

        }
    }



    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        batch.setProjectionMatrix(camera.combined);

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
    public void dispose() {
        batch.dispose();
    }



}
