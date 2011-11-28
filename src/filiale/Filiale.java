package filiale;

import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.ConnectException;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import mware_lib.NameService;
import mware_lib.ObjectBroker;
import mware_lib.exceptions.RemoteException;
import branch_access.Manager;

/**
 * GUI-basierte Hauptanwendung des Geldautomaten.
 * 
 */
public class Filiale extends Frame implements ActionListener {
    private static final long serialVersionUID = 6751599359033886260L;
    // Textfelder
    private TextField BankTextField, KontoTextFieldNeu, KontoTextFieldDelete,
            OwnerTextField;
    // Knoepfe
    private Button NeuesKontoButton, KontoLoeschenButton;
    // Statuszeile
    private StatusLine StatusLabel;
    private static String host;
    private static int port;
    private static Filiale myGUI_;

    /**
     * Statuszeile des Fensters
     */
    private class StatusLine extends Label {
        private static final long serialVersionUID = -1575611930399336962L;

        public StatusLine(String text) {
            super(text);
        }

        /**
         * FÃ¼r normalen Text.
         * 
         * @param message
         */
        public void setInfoText(String message) {
            setForeground(Color.BLUE);
            setText(message);
        }

        /**
         * FÃ¼r Fehlertext.
         * 
         * @param message
         */
        public void setErrorText(String message) {
            setForeground(Color.RED);
            setText(message);
        }
    }

    /**
     * Konstruktor. Aufbau der GUI.
     * 
     */
    public Filiale() {
        super("Filiale");

        // --- Layout manager
        GridBagConstraints constraints = new GridBagConstraints();
        GridBagLayout gridbag = new GridBagLayout();
        setLayout(gridbag);

        // --- Bank (Namenseingabe)
        Label BankLabel = new Label("Bank:        ");
        constraints.insets = new Insets(10, 20, 5, 20);
        constraints.gridwidth = GridBagConstraints.RELATIVE;
        constraints.anchor = GridBagConstraints.WEST;
        gridbag.setConstraints(BankLabel, constraints);
        add(BankLabel);

        BankTextField = new TextField(20);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        gridbag.setConstraints(BankTextField, constraints);
        add(BankTextField);

        // --- (Trenner) ------------------------
        Label separatorLabel1 = new Label(
                "--------------------------------------------------");
        constraints.insets = new Insets(5, 20, 5, 20);
        gridbag.setConstraints(separatorLabel1, constraints);
        add(separatorLabel1);

        // --- Operation: Konto einrichten

        Label KontoLabel = new Label("Kontoinhaber:   ");
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridwidth = GridBagConstraints.RELATIVE;
        constraints.insets = new Insets(10, 20, 5, 0);
        gridbag.setConstraints(KontoLabel, constraints);
        add(KontoLabel);

        OwnerTextField = new TextField(20);
        constraints.insets = new Insets(10, 20, 5, 25);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.anchor = GridBagConstraints.EAST;
        gridbag.setConstraints(OwnerTextField, constraints);
        add(OwnerTextField);

        NeuesKontoButton = new Button("Neues Konto!");
        constraints.insets = new Insets(5, 20, 5, 20);
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridwidth = GridBagConstraints.RELATIVE;
        gridbag.setConstraints(NeuesKontoButton, constraints);
        add(NeuesKontoButton);
        NeuesKontoButton.addActionListener(this);

        KontoTextFieldNeu = new TextField(20);
        KontoTextFieldNeu.setEditable(false);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        gridbag.setConstraints(KontoTextFieldNeu, constraints);
        add(KontoTextFieldNeu);

        // --- (Trenner) ------------------------
        Label separatorLabel2 = new Label(
                "--------------------------------------------------");
        constraints.insets = new Insets(5, 20, 5, 20);
        constraints.anchor = GridBagConstraints.WEST;
        gridbag.setConstraints(separatorLabel2, constraints);
        add(separatorLabel2);

        // --- Operation: Konto loeschen

        KontoTextFieldDelete = new TextField(20);
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridwidth = GridBagConstraints.RELATIVE;
        constraints.insets = new Insets(10, 20, 5, 0);
        gridbag.setConstraints(KontoTextFieldDelete, constraints);
        add(KontoTextFieldDelete);

        KontoLoeschenButton = new Button("Konto lÃ¶schen!");
        constraints.insets = new Insets(10, 20, 5, 25);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        gridbag.setConstraints(KontoLoeschenButton, constraints);
        add(KontoLoeschenButton);
        KontoLoeschenButton.addActionListener(this);

        // --- Statusfeld
        StatusLabel = new StatusLine(
                "                                                                      ");
        constraints.insets = new Insets(20, 20, 20, 20);
        gridbag.setConstraints(StatusLabel, constraints);
        add(StatusLabel);

        /************ Schliessknopf *************/
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        pack();
    }

