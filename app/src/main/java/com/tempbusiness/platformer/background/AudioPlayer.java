package com.tempbusiness.platformer.background;

import android.content.Context;
import android.media.MediaPlayer;

import com.tempbusiness.platformer.load.FileLoader;
import com.tempbusiness.platformer.util.Util;

import java.util.ArrayList;

public class AudioPlayer {
    public GameHandler game;
    public ArrayList<MusicClip> musicClips = new ArrayList<>();

    public AudioPlayer(GameHandler game) {
        this.game = game;
    }

    public void tick() {
        MusicClip[] clipArray = new MusicClip[musicClips.size()];
        musicClips.toArray(clipArray);
        for (MusicClip a : clipArray) {
            if (game.game.gameLoop.running) a.tick();
        }
    }

    public void play(FileLoader.Sound id, Context c) {
        game.game.soundPool.load(c, id.name, 1);
    }
    public void playMusic(FileLoader.Sound id) {
        musicClips.add(new MusicClip(id));
    }
    public void playMusic(FileLoader.Sound id, int timePassed, boolean bound) {
        musicClips.add(new MusicClip(id, timePassed, bound));
    }
    public class MusicClip {
        public int timePassed;
        public FileLoader.Sound id;
        public float volume = 1.0f;
        public MediaPlayer player;
        public boolean playing = false, bound = false;

        public MusicClip(FileLoader.Sound id) {
            this.id = id;
        }
        public MusicClip(FileLoader.Sound id, int timePassed, boolean bound) {
            this(id);
            this.timePassed = timePassed;
            this.bound = bound;
        }
        public void start(Context c) {
            player = MediaPlayer.create(c, id.name);
            player.setVolume(volume, volume);
            player.seekTo(timePassed);
            player.start();
            playing = true;
        }
        public void stop(boolean pause) {
            playing = false;
            player.release();
            if (!pause) musicClips.remove(this);
        }
        public void tick() {
            if (!playing) {
//                start();
            }
            timePassed = player.getCurrentPosition();

            if (timePassed == player.getDuration()) {
                stop(false);
            }
        }
    }
}
