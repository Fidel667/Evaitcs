public class DiscountCalculator {

    // 1. Interface
    public interface DiscountStrategy {
        double apply(double price);
    }

    // 2. Concrete implementations
    public static class StudentDiscount implements DiscountStrategy {
        @Override
        public double apply(double price) {
            return price * 0.80;
        }
    }

    public static class TeacherDiscount implements DiscountStrategy {
        @Override
        public double apply(double price) {
            return price * 0.70;
        }
    }

    public static class NoDiscount implements DiscountStrategy {
        @Override
        public double apply(double price) {
            return price;
        }
    }

    // 3. Calculator logic
    private final DiscountStrategy strategy;

    public DiscountCalculator(DiscountStrategy strategy) {
        this.strategy = strategy;
    }

    public double calculate(double price) {
        return strategy.apply(price);
    }
}