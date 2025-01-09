package kazam;

import battlecode.common.*;

public abstract class Bunny extends Robot {
    public MapInfo ruin = null;

    public Bunny(RobotController rc) throws GameActionException {
        super(rc);
    }

    public abstract void micro() throws GameActionException;

    public void run() throws GameActionException {
        micro();
    }
}
