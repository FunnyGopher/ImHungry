package com.github.funnygopher.imhungry.model;

// A scale for determining the price of a place. Monetary values are not given,
// because how expensive something is for a user is subject to opinion

public enum Price {
    REALLY_CHEAP, CHEAP, WELL_PRICED, EXPENSIVE, REALLY_EXPENSIVE;

    public int getValue() {
        return ordinal();
    }

    public static int getValue(int index) {
        return values()[index].ordinal();
    }

    public String getName() {
        return toString();
    }

    public static String getName(int index) {
        return values()[index].toString();
    }

    @Override
    public String toString() {
        String name = name().toLowerCase();
        StringBuilder nameBuilder = new StringBuilder();
        boolean space = false;
        for(int i = 0; i < name.length(); i++) {
            if(i == 0 || space) {
                nameBuilder.append(name.substring(i, i + 1).toUpperCase());
                space = false;
            }else if(name.charAt(i) == '_') {
                nameBuilder.append(" ");
                space = true;
            } else {
                nameBuilder.append(name.charAt(i));
            }
        }

        return nameBuilder.toString();
    }
}
