import javax.swing.JComboBox;
//import java.awt.Dimension;
//import java.awt.Toolkit;
//import java.awt.Window;
//
//import javax.swing.JFrame;

public class Launcher {

	public static void main(String[] args) {

		DisplayFrame f=new DisplayFrame();
		InteractionFrame f2=new InteractionFrame();
		f2.setLocation(f.getX()-f.getWidth()/2, f.getY());
		
		String[] shapeStrings = { "Circle", "Square", "Triangle"};
		JComboBox<Object> shapeList = new JComboBox<Object>(shapeStrings);
	    shapeList.setSelectedIndex(0);
	    f2.add(shapeList);
	}

}
