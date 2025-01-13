package baby_bottle.units;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import baby_bottle.Bunny;
import baby_bottle.Robot;

public class Mopper extends Bunny {
    public Mopper(RobotController rc) throws GameActionException {
        super(rc);
    }

    public void micro() throws GameActionException {
        Direction target = null;
        for (Direction dir : dirs) {
            if (rc.canMopSwing(dir)) {
                target = compare(target, dir);
            }
        }
        if (target != null) rc.mopSwing(target);
    }

    public Direction compare(Direction a, Direction b) {
        return b;
    }
}