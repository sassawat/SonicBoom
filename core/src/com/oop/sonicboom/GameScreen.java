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

public class GameScreen implements Screen {

	// Reference to our Game, used to set Screens
	final SonicBoom game;

	// basic GameScreen variables
	private OrthographicCamera gameCam;
	private Viewport gamePort;

	// Tiled map variables
	private TmxMapLoader maploader;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;

	// Box2d variables
	private World world;
	private Box2DDebugRenderer b2dr;
	
	// Background
	private Texture bg;
	
	// Hud
	private Hud hud;

	public GameScreen(SonicBoom game) {
		this.game = game;

		// create cam used to follow player through cam world
		gameCam = new OrthographicCamera();

		// create a FitViewport to maintain virtual aspect ratio despite screen
		// size
		gamePort = new StretchViewport(SonicBoom.V_WIDTH, SonicBoom.V_HEIGHT, gameCam);

		// Load map and setup our map renderer
		maploader = new TmxMapLoader();
		map = maploader.load("Maps/testMap/testMap.tmx");
		renderer = new OrthogonalTiledMapRenderer(map);
		gameCam.position.set(gamePort.getWorldWidth() / 2, 850, 0);

		// create Box2D world, setting no gravity in X, -10 gravity in Y, and
		// allow bodies to sleep
		world = new World(new Vector2(0, -10), true);

		// allows for debug lines of box2d world.
		b2dr = new Box2DDebugRenderer();
		
		// create hud
		hud = new Hud();
		
		// Backgound
		bg = new Texture("Maps/testMap/bg.png");
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
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			gameCam.position.x -= 500 * delta;
		}
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			gameCam.position.x += 500 * delta;
		}
		if (Gdx.input.isKeyPressed(Keys.UP)) {
			gameCam.position.y += 500 * delta;
		}
		if (Gdx.input.isKeyPressed(Keys.DOWN)) {
			gameCam.position.y -= 500 * delta;
		}
		if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			game.setScreen(new MenuScreen(game));
		}
	}
	
	public void update(float delta){
		world.step(1 / 60f, 6, 2);

		gameCam.update();
		renderer.setView(gameCam);
	}
	
	public void renderWorld(float delta){
		
		game.batch.begin();
		game.batch.draw(bg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		game.batch.end();
		
		renderer.render();
		b2dr.render(world, gameCam.combined);
		hud.render();
	}

}
