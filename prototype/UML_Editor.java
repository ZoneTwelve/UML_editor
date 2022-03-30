import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.*;
import javax.swing.ImageIcon;



// import mouse adapter
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UML_Editor{
  private static int selectedFeature = 0;
  public static void main(String[] args) {
    JFrame frame = new JFrame();
    frame.setTitle("UML Editor (by. ZoneTwelve) (https://blog.zonetwelve.io)");
    frame.setSize(new Dimension(800, 600));
    frame.setUndecorated(false);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
    frame.setMinimumSize(new Dimension(600, 400));
    frame.setPreferredSize(new Dimension(800, 600));
    // create two panel, first one put on the left, second one put on the right
    // create a head panel
    JPanel headPanel = new JPanel();
    JPanel leftPanel = new JPanel();
    JPanel rightPanel = new JPanel();
    // set panels location
    headPanel.setBounds(0, 0, 800, 50);
    leftPanel.setBounds(0, 100, 400, 550);
    rightPanel.setBounds(400, 100, 400, 550);

    headPanel.setBackground(new Color( 49, 49, 49 ));
    leftPanel.setBackground(new Color( 71, 71, 71 ));
    rightPanel.setBackground(new Color( 31, 31, 31 ));

    headPanel.setPreferredSize(new Dimension(800, 50));
    leftPanel.setPreferredSize(new Dimension(200, 550));    
    rightPanel.setPreferredSize(new Dimension(600, 550));

    // head

    // left
    String AsstesRoot = "Assets/";
    featureButton[] featureBtns = {
      new featureButton( AsstesRoot + "Arrow_pointer.png" ),
      new featureButton( AsstesRoot + "Association_line.png" ),
      new featureButton( AsstesRoot + "Generalization_line.png" ),
      new featureButton( AsstesRoot + "Composition_line.png" ),
      new featureButton( AsstesRoot + "Class.png" ),
      new featureButton( AsstesRoot + "Use_case.png" )
    };
    
    for( int i = 0 ; i < 6 ; i++ ){
      featureBtns[i].id = i;
      featureBtns[i].setBounds(0, i * 50, 180, 50);
      featureBtns[i].setBorder(new LineBorder(Color.BLACK));
      featureBtns[i].setOpaque(true);
      Color selectedColor = new Color(49, 49, 49), nonSelectedColor = new Color(79, 79, 79);
      featureBtns[i].setBackground( selectedFeature==i ? selectedColor : nonSelectedColor );
      featureBtns[i].setPreferredSize(new Dimension(180, 50));
      featureBtns[i].setHorizontalAlignment(JLabel.CENTER);
      featureBtns[i].setVisible(true);
      // feature button been clicked
      featureBtns[i].addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
          featureBtns[selectedFeature].setBackground(new Color(79, 79, 79));
          selectedFeature = featureBtns[i].id;
          featureBtns[selectedFeature].setBackground(new Color(49, 49, 49));
        }
      });
      leftPanel.add(featureBtns[i]);
    }

    // add button
    // JLabel[] featureBtns = new JLabel[6];
    // for( int i = 0; i < 6 ; i++){
    // }
    
    // right
    rightPanel.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        Graphics2D g2d = (Graphics2D) rightPanel.getGraphics();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        g2d.setColor(Color.BLACK);
        int redis = (int)Math.sqrt(Math.pow(50, 2))/2;
        g2d.fillOval(e.getX() - redis, e.getY() - redis, 50, 50);
        headPanel.repaint();
        leftPanel.repaint();
      }
      // Override drag
      @Override
      public void mouseDragged(MouseEvent e) {
        Graphics2D g2d = (Graphics2D) rightPanel.getGraphics();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        g2d.setColor(Color.BLACK);
        int redis = (int)Math.sqrt(Math.pow(50, 2))/2;
        g2d.fillOval(e.getX() - redis, e.getY() - redis, 50, 50);
        headPanel.repaint();
        leftPanel.repaint();
      }
    });
    
    
    
    
    frame.addComponentListener(new java.awt.event.ComponentAdapter() {
      public void componentResized(java.awt.event.ComponentEvent evt) {
        int width = frame.getWidth( );
        headPanel.setPreferredSize(new Dimension(width, 50));
        leftPanel.setPreferredSize(new Dimension(200, frame.getHeight()));
        rightPanel.setPreferredSize(new Dimension(width-150, frame.getHeight()));
      }
    });
    

    frame.add(headPanel, BorderLayout.NORTH);
    frame.add(leftPanel, BorderLayout.WEST);
    frame.add(rightPanel, BorderLayout.EAST);
    frame.pack();
  }

};

class featureButton extends JLabel{
  public int id; // I don't give a fuck
  private ImageIcon icon;
  private Color bg_color;
  featureButton(String path){
    icon = new ImageIcon(path);
    setIcon(icon);
  }

  @Override
  public void setBackground( Color bg ){
    bg_color = bg;
    super.setBackground(bg);
  }
}