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


关于setContentPane()和getContentPane()的应用

       我们可以在 JFrame 对象中添加 AWT 或者 Swing 组件。但是，虽然它有 add 方法，却不能直接用于添加组件，否则会抛出异常——不信就试试。造成这个现象的原因只有一个解释：
                                                           JFrame 不是一个容器，它只是一个框架。那么，应该怎么添加组件呢？

　　JFrame 有一个 Content Pane，窗口能显示的所有组件都是添加在这个 Content Pane 中。JFrame 提供了两个方法：getContentPane 和 setContentPane 就是用于获取和设置其 Content Pane 的。

　　对JFrame添加组件有两种方式：
　　1)用getContentPane()方法获得JFrame的内容面板，再对其加入组件：frame.getContentPane().add(childComponent)


　　2)建立一个Jpanel或JDesktopPane之类的中间容器，把组件添加到容器中，用setContentPane()方法把该容器置为JFrame的内容面板：


　　　　JPanel  contentPane=new  JPanel();
　　　　……//把其它组件添加到Jpanel中;
　　　　frame.setContentPane(contentPane);
　　　　//把contentPane对象设置成为frame的内容面板

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
