package com.github.catageek.BCProtect.Regions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.builder.HashCodeBuilder;

import com.github.catageek.BCProtect.BCProtect;
import com.github.catageek.BCProtect.Quadtree.Point;
import com.github.catageek.BCProtect.Quadtree.Region;

/**
 * Class implementing a cuboid.
 * Point A coordinates are always
 * inferior to Point B coordinates
 */

public final class Cuboid implements Region, Cloneable {
	
	private List<Point> list = new ArrayList<Point>();
	private Object data;
	
	public final void nullData() {
		this.data = null;
	}

	/**
	 * Initialize the cuboid with the first point
	 * 
	 * @param a first point
	 */
	public Cuboid(Point a, Object object) {
		list.add(a.clone());
		if (object == null)
			throw new IllegalArgumentException("Object stored can't be null.");
		this.data = object;
	}
	
	/**
	 * Add the second point
	 *
	 * @param b1 second point
	 */
	public Cuboid addPoint(Point b1) {
		if (list.size() != 1)
			throw new IllegalArgumentException("No more than 2 points must be defined.");
		// Swap coordinates if necessary
		Point a = getA();
		Point b = b1.clone();
		
		int ax = a.getX();
		if (ax > b.getX()) {
			// swap x
			a.setX(b.getX());
			b.setX(ax);
		}

		int ay = a.getY();
		if (ay > b.getY()) {
			//swap y
			a.setY(b.getY());
			b.setY(ay);
		}

		int az = a.getZ();
		if (az > b.getZ()) {
			//swap z
			a.setZ(b.getZ());
			b.setZ(az);
		}
		
		// b is the outside border so we increment it
		b.setX(b.getX() + 1);
		b.setY(b.getY() + 1);
		b.setZ(b.getZ() + 1);
		
		list.set(0, a);
		list.add(b);
		return this;
	}


	@Override
	public boolean isCollidingXAxis(int z) {
		boolean ret = getA().getZ() < z && getB().getZ() >= z;
		if (BCProtect.debugQuadtree)
			BCProtect.log.info(BCProtect.logPrefix + " Checking if " + this.toString() + " is colliding horizontal axis at " + z + " : " + ret);
		return ret;
	}
	
	@Override
	public boolean isCollidingZAxis(int x) {
		boolean ret = getA().getX() < x && getB().getX() >= x;
		if (BCProtect.debugQuadtree)
			BCProtect.log.info(BCProtect.logPrefix + " Checking if " + this.toString() + " is colliding vertical axis at " + x + " : " + ret);
		return ret;
	}

	@Override
	public Iterator<Point> getPointIterator() {
		return list.listIterator();
	}

	
	/* (non-Javadoc)
	 * @see com.github.catageek.BCRegions.Region#isInsideRegion(double, double)
	 */
	@Override
	public boolean isInsideRegion(double px, double py, double pz) {
		if (getB() == null)
			return false;

		if (BCProtect.debugQuadtree)
			BCProtect.log.info(BCProtect.logPrefix + "isInsideRegion: testing cuboid " + this.toString());

		if (px >= getA().getX() && px < getB().getX()
				&& py >= getA().getY() && py < getB().getY()
				&& pz >= getA().getZ() && pz < getB().getZ())
			return true;
		return false;
	}

	/**
	 * @return the a
	 */
	public Point getA() {
		return list.get(0);
	}

	/**
	 * @return the b
	 */
	public Point getB() {
		return list.get(1);
	}

	@Override
	public Object getData() {
		return data;
	}

	@Override
	public String toString() {
		return getA().toString() + "x" + getB().toString();
	}
	
	@SuppressWarnings("unchecked")
	public Cuboid clone() {
		Cuboid c = null;
		try {
			c = (Cuboid) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		c.list = (List<Point>) ((ArrayList<Point>)list).clone();
		return c;
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(29,43).append(getA()).append(getB()).append(getData()).toHashCode();
	}

	@Override
	public int getWeight() {
		if (getB() == null)
			return 0;
		return (getB().getX() - getA().getX()) * (getB().getY() - getA().getY() * (getB().getZ() - getA().getZ()));
	}

}
