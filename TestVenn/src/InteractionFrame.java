import javax.swing.*;
public class InteractionFrame extends JFrame {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1161518433100689027L;

	InteractionFrame() {
		super("Interaction Panel");//creating instance of JFrame  
        
		JButton b=new JButton("click");//creating instance of JButton  
		b.setBounds(130,100,100, 40);//x axis, y axis, width, height  
		
		 
		//JScrollbar b=new JButton("click");
		add(b);//adding button in JFrame  
		// f.add(b,BorderLayout.NORTH);
		          
		setSize(290,600);//400 width and 500 height  
		setLayout(null);//using no layout managers  
		setVisible(true);//making the frame visible  
		
		
	        
	        
	}
	

}
