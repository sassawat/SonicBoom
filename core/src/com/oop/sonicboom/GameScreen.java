package com.oop.sonicboom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import net.dermetfan.gdx.physics.box2d.Box2DMapObjectParser;

public class GameScreen implements Screen {

	// Reference to our Game, used to set Screens
	final SonicBoom game;

	// basic GameScreen variables
	private OrthographicCamera gameCam;
	private Viewport gamePort;

	// Tiled map variables
	private TmxMapLoader maploader;
	public TiledMap map;
	private OrthogonalTiledMapRenderer renderer;

	// Box2d variables
	private World world;
	private Box2DDebugRenderer b2dr;

	// Hud
	private Hud hud;

	// Background
	private Texture bg;
	private OrthographicCamera bgCam; // for static bg
	
	// Box2d parser variables
	public Box2DMapObjectParser parser;
	
	// Player
	Player player;

	public GameScreen(SonicBoom game) {
		this.game = game;

		// create cam used to follow player through cam world
		gameCam = new OrthographicCamera();

		// create a FitViewport to maintain virtual aspect ratio despite screen
		// size
		gamePort = new StretchViewport(SonicBoom.V_WIDTH / SonicBoom.PPM, SonicBoom.V_HEIGHT / SonicBoom.PPM, gameCam);

		// Load map and setup our map renderer
		maploader = new TmxMapLoader();
		map = maploader.load("Maps/testMap/testMap.tmx");
		renderer = new OrthogonalTiledMapRenderer(map, 1 / SonicBoom.PPM);
		gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

		// create Box2D world, setting no gravity in X, -10 gravity in Y, and
		// allow bodies to sleep
		world = new World(new Vector2(0, -9.81f), true);

		// allows for debug lines of box2d world.
		b2dr = new Box2DDebugRenderer();

		// create hud
		hud = new Hud();

		// create background
		bg = new Texture("Maps/testMap/bg.png");
		bgCam = new OrthographicCamera(SonicBoom.V_WIDTH, SonicBoom.V_HEIGHT);
		bgCam.position.set(SonicBoom.V_WIDTH / 2, SonicBoom.V_HEIGHT / 2, 0);
		
		// parse box2d object from map
		parser = new Box2DMapObjectParser(1 / SonicBoom.PPM);
		parser.load(world, map);
		
		// create Sonic!
		player = new Sonic(world, this);

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		handleInput(delta);
		update(delta);
		renderWorld(delta);
	}

	@Override
	public void resize(int width, int height) {
		gamePort.update(width, height);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	public void handleInput(float delta) {
		
		// Control Player
		if (Gdx.input.isKeyJustPressed(Keys.UP)) {
			player.body.applyLinearImpulse(new Vector2(0, 8f), player.body.getWorldCenter(), true);
		}
		if (Gdx.input.isKeyPressed(Keys.RIGHT) && player.body.getLinearVelocity().x <= 5) {
			player.body.applyLinearImpulse(new Vector2(0.02f, 0), player.body.getWorldCenter(), true);
			player.body.applyTorque(-1, true);
		}
		if (Gdx.input.isKeyPressed(Keys.LEFT) & player.body.getLinearVelocity().x >= -5) {
			player.body.applyLinearImpulse(new Vector2(-0.02f, 0), player.body.getWorldCenter(), true);
			player.body.applyTorque(1, true);
		}

		if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			game.setScreen(new MenuScreen(game));
		}
	}

	public void update(float delta) {
		world.step(1 / 60f, 8, 3);

		gameCam.position.x = player.body.getWorldCenter().x;
		gameCam.position.y = player.body.getWorldCenter().y;
		
		gameCam.update();
		bgCam.update();
		
		renderer.setView(gameCam);
	}

	public void renderWorld(float delta) {

		game.batch.setProjectionMatrix(bgCam.combined);
		game.batch.begin();
		game.batch.draw(bg, 0, 0, SonicBoom.V_WIDTH, SonicBoom.V_HEIGHT);
		game.batch.end();

		renderer.render();
		b2dr.render(world, gameCam.combined);
		hud.render();
	}

}
