

package nl.tue.s2id90.group42;

/**
 *
 * @author s121924
 */
public class Node {
    
    int value;
    Node[] childs;
    
    public Node(int value, Node[] childs) {
        this.value = value;
        this.childs = childs;
    }
    
}
