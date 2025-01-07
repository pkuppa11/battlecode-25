package glub.units;

import glub.Robot;
import battlecode.common.GameActionException;
import battlecode.common.RobotController;

public class Defense extends Robot {
    public int lvl;

    public Defense(RobotController rc, int lvl) throws GameActionException {
        super(rc);
        this.lvl = lvl;
    }

    public void run() throws GameActionException {}
}
