
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class WeatherGUI {
//Globals 
	static JButton B_Reset= new JButton("Reset");
	static JButton B_Get= new JButton("Get Info");
	static JTextField TF_lat = new JTextField(10);;
	static JTextField TF_lon = new JTextField(10);;
	static JLabel L_lat = new JLabel("Latitiude: ");
	static JLabel L_lon = new JLabel("Longitude: ");
	public static JFrame MainWindow = new JFrame();
	static JTextArea TA = new JTextArea();
	public static JScrollPane SP = new JScrollPane();
	
	public static void main(String [] args){
		BuildWindow();
		Initialize();
	}

	public static void Initialize() {
		B_Get.setVisible(true); 
		B_Get.setEnabled(true);	//Enabling Get Info button
		B_Get.setVisible(true);
		B_Reset.setEnabled(true);		//Enabling Reset button	
	}
//Building the main window for program
	public static void BuildWindow() {
		MainWindow.setTitle("Get Weather Info");
		MainWindow.setBounds(200, 200, 550, 450);
		MainWindow.setLocation(220,180);
		MainWindow.setVisible(true);
		ConfigureMainWindow();
		MainWindowAction();	
	}
	
	
// Configuring main window
	public static void ConfigureMainWindow() {
		MainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MainWindow.setBounds(200, 200, 550, 450);
		MainWindow.getContentPane().setLayout(null);
		
		L_lat.setBounds(50, 25, 149, 23);
		L_lat.setVisible(true);
		MainWindow.getContentPane().add(L_lat);
		
		L_lon.setBounds(270, 25, 149, 23);
		L_lon.setVisible(true);
		MainWindow.getContentPane().add(L_lon);
		
		TF_lat.setBounds(120, 25, 120, 23);
		MainWindow.getContentPane().add(TF_lat);
		TF_lat.setVisible(true);
		TF_lat.setEditable(true);
		
		TF_lon.setBounds(340, 25, 120, 23);
		MainWindow.getContentPane().add(TF_lon);
		TF_lon.setEditable(true);
		
		B_Get.setBounds(40, 272, 95, 23);
		MainWindow.getContentPane().add(B_Get);
		
		B_Reset.setBounds(400, 272, 95, 23);
		MainWindow.getContentPane().add(B_Reset);
					
		TA.setColumns(20);
		TA.setRows(5);
		TA.setEditable(false);
		TA.setLineWrap(true);
		
		SP.setViewportView(TA);
		SP.setBounds(30, 65, 475, 180);
		MainWindow.getContentPane().add(SP);		
	}

	public static void MainWindowAction() {
		B_Get.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent E)
			{					
				Get_Action(E);
			}

			});
		B_Reset.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent E)
			{					
				Reset_Action(E);
			}

		});
	}
	
	//for getting the weather info
	public static void Get_Action(ActionEvent e) {
		if(TF_lat.getText().equals("") || TF_lat.getText().equals("")){
			JOptionPane.showMessageDialog(null, "Please fill in correct values!");
		}
		else{
			TA.setText("");
			Float lat = Float.valueOf(TF_lat.getText());
			Float lon = Float.valueOf(TF_lon.getText());
			Connection.weather(lat,lon);
		}
	}
	
	//To reset the latitude and longitude values
	public static void Reset_Action(ActionEvent e) {
		TF_lat.setText("");
		TF_lon.setText("");
			
	}
}
