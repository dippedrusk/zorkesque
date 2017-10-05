
import java.util.*;

public class Map {

	private ArrayList<LinkedList<Integer>> locations = new ArrayList<LinkedList<Integer>>();;
  private final int GAMESPACE_LENGTH;

	public Map(int GAMESPACE_LENGTH) {
    for (int i = 0; i < GAMESPACE_LENGTH * GAMESPACE_LENGTH; i++) {
			LinkedList<Integer> list = new LinkedList<Integer>();
			this.locations.add(list);
		}
    this.GAMESPACE_LENGTH = GAMESPACE_LENGTH;
	}

  public void addObject(Object obj, int X, int Y) {
		int location = X * GAMESPACE_LENGTH + Y;
		int id = obj.getID();
		(locations.get(location)).add(id);
	}

  public LinkedList<Integer> getObjectsAtLocation(int X, int Y) {
		int location = X * GAMESPACE_LENGTH + Y;
		return locations.get(location);
	}

  public void removeObject(Object obj, int X, int Y) {
    int location = X * GAMESPACE_LENGTH + Y;
    (locations.get(location)).removeFirstOccurrence(obj.getID());
  }
}
