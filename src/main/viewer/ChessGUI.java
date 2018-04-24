package viewer;

import controller.Chess;
import model.BasePiece;
import model.Board;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The GUI for chessboard. GUI tutorial from https://www.youtube.com/watch?v=aHFiRhGnvKE.
 */
public class ChessGUI {

    /**
     * The panel for each piece.
     */
    public class PiecePanel extends JPanel {
        /**
         * The rank of this piece.
         */
        private int rank;
        /**
         * The file of this piece.
         */
        private int file;
        /**
         * The position of this piece.
         */
        private BasePiece.Position position;

        /**
         * If the panel is clicked but it leads to an illegal move. This variable is used for adding red warning border.
         */
        private boolean illegalMove = false;

        PiecePanel(int rank, int file) {
            super(new GridBagLayout());
            this.rank = rank;
            this.file = file;
            this.position = new BasePiece.Position(rank, file);
            setPreferredSize(PIECE_PANEL_DIMENSION);
            assignPieceColor();
            assignPieceImage(chessboard);

            validate();
        }

        public void setIllegal(boolean illegal) {
            illegalMove = illegal;
        }

        /**
         * Assign light or dark color of this piece by its rank and file.
         */
        private void assignPieceColor() {
            setBackground((rank + file) % 2 == 0 ? LIGHT_COLOR : DARK_COLOR);
        }

        /**
         * Assign the image icon from static image path {@link ChessGUI#IMG_PATH} to this piece.
         */
        private void assignPieceImage(Board board) {
            // Clear previous information
            this.removeAll();

            BasePiece piece = board.getPiece(new BasePiece.Position(rank, file));
            if (piece != null) {
                String name = piece.getNameString();
                String color = piece.getWhitePlayer() ? "W" : "B";
                try {
                    BufferedImage image = ImageIO.read(new File(IMG_PATH + color + name + ".png"));
                    // Sets the scale of image icon
                    Image dimg = image.getScaledInstance(60, 60,
                            Image.SCALE_SMOOTH);
                    JLabel label = new JLabel(new ImageIcon(dimg));
                    add(label);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

        /**
         * Assign light or dark legal move color of this piece by its rank and file.
         */
        private void assignLegalMoveColor() {
            setBackground((rank + file) % 2 == 0 ? LIGHT_LEGAL_MOVE_COLOR : DARK_LEGAL_MOVE_COLOR);
        }

        /**
         * Redraw this panel by new information from board.
         */
        public void drawPiece(final Board board) {
            if (chessGame.getSelectPiece() != null && chessGame.getSelectPiece().isSamePosition(position)) {
                this.setBorder(thickBorder);
            } else if (illegalMove) {
                this.setBorder(illegalBorder);
                PiecePanel panel = this;
                illegalMove = false;
                int delay = 1500; //milliseconds
                // The warning illegal border will only last for some time and then disappears
                ActionListener taskPerformer = new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        panel.setBorder(null);
                    }
                };
                new Timer(delay, taskPerformer).start();
            } else {
                this.setBorder(null);
            }

            assignPieceColor();
            if (chessGame.getSelectPiece() != null) {
                for (BasePiece.Position pos : board.getLegalMoves(chessGame.getSelectPiece())) {
                    if (pos.getFile() == file && pos.getRank() == rank) {
                        assignLegalMoveColor();
                    }
                }
            }
            assignPieceImage(board);
            validate();
            repaint();
        }

        public BasePiece.Position getPosition() {
            return position;
        }
    }

    /**
     * The basic board for chess game. One board holds 8*8 {@link PiecePanel}s.
     */
    public class BoardPanel extends JPanel {
        private ArrayList<PiecePanel> boardPieces;

