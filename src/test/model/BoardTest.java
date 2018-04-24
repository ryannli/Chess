package model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BoardTest {
    private Board board;

    @org.junit.Before
    public void setUp() throws Exception {
        board = new Board(8, 8);
    }

    @Test
    public void TestSetPiece() {
        BasePiece.Position pawnPosition = new BasePiece.Position(10, 4);
        BasePiece pawn = new Pawn(pawnPosition, true);

        assertEquals(board.setPiece(pawn), false);
    }

    @Test
    public void TestGetPiece() {
        BasePiece.Position pawnPosition = new BasePiece.Position(10, 4);
        assertEquals(board.getPiece(pawnPosition), null);
        pawnPosition = new BasePiece.Position(4, 4);
        BasePiece pawn = new Pawn(pawnPosition, true);
        board.setPiece(pawn);

        assertEquals(board.getPiece(pawnPosition), pawn);
    }

    @Test
    public void TestRemovePiece() {
        BasePiece.Position pawnPosition = new BasePiece.Position(10, 4);

        assertEquals(board.removePiece(pawnPosition), false);
    }

    @Test
    public void TestGetPlayerAllPieces() {
        BasePiece.Position pawnPosition = new BasePiece.Position(6, 4);
        BasePiece.Position kingPosition = new BasePiece.Position(4, 4);
        BasePiece.Position queenPosition = new BasePiece.Position(2, 4);
        BasePiece pawn = new Pawn(pawnPosition, true);
        BasePiece king = new King(kingPosition, true);
        BasePiece queen = new Queen(queenPosition, false);
        board.setPiece(pawn);
        board.setPiece(king);
        List<BasePiece> pieces = new ArrayList<BasePiece>();
        pieces.add(king);
        pieces.add(pawn);

        assertEquals(board.getPlayerAllPieces(true), pieces);
    }

    @Test
    public void TestTryMoveIfInCheck() {
        BasePiece.Position queenPosition = new BasePiece.Position(4, 6);
        BasePiece.Position pawnPosition = new BasePiece.Position(4, 5);
        BasePiece.Position kingPosition = new BasePiece.Position(4, 4);
        BasePiece.Position bishopPosition = new BasePiece.Position(5, 5);
        BasePiece pawn = new Pawn(pawnPosition, true);
        BasePiece king = new King(kingPosition, true);
        BasePiece queen = new Queen(queenPosition, false);
        BasePiece bishop = new Bishop(bishopPosition, false);
        board.setPiece(pawn);
        board.setPiece(king);
        board.setPiece(queen);
        board.setPiece(bishop);

        assertEquals(board.tryMoveIfInCheck(pawn, new BasePiece.Position(5, 5)), true);
        assertEquals(board.getPiece(new BasePiece.Position(4, 6)), queen);
        assertEquals(board.getPiece(new BasePiece.Position(4, 5)), pawn);
        assertEquals(board.getPiece(new BasePiece.Position(4, 4)), king);
    }

    @Test
    public void TestMovePiece() {
        BasePiece.Position pawnPosition = new BasePiece.Position(4, 4);
        BasePiece.Position kingPosition = new BasePiece.Position(3, 4);
        BasePiece.Position targetPosition = new BasePiece.Position(5, 4);
        BasePiece pawn = new Pawn(pawnPosition, true);
        BasePiece king = new King(kingPosition, true);
        board.setPiece(pawn);
        board.setPiece(king);

        assertEquals(board.movePiece(pawn, targetPosition), true);

        targetPosition = new BasePiece.Position(7, 4);

        assertEquals(board.movePiece(pawn, targetPosition), false);
    }

    @Test
    public void TestGetKingNull() {
        assertEquals(board.getKing(true), null);
    }

    @Test
    public void TestInCheckMateTrue() {
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

        assertEquals(board.inCheckmate(false), true);
    }

    @Test
    public void TestInCheckMateFalse() {
        BasePiece.Position queenPosition = new BasePiece.Position(2, 2);
        BasePiece.Position kingPosition = new BasePiece.Position(0, 1);
        BasePiece queen = new Queen(queenPosition, true);
        BasePiece king = new King(kingPosition, false);
        board.setPiece(queen);
        board.setPiece(king);

        assertEquals(board.inCheckmate(false), false);
    }

    @Test
    public void TestInStaleMateTrue() {
        BasePiece.Position rookPosition1 = new BasePiece.Position(2, 1);
        BasePiece.Position rookPosition2 = new BasePiece.Position(1, 2);
        BasePiece.Position kingPosition = new BasePiece.Position(0, 0);
        BasePiece rook1 = new Rook(rookPosition1, true);
        BasePiece rook2 = new Rook(rookPosition2, true);
        BasePiece king = new King(kingPosition, false);
        board.setPiece(rook1);
        board.setPiece(rook2);
        board.setPiece(king);

        assertEquals(board.inStaleMate(false), true);
    }

    @Test
    public void TestInStaleMateFalse() {
        BasePiece.Position rookPosition1 = new BasePiece.Position(2, 0);
        BasePiece.Position kingPosition = new BasePiece.Position(0, 0);
        BasePiece rook1 = new Rook(rookPosition1, true);
        BasePiece king = new King(kingPosition, false);
        board.setPiece(rook1);
        board.setPiece(king);

        assertEquals(board.inStaleMate(false), false);

        board.removePiece(rookPosition1);

        assertEquals(board.inStaleMate(false), false);
    }
}
