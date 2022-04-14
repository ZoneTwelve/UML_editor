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
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.border.*;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;


import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Vector;

// import mouse adapter
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UML_Editor{
  private int selectedFeature = 0;
  private int selectedMenuopt =-1;
  private int selectedItem    =-1;

  private JFrame frame;
  private JPanel headPanel = new JPanel(), 
                 leftPanel = new JPanel(), 
                 rightPanel = new JPanel();
  private Graphics2D g2d;
  private String AsstesRoot = "Assets/";
  private Mouse mouse = new Mouse( ), pmouse = new Mouse( );
  private featureButton[] featureBtns = {
    new featureButton( "select",         AsstesRoot + "Arrow_pointer.png" ),
    new featureButton( "association",    AsstesRoot + "Association_line.png" ),
    new featureButton( "generalization", AsstesRoot + "Generalization_line.png" ),
    new featureButton( "composition",    AsstesRoot + "Composition_line.png" ),
    new featureButton( "class",          AsstesRoot + "Class.png" ),
    new featureButton( "usecase",        AsstesRoot + "Use_case.png" )
  };
  private featureButton[] menuBtns = {
    new featureButton( "new",  AsstesRoot + "menu_new.png" ),
    new featureButton( "redo", AsstesRoot + "menu_redo.png" ),
    new featureButton( "undo", AsstesRoot + "menu_undo.png" ),
    new featureButton( "delete", AsstesRoot + "menu_del.png" )
    // new featureButton( AsstesRoot + "Open.png" ),
    // new featureButton( AsstesRoot + "Save.png" ),
    // new featureButton( AsstesRoot + "Save_as.png" ),
    // new featureButton( AsstesRoot + "Print.png" ),
    // new featureButton( AsstesRoot + "Exit.png" )
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
  private Vector<String> history = new Vector<String>( );
  private int historyIndex = -1;


  public UML_Editor( ){
    JFrame frame = new JFrame();
    frame.setTitle("UML Editor (by. ZoneTwelve) (https://blog.zonetwelve.io)");
    frame.setSize(new Dimension(800, 600));
    frame.setUndecorated(false);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
    frame.setMinimumSize(new Dimension(600, 400));
    frame.setPreferredSize(new Dimension(800, 600));
    // set frame relative location
    // frame.setLocationRelativeTo(null);
    // frame.setLocation(0, 0);

    frame.addKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        // turn key to number
        int key = e.getKeyChar();
        if(key >= '0' && key <= (char)featureBtns.length + '0' ){ // Number key
          selectFeature( key - '0' - 1 );
        }
        // menu button
        char[] menuBtnKey = { 'n', 'r', 'u', 'd' };
        for(int i = 0; i < menuBtnKey.length; i++){
          if(key == menuBtnKey[i]){
            selectMenu( i );
          }
        }
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
    

    // head panel
    // append button to head panel
    for(int i = 0; i < menuBtns.length; i++){
      menuBtns[i].id = i;
      menuBtns[i].setPreferredSize(new Dimension(64, 32));
      menuBtns[i].setHighlightBorder(new LineBorder(new Color( 255, 255, 255 ), 1), new LineBorder(new Color( 255, 255, 255 ), 0));
      menuBtns[i].setHighlight( new Color( 107, 107, 107 ), new Color( 49, 49, 49 ) );
      menuBtns[i].highlight( selectedMenuopt==i );
      // menuBtns[i].setBackground(new Color( 71, 71, 71 ));
      // menuBtns[i].setForeground(new Color( 255, 255, 255 ));
      // menuBtns[i].setFocusPainted(false);
      // menuBtns[i].setContentAreaFilled(false);
      menuBtns[i].setOpaque(true);
      menuBtns[i].addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
          featureButton btn = (featureButton)e.getSource();
          selectMenu( btn.id );
        }
      });
      // set text
      // menuBtns[i].setText(String.valueOf(i + 1));
      headPanel.add(menuBtns[i]);
    }
    
    
    

    

    // left
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
    history.add( "add item" );
    historyIndex++;
  }

  private void addUseCaseItem( ){
    Item item = new Item( );
    item.setLocation( mouse.x, mouse.y );
    item.setSize( 100, 50 );
    item.setType( "UseCase" );
    items.add( item );
    history.add( "add item" );
    historyIndex++;
  }

  private void featureAssociationItem( int id ){
    // Item item = items.get(id)
    boolean touch = items.get(id).touch( mouse.getLocation() );
    // get last item
    Item lastItem = items.get(items.size()-1);
    // boolean same = lastItem.getType() == "Association";
    if( touch && mouse.clicked ){
      System.out.println("Add");
      if( lastItem.follow ){
        Item item = lastItem;
        item.setFollow( false );
      }else{
        Item item = new Item( );
        item.setLocation( mouse.x, mouse.y );
        item.setSize( mouse.x, mouse.y );
        item.setType( "Association" );
        item.setFollow( true );
        items.add( item );
        history.add( "add item" );
        historyIndex++;
      }
    }
  }

  private void loop( ){
    String featureName = featureBtns[selectedFeature].name;
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
      featureBtns[selectedFeature].execClick = new Runnable( ){
        @Override
        public void run( ){
          System.out.println( featureName );
          switch( featureName ){
            case "class":
              // selectFeature( 0 );
              addClassItem( );
            break;
            case "usecase":
              // selectFeature( 0 );
              addUseCaseItem( );
            break;
          }
        }
      };
      featureBtns[selectedFeature].execClick.run();
    }

    // System.out.println( "items.length = " + items.size() );
    // for each items
    boolean first_lock = false;
    for( int i = 0 ; i < items.size( ) ; i++ ){
      final int itemid = i;
      Item item = items.get(i);
      if( item.getType() == "Association" && item.follow ){
        item.size.width = mouse.x;
        item.size.height = mouse.y;
      }
      featureBtns[selectedFeature].exec = new Runnable( ){
        @Override
        public void run( ){
          switch( featureName ){
            case "select":
              featureSelectItem( itemid );
            break;
            case "association":
              featureAssociationItem( itemid );
            break;
            case "generalization":
              featureAssociationItem( itemid );
            break;
            case "composition":
              featureAssociationItem( itemid );
            break;
          }
        }
      };
      featureBtns[selectedFeature].exec.run( );
      

      item.draw( g2d );
    }
    // g2d.fillOval( x, y, redis * 2, redis * 2 );
    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
  }

  private void featureSelectItem( int i ){
    Item item = items.get(i);
    Item itemOperan = items.get( items.size() - 1 - i );
    item.draw( g2d );
    if( item.follow ){
      item.setLocation( mouse.x - item.size.width / 2, mouse.y - item.size.height / 2 );
    }

    boolean touch = item.touch( mouse.getLocation() );
    if( touch ){
      if( mouse.clicked && selectedItem == -1 ){
        selectedItem = i;
        item.selected = true;
        item.setFollow(true);
        history.add( "select item " + i );
        historyIndex++;
        history.add( "move item from " + item.x + "," + item.y );
        historyIndex++;
      }
      if( selectedItem == i && mouse.pressed ){
        item.setFollow(true);
      }else if( !mouse.pressed ){
        item.setFollow( false );
        history.add( "move item to " + mouse.x + "," + mouse.y );
        historyIndex++;
      }
    }else{
      if( mouse.clicked && selectedItem == i ){
        selectedItem = -1;
        item.selected = false;
        history.add( "unselect item " + i );
        historyIndex++;
      }
    }
  }
  
  public void selectMenu( int id ){
    selectedMenuopt = id;
    for(int i = 0 ; i < menuBtns.length ; i++){
      menuBtns[i].highlight( selectedMenuopt==i );
    }
    switch( menuBtns[selectedMenuopt].name ){
      case "new":
        items.clear( );
        System.out.println( "new" );
      break;
      case "redo":
        // historyIndex++;
      break;
      case "undo":
        String action = history.get(historyIndex);
        System.out.println( action );
        historyIndex--;
        Pattern pattern = Pattern.compile("select item (\\d+)");
        if( action == "add item" ){
          items.remove( items.size() - 1 );
        }
        Matcher matcher = pattern.matcher(action);
        if( matcher.find() ){
          int idx = Integer.parseInt( matcher.group(1) );
          System.out.println( idx );
          // items.remove( idx );
        }
        // get id from regex /select item \d+/
        
        // if( action.indexOf )
      break;
      case "delete":
        System.out.println("Selected item id" + selectedItem);
        if( selectedItem != -1 ){
          items.remove( selectedItem );
          selectedItem = -1;
          System.out.println( "delete" );
        }
      break;
    }
    // create a new thread after 100 ms
    new Thread(new Runnable() {
      @Override
      public void run() {
        try{
          Thread.sleep(100);
        }catch(Exception e){
          e.printStackTrace();
        }
        selectedMenuopt = -1;
        for(int i = 0 ; i < menuBtns.length ; i++){
          menuBtns[i].highlight( selectedMenuopt==i );
        }
      }
    }).start();
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
  public String name;
  public int id; // I don't give a fuck
  private ImageIcon icon;
  private Color bg_color = new Color(49, 49, 49);
  private Color bg_color_defocus = new Color(79, 79, 79);

  public boolean enableBorder = false; // just don't give a fuck
  private LineBorder border_focus = new LineBorder(Color.WHITE, 1);
  private LineBorder border_defocus = new LineBorder(Color.BLACK, 1);

  public Runnable exec, execClick;

  featureButton(String _name, String path){
    name = _name;
    icon = new ImageIcon(path);
    setIcon(icon);
  }
  // @Override
  // public void setBackground( Color bg ){
  //   super.setBackground(bg);
  // }

  

  public void setHighlight( Color focus_color, Color defocus_color ){
    bg_color_defocus = defocus_color;
    bg_color = focus_color;
  }
  public void highlight( boolean s ){
    setBackground( s==true ? bg_color : bg_color_defocus );
    if( enableBorder ){
      setBorder( s==true ? border_focus : border_defocus );
    }
  }

  public void setHighlightBorder( LineBorder focus_border, LineBorder defocus_border ){
    enableBorder = true;
    border_focus = focus_border;
    border_defocus = defocus_border;
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

    // click event
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

class PloygonItem extends Item{
  public int[] x, y;
  public int n;
  public PloygonItem( int[] _x, int[] _y, int _n ){
    x = _x;
    y = _y;
    n = _n;
  }
  public void draw( Graphics2D g2d ){
    g2d.setColor( Color.WHITE );
    g2d.fillPolygon( x, y, n );
  }
}

class Item extends Point{
  private String type;
  public Size size = new Size(); // expect to implement, goto fucking public area
  public boolean follow = false; // I don't give a fuck
  public boolean selected = false; // Also I don't give a fuck, just make everything public
  // When information already public, nothing is leaked.
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
        g2d.setColor( Color.BLACK );
        g2d.drawRect( x, y, size.width, size.height );
      break;
      case "UseCase":
        g2d.setColor( Color.WHITE );
        g2d.fillOval( x, y, size.width, size.height );
        g2d.setColor( Color.BLACK );
        g2d.drawOval( x, y, size.width, size.height );
      break;
      case "Association":
        // draw line
        g2d.setColor( Color.WHITE );
        g2d.drawLine( x, y, size.width, size.height );
      break;
    }

    if( selected ){
      g2d.setColor( Color.BLACK );
      // need sub item system
      for( int i = 0 ; i < 4 ; i++ ){
        int ax = i & 0b01, ay = (i & 0b10)/2;
        g2d.fillRect( x + ax * size.width - 5, y + ay * size.height - 5, 10, 10 );
      }
    }
  }

  public boolean touch( Point p ){
    return p.x > x && p.x < x + size.width && p.y > y && p.y < y + size.height;
  }
  public void setFollow( boolean follow ){
    this.follow = follow;
  }
  public void setSize( int w, int h ){
    size.width = w;
    size.height = h;
  }
}