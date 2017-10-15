
import java.util.LinkedList;

public enum ZorkesqueObjectType {

  TIGER         ("creature"),
  ELEPHANT      ("creature"),
  RABBIT        ("creature"),
  BANANA        ("food"),
  MANGO         ("food"),
  SWORD         ("inventory"),
  FLAMETHROWER  ("inventory"),
  DAGGER        ("inventory"),
  MIRROR        ("other"),
  HEALER        ("food");

  public final LinkedList<VerbType> availableGameplays = new LinkedList<VerbType>();

  ZorkesqueObjectType(String subtype) {
    switch (subtype) {
      case "creature":
        this.availableGameplays.add(VerbType.KILL);
        this.availableGameplays.add(VerbType.EXAMINE);
      case "food":
        this.availableGameplays.add(VerbType.EAT);
        this.availableGameplays.add(VerbType.EXAMINE);
        break;
      case "inventory":
        this.availableGameplays.add(VerbType.TAKE);
        this.availableGameplays.add(VerbType.DROP);
      case "other":
        this.availableGameplays.add(VerbType.EXAMINE);
        break;
    }
  }

}
