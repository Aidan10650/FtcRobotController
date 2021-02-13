package org.firstinspires.ftc.teamcode.Calculators;

import org.firstinspires.ftc.teamcode.Hardware.Sensors.GoalPositionPipeline;
import org.firstinspires.ftc.teamcode.Hardware.Sensors.PowerShotPositionPipeline;
import org.firstinspires.ftc.teamcode.Hardware.Sensors.StackDeterminationPipeline;
import org.firstinspires.ftc.teamcode.Utilities.*;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraRotation;

public class OtherCalcs {

    public static Interfaces.OtherCalc whileOpMode(){

        return new Interfaces.OtherCalc(){
            double myProgress;
            @Override
            public double myProgress(Interfaces.MoveData d) {
                return myProgress;
            }

            @Override
            public void CalcOther(Interfaces.MoveData d){
                myProgress = 0.5;
            }
        };
    }

    public static Interfaces.OtherCalc TimeProgress(final long millis){
        return new Interfaces.OtherCalc() {
            long finalMillis = millis + System.currentTimeMillis();
            long initialMillis = System.currentTimeMillis();
            @Override
            public void CalcOther(Interfaces.MoveData d) {

            }

            @Override
            public double myProgress(Interfaces.MoveData d) {
                return (System.currentTimeMillis() - initialMillis)/(finalMillis - initialMillis);
            }
        };
    }

    public static Interfaces.OtherCalc Bucket(){

        return new Interfaces.OtherCalc(){
            double myProgress;
            @Override
            public double myProgress(Interfaces.MoveData d) {
                return myProgress;
            }

            @Override
            public void CalcOther(Interfaces.MoveData d) {
                final double SHOOTPOSITION = 0.25;
                //d.robot.bucket.setPosition((d.MAXBUCKET-d.MINBUCKET)*((d.manip.ls().y+1.0)/2.0)+d.MINBUCKET);
                if(d.manip.rb()) d.robot.bucket.setPosition(SHOOTPOSITION);
                else d.robot.bucket.setPosition(0.68);

                //else d.robot.bucket.setPosition((d.MAXBUCKET-d.MINBUCKET)*(1-d.manip.rt()) + d.MINBUCKET);

                if(d.manip.b()) {
                    d.robot.pusher.setPosition(1.0);
                } else {
                    d.robot.pusher.setPosition(0.0);
                }
            }
        };
    }

    public static Interfaces.OtherCalc Shoot(){
        return new Interfaces.OtherCalc() {
            //double myProg = 0.0;
            boolean prog = false;
            TimeUtil time = new TimeUtil();
            @Override
            public void CalcOther(Interfaces.MoveData d) {
                if(!prog) {
                    d.robot.shooterEx.setVelocity(1850.0);//1820
                    d.robot.bucket.setPosition(0.25);
                    time.startTimer(1000);
                    while (!time.timerDone()) ;
                    //d.robot.shooter.setPower(0.547);
                    time.resetTimer();
                    time.startTimer(1000);
                    while (!time.timerDone()) ;
                    time.resetTimer();
                    time.startTimer(1000);
                    while (!time.timerDone()) {
                        d.robot.pusher.setPosition(1.0);
                    }
                    time.resetTimer();
                    time.startTimer(1000);
                    while (!time.timerDone()) {
                        d.robot.pusher.setPosition(0.0);
                        d.robot.bucket.setPosition(0.40);
                    }
                    time.resetTimer();
                    time.startTimer(1000);
                    while(!time.timerDone()){
                        d.robot.bucket.setPosition(0.25);
                    }
                    time.resetTimer();
                    time.startTimer(1000);
                    while (!time.timerDone()) {
                        d.robot.pusher.setPosition(1.0);
                    }

                    time.resetTimer();
                    time.startTimer(1000);
                    while (!time.timerDone()) {
                        d.robot.pusher.setPosition(0.0);
                        d.robot.bucket.setPosition(0.40);
                    }
                    time.resetTimer();
                    time.startTimer(1000);
                    while(!time.timerDone()){
                        d.robot.bucket.setPosition(0.25);
                    }

                    time.resetTimer();
                    time.startTimer(1000);
                    while (!time.timerDone()) {
                        d.robot.pusher.setPosition(1.0);
                    }
                    time.resetTimer();
                    time.startTimer(1000);
                    while (!time.timerDone()) {
                        d.robot.pusher.setPosition(0.0);
                        d.robot.bucket.setPosition(0.40);
                    }
//                    time.resetTimer();
//                    time.startTimer(1000);
//                    while(!time.timerDone()){
//                        d.robot.bucket.setPosition(0.25);
//                    }
//                    time.resetTimer();
//                    time.startTimer(1000);
//                    while (!time.timerDone()) {
//                        d.robot.pusher.setPosition(1.0);
//                    }
//                    time.resetTimer();
//                    time.startTimer(1000);
//                    while (!time.timerDone()) {
//                        d.robot.pusher.setPosition(0.0);
//                        d.robot.bucket.setPosition(0.40);
//                    }
//                    time.resetTimer();
//                    time.startTimer(1000);
//                    while(!time.timerDone()){
//                        d.robot.bucket.setPosition(0.25);
//                    }
//                    time.resetTimer();
//                    time.startTimer(1000);
//                    while (!time.timerDone()) {
//                        d.robot.pusher.setPosition(1.0);
//                    }
//                    time.resetTimer();
//                    time.startTimer(1000);
//                    while (!time.timerDone()) {
//                        d.robot.pusher.setPosition(0.0);
//                        d.robot.bucket.setPosition(0.40);
//                    }
//                    time.resetTimer();
//                    time.startTimer(1000);
//                    while(!time.timerDone()){
//                        d.robot.bucket.setPosition(0.25);
//                    }
//                    d.robot.shooter.setPower(0.0);
                    d.robot.shooterEx.setVelocity(0.0);
                    prog = true;
                }
            }

            @Override
            public double myProgress(Interfaces.MoveData d) {
                if(prog)return 1.0;
                return 0.0;
            }
        };
    }
    public static Interfaces.OtherCalc Intake(){
        return new Interfaces.OtherCalc() {
            @Override
            public void CalcOther(Interfaces.MoveData d) {
                if(d.manip.a()) d.robot.intakeEx.setVelocity(600);
                else if(d.manip.y()) d.robot.intakeEx.setVelocity(-1600);
                else d.robot.intake.setPower(0.0);

            }

            @Override
            public double myProgress(Interfaces.MoveData d) {
                return 0;
            }
        };
    }

