import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.junit.Before;
import java.util.LinkedList;
import java.util.List;


public class TestCases
{
    double DELTA = 0.000001;
    //testing the constructors also includes testing the getters for each class and their superclasses


    Background background = new Background("name");

    @Test
    public void testBackgroundConstructor()
    {
	assertEquals(background.getName(), "name");
    }


    Point blacksmith_point = new Point(1, 5);
    Blacksmith blacksmith = new Blacksmith("name", blacksmith_point);

    @Test
    public void testBlacksmithConstructor()
    {
	assertEquals(blacksmith.getName(), "name");
	assertEquals(blacksmith.getPosition().getX(), 1);
	assertEquals(blacksmith.getPosition().getY(), 5);
    }
    @Test
    public void testBlacksmithEntityString()
    {
	assertEquals(blacksmith.entityString(), "blacksmith name 1 5");
    }


    EntityDistancePair pair = new EntityDistancePair(blacksmith, 2.0);

    @Test
    public void testEntityDistancePairConstructor()
    {
	assertEquals(pair.getEntity(), blacksmith);
	assertEquals(pair.getDistance(), 2.0, DELTA);
    }
    

    Grid background_grid = new Grid(3, 5, background);
    Grid entity_grid = new Grid(4, 7, blacksmith);

    @Test
    public void testGridConstructor()
    {
	assertEquals(background_grid.getWidth(), 3);
	assertEquals(background_grid.getHeight(), 5);
	for (int y = 0; y < 5; y++)
	{
	    for (int x = 0; x < 3; x++)
	    {
		Point grid_point = new Point(x, y);
		assertEquals((Background)background_grid.getCell(grid_point), background);
	    }
	}
	Point entity_grid_point = new Point(2, 4);
	entity_grid.setCell(entity_grid_point, miner_nf);
	assertEquals(entity_grid.getCell(entity_grid_point), miner_nf);
    }


    @Test
    public void testSign()
    {
	assertEquals(MathOperations.sign(-7), -1);
	assertEquals(MathOperations.sign(0), 0);
	assertEquals(MathOperations.sign(9), 1);
    }
    @Test
    public void testAdjacent()
    {
	Point p1 = new Point(1, 5);
	Point p2 = new Point(2, 5);
	Point p3 = new Point(1, 4);
	assertTrue(MathOperations.adjacent(p1, p2));
	assertTrue(MathOperations.adjacent(p1, p3));
    }
    @Test
    public void testDistanceSquared()
    {
	Point p1 = new Point(1, 5);
	Point p2 = new Point(2, 9);
	assertEquals(MathOperations.distanceSquared(p1, p2), 17.0, DELTA);
    }


    Point miner_nf_point = new Point(8, 9);
    MinerNotFull miner_nf = new MinerNotFull("name", miner_nf_point, 500, 23, 3);

    @Test
    public void testMinerNotFullConstructor()
    {
	assertEquals(miner_nf.getName(), "name");
	assertEquals(miner_nf.getPosition().getX(), 8);
	assertEquals(miner_nf.getPosition().getY(), 9);
	assertEquals(miner_nf.getAnimationRate(), 500);
	assertEquals(miner_nf.getRate(), 23);
	assertEquals(miner_nf.getResourceLimit(), 3);
	assertEquals(miner_nf.getResourceCount(), 0);
    }
    @Test
    public void testMinerNotFullEntityString()
    {
	assertEquals(miner_nf.entityString(), "miner name 8 9 3 23 500");
    }
    @Test
    public void testMinerNotFullSetResourceCount()
    {
        miner_nf.setResourceCount(3);
	assertEquals(miner_nf.getResourceCount(), 3);
    }
    @Test
    public void testTryTransformMinerNotFull()
    {
	miner_nf.setResourceCount(0);
	assertEquals(miner_nf.tryTransformMinerNotFull(), miner_nf);
	miner_nf.setResourceCount(3);
	MinerFull new_miner = new MinerFull("name", miner_nf_point, 500, 23, 3);
	assertEquals(miner_nf.tryTransformMinerNotFull().getResourceCount(), 3);
    }


