package net.tbkiron.guineapigsplus.entity.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum GuineaPigVariant {
    FREDAG(0),
    PLUTO(1);

    private static final GuineaPigVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator
            .comparingInt(GuineaPigVariant::getId)).toArray(GuineaPigVariant[]::new);
    private final int id;

    GuineaPigVariant(int id){
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

    public static GuineaPigVariant byId(int id){
        return BY_ID[id % BY_ID.length];
    }
}
