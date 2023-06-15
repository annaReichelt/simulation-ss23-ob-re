package src.Entities;

import desmoj.core.simulator.*;

public class Track extends Entity {

    private boolean isFree = true;
    private int trackNumber;
    private Train trainOnThisTrack = null;

    public Track(Model owner, String name, boolean showInTrace, int trackNumber) {
        super(owner, name, showInTrace);
        this.trackNumber = trackNumber;
    }

    public void setTrainOnTrack(Train train) {
        this.trainOnThisTrack = train;
    }

    public boolean isFree() {
        return isFree;
    }

    public Train getTrainOnTrack() {
        return this.trainOnThisTrack;
    }

    public void setFree(boolean bool) {
        this.isFree = bool;

        if (isFree) {
            this.trainOnThisTrack = null;
        }
    }

    public int getTrackNo() {
        return trackNumber;
    }
}
