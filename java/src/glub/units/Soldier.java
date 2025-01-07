package glub.units;

import battlecode.common.MapLocation;
import glub.Robot;
import battlecode.common.GameActionException;
import battlecode.common.RobotController;

public class Soldier extends Robot {
    public final MapLocation target = new MapLocation(rng.nextInt(W), rng.nextInt(H));

    public Soldier(RobotController rc) throws GameActionException {
        super(rc);
        rc.setIndicatorDot(target, 255, 0, 0);
    }

    public void run() throws GameActionException {
        nav.moveTo(target);
    }
}
