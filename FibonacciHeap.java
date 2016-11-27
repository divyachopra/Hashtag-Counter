public class FibonacciHeap {
    private Node max;
    private int numberOfNodes;

     static class Node {
         private int frequency;
         private String tag;
         private Node parent;
         private Node left;
         private Node right;
         private Node child;
         private int degree;
         private boolean childCut;

        Node(int frequency, String tag) {
            this.frequency= frequency;
            this.tag = tag;
            this.parent= null;
            this.left= this;
            this.right= this;
            this.child= null;
            this.degree= 0;
            this.childCut= false;
        }

        public String getTag()
        {
            return tag;
        }
        public int getFrequency()
        {
            return frequency;
        }

        public void setFrequency(int frequency) {
            this.frequency = frequency;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public Node getParent() {
            return parent;
        }

        public void setParent(Node parent) {
            this.parent = parent;
        }

        public Node getLeft() {
            return left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getRight() {
            return right;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        public Node getChild() {
            return child;
        }

        public void setChild(Node child) {
            this.child = child;
        }

        public void setDegree(int degree) {
            this.degree = degree;
        }

        public boolean isChildCut() {
            return childCut;
        }

        public void setChildCut(boolean childCut) {
            this.childCut = childCut;
        }

        public int getDegree()
        {
            return degree;
        }

    }

    /**
     * Creates a new Heap with null max.
     */
    public FibonacciHeap() {
        this.max= null;
    }

    /**
     *  Adds the node with specified hashtag and frequency
     */
    public Node insert(Node n) {

        if (max == null) {
            max= n;
            numberOfNodes++;
        } else {
            mergeSiblings(n, max);
            numberOfNodes++;
            if (n.frequency > max.frequency)
                max= n;
        }
        return n;
    }

    

    // makes x's right and left point to itself
    private void removeFromHeap(Node node) {
        if (node.right == node)
          return;
        node.right.left= node.left;
        node.left.right= node.right;
        node.right= node;
        node.left= node;
    }

    // joins siblings list of n with that of max
    private void mergeSiblings(Node max, Node n) {
        Node a = max.right;
        Node b = n.left;
        max.right = n;
        n.left = max;
        a.left = b;
        b.right = a;
    }


    public Node getMax() {
        return max;
    }

    public int getNumberOfNodes() {
        return numberOfNodes;
    }

    /**
     * Returns the hastag which has the maximum frequency in the
     * heap.  If the heap is empty, null is returned.
     */
    public Node extractMax() {
        if (max == null)
            return null;
        if (max.child != null) {
            Node tempNode= max.child;
            // setting parent pointers for max's children to null
            while (tempNode.parent != null) {
                tempNode.parent= null;
                tempNode= tempNode.right;
            }
            // combining max's children to root list
            mergeSiblings(tempNode, max);
        }
        // removing max from root list
        Node prevMax= max;
        if (max.right == max) {
            max= null;
            numberOfNodes--;
        } else {
            max= max.right;
            removeFromHeap(prevMax);

            pairwiseCombine();
            numberOfNodes--;
        }
        postRemoval(prevMax);
        return prevMax;
    }
    void postRemoval(Node n) {
        n.parent= null;
        n.left= n;
        n.right= n;
        n.child= null;
        n.degree= 0;
        n.childCut= false;
    }

    // pairwiseCombines heaps of same degree
    private void pairwiseCombine() {
       
        Node[] degreeArray= new Node[numberOfNodes+1];

        Node current= max;
        Node first= max;
        do {
            Node temp1= current;
            int currDegree= current.degree;
            while (degreeArray[currDegree] != null) {
                Node temp2= degreeArray[currDegree];
                if (temp2.frequency > temp1.frequency) {
                    Node tempNode= temp1;
                    temp1= temp2;
                    temp2= tempNode;
                }
                if (temp2 == first) {
                    first= first.right;
                }
                if (temp2 == current) {
                    current= current.left;
                }
                linkNodes(temp2, temp1);
                degreeArray[currDegree++]= null;
            }
            degreeArray[currDegree]= temp1;
            current= current.right;
        } while (current != first);
        max= null;
        for (int i= 0; i < degreeArray.length; i++)
            if (degreeArray[i] != null) {
                if ((max == null)||(degreeArray[i].frequency > max.frequency)  )
                    max= degreeArray[i];
            }
    }

    private void linkNodes(Node min, Node max) {
        removeFromHeap(min);
        min.parent= max;
        if (max.child == null)
            max.child= min;
        else
            mergeSiblings(max.child, min);
        max.degree++;
        min.childCut= false;
    }

    /**
     * increases the frequency value associated with
     * Hashtag. If the tag already exists in Hashmap/heap, we
     * just increase its frequency by the new value.
     */
    public void increaseKey(Node node, int frequency) {
        node.frequency= frequency;
        Node parent= node.parent;
        if ( (parent != null) && (node.frequency > parent.frequency) ) {
           cutNode(node, parent);
           cascading(parent);
        }
        if (node.frequency > max.frequency)
            max= node;

    }

    // cut node childNode from parentNode
    private void cutNode(Node childNode, Node parentNode) {
        // remove x from y's children
        if (parentNode.child == childNode)
            parentNode.child= childNode.right;
        if (parentNode.child == childNode)
            parentNode.child= null;

        parentNode.degree--;
        removeFromHeap(childNode);
        mergeSiblings(childNode, max);
        childNode.parent= null;
        childNode.childCut= false;

    }

    private void cascading(Node y) {
        Node parentNode= y.parent;
        if (parentNode != null) {
            if (!y.childCut) {
                y.childCut= true;
            } else {
               cutNode(y, parentNode);
               cascading(parentNode);
            }
        }
    }

}

