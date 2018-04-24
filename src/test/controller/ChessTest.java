package controller;

import model.*;
import org.junit.Assert;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.io.IOException;

import static junit.framework.TestCase.assertEquals;

public class ChessTest {

    @Test
    public void TestInitialize () throws IOException, AWTException{
        Chess chess = new Chess();
        chess.initialize();
        JFrame chessFrame = chess.getChessGUI().getChessFrame();
        Robot bot = new Robot();
        bot.mouseMove(100,500);
        bot.mousePress(InputEvent.BUTTON1_MASK);
        bot.mouseRelease(InputEvent.BUTTON1_MASK);
        try{Thread.sleep(250);}catch(InterruptedException e){}
        bot.mousePress(InputEvent.BUTTON1_MASK);
        bot.mouseRelease(InputEvent.BUTTON1_MASK);

        try{Thread.sleep(250);}catch(InterruptedException e){}
        bot.mousePress(InputEvent.BUTTON1_MASK);
        bot.mouseRelease(InputEvent.BUTTON1_MASK);
        try{Thread.sleep(250);}catch(InterruptedException e){}
        bot.mouseMove(150,450);
        bot.mousePress(InputEvent.BUTTON1_MASK);
        bot.mouseRelease(InputEvent.BUTTON1_MASK);
        try{Thread.sleep(250);}catch(InterruptedException e){}
        bot.mouseMove(100,450);
        bot.mousePress(InputEvent.BUTTON1_MASK);
        bot.mouseRelease(InputEvent.BUTTON1_MASK);

        try{Thread.sleep(250);}catch(InterruptedException e){}
        bot.mouseMove(100,400);
        bot.mousePress(InputEvent.BUTTON1_MASK);
        bot.mouseRelease(InputEvent.BUTTON1_MASK);
        try{Thread.sleep(250);}catch(InterruptedException e){}
        bot.mouseMove(100,350);
        bot.mousePress(InputEvent.BUTTON1_MASK);
        bot.mouseRelease(InputEvent.BUTTON1_MASK);

        // Press undo button
        try{Thread.sleep(250);}catch(InterruptedException e){}
        bot.mouseMove(600,100);
        bot.mousePress(InputEvent.BUTTON1_MASK);
        bot.mouseRelease(InputEvent.BUTTON1_MASK);
        try{Thread.sleep(250);}catch(InterruptedException e){}
        bot.mousePress(InputEvent.BUTTON1_MASK);
        bot.mouseRelease(InputEvent.BUTTON1_MASK);
        try{Thread.sleep(250);}catch(InterruptedException e){}

        // Press restart button
        try{Thread.sleep(250);}catch(InterruptedException e){}
        bot.mouseMove(600,60);
        bot.mousePress(InputEvent.BUTTON1_MASK);
        bot.mouseRelease(InputEvent.BUTTON1_MASK);
        try{Thread.sleep(250);}catch(InterruptedException e){}

        // Press white forfeit
        try{Thread.sleep(250);}catch(InterruptedException e){}
        bot.mouseMove(580,120);
        bot.mousePress(InputEvent.BUTTON1_MASK);
        bot.mouseRelease(InputEvent.BUTTON1_MASK);
        try{Thread.sleep(250);}catch(InterruptedException e){}

        // Press black forfeit
        try{Thread.sleep(250);}catch(InterruptedException e){}
        bot.mouseMove(630,120);
        bot.mousePress(InputEvent.BUTTON1_MASK);
        bot.mouseRelease(InputEvent.BUTTON1_MASK);
        try{Thread.sleep(250);}catch(InterruptedException e){}

        Assert.assertEquals(chessFrame.getComponents().length, 1);
        Board board = chess.getBoard();

        assertEquals(board.getPiece(new BasePiece.Position(1, 1)).getClass()
                , Pawn.class);
        assertEquals(board.getPiece(new BasePiece.Position(0, 1)).getClass()
                , Knight.class);
    }

    @Test
    public void TestMain () throws IOException{
        String[] args = null;
        Chess chess = new Chess();
        chess.main(args);
    }

    @Test
    public void TestCheckAndInformGameEnd () throws IOException{
        Chess chess = new Chess();
        chess.initialize();

        // Checkmate
        Board board = new Board(8 ,8);
        BasePiece.Position queenPosition = new BasePiece.Position(2, 2);
        BasePiece.Position rookPosition1 = new BasePiece.Position(2, 0);
        BasePiece.Position rookPosition2 = new BasePiece.Position(0, 2);
        BasePiece.Position kingPosition = new BasePiece.Position(0, 0);
        BasePiece queen = new Queen(queenPosition, true);
        BasePiece rook1 = new Rook(rookPosition1, true);
        BasePiece rook2 = new Rook(rookPosition2, true);
        BasePiece king = new King(kingPosition, false);
        board.setPiece(queen);
        board.setPiece(rook1);
        board.setPiece(rook2);
        board.setPiece(king);

        chess.setBoard(board);
        chess.checkAndInformGameEnd(rook1);

        // Stalemate
        board = new Board(8 ,8);
        rookPosition1 = new BasePiece.Position(2, 1);
        rookPosition2 = new BasePiece.Position(1, 2);
        kingPosition = new BasePiece.Position(0, 0);
        rook1 = new Rook(rookPosition1, true);
        rook2 = new Rook(rookPosition2, true);
        king = new King(kingPosition, false);
        board.setPiece(rook1);
        board.setPiece(rook2);
        board.setPiece(king);

        chess.setBoard(board);
        chess.checkAndInformGameEnd(rook2);

        // Check
        board = new Board(8 ,8);
        rookPosition1 = new BasePiece.Position(2, 0);
        rook1 = new Rook(rookPosition1, true);
        king = new King(kingPosition, false);
        board.setPiece(rook1);
        board.setPiece(king);

        chess.setBoard(board);
        chess.checkAndInformGameEnd(rook1);
    }
}
