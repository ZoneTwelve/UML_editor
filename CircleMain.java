/*
 * Program:  CircleMain.java - Uses the CirclePanel component
 * @version 2002-00-00
 * @author Mickey Mouse
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

/////////////////////////////////////////////////////////////////// CircleMain
class CircleMain extends JFrame{
    //================================================ instance variables
    CirclePanel drawing = new CirclePanel();               // Note 1
    
    //======================================================= constructor
    CircleMain() {
        //--- Get content pane, set layout, add components
        Container content = this.getContentPane();
        content.setLayout(new BorderLayout());
        
        content.add(drawing, BorderLayout.CENTER);        // Note 2
        
        this.setTitle("Circles");     
        this.pack();       // finalize the layout
    }//end constructor
    
    //============================================================== main
    public static void main(String[] args) {
        JFrame myWindow = new CircleMain();
        myWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myWindow.setVisible(true);
    }//end main
}//endclass CircleMain
