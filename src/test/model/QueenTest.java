package model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static model.BasePiece.Position;
import static model.BasePieceTest.getResultList;
import static org.junit.Assert.assertEquals;

public class QueenTest {

    private Board board;

    @org.junit.Before
    public void setUp() throws Exception {
        board = new Board(8, 8);
    }

    @Test
    public void TestIsValidMovePass() {
        Position queenPosition = new Position(4, 4);
        BasePiece queen = new Queen(queenPosition, true);
        board.setPiece(queen);

        assertEquals(queen.isValidMove(board.getChessBoard(), new BasePiece.Position(2, 4)), true);
        assertEquals(queen.isValidMove(board.getChessBoard(), new BasePiece.Position(5, 5)), true);

    }

    @Test
    public void TestIsValidMoveFailBlocked() {
        Position queenPosition = new Position(5, 5);
        Position blockPosition = new Position(3, 5);
        Position targetPosition = new Position(1, 5);
        BasePiece queen = new Queen(queenPosition, true);
        BasePiece king = new King(blockPosition, false);
        board.setPiece(queen);
        board.setPiece(king);

        assertEquals(queen.isValidMove(board.getChessBoard(), targetPosition), false);
    }

    @Test
    public void TestIsValidMoveFailIllegal() {
        Position queenPosition = new Position(3, 3);
        Position targetPosition = new Position(1, 6);
        BasePiece queen = new Queen(queenPosition, true);
        board.setPiece(queen);

        assertEquals(queen.isValidMove(board.getChessBoard(), targetPosition), false);
    }

    @Test
    public void TestGetPossibleLegalMoves() {
        Position queenPosition = new Position(1, 1);
        Position rookPosition = new Position(3, 1);
        Position kingPosition = new Position(3, 3);
        Position bishopPosition = new Position(1, 3);
        BasePiece queen = new Queen(queenPosition, true);
        BasePiece rook = new Rook(rookPosition, false);
        BasePiece king = new King(kingPosition, true);
        BasePiece bishop = new Bishop(bishopPosition, true);

        board.setPiece(queen);
        board.setPiece(rook);
        board.setPiece(king);
        board.setPiece(bishop);

        List<String> expect = new ArrayList<>();
        expect.add(0 + " " + 0);
        expect.add(0 + " " + 1);
        expect.add(0 + " " + 2);
        expect.add(1 + " " + 0);
        expect.add(1 + " " + 2);
        expect.add(2 + " " + 0);
        expect.add(2 + " " + 1);
        expect.add(2 + " " + 2);
        expect.add(3 + " " + 1);

        List<String> resultsList = getResultList(queen, board);
        assertTrue(expect.containsAll(resultsList)
                && expect.size() == resultsList.size());
    }
}

