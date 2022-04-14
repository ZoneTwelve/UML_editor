import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.border.*;
import javax.swing.ImageIcon;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Vector;

import java.io.File;
import javax.imageio.ImageIO;

public class UML_Editor{
  public UML_Editor( ){
    JFrame frame = new JFrame();
    frame.setTitle("UML Editor (by. ZoneTwelve) (https://blog.zonetwelve.io)");
    frame.setSize(new Dimension(800, 600));
    frame.setUndecorated(false);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
    frame.setMinimumSize(new Dimension(600, 400));
    frame.setPreferredSize(new Dimension(800, 600));
  }

  public static void main(String[] args) {
    new UML_Editor( );
  }
};