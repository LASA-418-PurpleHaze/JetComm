package org.lasarobotics.hazylib.controls;

public class BangBang extends ControlLoop {

    private boolean reversed;

    /**
     * @param isReversed Set to true to keep above a target, false to stay
     * below.
     */
    public BangBang(boolean isReversed) {
        this.reversed = isReversed;
    }

    /**
     * Calculate the voltage that needs to be sent to the motor.
     *
     * @param currentValue The current value of the variable we are trying to
     * control. i.e. the current position of an arm or RPM of a shooter.
     * @return The output to be set to the motor.
     */
    public double calculate(double currentValue) {
        //Update count to decide if done or not.
        doneCount = (Math.abs(targetValue - previousValue) < targetRange) ? doneCount + 1 : 0;

        if (reversed) {
            if (currentValue > targetValue) {
                return minU;
            }
        } else {
            if (currentValue < targetValue) {
                return maxU;
            }
        }

        return 0;
    }

    /**
     * Set whether or not the bangbang is running in reverse.
     *
     * @param reverse
     */
    public void setReversed(boolean reverse) {
        this.reversed = reverse;
    }

    /**
     * Reset the controller. For this very simple controller do nothing.
     */
    @Override
    public void reset() {
    }
}
