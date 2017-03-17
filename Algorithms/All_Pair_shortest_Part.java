
import java.util.*;
import java.lang.*;
import java.io.*;
import java.math.*;
/*
 * Author    : joney_000[let_me_start][jaswantsinghyadav007@gmail.com]
 * Algorithm : DP
 * Platform  : HackerCup
 *
 */

/*    The Main Class                */
 class A{
  
  private InputStream inputStream ;
  private OutputStream outputStream ;
  private FastReader in ;
  private PrintWriter out ;
  /*
    Overhead [Additional Temporary Strorage] but provides memory reusibility for multiple test cases.
     
  */
  
  //Critical Size Limit : 10^5 + 4
  private final int BUFFER = 100005;
  private int    tempints[] = new int[BUFFER];
  private long   templongs[] = new long[BUFFER];
  private double tempdoubles[] = new double[BUFFER];
  private char   tempchars[] = new char[BUFFER];
  private final long mod = 1000000000+7;
  private final int  INF  = Integer.MAX_VALUE / 10;
  private final long INF_L  = Long.MAX_VALUE / 100;
  
  public A(){}
  public A(boolean stdIO)throws FileNotFoundException{
    stdIO = false;
    if(stdIO){
      inputStream = System.in;
      outputStream = System.out;
    }else{
      inputStream = new FileInputStream("manic_moving.txt");
      outputStream = new FileOutputStream("output.txt");
    }
    in = new FastReader(inputStream);
    out = new PrintWriter(outputStream);
    
  }

  long w[][] = new long[101][101];
  long cost[][] = new long[101][101];
  long dp[][] = new long[5004][2];
  int u[] = new int[5004];
  int v[] = new int[5004];

  void run()throws Exception{
  
     int tests = i();
     //once();
     for(int t  = 1 ; t<= tests ; t++){
      clear();
      int n = i(); int m = i(); int k = i();
      for(int i = 1; i <= m; i++){
        int a = i(); int b = i();
        w[a][b] = w[b][a] = Math.min(w[a][b], i());
      }
      for(int i = 1; i <= k; i++){
        u[i] = i(); v[i] = i();
      }
      allPairShortestPath(n);
      long ans = 0;
      if(k == 1){
        ans = cost[1][u[1]] + cost[u[1]][v[1]];
        if(ans >= INF_L)ans = -1;
        out.write("Case #"+t+": "+ans+"\n");
        continue;
      }
      for(int i = 1; i <= k; i++){
        if(i == 1){
          dp[i][0] = cost[1][u[1]] + cost[u[1]][v[1]];
          dp[i][1] = cost[1][u[1]] + cost[u[1]][u[i+1]] + cost[u[i+1]][v[1]];
        }else if(i == k){
          dp[i][0] = Math.min(dp[i-1][0] + cost[v[i-1]][u[i]] + cost[u[i]][v[i]] , dp[i-1][1] + cost[v[i-1]][v[i]]);
          dp[i][1] = INF_L;
        }else{
          dp[i][0] = Math.min(dp[i-1][0] + cost[v[i-1]][u[i]] + cost[u[i]][v[i]] , dp[i-1][1] + cost[v[i-1]][v[i]]);
          dp[i][1] = Math.min(dp[i-1][0] + cost[v[i-1]][u[i]] + cost[u[i]][u[i+1]] + cost[u[i+1]][v[i]], dp[i-1][1] + cost[v[i-1]][u[i+1]] + cost[u[i+1]][v[i]]);
        }
        if(Math.min(dp[i][0],dp[i][1]) >= INF_L){
          ans = -1;
          break;
        }
        ans = dp[k][0];
      }
      out.write("Case #"+t+": "+ans+"\n");
    }//end tests  
  }//end run
  void allPairShortestPath(int n){
    for (int i = 1; i <= n; i++){
      for (int j = 1; j <= n; j++){
        cost[i][j] = w[i][j];
      }
    }
    for (int k = 1; k <= n; k++){
        // Pick all vertices as source one by one
        for (int i = 1; i <= n; i++){
            // Pick all vertices as destination for the
            // above picked source
            for (int j = 1; j <= n; j++){
                // If vertex k is on the shortest path from
                // i to j, then update the value of dist[i][j]
                if (cost[i][k] + cost[k][j] < cost[i][j]){
                    cost[i][j] = cost[i][k] + cost[k][j];
                }
            }
        }
    }
  }
  void clear(){
    for(int i = 0; i <= 101;i++)dp[i][0] = dp[i][1] = INF_L;
    dp[0][0] = 0;
    for(int i = 0; i <= 100; i++){
      for(int j = 0; j <= 100; j++){
        w[i][j] = INF_L;
        if(i==j)w[i][j] = 0L;
      }
    }
  }
  
//****************************** My Utilities ***********************//
  void print_r(Object...o){
        out.write("\n"+Arrays.deepToString(o)+"\n");
        out.flush();
  }
  
  int hash(String s){
    int base = 31;
    int a = 31;//base = a multiplier
    int mod = 100005;//range [0..100004]
    long val = 0;
    for(int i =  1 ; i<= s.length() ;i++){
      val += base * s.charAt(i-1);
      base = ( a * base ) % 100005;
    }
    return (int)(val % 100005) ;
  }
  
  boolean isPrime(long n){
      if(n==1)return false;
      if(n<=3)return true;
      if(n%2==0)return false;
      for(int i=2 ;i <= Math.sqrt(n); i++){
        if(n%i==0)return false;
      }
      return true;
  }
  // sieve
  int[] sieve(int n){
       
      boolean isPrime[] = new boolean[n+1];
      int p[] = new int[n+1];
      int idx = 1;
      // Put above 3 variables globle p[1..idx-1]
      
      
      Arrays.fill(isPrime,true);
      isPrime[0]=isPrime[1]=false;
      for(int i = 2 ; i<= n ; i++){
        if(isPrime[i]){
          p[idx++] = i;
          for(int j  = 2* i ; j<= n ; j+=i ){
            isPrime[j] = false;   
          }
            
        }
        
      }
      return p;
  }
  long gcd(long a , long b){
      if(b==0)return a;
      return gcd(b , a%b);
  }
  long lcm(long a , long b){
      if(a==0||b==0)return 0;
      return (a*b)/gcd(a,b);
  }
  long mulmod(long a , long b ,long mod){
      if(a==0||b==0)return 0;
      if(b==1)return a;
      long ans = mulmod(a,b/2,mod);
      ans = (ans*2)% mod;
      if(b%2==1)ans = (a + ans)% mod;
      return ans;
  }
  long pow(long a , long b ,long mod){
      if(b==0)return 1;
      if(b==1)return a;
      long ans = pow(a,b/2,mod);
      ans = (ans * ans);
      if(ans >= mod )ans %= mod;
      
      if(b%2==1)ans = (a * ans);
      if(ans >= mod )ans %= mod;
      
      return ans;
  }
  // 20*20   nCr Pascal Table
  long[][] ncrTable(){
      long ncr[][] = new long[21][21];
      for(int i=0 ;i<=20 ;i++){ncr[i][0]=1;ncr[i][i]=1;}
      for(int j=0;j<=20 ;j++){
        for(int i=j+1;i<= 20 ;i++){
            ncr[i][j] = ncr[i-1][j]+ncr[i-1][j-1];
        }
      }
    return ncr;
  }
  //*******************************I/O******************************//  
  int i()throws Exception{
    //return Integer.parseInt(br.readLine().trim());
    return in.nextInt();
  }
  int[] is(int n)throws Exception{
  //int arr[] = new int[n+1];
      for(int i=1 ; i <= n ;i++)tempints[i] = in.nextInt();  
    return tempints;
  }
  long l()throws Exception{
    return in.nextLong();
  }
  long[] ls(int n)throws Exception{
    for(int i=1 ; i <= n ;i++)templongs[i] = in.nextLong();  
    return templongs;
  }

  double d()throws Exception{
    return in.nextDouble();
  }
  double[] ds(int n)throws Exception{
      for(int i=1 ; i <= n ;i++)tempdoubles[i] = in.nextDouble();  
    return tempdoubles;
  }
  char c()throws Exception{
    return in.nextCharacter();
  }
  char[] cs(int n)throws Exception{
      for(int i=1 ; i <= n ;i++)tempchars[i] = in.nextCharacter();  
    return tempchars;
  }
  String s()throws Exception{
    return in.nextLine();
  }
  BigInteger bi()throws Exception{
    return in.nextBigInteger();
  }
//***********************I/O ENDS ***********************//
//*********************** 0.3%f [precision]***********************//
/* roundoff upto 2 digits 
   double roundOff = Math.round(a * 100.0) / 100.0;
                    or
   System.out.printf("%.2f", val);
          
*/
/*
  print upto 2 digits after decimal
  val = ((long)(val * 100.0))/100.0;
  
*/    private void closeResources(){
     out.flush();
         out.close();
         return;
  }
  public static void main(String[] args) throws java.lang.Exception{
    //let_me_start Shinch Returns 
    
 
    /*  
        // Old Reader Writer
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter out=new BufferedWriter(new OutputStreamWriter(System.out));
    BufferedReader br=new BufferedReader(new FileReader("input.txt"));
        BufferedWriter out=new BufferedWriter(new FileWriter("output.txt"));
    */   
         A driver = new A(true);
         long start =  System.currentTimeMillis();
         driver.run();
         long end =  System.currentTimeMillis();
         //out.write(" Total Time : "+(end - start)+"\n");
         driver.closeResources();
         return ;
         
  }  

}

