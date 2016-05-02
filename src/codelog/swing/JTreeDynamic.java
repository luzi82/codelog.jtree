package codelog.swing;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class JTreeDynamic {

	public static void main(String[] argv) {

		final DefaultMutableTreeNode dmtn = new DefaultMutableTreeNode("Root");

		final DefaultTreeModel dtm = new DefaultTreeModel(dmtn);

		final JTree jt = new JTree(dtm);

		final JFrame jf = new JFrame();

		jf.getContentPane().add(jt);

		final LinkedList<DefaultMutableTreeNode> nodeList = new LinkedList<DefaultMutableTreeNode>();

		new Thread() {
			@Override
			public void run() {
				try {
					while (true) {
						DefaultMutableTreeNode dmtn2 = new DefaultMutableTreeNode("" + System.currentTimeMillis());
						nodeList.add(dmtn2);
						dtm.insertNodeInto(dmtn2, dmtn, dmtn.getChildCount());
						while (dmtn.getChildCount() > 10) {
							dtm.removeNodeFromParent(nodeList.removeFirst());
						}
						for(DefaultMutableTreeNode n:nodeList){
							n.setUserObject(n.toString()+".");
							dtm.nodeChanged(n);
						}
//						for(DefaultMutableTreeNode n:nodeList){
//							n.setUserObject(n.toString()+".");
//							dtm.nodeChanged(n);
//						}
						Thread.sleep(1000);
					}
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
		}.start();

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		jf.setSize((int) (screenSize.width / 2), (int) (screenSize.height / 2));
		jf.setLocationRelativeTo(null);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}
