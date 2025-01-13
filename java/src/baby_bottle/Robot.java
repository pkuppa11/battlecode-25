package baby_bottle;

import battlecode.common.*;

import java.util.Random;

public abstract class Robot {
    public RobotController rc;
    public BugNav nav;
    public final Random rng;
    public int H, W;
    public final Direction[] dirs = {
            Direction.NORTH,
            Direction.NORTHEAST,
            Direction.EAST,
            Direction.SOUTHEAST,
            Direction.SOUTH,
            Direction.SOUTHWEST,
            Direction.WEST,
            Direction.NORTHWEST,
    };
    public Direction prevMove;

    public Robot(RobotController rc) throws GameActionException {
        this.rc = rc;
        nav = new BugNav(rc);
        rng = new Random(rc.getRoundNum());
        H = rc.getMapHeight();
        W = rc.getMapWidth();

        RobotInfo[] nearbyRobots = rc.senseNearbyRobots();
        for(RobotInfo r : nearbyRobots) {
            if(r.type == UnitType.LEVEL_ONE_DEFENSE_TOWER || r.type == UnitType.LEVEL_TWO_DEFENSE_TOWER || r.type == UnitType.LEVEL_THREE_DEFENSE_TOWER ||
               r.type == UnitType.LEVEL_ONE_MONEY_TOWER || r.type == UnitType.LEVEL_TWO_MONEY_TOWER || r.type == UnitType.LEVEL_THREE_MONEY_TOWER ||
               r.type == UnitType.LEVEL_ONE_PAINT_TOWER || r.type == UnitType.LEVEL_TWO_PAINT_TOWER || r.type == UnitType.LEVEL_THREE_PAINT_TOWER) {

                prevMove = r.location.directionTo(rc.getLocation());
                break;
            }

        }
    }

    public abstract void run() throws GameActionException;
    public void init() throws GameActionException {}
    public void end() throws GameActionException {}
}
