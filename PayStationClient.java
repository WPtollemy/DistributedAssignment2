import Carpark.*;
import config.orb_config;

import java.util.*;
import java.text.SimpleDateFormat;

import java.io.*;
import java.awt.*;
import javax.swing.*;

import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;
import org.omg.CosNaming.*;

public class PayStationClient extends JFrame
{
    //ORB Components / local server
    private LocalServer localServer;
    private PayStationImpl payStationServant;
    private String location = "";

    //JSwing Components
    private JTextField registrationNumIn;
    private JTextField timeIn;
    private JLabel notifLabel;
    private Container  cp;

    public PayStationClient(String location) {
        this.location = location;
        initOrb();
        initGUIComponents();
        this.setSize(450,150);
        pack();
    }

    private void initOrb() {
        try {
            // Initialize the ORB
            ORB orb = ORB.init(orb_config.returnArgs(), null);

            // get reference to rootpoa & activate the POAManager
            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();

            // Create the local server servant object
            payStationServant = new PayStationImpl(orb, location);

            // get object reference from the servant
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(payStationServant);
            PayStation cref = PayStationHelper.narrow(ref);

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
            String name = "localServer" + location;
            localServer = LocalServerHelper.narrow(nameService.resolve_str(name));

            // bind the pay station object in the Naming service
            String payStationName = "payStation" + location;
            NameComponent[] payStation = nameService.to_name(payStationName);
            nameService.rebind(payStation, cref);

            //  wait for invocations from clients
            //orb.run();

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

        notifLabel = new JLabel();
        jPanel1.add (notifLabel);

        //Center Panel to view comments
        JPanel jPanel2 = new JPanel();
        jPanel2.setLayout (new GridLayout (2, 2, 5, 5));

        JLabel regLabel = new JLabel();
        regLabel.setText ("Registration Number: ");
        jPanel2.add (regLabel);

        registrationNumIn = new JTextField (12);
        registrationNumIn.setText ("");
        jPanel2.add (registrationNumIn);


        JLabel timeLabel = new JLabel();
        timeLabel.setText ("Time required: ");
        jPanel2.add (timeLabel);

        timeIn = new JTextField (12);
        timeIn.setText ("");
        jPanel2.add (timeIn);

        //South Panel to add comments
        JPanel jPanel3 = new JPanel();
        jPanel3.setLayout (new FlowLayout ());

        JButton vehicleEntryButton = new JButton();
        vehicleEntryButton.setText("Find Car");
        vehicleEntryButton.addActionListener (new java.awt.event.ActionListener () {
            public void actionPerformed (java.awt.event.ActionEvent evt) {
                getTicket(evt);
            }
        }  );

        jPanel3.add(vehicleEntryButton);

        cp.add (jPanel1, "North");
        cp.add (jPanel2, "Center");
        cp.add (jPanel3, "South");
    }

    private void getTicket(java.awt.event.ActionEvent evt) {
        try {
            String registration = registrationNumIn.getText();
            if (localServer.vehicle_in_car_park(registration)) {
                payStationServant.storeTicket(createTicket());
                notifLabel.setText ("Ticket Created");
            }
        } catch(Exception e) {
            System.err.println("Exception");
            System.err.println(e);
        }
    }

    private Ticket createTicket() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HHmm");
        int intTime = Integer.parseInt(simpleDateFormat.format(cal.getTime()));

        DateTime dateTimeCreated = new DateTime(intTime, cal.get(Calendar.DATE));
        int  stayTime = Integer.parseInt(timeIn.getText());
        String regNum = registrationNumIn.getText();
        
        return new Ticket(dateTimeCreated, stayTime, regNum);
    }

    public static void main( String[] args ) {
        // Find location from args if exists
        String location = "";
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-location")) {
                location = args[i+1];
            }
        }

        PayStationClient payStation = new PayStationClient(location);
        payStation.setVisible(true);
    }
}
