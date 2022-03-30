package UML_Editor;

public class Vector{
  private int x, y, z;
  Vector( ){ Vector( 0, 0, 0 ); }
  Vector( int x, int y ){ Vector( x, y, 0 ); }
  Vector( int x, int y, int z ){ this.x = x; this.y = y; this.z = z; }

  public void set( int x, int y ){ set( x, y, 0 ); }
  public void set( int x, int y, int z ){ this.x = x; this.y = y; this.z = z; }
}

