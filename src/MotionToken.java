
public class MotionToken extends GameplayToken {

  private final MotionType motiontype;

	public MotionToken(MotionType motiontype) {
    super(GameplayTokenType.MOTION);
		this.motiontype = motiontype;
	}

  @Override
  public String getToken() {
    return motiontype.name();
  }

}
