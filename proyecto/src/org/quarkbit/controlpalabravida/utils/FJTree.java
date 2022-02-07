package org.quarkbit.controlpalabravida.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class FJTree extends JFrame {

    private static final long serialVersionUID = 1L;

    private static List<String[]> data = new ArrayList<String[]>();

    static {
        data.add(new String[] { "Id1", "Start 1", null });
        data.add(new String[] { "Id2", "Node 1.1", "Id3" });
        data.add(new String[] { "Id3", "Node 1", "Id1" });
        data.add(new String[] { "Id4", "Node 1.2", "Id3" });
        data.add(new String[] { "Id5", "Node 2", "Id1" });
        data.add(new String[] { "Id6", "Start 2", null });
    }

    public static void main(final String[] args) {
        new FJTree();
    }

    FJTree() {
        JTree tree = new JTree();

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Partidas");

        createTreeNodesForElement(root, getElementTreeFromPlainList());

        tree.setModel(new DefaultTreeModel(root));

        add(tree);

        setVisible(true);
        setBounds(0, 0, 200, 200);
    }

    Collection<Element> getElementTreeFromPlainList() {
        // builds a map of elements object returned from store
        Map<String, Element> values = new HashMap<String, Element>();
        for (String[] s : data) {
            values.put(s[0], new Element(s[2], s[1]));
        }

        // creates a result list
        Collection<Element> result = new ArrayList<FJTree.Element>();

        // for each element in the result list that has a parent, put it into it
        // otherwise it is added to the result list
        for (Element e : values.values()) {
            if (e.parent != null) {
                values.get(e.parent).getChildren().add(e);
            } else {
                result.add(e);
            }
        }

        return result;
    }

    void createTreeNodesForElement(final DefaultMutableTreeNode dmtn, final Collection<Element> elements) {
        // for each element a tree node is created
        for (Element child : elements) {
            DefaultMutableTreeNode created = new DefaultMutableTreeNode(child.getName());
            dmtn.add(created);
            createTreeNodesForElement(created, child.getChildren());
        }
    }

    public static class Element {
        private final String parent;
        private final String name;
        private final Collection<Element> children = new ArrayList<FJTree.Element>();

        public Element(final String parent, final String name) {
            super();
            this.parent = parent;
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public Collection<Element> getChildren() {
            return children;
        }

    }

}