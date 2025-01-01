package no.hauglum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.yoctopuce.YoctoAPI.*;

import java.util.Scanner;


/**
 * Simple example of Yoctopuce library usage
 *
 */

public class App
{
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main( String[] args ) {

        System.out.println("Yoctopuce command line example (" + YAPI.GetAPIVersion() + ")");

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            displayMenu();
            char choice = scanner.nextLine().toUpperCase().charAt(0);

            switch (choice) {
                case '1':
                    optionOne();
                    break;
                case '2':
                    optionTwo();
                    break;
                case '3':
                    optionThree();
                    break;
                case 'Q':
                    System.out.println("Exiting the application. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();

    }

    private static void displayMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. List modules connected");
        System.out.println("2. Run pre made sequence for simple map");
        System.out.println("3. Manually control the ball (WASD Controls)");
        System.out.println("Q. Quit");
        System.out.print("Choose an option: ");
    }

    private static void optionOne() {
        try {
            // setup the API to use local VirtualHub
            YAPI.RegisterHub("127.0.0.1");
            System.out.println("Device list");
            YModule module = YModule.FirstModule();
            while (module != null) {
                System.out.println(module.get_serialNumber() + " (" + module.get_productName() + ")");
                module = module.nextModule();
            }
        } catch (YAPI_Exception ex) {
            logger.error("there is an error while reading the connected devices", ex);
        }
        YAPI.FreeAPI();
    }

    private static void optionTwo() {
        System.out.println("You selected Option Two.");
        // Add functionality for Option Two here
    }

    private static void optionThree() {
        System.out.println("You selected Option Three.");
        // Add functionality for Option Three here
    }
}
