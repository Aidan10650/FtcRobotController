package org.firstinspires.ftc.teamcode.Calculators;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Utilities.*;

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


    public static Interfaces.OtherCalc TeleServos(){

        return new Interfaces.OtherCalc(){
            double myProgress;
            @Override
            public double myProgress(Interfaces.MoveData d) {
                return myProgress;
            }

            @Override
            public void CalcOther(Interfaces.MoveData d) {
//                d.robot.hooker.setPosition(d.manip.b() ? 0.5 : d.manip.u() ? 1.0 : d.manip.rt()>0.9 ? 0.25 : 0);
                d.robot.hooker.setPosition((d.manip.rs().x+1)/2);
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
                    d.robot.hooker.setPosition(0.5);
                }
            }
        };
    }


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
                switch(side) {
                    case FRONT:
                        if (startStopDist < d.robot.frontRange.getDistance(DistanceUnit.CM)) {
                            myProgress = 0;
                        } else if (startStopDist > d.robot.frontRange.getDistance(DistanceUnit.CM)) {
                            myProgress = startProgress + (endProgress - startProgress) * ((startStopDist - d.robot.frontRange.getDistance(DistanceUnit.CM))
                                    / (startStopDist - stopStopDist));
                        } else if (stopStopDist > d.robot.frontRange.getDistance(DistanceUnit.CM)) {
                            myProgress = endProgress;
                        }
                        break;
                    case BACK:
                        if (startStopDist < d.robot.backRange.getDistance(DistanceUnit.CM)){
                            myProgress = 0;
                        } else if (startStopDist > d.robot.backRange.getDistance(DistanceUnit.CM)) {
                            myProgress = startProgress + (endProgress - startProgress) * ((startStopDist - d.robot.backRange.getDistance(DistanceUnit.CM))
                                    / (startStopDist - stopStopDist));
                        } else if (stopStopDist > d.robot.backRange.getDistance(DistanceUnit.CM)) {
                            myProgress = endProgress;
                        }
                        break;
                    case RIGHT:
//                        if (startStopDist < d.robot.rightRange.getDistance(DistanceUnit.CM)) {
//                            myProgress = 0;
//                        } else if (startStopDist > d.robot.rightRange.getDistance(DistanceUnit.CM)) {
//                            myProgress = startProgress + (endProgress - startProgress) * ((startStopDist - d.robot.rightRange.getDistance(DistanceUnit.CM))
//                                    / (startStopDist - stopStopDist));
//                        } else if (stopStopDist > d.robot.rightRange.getDistance(DistanceUnit.CM)) {
//                            myProgress = endProgress;
//                        }
                        break;
                    case LEFT:
//                        if (startStopDist < d.robot.leftRange.getDistance(DistanceUnit.CM)) {
//                            myProgress = 0;
//                        } else if (startStopDist > d.robot.leftRange.getDistance(DistanceUnit.CM)) {
//                            myProgress = startProgress + (endProgress - startProgress) * ((startStopDist - d.robot.leftRange.getDistance(DistanceUnit.CM))
//                                    / (startStopDist - stopStopDist));
//                        } else if (stopStopDist > d.robot.leftRange.getDistance(DistanceUnit.CM)) {
//                            myProgress = endProgress;
//                        }
                        break;
                }
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
