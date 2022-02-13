package dev.jwaters.hacknotts21;
import javax.swing.*;
import java.awt.*;

public class Main {
    public JPanel createContentPane (){

        // As usual, we create our bottom-level panel.
        JPanel totalGUI = new JPanel();

        // This is the story we took from Wikipedia.
        String story = "The Internet Foundation Classes (IFC) were a graphics "+
                "library for Java originally developed by Netscape Communications "+
                "Corporation and first released on December 16, 1996.\n\n"+
                "On April 2, 1997, Sun Microsystems and Netscape Communications"+
                " Corporation announced their intention to combine IFC with other"+
                " technologies to form the Java Foundation Classes. In addition "+
                "to the components originally provided by IFC, Swing introduced "+
                "a mechanism that allowed the look and feel of every component "+
                "in an application to be altered without making substantial "+
                "changes to the application code. The introduction of support "+
                "for a pluggable look and feel allowed Swing components to "+
                "emulate the appearance of native components while still "+
                "retaining the benefits of platform independence. This feature "+
                "also makes it easy to have an individual application's appearance "+
                "look very different from other native programs.\n\n"+
                "Originally distributed as a separately downloadable library, "+
                "Swing has been included as part of the Java Standard Edition "+
                "since release 1.2. The Swing classes are contained in the "+
                "javax.swing package hierarchy.";

        // We create the TextArea and pass the story in as an argument.
        // We also set it to be non-editable, and the line and word wraps set to true.
        JTextArea storyArea = new JTextArea(story);
        storyArea.setEditable(false);
        storyArea.setLineWrap(true);
        storyArea.setWrapStyleWord(true);

        // We create the ScrollPane and instantiate it with the TextArea as an argument
        // along with two constants that define the behaviour of the scrollbars.
        JScrollPane area = new JScrollPane(storyArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        // We then set the preferred size of the scrollpane.
        area.setPreferredSize(new Dimension(300, 200));

        // and add it to the GUI.
        totalGUI.add(area);
        totalGUI.setOpaque(true);
        return totalGUI;
    }

    private static void createAndShowGUI() {

        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("[=] Embrace of the JScrollPane [=]");

        Main demo = new Main();
        frame.setContentPane(demo.createContentPane());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 300);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
//    public static void main(String[] args) {
//        // Set LaF to Numbus
//        try {
//            for (UIManager.LookAndFeelInfo laf : UIManager.getInstalledLookAndFeels()) {
//                if ("Numbus".equals(laf.getName())) {
//                    UIManager.setLookAndFeel(laf.getClassName());
//                }
//            }
//        } catch (Exception e) {
//            // If LaF is not available, use the default
//        }
//        JFrame frame = new JFrame("My First GUI");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(300,300);
//        JButton button1 = new JButton("Button 1");
//        JButton button2 = new JButton("Button 2");
//        frame.getContentPane().add(button1);
//        frame.getContentPane().add(button2);
//        frame.setVisible(true);
//    }
}
