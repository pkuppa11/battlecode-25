package baby_bottle.units;

import battlecode.common.*;
import baby_bottle.Bunny;
import baby_bottle.Robot;

import java.util.ArrayList;

public class Soldier extends Bunny {
    MapLocation spawnLoc;
    MapLocation[] enemyLocs;
    int eI = 0;
    int vis = 0;
    ArrayList<MapLocation> exploreLocs = new ArrayList<MapLocation>();
    int xI;
    int xvis = 0;

    public Soldier(RobotController rc) throws GameActionException {
        super(rc);
        spawnLoc = rc.getLocation();
        enemyLocs = new MapLocation[] {
                        new MapLocation(W - spawnLoc.x, H - spawnLoc.y),
                        new MapLocation(W - spawnLoc.x, spawnLoc.y),
                        new MapLocation(spawnLoc.x, spawnLoc.y) };
        eI = rng.nextInt(enemyLocs.length);

//        exploreLocs = new MapLocation[]{
//                new MapLocation(W/4, H/4), new MapLocation(W/4, H/2), new MapLocation(W/4, 3*H/4),
//                new MapLocation(W/2, H/4), new MapLocation(W/2, H/2), new MapLocation(W/2, 3*H/4),
//                new MapLocation(3*W/4, H/4), new MapLocation(3*W/4, H/2), new MapLocation(3*W/4, 3*H/4)
//        };

        for(int i=0; i<=4; i++)
            for(int j=0; j<=4; j++)
                exploreLocs.add(new MapLocation(W*i/4, H*j/4));

        xI = rng.nextInt(exploreLocs.size());


    }

    public void micro() throws GameActionException {
        MapInfo[] nearbyTiles = rc.senseNearbyMapInfos();
        RobotInfo[] nearbyRobots = rc.senseNearbyRobots(3);

        defenseProtocol(nearbyRobots);
        ruinProtocol(nearbyTiles);
        resourceProtocol();

        exploreProtocol();

        // Move and attack randomly if no objective.
//        MapInfo[] adjacentTiles = rc.senseNearbyMapInfos(2);
//
//        ArrayList<Direction> priorityDirections1 = new ArrayList<Direction>();
//        for(MapInfo m : adjacentTiles) {
//            if(m.getPaint() == PaintType.EMPTY)
//                priorityDirections1.add(rc.getLocation().directionTo(m.getMapLocation()));
//        }
//
//        ArrayList<Direction> priorityDirections2 = new ArrayList<Direction>();
//        for(RobotInfo r : nearbyRobots) {
//            if(r.team == rc.getTeam())
//                priorityDirections2.add(r.location.directionTo(rc.getLocation()));
//        }
//
//        Direction dir;
//        if (priorityDirections1.size() > 0)
//            dir = priorityDirections1.get(rng.nextInt(priorityDirections1.size()));
//        else if (priorityDirections2.size() > 0)
//            dir = priorityDirections2.get(rng.nextInt(priorityDirections2.size()));
//        else
//            dir = dirs[rng.nextInt(dirs.length)];
//        MapLocation nextLoc = rc.getLocation().add(dir);
//        if (rc.canMove(dir)){
//            rc.move(dir);
//        }
        // Try to paint beneath us as we walk to avoid paint penalties.
        // Avoiding wasting paint by re-painting our own tiles.
        MapInfo currentTile = rc.senseMapInfo(rc.getLocation());
        if (!currentTile.getPaint().isAlly() && rc.canAttack(rc.getLocation())){
            rc.attack(rc.getLocation());
        }

    }

    public void defenseProtocol(RobotInfo[] nearbyRobots) throws GameActionException {
        boolean ownTower = false;
        boolean enemyBot = false;
        int numAllies = 0;
        for(RobotInfo r : nearbyRobots) {
            switch (r.type) {
                case SOLDIER:
                    if (r.team != rc.getTeam())
                        enemyBot = true;
                    else
                        numAllies++;
                case SPLASHER:
                    if (r.team != rc.getTeam())
                        enemyBot = true;
                    else
                        numAllies++;
                case MOPPER:
                    continue;
                case LEVEL_ONE_PAINT_TOWER:
                    if (r.team == rc.getTeam())
                        ownTower = true;
                case LEVEL_TWO_PAINT_TOWER:
                    if (r.team == rc.getTeam())
                        ownTower = true;
                case LEVEL_THREE_PAINT_TOWER:
                    if (r.team == rc.getTeam())
                        ownTower = true;
                case LEVEL_ONE_MONEY_TOWER:
                    if (r.team == rc.getTeam())
                        ownTower = true;
                case LEVEL_TWO_MONEY_TOWER:
                    if (r.team == rc.getTeam())
                        ownTower = true;
                case LEVEL_THREE_MONEY_TOWER:
                    if (r.team == rc.getTeam())
                        ownTower = true;
                case LEVEL_ONE_DEFENSE_TOWER:
                    if (r.team == rc.getTeam())
                        ownTower = true;
                case LEVEL_TWO_DEFENSE_TOWER:
                    if (r.team == rc.getTeam())
                        ownTower = true;
                case LEVEL_THREE_DEFENSE_TOWER:
                    if (r.team == rc.getTeam())
                        ownTower = true;
                default:
                    continue;
            }
        }

        MapInfo currentTile = rc.senseMapInfo(rc.getLocation());
        if (!currentTile.getPaint().isAlly() && rc.canAttack(rc.getLocation())){
            rc.attack(rc.getLocation());
        }

        //if(ownTower && enemyBot)
        //    Clock.yield();
        if (rc.getRoundNum() < 250)
            rushProtocol();
        else
            exploreProtocol();
    }

