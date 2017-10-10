
import java.util.*;

public class Map {

	private ArrayList<LinkedList<Integer>> locations = new ArrayList<LinkedList<Integer>>();;
	private ArrayList<Object> objects = new ArrayList<Object>();

  private final int GAMESPACE_LENGTH;
	private int curr_X;
	private int curr_Y;

	public Map(int GAMESPACE_LENGTH, int START_X, int START_Y) {
    for (int i = 0; i < GAMESPACE_LENGTH * GAMESPACE_LENGTH; i++) {
			LinkedList<Integer> list = new LinkedList<Integer>();
			this.locations.add(list);
		}
    this.GAMESPACE_LENGTH = GAMESPACE_LENGTH;
		this.curr_X = START_X;
		this.curr_Y = START_Y;
		addStartingObjects();
	}

	private void addStartingObjects() {
		addObjectAtCoordinates(new Object(ObjectType.TIGER), 6, 6);
		addObjectAtCoordinates(new Object(ObjectType.DAGGER), 4, 5);
	}

  public void addObjectAtCoordinates(Object obj, int X, int Y) {
		int location = X * GAMESPACE_LENGTH + Y;
		objects.add(obj);
		(locations.get(location)).add(obj.getID());
	}

	public void addObject(Object obj) {
		int current_location = curr_X * GAMESPACE_LENGTH + curr_Y;
		(objects.get(obj.getID())).activateObject();
		(locations.get(current_location)).add(obj.getID());
	}

  public LinkedList<Object> getObjectsAtCurrentLocation() {
		int current_location = curr_X * GAMESPACE_LENGTH + curr_Y;
		LinkedList<Integer> ids = locations.get(current_location);
		LinkedList<Object> ret = new LinkedList<Object>();
		for (int i = 0; i < ids.size(); i++) {
			int id = ids.get(i);
			ret.add(objects.get(id));
		}
		return ret;
	}

  public void removeObject(Object obj) {
		int current_location = curr_X * GAMESPACE_LENGTH + curr_Y;
		(objects.get(obj.getID())).deactivateObject();
		if (!obj.isCreature()) {
			(locations.get(current_location)).removeFirstOccurrence(obj.getID());
		}
  }

  public void printLocationDescription() {
    System.out.println("You are surrounded by thick tropical vegetation.");
		LinkedList<Object> objects = getObjectsAtCurrentLocation();
		boolean deadobject = false;

		for (Object o : objects) { // to orient the player, dead bodies don't disappear
			if (!o.isActive()) {
				System.out.format("Here lies a dead %s.%n", o.getType().toString());
				objects.remove(o);
				deadobject = true;
			}
		}

		if (objects.size() == 1) {
			if (deadobject) {
				System.out.format("There is also a ");
			}
			else {
				System.out.format("Here, there is a ");
			}
			System.out.format("%s.%n", (objects.pop()).getType().toString());
		}
		if (objects.size() > 1) {
			if (deadobject) {
				System.out.format("There are also:");
			}
			else {
				System.out.format("Here, there are:");
			}
			for (Object o : objects) {
				System.out.format("  %s%n", o.getType().toString());
			}
		}
  }

  public void updateCurrentLocation(MotionType direction) {
		switch (direction) {
			case N:
				curr_Y = (curr_Y + 1) % GAMESPACE_LENGTH;
				System.out.println("Moving North...");
				break;
			case S:
				curr_Y = (curr_Y - 1 + GAMESPACE_LENGTH) % GAMESPACE_LENGTH;
				System.out.println("Moving South...");
				break;
			case E:
				curr_X = (curr_X + 1) % GAMESPACE_LENGTH;
				System.out.println("Moving East...");
				break;
			case W:
				curr_X = (curr_X - 1 + GAMESPACE_LENGTH) % GAMESPACE_LENGTH;
				System.out.println("Moving West...");
				break;
		}
  }

	public Object isHere(ObjectType type) {
		LinkedList<Object> objects = getObjectsAtCurrentLocation();
		for (Object o : objects) {
			if ((o.getType() == type) && (o.isActive())) {
				return o;
			}
		}
		return null;
	}
}
