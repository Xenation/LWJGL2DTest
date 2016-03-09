package toolbox;

public class Vector2i {
	
	public int x;
	public int y;
	
	public Vector2i() {
		this.x = 0;
		this.y = 0;
	}
	public Vector2i(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void set(Vector2i vec) {
		this.x = vec.x;
		this.y = vec.y;
	}
	
	public void set(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2i clone() {
		return new Vector2i(x, y);
	}
	
	public boolean equals(Vector2i vec) {
		return this.x == vec.x && this.y == vec.y;
	}
	
	public boolean equals(int x, int y) {
		return this.x == x && this.y == y;
	}
	
}
