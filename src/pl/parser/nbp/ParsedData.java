package pl.parser.nbp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ParsedData {
    public BigDecimal averageSum;
    public BigDecimal deviationSum;
    public List<BigDecimal> deviationList;
    public BigDecimal counter;

    public ParsedData() {
        this.averageSum = new BigDecimal("0.0000");
        this.deviationSum = new BigDecimal("0.0000");
        this.deviationList = new ArrayList<BigDecimal>();
    }
}
