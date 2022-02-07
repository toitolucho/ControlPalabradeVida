package org.quarkbit.controlpalabravida.utils;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class Main extends JFrame {
//  DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("J");
//
//  DefaultTreeModel model = new DefaultTreeModel(rootNode);
//
//  JTree m_tree = new JTree(model);
//
//  JButton addButton;
//
//  public Main() {
//    DefaultMutableTreeNode forums = new DefaultMutableTreeNode("F");
//    forums.add(new DefaultMutableTreeNode("T"));
//    DefaultMutableTreeNode articles = new DefaultMutableTreeNode("A");
//    articles.add(new DefaultMutableTreeNode("1"));
//    DefaultMutableTreeNode examples = new DefaultMutableTreeNode("E");
//    examples.add(new DefaultMutableTreeNode("2"));
//
//    rootNode.add(forums);
//    rootNode.add(articles);
//    rootNode.add(examples);
//
//    m_tree.setEditable(true);
//    m_tree.setSelectionRow(0);
//
//    JScrollPane scrollPane = new JScrollPane(m_tree);
//    getContentPane().add(scrollPane, BorderLayout.CENTER);
//
//    JPanel panel = new JPanel();
//    addButton = new JButton("Add Node");
//    addButton.addActionListener(new ActionListener() {
//      public void actionPerformed(ActionEvent event) {
//        DefaultMutableTreeNode selNode = (DefaultMutableTreeNode) m_tree
//            .getLastSelectedPathComponent();
//        if (selNode != null) {
//          DefaultMutableTreeNode newNode = new DefaultMutableTreeNode("New Node");
//          model.insertNodeInto(newNode, selNode, selNode.getChildCount());
//          TreeNode[] nodes = model.getPathToRoot(newNode);
//          TreePath path = new TreePath(nodes);
//          m_tree.scrollPathToVisible(path);
//          m_tree.setSelectionPath(path);
//          m_tree.startEditingAtPath(path);
//        }
//      }
//    });
//    panel.add(addButton);
//    getContentPane().add(panel, BorderLayout.SOUTH);
//    setSize(300, 400);
//    setVisible(true);
//  }
	
	DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("J");

	DefaultTreeModel model = new DefaultTreeModel(rootNode);

	JTree m_tree = new JTree(model);

	JButton addButton;

	public Main() { 
		DefaultMutableTreeNode forums = new DefaultMutableTreeNode("F");
		forums.add(new DefaultMutableTreeNode("T"));
		DefaultMutableTreeNode articles = new DefaultMutableTreeNode("A");
		articles.add(new DefaultMutableTreeNode("1"));
		DefaultMutableTreeNode examples = new DefaultMutableTreeNode("E");
		examples.add(new DefaultMutableTreeNode("2"));

		rootNode.add(forums);
		rootNode.add(articles);
		rootNode.add(examples);

		m_tree.setEditable(true);
		m_tree.setSelectionRow(0);

		JScrollPane scrollPane = new JScrollPane(m_tree);
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		addButton = new JButton("Add Node");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				DefaultMutableTreeNode selNode = (DefaultMutableTreeNode) m_tree
						.getLastSelectedPathComponent();
				if (selNode != null) {
					DefaultMutableTreeNode newNode = new DefaultMutableTreeNode("New Node");
					model.insertNodeInto(newNode, selNode, selNode.getChildCount());
					TreeNode[] nodes = model.getPathToRoot(newNode);
					TreePath path = new TreePath(nodes);
					m_tree.scrollPathToVisible(path);
					m_tree.setSelectionPath(path);
//					m_tree.startEditingAtPath(path);
				}
			}
		});
		panel.add(addButton);
		getContentPane().add(panel, BorderLayout.SOUTH);
		setSize(300, 400);
		setVisible(true);
	}
	public static void main(String[] arg) {
		new Main();
	}
}