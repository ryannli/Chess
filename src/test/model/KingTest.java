package model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static model.BasePieceTest.getResultList;
import static org.junit.Assert.assertEquals;

public class KingTest {

    private Board board;

    @org.junit.Before
    public void setUp() throws Exception {
        board = new Board(8, 8);
    }

    @Test
    public void TestIsValidMovePassed() {
        BasePiece.Position kingPosition = new BasePiece.Position(4, 4);
        BasePiece king = new King(kingPosition, true);
        board.setPiece(king);

        assertEquals(king.isValidMove(board.getChessBoard(), new BasePiece.Position(3, 4)), true);
    }

    @Test
    public void TestIsValidMoveFailIllegal() {
        BasePiece.Position kingPosition = new BasePiece.Position(3, 3);
        BasePiece.Position targetPosition = new BasePiece.Position(1, 2);
        BasePiece king = new King(kingPosition, true);
        board.setPiece(king);

        assertEquals(king.isValidMove(board.getChessBoard(), targetPosition), false);
    }

    @Test
    public void TestGetPossibleLegalMoves() {
        BasePiece.Position kingPosition = new BasePiece.Position(2, 1);
        BasePiece.Position queenPosition = new BasePiece.Position(3, 2);
        BasePiece king = new King(kingPosition, true);
        BasePiece queen = new Queen(queenPosition, true);
        board.setPiece(king);
        board.setPiece(queen);

        List<String> expect = new ArrayList<>();
        expect.add(3 + " " + 1);
        expect.add(1 + " " + 0);
        expect.add(1 + " " + 1);
        expect.add(2 + " " + 2);
        expect.add(2 + " " + 0);
        expect.add(1 + " " + 2);
        expect.add(3 + " " + 0);

        List<String> resultsList = getResultList(king, board);
        assertTrue(expect.containsAll(resultsList)
                && expect.size() == resultsList.size());
    }
}