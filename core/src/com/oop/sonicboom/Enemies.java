package com.oop.sonicboom;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class Enemies implements Disposable {

	private GameScreen game;

	private Array<Enemy> enemies;

	public Enemies(GameScreen game) {
		this.game = game;

		createEnemies();
	}

	private void createEnemies() {
		enemies = new Array<Enemy>();

		try {
			for (MapObject object : game.getMap().getLayers().get("Enemies").getObjects()) {
				if (object instanceof TextureMapObject && object.getName().equals("sampleEnemy")) {
					enemies.add(new SampleEnemy(game, object));
				}
				if (object instanceof TextureMapObject && object.getName().equals("enemyFly")) {
					enemies.add(new EnemyFly(game, object));
				}
				if (object instanceof TextureMapObject && object.getName().equals("sheepEnemy")) {
					enemies.add(new SheepEnemy(game, object));
				}
			}
		} catch (Exception e) {
			System.out.println("load Enemy failed.");
		}
	}

	public void update(float delta) {
		for (Enemy enemy : enemies) {
			enemy.update(delta);

			if (enemy.destroyed) {
				enemies.removeValue(enemy, true);
			}
		}
	}

	public void draw(Batch batch) {
		for (Enemy enemy : enemies) {
			enemy.draw(batch);
		}
	}

	@Override
	public void dispose() {
		for (Enemy enemy : enemies) {
			enemy.dispose();
		}

	}

}
