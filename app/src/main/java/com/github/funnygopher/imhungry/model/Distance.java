package com.github.funnygopher.imhungry.model;

public enum Distance {
    NEARBY(1), AROUND_TOWN(2), ANYWHERE(3);

    private int value;

    Distance(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String getReadableString() {
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
