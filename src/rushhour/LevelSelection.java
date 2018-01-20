package rushhour;

import java.awt.Container;
import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

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
		top.add(new DefaultMutableTreeNode("Random Map"));
		t.expandRow(0);
		
		t.addTreeSelectionListener(new TreeSelectionListener() {
			
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode)t.getLastSelectedPathComponent();
				if(node.isLeaf()){
					
					if("Random Map".equals(node.getUserObject())){
						Random rnd = new Random();
						int lvl = rnd.nextInt(converter.getLevels().size()-1);
						int mp = rnd.nextInt(converter.getLevels().get(lvl).getMaps().size()-1);
						currentGame = new Window("Rush Hour", converter.getLevels().get(lvl).getMapByIndex(mp));
						currentGame.showWindow();
					}
					else{
						Map selected = (Map)(node).getUserObject();
						currentGame = new Window("Rush Hour", selected);
						currentGame.showWindow();
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