    public static Interfaces.OtherCalc PIDTest(){
        return new Interfaces.OtherCalc() {
            @Override
            public void CalcOther(Interfaces.MoveData d) {
                if(d.manip.u()){

                }
            }

            @Override
            public double myProgress(Interfaces.MoveData d) {
                return 0;
            }
        };
    }

    public static Interfaces.OtherCalc Lift(){
        return new Interfaces.OtherCalc() {
            @Override
            public void CalcOther(Interfaces.MoveData d) {
                d.robot.vex.setPower(d.manip.ls().y);
            }

            @Override
            public double myProgress(Interfaces.MoveData d) {
                return 0;
            }
        };
    }

    public static Interfaces.OtherCalc Yeetor(){
        return new Interfaces.OtherCalc() {
            @Override
            public void CalcOther(Interfaces.MoveData d) {

//                if(d.manip.lb()) d.robot.shooter.setPower(1.0);
//                else d.robot.shooter.setPower(d.manip.lt());
                if(d.manip.ls().y>0.5) d.robot.shooterEx.setVelocity(1750.0);
                else if (d.manip.ls().y<-0.5) d.robot.shooterEx.setVelocity(1500);
                else d.robot.shooterEx.setVelocity(2200*d.manip.lt());

            }


            @Override
            public double myProgress(Interfaces.MoveData d) {
                return 0;
            }
        };
    }

    public static Interfaces.OtherCalc Wobble(){
        return new Interfaces.OtherCalc() {
            boolean dx = true;
            boolean grab = false;
            short i = 0;
            @Override
            public void CalcOther(Interfaces.MoveData d) {
                if(dx&&d.manip.x()){
                    dx=false;
                    i++;
                }
                if(!d.manip.x()) dx = true;
                if(i == 0){
                    d.robot.wobble.setTargetPosition(3);
                } else if (i == 1) {
                    d.robot.wobble.setTargetPosition(550);//520//170
                } //else if (i == 2){
//                    d.robot.wobble.setTargetPosition(0);
//                } else {
//                    d.robot.wobble.setTargetPosition(70);
//                }
                i%=2;
                if(d.manip.l()) grab = true;
                if(d.manip.r()) grab = false;
                if(grab) d.robot.graber.setPosition(0.3);
                else d.robot.graber.setPosition(0);
                //                d.robot.wobble.setPower(d.manip.rs().x/20);
            }

            @Override
            public double myProgress(Interfaces.MoveData d) {
                return 0;
            }
        };
    }
    public static Interfaces.OtherCalc SetWobblePosition(final int wobblePos){
        return new Interfaces.OtherCalc() {
            @Override
            public void CalcOther(Interfaces.MoveData d) {
                d.robot.wobbleEx.setTargetPosition(wobblePos);
            }

            @Override
            public double myProgress(Interfaces.MoveData d) {
                return 0;
            }
        };
    }


