package org.firstinspires.ftc.teamcode.ftc10650.Tele;

import org.firstinspires.ftc.teamcode.Calculators.*;
import org.firstinspires.ftc.teamcode.Calculators.Interfaces.MoveData;
import org.firstinspires.ftc.teamcode.Op.ComplexOp;
import org.firstinspires.ftc.teamcode.Utilities.Vector2D;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp(name = "ComplexTele", group = "ftc10650")
public class ComplexTele extends ComplexOp {

    @Override
    public MoveData.StartData startPositionAndOrientation() {
        return new MoveData.StartData(new Vector2D(0,0), -90);
    }

    @Override
    public void body() throws InterruptedException {
        ComplexMove(
//                SpeedCalcs.SetSpeed(1.0),
                SpeedCalcs.JoystickSpeed(),
                MotionCalcs.FieldCentricJoystick(),
                //MotionCalcs.ConstantDistanceToPoint(100, new Vector2D(100,100)),
                //OrientationCalcs.turnWithJoystick(),
                OrientationCalcs.lookToOrientationUnderJoystick(-90),
                /*OrientationCalcs.lookToPointTurnWithBumperTurnWithJoystick(
                        "a",
                        new OrientationCalcs.lookProgress(new Vector2D(0,0),0.95),
                        new OrientationCalcs.lookProgress(new Vector2D(150,150),1.0)),*/
//                OtherCalcs.TeleOpMatch(),
                OtherCalcs.TelemetryPosition(),
                OtherCalcs.Yeetor(),
                OtherCalcs.Intake(),
                OtherCalcs.Wobble(),
                OtherCalcs.Bucket());

/*        ComplexMove(
                SpeedCalcs.SetSpeed(1),
                MotionCalcs.PointMotion(5, new Vector2D(100,100)),
                OrientationCalcs.lookToPoint(new OrientationCalcs.lookProgress(new Vector2D(150,150), 1)),
                OtherCalcs.TapeMeasure());*/
    }
}
