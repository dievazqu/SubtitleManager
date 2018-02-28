package model;

public class Time {

	long ms;

	public Time(int h, int m, int s, int ms) {
		m = m + 60 * h;
		s = s + 60 * m;
		this.ms = ms + 1000 * s;
	}
	
	public void add(int delay){
		ms += delay;
	}
	
	public long getMs() {
		return ms;
	}
	
	@Override
	public String toString() {
		if(ms>=0){
			int _ms = (int)(ms%1000);
			int s = (int)((ms/1000)%60);
			int m = (int)((ms/60000)%60);
			int h = (int)(ms/3600000);
			return String.format("%02d:%02d:%02d,%03d", h, m, s, _ms);
		}else{
			return String.format("%02d:%02d:%02d,%03d", 0, 0, 0, 0);
		}
		
	}
	
	
}
