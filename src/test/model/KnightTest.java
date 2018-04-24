package model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static model.BasePieceTest.getResultList;
import static org.junit.Assert.assertEquals;

public class KnightTest {

    private Board board;

    @org.junit.Before
    public void setUp() throws Exception {
        board = new Board(8, 8);
    }

    @Test
    public void TestIsValidMovePassed() {
        BasePiece.Position knightPosition = new BasePiece.Position(4, 4);
        BasePiece knight = new Knight(knightPosition, true);
        board.setPiece(knight);

        assertEquals(knight.isValidMove(board.getChessBoard(), new BasePiece.Position(2, 3)), true);
    }

    @Test
    public void TestIsValidMoveFailIllegal() {
        BasePiece.Position knightPosition = new BasePiece.Position(3, 3);
        BasePiece.Position targetPosition = new BasePiece.Position(1, 1);
        BasePiece knight = new Knight(knightPosition, true);
        board.setPiece(knight);

        assertEquals(knight.isValidMove(board.getChessBoard(), targetPosition), false);
    }

    @Test
    public void TestGetPossibleLegalMoves() {
        BasePiece.Position knightPosition = new BasePiece.Position(2, 1);
        BasePiece knight = new Knight(knightPosition, true);
        board.setPiece(knight);

        List<String> expect = new ArrayList<>();
        expect.add(4 + " " + 0);
        expect.add(4 + " " + 2);
        expect.add(3 + " " + 3);
        expect.add(1 + " " + 3);
        expect.add(0 + " " + 0);
        expect.add(0 + " " + 2);

        List<String> resultsList = getResultList(knight, board);
        assertTrue(expect.containsAll(resultsList)
                && expect.size() == resultsList.size());
    }
}