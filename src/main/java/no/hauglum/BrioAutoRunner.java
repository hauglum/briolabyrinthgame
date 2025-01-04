package no.hauglum;

import com.yoctopuce.YoctoAPI.YAPI;
import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.YoctoAPI.YServo;

import java.util.Scanner;

public class BrioAutoRunner implements BrioRunner{
    private static YServo servo1;
    private static YServo servo2;
    private static int levelEv;

    private static volatile boolean running = true; // Volatile to ensure thread visibility

    public void run(){
        Thread inputThread = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Press 'Q' to stop the task.");

            while (running) {
                String input = scanner.nextLine().toUpperCase();
                if ("Q".equals(input)) {
                    running = false;
                    System.out.println("Stopping the task...");
                }
            }

            scanner.close();
        });
        inputThread.start();

        doRun();

        try {
            inputThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Input thread interrupted.");
        }

    }

    private static void doRun() {

        while (running) {
            try {
                try {
                    // setup the API to use local VirtualHub
                    YAPI.RegisterHub("127.0.0.1");
                } catch (YAPI_Exception ex) {
                    System.out.println("Cannot contact VirtualHub on 127.0.0.1 (" + ex.getLocalizedMessage() + ")");
                    System.out.println("Ensure that the VirtualHub application is running");
                    System.exit(1);
                }


                YServo tmp = YServo.FirstServo();
                if (tmp == null) {
                    System.out.println("No module connected (check USB cable)");
                    System.exit(1);
                }
                String serial = null;
                try {
                    serial = tmp.module().get_serialNumber();
                } catch (YAPI_Exception ex) {
                    System.out.println("No module connected (check USB cable)");
                    System.exit(1);
                }

                servo1 = YServo.FindServo(serial + ".servo1");
                servo2 = YServo.FindServo(serial + ".servo2");
                try {
                    levelEv = -480;
                    int msDuration = 500;
                    servo1.move(-600, msDuration);//imediat transition
                    servo2.move(-600, msDuration);//imediat transition
                    System.out.println("sett på plass kula");
                    YAPI.Sleep(2000);


                    //calibration starts
                    servo2.move(-150, 900);
                    for (int i = 0; i < 4; i++) {
                        int delta = 80;
                        int target = -800 + (i * delta);

                        servo1.move(target, msDuration);
                        YAPI.Sleep(1000);

                        servo1.move(target - delta, msDuration);
                        YAPI.Sleep(1000);
                        System.out.println("Kula balanserer på " + target + "?");
                    }
                    YAPI.Sleep(2000);
                    //calibration end

                    east(msDuration);
                    north(msDuration);
                    south(msDuration);
                    north(msDuration);
                    //hole 8 is passed
                    softNorth(msDuration);
                    west(msDuration);
                    shortEast(msDuration);
                    west(msDuration);
                    east(msDuration);
                    west(msDuration);
                    south(msDuration);
                    east(msDuration);
//            softSouth( msDuration);
                    west(msDuration);
                    east(msDuration);
                    shortWest(msDuration);
                    north(msDuration);
                    south(msDuration);
                    north(msDuration);
                    east(msDuration);
                    west(msDuration);
                    south(msDuration);
                    north(msDuration);
                    east(msDuration);


                } catch (YAPI_Exception e) {
                    System.out.println("Module not connected (check identification and USB cable)");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Long-running task interrupted.");
            }
        }
    }

    private static void isInterupted() throws InterruptedException {
        if(!running){
            throw new InterruptedException();
        }
    }

    private static void north(int msDuration) throws YAPI_Exception, InterruptedException {
        isInterupted();
        YAPI.Sleep(2000);
        servo2.move(900 + levelEv, msDuration);//imediat transition            YAPI.Sleep(2000);
    }

    private static void softNorth(int msDuration) throws YAPI_Exception, InterruptedException {
        isInterupted();
        YAPI.Sleep(2000);
        servo2.move(700 + levelEv, msDuration);//imediat transition            YAPI.Sleep(2000);
    }

    private static void shortEast(int msDuration) throws YAPI_Exception, InterruptedException {
        isInterupted();
        YAPI.Sleep(500);
        servo1.move(400 + levelEv, msDuration);//imediat transition
    }
    private static void east(int msDuration) throws YAPI_Exception, InterruptedException {
        isInterupted();
        YAPI.Sleep(2000);
        servo1.move(400 + levelEv, msDuration);//imediat transition
    }

    private static void west(int msDuration) throws YAPI_Exception, InterruptedException {
        isInterupted();
        YAPI.Sleep(2000);
        servo1.move(-500 + levelEv, msDuration);//imediat transition
    }
    private static void shortWest(int msDuration) throws YAPI_Exception, InterruptedException {
        isInterupted();
        YAPI.Sleep(800);
        servo1.move(-500 + levelEv, msDuration);//imediat transition
    }

    private static void south(int msDuration) throws YAPI_Exception, InterruptedException {
        isInterupted();
        YAPI.Sleep(2000);
        servo2.move(-800, msDuration);//imediat transition
    }
    private static void softSouth( int msDuration) throws YAPI_Exception, InterruptedException {
        isInterupted();
        YAPI.Sleep(2000);
        servo2.move(-400, msDuration);//imediat transition
    }
}
