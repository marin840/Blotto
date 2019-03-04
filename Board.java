package blotto;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.TextField;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

@SuppressWarnings("serial")
public class Board extends JFrame {

	private TextField name;
	private JLabel remainingSoldiers;
	private JButton bNext;
	private JButton bPlay;
	private List<Player> listOfPlayers;
	private static GameThread gameThread;
	private TextField[] vojnici = new TextField[10];

	
	public Board() {
		for(int i = 0; i < 10; i++) vojnici[i] = new TextField();
		/*
		//polje za unos broja igraca i tipa igre
		JPanel startPanel = new JPanel();
		add(startPanel, BorderLayout.NORTH);
		startPanel.setLayout(new GridLayout(2,2));
		//row 0
		startPanel.add(new JLabel("Broj igrača:"));
		numOfPlayers = new TextField();
		startPanel.add(numOfPlayers);
		//row 1
		startPanel.add(new JLabel("Tip igre:"));
		typeOfGame = new TextField();
		startPanel.add(typeOfGame);
		startPanel.setBackground(new Color(150, 200, 250));
		*/
		JLabel naslov = new JLabel("BLOTTO", SwingConstants.CENTER);
		naslov.setFont(new Font("Courier New", Font.ITALIC, 40));
		add(naslov, BorderLayout.NORTH);
		
		//---------------------------------------------------------
		//polje za upis strategija igraca
		JPanel gamePanel = new JPanel();
		gamePanel.setLayout(new BorderLayout());
		add(gamePanel, BorderLayout.CENTER);
		
		JPanel enterPanel = new JPanel();
		gamePanel.add(enterPanel, BorderLayout.CENTER);
		enterPanel.setLayout(new GridLayout(0,1));
		//row 0
		JPanel vojska = new JPanel();
		vojska.setLayout(new GridLayout(0,10));
		enterPanel.add(vojska);
		for(int i = 0; i < 10; i++) {
			JPanel panel = new JPanel();
			vojska.add(panel);
			panel.add(vojnici[i]);
		}
		
		//row 1
		JPanel nameSoldiersPanel = new JPanel();
		gamePanel.add(nameSoldiersPanel, BorderLayout.SOUTH);
		nameSoldiersPanel.setLayout(new BorderLayout());
		
		JPanel remainingSoldiersPanel = new JPanel();
		remainingSoldiersPanel.add(new JLabel("Remaining soldiers:"));
		remainingSoldiers = new JLabel();
		remainingSoldiers.setBorder(BorderFactory.createLineBorder(new Color(0,250,200), 15));
		remainingSoldiersPanel.add(remainingSoldiers);
		remainingSoldiers.setFont(new Font("Times new Roman", Font.ITALIC, 24));
		remainingSoldiers.setText("100");
		
		ImageIcon slikaVojnika = new ImageIcon(getClass().getResource("vojniciZaBlotto.gif"));
		ImageIcon slikaVojnikaScaled = new ImageIcon(slikaVojnika.getImage().getScaledInstance(100,100, Image.SCALE_DEFAULT));
		remainingSoldiersPanel.add(new JLabel(slikaVojnikaScaled));
		
		nameSoldiersPanel.add(remainingSoldiersPanel, BorderLayout.CENTER);
				
		//row 2
		JPanel namePanel = new JPanel();
		namePanel.add(new JLabel("Player name:"));
		name = new TextField(30);
		namePanel.add(name);
		
		nameSoldiersPanel.add(namePanel, BorderLayout.SOUTH);
		
		/*//row 1
		enterPanel.add(new JLabel("Strategija:"));
		strategy = new TextField();
		enterPanel.add(strategy);*/
		
		JPanel kule = new JPanel();
		kule.setLayout(new BorderLayout());  //dodano
		gamePanel.add(kule, BorderLayout.NORTH);
		ImageIcon slikaKule = new ImageIcon(getClass().getResource("kulaZaBlotto.jpg"));
		ImageIcon imageIcon = new ImageIcon(slikaKule.getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT));
		JPanel vrijednostiKula = new JPanel();  //dodano
		JPanel slikeKula = new JPanel();     //dodano
		kule.add(vrijednostiKula, BorderLayout.NORTH);     //dodano
		kule.add(slikeKula, BorderLayout.CENTER);     //dodano
		vrijednostiKula.setLayout(new GridLayout(0, 10));    //dodano
		for(int i = 0; i < 10; i++) {
			JPanel panel = new JPanel();
			vrijednostiKula.add(panel);
			panel.add(new JLabel(Integer.valueOf(Kula.getValue(i)).toString()));
		}
		for(int i = 0; i < 10; i++) slikeKula.add(new JLabel(imageIcon));
		//---------------------------------------------------------
		