    Point miner_f_point = new Point(8, 9);
    MinerFull miner_f = new MinerFull("name", miner_f_point, 500, 23, 3);

    @Test
    public void testMinerFullConstructor()
    {
	assertEquals(miner_f.getName(), "name");
	assertEquals(miner_f.getPosition().getX(), 8);
	assertEquals(miner_f.getPosition().getY(), 9);
	assertEquals(miner_f.getAnimationRate(), 500);
	assertEquals(miner_f.getRate(), 23);
	assertEquals(miner_f.getResourceLimit(), 3);
	assertEquals(miner_f.getResourceCount(), 3);
    }
    @Test
    public void testMinerFullSetResourceCount()
    {
        miner_f.setResourceCount(3);
	assertEquals(miner_f.getResourceCount(), 3);
    }
    @Test
    public void testTryTransformMinerFull()
    {
	assertEquals(miner_f.tryTransformMinerFull().getResourceCount(), 0);
    }


    Point obstacle_point = new Point(9, 2);
    Obstacle obstacle = new Obstacle("name", obstacle_point);

    @Test
    public void testObstacleConstructor()
    {
	assertEquals(obstacle.getName(), "name");
	assertEquals(obstacle.getPosition().getX(), 9);
	assertEquals(obstacle.getPosition().getY(), 2);
    }
    @Test
    public void testObstacleEntityString()
    {
	assertEquals(obstacle.entityString(), "obstacle name 9 2");
    }


    Point ore_point = new Point(4, 3);
    Ore ore = new Ore("name", ore_point, 200);

    @Test
    public void testOreConstructor()
    {
	assertEquals(ore.getName(), "name");
	assertEquals(ore.getPosition().getX(), 4);
	assertEquals(ore.getPosition().getY(), 3);
	assertEquals(ore.getRate(), 200);
    }
    @Test
    public void testOreEntityString()
    {
	assertEquals(ore.entityString(), "ore name 4 3 200");
    }


    Point ore_blob_point = new Point(1, 0);
    OreBlob ore_blob = new OreBlob("name", ore_blob_point, 3000, 500);

    @Test
    public void testOreBlobConstructor()
    {
	assertEquals(ore_blob.getName(), "name");
	assertEquals(ore_blob.getPosition().getX(), 1);
	assertEquals(ore_blob.getPosition().getY(), 0);
	assertEquals(ore_blob.getAnimationRate(), 3000);
	assertEquals(ore_blob.getRate(), 500);
    }   


    Point point = new Point(300, 200);
	
    @Test
    public void testPointConstructor()
    {
	point.setX(10);
	point.setY(15);
	assertEquals(point.getX(), 10);
	assertEquals(point.getY(), 15);
    }


    Point quake_point = new Point(6, 7);
    Quake quake = new Quake("name", quake_point, 250);

    @Test
    public void testQuakeConstructor()
    {
	assertEquals(quake.getName(), "name");
	assertEquals(quake.getPosition().getX(), 6);
	assertEquals(quake.getPosition().getY(), 7);
	assertEquals(quake.getAnimationRate(), 250);
    }  


    Point vein_point = new Point(12, 13);
    Vein vein = new Vein("name", vein_point, 50);

    @Test
    public void testVeinConstructor()
    {
	assertEquals(vein.getName(), "name");
	assertEquals(vein.getPosition().getX(), 12);
	assertEquals(vein.getPosition().getY(), 13);
	assertEquals(vein.getRate(), 50);
	assertEquals(vein.getResourceDistance(), 1);
    } 

    List<Entity> worldmodel_entities = new LinkedList<Entity>();
    Grid worldmodel_occupancy_grid = new Grid(16, 10, blacksmith);
    Grid worldmodel_background_grid = new Grid(16, 10, background);
    WorldModel worldmodel = new WorldModel(10, 16, worldmodel_background_grid, worldmodel_occupancy_grid,
					   worldmodel_entities);
    Point worldmodel_in_point = new Point(15, 9);
    Point worldmodel_out_point = new Point(16, 10);

