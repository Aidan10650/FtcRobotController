package org.firstinspires.ftc.teamcode.Calculators;

import org.firstinspires.ftc.teamcode.Utilities.PID;
import org.firstinspires.ftc.teamcode.Utilities.TimeUtil;
import org.firstinspires.ftc.teamcode.Utilities.Vector2D;

public class OrientationCalcs {

    public static Interfaces.OrientationCalc GameOrient(){
        Vector2D ultimatePoint = new Vector2D(128, 330);
        final Interfaces.OrientationCalc lookUltimate = lookToPoint(new lookProgress(ultimatePoint,  1.0));
        Vector2D power1Point = new Vector2D(110, 330);
        Vector2D power2Point = new Vector2D(100, 330);
        Vector2D power3Point = new Vector2D(90, 330);
        final Interfaces.OrientationCalc[] lookPower = {
                lookToPoint(new lookProgress(power1Point,  1.0)),
                lookToPoint(new lookProgress(power2Point,  1.0)),
                lookToPoint(new lookProgress(power3Point,  1.0))};
        final Interfaces.OrientationCalc joystick = turnWithJoystick();
        return new Interfaces.OrientationCalc() {
            boolean aHasUp = true, turnToGoal = true;
            boolean bHasUp = true, turnToPower = false;
            boolean rbHasUp = true, lbHasUp = true;
            int currPower = 0;
            @Override
            public double CalcOrientation(Interfaces.MoveData d) {
                if (!d.manip.a()) aHasUp = true;
                if (!d.manip.b()) bHasUp = true;
                if (!d.manip.rb()) rbHasUp = true;
                if (!d.manip.lb()) lbHasUp = true;
                if (d.manip.a() && aHasUp){
                    aHasUp = false;
                    turnToGoal = !turnToGoal;
                    turnToPower = false;
                }
                if (d.manip.b() && bHasUp){
                    bHasUp = false;
                    turnToPower = !turnToPower;
                    turnToGoal = false;
                }


                if(d.manip.rb() && rbHasUp) {currPower+=2;rbHasUp=false;}
                if(d.manip.lb() && lbHasUp) {++currPower;lbHasUp=false;}
                currPower = Math.abs(currPower);
                currPower %= 3;
                if(turnToGoal) {
                    return lookUltimate.CalcOrientation(d);
                } else if (turnToPower){
                    return lookPower[currPower].CalcOrientation(d);
                } else {
                    return joystick.CalcOrientation(d);
                }
            }

            @Override
            public double myProgress(Interfaces.MoveData d) {
                return 0;
            }
        };
    }

    public static Interfaces.OrientationCalc turnWithJoystick(){

        return new Interfaces.OrientationCalc(){
            @Override
            public double myProgress(Interfaces.MoveData d) {
                return 0;
            }

            @Override
            public double CalcOrientation(Interfaces.MoveData d){
                return d.driver.rs().x;
            }
        };
    }


    /**
     * This in the future will use a PID to hold the orientation but currently it is returning a turning speed of zero
     * @return a power that will keep the orientation straight
     */
    public static Interfaces.OrientationCalc holdHeading(){

        return new Interfaces.OrientationCalc(){
            @Override
            public double myProgress(Interfaces.MoveData d) {
                return 0;
            }

            @Override
            public double CalcOrientation(Interfaces.MoveData d){
                return 0.0;
            }
        };
    }


    /**
     * This is spinning without any pid just a power
     * @param speed this is the power that the robot will turn at
     * @return the speed the robot will turn at
     */
    public static Interfaces.OrientationCalc spinSpeed(final double speed){

        return new Interfaces.OrientationCalc(){
            @Override
            public double myProgress(Interfaces.MoveData d) {
                return 0;
            }

            @Override
            public double CalcOrientation(Interfaces.MoveData d){
                return speed;
            }
        };
    }