class FastReader{

  private boolean finished = false;

  private InputStream stream;
  private byte[] buf = new byte[4*1024];
  private int curChar;
  private int numChars;
  private SpaceCharFilter filter;

  public FastReader(InputStream stream){
    this.stream = stream;
  }

  public int read(){
    if (numChars == -1){
      throw new InputMismatchException ();
    }
    if (curChar >= numChars){
      curChar = 0;
      try{
        numChars = stream.read (buf);
      } catch (IOException e){
        throw new InputMismatchException ();
      }
      if (numChars <= 0){
        return -1;
      }
    }
    return buf[curChar++];
  }

  public int peek(){
    if (numChars == -1){
      return -1;
    }
    if (curChar >= numChars){
      curChar = 0;
      try{
        numChars = stream.read (buf);
      } catch (IOException e){
        return -1;
      }
      if (numChars <= 0){
        return -1;
      }
    }
    return buf[curChar];
  }

  public int nextInt(){
    int c = read ();
    while (isSpaceChar (c))
      c = read ();
    int sgn = 1;
    if (c == '-'){
      sgn = -1;
      c = read ();
    }
    int res = 0;
    do{
      if(c==','){
        c = read();
      }
      if (c < '0' || c > '9'){
        throw new InputMismatchException ();
      }
      res *= 10;
      res += c - '0';
      c = read ();
    } while (!isSpaceChar (c));
    return res * sgn;
  }

