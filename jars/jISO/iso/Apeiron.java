package iso;

import java.util.ArrayList;

/**
 * Specifies, and when it can, it implements the creation, destruction 
 * and transformation (morphing into something else)
 * of everything, as generically as possible.
 * 
 * The general philosophy is that things are morphed into other things
 * reversibly - and thus the originals fade away in the process.
 * Computations mimic a semi-functional style i.e. arguments are consumed
 * in a somewhat natural way - whatever is left of them is usually meaningful.
 * 
 * However, by using copy() on an argument before is passed to an operation
 * one can ensure, when needed, that the orginal argument is never mutated.
 * This provides an easy way to build wrappers that are behaving in a genuinely
 * functional style by insuring that arguments are values never mutated.
 */
abstract public class Apeiron {
  
  // Emptyness: recognizer and constructor
  
  /**
   * recognizes the Empty
   */
  abstract public boolean e_();
  
  /**
   * creates the Empty
   */
  abstract public Apeiron e();
  
  /** 
   * generic replicator: creates something with identical
   * content that it can be mutated independently
   */
  abstract public Apeiron copy();
  
  // {0,1}* bijective base-2 bitstring view the Odds and the Evens
  
  /**
   * recongnizes the Odds
   */
  abstract public boolean o_();
  
  /**
   * morphs something into an Odd
   */
  abstract public Apeiron o();
  
  /**
   * morphs something into an Even
   */
  abstract public Apeiron i();
  
  /**
   * reduces something to what it was right before
   */
  abstract public Apeiron r();
  
  /**
  * ingests "other" by chopping off its bits one at a time and
  * rebuilding it inside this with i() and o() operations
  */
  void ioIngest(Apeiron other) {
    while(!other.e_()) {
      if(other.o_())
        o();
      else
        i();
      other.r();
    }
  }
  
  /**
   * recognizes the Evens
   */
  public boolean i_() {
    return !e_()&&!o_();
  }
  
  /**
   * creates the Unit - the first thing after empty
   */
  public Apeiron u() {
    return e().o();
  }
  
  /**
   * recognizes the Unit
   */
  public boolean u_() {
    boolean ok=false;
    if(o_()) {
      ok=r().e_();
      o();
    }
    return ok;
  }
  
  /**
  * reverses something seen as built of Odds and Evens
  */
  public Apeiron reverse() {
    Apeiron other=e();
    while(!e_()) {
      if(o_())
        other.o();
      else
        other.i();
      r();
    }
    return other;
  }
  
  // a temporal view - Peano arithmetics
  
  /**
   * makes time flow one step - the Successor
   */
  public Apeiron s() {
    if(e_())
      return o();
    if(o_())
      return r().i();
    else
      // i_()
      return r().s().o();
  }
  
  /**
   * goes back in time one step, the Predecessor
   */
  public Apeiron p() {
    if(u_())
      return r();
    if(o_())
      return r().p().i();
    else
      // i_()
      return r().o();
  }
  
  /**
   * checks for equality - while consuming both
   */
  boolean eq(Apeiron other) {
    while(!e_()&&!other.e_()) {
      if(o_()&&other.i_())
        break;
      if(i_()&&other.o_())
        break;
      r();
      other.r();
    }
    return e_()&&other.e_();
  }
  
  /**
   * chopps off the half of something
   */
  public Apeiron hf() {
    return s().r();
  }
  
  /**
   * doubles something
   */
  public Apeiron db() {
    return o().p();
  }
  
  // the free magma of binary trees
  /**
   * The Cons: extends something with x as its the head of something.
   * Note that the thing itself is ingested in the process.
   */
  public Apeiron c(Apeiron x) {
    Apeiron z=this.o();
    while(!x.e_()) {
      x=x.p();
      z=z.db();
    }
    return z;
  }
  
  /**
   * chopps off the head of something
   */
  public Apeiron h() {
    if(e_())
      return null;
    Apeiron z=this;
    Apeiron x=e();
    while(z.i_()) {
      z=z.r().s();
      x=x.s();
    }
    z=z.r(); // to leave behind exactly the tail
    return x;
  }
  
  /**
   * returns what is left after chopping off the head of something
   */
  public Apeiron t() {
    h();
    return this;
  }
  
  /**
   * recongnizes something that is not empty and 
   * therefore has a head and tail
   */
  
  public boolean c_() {
    return !e_();
  }
  
  /**
  * ingests "other" by chopping deconstructing it with h() t()
  * rebuilding it inside this with i() and o() operations
  */
  Apeiron cIngest(Apeiron other) {
    if(other.e_())
      return e();
    Apeiron x=cIngest(other.h());
    return cIngest(other).c(x);
  }
  
  // convenience methods
  
