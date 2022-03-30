import javax.swing.*;
import java.awt.*;

public class UML_Editor{
  public static UML_Object[999] uml_objects;
  public static void main( String[] args ){
    JFrame frame = new JFrame( "UML Editor" );
    frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    frame.setSize( 800, 600 );
    frame.setVisible( true );

    // create left and right panels then left will be balck and right will be gray
    JPanel left = new JPanel();
    // left.setBackground( Color.BLACK );
    JPanel right = new JPanel();
    // right.setBackground( Color.GRAY );
    // left take off 50% of the width and right take off 50% of the width
    left.setPreferredSize( new Dimension( frame.getWidth() / 4, frame.getHeight() ) );
    right.setPreferredSize( new Dimension( frame.getWidth() / 4 * 3, frame.getHeight() ) );
    // add left and right panels to the frame
    frame.add( left, BorderLayout.WEST );
    frame.add( right, BorderLayout.EAST );
  }
};

public class Vector{
  private int x, y, z;
  Vector( ){ Vector( 0, 0, 0 ); }
  Vector( int x, int y ){ Vector( x, y, 0 ); }
  Vector( int x, int y, int z ){ this.x = x; this.y = y; this.z = z; }

  public void set( int x, int y ){ set( x, y, 0 ); }
  public void set( int x, int y, int z ){ this.x = x; this.y = y; this.z = z; }
}

public class UML_Object{
  private String name; // type of object
  private Vector position; // position of the shape
  private Size size; // size of the shape

  public void draw( Graphics g ){
    // draw the object
  }
}