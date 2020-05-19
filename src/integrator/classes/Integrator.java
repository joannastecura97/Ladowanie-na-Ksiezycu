package integrator.classes;

import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;
import org.apache.commons.math3.ode.FirstOrderIntegrator;
import org.apache.commons.math3.ode.nonstiff.ClassicalRungeKuttaIntegrator;
import org.apache.commons.math3.ode.sampling.StepHandler;

/**
 * Class used for computing following values of rocket position, mass and velocity.
 */
public class Integrator {

    /**
     * Method used for calculating steps for rocket parameters. It integrates for one second with <code>dt = 0.005</code>.
     * @param stepHandler Step handler for the following values.
     * @param u the amount of fuel burned per second.
     * @param m mass of the rocket.
     * @param v velocity of the rocket.
     * @param h distance between the rocket and the moon.
     */
    public void integrateStep(StepHandler stepHandler, double u, double m, double v, double h){

        FirstOrderDifferentialEquations equations = new RocketMovementEquation(u);

        FirstOrderIntegrator integrator = new ClassicalRungeKuttaIntegrator(0.005);

        double[] xStart = new double[]{h, v, m};
        double[] xStop = new double[]{0, -300000, 1000};

        integrator.addStepHandler(stepHandler);

        double t_START = 0;
        double t_STOP = 0.1;
        integrator.integrate(equations, t_START, xStart, t_STOP, xStop );
    }

}
