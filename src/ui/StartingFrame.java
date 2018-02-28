package ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import manager.SubtitleManager;
import model.SubtitleLine;
import model.Time;


public class StartingFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	File subtitle;
	final JLabel infoLabel;
	final JComboBox<SubtitleLine> subtitleComboBox;
	Timer t = new Timer();
	
	public StartingFrame(){
		infoLabel = new JLabel();
		infoLabel.setBounds(30, 300, 200, 30);
		infoLabel.setFont(infoLabel.getFont().deriveFont(14f));
		add(infoLabel);
		setTitle("Administrador de Subtitulos");
		setSize(500, 380);
		setLocationRelativeTo(null);
		JLabel selectedFileLabel = new JLabel();
		selectedFileLabel.setBounds(250, 30, 500, 30);
		add(selectedFileLabel);
		
		subtitleComboBox = new JComboBox<SubtitleLine>();
		subtitleComboBox.setBounds(30, 230, 250, 30);
		add(subtitleComboBox);
		
		JButton selectFileButton = new JButton("Seleccione subtitulo");
		selectFileButton.setBounds(30, 30, 200, 30);
		selectFileButton.addActionListener( l -> {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.showDialog(null, "Cargar Subtitulos");
			if (fileChooser.getSelectedFile() != null) {
				subtitle = fileChooser.getSelectedFile();
				selectedFileLabel.setText(subtitle.getPath());
				try{
					SubtitleManager.loadSubtitleComboBox(subtitle, subtitleComboBox);
				}catch(Exception e){
					showInfoText("No se pudo abrir el subtitulo");
				}
			}
		});
		add(selectFileButton);
		JLabel delayLabel = new JLabel("Milisegundos a desfazar (negativo para adelantar): ");
		delayLabel.setBounds(30, 90, 300, 30);
		add(delayLabel);
		JTextField delayTextField = new JTextField();
		delayTextField.setBounds(350, 90, 110, 30);
		add(delayTextField);
		
		
	
		JLabel absTimeLabel = new JLabel("Tiempo del subtitulo elegido (min seg ms): ");
		absTimeLabel.setBounds(30, 200, 250, 30);
		add(absTimeLabel);
		JTextField minTextField = new JTextField();
		minTextField.setBounds(290, 220, 50, 30);
		add(minTextField);
		
		JTextField segTextField = new JTextField();
		segTextField.setBounds(350, 220, 50, 30);
		add(segTextField);
		
		JTextField msTextField = new JTextField();
		msTextField.setBounds(410, 220, 50, 30);
		add(msTextField);
		
		
		
		JButton saveFileWithDelayButton = new JButton("Guardar subtitulo con desfazaje");
		saveFileWithDelayButton.setBounds(30, 140, 250, 30);
		
		saveFileWithDelayButton.addActionListener( l -> {

			if(subtitle==null){
				showInfoText("No se eligio subtitulo");
			}else{
				try{
					int delay = Integer.parseInt(delayTextField.getText());
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.showDialog(null, "Guardar Subtitulos");
					if (fileChooser.getSelectedFile() != null) {
						try{				
							SubtitleManager.subtitleWithDelay(subtitle, fileChooser.getSelectedFile(), delay);
							showInfoText("Nuevo subtitulo guardado", 10000);
						}catch(FileNotFoundException e){
							showInfoText("No se pudo abrir el subtitulo");
						}
					}
				}catch(NumberFormatException e){
					showInfoText("Milisegundos invalidos");
				}
			}
		});
		add(saveFileWithDelayButton);
		
		JButton saveSynchronizedFileButton = new JButton("Guardar subtitulo sincronizado");
		saveSynchronizedFileButton.setBounds(30, 270, 250, 30);
		saveSynchronizedFileButton.addActionListener( l -> {
			if(subtitle==null){
				showInfoText("No se eligio subtitulo");
			}else{
				try{
					int m = Integer.parseInt(minTextField.getText());
					int s = Integer.parseInt(segTextField.getText());
					int ms = Integer.parseInt(msTextField.getText());
					if( ms<0 || ms>999 || s<0 || s>59 || m<0 || m>59){
						throw new NumberFormatException();
					}
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.showDialog(null, "Guardar Subtitulos");
					if (fileChooser.getSelectedFile() != null) {
						try{	
							SubtitleLine sl = ((SubtitleLine)subtitleComboBox.getSelectedItem());
							Time n = new Time(0,m,s,ms);
							SubtitleManager.subtitleWithDelay(subtitle, fileChooser.getSelectedFile(), (int)(n.getMs()-sl.getMs()));
							//SubtitleManager.subtitleSynchronized(subtitle, fileChooser.getSelectedFile(), m, s, ms);
							showInfoText("Nuevo subtitulo guardado", 10000);
						}catch(FileNotFoundException e){
							showInfoText("No se pudo abrir el subtitulo");
						}
					}
				}catch(NumberFormatException e){
					showInfoText("Tiempo invalido");
				}
			}
		});
		add(saveSynchronizedFileButton);
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setVisible(true);
	};
	
	public void showInfoText(String s){
		infoLabel.setText(s);
		t.cancel();
		t = new Timer();
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				infoLabel.setText("");
			}
		}, 3000);
	}
	
	public void showInfoText(String s, int delay){
		infoLabel.setText(s);
		t.cancel();
		t = new Timer();
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				infoLabel.setText("");
			}
		}, delay);
	}
	
	public static void main(String[] args) {
		new StartingFrame();
	}
}
