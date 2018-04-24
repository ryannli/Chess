package model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static model.BasePieceTest.getResultList;
import static org.junit.Assert.assertEquals;

public class SoldierTest {

    private Board board;

    @org.junit.Before
    public void setUp() throws Exception {
        board = new Board(8, 8);
    }

    @Test
    public void TestIsValidMovePassed() {
        BasePiece.Position pawnPosition = new BasePiece.Position(3, 4);
        BasePiece soldier = new Soldier(pawnPosition, true);
        board.setPiece(soldier);

        assertEquals(soldier.getNameString(), "S");
        assertEquals(soldier.isValidMove(board.getChessBoard(), new BasePiece.Position(4, 4)), true);
    }

    @Test
    public void TestIsValidMoveFailIllegal() {
        BasePiece.Position soldierPosition = new BasePiece.Position(3, 3);
        BasePiece.Position targetPosition = new BasePiece.Position(3, 4);
        BasePiece soldier = new Soldier(soldierPosition, true);
        board.setPiece(soldier);

        assertEquals(soldier.isValidMove(board.getChessBoard(), targetPosition), false);

        targetPosition = new BasePiece.Position(4, 5);

        assertEquals(soldier.isValidMove(board.getChessBoard(), targetPosition), false);
    }

    @Test
    public void TestGetPossibleLegalMoves() {
        BasePiece.Position soldierPosition = new BasePiece.Position(3, 3);
        BasePiece soldier = new Soldier(soldierPosition, false);
        board.setPiece(soldier);

        List<String> expect = new ArrayList<>();
        expect.add(2 + " " + 3);
        expect.add(3 + " " + 2);
        expect.add(3 + " " + 4);

        List<String> resultsList = getResultList(soldier, board);
        assertTrue(expect.containsAll(resultsList)
                && expect.size() == resultsList.size());
    }
}
