
package trainer;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.io.jvm.JVMAudioInputStream;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;
import java.io.ByteArrayOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;


// recorder object, handles the recording of the sound and detect the pitch 
public class Recorder implements PitchDetectionHandler{

    TargetDataLine targetDataLine; 
    SourceDataLine sourceDataLine;    
    ByteArrayOutputStream byteArrayOutputStream;
    boolean vege = false;
    GamePanel gamePanel; 
 
    public Recorder(GamePanel gamePanel){
        this.gamePanel = gamePanel;        
    }
    
    AudioFormat getAudioFormat() {
        float sampleRate = 44100;
        int sampleSizeInBits = 16;
        int channels = 1;
        boolean signed = true;
        boolean bigEndian = false;
        AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits,
                                             channels, signed, bigEndian);
        return format;
    }
    
    void record(){
        try {                   
            int bufferSize = 1536;
            int overlap = 0;
            
            AudioFormat format = getAudioFormat();
            
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            targetDataLine = (TargetDataLine) AudioSystem.getLine(info);           
                        
            final int numberOfSamples = bufferSize;
            targetDataLine.open(format, numberOfSamples);
            targetDataLine.start();
            final AudioInputStream stream = new AudioInputStream(targetDataLine);
            
            JVMAudioInputStream audioStream = new JVMAudioInputStream(stream);
            // create a new dispatcher
            AudioDispatcher dispatcher = new AudioDispatcher(audioStream, bufferSize, overlap);
                        
            dispatcher.addAudioProcessor(new PitchProcessor(PitchProcessor.PitchEstimationAlgorithm.AMDF, format.getSampleRate(), bufferSize, this));
            
            // run the dispatcher (on a new thread).
            new Thread(dispatcher,"Audio dispatching").start();
        } catch (LineUnavailableException ex) {
            Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void finish() {
        targetDataLine.stop();
        targetDataLine.close(); 
    }
    
    // forwards the detected pitch at the detected time
    @Override
    public void handlePitch(PitchDetectionResult pitchDetectionResult, AudioEvent ae) {
        float pitch = pitchDetectionResult.getPitch();
        double timeStamp = ae.getTimeStamp();  
        gamePanel.setMarker(timeStamp, pitch);
    }
}
