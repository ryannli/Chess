package viewer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.ArrayList;

public class ControlPanel extends JPanel {
    /**
     * The restart button to restart a new game
     */
    private JButton restartButton;
    /**
     * The undo button to undo the last move
     */
    private JButton undoButton;
    /**
     * The forfeit button of white player
     */
    private JButton whiteForfeitButton;
    /**
     * The forfeit button of black player
     */
    private JButton blackForfeitButton;
    /**
     * The label to show current turn information
     */
    private JLabel currentTurnLabel;
    /**
     * The scroll panel to hold game history
     */
    private JScrollPane scrollPane;
    /**
     * The table of game history
     */
    private JTable table;
    /**
     * The model of history table
     */
    private HistoryTableModel historyTableModel;
    /**
     * The dimension of history table
     */
    private static final Dimension HISTORY_DIMENSION = new Dimension(30, 450);
    /**
     * The dimension of button
     */
    private static final Dimension BUTTON_DIMENSION = new Dimension(90, 15);

    ControlPanel() {
        this.setLayout(new BorderLayout());

        JPanel northPanel = new JPanel(new BorderLayout());
        restartButton = new JButton("Restart");
        undoButton = new JButton("Undo");
        currentTurnLabel = new JLabel("");
        northPanel.add(restartButton, BorderLayout.NORTH);
        northPanel.add(undoButton, BorderLayout.CENTER);
        northPanel.add(currentTurnLabel, BorderLayout.SOUTH);

        whiteForfeitButton = new JButton("Forfeit (W)");

        whiteForfeitButton.setForeground(Color.decode("#EBBF95"));
        whiteForfeitButton.setPreferredSize(BUTTON_DIMENSION);

        blackForfeitButton = new JButton("Forfeit (B)");
        blackForfeitButton.setForeground(Color.decode("#CF8B4D"));
        whiteForfeitButton.setPreferredSize(BUTTON_DIMENSION);
        blackForfeitButton.setPreferredSize(BUTTON_DIMENSION);

        historyTableModel = new HistoryTableModel();
        table = new JTable(historyTableModel);
        table.setRowHeight(15);
        this.scrollPane = new JScrollPane(table);
        scrollPane.setColumnHeaderView(table.getTableHeader());
        scrollPane.setPreferredSize(HISTORY_DIMENSION);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Game History"));

        this.add(northPanel, BorderLayout.NORTH);
        this.add(whiteForfeitButton, BorderLayout.WEST);
        this.add(blackForfeitButton, BorderLayout.EAST);
        this.add(scrollPane, BorderLayout.SOUTH);
    }

    /**
     * The add game results to history table
     *
     * @param whiteResult game result for white player
     * @param blackResult game result for black player
     */
    public void addHistory(String whiteResult, String blackResult) {
        int row = historyTableModel.getRowCount();

        historyTableModel.setValueAt(whiteResult, row, 0);
        historyTableModel.setValueAt(blackResult, row, 1);
        historyTableModel.fireTableDataChanged();
        scrollPane.repaint();
        scrollPane.validate();
    }

    /**
     * The player names on GUI
     *
     * @param playerNames list of player names
     */
    public void setPlayerNames(String[] playerNames) {
        String[] playerNamesWithWB = new String[]{playerNames[0] + " (W)", playerNames[1] + " (B)"};
        historyTableModel.setPlayerNames(playerNamesWithWB);
        JTableHeader header = table.getTableHeader();
        TableColumnModel colMod = header.getColumnModel();
        colMod.getColumn(0).setHeaderValue(playerNamesWithWB[0]);
        colMod.getColumn(1).setHeaderValue(playerNamesWithWB[1]);
        header.repaint();

        currentTurnLabel.setText("Current Turn: " + playerNamesWithWB[0]);
        currentTurnLabel.repaint();
    }

    public String[] getPlayerNames() {
        return historyTableModel.getPlayerNames();
    }

    public JButton getBlackForfeitButton() {
        return blackForfeitButton;
    }


    public JButton getWhiteForfeitButton() {
        return whiteForfeitButton;
    }

    public JButton getRestartButton() {
        return restartButton;
    }

    public JButton getUndoButton() {
        return undoButton;
    }

    public JLabel getCurrentTurnLabel() {
        return currentTurnLabel;
    }

    /**
     * The model of history table.
     * Tutorial from https://www.youtube.com/watch?v=PqM9cjpmoH8.
     */
    public static class HistoryTableModel extends DefaultTableModel {
        private ArrayList<Row> values;
        private String[] playerNames = {"White", "Black"};

        HistoryTableModel() {
            this.values = new ArrayList<Row>();
        }

        public void setPlayerNames(String[] names) {
            playerNames = names;
        }

        public String[] getPlayerNames() {
            return playerNames;
        }

        @Override
        public int getRowCount() {
            if (this.values == null) {
                return 0;
            }
            return this.values.size();
        }

        @Override
        public int getColumnCount() {
            return playerNames.length;
        }

        @Override
        public Object getValueAt(final int row, final int col) {
            final Row currentRow = this.values.get(row);
            if (col == 0) {
                return currentRow.getWhiteResult();
            } else {
                return currentRow.getBlackResult();
            }
        }

        @Override
        public void setValueAt(Object result, int row, int col) {
            Row currentRow;
            if (this.values.size() <= row) {
                currentRow = new Row();
                this.values.add(currentRow);
            } else {
                currentRow = this.values.get(row);
            }
            if (col == 0) {
                currentRow.setWhiteResult((String) result);
            } else if (col == 1) {
                currentRow.setBlackResult((String) result);
            }
            fireTableCellUpdated(row, col);
        }

        @Override
        public String getColumnName(int column) {
            return playerNames[column];
        }
    }

    /**
     * The class to hold a pair of strings
     * Tutorial from https://www.youtube.com/watch?v=PqM9cjpmoH8.
     */
    public static class Row {
        private String whiteResult;
        private String blackResult;

        public String getWhiteResult() {
            return whiteResult;
        }

        public String getBlackResult() {
            return blackResult;
        }

        public void setWhiteResult(String result) {
            whiteResult = result;
        }

        public void setBlackResult(String result) {
            blackResult = result;
        }
    }
}
