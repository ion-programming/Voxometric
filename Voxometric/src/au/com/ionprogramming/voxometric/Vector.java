package au.com.ionprogramming.voxometric;

import javax.management.RuntimeErrorException;

public class Vector {
	private int x;
	private int y;
	private int z;

	public Vector(int x, int y, int z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}
	@Override
	public String toString() {
		return "Vector [" + x + ", " + y + ", " + z + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Vector) {
			Vector v = (Vector) obj;
			return getX()==v.getX()
				&& getY()==v.getY()
				&& getZ()==v.getZ();
		}
		return false;
	}

	/**
	 * The distance and direction from the plane <code>x + y + z = 0</code>
	 */
	public float getCameraDistance(int angle) {
		switch (angle) {
		case 0:
			return getX() + getY() + getZ();
		case 1:
			return getX() - getY() + getZ();
		case 2:
			return - getX() - getY() + getZ();
		case 3:
			return - getX() + getY() + getZ();
		}
		throw new RuntimeErrorException(new Error("No such angle: " + angle));
	}
}