    public static Interfaces.OtherCalc ExitAtProgress(final double exitProgress){
        return new Interfaces.OtherCalc() {
            double myProgress;
            @Override
            public void CalcOther(Interfaces.MoveData d) {
                if(d.progress>exitProgress) myProgress = 1;
            }

            @Override
            public double myProgress(Interfaces.MoveData d) {
                return myProgress;
            }
        };
    }


    public static Interfaces.OtherCalc TapeMeasure(){

        return new Interfaces.OtherCalc(){
            double myProgress;
            @Override
            public double myProgress(Interfaces.MoveData d) {
                return myProgress;
            }

            @Override
            public void CalcOther(Interfaces.MoveData d) {
                if(d.progress > 0.95){
                    //d.robot.hooker.setPosition(0.5);
                }
            }
        };
    }

    public static Interfaces.OtherCalc GetDonutStack(){


//                    phoneCam.setViewportRenderingPolicy(OpenCvCamera.ViewportRenderingPolicy.OPTIMIZE_VIEW);
        return new Interfaces.OtherCalc(){
            final StackDeterminationPipeline pipeline = new StackDeterminationPipeline();
            double myProgress = 0;
            boolean first = true;
            @Override
            public double myProgress(Interfaces.MoveData d) {
                return myProgress;
            }

            @Override
            public void CalcOther(final Interfaces.MoveData d) {
                if(first){
                    d.robot.yeetCam.setPipeline(pipeline);
                    d.robot.yeetCam.setViewportRenderingPolicy(OpenCvCamera.ViewportRenderingPolicy.OPTIMIZE_VIEW);
                    d.robot.yeetCam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
                    {
                        @Override
                        public void onOpened() {
                            d.robot.yeetCam.startStreaming(432, 240, OpenCvCameraRotation.SIDEWAYS_LEFT);
                        }
                    });
                    first = false;
                }
                d.stackHeight = pipeline.getHeight();
                myProgress += 0.01;
            }
        };
    }


    public static Interfaces.OtherCalc GetXOfGoal(){
        return new Interfaces.OtherCalc() {
            final GoalPositionPipeline pipeline = new GoalPositionPipeline();
            boolean first = true;
            @Override
            public void CalcOther(final Interfaces.MoveData d) {


                d.goalBox = pipeline.getPos();
                d.hsvValues = pipeline.hsvValues();
                if(first){
                    d.robot.yeetCam.setPipeline(pipeline);
                    d.robot.yeetCam.setViewportRenderingPolicy(OpenCvCamera.ViewportRenderingPolicy.OPTIMIZE_VIEW);
                    d.robot.yeetCam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
                    {
                        @Override
                        public void onOpened() {
                            d.robot.yeetCam.startStreaming(320, 240, OpenCvCameraRotation.SIDEWAYS_LEFT);//320 240
                        }
                    });
                    first = false;
                }
            }

            @Override
            public double myProgress(Interfaces.MoveData d) {
                return 0;
            }
        };
    }

    public static Interfaces.OtherCalc GetPowerPositions(){
        return new Interfaces.OtherCalc() {
            final PowerShotPositionPipeline pipeline = new PowerShotPositionPipeline();
            boolean first = true;
            @Override
            public void CalcOther(final Interfaces.MoveData d) {

                d.powerCenter = pipeline.getPowerCenter();
                if(first){
                    d.robot.yeetCam.setPipeline(pipeline);
                    d.robot.yeetCam.setViewportRenderingPolicy(OpenCvCamera.ViewportRenderingPolicy.OPTIMIZE_VIEW);
                    d.robot.yeetCam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
                    {
                        @Override
                        public void onOpened() {
                            d.robot.yeetCam.startStreaming(1280, 720, OpenCvCameraRotation.SIDEWAYS_LEFT);//320 240 //1280 was 960
                        }
                    });
                    first = false;
                }
            }

            @Override
            public double myProgress(Interfaces.MoveData d) {
                return 0;
            }
        };
    }

    public static Interfaces.OtherCalc SingleShot(final int delay){

        return new Interfaces.OtherCalc() {
            TimeUtil time = new TimeUtil();
            boolean prog = false;
            @Override
            public void CalcOther(Interfaces.MoveData d) {
                time.startTimer(delay);
                d.robot.pusher.setPosition(0.0);
                d.robot.bucket.setPosition(0.25);
                d.robot.shooterEx.setVelocity(1500);
                while(!time.timerDone());
                d.robot.pusher.setPosition(1.0);
                prog = true;
            }

            @Override
            public double myProgress(Interfaces.MoveData d) {
                return prog?1:0;
            }
        };
    }
