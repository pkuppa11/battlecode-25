package glub.units;

import glub.Robot;
import battlecode.common.MapLocation;
import battlecode.common.UnitType;
import battlecode.common.GameActionException;
import battlecode.common.RobotController;

public class Paint extends Robot {
    public int lvl;

    public Paint(RobotController rc, int lvl) throws GameActionException {
        super(rc);
        this.lvl = lvl;
    }

    public void run() throws GameActionException {
        MapLocation loc = rc.getLocation().add(dirs[rng.nextInt(dirs.length)]);
        if (rc.canBuildRobot(UnitType.SOLDIER, loc)) {
            rc.buildRobot(UnitType.SOLDIER, loc);
        }
    }
}