    public static Interfaces.OrientationCalc waitToTurn(){
        return new Interfaces.OrientationCalc() {
            TimeUtil time = new TimeUtil();
            @Override
            public double CalcOrientation(Interfaces.MoveData d) {
                time.startTimer(15000);
                if(time.timerDone()) return (0-d.heading)*d.orientationP;
                else return 0;
            }

            @Override
            public double myProgress(Interfaces.MoveData d) {
                return 0;
            }
        };
    }
    /**
     * spinToProgress
     * @param spinData
     * @return
     */
    public static Interfaces.OrientationCalc spinToProgress(final spinProgress... spinData){

        return new Interfaces.OrientationCalc(){
            @Override
            public double myProgress(Interfaces.MoveData d) {
                return 0;
            }

            @Override
            public double CalcOrientation(Interfaces.MoveData d){

                for(int i = 0 ; i < spinData.length ; i++){
                    if(d.progress>=spinData[i].startSpinProgress && d.progress<=spinData[i].endSpinProgress){
                        d.currentSpin = i;
                        d.foundSpin = true;
                    }
                }

                if(d.foundSpin){

                    double currSpinProgress = (d.progress - spinData[d.currentSpin].startSpinProgress)/
                            (spinData[d.currentSpin].endSpinProgress-spinData[d.currentSpin].startSpinProgress);
                    double currSpinTo = currSpinProgress*spinData[d.currentSpin].spinTo;
                    d.orientationError = d.heading-currSpinTo;
                } else {
                    d.orientationError = 0;
                }

                d.foundSpin = false;
                return d.orientationError*d.orientationP;
            }
        };
    }

    public static Interfaces.OrientationCalc lookToOrientation(final double orientation){
        return new Interfaces.OrientationCalc() {
            @Override
            public double CalcOrientation(Interfaces.MoveData d) {
                return -(orientation-d.heading)*d.orientationP;
            }

            @Override
            public double myProgress(Interfaces.MoveData d) {
                return 0;
            }
        };
    }

    public static Interfaces.OrientationCalc lookToOrientationUnderJoystick(final double orientation){
        final Interfaces.OrientationCalc lookOrient = lookToOrientation(orientation);
        final Interfaces.OrientationCalc lookJoystick = turnWithJoystick();

        return new Interfaces.OrientationCalc() {
            boolean sb = false;
            boolean ub = true;
            @Override
            public double CalcOrientation(Interfaces.MoveData d) {
                if(!d.driver.b()) ub = true;
                if(d.driver.b()&&ub){sb=!sb; ub=false;}
                d.debugDataBool = sb;
                if(sb) {
                    return lookOrient.CalcOrientation(d);
                }
                else {
                    return lookJoystick.CalcOrientation(d);
                }

            }

            @Override
            public double myProgress(Interfaces.MoveData d) {
                return 0;
            }
        };
    }

    public static Interfaces.OrientationCalc lookToPoint(final lookProgress... point){
        return new Interfaces.OrientationCalc() {
            @Override
            public double CalcOrientation(Interfaces.MoveData d) {
                int pointNum = 0;
                for (int i = 0; i < point.length; i++){
                    if(point[i].untilProgress>d.progress){
                        pointNum = i;
                        break;
                    }
                }

                Vector2D vectorHeading = point[pointNum].point.getSubtracted(d.wPos);
                if (vectorHeading.getLength() == 0) return 0;
                return Vector2D.angleDifferenceDeg(vectorHeading, d.heading) * d.orientationP;
            }

            @Override
            public double myProgress(Interfaces.MoveData d) {
                return 0;
            }
        };
    }

    public static Interfaces.OrientationCalc lookToPower(){
        final Interfaces.OrientationCalc lookOrient = lookToOrientation(0);
        return new Interfaces.OrientationCalc() {
            @Override
            public double CalcOrientation(Interfaces.MoveData d) {
                if(d.driver.x()||d.aimToPowerOverride) {

                    double localHeading = d.heading % 360;
                    if (localHeading > 180.0) {
                        localHeading -= 360.0;
                    } else if (localHeading < -180.0) {
                        localHeading += 360.0;
                    }
                    double error = ((720.0 / 2) + 5.0) - d.powerCenter.y;//offset//5.0 was 10.0
                    if (Math.abs(localHeading - 5) < 30 && d.powerCenter.y >= 0 && d.powerCenter.x >= 0) {
                        //return Math.sqrt(Math.abs(error)) * 0.01 * Math.signum(error);
                        if (Math.abs(error) > 70) error = 90 * Math.signum(error);
                        d.powerError = error;
                        //return Math.signum(error) > 0 ? 0.2 : -0.2;
                        return error*0.0005;
                        //return Math.sqrt(Math.abs(error)) * 0.006 * Math.signum(error);
                    } else if (error<5){
                        return 0.0;
                    } else {
                        return (localHeading - 5) * 0.0025;
                    }
                } else if (d.driver.b()){
                    return lookOrient.CalcOrientation(d);
                } else {
                    return d.driver.rs().x;
                    //double i = Math.signum(d.driver.rs().x);
                    //return Math.pow(Math.abs(d.driver.rs().x), 3)*i;
                }
            }

            @Override
            public double myProgress(Interfaces.MoveData d) {
                return 0;
            }
        };
    }

