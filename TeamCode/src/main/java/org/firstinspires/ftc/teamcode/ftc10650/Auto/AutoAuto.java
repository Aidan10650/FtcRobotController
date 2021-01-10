package org.firstinspires.ftc.teamcode.ftc10650.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.Calculators.*;
import org.firstinspires.ftc.teamcode.Calculators.Interfaces.MoveData;
import org.firstinspires.ftc.teamcode.Hardware.Sensors.StackDeterminationPipeline;
import org.firstinspires.ftc.teamcode.Op.ComplexOp;
import org.firstinspires.ftc.teamcode.Utilities.Vector2D;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

@Autonomous(name = "DonutAuto", group = "ftc10650")
public class AutoAuto extends ComplexOp {

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
        ComplexMove(null, null, null, OtherCalcs.GetDonutStack());
        ComplexMove(null,null,null, OtherCalcs.Shoot());

        if(MoveData.stackHeight == 0){
            ComplexMove(SpeedCalcs.SetSpeed(0.2), MotionCalcs.PointMotion(5, new Vector2D(0, 5)), OrientationCalcs.holdHeading());
            ComplexMove(SpeedCalcs.SetSpeed(0.2), MotionCalcs.PointMotion(5, new Vector2D(5, 60)), OrientationCalcs.lookToOrientation(180));
//            ComplexMove(SpeedCalcs.SetSpeed(0.4), null, OrientationCalcs.lookToOrientation(180));  new Vector2D(5, 60) OrientationCalcs.lookToOrientation(180)
            ComplexMove(null, null, OrientationCalcs.holdHeading(), OtherCalcs.SetWobblePosition(40));
        } else if (MoveData.stackHeight == 1) {
            ComplexMove(SpeedCalcs.SetSpeed(0.4), MotionCalcs.PointMotion(5, new Vector2D(10, 50)), OrientationCalcs.holdHeading());
        } else if (MoveData.stackHeight == 4) {
            ComplexMove(SpeedCalcs.SetSpeed(0.4), MotionCalcs.PointMotion(5, new Vector2D(10, 50)), OrientationCalcs.holdHeading());
        }


    }
}
