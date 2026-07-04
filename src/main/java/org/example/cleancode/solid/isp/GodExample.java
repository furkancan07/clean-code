package org.example.cleancode.solid.isp;
/*
* Interface Segregation Principle  : bir sınıf, kullanmadığı metodlara bağımlı olmaya zorlanmamalı
* Yani büyük  "her şeyi yapan" interface'ler yerine küçük, amaca özel interface'ler tercih edilmeli.
*
* lsp de bellirtiğim gibi isveç çakısı gibi her işi yapan interfaceler tasarladnmadığın sürece bu 2 prensipte sıkıntı yaşamazsın
* */
public class GodExample {

    interface Workable {
        void work();
    }

    interface Eatable {
        void eat();
    }

    interface Sleepable {
        void sleep();
    }

    static class HumanWorker implements Workable, Eatable, Sleepable {
        public void work() {
            System.out.println("İnsan çalışıyor");
        }
        public void eat() {
            System.out.println("İnsan yemek yiyor");
        }
        public void sleep() {
            System.out.println("İnsan uyuyor");
        }
    }

    static class RobotWorker implements Workable {
        public void work() {
            System.out.println("Robot çalışıyor");
        }
        // eat() ve sleep() yok - zaten robota gerek yok
    }

    public static void main(String[] args) {
        Workable robot = new RobotWorker();
        robot.work(); // sorunsuz, sadece ihtiyacı olan davranışa bağımlı

        HumanWorker human = new HumanWorker();
        human.work();
        human.eat();
        human.sleep();
    }
}