  public long nextLong(){
    int c = read ();
    while (isSpaceChar (c))
      c = read ();
    int sgn = 1;
    if (c == '-'){
      sgn = -1;
      c = read ();
    }
    long res = 0;
    do{
      if (c < '0' || c > '9'){
        throw new InputMismatchException ();
      }
      res *= 10;
      res += c - '0';
      c = read ();
    } while (!isSpaceChar (c));
    return res * sgn;
  }

  public String nextString(){
    int c = read ();
    while (isSpaceChar (c))
      c = read ();
    StringBuilder res = new StringBuilder ();
    do{
      res.appendCodePoint (c);
      c = read ();
    } while (!isSpaceChar (c));
    return res.toString ();
  }

  public boolean isSpaceChar(int c){
    if (filter != null){
      return filter.isSpaceChar (c);
    }
    return isWhitespace (c);
  }

  public static boolean isWhitespace(int c){
    return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
  }

  private String readLine0(){
    StringBuilder buf = new StringBuilder ();
    int c = read ();
    while (c != '\n' && c != -1){
      if (c != '\r'){
        buf.appendCodePoint (c);
      }
      c = read ();
    }
    return buf.toString ();
  }

  public String nextLine(){
    String s = readLine0 ();
    while (s.trim ().length () == 0)
      s = readLine0 ();
    return s;
  }

