package com.peo.core.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.peo.core.actors.Background;
import com.peo.core.actors.MenuImage;
import com.peo.core.actors.Title;
import com.peo.core.managers.MainMenuStateEnum;
import com.peo.core.managers.ScreenEnum;
import com.peo.core.managers.ScreenManager;
import com.peo.utils.GamePreferences;

public class MainMenuScreen extends AbstractScreen
{
    private Stage menuStage;
    private Stage helpStage;
    private Stage creditsStage;
    private Skin skin;
    private Title title;
    private Music levelMusic;
    private GamePreferences gamePreferences;
    private MainMenuStateEnum menuState;

    public MainMenuScreen (final Game game )
    {
        super ( game );

        menuStage = new Stage ();
        helpStage = new Stage ();
        creditsStage = new Stage ();
        skin = new Skin ();
        title = new Title ();
        menuState = MainMenuStateEnum.MENU;

        gamePreferences = new GamePreferences ();

        Pixmap pixmap = new Pixmap ( 100, 50, Pixmap.Format.RGBA8888 );
        pixmap.setColor ( Color.BLUE );
        pixmap.fill ();

        skin.add ( "blue", new Texture ( pixmap ) );

        BitmapFont font = new BitmapFont ();
        font.getData ().setScale ( 1.5f );

        skin.add ( "default", font );

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle ();
        textButtonStyle.up = skin.newDrawable ( "blue", Color.BLUE );
        textButtonStyle.down = skin.newDrawable ( "blue", Color.BLUE );
        textButtonStyle.checked = skin.newDrawable ( "blue", Color.CYAN );
        textButtonStyle.over = skin.newDrawable ( "blue", Color.LIGHT_GRAY );
        textButtonStyle.font = skin.getFont ( "default" );

        skin.add ( "default", textButtonStyle );

        final TextButton playButton = new TextButton ( "PLAY", textButtonStyle );
        final TextButton exitButton = new TextButton ( "EXIT", textButtonStyle );
        final TextButton helpButton = new TextButton ( "HELP", textButtonStyle );
        final TextButton creditButton = new TextButton ( "CREDIT", textButtonStyle );
        final TextButton returnButton = new TextButton ( "BACK", textButtonStyle );
        playButton.setPosition (
            ( gamePreferences.getWidthResolution () / 2 ) - 50,
            gamePreferences.getHeightResolution() / 2
        );
        exitButton.setPosition (
            ( gamePreferences.getWidthResolution () / 2 ) - 50,
            ( gamePreferences.getHeightResolution() / 2 ) - 210
        );
        helpButton.setPosition (
            ( gamePreferences.getWidthResolution () / 2 ) - 50,
            ( gamePreferences.getHeightResolution() / 2 ) - 70
        );
        creditButton.setPosition (
            ( gamePreferences.getWidthResolution () / 2 ) - 50,
            ( gamePreferences.getHeightResolution() / 2 ) - 140
        );
        returnButton.setPosition ( ( gamePreferences.getWidthResolution () / 2 ) - 50, 100 );

        playButton.addListener( new ChangeListener () {
            @Override public void changed ( ChangeEvent event, Actor actor ) {
            ScreenManager.getInstance ().show ( ScreenEnum.GAME_SCREEN, game );
            levelMusic.stop ();
            levelMusic.dispose ();
            }
        });

        returnButton.addListener( new ChangeListener () {
            @Override public void changed ( ChangeEvent event, Actor actor ) {
                menuState = MainMenuStateEnum.MENU;
            }
        });

        creditButton.addListener( new ChangeListener () {
            @Override public void changed ( ChangeEvent event, Actor actor ) {
                menuState = MainMenuStateEnum.CREDITS;
            }
        });

        helpButton.addListener( new ChangeListener () {
            @Override public void changed ( ChangeEvent event, Actor actor ) {
                menuState = MainMenuStateEnum.HELP;
            }
        });

        exitButton.addListener( new ChangeListener () {
            @Override public void changed ( ChangeEvent event, Actor actor ) {
                Gdx.app.exit ();
            }
        });

        menuStage.addActor ( new Background () );
        menuStage.addActor ( title );
        menuStage.addActor ( playButton );
        menuStage.addActor ( helpButton );
        menuStage.addActor ( creditButton );
        menuStage.addActor ( exitButton );

        helpStage.addActor ( new Background () );
        helpStage.addActor ( new MenuImage ( "img/main-menu/assets/instructions.png" ) );
        helpStage.addActor ( returnButton );

        creditsStage.addActor ( new Background () );
        creditsStage.addActor ( new MenuImage ( "img/main-menu/assets/credits.png" ) );
        creditsStage.addActor ( returnButton );

        levelMusic = Gdx.audio.newMusic ( Gdx.files.internal ( "audio/bg-music-3.mp3" ) );
        levelMusic.setLooping ( true );
        levelMusic.setVolume ( 0.5f );
        levelMusic.play ();
    }

    @Override public void render ( float delta )
    {
        Gdx.gl.glClearColor (0.2f, 0.2f, 0.2f, 1 );
        Gdx.gl.glClear ( GL20.GL_COLOR_BUFFER_BIT );

        if ( menuState == MainMenuStateEnum.MENU ) {
            Gdx.input.setInputProcessor ( menuStage );

            menuStage.act ();
            menuStage.draw ();
        } else if ( menuState == MainMenuStateEnum.CREDITS ) {
            Gdx.input.setInputProcessor ( creditsStage );

            creditsStage.act ();
            creditsStage.draw ();
        } else if ( menuState == MainMenuStateEnum.HELP ) {
            Gdx.input.setInputProcessor ( helpStage );

            helpStage.act ();
            helpStage.draw ();
        }
    }
}
