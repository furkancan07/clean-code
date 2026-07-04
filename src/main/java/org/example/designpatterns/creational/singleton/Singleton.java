package org.example.designpatterns.creational.singleton;
/*

Uygulama boyunca bir sınıftan yalnızca bir instance olsun.

Ne zaman kullan

Uygulama genelinde paylaşılan config/state taşıyan sınıflar
Birden fazla instance olması tutarsızlığa yol açacaksa

basic seviyede oldupu için kod örneğine çok gerek yok ama bunuda enumlarla yönetebiliriz
ama genelde spring projesi kullandığımız için aşağıdaki gibi bir design pattern tasarlamamızda gerek yok
çünkü spring varsayılan bean scope u zaten sinlgleton babuş
* */
public class Singleton {

    public static final class ConfigManager {

        private static volatile ConfigManager instance;
        private final String appName;

        private ConfigManager() {
            this.appName = "DesignPatternsDemo";
        }

        public static ConfigManager getInstance() {
            if (instance == null) {
                synchronized (ConfigManager.class) {
                    if (instance == null) {
                        instance = new ConfigManager();
                    }
                }
            }
            return instance;
        }

        public String getAppName() {
            return appName;
        }
    }
}
