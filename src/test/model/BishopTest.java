package model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static model.BasePiece.Position;
import static model.BasePieceTest.getResultList;
import static org.junit.Assert.assertEquals;

public class BishopTest {

    private Board board;

    @org.junit.Before
    public void setUp() throws Exception {
        board = new Board(8, 8);
    }

    @Test
    public void TestIsValidMovePassed() {
        Position bishopPosition = new Position(4, 4);
        BasePiece bishop = new Bishop(bishopPosition, true);
        board.setPiece(bishop);

        assertEquals(bishop.isValidMove(board.getChessBoard(), new BasePiece.Position(1, 1)), true);
    }

    @Test
    public void TestIsValidMoveFailBlocked() {
        Position bishopPosition = new Position(3, 3);
        Position blockPosition = new Position(2, 2);
        Position targetPosition = new Position(1, 1);
        BasePiece bishop = new Bishop(bishopPosition, true);
        BasePiece queen = new Queen(blockPosition, false);
        board.setPiece(bishop);
        board.setPiece(queen);

        assertEquals(bishop.isValidMove(board.getChessBoard(), targetPosition), false);
    }

    @Test
    public void TestIsValidMoveFailIllegal() {
        Position bishopPosition = new Position(3, 3);
        Position targetPosition = new Position(1, 2);
        BasePiece bishop = new Bishop(bishopPosition, true);
        board.setPiece(bishop);

        assertEquals(bishop.isValidMove(board.getChessBoard(), targetPosition), false);
    }

    @Test
    public void TestGetPossibleLegalMoves() {
        Position bishopPosition = new Position(2, 1);
        Position queenPosition = new Position(4, 3);
        BasePiece bishop = new Bishop(bishopPosition, true);
        BasePiece queen = new Queen(queenPosition, true);
        board.setPiece(bishop);
        board.setPiece(queen);

        List<String> expect = new ArrayList<>();
        expect.add(1 + " " + 0);
        expect.add(3 + " " + 0);
        expect.add(3 + " " + 2);
        expect.add(1 + " " + 2);
        expect.add(0 + " " + 3);

        List<String> resultsList = getResultList(bishop, board);
        assertTrue(expect.containsAll(resultsList)
                && expect.size() == resultsList.size());
    }
}