//    public static Interfaces.OtherCalc GetDonutStack(final HardwareMap hw, final Telemetry telemetry){
//
//            class MyDonutStackOtherCalc implements Interfaces.OtherCalc{
//                telemetry.addData();
//
//                final StackDeterminationPipeline pipeline = new StackDeterminationPipeline();
//                public MyDonutStackOtherCalc(){
//                    phoneCam.setPipeline(pipeline);
//                    phoneCam.setViewportRenderingPolicy(OpenCvCamera.ViewportRenderingPolicy.OPTIMIZE_VIEW);
//
//                    phoneCam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
//                    {
//                        @Override
//                        public void onOpened() {
//                            phoneCam.startStreaming(320, 240, OpenCvCameraRotation.SIDEWAYS_LEFT);
//                        }
//                    });
//                }
//
//
//                @Override
//                public void CalcOther(Interfaces.MoveData d) {
//
//                    d.stackHeight = pipeline.getHeight();
//                    // We set the viewport policy to optimized view so the preview doesn't appear 90 deg
//                    // out when the RC activity is in portrait. We do our actual image processing assuming
//                    // landscape orientation, though.
//
//                }
//
//                @Override
//                public double myProgress(Interfaces.MoveData d) {
//                    return 0;
//                }
//            }
//            return new MyDonutStackOtherCalc();
//    }

    public static Interfaces.OtherCalc TeleOpMatch(){

        final TimeUtil matchTime = new TimeUtil();
        final TimeUtil endGameTime = new TimeUtil();

        return new Interfaces.OtherCalc(){
            private double myProgress;
            private boolean firstLoop = true;
            @Override
            public double myProgress(Interfaces.MoveData d) {
                return myProgress;
            }

            @Override
            public void CalcOther(Interfaces.MoveData d){
                if(firstLoop){
                    endGameTime.startTimer(120000);
                    matchTime.startTimer(150000);
                    firstLoop=false;
                }

                d.timeRemainingUntilEndgame = endGameTime.timeRemaining();
                d.timeRemainingUntilMatch = matchTime.timeRemaining();
                myProgress = 1-(d.timeRemainingUntilMatch/150000);

            }
        };
    }


    public static Interfaces.OtherCalc TelemetryPosition(){
        return new Interfaces.OtherCalc() {
            protected int stringFieldWidth = 13;
            protected int stringFieldHeight = 10;
            @Override
            public void CalcOther(Interfaces.MoveData d) {
                int realFieldWidth = 340;
                int realFieldHeight = 340;
                int adjustedColumn = (int)Math.round((d.wPos.x/realFieldWidth)* stringFieldWidth);
                int adjustedRow = (int)Math.round((d.wPos.y/realFieldHeight)* stringFieldHeight);
                String rval = "";
                for(int row = stringFieldHeight-1; row>-1; row--){
                    if(row == stringFieldHeight-1) rval += "\u2004____________________________\n";
                    rval += "|";

                    for(int col = 0; col< stringFieldWidth; col++){

                        if(row == adjustedRow && col == adjustedColumn){
                            rval += "■";
                        } else {
                            rval += "\u2004\u2002";
                        }

                        if(col == stringFieldWidth-1) {
                            if (row == 0) {
                                rval += "|";
                            } else {
                                rval += "|\n";
                            }
                        }

                    }
                }
//                rval += "___________________________\u2004";
                d.field = rval;
            }

            @Override
            public double myProgress(Interfaces.MoveData d) {
                return 0;
            }
        };
    }


    public static Interfaces.OtherCalc TeleSafe(){
        return new Interfaces.OtherCalc() {
            @Override
            public void CalcOther(Interfaces.MoveData d) {
                /*if(!d.manip.isConnected() && !d.driver.isConnected()) {
                    System.out.println("your skrewed");
                } else{
                    if(!d.manip.isConnected()) {
                        System.out.println("yes");
                    }
                    else if(!d.driver.isConnected()) {
                        System.out.println("no");
                    }
                    else {
                        System.out.println("perfect");
                    }
                }*/

            }

            @Override
            public double myProgress(Interfaces.MoveData d) {
                return 0;
            }
        };
    }


    public static Interfaces.OtherCalc AutoOpMatch(){
        final TimeUtil autoTime = new TimeUtil();

        return new Interfaces.OtherCalc() {
            private double myProgress;
            protected int timeInAuto = 30_000;
            private boolean firstLoop = true;
            @Override
            public void CalcOther(Interfaces.MoveData d) {
                if(firstLoop){
                    autoTime.startTimer(timeInAuto);
                    firstLoop = false;
                }
                myProgress = 1-(autoTime.timeRemaining()/timeInAuto);
            }

            @Override
            public double myProgress(Interfaces.MoveData d) {
                return myProgress;
            }
        };

    }


    public static Interfaces.OtherCalc DistanceStop(final Side side, final double startStopDist, final double stopStopDist, final double startProgress, final double endProgress){

        return new Interfaces.OtherCalc(){
            private double myProgress = 0;
            @Override
            public double myProgress(Interfaces.MoveData d) {
                return myProgress;
            }

            @Override
            public void CalcOther(Interfaces.MoveData d){
//                switch(side) {
//                    case FRONT:
//                        if (startStopDist < d.robot.frontRange.getDistance(DistanceUnit.CM)) {
//                            myProgress = 0;
//                        } else if (startStopDist > d.robot.frontRange.getDistance(DistanceUnit.CM)) {
//                            myProgress = startProgress + (endProgress - startProgress) * ((startStopDist - d.robot.frontRange.getDistance(DistanceUnit.CM))
//                                    / (startStopDist - stopStopDist));
//                        } else if (stopStopDist > d.robot.frontRange.getDistance(DistanceUnit.CM)) {
//                            myProgress = endProgress;
//                        }
//                        break;
//                    case BACK:
//                        if (startStopDist < d.robot.backRange.getDistance(DistanceUnit.CM)){
//                            myProgress = 0;
//                        } else if (startStopDist > d.robot.backRange.getDistance(DistanceUnit.CM)) {
//                            myProgress = startProgress + (endProgress - startProgress) * ((startStopDist - d.robot.backRange.getDistance(DistanceUnit.CM))
//                                    / (startStopDist - stopStopDist));
//                        } else if (stopStopDist > d.robot.backRange.getDistance(DistanceUnit.CM)) {
//                            myProgress = endProgress;
//                        }
//                        break;
//                    case RIGHT:
////                        if (startStopDist < d.robot.rightRange.getDistance(DistanceUnit.CM)) {
////                            myProgress = 0;
////                        } else if (startStopDist > d.robot.rightRange.getDistance(DistanceUnit.CM)) {
////                            myProgress = startProgress + (endProgress - startProgress) * ((startStopDist - d.robot.rightRange.getDistance(DistanceUnit.CM))
////                                    / (startStopDist - stopStopDist));
////                        } else if (stopStopDist > d.robot.rightRange.getDistance(DistanceUnit.CM)) {
////                            myProgress = endProgress;
////                        }
//                        break;
//                    case LEFT:
////                        if (startStopDist < d.robot.leftRange.getDistance(DistanceUnit.CM)) {
////                            myProgress = 0;
////                        } else if (startStopDist > d.robot.leftRange.getDistance(DistanceUnit.CM)) {
////                            myProgress = startProgress + (endProgress - startProgress) * ((startStopDist - d.robot.leftRange.getDistance(DistanceUnit.CM))
////                                    / (startStopDist - stopStopDist));
////                        } else if (stopStopDist > d.robot.leftRange.getDistance(DistanceUnit.CM)) {
////                            myProgress = endProgress;
////                        }
//                        break;
//                }
            }
        };
    }


//    public static Interfaces.OtherCalc TimeProgress(){
//
//        TimeUtil matchTime = new TimeUtil();
//        TimeUtil endGameTime = new TimeUtil();
//
//        return new Interfaces.OtherCalc(){
//            @Override
//            public double myProgress(Interfaces.MoveData d) {
//                return true;
//            }
//
//            @Override
//            public void CalcOther(Interfaces.MoveData d){
//                if(d.firstLoop){
//                    endGameTime.startTimer(120000);
//                    matchTime.startTimer(150000);
//                    d.firstLoop=false;
//                }
//
//                d.timeRemainingUntilEndgame = endGameTime.timeRemaining();
//                d.timeRemainingUntilMatch = matchTime.timeRemaining();
//                d.progress = 1-(d.timeRemainingUntilMatch/150000);
//
//            }
//        };
//    }


    public enum Controller{
        DRIVER,
        MANIP
    }

    public enum Side {
        FRONT,
        BACK,
        RIGHT,
        LEFT
    }

}
