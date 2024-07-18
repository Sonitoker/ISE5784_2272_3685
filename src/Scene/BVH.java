
package Scene;

import geometries.AABB;
import geometries.Geometries;
import geometries.Geometry;
import geometries.Intersectable;
import primitives.Ray;


/**
 * Node class for Bounding Volume Hierarchy
 */
class Node {
    /**
     * left node
     */
    private Node left;
    /**
     * right node
     */
    private Node right;
    /**
     * bounding box
     */
    private AABB box;
    /**
     * list of geometries
     */
    private Geometries geometries;


    /**
     * constructor for Node class
     * @param left left node
     * @param right right node
     * @param box bounding box
     */
    public Node(Node left, Node right, AABB box)
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
    public Node(AABB box, Geometries geometries)
    {
        this.box = box;
        this.geometries = geometries;
        this.left = null;
        this.right = null;
    }

    /**
     * checks if the node is a leaf
     * @return true if the node is a leaf, false otherwise
     */
    public boolean isLeaf()
    {
        return left==null && right==null;
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
    public AABB getBox() {
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
public class BVH {

    private Node root;

    /**
     * constructor for Bvh class
     * @param geometries list of geometries
     */
    public BVH(Geometries geometries)
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
        AABB box = new AABB(geometries.getMinCoords(), geometries.getMaxCoords());

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
     * returns the intersected geometrie
     * @param ray ray
     * @param node node
     * @return list of intersected geometries
     */
    private Geometries getIntersectedGeometries(Ray ray, Node node)
    {
//        if(node.isLeaf() && node.getBox().intersects(ray))
//        {
//            return node.getGeometries();
//        }
//
//        Geometries geometries1 = new Geometries();
//        if(node.getLeft() != null && node.getLeft().getBox().intersects(ray))
//        {
//            Geometries geoLeft = getIntersectedGeometries(ray, node.getLeft());
//            if(geoLeft != null && geoLeft.size() > 0)
//                geometries1.add(geoLeft);
//        }
//        if(node.getRight() != null && node.getRight().getBox().intersects(ray))
//        {
//            Geometries geoRight = getIntersectedGeometries(ray, node.getRight());
//            if(geoRight != null && geoRight.size() > 0)
//                geometries1.add(geoRight);
//        }
//        return geometries1;

        Geometries intersectedGeometries = new Geometries();

        // אם הנוד הוא עלה ויש חיתוך עם הקופסה, נוסיף את הגיאומטריות שלו
        if (node.isLeaf() && node.getBox().intersects(ray)) {
            intersectedGeometries.add(node.getGeometries());
            return intersectedGeometries;
        }

        // נבדוק את הילדים אם קיימים ונוסיף את הגיאומטריות שלהם אם יש חיתוך
        boolean leftIntersected = node.getLeft() != null && node.getLeft().getBox().intersects(ray);
        boolean rightIntersected = node.getRight() != null && node.getRight().getBox().intersects(ray);

        if (leftIntersected) {
            Geometries geoLeft = getIntersectedGeometries(ray, node.getLeft());
            if (geoLeft != null && geoLeft.size() > 0) {
                intersectedGeometries.add(geoLeft);
            }
        }
        if (rightIntersected) {
            Geometries geoRight = getIntersectedGeometries(ray, node.getRight());
            if (geoRight != null && geoRight.size() > 0) {
                intersectedGeometries.add(geoRight);
            }
        }

        return intersectedGeometries;
    }




//    /**
//     * returns the intersected geometries
//     * @param ray ray
//     * @param node node
//     * @return list of intersected geometries
//     */
//    private boolean getIntersectedGeometries(Ray ray, Node node) {
//        // Check if the current node is a leaf node
//        if (node.isLeaf()) {
//            // Return true if the ray intersects with the bounding box of the leaf node
//            return node.getBox().intersects(ray);
//        }
//
//        // Check if the ray intersects with the left child node's bounding box and recursively check its children
//        if (node.getRight() == null && node.getLeft().getBox().intersects(ray)) {
//            return getIntersectedGeometries(ray, node.getLeft());
//
//        }
//
//        // Check if the ray intersects with the right child node's bounding box and recursively check its children
//        if (node.getLeft() == null && node.getRight().getBox().intersects(ray)) {
//            return getIntersectedGeometries(ray, node.getRight());
//        }
//        return getIntersectedGeometries(ray, node.getRight()) || getIntersectedGeometries(ray, node.getLeft());
//
//    }

}
