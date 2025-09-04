package service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import route.Route;

public class FareCalculator {

    private static final BigDecimal BASE = new BigDecimal("2.50");

    public BigDecimal calculate(Route route) {

        if (route == null || route.getPath() == null) throw new IllegalArgumentException("route");
        int hops = route.getPath().length <= 1 ? 1 : route.getPath().length - 1;
        BigDecimal variable = new BigDecimal(hops).multiply(new BigDecimal("0.40"));
        return BASE.add(variable).setScale(2, RoundingMode.HALF_UP);
    }
}
