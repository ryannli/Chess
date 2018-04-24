package viewer;

import controller.Chess;
import org.junit.Test;

import javax.swing.*;

import java.awt.*;
import java.awt.event.InputEvent;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class ChessGUITest {

    @Test
    public void TestGUI() throws IOException, AWTException{
        Chess chess = new Chess();
        chess.initialize();
    }
}
