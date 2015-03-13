package iso;

/**
 * a class for experimenting with instances of various Apeiron subclasses
 */
public class Be {
  
  /**
   * Nat prototype
   */
  public static final Apeiron nat=Nat.any;
  
  /**
   * Bits prototype
   */
  public static final Apeiron bits=Bits.any;
  
  public static final Apeiron binTree=BinTree.any;
  
  /**
   * printer to standard output
   */
  public static void pp(Object s) {
    System.out.println(s);
  }
  
}