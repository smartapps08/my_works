package bd.org.basis.sensordemo;

public class AccelData {
	private double x, y, z;
	private long timeStamp;

	public AccelData(double x, double y, double z, long time) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		this.timeStamp = time;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

}
