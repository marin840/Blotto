package blotto;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

@SuppressWarnings("serial")
public class ResultFrame extends JFrame{
	
	private Map<String, Integer> results;
	private List<Player> players;

	public ResultFrame(Map<String, Integer> results, List<Player> players) {
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.results = results;
		this.players = players;
		this.setVisible(true);
		this.setTitle("Results");
		
		//-----------------------------------------------------------
		//redni broj s lijeve strane
		JPanel rankPanel = new JPanel();
		add(rankPanel, BorderLayout.WEST);
		rankPanel.setLayout(new GridLayout(0, 1));
		int br = results.size();
		rankPanel.add(new JLabel(""));
		
		for(Integer i = 1; i <= br; i++) {
			JLabel label = new JLabel("   " + i.toString() + "   ");
			label.setBackground(new Color(200,200,250));
			label.setSize(new Dimension(60,60));
			//label.setBorder(BorderFactory.createLineBorder(new Color(200,200,250), 15));
			label.setOpaque(true);
			rankPanel.add(label);
		}
		//----------------------------------------------------------
		//rezultati ime i bodovi
		JPanel scorePanel = new JPanel();
		add(scorePanel, BorderLayout.CENTER);
		scorePanel.setLayout(new GridLayout(0,2));
		
		JLabel nameLabel = new JLabel("Name");
		JLabel scoreLabel = new JLabel("Score");
		nameLabel.setOpaque(true);
		scoreLabel.setOpaque(true);
		nameLabel.setBackground(new Color(200,200,250));
		scoreLabel.setBackground(new Color(200,200,250));

		scorePanel.add(nameLabel);
		scorePanel.add(scoreLabel);
		
		ImageIcon slikaZlato = new ImageIcon(getClass().getResource("goldmedal.jpg"));
		ImageIcon slikaZlatoScaled = new ImageIcon(slikaZlato.getImage().getScaledInstance(30,30, Image.SCALE_DEFAULT));
		
		int brojac=0;
		for(Map.Entry<String, Integer> entry: results.entrySet()) {
			++brojac;
			if(brojac == 1) {
				JPanel zlatniPanel = new JPanel();
				zlatniPanel.setLayout(new BorderLayout());
				scorePanel.add(zlatniPanel, SwingConstants.LEFT);
				zlatniPanel.add(new JLabel(entry.getKey()), BorderLayout.WEST);
				zlatniPanel.add(new JLabel(slikaZlatoScaled));
				scorePanel.add(new JLabel(entry.getValue().toString()));
			}
			else {
			scorePanel.add(new JLabel(entry.getKey()));
			scorePanel.add(new JLabel(entry.getValue().toString()));
			}
		}
		
//		for(int i = 1; i <= results.size(); i++) {
//			scorePanel.add(new JLabel(results[i]));
//			scorePanel.add(new JLabel(value.toString()));
//		}
//		int a=0;
//		results.forEach((key, value) -> {a++;
//			scorePanel.add(new JLabel(key));
//			scorePanel.add(new JLabel(value.toString()));
//		});
		
		//---------------------------------------------------------
		//strategije igraca desno
		JPanel strategiesPanel = new JPanel();
		add(strategiesPanel, BorderLayout.EAST);
		strategiesPanel.setLayout(new GridLayout(0,1));
		
		JLabel strategyLabel = new JLabel("Strategy");
		strategyLabel.setOpaque(true);
		strategyLabel.setBackground(new Color(200,200,250));
		strategiesPanel.add(strategyLabel);
		String s = "";
		for(String ime: results.keySet()) {
			for(Player p: players) {
				if(p.getName().equals(ime))
					s = p.getStrategy();
			}
			strategiesPanel.add(new JLabel(s));
		}
		
		this.setSize(450, 60*(br+1));
	}
}
