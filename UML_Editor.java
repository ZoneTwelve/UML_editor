import java.io.File;
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

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.*;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;





// import mouse adapter
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UML_Editor{
  private int selectedFeature = 0;

  private JFrame frame;
  private JPanel headPanel = new JPanel(), 
                 leftPanel = new JPanel(), 
                 rightPanel = new JPanel();
  private Graphics2D g2d;
  private String AsstesRoot = "Assets/";
  private Mouse mouse = new Mouse( ), pmouse = new Mouse( );
  private featureButton[] featureBtns = {
    new featureButton( AsstesRoot + "Arrow_pointer.png" ),
    new featureButton( AsstesRoot + "Association_line.png" ),
    new featureButton( AsstesRoot + "Generalization_line.png" ),
    new featureButton( AsstesRoot + "Composition_line.png" ),
    new featureButton( AsstesRoot + "Class.png" ),
    new featureButton( AsstesRoot + "Use_case.png" )
  };
  private BufferedImage assetsImage[];
  private String ImageResourcePath[] = {
    AsstesRoot + "Arrow_pointer.png",
    AsstesRoot + "Association_line.png",
    AsstesRoot + "Generalization_line.png",
    AsstesRoot + "Composition_line.png",
    AsstesRoot + "Class.png",
    AsstesRoot + "Use_case.png"
  };


  public UML_Editor( ){
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

    headPanel.setBounds(0, 0, 800, 50);
    leftPanel.setBounds(0, 100, 400, 550);
    rightPanel.setBounds(400, 150, 400, 550);

    headPanel.setBackground(new Color( 49, 49, 49 ));
    leftPanel.setBackground(new Color( 71, 71, 71 ));
    rightPanel.setBackground(new Color( 31, 31, 31 ));

    headPanel.setPreferredSize(new Dimension(800, 50));
    leftPanel.setPreferredSize(new Dimension(200, 550));
    rightPanel.setPreferredSize(new Dimension(600, 550));

    // set right panel location
    rightPanel.setLayout(new BorderLayout());
    

    // // head

    // // left
    
    
    for( int i = 0 ; i < featureBtns.length ; i++ ){
      featureBtns[i].id = i;
      featureBtns[i].setBounds(0, i * 50, 180, 50);
      featureBtns[i].setBorder(new LineBorder(Color.BLACK));
      featureBtns[i].setOpaque(true);
      featureBtns[i].highlight( selectedFeature == i );
      featureBtns[i].setPreferredSize(new Dimension(180, 50));
      featureBtns[i].setHorizontalAlignment(JLabel.CENTER);
      featureBtns[i].setVisible(true);
      // feature button been clicked
      featureBtns[i].addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
          featureButton btn = (featureButton)e.getSource();
          selectFeature( btn.id );
        }
      });
      leftPanel.add(featureBtns[i]);
    }    

    // right
    rightPanel.addMouseListener( new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        g2d.setColor( Color.BLACK );
        int redis = (int) Math.sqrt( Math.pow( 50, 2 ) ) / 2;
        int x = e.getX( ) - redis,
            y = e.getY( ) - redis;

        g2d.fillOval( x, y, redis * 2, redis * 2 );

      }
    });
    // disable frame resize
    frame.setResizable(false);
    // frame.addComponentListener(new java.awt.event.ComponentAdapter() {
    //   public void componentResized(java.awt.event.ComponentEvent evt) {
    //     int width = frame.getWidth( ),
    //         height = frame.getHeight( );
    //     // headPanel.setPreferredSize(new Dimension(width, 50));
    //     // leftPanel.setPreferredSize(new Dimension(200, height));
    //     // rightPanel.setPreferredSize(new Dimension(width, height));
    //   }
    // });
    
    // load image resource
    assetsImage = new BufferedImage[ImageResourcePath.length];
    for(int i = 0 ; i < ImageResourcePath.length ; i++){
      try{
        assetsImage[i] = ImageIO.read( new File( ImageResourcePath[i] ) );
      }catch(Exception e){
        System.out.println( "Error: " + e.getMessage() );
      }
    }


    frame.add(headPanel, BorderLayout.NORTH);
    frame.add(leftPanel, BorderLayout.WEST);
    frame.add(rightPanel, BorderLayout.EAST);
    frame.pack();
    g2d = (Graphics2D)rightPanel.getGraphics();
    // Create a interval execute function
    new Thread(new Runnable() {
      @Override
      public void run() {
        while(true){
          try{
            Thread.sleep(10);
          }catch(Exception e){
            e.printStackTrace();
          }
          Point pos = rightPanel.getMousePosition( );
          if( pos != null ){
            pmouse = mouse;
            mouse.setLocation( pos );
          }
          g2d.setColor( Color.WHITE );
          g2d.fillRect( 0, 0, rightPanel.getWidth( ), rightPanel.getHeight( ) );

          loop( );

          g2d.drawImage( assetsImage[selectedFeature], mouse.x - 10, mouse.y - 10, 20, 20, null );

          headPanel.repaint();
          leftPanel.repaint();

        }
      }
    }).start();
  }

  private void loop( ){
    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
    g2d.setColor( Color.BLACK );
    int redis = (int) Math.sqrt( Math.pow( 50, 2 ) ) / 2;
    int x = mouse.x - redis,
        y = mouse.y - redis;
    g2d.fillOval( x, y, redis * 2, redis * 2 );
    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
  }
  


  public void selectFeature( int id ){
    selectedFeature = id;
    for(int i = 0 ; i < 6 ; i++){
      featureBtns[i].highlight( selectedFeature==i );
    }
  }

  public static void main(String[] args) {
    new UML_Editor( );
  }

};

class History {
  private String target; // [ Mouse, Class, Association, Generalization, Composition, UseCase ]
  // private Point position; // [ Class, Association, Generalization, Composition, UseCase ]
}

class featureButton extends JLabel{
  public int id; // I don't give a fuck
  private ImageIcon icon;
  private Color bg_color = new Color(49, 49, 49);
  private Color bg_color_defocus = new Color(79, 79, 79);

  featureButton(String path){
    icon = new ImageIcon(path);
    setIcon(icon);
  }

  @Override
  public void setBackground( Color bg ){
    super.setBackground(bg);
  }

  public void setHighlight( Color focus_color, Color defocus_color ){
    bg_color_defocus = defocus_color;
    bg_color = focus_color;
  }
  public void highlight( boolean s ){
    setBackground( s==true ? bg_color : bg_color_defocus );
  }
}

class Mouse extends Point{
  public Mouse( ){
    super(0, 0);
  }
  public Mouse( int x, int y ){
    super(x, y);
  }
}

class Item extends Point{

}