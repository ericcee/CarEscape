package rushhour;

import java.awt.Container;
import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

public class LevelSelection extends JFrame {
	private Converter converter = null;
	private Window currentGame = null;
	public LevelSelection(Converter conv){
		this.converter = conv;
		
		this.setBounds(0, 0, 200, 256);
		this.setLocationRelativeTo(null);
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("Levels");
		JTree t = new JTree(top);
		addNodes(top);
		
		t.addTreeSelectionListener(new TreeSelectionListener() {
			
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode)t.getLastSelectedPathComponent();
				if(node.isLeaf()){
					Map selected = (Map)(node).getUserObject();
					currentGame = new Window("Rush Hour", selected);
					currentGame.showWindow();
					try {
						converter.reload();
						addNodes(top);
					} catch (NumberFormatException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			}
		});
		Font curr = t.getFont();
		Font bigFont = new Font(curr.getFontName(), curr.getStyle(), curr.getSize()+5);
		t.setFont(bigFont);
		this.add(new JScrollPane(t));
		this.setResizable(false);
		
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	private DefaultMutableTreeNode addNodes(DefaultMutableTreeNode top){
		for(Level l : converter.getLevels()){
			DefaultMutableTreeNode nextNode = new DefaultMutableTreeNode(l);
			for(Map m : l.getMaps()){
				nextNode.add(new DefaultMutableTreeNode(m));
			}
			top.add(nextNode);
		}
		return top;
	}
	
}
