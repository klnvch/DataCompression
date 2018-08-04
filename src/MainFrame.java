import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;

import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;


public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2895165412746545084L;
	private JPanel contentPane;
	private JTextField tfIterationLimit;
	private JTextField tfBlockSize;
	private JTextField tfKohonenSizeX;
	private JTextField tfKohonenSizeY;
	private JTextField tfLerningRateL0;
	private JTextField tfLerningRateLambda;
	private JTextField tfNeigbourInfluenceS0;
	private JTextField tfNeigbourInfluenceLambda;
	private JTextField tfLPFradius;
	
	private JPanel panelImages;
	private JLabel lblBytesBefore;
	private JLabel lblBytesAfter;
	private JLabel lblBytesCodebook;
	private JLabel lblCompression;
	private JLabel lblErrorMessage;
	
	private int[][] redImage;
	private int[][] greenImage;
	private int[][] blueImage;
	
	private int[][] redImageOut;
	private int[][] greenImageOut;
	private int[][] blueImageOut;
	
	private BufferedImage img = null;


	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 750);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmOpen = new JMenuItem("Open...");
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(new File("."));
                chooser.setSelectedFile(new File("Unnamed"));
                chooser.setFileFilter(new FileFilter()
                {
                        public boolean accept(File f)
                        {
                            return f.isDirectory() || f.getName().toLowerCase().endsWith(".jpg") || f.getName().toLowerCase().endsWith(".png");
                        }
                        public String getDescription()
                        {
                            return "Pictures";
                        }

                });
                int returnVal = chooser.showOpenDialog(MainFrame.this);
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    String filename = chooser.getSelectedFile().getPath();
                    
                    try {
            			img = ImageIO.read(new File(filename));
            			
            			ShowImage panel1 = new ShowImage(img);
    					
    					panelImages.removeAll();
    					panelImages.add(panel1);
    					panelImages.validate();
    					panelImages.repaint();
            			
            		}catch (IOException ex){
            			lblErrorMessage.setText(ex.toString());
            		}
                }
            }
		});
		mnFile.add(mntmOpen);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		panelImages = new JPanel();
		contentPane.add(panelImages, BorderLayout.CENTER);
		panelImages.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel panel_3 = new JPanel();
		contentPane.add(panel_3, BorderLayout.SOUTH);
		panel_3.setLayout(new GridLayout(1, 2, 0, 0));
		
		JPanel panel_8 = new JPanel();
		panel_3.add(panel_8);
		panel_8.setLayout(new GridLayout(6, 0, 0, 0));
		
		JPanel panel_4 = new JPanel();
		panel_8.add(panel_4);
		FlowLayout flowLayout = (FlowLayout) panel_4.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		
		JLabel lblIterationLimit = new JLabel("Iteration limit:");
		panel_4.add(lblIterationLimit);
		
		tfIterationLimit = new JTextField();
		tfIterationLimit.setText(Integer.toString(Main.limit));
		panel_4.add(tfIterationLimit);
		tfIterationLimit.setColumns(10);
		
		JPanel panel_14 = new JPanel();
		FlowLayout flowLayout_11 = (FlowLayout) panel_14.getLayout();
		flowLayout_11.setAlignment(FlowLayout.LEFT);
		panel_8.add(panel_14);
		
		JLabel lblBlockSize = new JLabel("Block size:");
		panel_14.add(lblBlockSize);
		
		tfBlockSize = new JTextField();
		tfBlockSize.setText(Integer.toString(Main.blockSize));
		panel_14.add(tfBlockSize);
		tfBlockSize.setColumns(10);
		
		JPanel panel_5 = new JPanel();
		panel_8.add(panel_5);
		FlowLayout flowLayout_1 = (FlowLayout) panel_5.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		
		JLabel lblKohonenSize = new JLabel("Kohonen Size:");
		panel_5.add(lblKohonenSize);
		
		tfKohonenSizeX = new JTextField();
		tfKohonenSizeX.setText(Integer.toString(Main.kohonenSizeHeight));
		panel_5.add(tfKohonenSizeX);
		tfKohonenSizeX.setColumns(10);
		
		JLabel lblX = new JLabel("X");
		panel_5.add(lblX);
		
		tfKohonenSizeY = new JTextField();
		tfKohonenSizeY.setText(Integer.toString(Main.kohonenSizeWidth));
		panel_5.add(tfKohonenSizeY);
		tfKohonenSizeY.setColumns(10);
		
		JPanel panel_1 = new JPanel();
		panel_8.add(panel_1);
		FlowLayout flowLayout_2 = (FlowLayout) panel_1.getLayout();
		flowLayout_2.setAlignment(FlowLayout.LEFT);
		
		JLabel lblLearningRate = new JLabel("Learning rate:");
		panel_1.add(lblLearningRate);
		
		JLabel lblL = new JLabel("L0");
		panel_1.add(lblL);
		
		tfLerningRateL0 = new JTextField();
		tfLerningRateL0.setText(Double.toString(Main.L0));
		panel_1.add(tfLerningRateL0);
		tfLerningRateL0.setColumns(10);
		
		JLabel lblLambda = new JLabel("lambda");
		panel_1.add(lblLambda);
		
		tfLerningRateLambda = new JTextField();
		tfLerningRateLambda.setEnabled(false);
		tfLerningRateLambda.setText(Double.toString(Main.lambda1));
		panel_1.add(tfLerningRateLambda);
		tfLerningRateLambda.setColumns(10);
		
		JPanel panel_2 = new JPanel();
		panel_8.add(panel_2);
		FlowLayout flowLayout_3 = (FlowLayout) panel_2.getLayout();
		flowLayout_3.setAlignment(FlowLayout.LEFT);
		
		JLabel lblNeigbourInfluence = new JLabel("Neigbour influence:");
		panel_2.add(lblNeigbourInfluence);
		
		JLabel lblS = new JLabel("S0");
		panel_2.add(lblS);
		
		tfNeigbourInfluenceS0 = new JTextField();
		tfNeigbourInfluenceS0.setEnabled(false);
		tfNeigbourInfluenceS0.setText(Double.toString(Main.S0));
		panel_2.add(tfNeigbourInfluenceS0);
		tfNeigbourInfluenceS0.setColumns(10);
		
		JLabel lblLambda_1 = new JLabel("lambda");
		panel_2.add(lblLambda_1);
		
		tfNeigbourInfluenceLambda = new JTextField();
		tfNeigbourInfluenceLambda.setEnabled(false);
		tfNeigbourInfluenceLambda.setText(Double.toString(Main.lambda2));
		panel_2.add(tfNeigbourInfluenceLambda);
		tfNeigbourInfluenceLambda.setColumns(10);
		
		JPanel panel_12 = new JPanel();
		FlowLayout flowLayout_9 = (FlowLayout) panel_12.getLayout();
		flowLayout_9.setAlignment(FlowLayout.LEFT);
		panel_8.add(panel_12);
		
		JLabel lblLowPassFilter = new JLabel("Low pass filter radius:");
		panel_12.add(lblLowPassFilter);
		
		tfLPFradius = new JTextField();
		tfLPFradius.setText(Double.toString(Main.D0));
		panel_12.add(tfLPFradius);
		tfLPFradius.setColumns(10);
		
		JPanel panel_9 = new JPanel();
		panel_3.add(panel_9);
		panel_9.setLayout(new GridLayout(6, 0, 0, 0));
		
		JPanel panel_7 = new JPanel();
		panel_9.add(panel_7);
		FlowLayout flowLayout_4 = (FlowLayout) panel_7.getLayout();
		flowLayout_4.setAlignment(FlowLayout.LEFT);
		
		JButton btnCompress = new JButton("Compress!!!");
		btnCompress.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					Main.limit = Integer.parseInt(tfIterationLimit.getText());
					Main.blockSize = Integer.parseInt(tfBlockSize.getText());
					Main.kohonenSizeHeight = Integer.parseInt(tfKohonenSizeX.getText());
					Main.kohonenSizeWidth = Integer.parseInt(tfKohonenSizeY.getText());
					Main.L0 = Double.parseDouble(tfLerningRateL0.getText());
					Main.lambda1 = Double.parseDouble(tfLerningRateLambda.getText());
					Main.S0 = Double.parseDouble(tfNeigbourInfluenceS0.getText());
					Main.lambda2 = Double.parseDouble(tfNeigbourInfluenceLambda.getText());
					Main.D0 = Double.parseDouble(tfLPFradius.getText());
					Main.bytesBefore = 0;
					Main.bytesAfter = 0;
					Main.bytesCodebook = 0;
					
					
					int w = img.getWidth();
        			int h = img.getHeight();
        			
        			// height and width must be devizible by block size
        			w -= w % Main.blockSize;
        			h -= h % Main.blockSize;
        			
        			// get pixels
        			
        			redImage = new int[w][h];
        			greenImage = new int[w][h];
        			blueImage = new int[w][h];
        			
        			redImageOut = new int[w][h];
        			greenImageOut = new int[w][h];
        			blueImageOut = new int[w][h];

        			for(int i=0; i!=w; ++i){
        				for(int j=0; j!= h; ++j){
        					Color color = new Color(img.getRGB(i, j));
        					redImage[i][j] = color.getRed();
        					greenImage[i][j] = color.getGreen();
        					blueImage[i][j] = color.getBlue();
        				}
        			}
					
					
					Main.process(redImage, redImageOut);
					Main.process(greenImage, greenImageOut);
					Main.process(blueImage, blueImageOut);
					
					BufferedImage image1 = Main.createImage(redImage, greenImage, blueImage);
					BufferedImage image2 = Main.createImage(redImageOut, greenImageOut, blueImageOut);
					
					ShowImage panel1 = new ShowImage(image1);
					ShowImage panel2 = new ShowImage(image2);
					
					panelImages.removeAll();
					panelImages.add(panel1);
					panelImages.add(panel2);
					panelImages.repaint();
					
					lblBytesBefore.setText(Integer.toString(Main.bytesBefore));
					lblBytesAfter.setText(Integer.toString(Main.bytesAfter));
					lblBytesCodebook.setText(Integer.toString(Main.bytesCodebook));
					double compressionRate = (100.0*(Main.bytesAfter+Main.bytesCodebook))/Main.bytesBefore;
					lblCompression.setText(Double.toString((compressionRate)) + "%");
					
				} catch (Exception ex) {
					lblErrorMessage.setText(ex.toString());
				}

			}
		});
		panel_7.add(btnCompress);
		
		JPanel panel = new JPanel();
		panel_9.add(panel);
		FlowLayout flowLayout_5 = (FlowLayout) panel.getLayout();
		flowLayout_5.setAlignment(FlowLayout.LEFT);
		
		JLabel label111 = new JLabel("Bytes before:");
		panel.add(label111);
		
		lblBytesBefore = new JLabel("0");
		panel.add(lblBytesBefore);
		
		JPanel panel_6 = new JPanel();
		panel_9.add(panel_6);
		FlowLayout flowLayout_6 = (FlowLayout) panel_6.getLayout();
		flowLayout_6.setAlignment(FlowLayout.LEFT);
		
		JLabel label222 = new JLabel("Bytes after:");
		panel_6.add(label222);
		
		lblBytesAfter = new JLabel("0");
		panel_6.add(lblBytesAfter);
		
		JPanel panel_11 = new JPanel();
		FlowLayout flowLayout_8 = (FlowLayout) panel_11.getLayout();
		flowLayout_8.setAlignment(FlowLayout.LEFT);
		panel_9.add(panel_11);
		
		JLabel label333 = new JLabel("Bytes for codebook:");
		panel_11.add(label333);
		
		lblBytesCodebook = new JLabel("0");
		panel_11.add(lblBytesCodebook);
		
		JPanel panel_13 = new JPanel();
		FlowLayout flowLayout_10 = (FlowLayout) panel_13.getLayout();
		flowLayout_10.setAlignment(FlowLayout.LEFT);
		panel_9.add(panel_13);
		
		JLabel label444 = new JLabel("Compression:");
		panel_13.add(label444);
		
		lblCompression = new JLabel("0%");
		panel_13.add(lblCompression);
		
		JPanel panel_10 = new JPanel();
		FlowLayout flowLayout_7 = (FlowLayout) panel_10.getLayout();
		flowLayout_7.setAlignment(FlowLayout.LEFT);
		panel_9.add(panel_10);
		
		JLabel lblError = new JLabel("Error:");
		panel_10.add(lblError);
		
		lblErrorMessage = new JLabel("-");
		panel_10.add(lblErrorMessage);
	}
	
	@Override
	public void paint(Graphics arg0) {
		super.paint(arg0);
	}
	
	public static void main(String[] args) {
		MainFrame mainFrame = new MainFrame();
		mainFrame.setTitle("DataCompression");
		mainFrame.setVisible(true);
	}
}
