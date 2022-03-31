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
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.*;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;


import java.util.Vector;

// import mouse adapter
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UML_Editor{
  private int selectedFeature = 0;
  private int sleectedItem = 0;

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
  private Vector<Item> items = new Vector<Item>( );


  public UML_Editor( ){
    JFrame frame = new JFrame();
    frame.setTitle("UML Editor (by. ZoneTwelve) (https://blog.zonetwelve.io)");
    frame.setSize(new Dimension(800, 600));
    frame.setUndecorated(false);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
    frame.setMinimumSize(new Dimension(600, 400));
    frame.setPreferredSize(new Dimension(800, 600));

    frame.addKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        // turn key to number
        int key = e.getKeyChar();
        if(key >= '0' && key <= (char)featureBtns.length + '0' ){ // Number key
          selectFeature( key - '0' - 1 );
        }
        System.out.println();
      }

      @Override
      public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
      }

      @Override
      public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
      }
    });

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

      @Override
      public void mousePressed(MouseEvent e) {
        mouse.pressed = true;
      }
      @Override
      public void mouseReleased(MouseEvent e) {
        mouse.pressed = false;
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

    // Create a keyboard listener
    


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
          // canvas background
          g2d.setColor( new Color( 51, 51, 51 ) );
          g2d.fillRect( 0, 0, rightPanel.getWidth( ), rightPanel.getHeight( ) );

          loop( );

          g2d.drawImage( assetsImage[selectedFeature], mouse.x - 5, mouse.y - 5, 20, 20, null );

          headPanel.repaint();
          leftPanel.repaint();

        }
      }
    }).start();
  }

  private void addClassItem( ){
    Item item = new Item( );
    item.setLocation( mouse.x, mouse.y );
    item.setType( "Class" );
    items.add( item );
  }

  private void addUseCaseItem( ){
    Item item = new Item( );
    item.setLocation( mouse.x, mouse.y );
    item.setType( "UseCase" );
    items.add( item );
  }

  private void loop( ){
    mouse.update( );

    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
    g2d.setColor( Color.BLACK );
    int redis = (int) Math.sqrt( Math.pow( 50, 2 ) ) / 2;
    int x = mouse.x - redis,
        y = mouse.y - redis;
    if( mouse.pressed ){
      g2d.fillOval( x, y, redis * 2, redis * 2 );

    }
    if( mouse.clicked ){
      switch( selectedFeature ){
        case 0:
          System.out.println("Drag");
        break;
        case 1:
          System.out.println("Add Association");
        break;
        case 2:
          System.out.println("Add Generalization");
        break;
        case 3:
          System.out.println("Add Composite line");
        break;
        case 4:
          // selectFeature( 0 );
          addClassItem( );
          System.out.println("Add Class");
        break;
        case 5:
          // selectFeature( 0 );
          addUseCaseItem( );
          System.out.println("Add Use case");
        break;

      }
    }

    // for each items
    boolean first_lock = false;
    for( int i = 0 ; i < items.size( ) ; i++ ){
      Item item = items.get(i);
      Item itemOperan = items.get( items.size() - 1 - i );
      item.draw( g2d );
      if( item.follow ){
        item.setLocation( mouse.x - item.size.width / 2, mouse.y - item.size.height / 2 );
      }
      if( first_lock || selectedFeature != 0 ){
        continue;
      }
      item = itemOperan;
      boolean targetedObject = item.touch( mouse.getLocation( ) ) && mouse.clicked;
      if( targetedObject ){
        item.setFollow( true );
        first_lock = true;
      }else if( mouse.pressed == false ){
        item.setFollow( false );
      }
    }
    // g2d.fillOval( x, y, redis * 2, redis * 2 );
    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
  }
  


  public void selectFeature( int id ){
    selectedFeature = id;
    for(int i = 0 ; i < featureBtns.length ; i++){
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

class Size{
  int width, height;
  Size( ){
    width = 50;
    height = 50;
  }
  Size( int w, int h ){
    width = w;
    height = h;
  }
}

class Mouse extends Point{
  public boolean pressed = false;
  public boolean clicked = false;
  public boolean dragged = false;
  public boolean clickLock = false;
  public Mouse( ){
    super(0, 0);
  }
  public Mouse( int x, int y ){
    super(x, y);
  }
  public void update( ){
    if( clickLock == false && pressed == true ){
      clicked = true;
      clickLock = true;
    }else if( clickLock == true && pressed == false ){
      clickLock = false;
    }else if( clickLock == true && pressed == true ){
      clicked = false;
    }
  }
}

class Item extends Point{
  private String type;
  public Size size = new Size(); // expect to implement, goto fucking public area
  public boolean follow = false; // I don't give a fuck
  public Item( int x, int y ){
    super(x, y);
  }
  public Item( ){
    super( 0, 0 );
  }
  public Item( int x, int y, int w, int h ){
    super( x - w/2, y - h/2 );
    size.width = w;
    size.height = h;
  }
  public void setType( String type ){
    this.type = type;
  }
  public String getType( ){
    return type;
  }
  public void draw( Graphics2D g2d ){
    switch( type ){
      case "Class":
        g2d.setColor( Color.WHITE );
        g2d.fillRect( x, y, size.width, size.height );
        g2d.setColor( Color.GRAY );
        g2d.drawRect( x, y, size.width, size.height );
      break;
      case "UseCase":
        g2d.setColor( Color.WHITE );
        g2d.fillOval( x, y, size.width, size.height );
        g2d.setColor( Color.GRAY );
        g2d.drawOval( x, y, size.width, size.height );
      break;
    }
  }

  public boolean touch( Point p ){
    return p.x > x && p.x < x + size.width && p.y > y && p.y < y + size.height;
  }
  public void setFollow( boolean follow ){
    this.follow = follow;
  }
}