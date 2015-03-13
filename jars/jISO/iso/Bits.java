package iso;

import java.util.BitSet;

public class Bits extends Apeiron {
  
  public final static Bits any=new Bits();
  
  private int top;
  
  private final BitSet bits;
  
  private Bits(){
    this.bits=new BitSet();
    this.top=-1;
  }
  
  private Bits(Bits other){
    this.bits=(BitSet)other.bits.clone();
    this.top=other.top;
  }
  
  public Bits copy() {
    return new Bits(this);
  }
  
  public boolean e_() {
    return top<0;
  }
  
  public boolean o_() {
    return top>=0&&!bits.get(top);
  }
  
  public Bits e() {
    return new Bits();
  }
  
  public Bits i() {
    top++;
    bits.set(top,true);
    return this;
  }
  
  public Bits o() {
    top++;
    bits.set(top,false);
    return this;
  }
  
  public Bits r() {
    if(top<0)
      return null;
    bits.set(top,false); // optional clean-up
    top--;
    return this;
  }
  
}
