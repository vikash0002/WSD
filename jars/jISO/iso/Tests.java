package iso;

public class Tests extends Be {
  
  public static void test1() {
    pp("test1");
    Apeiron n=bits.e().i().i().i().o().i().o().i().i().o().o().o(); // reversed
    // !!!
    pp("ORD: "+n+":"+n);
    n=n.reverse();
    pp("REV: "+n+":"+n);
    pp("---");
    n=bits.fromBitString("000111");
    pp(n+"---->"+n.toBitString());
    n=n.reverse();
    pp(n+"---->"+n.toBitString());
    pp("");
  }
  
  public static void test2() {
    pp("test2");
    Apeiron n=bits.e();
    pp(n);
    for(long i=0;i<16;i++) {
      n=bits.fromLong(i);
      pp(i+"-->"+n+":"+n.toBitString());
      pp(i+",succ="+n.s()+",residue="+n);
      pp(i+",pred="+n.p()+",residue="+n);
    }
    pp("");
  }
  
  /*
  ISO> mapM_ print $ map (\x->(x,(as bits nat x))) [0..14]
  (0,[])
  (1,[0])
  (2,[1])
  (3,[0,0])
  (4,[1,0])
  (5,[0,1])
  (6,[1,1])
  (7,[0,0,0])
  (8,[1,0,0])
  (9,[0,1,0])
  (10,[1,1,0])
  (11,[0,0,1])
  (12,[1,0,1])
  (13,[0,1,1])
  (14,[1,1,1])
  */

  public static void test3() {
    pp("test3");
    for(long i=0;i<15;i++) {
      Apeiron n=bits.fromLong(i);
      pp(i+"-->"+n+"="+n);
    }
    pp(bits.fromBitString("111000").toString());
    pp(bits.fromLong(123456789));
    pp("");
  }
  
  public static void test4() {
    pp("test4");
    Apeiron x=bits.e();
    for(long i=0;i<15;i++) {
      Apeiron n=bits.fromLong(i);
      pp(i+"-->"+n);
      pp(i+"==>"+x);
      pp("");
      x.s();
    }
    
    for(long i=15;i>0;i--) {
      Apeiron n=bits.fromLong(i);
      pp(i+"-->"+n);
      pp(i+"==>"+x);
      pp("");
      x.p();
    }
    pp("");
  }
  
  public static void test5() {
    pp("test5 ok");
    for(long i=1;i<21;i++) {
      Apeiron z=nat.fromLong(i);
      // pp("z="+z.copy().toBig()+",hf="+z.db().toBig());
      
      Apeiron x=z.copy().h();
      Apeiron y=z.copy().t();
      Apeiron w=y.copy().c(x.copy());
      pp(x+"+"+y+"="+w);
      
    }
    pp("");
  }
  
  /*
  ((0,0),1)
  ((1,0),2)
  ((0,1),3)
  ((2,0),4)
  ((0,2),5)
  ((1,1),6)
  ((0,3),7)
  ((3,0),8)
  ((0,4),9)
  ((1,2),10)
  ((0,5),11)
  ((2,1),12)
  ((0,6),13)
  ((1,3),14)
  ((0,7),15)
  ((4,0),16)
  ((0,8),17)
  ((1,4),18)
  ((0,9),19)
  ((2,2),20)
   */

  public static void test6() {
    pp("test6");
    for(long i=1;i<21;i++) {
      Apeiron z=bits.fromLong(i);
      // pp("z="+z.copy().toBig()+",hf="+z.db().toBig());
      
      Apeiron y=z.copy();
      Apeiron x=y.h();
      Apeiron w=y.copy().c(x.copy());
      pp(x+"+"+y+"="+w);
      
    }
    pp("");
  }
  
  public static void test7() {
    pp("test8");
    for(long i=1;i<16;i++) {
      Apeiron b=bits.fromLong(i);
      Apeiron n=nat.cIngest(b.copy());
      Apeiron bb=bits.cIngest(n.copy());
      pp(b+"+"+n+"="+bb+",eq:"+n.eq(bb));
    }
    pp("");
  }
  
  public static void test8() {
    pp("test8");
    for(long i=1;i<16;i++) {
      Apeiron b=bits.fromLong(i);
      Apeiron bb=nat.cIngest(b.copy());
      bb.s();
      pp(b+"="+bb+",eq:"+b.eq(bb));
    }
    pp("");
  }
  
  public static void test9() {
    pp("test9");
    Apeiron b=binTree.e();
    for(long i=0;i<18;i++) {
      // b=binTree.fromLong(i);
      b=b.s();
      // pp(i+"->"+b);
      pp(i+"->"+b+"="+b.toConsString());
    }
    pp("");
    // nat.e().p();
  }
  
  public static void test10() {
    pp("test10");
    Apeiron x=binTree.fromLong(1000000000000000000L);
    Apeiron y=binTree.fromLong(2000000000000000000L);
    pp("x="+x+",y="+y);
    Apeiron z=x.a(y);
    pp("x="+x+",y="+y);
    pp("z="+z);
    pp("list="+z.copy().toList());
    pp("mset="+z.copy().toMSet());
    pp("set="+z.copy().toSet());
    pp("z0="+z);
    pp("digraph="+z.copy().toDigraph());
    pp("z1="+z);
    pp("DAG="+z.toDAG());
    pp("z2="+z);
    pp("-----");
  }
  
  public static void test11() {
    pp("test11");
    Apeiron x=binTree.fromLong(47L);
    Apeiron y=binTree.fromLong(10L);
    pp("x="+x+",y="+y);
    Apeiron z=x.d(y);
    pp("x="+x+",y="+y);
    pp("z="+z);
    pp("-----");
  }
}