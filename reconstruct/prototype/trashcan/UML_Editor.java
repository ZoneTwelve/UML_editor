import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

// import mouse adapter
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;



public class UML_Editor{
  public static void main(String[] args) {
    JFrame frame = new JFrame();

    frame.setSize(new Dimension(800, 600));
    frame.setUndecorated(false);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    frame.setVisible(true);
    // Override frame's paint method 
    
  }


};