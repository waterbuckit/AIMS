package aims;

/**
 *
 * @author waterbucket
 */
public class AIMS implements Runnable{

    public static AIMS instance;
    
    public static void main(String[] args) {
        instance = new AIMS();
        instance.run();
    }

    @Override
    public void run() {
        DisplayScreen displayScreen = new DisplayScreen();
        displayScreen.guiSetUp();
    }
}
