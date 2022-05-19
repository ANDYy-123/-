import Music.Music;
import javazoom.jl.decoder.JavaLayerException;
import view.ChessGameFrame;
import view.Menu;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, JavaLayerException {
        String filepath = "./music/Supercell Games - Battle 01.mp3";
        Music musicObject = new Music(filepath);
        SwingUtilities.invokeLater(() -> {
            Menu mainFrame = new Menu(641, 834,musicObject);
            mainFrame.setVisible(true);
        });

        musicObject.play();



    }
}
