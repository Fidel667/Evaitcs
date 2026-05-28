public class Engine {
    private static Engine instance = new Engine();
    private Engine(){}
        public static Engine getInstance (){return instance; }
    }

    class Car{
        Engine engine;

        Car (Engine engine ) {this.engine = engine; }

    }


    class Main{
        public static void main (String [] args) {
            Engine e = Engine.getInstance();
            Car car = new Car(e);
        }
    }
