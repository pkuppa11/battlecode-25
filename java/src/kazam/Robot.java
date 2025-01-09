package kazam;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.RobotController;

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

    public Robot(RobotController rc) throws GameActionException {
        this.rc = rc;
        nav = new BugNav(rc);
        rng = new Random(rc.getRoundNum());
        H = rc.getMapHeight();
        W = rc.getMapWidth();
    }

    public abstract void run() throws GameActionException;
    public void init() throws GameActionException {}
    public void end() throws GameActionException {}
}
