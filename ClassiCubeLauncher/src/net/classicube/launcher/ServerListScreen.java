package net.classicube.launcher;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.SwingWorker.StateValue;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class ServerListScreen extends javax.swing.JFrame {

    public ServerListScreen() {
        LogUtil.getLogger().log(Level.FINE, "ServerListScreen");
        initComponents();

        // prepare to auto-adjust table columns (when the data arrives)
        serverTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableColumnAdjuster = new TableColumnAdjuster(serverTable);

        // configure table sorting and selection
        serverTable.setAutoCreateRowSorter(true);
        serverTable.setCellSelectionEnabled(false);
        serverTable.setRowSelectionAllowed(true);
        serverTable.removeColumn(serverTable.getColumn("hiddenHash"));

        // center the form on screen (initially)
        setLocationRelativeTo(null);

        // start fetching the server list
        tSearch.setText("Loading server list...");
        tSearch.setEnabled(false);
        getServerListTask = SessionManager.getSession().getServerListAsync();
        getServerListTask.addPropertyChangeListener(
                new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("state".equals(evt.getPropertyName())) {
                    if (evt.getNewValue().equals(StateValue.DONE)) {
                        onServerListDone();
                    }
                }
            }
        });
        getServerListTask.execute();
    }

    class UptimeCellRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (column == 3) {
                final int ticks = (int) value;
                this.setText(MinecraftNetSession.formatUptime(ticks));
            } else {
                this.setText("");

            }
            return this;
        }
    }

    private void onServerListDone() {
        LogUtil.getLogger().log(Level.FINE, "ServerListScreen.onServerListDone");
        try {
            final ServerInfo[] result = getServerListTask.get();
            final DefaultTableModel model = (DefaultTableModel) serverTable.getModel();
            for (ServerInfo server : result) {
                model.addRow(new Object[]{
                    server.name,
                    server.players,
                    server.maxPlayers,
                    server.uptime,
                    server.flag
                });
                lastServer = server;
            }
            tSearch.setText("Search...");
            tSearch.setEnabled(true);
            tSearch.selectAll();
            tSearch.requestFocus();
            progress.setVisible(false);

            tableColumnAdjuster.adjustColumns();

        } catch (InterruptedException | ExecutionException ex) {
            LogUtil.showWarning(ex.toString(), "Problem loading server list");
            tSearch.setText("Could not load server list.");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT
     * modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        bChangeUser = new javax.swing.JButton();
        tSearch = new javax.swing.JTextField();
        javax.swing.JSeparator separator1 = new javax.swing.JSeparator();
        javax.swing.JSeparator separator2 = new javax.swing.JSeparator();
        bPreferences = new javax.swing.JButton();
        tServerURL = new javax.swing.JTextField();
        bConnect = new javax.swing.JButton();
        serverTableContainer = new javax.swing.JScrollPane();
        serverTable = new javax.swing.JTable();
        progress = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        java.awt.GridBagLayout layout = new java.awt.GridBagLayout();
        layout.columnWidths = new int[] {0, 5, 0, 5, 0};
        layout.rowHeights = new int[] {0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0, 5, 0};
        getContentPane().setLayout(layout);

        bChangeUser.setText("< Change User");
        bChangeUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bChangeUserActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        getContentPane().add(bChangeUser, gridBagConstraints);

        tSearch.setText("Search");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_END;
        getContentPane().add(tSearch, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        getContentPane().add(separator1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        getContentPane().add(separator2, gridBagConstraints);

        bPreferences.setText("Preferences");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_END;
        getContentPane().add(bPreferences, gridBagConstraints);

        tServerURL.setText("Server URL");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        getContentPane().add(tServerURL, gridBagConstraints);

        bConnect.setText("Connect >");
        bConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bConnectActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LAST_LINE_END;
        getContentPane().add(bConnect, gridBagConstraints);

        serverTableContainer.setMinimumSize(new java.awt.Dimension(302, 152));
        serverTableContainer.setPreferredSize(new java.awt.Dimension(520, 400));

        serverTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Players", "Max", "Uptime", "Location", "hiddenHash"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        serverTable.setColumnSelectionAllowed(true);
        serverTable.getTableHeader().setReorderingAllowed(false);
        serverTableContainer.setViewportView(serverTable);
        serverTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        serverTable.getColumnModel().getColumn(1).setResizable(false);
        serverTable.getColumnModel().getColumn(1).setPreferredWidth(60);
        serverTable.getColumnModel().getColumn(2).setResizable(false);
        serverTable.getColumnModel().getColumn(2).setPreferredWidth(60);
        serverTable.getColumnModel().getColumn(3).setResizable(false);
        serverTable.getColumnModel().getColumn(3).setPreferredWidth(60);
        serverTable.getColumnModel().getColumn(3).setCellRenderer(new UptimeCellRenderer());
        serverTable.getColumnModel().getColumn(4).setResizable(false);
        serverTable.getColumnModel().getColumn(4).setPreferredWidth(60);
        serverTable.getColumnModel().getColumn(5).setResizable(false);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(serverTableContainer, gridBagConstraints);

        progress.setIndeterminate(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        getContentPane().add(progress, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bChangeUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bChangeUserActionPerformed
        LogUtil.getLogger().log(Level.INFO, "[Change User]");
        EntryPoint.ShowSignInScreen();
    }//GEN-LAST:event_bChangeUserActionPerformed

    private void onServerDetailsDone() {
        LogUtil.getLogger().log(Level.FINE, "ServerListScreen.onServerDetailsDone");
        try {
            final boolean result = getServerDetailsTask.get();
            if (result) {
                ServerInfo server = getServerDetailsTask.getServerInfo();
                launchClient(server);
            } else {
                LogUtil.showError("Could not fetch server details.", "Error");
            }
        } catch (InterruptedException | ExecutionException ex) {
            LogUtil.showWarning(ex.toString(), "Problem loading server list");
            tSearch.setText("Could not load server list.");
        }
    }

    private void bConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bConnectActionPerformed
        // TODO: figure out which ServerInfo is selected
        getServerDetailsTask = SessionManager.getSession().getServerDetailsAsync(lastServer);
        getServerDetailsTask.addPropertyChangeListener(
                new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("state".equals(evt.getPropertyName())) {
                    if (evt.getNewValue().equals(StateValue.DONE)) {
                        onServerDetailsDone();
                    }
                }
            }
        });
        progress.setVisible(true);
        getServerDetailsTask.execute();
    }

    private void launchClient(ServerInfo server) {
        try {
            // wait for updater to finish (if still running)
            ClientUpdateTask.getInstance().get();

        } catch (InterruptedException | ExecutionException ex) {
            LogUtil.die("Error while updating: " + ex);
            return;
        }

        final File java = getJavaPath();
        final ProcessBuilder processBuilder = new ProcessBuilder(
                java.getAbsolutePath(),
                "-jar",
                ClientUpdateTask.getClientPath().getAbsolutePath(),
                server.address.getHostAddress(),
                Integer.toString(server.port),
                SessionManager.getSession().account.PlayerName,
                server.hash);

        try {
            setVisible(false);
            LogUtil.getLogger().log(Level.INFO, concatStringsWSep(processBuilder.command(), " "));
            processBuilder.start();
            System.exit(0);
        } catch (IOException ex) {
            LogUtil.die("Error launching client: " + ex);
        }
    }

    public static String concatStringsWSep(List<String> strings, String separator) {
        StringBuilder sb = new StringBuilder();
        String sep = "";
        for (String s : strings) {
            sb.append(sep).append(s);
            sep = separator;
        }
        return sb.toString();
    }

    private File getJavaPath() {
        return new File(System.getProperty("java.home"), "bin/java");
    }//GEN-LAST:event_bConnectActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bChangeUser;
    private javax.swing.JButton bConnect;
    private javax.swing.JButton bPreferences;
    private javax.swing.JProgressBar progress;
    private javax.swing.JTable serverTable;
    private javax.swing.JScrollPane serverTableContainer;
    private javax.swing.JTextField tSearch;
    private javax.swing.JTextField tServerURL;
    // End of variables declaration//GEN-END:variables
    private GameSession.GetServerListTask getServerListTask;
    private GameSession.GetServerDetailsTask getServerDetailsTask;
    private TableColumnAdjuster tableColumnAdjuster;
    ServerInfo lastServer; // temporary -- for testing
}
