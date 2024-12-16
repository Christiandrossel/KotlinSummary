import java.net.URI;

class Scratch {

  public static void main(String[] args) {
    String pfad = "C://user/picture";
    URI uri = URI.create(pfad);
    String pfad2 = uri.toString();
    System.out.println(pfad);
    System.out.println(uri);
    System.out.println(pfad2);
  }
}