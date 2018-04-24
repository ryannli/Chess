package model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static model.BasePieceTest.getResultList;
import static org.junit.Assert.assertEquals;

public class ElephantTest {

    private Board board;

    @org.junit.Before
    public void setUp() throws Exception {
        board = new Board(8, 8);
    }

    @Test
    public void TestIsValidMovePassed() {
        BasePiece.Position elephantPosition = new BasePiece.Position(4, 4);
        BasePiece elephant = new Elephant(elephantPosition, true);
        board.setPiece(elephant);

        assertEquals(elephant.isValidMove(board.getChessBoard(), new BasePiece.Position(2, 2)), true);
        assertEquals(elephant.getNameString(), "E");
    }

    @Test
    public void TestIsValidMoveFailBlocked() {
        BasePiece.Position elephantPosition = new BasePiece.Position(3, 3);
        BasePiece.Position blockPosition = new BasePiece.Position(2, 2);
        BasePiece.Position targetPosition = new BasePiece.Position(1, 1);
        BasePiece elephant = new Elephant(elephantPosition, true);
        BasePiece queen = new Queen(blockPosition, false);
        board.setPiece(elephant);
        board.setPiece(queen);

        assertEquals(elephant.isValidMove(board.getChessBoard(), targetPosition), false);
    }


    @Test
    public void TestGetPossibleLegalMoves() {
        BasePiece.Position elephantPosition = new BasePiece.Position(2, 5);
        BasePiece.Position queenPosition = new BasePiece.Position(4, 3);
        BasePiece elephant = new Elephant(elephantPosition, true);
        BasePiece queen = new Queen(queenPosition, true);
        board.setPiece(elephant);
        board.setPiece(queen);

        List<String> expect = new ArrayList<>();
        expect.add(4 + " " + 7);
        expect.add(0 + " " + 7);
        expect.add(0 + " " + 3);

        List<String> resultsList = getResultList(elephant, board);
        assertTrue(expect.containsAll(resultsList)
                && expect.size() == resultsList.size());
    }
}
