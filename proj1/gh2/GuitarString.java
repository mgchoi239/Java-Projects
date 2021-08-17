package gh2;

// TODO: uncomment the following import once you're ready to start this portion

import deque.ArrayDeque;
import deque.Deque;

// TODO: maybe more imports

//Note: This file will not compile until you complete the Deque implementations
public class GuitarString {
    /** Constants. Do not change. In case you're curious, the keyword final
     * means the values cannot be changed at runtime. We'll discuss this and
     * other topics in lecture on Friday. */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor
    private static int cap;

    /* Buffer for storing sound data. */

     private Deque<Double> buffer;

    // TODO: uncomment the following line once you're ready to start this portion

    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {
        // TODO: Create a buffer with capacity = SR / frequency. You'll need to
        //       cast the result of this division operation into an int. For
        //       better accuracy, use the Math.round() function before casting.
        //       Your should initially fill your buffer array with zeros.

        buffer= new ArrayDeque<>();
        cap= (int) Math.round(SR/frequency);
        for (int i=0; i<cap; i++){
            buffer.addLast(0.0);
        }
    }


    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        // TODO: Dequeue everything in buffer, and replace with random numbers
        //       between -0.5 and 0.5. You can get such a number by using:
        //       double r = Math.random() - 0.5;
        /*for (int i = 0; i < 10; i++) {
            double r = Math.random() - 0.5;
            buffer.addFirst(r);
        }*/
        //       Make sure that your random numbers are different from each
        //       other. This does not mean that you need to check that the numbers
        //       are different from each other. It means you should repeatedly call
        //       Math.random() - 0.5 to generate new random numbers for each array index.
        for (int i=0; i<cap; i++){
            buffer.removeFirst();
        }
        for (int i=0; i<cap; i++){
            buffer.addFirst(Math.random()-0.5);
        }

    }

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    public void tic() {
        // TODO: Dequeue the front sample and enqueue a new sample that is
        //       the average of the two multiplied by the DECAY factor.
        //       **Do not call StdAudio.play().**

        buffer.addLast(((buffer.removeFirst()+buffer.get(0))/2.0)*DECAY);
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        return buffer.get(0);
    }
}