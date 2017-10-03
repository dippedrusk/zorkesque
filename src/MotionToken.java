
public class MotionToken extends Token {

  private final MotionType motiontype;

	public MotionToken(MotionType motiontype) {
    super(TokenType.MOTION);
		this.motiontype = motiontype;
	}

  public MotionType getMotionType() {
    return motiontype;
  }

  public static Token tokenizableAsMotion(String s) {
    for (MotionType m : MotionType.values()) {
      if ((m.name()).equals(s)) {
        return new MotionToken(m);
      }
    }
    return null;
  }

}
