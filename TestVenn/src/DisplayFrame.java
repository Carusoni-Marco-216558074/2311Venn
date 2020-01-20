import java.awt.*;

import javax.swing.*;
public class DisplayFrame extends JFrame{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	DisplayFrame() {
	
		super("Display Panel");//creating instance of JFrame  
        
		JButton b=new JButton("click");//creating instance of JButton  
		
		//b.setBounds(130,100,100, 40);//x axis, y axis, width, height  
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		
		//JScrollbar b=new JButton("click");
		add(b);//adding button in JFrame  
		// f.add(b,BorderLayout.NORTH);
		          
		setSize(600,600);//400 width and 500 height  
		setLayout(null);//using no layout managers  
		setVisible(true);//making the frame visible  
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/2-getSize().width/2, dim.height/2-getSize().height/2);
		
		
	}
	
	

}
