package integrator.classes;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;
/* <a href="http://commons.apache.org/proper/commons-math/">LINK</a> */

/**
 * Class used for storing movement equations of the rocket. It implements interface <code>FirstOrderDifferentialEquations</code>
 * present in repository <a href="http://commons.apache.org/proper/commons-math/">apache commons math</a>.
 */
public class RocketMovementEquation implements FirstOrderDifferentialEquations {

    /**
     * Final field describing the gravity constant on the moon.
     */
    private final double G = 1.63;

    /**
     * Final field representing velocity of gases produces by the rocket.
     */
    private final double k = 636.0;

    /**
     * Field describing the amount of fuel burned per second.
     */
    private double u ;

    /**
     * Constructor setting all fields.
     * @param u the amount of fuel burned per second.
     */
    public RocketMovementEquation(double u) {
        this.u = u;
    }

    /**
     * Method used for returning the length of the differential equation vector.
     * @return length of the differential equation vector.
     */
    @Override
    public int getDimension() {
        return 3;
    }

    /**
     * Method used for computing derivatives of the differential equations describing rocket movement.
     * @param t time
     * @param x initial conditions
     * @param dxdt differential equations
     * @throws MaxCountExceededException if the number of functions evaluations is exceeded
     * @throws DimensionMismatchException if arrays dimensions do not match equations settings
     */
    @Override
    public void computeDerivatives(double t, double[] x, double[] dxdt) throws MaxCountExceededException, DimensionMismatchException {
        dxdt[0] = x[1];
        dxdt[1] = -G-k*u/x[2];
        dxdt[2] = u;
    }
}
