import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.net.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Display extends JFrame{
	public String SearchWord;
	public JButton jbtLogin = new JButton("��½");
	public JButton jbtSearch = new JButton("����");
	public JButton jbtRegister = new JButton("ע��");
	public JButton jbtCardPre = new JButton("����");
	public JButton jbtCardCheck = new JButton("�鿴");
	public JButton jbtUser=new JButton("����");
	private JButton jbtzan1=new JButton();
	private JButton jbtzan2=new JButton();
	private JButton jbtzan3=new JButton();
	public JTextField jtfSearchWord = new JTextField(); //��������
	private static DefaultListModel<String> Friend=new DefaultListModel<String>();
	private ArrayList<String> Online=new ArrayList<>();
	private ArrayList<String> Outline=new ArrayList<String>();
	public JList jtaFriend = new JList(Friend); // �����б�
	public JScrollPane jspFriend = new JScrollPane(jtaFriend);
	public JTextField jtfMessage = new JTextField();
	
	public JCheckBox jcbjinshan = new JCheckBox("��ɽ");
	public JCheckBox jcbyoudao = new JCheckBox("�е�");
	public JCheckBox jcbbiying = new JCheckBox("��Ӧ");
	
	public Label friendHint = new Label("�û���Ϣ:");
	public Label inputHint = new Label("���뵥��:");
	public Label cardHint = new Label("���ʿ�:");
	public Label messageHint = new Label("��Ϣ");
	
	public Label lbf1 = new Label();	//������ʾ��һλ
	public JTextArea jtaf1=new JTextArea();
	public JScrollPane jspf1 = new JScrollPane(jtaf1);
	public Label lbf2 = new Label(); //������ʾ�ڶ�λ
	public JTextArea jtaf2 = new JTextArea();
	public JScrollPane jspf2 = new JScrollPane(jtaf2);
	public Label lbf3 = new Label(); //������ʾ����λ
	public JTextArea jtaf3=new JTextArea();
	public JScrollPane jspf3 = new JScrollPane(jtaf3);
	private static ArrayList<ImageIcon> imageList=new ArrayList<ImageIcon>();
	private boolean isLogin=false;
	ObjectInputStream input;
	ObjectOutputStream output;
	private String toDraw="";
	private static String userName;
	public Display() throws UnknownHostException, IOException{
		Socket socket=new Socket("114.212.132.3",8000);
		output=new ObjectOutputStream(socket.getOutputStream());
		input=new ObjectInputStream(socket.getInputStream());
		jspf1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		jspf2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		jspf3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
		//jspFriend.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
		inputHint.setBounds(20,20,60,20);
		jtfSearchWord.setBounds(80,20,120,20);
		jbtSearch.setBounds(200,20,70,20);
		jbtLogin.setBounds(300,20,70,20);
		jbtRegister.setBounds(390,20,70,20);

		jcbjinshan.setBounds(60,60,60,20);
		jcbyoudao.setBounds(140,60,60,20);
		jcbbiying.setBounds(220,60,60,20);
		//Friend1.addElement("hello");
		
		lbf1.setBounds(20,90,40,20);
		jbtzan1.setIcon(new ImageIcon("1.png"));
		jbtzan2.setIcon(new ImageIcon("1.png"));
		jbtzan3.setIcon(new ImageIcon("1.png"));
		jbtzan1.setBounds(20, 110, 32, 32);
		lbf2.setBounds(20,180,40,20);
		jbtzan2.setBounds(20, 200, 32, 32);
		jbtzan3.setBounds(20, 290, 32, 32);
		lbf3.setBounds(20,270,40,20);
		
		jspf1.setBounds(60,90,210,80);
		jspf2.setBounds(60,180,210,80);
		jspf3.setBounds(60,270,210,80);
		
		friendHint.setBounds(300,60,80,20);
		jbtUser.setBounds(400, 60, 60, 20 );
		jspFriend.setBounds(300,90,160,220);
		jtaf1.setLineWrap(true);
		jtaf2.setLineWrap(true);
		jtaf3.setLineWrap(true);
		cardHint.setBounds(295,330,40,20);
		jbtCardPre.setBounds(340,330,60,20);
		jbtCardCheck.setBounds(400,330,60,20);
		
		messageHint.setBounds(20,375,40,20);
		jtfMessage.setBounds(60,375,400,20);
		setLayout(null);
		
		add(jspf1);
		add(jspf2);
		add(jspf3);
		add(lbf1);
		add(lbf2);
		add(lbf3);
		add(inputHint);
		add(jbtLogin);
		add(jbtSearch);
		add(jbtRegister);
		add(jtfSearchWord);
		add(jcbjinshan);
		add(jcbyoudao);
		add(jcbbiying);
		add(jbtUser);
		add(jspFriend);
		add(friendHint);
		add(jbtCardPre);
		add(jbtCardCheck);
		add(cardHint);
		add(messageHint);
		add(jtfMessage);
		add(jbtzan1);
		add(jbtzan2);
		add(jbtzan3);
		jbtLogin.addActionListener(new loginButtonListener());
		jbtRegister.addActionListener(new registerButtonListener());
		jbtCardPre.addActionListener(new cardpreButtonListener());
		jbtCardCheck.addActionListener(new cardcheckButtonListener());
		jbtSearch.addActionListener(new searchListener());
		jbtzan1.addActionListener(new ZanListener1());
		jbtzan2.addActionListener(new ZanListener2());
		jbtzan3.addActionListener(new ZanListener3());
		jbtUser.addActionListener(new LineListener());
		Listener lis=new Listener();
		Thread th=new Thread(lis);
		th.start();
	}
	
	public class LineListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(isLogin){
				String now=jbtUser.getText().trim();
				if(now.equals("����")){
					jbtUser.setText("����");
					Friend.clear();
					for(int i=0;i<Outline.size();i++){
						Friend.addElement(Outline.get(i));
					}
				}
				else if(now.equals("����")){
					jbtUser.setText("����");
					Friend.clear();
					for(int i=0;i<Online.size();i++){
						Friend.addElement(Online.get(i));
					}
				}
			}
			else{
				jtfMessage.setText("���ȵ�¼");
			}
		}
	}

	public class ZanListener1 implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(isLogin){
				try {
					output.writeUTF("like");
					output.writeUTF(jtfSearchWord.getText().trim());
					if(lbf1.getText().equals("��ɽ")){
						output.writeUTF("jinshan");
					}
					else if(lbf1.getText().equals("�е�")){
						output.writeUTF("youdao");
					}
					else {
						output.writeUTF("bing");
					}
					output.flush();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else{
				jtfMessage.setText("���ȵ�¼���ٵ���");
			}
		}
	}
	
	public class ZanListener2 implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(isLogin){
				try {
					output.writeUTF("like");
					output.writeUTF(jtfSearchWord.getText().trim());
					if(lbf2.getText().equals("��ɽ")){
						output.writeUTF("jinshan");
					}
					else if(lbf2.getText().equals("�е�")){
						output.writeUTF("youdao");
					}
					else {
						output.writeUTF("bing");
					}
					output.flush();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else{
				jtfMessage.setText("���ȵ�¼���ٵ���");
			}
		}
	}
	
	public class ZanListener3 implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(!isLogin){
				jtfMessage.setText("���ȵ�¼���ٵ���");
			}
			else{
				try {
					output.writeUTF("like");
					output.writeUTF(jtfSearchWord.getText().trim());
					if(lbf3.getText().equals("��ɽ")){
						output.writeUTF("jinshan");
					}
					else if(lbf3.getText().equals("�е�")){
						output.writeUTF("youdao");
					}
					else {
						output.writeUTF("bing");
					}
					output.flush();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
	
	public class searchListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			toDraw="";
			lbf1.setText(null);
			lbf2.setText(null);
			lbf3.setText(null);
			jtaf1.setText(null);
			jtaf2.setText(null);
			jtaf3.setText(null);
			String word=jtfSearchWord.getText().trim(); 
			if(!word.matches("[a-zA-z]+")){
				jtfMessage.setText("��������ݲ��ǵ��ʣ�����������");
				jtfSearchWord.setText("");
				return ;
			}
			SearchWord=word;
			Search s=new Search();
			if(!isLogin&&!word.equals("")){
				try {
					String aJinshan=s.Jinshan(word);
					String ayoudao=s.Youdao(word);
					if(ayoudao.equals("")){
						jtfMessage.setText("δ�ҵ�����");
						return ;
					}
					String aBing=s.Bing(word);
					
					if(jcbjinshan.isSelected()){
						lbf1.setText("��ɽ");
						jtaf1.setText(aJinshan);
						if(jcbyoudao.isSelected()){
							lbf2.setText("�е�");
							jtaf2.setText(ayoudao);
							if(jcbbiying.isSelected()){
								lbf3.setText("��Ӧ");
								jtaf3.setText(aBing);
							}
						}
						else{
							if(jcbbiying.isSelected()){
								lbf2.setText("��Ӧ");
								jtaf2.setText(aBing);
							}
						}
					}
					else{
						if(jcbyoudao.isSelected()){
							lbf1.setText("�е�");
							jtaf1.setText(ayoudao);
							if(jcbbiying.isSelected()){
								lbf2.setText("��Ӧ");
								jtaf2.setText(aBing);
							}
						}
						else{
							if(jcbbiying.isSelected()){
								lbf1.setText("��Ӧ");
								jtaf1.setText(aBing);
							}
						}
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else{
				try {
					output.writeUTF("search");
					output.writeUTF(word);
					output.flush();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
	
	public class loginButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(isLogin){
				jbtLogin.setText("��¼");
				Friend.clear();
				isLogin=false;
				try {
					output.writeUTF("logout");
					output.flush();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else{
				Login log=new Login();
				log.pack();
				log.setTitle("��¼");
				log.setSize(300,230);
				log.setLocationRelativeTo(null);
				log.setVisible(true);
			}
		}
	}
	
	public class registerButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(!isLogin){
				Register log=new Register();
				log.pack();
				log.setTitle("ע��");
				log.setSize(300,230);
				log.setLocationRelativeTo(null);
				//log.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				log.setVisible(true);
			}
		}
	}
	
	public class cardpreButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			/*���ʿ�Ԥ���¼�*/
			if(!isLogin){
				jtfMessage.setText("���ȵ�½");
			}
			else{
				if(toDraw.equals("")){
					
				}
				else{
					SeedCard card=new SeedCard();
					card.pack();
					card.setTitle("Card");
					card.setSize(400,450);
					card.setLocationRelativeTo(null);
					card.setVisible(true);
				}
			}
		}
	}
	
	public class cardcheckButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			/*�鿴���ʿ��¼�*/
			if(!isLogin){
				jtfMessage.setText("���ȵ�½");
			}
			else if(imageList.size()>0){
				jtfMessage.setText("");
				Card card=new Card();
				card.pack();
				card.setTitle("Card");
				card.setSize(360,380);
				card.setLocationRelativeTo(null);
				card.setVisible(true);
			}
			else if(imageList.size()==0){
				jtfMessage.setText("���ʿ�Ϊ�գ�û�к������㷢�͵��ʿ�");
			}
		}
	}
	public Image newImage(){
		int imageWidth=300;
		int imageHeight=300;
		Image image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
		Graphics graphics = image.getGraphics();
		graphics.setColor(new Color(43,52,69));
		graphics.fillRect(0, 0, imageWidth, imageHeight);
		graphics.setColor(Color.WHITE);
		String[] draw=toDraw.split("[\n\t]");
		graphics.drawString(jtfSearchWord.getText().trim(),50,20);
		//
		int i=0;
		for(;i<draw.length;i++)
			graphics.drawString(draw[i],10, 45+i*15);
		graphics.drawString("�����ˣ�",10, 290);
		graphics.drawString(userName,60, 290);
		toDraw="";
		return image;
	}
	public static void main(String[] args) throws UnknownHostException, IOException{
		JFrame MainWindow = new Display();
		//MainWindow.getContentPane().setBackground(new Color(43,52,69));
		MainWindow.pack();
		MainWindow.setTitle("Dictionary");
		MainWindow.setSize(500,440);
		MainWindow.setLocationRelativeTo(null);
		MainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MainWindow.setVisible(true);
	}
	class SeedCard extends JFrame{
		private JLabel lable1=new JLabel();
		private JTextArea jta=new JTextArea();
		private JLabel lab2=new JLabel("�ռ���");
		private JButton bt1=new JButton("ѡ��");
		private JButton bt2=new JButton("����");
		private JButton bt3=new JButton("ȡ��");
		Image img=newImage();
		private ArrayList<String> accepter=new ArrayList<String>();
		public SeedCard(){
			setLayout(null);
			lable1.setIcon(new ImageIcon(img));
			lable1.setBounds(50, 10, 300, 300);
			lab2.setBounds(40, 320, 40, 40);
			jta.setBounds(90, 320, 190, 40);
			bt1.setBounds(290, 320, 80, 30);
			bt2.setBounds(100, 380, 90, 20);
			bt3.setBounds(210, 380, 90, 20);
			add(lable1);
			add(lab2);
			add(jta);
			add(bt1);
			add(bt2);
			add(bt3);
			bt1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					accepter.clear();
					Choice ch=new Choice();
					ch.pack();
					ch.setTitle("ѡ�����");
					ch.setSize(280,380);
					ch.setLocationRelativeTo(null);
					ch.setVisible(true);
				}
			});
			bt2.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if(accepter.size()>0){
						try {
							for(int i=0;i<accepter.size();i++){
								output.writeUTF("card");
								output.writeUTF(accepter.get(i));
								output.writeObject(new ImageIcon(img));
							}
							output.flush();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					setVisible(false);
				}
			});
			bt3.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					setVisible(false);
				}
			});
		}
		class Choice extends JFrame{
			private JList list1=new JList(Friend);
			private JScrollPane jsplist1=new JScrollPane(list1);
			private JLabel lab1=new JLabel("��ѡ");
			private JTextArea jta2=new JTextArea();
			private JButton bt4=new JButton("ȷ��");
			private JButton bt5=new JButton("ȡ��");
			ArrayList<Integer> AccIndex=new ArrayList<Integer>(); 
			public Choice(){
				setLayout(null);
				jsplist1.setBounds(20, 10, 225, 240);
				lab1.setBounds(10, 260, 40, 20);
				jta2.setBounds(45, 260, 200, 40);
				bt4.setBounds(70, 310, 60, 20);
				bt5.setBounds(140, 310, 60, 20);
				add(jsplist1);
				add(lab1);
				add(jta2);
				add(bt4);
				add(bt5);
				list1.addListSelectionListener(new ListSelectionListener() {
					@Override
					public void valueChanged(ListSelectionEvent arg0) {
						// TODO Auto-generated method stub
						if(list1.getValueIsAdjusting()){
							int index=list1.getLeadSelectionIndex();
							boolean ex=false;
							for(int i=0;i<AccIndex.size();i++){
								if(index==AccIndex.get(i)){
									ex=true;
								}
							}
							if(!ex){
								AccIndex.add(index);
								jta2.append(Friend.elementAt(index));
								jta.append(Friend.elementAt(index)+"��");
								accepter.add(Friend.elementAt(index));
							}
						}
					}
				});
				bt4.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						setVisible(false);
					}
				});
				bt5.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						accepter.clear();
						setVisible(false);
					}
				});
			}
		}
	}
	class Card extends JFrame{
		private JButton bt1=new JButton("previous");
		private JButton bt2=new JButton("next");
		private JLabel lab=new JLabel();
		int index=0;
		public Card(){
			setLayout(null);
			lab.setIcon(imageList.get(index));
			lab.setBounds(30, 10, 300, 300);
			bt1.setBounds(70, 310, 100, 25);
			bt2.setBounds(190, 310, 100, 25);
			add(lab);
			add(bt1);
			add(bt2);
			bt1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(index==0){
						index=imageList.size()-1;
						lab.setIcon(imageList.get(index));
					}
					else{
						index--;
						lab.setIcon(imageList.get(index));
					}
				}
			});
			bt2.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(index==(imageList.size()-1)){
						index=0;
						lab.setIcon(imageList.get(index));
					}
					else{
						index++;
						lab.setIcon(imageList.get(index));
					}
				}
			});
		}
	}
	class Login extends JFrame{
		private JButton bt1=new JButton("��¼");
		private JButton bt2=new JButton("ע��");
		private JTextField t1=new JTextField();
		private JTextField t2=new JTextField();
		public Login(){
			setLayout(null);
			JLabel lab1=new JLabel("�û���");
			JLabel lab2=new JLabel("����");
			lab1.setBounds(40, 30, 60, 25);
			lab2.setBounds(45, 80, 60, 25);
			t1.setBounds(100, 30, 120, 25);
			t2.setBounds(100, 80, 120, 25);
			bt1.setBounds(70, 130, 60, 25);
			bt2.setBounds(160, 130, 60, 25);
			add(lab1);
			add(lab2);
			add(t1);
			add(t2);
			add(bt1);
			add(bt2);
			bt1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					try {
						output.writeUTF("login");
						String str=t1.getText().trim();
						output.writeUTF(str);
						str=t2.getText().trim();
						output.writeUTF(str);
						output.flush();
						setVisible(false);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			bt2.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					setVisible(false);
					Register log=new Register();
					log.pack();
					log.setTitle("ע��");
					log.setSize(300,230);
					log.setLocationRelativeTo(null);
					log.setVisible(true);
				}
			});
		}
	}
	class Register extends JFrame{
		private JButton bt1=new JButton("ע��");
		private JButton bt2=new JButton("ȡ��");
		private JTextField t1=new JTextField();
		private JTextField t2=new JTextField();
		public Register(){
			setLayout(null);
			JLabel lab1=new JLabel("�û���");
			JLabel lab2=new JLabel("����");
			lab1.setBounds(40, 30, 60, 25);
			lab2.setBounds(45, 80, 60, 25);
			t1.setBounds(100, 30, 120, 25);
			t2.setBounds(100, 80, 120, 25);
			bt1.setBounds(70, 130, 60, 25);
			bt2.setBounds(160, 130, 60, 25);
			add(lab1);
			add(lab2);
			add(t1);
			add(t2);
			add(bt1);
			add(bt2);
			bt1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					try {
						String str1=t1.getText().trim();
						String str2=t2.getText().trim();
						if(str1.equals("")||str2.equals("")){
							jtfMessage.setText("�û��������벻��Ϊ��");
						}
						else{
							output.writeUTF("register");
							output.writeUTF(str1);
							output.writeUTF(str2);
							output.flush();
							setVisible(false);
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			bt2.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					setVisible(false);
				}
			});
		}
	}
	class Listener implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				while(true){
					String order=input.readUTF();
					if(order.equals("login")){
						userName=input.readUTF();
						String result=input.readUTF();
						if(result.equals("true")){
							//System.out.println("true");
							jbtLogin.setText("ע��");
							jtfMessage.setText("��¼�ɹ�");
							isLogin=true;
							String number=input.readUTF();
							int num=Integer.parseInt(number);
							for(int i=0;i<num;i++){
								String next=input.readUTF();
								Friend.addElement(next);
								System.out.print(next);
								Online.add(next);
							}
							number=input.readUTF();
							num=Integer.parseInt(number);
							for(int i=0;i<num;i++){
								String next=input.readUTF();
								System.out.print(next);
								Outline.add(next);
							}
						}
						else{
							jtfMessage.setText("��¼ʧ�ܣ���ȷ���û��������벢���µ�¼");
						}
					}
					else if(order.equals("register")){
						String result=input.readUTF();
						if(result.equals("true")){
							jtfMessage.setText("ע��ɹ�");
						}
						else
							jtfMessage.setText("ע��ʧ�ܣ����û���������ʹ�ã�������ע��");
					}
					else if(order.equals("search")){
						String word=input.readUTF();
						ArrayList<String> weblist=new ArrayList<String>();
						for(int i=0;i<3;i++){
							String op=input.readUTF();
							if(op.equals("jinshan")&&jcbjinshan.isSelected()){
								
								weblist.add(op);
							}
							else if(op.equals("youdao")&&jcbyoudao.isSelected()){
								weblist.add(op);
							}
							else if(op.equals("bing")&&jcbbiying.isSelected()){
								weblist.add(op);
							}
						}
						Search str=new Search();
						for(int i=0;i<weblist.size();i++){
							if(weblist.get(i).equals("jinshan")){
								searchSet(str.Jinshan(word),"��ɽ",i);
							}
							else if(weblist.get(i).equals("youdao")){
								searchSet(str.Youdao(word),"�е�",i);
							}
							else{
								searchSet(str.Bing(word),"��Ӧ",i);
							}
						}
					}
					else if(order.equals("card")){
						try {
							ImageIcon img=(ImageIcon) input.readObject();
							imageList.add(img);
							jtfMessage.setText("�����µĵ��ʿ�����ע����գ�");
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		public void searchSet(String result,String web,int i){
			if(i==0){
				lbf1.setText(web);
				jtaf1.setText(result);
				toDraw=toDraw+web+"\n"+result+"\n";
			}
			else if(i==1){
				lbf2.setText(web);
				jtaf2.setText(result);
				toDraw=toDraw+web+"\n"+result+"\n";
			}
			else{
				lbf3.setText(web);
				jtaf3.setText(result);
				toDraw=toDraw+web+"\n"+result+"\n";
			}
		}
	}

}
