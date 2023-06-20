package src.Entities;

import desmoj.core.simulator.*;
import src.Config;

public class Track extends Entity {

    private boolean isFree = true;
    private int trackNumber;
    private Train trainOnThisTrack = null;

    public Track(Model owner, String name, boolean showInTrace, int trackNumber) {
        super(owner, name, Config.TRACE_TRACKS.getBool());
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

    public String toString() {
        return "Track " + trackNumber + " is free: " + isFree + " Train on track: " + ((trainOnThisTrack == null) ? "none" : trainOnThisTrack.getName());
    }
}
