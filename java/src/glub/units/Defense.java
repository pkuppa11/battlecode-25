package glub.units;

import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import glub.Robot;

public class Defense extends Robot {
    public int lvl;

    public Defense(RobotController rc, int lvl) throws GameActionException {
        super(rc);
        this.lvl = lvl;
    }

    public void run() throws GameActionException {}
}