  /**
   * views as a Nat
   */
  public Nat asNat() {
    Nat n=Nat.any.e();
    n.ioIngest(this.copy().reverse());
    return n;
  }
  
  /** inputs from a long
   * note that in builds and instance based on the subclass it is called from
   */
  public Apeiron fromLong(long n) {
    StringBuffer s=new StringBuffer();
    while(n>0) {
      n--;
      char c=(0==(n&1))?'0':'1';
      s.append(c);
      n=n>>1;
    }
    
    return fromBitString(s.toString());
  }
  
  /** inputs from 0,1 string representing a natural number 
   * in bijective base 2
   * note that in builds and instance based on the subclass it is called from
   */
  public Apeiron fromBitString(String s) {
    int l=s.length();
    Apeiron x=e();
    l--;
    for(int i=l;i>=0;i--) {
      char c=s.charAt(i);
      if('0'==c)
        x.o();
      else
        x.i();
    }
    return x;
  }
  
  /**
   * represents as a 0,1 string in bijective base 2
   */
  public String toBitString() {
    Apeiron other=this.copy();
    StringBuffer B=new StringBuffer();
    while(!other.e_()) {
      char c=other.o_()?'0':'1';
      B.append(c);
      other.r();
    }
    
    return B.toString();
  }
  
  /**
   * represents as parenthesized string the construction of a binary tree
   */
  public String toConsString() {
    Apeiron other=this.copy();
    // return other.ca();
    return other.cs();
  }
  
  private String ca() { // alternative, with arrows
    if(e_())
      return "e";
    String hs=h().ca();
    return "("+hs+"->"+ca()+")";
  }
  
  private String cs() {
    if(e_())
      return "E";
    String hs=h().cs();
    return "(C "+hs+" "+cs()+")";
  }
  
  /**
   * returns a String representation - as a Nat, for now
   */
  public String toString() {
    return this.asNat().toString();
  }
  
  // Arithmetic - model in C.hs
  
  public Apeiron a(Apeiron y) {
    if(y.e_())
      return this;
    if(e_())
      return y;
    if(o_()&&y.o_()) {
      return r().a(y.r()).i();
    }
    if((o_()&&y.i_())||(i_()&&y.o_())) {
      return r().a(y.r()).s().o();
    }
    // i_() i_()
    return r().a(y.r()).s().i();
  }
  
  public Apeiron d(Apeiron y) {
    if(y.e_())
      return this;
    if(copy().eq(y.copy()))
      return e();
    
    if(i_()&&y.o_()) {
      return r().d(y.r()).o();
    }
    if((o_()&&y.o_())||(i_()&&y.i_())) {
      return r().d(y.r().s()).i();
    }
    // i_() i_()
    return r().d(y.r().s()).o();
  }
  
  public ArrayList<Apeiron> toList() {
    ArrayList<Apeiron> ns=new ArrayList<Apeiron>();
    while(!e_()) {
      ns.add(h());
    }
    return ns;
  }
  
  private ArrayList<Apeiron> toMSetOrSet(boolean toSet) {
    ArrayList<Apeiron> ns=toList();
    Apeiron sum=e();
    int l=ns.size();
    for(int i=0;i<l;i++) {
      sum=sum.a(ns.get(i));
      if(toSet)
        sum=sum.s();
      ns.set(i,sum.copy());
    }
    return ns;
  }
  
  public ArrayList<Apeiron> toMSet() {
    return toMSetOrSet(false);
  }
  
  public ArrayList<Apeiron> toSet() {
    return toMSetOrSet(true);
  }
  
  public Pair toOrdPair() {
    Apeiron x=s();
    return new Pair(x.h(),x,true);
  }
  
  public Pair toUpPair() {
    Apeiron x=s();
    Apeiron fst=x.h();
    x=x.a(fst.copy().s());
    return new Pair(fst,x,true);
  }
  
  private ArrayList<Pair> toDG(boolean acyclic) {
    ArrayList<Apeiron> ns=toSet();
    int l=ns.size();
    ArrayList<Pair> ps=new ArrayList<Pair>(l);
    for(int i=0;i<l;i++) {
      Apeiron x=ns.get(i);
      Pair p=acyclic?x.toUpPair():x.toOrdPair();
      ps.add(p);
    }
    return ps;
  }
  
  public ArrayList<Pair> toDigraph() {
    return toDG(false);
  }
  
  public ArrayList<Pair> toDAG() {
    return toDG(true);
  }
}

class Pair {
  Apeiron x;
  
  Apeiron y;
  
  boolean ord;
  
  Pair(Apeiron x,Apeiron y,boolean ord){
    this.x=x;
    this.y=y;
    this.ord=ord;
  }
  
  public String toString() {
    String arrow=ord?"->":"<->";
    return x+arrow+y;
  }
}