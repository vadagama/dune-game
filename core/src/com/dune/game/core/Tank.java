package com.dune.game.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Tank {
    private Vector2 position;
    private Vector2 tmp;
    private TextureRegion[] textures;
    private TextureRegion projectileTexture;
    private float angle;
    private float speed;

    private float moveTimer;
    private float timePerFrame;

    private Projectile projectile;

    public Vector2 getPosition() {
        return position;
    }

    public Projectile getProjectile() {
        return projectile;
    }

    public Tank(TextureAtlas atlas, float x, float y) {
        super();
        this.position = new Vector2(x, y);
        this.tmp = new Vector2(0, 0);
        this.textures = new TextureRegion(atlas.findRegion("tankanim")).split(64, 64)[0];
        this.projectile = new Projectile(atlas, false);
        this.speed = 140.0f;
        this.timePerFrame = 0.08f;
    }

    private int getCurrentFrameIndex() {
        return (int) (moveTimer / timePerFrame) % textures.length;
    }

    public void update(float dt) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            angle += 180.0f * dt;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            angle -= 180.0f * dt;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            position.add(speed * MathUtils.cosDeg(angle) * dt, speed * MathUtils.sinDeg(angle) * dt);
            moveTimer += dt;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            if(!projectile.isFire()) {
                makeFire();
            }
        }
        else {
            if (getCurrentFrameIndex() != 0) {
                moveTimer += dt;
            }
        }
        checkBounds();
    }

    public void makeFire() {
        //tmp.set(1,0).rotate(angle).scl(27).add(position.x, position.y - 10);
        projectile.setPosition(tmp);
        projectile.fire(angle);
        projectile.setFire(true);
        System.out.println("fire");
    }


    public void checkBounds() {
        if (position.x < 40) {
            position.x = 40;
        }
        if (position.y < 40) {
            position.y = 40;
        }
        if (position.x > 1240) {
            position.x = 1240;
        }
        if (position.y > 680) {
            position.y = 680;
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(textures[getCurrentFrameIndex()], position.x - 40, position.y - 40, 40, 40, 80, 80, 1, 1, angle);
    }
}
