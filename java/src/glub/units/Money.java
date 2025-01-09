package glub.units;

import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import glub.Robot;

public class Money extends Robot {
    public int lvl;

    public Money(RobotController rc, int lvl) throws GameActionException {
        super(rc);
        this.lvl = lvl;
    }

    public void run() throws GameActionException {}
}
