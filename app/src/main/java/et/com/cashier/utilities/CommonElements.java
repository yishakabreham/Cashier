package et.com.cashier.utilities;

import com.annimon.stream.Stream;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import et.com.cashier.activities.windowLogin;
import et.com.cashier.network.retrofit.pojo.Config;

public class CommonElements
{
    public static Date currentDate;
    public static void setCurrentDate()
    {

    }
    public static String currencyFormat(String amount) {
        DecimalFormat formatter = new DecimalFormat("###,###,##0.00");
        return formatter.format(Double.parseDouble(amount));
    }
    public static double discountCalculator(double unitPrice, double discountPercent)
    {
        double result = 0;
        if(discountPercent > 0)
        {
            result = unitPrice * (discountPercent / 100);
        }
        return result;
    }
    public static Config getSystemConfiguration(String attribute)
    {
        Config _config = null;
        if(windowLogin.systemConfigurations != null)
        {
            List<Config> configList = windowLogin.systemConfigurations.getConfigs();
            if(configList.size() > 0)
            {
                Config config = Stream.of(configList).filter(x -> x.getAttribute().equals(attribute)).findFirst().orElse(null);
                if(config != null) _config = config;
            }
        }
        return _config;
    }
}