    public void exploreProtocol() throws GameActionException {
        MapLocation exploreLoc = exploreLocs.get(xI);
        boolean clear = false;
        if (rc.isActionReady()) {
            clear = true;
            if (rc.canSenseLocation(exploreLoc)) xvis++;
            for (RobotInfo enemy : rc.senseNearbyRobots()) {
                if (enemy.getTeam() == rc.getTeam()) continue;
                if (rc.canAttack(enemy.getLocation())) {
                    exploreLoc = enemy.getLocation();
                    rc.attack(enemy.getLocation());
                    clear = false;
                }
            }
        }

        if (clear && xvis >= 1) {
            if (rc.canAttack(rc.getLocation())) rc.attack(rc.getLocation());
            xI = (xI+1) % exploreLocs.size();
            xvis = 0;
        }

        nav.moveTo(exploreLoc);
    }

    public void rushProtocol() throws GameActionException {
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

    public void ruinProtocol(MapInfo[] nearbyTiles) throws GameActionException {
        MapInfo curRuin = null;
        for (MapInfo tile : nearbyTiles){
            if (tile.hasRuin()){
                curRuin = tile;
            }
        }
        if (curRuin != null){
            MapLocation targetLoc = curRuin.getMapLocation();
            Direction dir = rc.getLocation().directionTo(targetLoc);
            if (rc.canMove(dir))
                rc.move(dir);
            // Mark the pattern we need to draw to build a tower here if we haven't already.
            MapLocation shouldBeMarked = curRuin.getMapLocation().subtract(dir);
            if (rc.senseMapInfo(shouldBeMarked).getMark() == PaintType.EMPTY && rc.canMarkTowerPattern(UnitType.LEVEL_ONE_PAINT_TOWER, targetLoc)){
                rc.markTowerPattern(UnitType.LEVEL_ONE_PAINT_TOWER, targetLoc);
                System.out.println("Trying to build a tower at " + targetLoc);
            }
            // Fill in any spots in the pattern with the appropriate paint.
            for (MapInfo patternTile : rc.senseNearbyMapInfos(targetLoc, 8)){
                if (patternTile.getMark() != patternTile.getPaint() && patternTile.getMark() != PaintType.EMPTY){
                    boolean useSecondaryColor = patternTile.getMark() == PaintType.ALLY_SECONDARY;
                    if (rc.canAttack(patternTile.getMapLocation()))
                        rc.attack(patternTile.getMapLocation(), useSecondaryColor);
                }
            }
            // Complete the ruin if we can.
            if (rc.canCompleteTowerPattern(UnitType.LEVEL_ONE_PAINT_TOWER, targetLoc)){
                rc.completeTowerPattern(UnitType.LEVEL_ONE_PAINT_TOWER, targetLoc);
                rc.setTimelineMarker("Tower built", 0, 255, 0);
                System.out.println("Built a tower at " + targetLoc + "!");
            }
        }
    }

    public void resourceProtocol() throws GameActionException {

        MapLocation targetLoc = rc.getLocation();
        if (rc.senseMapInfo(targetLoc).getMark() == PaintType.EMPTY && rc.canMarkResourcePattern(targetLoc)){
            rc.markTowerPattern(UnitType.LEVEL_ONE_PAINT_TOWER, targetLoc);
            System.out.println("Trying to build resource pattern at " + targetLoc);
        }

        for (MapInfo patternTile : rc.senseNearbyMapInfos(targetLoc, 8)){
            if (patternTile.getMark() != patternTile.getPaint() && patternTile.getMark() != PaintType.EMPTY){
                boolean useSecondaryColor = patternTile.getMark() == PaintType.ALLY_SECONDARY;
                if (rc.canAttack(patternTile.getMapLocation()))
                    rc.attack(patternTile.getMapLocation(), useSecondaryColor);
            }
        }
        // Complete the ruin if we can.
        if (rc.canCompleteResourcePattern(targetLoc)){
            rc.completeResourcePattern(targetLoc);
        }
    }

}