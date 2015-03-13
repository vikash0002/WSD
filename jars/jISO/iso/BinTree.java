package iso;

class T {
  private final T h;
  
  private final T t;
  
  T(T h,T t){
    this.h=h;
    this.t=t;
  }
  
  static final T e=null;
  
  static final boolean e_(T z) {
    return e==z;
  }
  
  static final boolean c_(T z) {
    return !e_(z);
  }
  
  static final T c(T x,T y) {
    return new T(x,y);
  }
  
  static final T h(T z) {
    return z.h;
  }
  
  static final T t(T z) {
    return z.t;
  }
  
  /*
  static final private T incHd(T x) {
    return c(s(h(x)),t(x));
  }
  
  static final private T decHd(T x) {
    return c(p(h(x)),t(x));
  }
  */

  static final T s(T z) {
    if(e_(z))
      return c(e,e);
    else if(e_(h(z))) {
      T y=s(t(z));
      return c(s(h(y)),t(y));
    } else {
      return c(e,c(p(h(z)),t(z)));
    }
  }
  
  static final T p(T z) {
    if(e_(h(z))) {
      if(e_(t(z)))
        return e;
      else {
        T v=t(z);
        return c(s(h(v)),t(v));
      }
    } else
      return c(e,p(c(p(h(z)),t(z))));
  }
  
  static final T r(T z) {
    if(e_(h(z)))
      return t(z);
    T w=p(z);
    // assert: e_(h(w))
    return t(w);
  }
  
  static final T o(T x) {
    return c(e,x);
  }
  
  static final T i(T x) {
    return s(o(x));
  }
  
  static final boolean o_(T x) {
    return c_(x)&&e_(h(x));
  }
  
  static final boolean i_(T x) {
    return !(e_(x)||o_(x));
  }
  
}

public class BinTree extends Apeiron {
  
  public static final BinTree any=new BinTree();
  
  private T bt;
  
  public BinTree(){
    this.bt=T.e;
  }
  
  private BinTree(T bt){
    this.bt=bt;
  }
  
  public boolean e_() {
    return T.e_(bt);
  }
  
  public BinTree e() {
    return new BinTree();
  }
  
  public BinTree copy() {
    BinTree other=new BinTree(this.bt);
    return other;
  }
  
  public boolean o_() {
    return(T.o_(bt));
  }
  
  public BinTree o() {
    bt=T.o(bt);
    return this;
  }
  
  public BinTree i() {
    bt=T.i(bt);
    return this;
  }
  
  public BinTree r() {
    bt=T.r(bt);
    return this;
  }
  
  /*
  public BinTree c(BinTree x) {
    this.bt=T.c(x.bt,this.bt);
    return this;
  }
  
  public BinTree h() {
    if(T.e_(bt))
      return null;
    T hd=T.h(this.bt);
    this.bt=T.t(this.bt);
    return new BinTree(hd);
  }
  
  public BinTree t() {
    if(null==h())
      return null;
    return this;
  }
  */

}
