
import java.util.LinkedList;

public enum ObjectType {

  TIGER         ("creature"),
  ELEPHANT      ("creature"),
  RABBIT        ("creature"),
  BANANA        ("food"),
  MANGO         ("food"),
  SWORD         ("inventory"),
  FLAMETHROWER  ("inventory"),
  DAGGER        ("inventory"),
  MIRROR        ("other");

  public final LinkedList<VerbType> availableGameplays = new LinkedList<VerbType>();

  ObjectType(String subtype) {
    switch (subtype) {
      case "creature":
        this.availableGameplays.add(VerbType.KILL);
      case "food":
        this.availableGameplays.add(VerbType.EAT);
        break;
      case "inventory":
        this.availableGameplays.add(VerbType.TAKE);
        this.availableGameplays.add(VerbType.DROP);
        //break;
      case "other":
        this.availableGameplays.add(VerbType.EXAMINE);
        break;
    }
  }

}
