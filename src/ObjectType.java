
import java.util.LinkedList;

public enum ObjectType {

  TIGER         ("creature"),
  ELEPHANT      ("creature"),
  RABBIT        ("creature"),
  SWORD         ("inventory"),
  FLAMETHROWER  ("inventory"),
  DAGGER        ("inventory"),
  MIRROR        ("other");

  public final LinkedList<VerbType> availableGameplays = new LinkedList<VerbType>();

  ObjectType(String subtype) {
    switch (subtype) {
      case "creature":
        this.availableGameplays.add(VerbType.KILL);
        this.availableGameplays.add(VerbType.CARESS);
      case "inventory":
        this.availableGameplays.add(VerbType.TAKE);
        this.availableGameplays.add(VerbType.DROP);
      case "other":
        this.availableGameplays.add(VerbType.EXAMINE);
    }
  }

}
