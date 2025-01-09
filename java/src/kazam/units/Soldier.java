package kazam.units;

import battlecode.common.*;
import kazam.Bunny;
import kazam.Robot;

public class Soldier extends Bunny {
    MapLocation spawnLoc;
    MapLocation[] enemyLocs;
    int eI = 0;
    int vis = 0;

    public Soldier(RobotController rc) throws GameActionException {
        super(rc);
        spawnLoc = rc.getLocation();
        enemyLocs = new MapLocation[] {
                        new MapLocation(W - spawnLoc.x, H - spawnLoc.y),
                        new MapLocation(W - spawnLoc.x, spawnLoc.y),
                        new MapLocation(spawnLoc.x, spawnLoc.y) };
        eI = rng.nextInt(enemyLocs.length);
    }

    public void micro() throws GameActionException {
        MapLocation enemyLoc = enemyLocs[eI];
        boolean clear = false;
        if (rc.isActionReady()) {
            clear = true;
            if (rc.canSenseLocation(enemyLoc)) vis++;
            for (RobotInfo enemy : rc.senseNearbyRobots()) {
                if (enemy.getTeam() == rc.getTeam()) continue;
                if (rc.canAttack(enemy.getLocation())) {
                    enemyLoc = enemy.getLocation();
                    rc.attack(enemy.getLocation());
                    clear = false;
                }
            }
        }

        if (clear && vis > 10) {
            if (rc.canAttack(rc.getLocation())) rc.attack(rc.getLocation());
            eI = (eI+1) % enemyLocs.length;
            vis = 0;
        }

        nav.moveTo(enemyLoc);
    }
}