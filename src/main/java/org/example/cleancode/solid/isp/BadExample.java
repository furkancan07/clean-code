package org.example.cleancode.solid.isp;

public class BadExample {
    interface Worker {
        void work();
        void eat();
        void sleep();
    }

    static class HumanWorker implements Worker {
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

    static class RobotWorker implements Worker {
        public void work() {
            System.out.println("Robot çalışıyor");
        }
        public void eat() {
            // Robot yemek yemez ama interface zorluyor
            throw new UnsupportedOperationException("Robot yemek yiyemez!");
        }
        public void sleep() {
            // Robot uyumaz ama interface zorluyor
            throw new UnsupportedOperationException("Robot uyuyamaz!");
        }
    }

    public static void main(String[] args) {
        Worker robot = new RobotWorker();
        robot.work();
        robot.eat(); // patlar
    }
}
