package kazam;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import battlecode.common.UnitType;

public abstract class Tower extends Robot {
    public int lvl;
    public int nSo = 0, nMo = 0, nSp = 0;
    public MapLocation[] aR;

    public Tower (RobotController rc, int lvl) throws GameActionException {
        super(rc);
        this.lvl = lvl;
        aR = rc.getAllLocationsWithinRadiusSquared(rc.getLocation(), 3);
    }

    public abstract void micro() throws GameActionException;

    public void run() throws GameActionException {
        deploy();
        destroy();
        micro();
    }

    public void deploy() throws GameActionException {
        if (!rc.isActionReady()) return;

        MapLocation spawnLoc = aR[0];
        for (MapLocation loc : aR) {
            if (rc.canBuildRobot(UnitType.SOLDIER, loc) && spawnLoc.distanceSquaredTo(nav.center) < loc.distanceSquaredTo(nav.center)) {
                spawnLoc = loc;
            }
        }

        if (rc.canBuildRobot(UnitType.SOLDIER, spawnLoc)) {
            rc.buildRobot(UnitType.SOLDIER, spawnLoc);
        }
    }

    public void destroy() throws GameActionException {
        if (!rc.isActionReady()) return;

        for (MapLocation loc : aR) {
            if (rc.canAttack(loc)) {
                rc.attack(loc);
                return;
            }
        }
    }
}
