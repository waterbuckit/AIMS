package aims;

/**
 *
 * @author waterbucket
 */
public class AIMS implements Runnable {

    static AIMS instance;
    

    public static void main(String[] args) {
        instance = new AIMS();
    }

    @Override
    public void run() {
        DisplayScreen displayScreen = new DisplayScreen();
        displayScreen.guiSetUp();
    }
}