        BoardPanel() throws IOException {
            super(new GridLayout(8, 8));
            this.boardPieces = new ArrayList<PiecePanel>();
            // Assign 8*8 piece panels.
            for (int rank = 7; rank >= 0; rank--) {
                for (int file = 0; file < 8; file++) {
                    final PiecePanel piecePanel = new PiecePanel(rank, file);
                    this.boardPieces.add(piecePanel);
                    add(piecePanel);
                }
            }
            setPreferredSize(BOARD_PANEL_DIMENSION);
            validate();
        }

        /**
         * Redraw this panel recursively by new information from board
         */
        public void drawBoard(Board board) {
            removeAll();
            for (PiecePanel piecePanel : boardPieces) {
                piecePanel.drawPiece(board);
                add(piecePanel);
            }
            validate();
            repaint();
        }

        public ArrayList<PiecePanel> getPiecePanels() {
            return boardPieces;
        }
    }

    private Chess chessGame;

    /**
     * The main chess GUI frame
     */
    private JFrame chessFrame;

    /**
     * The board panel on main frame
     */
    private BoardPanel boardPanel;

    /**
     * The control panel on main frame
     */
    private ControlPanel controlPanel;

    /**
     * The chessboard data structure for inner logic of chess
     */
    private Board chessboard;

    /**
     * The border to circle and emphasize the current selected piece
     */
    private Border thickBorder = new LineBorder(Color.WHITE, 2);

    /**
     * The border to circle and emphasize the illegal move piece
     */
    private Border illegalBorder = new LineBorder(Color.RED, 3);

    /**
     * The dimension of chess frame
     */
    private static final Dimension CHESS_DIMENSION = new Dimension(720, 600);

    /**
     * The dimension of board panel
     */
    private static final Dimension BOARD_PANEL_DIMENSION = new Dimension(600, 600);

    /**
     * The dimension of piece panel
     */
    private static final Dimension PIECE_PANEL_DIMENSION = new Dimension(50, 50);

    /**
     * The path of static image icons of chess pieces
     */
    private static String IMG_PATH = "img/";

    /**
     * The light brown color for chess table
     */
    private static Color LIGHT_COLOR = Color.decode("#EBBF95");

    /**
     * The dark brown color for chess table
     */
    private static Color DARK_COLOR = Color.decode("#CF8B4D");

    /**
     * The light color legal move for chess table
     */
    private static Color LIGHT_LEGAL_MOVE_COLOR = Color.decode("#f7eee6");

    /**
     * The dark color legal move for chess table
     */
    private static Color DARK_LEGAL_MOVE_COLOR = Color.decode("#f4e5d7");

    public ChessGUI(Board board) {
        this.chessFrame = new JFrame("CHESS");
        this.chessboard = board;
    }

    /**
     * Initialize a new chess GUI by adding all components
     */
    public void initialize(Chess chessGame) throws IOException {
        this.chessGame = chessGame;
        chessFrame.setSize(CHESS_DIMENSION);
        chessFrame.setLayout(new BorderLayout());
        chessFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        boardPanel = new BoardPanel();
        controlPanel = new ControlPanel();
        chessFrame.add(boardPanel, BorderLayout.CENTER);
        chessFrame.add(controlPanel, BorderLayout.EAST);
        chessFrame.setVisible(true);

        // Obtain user input of player names
        JPanel p = new JPanel();
        JTextField whitePlayerField = new JTextField(10);
        JTextField blackPlayerField = new JTextField(10);

        p.add(new JLabel("White Player Name:"));
        p.add(whitePlayerField);
        p.add(new JLabel("Black Player Name: "));
        p.add(blackPlayerField);

        JOptionPane.showConfirmDialog(null, p, "Enter Player Nicknames: ", JOptionPane.OK_CANCEL_OPTION);
        String[] playerNames = {whitePlayerField.getText(), blackPlayerField.getText()};
        controlPanel.setPlayerNames(playerNames);
    }

    public JFrame getChessFrame() {
        return chessFrame;
    }

    public BoardPanel getChessPanel() {
        return boardPanel;
    }

    public ControlPanel getControlPanel() {
        return controlPanel;
    }
}
