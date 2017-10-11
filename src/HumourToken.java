
import java.util.Random;

public class HumourToken extends Token {

  private final HumourTokenType humourtype;

	public HumourToken(HumourTokenType humourtype) {
    super(TokenType.HUMOUR);
		this.humourtype = humourtype;
	}

  public static Token tokenizableAsHumour(String s) {
    for (HumourTokenType h : HumourTokenType.values()) {
      if ((h.name()).equals(s)) {
        return new HumourToken(h);
      }
    }
    return null;
  }

  public void respondToHumour() {
    Random rand = new Random();
    int option = rand.nextInt(4);

    switch (humourtype) {
      case SCREAM:
        System.out.println("AAAARRRRRRGGGGGHHHHHHHHHHHH!!!!!!");
        break;
      case FUCK:
      case SHIT:
      case DAMN:
        switch (option) {
          case 0:
            System.out.println("Such language in a high-class establishment like this!");
            break;
          case 1:
            System.out.println("Do you kiss your mother with that mouth?");
            break;
          case 2:
            System.out.println("It's not so bad. You could be dead already...");
            break;
          case 3:
            System.out.println("Tough shit, asshole.");
            break;
        }
        break;
      case HI:
      case HELLO:
        System.out.println("Hello there.");
    }
  }

}