    public static Interfaces.OrientationCalc lookToGoal(){

        return new Interfaces.OrientationCalc() {
            @Override
            public double CalcOrientation(Interfaces.MoveData d) {
                double avg = d.goalBox.y;
                double targAvg = (56+206)/2;
                double diff = targAvg - avg;
                return diff*0.003;
                //                if(Math.abs(diff)<5){
//                    return 0.0;
//                } else if (diff>10){
//                    return -0.25;
//                } else {
//                    return 0.25;
//                }
            }

            @Override
            public double myProgress(Interfaces.MoveData d) {
                return 0;
            }
        };
    }

    public static Interfaces.OrientationCalc lookToPointUnderJoystickTurn(final String button, final lookProgress... point){
        final Interfaces.OrientationCalc look = lookToPoint(point);
        final Interfaces.OrientationCalc joystick = turnWithJoystick();

        return new Interfaces.OrientationCalc() {
            boolean doLook = true;
            @Override
            public double CalcOrientation(Interfaces.MoveData d) {

                if (Math.abs(d.driver.rs().x) < 0.1 && doLook) {
                    return look.CalcOrientation(d);
                } else {
                    doLook = false;
                    if (d.driver.getButton(button)) doLook = true;
                    return joystick.CalcOrientation(d);
                }
            }

            @Override
            public double myProgress(Interfaces.MoveData d) {
                return 0;
            }
        };
    }


    public static Interfaces.OrientationCalc lookToPointTurnWithBumperTurnWithJoystick(final String button, final lookProgress... point){
        final Interfaces.OrientationCalc bump = turnWithBumpers();
        final Interfaces.OrientationCalc look = lookToPoint(point);
        final Interfaces.OrientationCalc joystick = turnWithJoystick();

        return new Interfaces.OrientationCalc() {
            boolean doLook = true;
            boolean doBump = false;
            @Override
            public double CalcOrientation(Interfaces.MoveData d) {

                boolean neither = true;
                if (Math.abs(d.driver.rs().x) < 0.1 && doLook) {
                    doBump = false;
                    doLook = true;
                    neither = false;
                }
                boolean bumper = d.driver.lb() || d.driver.rb() || doBump;
                if (bumper && Math.abs(d.driver.rs().x) < 0.1){
                    if (d.driver.getButton(button)) {
                        doLook = true;
                        doBump = false;
                    } else {
                        doBump = true;
                        doLook = false;
                    }
                    neither = false;
                }
                if (neither) {
                    doLook = false;
                    doBump = false;

                    if (d.driver.getButton(button)) doLook = true;

                }
                if(doBump) return bump.CalcOrientation(d);
                else if(doLook) return look.CalcOrientation(d);
                else return joystick.CalcOrientation(d);
            }

            @Override
            public double myProgress(Interfaces.MoveData d) {
                return 0;
            }
        };
    }


    public static Interfaces.OrientationCalc turnWithBumpers(){
        return new Interfaces.OrientationCalc() {
            boolean isHoldingLeft = false;
            boolean isHoldingRight = false;
            int turnToAngle = 0;
            @Override
            public double CalcOrientation(Interfaces.MoveData d) {
                if(d.driver.lb() && !isHoldingLeft) {
                    isHoldingLeft = true;
                    turnToAngle--;
                } if(!d.driver.lb()) isHoldingLeft = false;
                if(d.driver.rb() && !isHoldingRight) {
                    isHoldingRight = true;
                    turnToAngle++;
                } if(!d.driver.rb()) isHoldingRight = false;
                turnToAngle %= 4;
                return standardizeAngle(turnToAngle*90 - d.heading) * d.orientationP;
            }

            @Override
            public double myProgress(Interfaces.MoveData d) {
                return 0;
            }
        };
    }



    public static double standardizeAngle(double angle){
        angle %= 360;
        if(angle>180.0){
            return angle-360.0;
        } else if (angle<-180.0){
            return angle+360.0;
        } else {
            return angle;
        }
    }


    public static class lookProgress{
        Vector2D point;
        double untilProgress;
        public lookProgress (Vector2D point, double untilProgress){
            this.point = point;
            this.untilProgress = untilProgress;
        }
    }


    public static class spinProgress{
        double startSpinProgress;
        double endSpinProgress;
        double spinTo;
        public spinProgress(double startSpinProgress, double endSpinProgress, double spinTo){
            this.startSpinProgress = startSpinProgress;
            this.endSpinProgress = endSpinProgress;
            this.spinTo = spinTo;
        }
    }
}
