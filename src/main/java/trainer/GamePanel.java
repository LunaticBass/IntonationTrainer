
package trainer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import javax.swing.JOptionPane;


public class GamePanel extends javax.swing.JPanel{   
    
    private double currentMarker;  // current position in seconds
    
    private final static double COUNTINGIN = 3; // seconds before record
    private final static double AFTERRECORD = 1; // seconds after record
    private double recordLength; // in seconds
    private final double totalTime;
    
    private final Recorder recorder = new Recorder(this);
    
    private final ArrayList<Double> timeStamps = new ArrayList<>(); 
    private final ArrayList<Double> pitches = new ArrayList<>(); // detected pitches stored in ArrayList
    
    private static final double CENTS_DEVIATION = 30; // tolerated deviation in cents
    private double rootNote;
    private double[] pattern; // pattern stored in array - values in cents 
    private double[] timing; // pattern elements duration - quarter notes
    private double patternLengthInQuarterNotes;
    
    private int highestScore;
    
   
    public GamePanel(double rootNote, double[] timing, double[] pattern, double recordLength) {
        this.rootNote = rootNote;
        this.timing = timing;
        this.pattern = pattern;
        this.recordLength = recordLength;
        
        initComponents();
        
        totalTime = COUNTINGIN + recordLength + AFTERRECORD;
        for(double d : timing){
            patternLengthInQuarterNotes += d;
	} 
    }

    // calculates the difference of the given Hz from the root note in cents
    double hertzToRelativeCents(double refHz, double Hz){
         // c = 1200 × log2(f2 / f1)
        double LOG_TWO = Math.log(2.0);
        double absCent = 1200 * Math.log(Hz / refHz) / LOG_TWO; 
        if (absCent < 0) {
            absCent = Math.abs(1200 + absCent);
        }
        return absCent % 1200.0;
    }    
 
    
@Override
    public void paint(final Graphics g) {
     
        final Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
                        RenderingHints.VALUE_FRACTIONALMETRICS_ON);

        graphics.clearRect(0, 0, getWidth(), getHeight());
        graphics.setColor(new Color(218, 251, 122));
        graphics.fillRect(0, 0, getWidth(), getHeight());
        
     
        
        // horizontal margin
        double margin = 0.1 * getHeight();
        double usableHeight = getHeight() - 2*margin;
        
        // draw line for record start
        graphics.setColor(Color.GREEN);
        int markerCountIn = (int) (COUNTINGIN / totalTime * getWidth());
        graphics.drawLine(markerCountIn, 0, markerCountIn, getHeight());
        int markerEnd = (int)((totalTime - AFTERRECORD) / totalTime* getWidth());
        graphics.drawLine(markerEnd, 0, markerEnd, getHeight());
        
        
        
        // draw patterns
        graphics.setColor(new Color(42, 85, 252));
        double lengthPerQuarterNote = recordLength / patternLengthInQuarterNotes;
        double currentXPosition = COUNTINGIN;
        for(int i = 0 ; i < pattern.length ; i++){
            double lengthInSeconds = timing[i] * lengthPerQuarterNote; // length of pattern in seconds
            
            int patternWidth = (int) ( lengthInSeconds / recordLength * ((getWidth() - markerCountIn) - (getWidth() - markerEnd)));//pixels
            int patternHeight = (int) (CENTS_DEVIATION / 1200.0 * usableHeight); // 1200 cents = 1 octave difference
            
            int patternX = (int) ( (currentXPosition) / recordLength * ((getWidth() - markerCountIn) - (getWidth() - markerEnd)));
            int patternY = getHeight() - (int) (pattern[i] / 1200.0 * usableHeight + margin) - patternHeight;

            graphics.fillRect(patternX, patternY, patternWidth, patternHeight);
           
            currentXPosition += lengthInSeconds; //in seconds
        }
        
        // draw actual position
        graphics.setColor(Color.BLACK);       
        int x = (int) (currentMarker / (float) recordLength * getWidth());
        graphics.drawLine(x, 0, x, getHeight());
        
        // draw detected pitch 
        graphics.setColor(Color.RED);
        for(int i = 0 ; i < pitches.size() ; i++){
            double pitchInCents = pitches.get(i);
            double startTimeStamp = timeStamps.get(i) % recordLength;

            int patternX = (int) (startTimeStamp / (double) recordLength * getWidth());
            int patternY = getHeight() - (int) (pitchInCents / 1200.0 * usableHeight + margin);

            graphics.fillRect(patternX, patternY, 2,2);
        } 
    }
    
   

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

public void setMarker(double timeStamp, float pitch){
    
    currentMarker = timeStamp % totalTime;

    if (currentMarker <= recordLength){
        if(pitch > 20 && pitch < 20000){            
            double pitchInCents = hertzToRelativeCents(rootNote, pitch);                        
            pitches.add(pitchInCents);
            timeStamps.add(timeStamp);
        }       
        repaint();
    } else{
        stop();
    }
}

public void start(){  
    recorder.record();
}

public void stop(){ 
    recorder.finish();
    score(); 
    currentMarker = 0;
    pitches.clear();
    timeStamps.clear();
    repaint();          
}

public void score(){
    int score = 0;
    
    for(int i = 0 ; i < pitches.size() ; i++){
        double currentXPosition = 0;
        double pitchInCents = pitches.get(i);
        double startTimeStamp = timeStamps.get(i) % recordLength;
        double lengthPerQuarterNote = recordLength/patternLengthInQuarterNotes;
        for(int j = 0 ; j < pattern.length ; j++){
            double lengthInSeconds = timing[j] * lengthPerQuarterNote; // length of the given pattern
            if(startTimeStamp > currentXPosition && startTimeStamp <= currentXPosition + lengthInSeconds 
                    && Math.abs(pitchInCents-pattern[j]) < 30){
                score++;
            }
            currentXPosition += lengthInSeconds; //in seconds
        }
    }
    if (score > highestScore){
        highestScore = score;
    }
    String infoMessage = "Score: " + score + "\n Highest score: " + highestScore;
    JOptionPane.showMessageDialog(null, infoMessage, "Score: ", JOptionPane.INFORMATION_MESSAGE);
    
    int retry = JOptionPane.showConfirmDialog(null, "Retry challenge?", "Retry", JOptionPane.YES_NO_OPTION);
    if (retry == 0){
        this.start();
    }else{
        JOptionPane.showMessageDialog(null, "Thank you for playing!", "Exit", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }
    
}

// setters
    public void setRecordLength(double recordLength) {
        this.recordLength = recordLength;
    }

    public void setRootNote(double rootNote) {
        this.rootNote = rootNote;
    }

    public void setPattern(double[] pattern) {
        this.pattern = pattern;
    }

    public void setTiming(double[] timing) {
        this.timing = timing;
    }
}
