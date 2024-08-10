package scene;

import geometries.Intersectable.Box;
import geometries.Geometries;
import geometries.Geometry;
import geometries.Intersectable;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

/**
 * Node class for Bounding Volume Hierarchy
 */
class Node {
    private Node left;
    private Node right;
    private Box box;
    private Geometries geometries;

    /**
     * constructor for Node class
     * @param left left node
     * @param right right node
     * @param box bounding box
     */
    public Node(Node left, Node right, Box box)
    {
        this.left = left;
        this.right = right;
        this.box = box;
    }

    /**
     * constructor for a leaf node
     * @param box  bounding box
     * @param geometries list of geometries
     */
    public Node(Box box, Geometries geometries)
    {
        this.box = box;
        this.geometries = geometries;
    }

    /**
     * checks if the node is a leaf
     * @return true if the node is a leaf, false otherwise
     */
    public boolean isLeaf()
    {
        return geometries != null;
    }

    /**
     * getter for left node
     * @return left node
     */
    public Node getLeft() {
        return left;
    }

    /**
     * getter for right node
     * @return right node
     */
    public Node getRight() {
        return right;
    }

    /**
     * getter for bounding box
     * @return bounding box
     */
    public Box getBox() {
        return box;
    }

    /**
     * getter for geometries
     * @return geometries
     */
    public Geometries getGeometries() {
        return geometries;
    }

}

/**
 * Bounding Volume Hierarchy
 */
public class BVHTree {

    private Node root;

    /**
     * constructor for Bvh class
     * @param geometries list of geometries
     */
    public BVHTree(Geometries geometries)
    {
        root = buildTree(geometries);
    }

    /**
     * builds the tree recursively
     * @param geometries list of geometries
     * @return root node
     */
    private Node buildTree(Geometries geometries)
    {
        Box box = new Box(geometries.box.minX, geometries.box.minY, geometries.box.minZ, geometries.box.maxX, geometries.box.maxY, geometries.box.maxZ);
        if(geometries.size() <= 4)
        {
            return new Node(box, geometries);
        }

        Geometries newGeometries = geometries.splitAxisAligned(box);
        return new Node(buildTree(geometries), buildTree(newGeometries), box);
    }

    /**
     * returns the intersected geometries
     * @param ray ray
     * @return list of intersected geometries
     */
    public Geometries getIntersectedGeometries(Ray ray)
    {
        return getIntersectedGeometries(ray, root);
    }




    /**
     * returns the intersected geometries
     * @param ray ray
     * @param node node
     * @return list of intersected geometries
     */
    private Geometries getIntersectedGeometries(Ray ray, Node node)
    {
        if(node.isLeaf())
        {
            return (node.getGeometries());
            //return geometries;
        }

        Geometries geometries1 = new Geometries();
        if(node.getLeft() != null && node.getLeft().getBox().intersects(ray, Double.POSITIVE_INFINITY))
        {
            Geometries geoLeft = getIntersectedGeometries(ray, node.getLeft());
            if(geoLeft != null && geoLeft.size() > 0)
                geometries1.add(geoLeft);
        }
        if(node.getRight() != null && node.getRight().getBox().intersects(ray, Double.POSITIVE_INFINITY))
        {
            Geometries geoRight = getIntersectedGeometries(ray, node.getRight());
            if(geoRight != null && geoRight.size() > 0)
                geometries1.add(geoRight);
        }
        return geometries1;
    }
}