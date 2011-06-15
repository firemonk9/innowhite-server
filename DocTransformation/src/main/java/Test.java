import java.io.IOException;


public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		    // Execute a command without arguments
		    String command = "C:/sharedFloder/pptx/1021/thumbs.bat";
		   
		    
		   System.err.println(command.substring(0, (command.length()-4)));
		    
//		    Process child = Runtime.getRuntime().exec(command);
//		    System.out.println(child.exitValue());
		    // Execute a command with an argument
		   // command = "ls /tmp";
		    //child = Runtime.getRuntime().exec(command);
		
	}

}