    @Test
    public void testWorldModelConstructor()
    {
	assertEquals(worldmodel.getEntities(), worldmodel_entities);
    }
    @Test
    public void testWorldModelGetTileBackground()
    {
	assertEquals(worldmodel.getTileBackground(worldmodel_in_point), background);
	assertNull(worldmodel.getTileBackground(worldmodel_out_point));
    }
    @Test
    public void testWorldModelSetTileBackground()
    {
	worldmodel.setTileBackground(worldmodel_in_point, background);
	assertEquals(worldmodel.getTileBackground(worldmodel_in_point), background);
    }
    @Test
    public void testAddEntityAndGetTileOccupant()
    {
	Point pos = new Point(1, 5);
	Quake tile_occupant = new Quake("george", pos, 4); 
	worldmodel.addEntity(tile_occupant);
	assertEquals(worldmodel.getTileOccupant(pos).getName(), "george");
    }
    @Test
    public void testMoveEntity()
    {
	Point moving_miner_point = new Point(5, 6);
	MinerFull moving_miner = new MinerFull("stacy", moving_miner_point, 1000, 100, 4);
	Point moving_miner_new_point = new Point(7, 8);
	worldmodel.moveEntity(moving_miner, moving_miner_new_point);
	assertEquals(moving_miner.getPosition(), moving_miner_new_point);
    }
    @Test
    public void testRemoveEntity() //this one will also fully test removeEntityAt
    {
	Point removing_miner_point = new Point(7, 3);
	MinerNotFull removing_miner = new MinerNotFull("tim", removing_miner_point, 100, 300, 4);
	worldmodel.removeEntity(removing_miner);
	assertNull(worldmodel.getTileOccupant(removing_miner_point));
    }
    @Test
    public void testWithinBounds()
    {
	assertTrue(worldmodel.withinBounds(worldmodel_in_point));
	assertFalse(worldmodel.withinBounds(worldmodel_out_point));
    }
    @Test
    public void testIsOccupied()
    {
	worldmodel.addEntity(blacksmith);
	assertTrue(worldmodel.isOccupied(blacksmith.getPosition()));
	worldmodel.removeEntity(blacksmith);
	assertFalse(worldmodel.isOccupied(blacksmith.getPosition()));
    }
    @Test
    public void testNextPosition1()  //in it's current form, blobNextPosition is exactly the same as nextPosition, so 
				     //these tests will work for it as well
    {
	Grid empty_entity_grid = new Grid(15, 20, null);
	Grid empty_background_grid = new Grid(15, 20, background);
	WorldModel empty_world = new WorldModel(20, 15, empty_background_grid, empty_entity_grid, worldmodel_entities);

	Point current_point = new Point(1, 1);
	Point destination_point = new Point(9, 3);
	Point next_point = new Point(2, 1);

	assertEquals(empty_world.nextPosition(current_point, destination_point).getX(), next_point.getX());
	assertEquals(empty_world.nextPosition(current_point, destination_point).getY(), next_point.getY());
    }
    @Test
    public void testNextPosition2()
    {
	Grid empty_entity_grid = new Grid(15, 20, null);
	Grid empty_background_grid = new Grid(15, 20, background);
	WorldModel empty_world = new WorldModel(20, 15, empty_background_grid, empty_entity_grid, worldmodel_entities);

	Point current_point = new Point(4, 8);
	Point destination_point = new Point(4, 4);
	Point next_point = new Point(4, 7);

	assertEquals(empty_world.nextPosition(current_point, destination_point).getX(), next_point.getX());
	assertEquals(empty_world.nextPosition(current_point, destination_point).getY(), next_point.getY());
    }
    @Test
    public void testNextPosition3()
    {
	Grid empty_entity_grid = new Grid(15, 20, null);
	Grid empty_background_grid = new Grid(15, 20, background);
	WorldModel empty_world = new WorldModel(20, 15, empty_background_grid, empty_entity_grid, worldmodel_entities);

	Point current_point = new Point(3, 3);
	Point destination_point = new Point(3, 3);
	Point next_point = new Point(3, 3);

	assertEquals(empty_world.nextPosition(current_point, destination_point).getX(), next_point.getX());
	assertEquals(empty_world.nextPosition(current_point, destination_point).getY(), next_point.getY());
    }
    @Test
    public void testFindOpenAround1()
    {
        Grid empty_entity_grid = new Grid(15, 20, null);
	Grid empty_background_grid = new Grid(15, 20, background);
	WorldModel empty_world = new WorldModel(20, 15, empty_background_grid, empty_entity_grid, worldmodel_entities);
	
	Point intheway1_point = new Point(2, 2);
	Blacksmith intheway1 = new Blacksmith("1", intheway1_point);
	empty_world.addEntity(intheway1);

	Point spawner_point = new Point(3, 3);
	Point spawn_point = new Point(3, 2);
	assertEquals(empty_world.findOpenAround(spawner_point, 1).getX(), spawn_point.getX());
	assertEquals(empty_world.findOpenAround(spawner_point, 1).getY(), spawn_point.getY());
    }
    @Test
    public void testFindOpenAround2()
    {
        Grid empty_entity_grid = new Grid(15, 20, null);
	Grid empty_background_grid = new Grid(15, 20, background);
	WorldModel empty_world = new WorldModel(20, 15, empty_background_grid, empty_entity_grid, worldmodel_entities);
	
	Point intheway1_point = new Point(2, 2);
	Blacksmith intheway1 = new Blacksmith("1", intheway1_point);
	empty_world.addEntity(intheway1);
	Point intheway2_point = new Point(3, 2);
	Blacksmith intheway2 = new Blacksmith("2", intheway2_point);
	empty_world.addEntity(intheway2);
	Point intheway3_point = new Point(4, 2);
	Blacksmith intheway3 = new Blacksmith("3", intheway3_point);
	empty_world.addEntity(intheway3);
	Point intheway4_point = new Point(2, 3);
	Blacksmith intheway4 = new Blacksmith("4", intheway4_point);
	empty_world.addEntity(intheway4);
	Point intheway5_point = new Point(3, 3);
	Blacksmith intheway5 = new Blacksmith("5", intheway5_point);
	empty_world.addEntity(intheway5);
	Point intheway6_point = new Point(4, 3);
	Blacksmith intheway6 = new Blacksmith("6", intheway6_point);
	empty_world.addEntity(intheway6);
	Point intheway7_point = new Point(2, 4);
	Blacksmith intheway7 = new Blacksmith("7", intheway7_point);
	empty_world.addEntity(intheway7);
	Point intheway8_point = new Point(3, 4);
	Blacksmith intheway8 = new Blacksmith("8", intheway8_point);
	empty_world.addEntity(intheway8);
	Point intheway9_point = new Point(4, 4);
	Blacksmith intheway9 = new Blacksmith("9", intheway9_point);
	empty_world.addEntity(intheway9);

	Point spawner_point = new Point(3, 3);
	assertNull(empty_world.findOpenAround(spawner_point, 1));
    }
    @Test
    public void testFindNearest() //this one will also test nearestEntity()
    {
	Grid empty_entity_grid = new Grid(15, 20, null);
	Grid empty_background_grid = new Grid(15, 20, background);
	WorldModel empty_world = new WorldModel(20, 15, empty_background_grid, empty_entity_grid, worldmodel_entities);
	
	Point intheway1_point = new Point(4, 4);
	Blacksmith intheway1 = new Blacksmith("1", intheway1_point);
	empty_world.addEntity(intheway1);
	Point intheway2_point = new Point(5, 4);
	Blacksmith intheway2 = new Blacksmith("2", intheway2_point);
	empty_world.addEntity(intheway2);
	Point intheway3_point = new Point(8, 3);
	Blacksmith intheway3 = new Blacksmith("3", intheway3_point);
	empty_world.addEntity(intheway3);

	Point distance_point = new Point(3, 2);
	assertEquals(empty_world.findNearest(distance_point, Blacksmith.class), intheway1);
    }
    
    //the remaining methods of WorldModel all essentially just create an Object based on a point; since scheduling isn't included at the moment, they contain nothing new that needs to be tested.
}
