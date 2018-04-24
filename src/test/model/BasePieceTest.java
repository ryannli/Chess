package model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static model.BasePiece.Position;


import static org.junit.Assert.*;

public class BasePieceTest {

    public static List<String> getResultList(BasePiece piece, Board board) {
        List<String> resultsList = new ArrayList<>();
        HashSet<Position> results = piece.getPossibleLegalMoves(board.getChessBoard());
        for (Position pos : results) {
            resultsList.add(pos.getRank() + " " + pos.getFile());
        }
        return resultsList;
    }

    private Board board;

    @org.junit.Before
    public void setUp() throws Exception {
        board = new Board(8, 8);
    }

    @Test
    public void testPosition() {
        Position position = new Position(1, 3);
        assertEquals(1, position.getRank());
        assertEquals(3, position.getFile());
    }

    @Test
    public void testIsValidMoveBasicCheckSamePosition() {
        Position position = new Position(3, 3);
        BasePiece king = new King(position, true);
        board.setPiece(king);

        assertEquals(king.isValidMoveBasicCheck(board.getChessBoard(), position), false);
    }

    @Test
    public void testIsSamePlayerNull() {
        Position position = new Position(3, 3);
        BasePiece king = new King(position, true);

        assertEquals(king.isSamePlayer(null), false);
    }

    @Test
    public void testIsValidMoveBasicCheckSamePlayer() {
        Position queenPosition = new Position(3, 3);
        Position kingPosition = new Position(1, 3);
        BasePiece queen = new Queen(queenPosition, true);
        BasePiece king = new King(kingPosition, true);
        board.setPiece(queen);
        board.setPiece(king);

        assertEquals(king.isValidMoveBasicCheck(board.getChessBoard(), queenPosition), false);
    }

    @Test
    public void testIsValidMoveBasicPassed() {
        Position queenPosition = new Position(3, 3);
        BasePiece queen = new Queen(queenPosition, true);
        board.setPiece(queen);

        assertEquals(queen.isValidMoveBasicCheck(board.getChessBoard(), new Position(3, 0)), true);
    }
}