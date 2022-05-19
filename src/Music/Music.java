package Music;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Music implements Runnable{
    Player player;
    String music;
    Thread thread = null;
    public Music(String file)throws FileNotFoundException, JavaLayerException {
        this.music = file;
        try {
            BufferedInputStream buffer = new BufferedInputStream(new FileInputStream(music));
            player = new Player(buffer);
        } catch (FileNotFoundException | JavaLayerException e) {
            e.printStackTrace();
        }

//        Player C=new Player()

    }

    @Override
    public void run() {
        try {
            play();
        } catch (FileNotFoundException | JavaLayerException e) {
            e.printStackTrace();
        }
    }
    public void play() throws FileNotFoundException, JavaLayerException {
//        BufferedInputStream buffer = new BufferedInputStream(new FileInputStream(music));
//        player = new Player(buffer);
        player.play();
    }
    public void stop(){
        player.close();
    }
}