		listOfPlayers = new ArrayList<>();
		
		//tipka za sljedeceg igraca
		bNext = new JButton("Submit");
		
		JPanel buttonPanel = new JPanel();
		add(buttonPanel, BorderLayout.SOUTH);

		buttonPanel.add(bNext);
		bNext.addActionListener((e) -> {
			//pohrani igraca u bazu
			String playerName = name.getText();
			String s="";
			for(int i = 0; i < 10; i++) {
				String broj = vojnici[i].getText();
				if(broj.equals("")) broj = "0";
				s = s + broj + " ";
			}
			String playerStrategy = s;System.out.println(playerStrategy);
			if(remainingSoldiers.getText().equals("0")) {
				name.setText("");
				remainingSoldiers.setText("100");
				for(int i = 0; i < 10; i++) vojnici[i].setText("");
				Player player = new Player(playerName, playerStrategy);
				listOfPlayers.add(player);
				this.revalidate();
			}
			else {
				Integer ostatak = Integer.parseInt(remainingSoldiers.getText());
				System.out.println(ostatak);
				if(ostatak > 0)
					JOptionPane.showMessageDialog(buttonPanel, "You didn't put all your soldiers in the battle!", "Wrong input", JOptionPane.INFORMATION_MESSAGE);
				else
					JOptionPane.showMessageDialog(buttonPanel, "You put too many soldiers in the battle!", "Wrong input", JOptionPane.INFORMATION_MESSAGE);

			}
		});
		
		//---------------------------------------------------------
		//tipka za zapoceti igru
		bPlay = new JButton("Play");
		buttonPanel.add(bPlay);
		bPlay.addActionListener((e) -> {
			name.setText("");
			for(int i = 0; i < 10; i++) vojnici[i].setText("");
			gameThread =new GameThread(listOfPlayers);
			gameThread.start();
			this.revalidate();
		//	showResults();    ------ne zelimo u edt to raditi
		});
		
		//---------------------------------------------------------
		// Listen for changes in the text
		for(int i = 0; i < 10; i++) {
			vojnici[i].addFocusListener(new FocusListener() {
				
				@Override
				public void focusLost(FocusEvent e) {
					Integer r = 0;
					String s;
						for(int j = 0; j < 10; j++) {
							String broj = vojnici[j].getText();
							try {
								if(!(vojnici[j].getText().equals("")))
									Integer.parseInt(broj);
							
								if(!(vojnici[j].getText().equals("")) && Integer.parseInt(broj) < 0) {
									vojnici[j].setText("0");
									JOptionPane.showMessageDialog(buttonPanel, "You can't put negative number of soldiers!", "Wrong input", JOptionPane.INFORMATION_MESSAGE);
								}
								if(!(s = vojnici[j].getText()).equals(""))
									r += Integer.parseInt(s);
							}catch(Exception ex) {
								vojnici[j].setText("0");
								JOptionPane.showMessageDialog(buttonPanel, "Put a positive integer!", "Wrong input", JOptionPane.INFORMATION_MESSAGE);
							}
						}
					Integer ostalo = 100 - r;
					remainingSoldiers.setText(ostalo.toString());
				}

				@Override
				public void focusGained(FocusEvent e) {
					// TODO Auto-generated method stub
				}
			});
		}
		this.revalidate();
	}
	
	/*public static void showResults() {
		try {
			gameThread.join();
		}catch(InterruptedException e) {
		}
		Map<String, Integer> rezultati = gameThread.getRezultati();
		System.out.println("ide");
		rezultati.forEach((key, value) -> System.out.println(key + " : " + value + "\n"));
	}*/
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
		Board board = new Board();
		board.setTitle("Blotto game");
		board.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		board.pack();
		board.setVisible(true);
		});	
	}
}
