package no.hauglum;

import com.yoctopuce.YoctoAPI.*;

/**
 * Simple example of Yoctopuce library usage
 *
 */
public class App
{
    public static void main( String[] args )
    {
        System.out.println("Yoctopuce command line example (" + YAPI.GetAPIVersion() + ")");
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
            System.out.println("IO error:" + ex.getLocalizedMessage());
            ex.printStackTrace();
        }
        YAPI.FreeAPI();
    }
}