  public String nextLine(boolean ignoreEmptyLines){
    if (ignoreEmptyLines){
      return nextLine ();
    }else{
      return readLine0 ();
    }
  }

  public BigInteger nextBigInteger(){
    try{
      return new BigInteger (nextString ());
    } catch (NumberFormatException e){
      throw new InputMismatchException ();
    }
  }

  public char nextCharacter(){
    int c = read ();
    while (isSpaceChar (c))
      c = read ();
    return (char) c;
  }

  public double nextDouble(){
    int c = read ();
    while (isSpaceChar (c))
      c = read ();
    int sgn = 1;
    if (c == '-'){
      sgn = -1;
      c = read ();
    }
    double res = 0;
    while (!isSpaceChar (c) && c != '.'){
      if (c == 'e' || c == 'E'){
        return res * Math.pow (10, nextInt ());
      }
      if (c < '0' || c > '9'){
        throw new InputMismatchException ();
      }
      res *= 10;
      res += c - '0';
      c = read ();
    }
    if (c == '.'){
      c = read ();
      double m = 1;
      while (!isSpaceChar (c)){
        if (c == 'e' || c == 'E'){
          return res * Math.pow (10, nextInt ());
        }
        if (c < '0' || c > '9'){
          throw new InputMismatchException ();
        }
        m /= 10;
        res += (c - '0') * m;
        c = read ();
      }
    }
    return res * sgn;
  }

  public boolean isExhausted(){
    int value;
    while (isSpaceChar (value = peek ()) && value != -1)
      read ();
    return value == -1;
  }

  public String next(){
    return nextString ();
  }

  public SpaceCharFilter getFilter(){
    return filter;
  }

  public void setFilter(SpaceCharFilter filter){
    this.filter = filter;
  }

  public interface SpaceCharFilter{
    public boolean isSpaceChar(int ch);
  }
}
 /******************** Point class ***********************/
 
class Point implements Comparable<Point>{

 public double x;
 public double y;

 public Point(){
  this.x = 0;
  this.y = 0;
 }
 public Point(double x , double y ){
    this.x = x;
    this.y = y;
 }
 public int compareTo(Point p){
  if(this.x < p.x)return -1;
  else if(this.x > p.x )return 1;
  else {
    if(this.y < p.y)return -1;
    else if(this.y > p.y )return 1;
    else return 0;
  
  }
 }
 public String toString(){
    return "x="+this.x+" y="+this.y;
 }
 
} 
 /******************** Pair class ***********************/
 
class Pair implements Comparable<Pair>{
 public int id;
 public long b;
 public long a;
 public long c;
 public Pair(){
  this.id = 1000;
 
  this.a = 0;
  this.b = 0;
  this.c = 0;
 }
 public Pair(int id , long a,long b , long c ){
  this.id = id;
  this.a = a;
  this.b = b;
  this.c = c;
 }
 public int compareTo(Pair p){
  if(this.a < p.a)return -1;
  else if(this.a > p.a )return 1;
  else {
    if(this.b < p.b)return -1;
    else if(this.b > p.b )return 1;
    else return 0;
  
  }
 }
 public String toString(){
  return "a="+this.a+" b="+this.b;
 }
 
} 
