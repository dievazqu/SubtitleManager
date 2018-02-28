package manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JComboBox;

import model.SubtitleLine;
import model.Time;

public class SubtitleManager {

	public static void subtitleWithDelay(File inFile, File outFile, int delay)
			throws FileNotFoundException {
		Scanner s = new Scanner(inFile);
		PrintWriter out = new PrintWriter(outFile);
		while (s.hasNextLine()) {
			out.println(s.nextLine()); // Number sequence
			String line = s.nextLine();
			Pattern p = Pattern
					.compile("(\\d{2}):(\\d{2}):(\\d{2}),(\\d{3}) --> (\\d{2}):(\\d{2}):(\\d{2}),(\\d{3})");
			Matcher m = p.matcher(line);
			// System.out.println(line);
			m.matches();
			int hi = Integer.valueOf(m.group(1));
			int mi = Integer.valueOf(m.group(2));
			int si = Integer.valueOf(m.group(3));
			int msi = Integer.valueOf(m.group(4));
			Time ti = new Time(hi, mi, si, msi);
			ti.add(delay);
			int hf = Integer.valueOf(m.group(5));
			int mf = Integer.valueOf(m.group(6));
			int sf = Integer.valueOf(m.group(7));
			int msf = Integer.valueOf(m.group(8));
			Time tf = new Time(hf, mf, sf, msf);
			tf.add(delay);
			// System.out.println("new: "+ti+" --> "+tf);
			out.println(ti + " --> " + tf);
			do {
				line = s.nextLine();
				out.println(line);
			} while (!line.equals(""));
		}
		out.close();
		s.close();
	}

	public static void subtitleSynchronized(File inFile, File outFile, int min,
			int seg, int ms) throws FileNotFoundException {
		Scanner s = new Scanner(inFile);
		s.nextLine(); // Number sequence
		String line = s.nextLine();
		Pattern p = Pattern
				.compile("(\\d{2}):(\\d{2}):(\\d{2}),(\\d{3}) --> (\\d{2}):(\\d{2}):(\\d{2}),(\\d{3})");
		Matcher m = p.matcher(line);
		m.matches();
		int hi = Integer.valueOf(m.group(1));
		int mi = Integer.valueOf(m.group(2));
		int si = Integer.valueOf(m.group(3));
		int msi = Integer.valueOf(m.group(4));
		
		Time t = new Time(hi, mi, si, msi);
		Time n = new Time(0, min, seg, ms);
		int delay = (int)(n.getMs() - t.getMs());
		s.close();
		subtitleWithDelay(inFile, outFile, delay);
	}
	
	
	public static void loadSubtitleComboBox(File inFile, JComboBox<SubtitleLine> combo) throws FileNotFoundException {
		Scanner s = new Scanner(inFile);
		for(int i=0; i<10; i++){
			s.nextLine(); // Number sequence
			String line = s.nextLine();
			Pattern p = Pattern
					.compile("(\\d{2}):(\\d{2}):(\\d{2}),(\\d{3}) --> (\\d{2}):(\\d{2}):(\\d{2}),(\\d{3})");
			Matcher m = p.matcher(line);
			m.matches();
			int hi = Integer.valueOf(m.group(1));
			int mi = Integer.valueOf(m.group(2));
			int si = Integer.valueOf(m.group(3));
			int msi = Integer.valueOf(m.group(4));
			
			Time t = new Time(hi, mi, si, msi);
			line = s.nextLine();
			combo.addItem(new SubtitleLine(line, t));
			while(!line.equals("")){
				line = s.nextLine();	
			}
		}
		s.close();
	}
}
