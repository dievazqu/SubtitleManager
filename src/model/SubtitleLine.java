package model;

public class SubtitleLine {

	String sub;
	Time time;
	public SubtitleLine(String sub, Time time) {
		super();
		this.sub = sub;
		this.time = time;
	}
	
	public long getMs(){
		return time.getMs();
	}
	
	@Override
	public String toString() {
		return sub;
	}
}
