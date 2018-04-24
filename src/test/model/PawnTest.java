package model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static model.BasePieceTest.getResultList;
import static org.junit.Assert.assertEquals;

public class PawnTest {

    private Board board;

    @org.junit.Before
    public void setUp() throws Exception {
        board = new Board(8, 8);
    }

    @Test
    public void TestIsValidMovePassed() {
        BasePiece.Position pawnPosition = new BasePiece.Position(3, 4);
        BasePiece pawn = new Pawn(pawnPosition, true);
        board.setPiece(pawn);

        assertEquals(pawn.isValidMove(board.getChessBoard(), new BasePiece.Position(4, 4)), true);
    }

    @Test
    public void TestIsValidMoveFailIllegal() {
        BasePiece.Position pawnPosition = new BasePiece.Position(3, 3);
        BasePiece.Position targetPosition = new BasePiece.Position(2, 3);
        BasePiece pawn = new Pawn(pawnPosition, true);
        board.setPiece(pawn);

        assertEquals(pawn.isValidMove(board.getChessBoard(), targetPosition), false);

        targetPosition = new BasePiece.Position(4, 5);

        assertEquals(pawn.isValidMove(board.getChessBoard(), targetPosition), false);
    }

    @Test
    public void TestGetPossibleLegalMoves() {
        BasePiece.Position pawnPosition = new BasePiece.Position(3, 3);
        BasePiece pawn = new Pawn(pawnPosition, true);
        board.setPiece(pawn);

        List<String> expect = new ArrayList<>();
        expect.add(4 + " " + 3);
        expect.add(5 + " " + 3);

        List<String> resultsList = getResultList(pawn, board);
        assertTrue(expect.containsAll(resultsList)
                && expect.size() == resultsList.size());
    }
}