package glub;

import glub.*;
import battlecode.common.*;

public class BugNav {
    public RobotController rc;

    public BugNav(RobotController rc) {
        this.rc = rc;
    }

    // stolen from sample code for AI Coliseum
    // todo: prefer going through your own paint

    boolean rotateRight = true;
    MapLocation lastObstacleFound = null;
    int minDistToEnemy = Util.INF;
    MapLocation prevTarget = null;

    public void moveTo(MapLocation target) throws GameActionException {
        if (target == null) return;

        if (prevTarget == null || !target.equals(prevTarget)) resetPathfinding();

        MapLocation myLoc = rc.getLocation();
        int d = myLoc.distanceSquaredTo(target);
        if (d <= minDistToEnemy) resetPathfinding();

        prevTarget = target;
        minDistToEnemy = Math.min(d, minDistToEnemy);

        Direction dir = myLoc.directionTo(target);
        if (lastObstacleFound != null) dir = myLoc.directionTo(lastObstacleFound);

        if (rc.canMove(dir)) resetPathfinding();

        for (int i = 0; i < 16; ++i){
            if (rc.canMove(dir)){
                rc.move(dir);
                return;
            }

            MapLocation newLoc = myLoc.add(dir);
            if (rc.onTheMap(newLoc)) rotateRight = !rotateRight;
            else lastObstacleFound = myLoc.add(dir);
            if (rotateRight) dir = dir.rotateRight();
            else dir = dir.rotateLeft();
        }

        if (rc.canMove(dir)) rc.move(dir);
    }

    void resetPathfinding() {
        lastObstacleFound = null;
        minDistToEnemy = Util.INF;
    }
}
