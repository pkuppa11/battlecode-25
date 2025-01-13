package baby_bottle;

import battlecode.common.*;

import java.util.ArrayList;
import java.util.Random;

public abstract class Tower extends Robot {
    public int lvl;
    public int nSo = 0, nMo = 0, nSp = 0;
    public MapLocation[] aR;
    public final Random rng;

    public Tower (RobotController rc, int lvl) throws GameActionException {
        super(rc);
        this.lvl = lvl;
        aR = rc.getAllLocationsWithinRadiusSquared(rc.getLocation(), 3);
        this.rng = new Random(rc.getRoundNum());
    }

    public abstract void micro() throws GameActionException;

    public void run() throws GameActionException {
        //defend();
        deploy();
        destroy();
        micro();
    }

    public void defend() throws GameActionException {
        RobotInfo[] nearbyRobots = rc.senseNearbyRobots();
        MapLocation myLocation = rc.getLocation();
        MapLocation nearestEnemyLocation = null;
        int mindist = -1;
        int numEnemies = 0;
        for(RobotInfo r : nearbyRobots) {
            int distance = myLocation.distanceSquaredTo(r.location);
            if (r.team != rc.getTeam()) {
                if(distance > 2)
                    if(mindist == -1 || distance < mindist) {
                        mindist = distance;
                        nearestEnemyLocation = r.location;
                    }
                numEnemies++;
            }
        }

        if (nearestEnemyLocation != null) {
            MapLocation buildLocation = nearestEnemyLocation.subtract(myLocation.directionTo(nearestEnemyLocation));
            if (rc.canBuildRobot(UnitType.SOLDIER, buildLocation))
                rc.buildRobot(UnitType.SOLDIER, buildLocation);
        }
        if(numEnemies > 0)
            rc.attack(null);
    }

    public void deploy() throws GameActionException {
        if (!rc.isActionReady()) return;

        MapLocation spawnLoc = aR[rng.nextInt(aR.length)];
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
