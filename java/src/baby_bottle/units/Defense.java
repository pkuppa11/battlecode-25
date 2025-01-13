package baby_bottle.units;

import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import baby_bottle.Tower;

public class Defense extends Tower {
    public Defense(RobotController rc, int lvl) throws GameActionException {
        super(rc, lvl);
    }

    public void micro() throws GameActionException {}
}
