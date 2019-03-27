import Carpark.*;
import config.orb_config;

import java.io.*;
import java.awt.*;
import javax.swing.*;

import org.omg.CORBA.*;
import org.omg.CosNaming.*;

public class PayStationClient extends JFrame
{
    //ORB Components / local server
    LocalServer localServer;

    //JSwing Components
    private JTextField registrationNumIn;
    private JTextField registrationNumIn2;
    private Container  cp;
    private Container secondPane;

    public PayStationClient() {
        initOrb();
        initGUIComponents();
        this.setSize(450,150);
        pack();
    }

    private void initOrb() {
        try {
            // Initialize the ORB
            ORB orb = ORB.init(orb_config.returnArgs(), null);

            // Get a reference to the Naming service
            org.omg.CORBA.Object nameServiceObj = orb.resolve_initial_references ("NameService");
            if (nameServiceObj == null) {
                return;
            }

            // Use NamingContextExt instead of NamingContext. This is
            // part of the Interoperable naming Service.
            NamingContextExt nameService = NamingContextExtHelper.narrow(nameServiceObj);
            if (nameService == null) {
                return;
            }

            // resolve the local server object reference in the Naming service
            String name = "localServer";
            localServer = LocalServerHelper.narrow(nameService.resolve_str(name));

        } catch(Exception e) {
            System.err.println("Exception");
            System.err.println(e);
        }
    }

    private void initGUIComponents() {
        setTitle("Pay Station UI");

        cp = getContentPane();
        cp.setLayout (new BorderLayout ());

        //North Panel to select topics
        JPanel jPanel1 = new JPanel();
        jPanel1.setLayout (new FlowLayout ());

        //Center Panel to view comments
        JPanel jPanel2 = new JPanel();
        jPanel2.setLayout (new GridLayout (2, 2, 5, 5));

        JLabel regLabel = new JLabel();
        regLabel.setText ("Registration Number: ");
        jPanel2.add (regLabel);

        registrationNumIn = new JTextField (12);
        registrationNumIn.setText ("");
        jPanel2.add (registrationNumIn);

        //South Panel to add comments
        JPanel jPanel3 = new JPanel();
        jPanel3.setLayout (new FlowLayout ());

        JButton vehicleEntryButton = new JButton();
        vehicleEntryButton.setText("Find Car");
        vehicleEntryButton.addActionListener (new java.awt.event.ActionListener () {
            public void actionPerformed (java.awt.event.ActionEvent evt) {
                checkRegistration(evt);
            }
        }  );

        jPanel3.add(vehicleEntryButton);

        cp.add (jPanel1, "North");
        cp.add (jPanel2, "Center");
        cp.add (jPanel3, "South");


        // Second stage / pane
        secondPane = new JPanel();
        secondPane.setLayout (new BorderLayout ());

        //North Panel to select topics
        JPanel jPanel4 = new JPanel();
        jPanel4.setLayout (new FlowLayout ());

        //Center Panel to view comments
        JPanel jPanel5 = new JPanel();
        jPanel5.setLayout (new GridLayout (2, 2, 5, 5));

        JLabel timeLabel = new JLabel();
        timeLabel.setText ("Time required: ");
        jPanel5.add (timeLabel);

        registrationNumIn2 = new JTextField (12);
        registrationNumIn2.setText ("");
        jPanel5.add (registrationNumIn2);

        //South Panel to add comments
        JPanel jPanel6 = new JPanel();
        jPanel6.setLayout (new FlowLayout ());

        JButton ticketButton = new JButton();
        ticketButton.setText("Get ticket");
        ticketButton.addActionListener (new java.awt.event.ActionListener () {
            public void actionPerformed (java.awt.event.ActionEvent evt) {
                getTicket(evt);
            }
        }  );

        jPanel6.add(ticketButton);

        secondPane.add (jPanel4, "North");
        secondPane.add (jPanel5, "Center");
        secondPane.add (jPanel6, "South");
    }

    private void checkRegistration(java.awt.event.ActionEvent evt) {
        try {
            String registration = registrationNumIn.getText();
            if (localServer.vehicle_in_car_park(registration)) {
                System.out.println("Car found");
                cp = secondPane;
            }
            System.out.println("Car tried to be found: " + registration);
        } catch(Exception e) {
            System.err.println("Exception");
            System.err.println(e);
        }
    }

    private void getTicket(java.awt.event.ActionEvent evt) {
        try {
            localServer.vehicle_in_car_park(registrationNumIn.getText());

        } catch(Exception e) {
            System.err.println("Exception");
            System.err.println(e);
        }
    }

    public static void main( String[] args ) {
        PayStationClient payStation = new PayStationClient();
        payStation.setVisible(true);
    }
}
