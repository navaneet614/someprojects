
public class Main {
	static Window w;

	public static void main(String[] args) {
		w = new Window();
		Screen.makeBoard();
	}
	
	public static void end(){
		Screen.clearBoard();
		w.infoRepaint();
	}
	
}
