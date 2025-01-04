package no.hauglum;

import com.yoctopuce.YoctoAPI.YAPI;
import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.YoctoAPI.YServo;

import java.util.function.Consumer;

public class BrioManualRunner implements BrioRunner{

    private static YServo servo1;
    private static YServo servo2;

    public BrioManualRunner() {

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
    }

    @Override
    public void run() {
        WASDControls wasdControls = new WASDControls();

        wasdControls.setMoveUpAction(upDown());

        wasdControls.setMoveLeftAction(leftRight());


    }

    private Consumer<Direction> upDown(){
        return new Consumer<Direction>() {

            private int position;

            {
                try {
                    position = servo2.get_position();
                    System.out.println("servo 2 at " + position );

                } catch (YAPI_Exception e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void accept(Direction dir) {
                try {
                    System.out.println("servo 2 at " + position );
                    int newval = position + (50 * dir.getVale());
                    servo2.setPosition(newval);
                    int servoMax = 1000;
                    int servoMin = -1000;
                    if(newval <= servoMax && newval >= servoMin){
                        position = newval;
                    }

                } catch (YAPI_Exception e) {
                    throw new RuntimeException(e);
                }

            }
        };
    }

    private Consumer<Direction> leftRight(){
        return new Consumer<Direction>() {

            private int position;

            {
                try {
                    position = servo1.get_position();
                    System.out.println("servo 1 at " + position );

                } catch (YAPI_Exception e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void accept(Direction dir) {
                try {
                    System.out.println("servo 1 at " + position );
                    int newval = position + (50 * dir.getVale());
                    servo1.setPosition(newval);
                    int servoMax = 1000;
                    int servoMin = -1000;
                    if(newval <= servoMax && newval >= servoMin){
                        position = newval;
                    }

                } catch (YAPI_Exception e) {
                    throw new RuntimeException(e);
                }

            }
        };
    }

}
