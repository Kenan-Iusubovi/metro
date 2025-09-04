package service;

import route.Route;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class FareCalculator {

    private static BigDecimal baseCost = new BigDecimal("2.50");

    public BigDecimal calculate(Route route) {
        if (route == null || route.getPath() == null)
            throw new IllegalArgumentException("route");

        int hops = route.getPath().length <= 1 ? 1 : route.getPath().length - 1;
        BigDecimal variable = new BigDecimal(hops).multiply(new BigDecimal("0.40"));

        return baseCost.add(variable).setScale(2, RoundingMode.HALF_UP);
    }

    public static BigDecimal getBaseCost() {
        return baseCost;
    }

    public static void setBaseCost(BigDecimal base) {
        if (base == null || base.signum() < 0)
            throw new IllegalArgumentException("baseCost");
        baseCost = base;
    }
}
