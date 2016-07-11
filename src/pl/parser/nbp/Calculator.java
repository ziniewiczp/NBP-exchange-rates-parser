package pl.parser.nbp;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Calculator {
    public String average;
    public String deviation;

	public Calculator() {
		this.average = "";
		this.deviation = "";
	}
	
	private static BigDecimal sqrt(BigDecimal value) {
		double temp = value.doubleValue();
		temp = Math.sqrt(temp);

		return new BigDecimal(temp);
	}
	
	public void calculate(ParsedData data) {
		BigDecimal average;
		BigDecimal deviation;
		BigDecimal deviationAverage;
		
		average = data.averageSum.divide(data.counter);
		average = average.setScale(4, RoundingMode.CEILING);
		
		deviationAverage = data.deviationSum.divide(data.counter);
		
		// resetting unused variable to reuse it in the next step
		data.deviationSum = BigDecimal.ZERO;
		
		for(BigDecimal elem : data.deviationList) {
		    BigDecimal temp = elem.subtract(deviationAverage);
			
		    data.deviationSum = data.deviationSum.add(temp.multiply(temp));
		}
		
		deviation = sqrt(data.deviationSum.divide(data.counter));
		deviation = deviation.setScale(4, RoundingMode.CEILING);
	
		this.average = average.toString();
		this.deviation = deviation.toString();
	}
}