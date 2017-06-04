package com.platformer.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.platformer.game.Screens.PlayScreen;
import com.platformer.game.platformerGame;

/**
 * Created by User on 6/4/2017.
 */

public class DownSpike extends MoveSpike {
    public DownSpike(PlayScreen screen, float x, float y) {
        super(screen, x, y);
//        Image image = new Image(new Texture("sprites cancer\\spike down.png"));
        setBounds(getX(),getY(),64/platformerGame.PPM,64/platformerGame.PPM);
    }

    public void update(float dt){
        setPosition(b2body.getPosition().x-getWidth()/2,b2body.getPosition().y-getHeight()/2);
    }

    @Override
    protected void defineMoveSpike() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(128/ platformerGame.PPM,31164/platformerGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body= world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(30/platformerGame.PPM);

        fdef.filter.categoryBits = platformerGame.MOVESPIKE_BIT;
        fdef.filter.maskBits = platformerGame.GROUND_BIT | platformerGame.PLAYER_BIT ;

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }
}
