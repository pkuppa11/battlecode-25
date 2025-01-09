package kazam;

import battlecode.common.Clock;
import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import kazam.units.*;

/*
 * Layout based on
 * https://github.com/IvanGeffner/BTC24
 */

public class RobotPlayer {
    public static void run(RobotController rc) throws GameActionException {
        if (rc.getRoundNum() > 250) {
            ExampleRobotPlayer.run(rc);
        }

        Robot r = getUnit(rc);
        while (true) {
            try {
                r.init();
                r.run();
                r.end();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                Clock.yield();
            }
        }
    }

    public static Robot getUnit(RobotController rc) throws GameActionException {
        switch (rc.getType()) {
            case SOLDIER:
                return new Soldier(rc);
            case SPLASHER:
                return new Splasher(rc);
            case MOPPER:
                return new Mopper(rc);
            case LEVEL_ONE_PAINT_TOWER:
                return new Paint(rc, 0);
            case LEVEL_TWO_PAINT_TOWER:
                return new Paint(rc, 1);
            case LEVEL_THREE_PAINT_TOWER:
                return new Paint(rc, 2);
            case LEVEL_ONE_MONEY_TOWER:
                return new Money(rc, 0);
            case LEVEL_TWO_MONEY_TOWER:
                return new Money(rc, 1);
            case LEVEL_THREE_MONEY_TOWER:
                return new Money(rc, 2);
            case LEVEL_ONE_DEFENSE_TOWER:
                return new Defense(rc, 0);
            case LEVEL_TWO_DEFENSE_TOWER:
                return new Defense(rc, 1);
            case LEVEL_THREE_DEFENSE_TOWER:
                return new Defense(rc, 2);
            default:
                return null;
        }
    }
}
