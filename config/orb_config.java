package config;

public class orb_config
{
    private static String orbInitialPort = "1080";
    private static String orbInitialHost = "";
    private static String orbPort        = "";

    public static String[] returnArgs()
    {
        String[] args = { "-ORBInitialPort", orbInitialPort};
        return args;
    }
}
