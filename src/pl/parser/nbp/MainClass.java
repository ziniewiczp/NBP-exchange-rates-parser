package pl.parser.nbp;

public class MainClass {
    public static void main(String[] args) throws Exception {
        Validator validator = new Validator();

        if (args.length != 3) {
            System.out.println(
                    "In order to run program you need to provide 3 arguments: currency, start date and end date.");
        } else if (validator.validateInput(args[0], args[1], args[2])) {
            try {
                Parser parser = new Parser();
                Calculator calculator = new Calculator();

                calculator.calculate(parser.parseData(args[0], args[1], args[2]));

                System.out.println(calculator.average);
                System.out.println(calculator.deviation);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
