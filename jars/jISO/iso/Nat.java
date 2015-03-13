package iso;

import java.math.BigInteger;

public class Nat extends Apeiron {
  public final static Nat any=new Nat();
  
  private BigInteger big;
  
  public Nat(){
    this.big=BigInteger.ZERO;
  }
  
  public Nat(Nat other){
    this.big=other.big;
  }
  
  public Nat(String s){
    this.big=new BigInteger(s);
  }
  
  public Nat copy() {
    return new Nat(this);
  }
  
  public boolean e_() {
    return BigInteger.ZERO.equals(this.big);
  }
  
  public boolean o_() {
    return big.testBit(0);
  }
  
  public Nat e() {
    return new Nat();
  }
  
  public Nat i() {
    this.big=this.big.add(BigInteger.ONE).shiftLeft(1);
    return this;
  }
  
  public Nat o() {
    this.big=this.big.shiftLeft(1).add(BigInteger.ONE);
    return this;
  }
  
  public Nat r() {
    if(e_())
      return null;
    this.big=this.big.subtract(BigInteger.ONE).shiftRight(1);
    return this;
  }
  
  public String toString() {
    return this.big.toString();
  }
}
