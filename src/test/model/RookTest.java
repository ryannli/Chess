package model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static model.BasePiece.Position;
import static model.BasePieceTest.getResultList;
import static org.junit.Assert.assertEquals;

public class RookTest {

    private Board board;

    @org.junit.Before
    public void setUp() throws Exception {
        board = new Board(8, 8);
    }

    @Test
    public void TestIsValidMovePass() {
        Position rookPosition = new Position(4, 4);
        BasePiece rook = new Rook(rookPosition, true);
        board.setPiece(rook);

        assertEquals(rook.isValidMove(board.getChessBoard(), new BasePiece.Position(1, 4)), true);
    }

    @Test
    public void TestIsValidMoveFailBlocked() {
        Position rookPosition = new Position(5, 5);
        Position blockPosition = new Position(3, 5);
        Position targetPosition = new Position(1, 5);
        BasePiece rook = new Rook(rookPosition, true);
        BasePiece queen = new Queen(blockPosition, false);
        board.setPiece(rook);
        board.setPiece(queen);

        assertEquals(rook.isValidMove(board.getChessBoard(), targetPosition), false);
    }

    @Test
    public void TestIsValidMoveFailIllegal() {
        Position rookPosition = new Position(3, 3);
        Position targetPosition = new Position(1, 2);
        BasePiece rook = new Rook(rookPosition, true);
        board.setPiece(rook);

        assertEquals(rook.isValidMove(board.getChessBoard(), targetPosition), false);
    }

    @Test
    public void TestGetPossibleLegalMoves() {
        Position rookPosition = new Position(2, 2);
        Position queenPosition = new Position(4, 2);
        Position kingPosition = new Position(2, 4);
        BasePiece rook = new Rook(rookPosition, true);
        BasePiece queen = new Queen(queenPosition, true);
        BasePiece king = new King(kingPosition, true);
        board.setPiece(rook);
        board.setPiece(queen);
        board.setPiece(king);

        List<String> expect = new ArrayList<>();
        expect.add(1 + " " + 2);
        expect.add(0 + " " + 2);
        expect.add(2 + " " + 1);
        expect.add(2 + " " + 0);
        expect.add(3 + " " + 2);
        expect.add(2 + " " + 3);

        List<String> resultsList = getResultList(rook, board);
        assertTrue(expect.containsAll(resultsList)
                && expect.size() == resultsList.size());
    }
}

