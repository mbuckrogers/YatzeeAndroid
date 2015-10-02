package thelf.ch.yatzee.domain;

public class DiceEye implements Comparable<DiceEye>{
	
	private int x, y, radius;


	
	public DiceEye(int x, int y, int radius) {
		super();
		this.x = x;
		this.y = y;
		this.radius = radius;
	}
	
	
	
	public boolean isContained(DiceEye other) {
		return false; // return isWithinDistance(other, this.radius) || other.isWithinDistance(this, other.getRadius());
	}
	
	public boolean isWithinDistance(DiceEye other, double reach) {
		if(this.equals(other)) { return true; }
		
		return distance(other) <= reach;
	}
	

	
	public int distance(DiceEye other) {
		return (int) Math.sqrt(Math.pow(x - other.x, 2) + (Math.pow(y - other.y, 2)));
	}

	@Override
	public String toString() {
		return "DiceEye [x=" + x + ", y=" + y + ", radius=" + radius + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + radius;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DiceEye other = (DiceEye) obj;
		if (radius != other.radius)
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}


	public int getRadius() {
		return radius;
	}


	public void setRadius(int radius) {
		this.radius = radius;
	}



	@Override
	public int compareTo(DiceEye o) {
		return hashCode() - o.hashCode();
	}
	
	
	

}
