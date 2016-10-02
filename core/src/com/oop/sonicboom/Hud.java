package com.oop.sonicboom;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Hud implements Disposable {

	// batch for draw
	private SpriteBatch batch;

	// Scene2D.ui Stage and its own Viewport for HUD
	public Stage stage;
	public Viewport viewport;

	// Scene2D widgets
	private Label testText;

	public Hud() {
		batch = new SpriteBatch();

		// setup the HUD viewport using a new camera seperate from our gamecam
		// define our stage using that viewport and our games spritebatch
		viewport = new FitViewport(SonicBoom.V_WIDTH, SonicBoom.V_HEIGHT, new OrthographicCamera());
		stage = new Stage(viewport, batch);

		// define a table used to organize our hud's labels
		Table table = new Table();
		// Top-Align table
		table.top();
		// make the table fill the entire stage
		table.setFillParent(true);
		
		//define our labels using the String, and a Label style consisting of a font and color
        testText = new Label("Hello Sonic Boom!, press arrow to explore", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

		// add our labels to our table, padding the top, and giving them all
		// equal width with expandX
		table.add(testText).expandX().padTop(10);
		
		//add our table to the stage
        stage.addActor(table);
	}

	public void render() {
		stage.draw();
	}

	@Override
	public void dispose() {
		stage.dispose();
	}

}