    /**
     * Hier werden die eigentlichen Aktionen veranlasst.
     */
    public void actionPerformed(ActionEvent e) {
        StatusLabel.setInfoText("");

        if (e.getSource() == NeuesKontoButton) {
            if (BankTextField.getText().trim().length() == 0) {
                StatusLabel.setErrorText("Keine Bank angegeben!");
            } else if (OwnerTextField.getText().length() == 0) {
                StatusLabel.setErrorText("Kein Kontoinhaber angegeben!");
            } else {
                String kontoIDneu = null;
                String accountOwner = OwnerTextField.getText();

                /*--------------------------------------
                 * Neues Konto einrichten lassen
                 * -------------------------------------
                 */
                try {
                    ObjectBroker ob = ObjectBroker.getBroker(host, port);
                    NameService ns = ob.getNameService();
                    Manager manager = (Manager) ns.resolve(BankTextField
                            .getText());
                    kontoIDneu = manager.createAccount(accountOwner);
                    StatusLabel.setInfoText("Neues konto mit ID " + kontoIDneu
                            + " für " + accountOwner + " eingerichtet");
                    KontoTextFieldNeu.setText(kontoIDneu); // ins Kontofeld
                                                           // eintragen
                } catch (ConnectException e1) {
                    JOptionPane.showMessageDialog(myGUI_, e1.getClass() + ": "
                            + e1.getMessage());
                    StatusLabel.setInfoText("Neues konto mit ID " + kontoIDneu
                            + " für " + accountOwner
                            + " konnte nicht eingerichtet werden");
                    e1.printStackTrace();
                } catch (UnknownHostException e1) {
                    JOptionPane.showMessageDialog(myGUI_, e1.getClass() + ": "
                            + e1.getMessage());
                    StatusLabel.setInfoText("Neues konto mit ID " + kontoIDneu
                            + " für " + accountOwner
                            + " konnte nicht eingerichtet werden");
                    e1.printStackTrace();
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(myGUI_, e1.getClass() + ": "
                            + e1.getMessage());
                    StatusLabel.setInfoText("Neues konto mit ID " + kontoIDneu
                            + " für " + accountOwner
                            + " konnte nicht eingerichtet werden");
                    e1.printStackTrace();
                } catch (RemoteException e1) {
                    JOptionPane.showMessageDialog(myGUI_, e1.getClass() + ": "
                            + e1.getMessage());
                    e1.printStackTrace();
                }
            }
        } else if (e.getSource() == KontoLoeschenButton) {
            if (BankTextField.getText().trim().length() == 0) {
                StatusLabel.setErrorText("Keine Bank angegeben!");
            } else if (KontoTextFieldDelete.getText().trim().length() == 0) {
                StatusLabel.setErrorText("Kein Konto angegeben!");
            } else {
                String kontoID = KontoTextFieldDelete.getText();

                /*--------------------------------------
                 * Konto loeschen lassen
                 * -------------------------------------
                 */

                try {
                    NameService ns = ObjectBroker.getBroker(host, port)
                            .getNameService();
                    Manager manager = (Manager) ns.resolve(BankTextField
                            .getText());
                    if (manager.removeAccount(kontoID)) {
                        StatusLabel.setInfoText("Konto mit ID " + kontoID
                                + " gelöscht");
                        KontoTextFieldDelete.setText("");
                    } else {
                        StatusLabel.setInfoText("Konto mit ID " + kontoID
                                + " nicht gefunden.");
                    }
                } catch (UnknownHostException e1) {
                    JOptionPane.showMessageDialog(myGUI_, e.getClass() + ": "
                            + e1.getMessage());
                    e1.printStackTrace();
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(myGUI_, e.getClass() + ": "
                            + e1.getMessage());
                    e1.printStackTrace();
                } catch (RemoteException e1) {
                    JOptionPane.showMessageDialog(myGUI_, e.getClass() + ": "
                            + e1.getMessage());
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        if (args.length > 0 && !args[0].equals("--help")) {
            Filiale kundenDienst = new Filiale();
            myGUI_ = kundenDienst;
            kundenDienst.setVisible(true);
            host = args[0];
            port = Integer.parseInt(args[1]);
        } else {
            System.err
                    .println("Usage: java filiale.Filiale <name-service-host> <name-service-port>");
        }
    }

}
