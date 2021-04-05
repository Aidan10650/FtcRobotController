package org.firstinspires.ftc.teamcode.ftc10650.Auto;

import org.firstinspires.ftc.teamcode.Calculators.*;
import org.firstinspires.ftc.teamcode.Calculators.Interfaces.MoveData;
import org.firstinspires.ftc.teamcode.Op.ComplexOp;
import org.firstinspires.ftc.teamcode.Utilities.Vector2D;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Disabled
@Autonomous(name = "ExampleAuto", group = "ftc10650")
public class ExampleAuto extends ComplexOp {

    @Override
    public MoveData.StartData startPositionAndOrientation() {
        /**
         * @see StartPositionAndOrientation is used at the beginning of every game code
         * this includes tele-op and autonomous
         */
        return new MoveData.StartData(new Vector2D(0,0), 0);
    }

    @Override
    public void body() throws InterruptedException {

        ComplexMove(
                SpeedCalcs.SetProgressSpeed(new SpeedCalcs.ProgressSpeed(0.05, 0.0, SpeedCalcs.ProgressSpeed.timeOrProg.PROG),
                        new SpeedCalcs.ProgressSpeed(0.05, 1.0, SpeedCalcs.ProgressSpeed.timeOrProg.PROG)),
                MotionCalcs.PointMotion(5,
                        new Vector2D(0, 10),
                        new Vector2D(1, 0)),
//                        new Vector2D(20,40)),
                OrientationCalcs.lookToOrientation(0));
//                OtherCalcs.DistanceStop(OtherCalcs.Side.FRONT,25,5,0.95,1.0));

//        ComplexMove(
//                SpeedCalcs.SetSpeed(0.3),
//                MotionCalcs.CurveMotion(200,180,270),
//                OrientationCalcs.turnWithJoystick(),
//                OtherCalcs.DistanceStop(OtherCalcs.Side.FRONT,25,5,0.95,1.0));
    }
}
