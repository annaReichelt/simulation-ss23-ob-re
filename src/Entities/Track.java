package src.Entities;

import desmoj.core.simulator.*;

public class Track extends Entity {

    private boolean isFree = true;
    private int trackNumber;

    public Track(Model owner, String name, boolean showInTrace, int trackNumber) {
        super(owner, name, showInTrace);
        this.trackNumber = trackNumber;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean bool) {
        this.isFree = bool;
    }

}
