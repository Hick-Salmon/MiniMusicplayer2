package MiniMusicPlayer2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.sound.midi.ControllerEventListener;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MIniMusicPlayer2 {
	static JFrame f = new JFrame("My First Music Player");
	static MyDrawPanel ml;
	
	
	public static void main(String[] args) {
		MIniMusicPlayer2 mini = new MIniMusicPlayer2();
		mini.go();
	}
	
	
	
	public void setUpGui(){
		ml = new MyDrawPanel();
		f.setContentPane(ml);
		f.setBounds(30, 30, 300, 300);
		f.setVisible(true);
	}
	
/*


����setContentPane()��getContentPane()��Ӧ��

       ���ǿ����� JFrame ��������� AWT ���� Swing ��������ǣ���Ȼ���� add ������ȴ����ֱ��������������������׳��쳣�������ž����ԡ������������ԭ��ֻ��һ�����ͣ�
                                                           JFrame ����һ����������ֻ��һ����ܡ���ô��Ӧ����ô�������أ�

����JFrame ��һ�� Content Pane����������ʾ��������������������� Content Pane �С�JFrame �ṩ������������getContentPane �� setContentPane �������ڻ�ȡ�������� Content Pane �ġ�

������JFrame�����������ַ�ʽ��
����1)��getContentPane()�������JFrame��������壬�ٶ�����������frame.getContentPane().add(childComponent)


����2)����һ��Jpanel��JDesktopPane֮����м��������������ӵ������У���setContentPane()�����Ѹ�������ΪJFrame��������壺


��������JPanel  contentPane=new  JPanel();
������������//�����������ӵ�Jpanel��;
��������frame.setContentPane(contentPane);
��������//��contentPane�������ó�Ϊframe���������

 */	
	
	public void go(){
		setUpGui();
		
		try{
			Sequencer sequencer = MidiSystem.getSequencer();
			sequencer.open();
			sequencer.addControllerEventListener(ml, new int[] {127});
			Sequence seq = new Sequence(Sequence.PPQ, 4);
			javax.sound.midi.Track track = seq.createTrack();
			
			int r = 0;
			for(int i = 0; i < 60; i+=4){
				r = (int)((Math.random() * 50) + 1);
				track.add(makeEvent(144, 1, r, 100, i));
				track.add(makeEvent(176, 1, 127, 0, i));
				track.add(makeEvent(128, 1, r, 100, i + 2));
			}
			
			sequencer.setSequence(seq);
			sequencer.setTempoInBPM(120);
			sequencer.start();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	
	
	public MidiEvent makeEvent(int comd, int chan, int one, int two, int tick){
		MidiEvent event = null;
		try{
			ShortMessage a = new ShortMessage();
			a.setMessage(comd, chan, one, two);
			event = new MidiEvent(a, tick);
		}catch(Exception e){}
		return event;
	}
	
	
	
	class MyDrawPanel extends JPanel implements ControllerEventListener{
		
		boolean msg = false;
		
		public void controlChange(ShortMessage arg0) {
			// TODO Auto-generated method stub
			msg = true;
			repaint();
		}
		
		public void paintComponent(Graphics g){
			if(msg){
				
				Graphics2D g2 = (Graphics2D)g;
				g.setColor(Color.white);
				g.fillRect(0, 0, this.getWidth(), this.getHeight());
				
				int r = (int)(Math.random() * 250);
				int gr = (int)(Math.random() * 250);
				int b = (int)(Math.random() * 250);
				
				g.setColor(new Color(r, gr, b));
				
				int ht = (int) ((Math.random() * 120) + 10);
				int width = (int) ((Math.random() * 120) + 10);
				
				int x = (int)((Math.random() * 40) + 10);
				int y = (int)((Math.random() * 40) + 10);
				
				g.fillRect(x, y, ht, width);
				msg = false;
			}
		}
		
	}
}
