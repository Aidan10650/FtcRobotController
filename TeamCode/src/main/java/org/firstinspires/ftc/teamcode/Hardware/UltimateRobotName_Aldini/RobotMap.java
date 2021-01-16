package org.firstinspires.ftc.teamcode.Hardware.UltimateRobotName_Aldini;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;
import com.vuforia.Vuforia;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
//import org.firstinspires.ftc.teamcode.Hardware.Sensors.Camera;
//import org.firstinspires.ftc.teamcode.Hardware.Sensors.NavX;
//import org.firstinspires.ftc.teamcode.Hardware.Sensors.VexEncoder;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvInternalCamera;

public class RobotMap {

    public static DcMotor bright, fright, bleft, fleft, shooter, intake, wobble;

    public static DcMotorEx brightEx, frightEx, bleftEx, fleftEx, shooterEx, intakeEx, wobbleEx;

    public static Servo bucket, pusher, graber;

    public static CRServo vex;

    public static BNO055IMU gyro;

    //public static VexEncoder vexCrap;

    public static OpenCvInternalCamera yeetCam;

    public static HardwareMap hw;

    //public static ModernRoboticsI2cRangeSensor frontRange, backRange;

    public RobotMap(HardwareMap hw, double startHeading) {

        this.hw = hw;
        /**
         * @see <a href="https://ftc-tricks.com/dc-motors/"</a>
         * @see DcMotor.RunMode.RUN_USING_ENCODER this implements a PID for all of the motors
         * This elminates the problems such as the inconsistent auto and having to charge the battery to full every use
         * @see DcMotorSimple.Direction.REVERSE is the correct place to change the directions of the motors
         * it should not be done in a higher level code this is the correct spot
         */

        bright = hw.get(DcMotor.class, "bright");
        //RUN_USING_ENCODER gives each motor a PID and ensures the motors run at the same speed every time.
        bright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bright.setDirection(DcMotorSimple.Direction.REVERSE);
        bright.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        brightEx = (DcMotorEx) bright;


        fright = hw.get(DcMotor.class, "fright");
        fright.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fright.setDirection(DcMotorSimple.Direction.REVERSE);
        fright.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frightEx = (DcMotorEx) fright;

        bleft = hw.get(DcMotor.class, "bleft");
        bleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bleft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bleftEx = (DcMotorEx) bleft;

        fleft = hw.get(DcMotor.class, "fleft");
        fleft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fleft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fleftEx = (DcMotorEx) fleft;

        intake = hw.get(DcMotor.class, "intake");
        intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        intake.setDirection(DcMotorSimple.Direction.REVERSE);
        intakeEx = (DcMotorEx) intake;

        shooter = hw.get(DcMotor.class, "shooter");
        shooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        shooter.setDirection(DcMotorSimple.Direction.REVERSE);
        final double NEW_P_YEET = 5.0345;
        final double NEW_I_YEET = 0.3631;
        final double NEW_D_YEET = 0.0000;
        shooterEx  = (DcMotorEx)shooter;
//        int motorIndexYeet = ((DcMotorEx)shooter).getPortNumber();
//        PIDCoefficients pidNewYeet = new PIDCoefficients(NEW_P_YEET, NEW_I_YEET, NEW_D_YEET);
//        shooterEx.setPIDCoefficients(motorIndexYeet, DcMotor.RunMode.RUN_USING_ENCODER, pidNewYeet);

        wobble = hw.get(DcMotor.class, "wobble");
        wobble.setTargetPosition(0);
        wobble.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        wobbleEx = (DcMotorEx) wobble;
        wobbleEx.setVelocity(400);
        final double NEW_P = 12;
        final double NEW_I = 6;
        final double NEW_D = 0.2;
        DcMotorControllerEx motorControllerEx = (DcMotorControllerEx)wobble.getController();
        int motorIndex = ((DcMotorEx)wobble).getPortNumber();
        PIDCoefficients pidNew = new PIDCoefficients(NEW_P, NEW_I, NEW_D);
        motorControllerEx.setPIDCoefficients(motorIndex, DcMotor.RunMode.RUN_USING_ENCODER, pidNew);



        /**
         * @see NavX is constructed with the heading from the beginning of an {@link org.firstinspires.ftc.teamcode.ftc10650.Auto} and
         * {@link org.firstinspires.ftc.teamcode.ftc10650.Tele}
         */
        //gyro = new NavX(hw, "navX",startHeading);

        gyro = hw.get(BNO055IMU.class, "imu");
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();

        parameters.mode                = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled      = false;

        gyro.initialize(parameters);


        //vexCrap = hw.get(VexEncoder.class, "vexCrap");


        /**
         * the servos are not currently in use but this is where the would be initialized
         */
        bucket = hw.get(Servo.class, "bucket");
        pusher = hw.get(Servo.class, "pusher");
        graber = hw.get(Servo.class, "graber");

        vex = hw.get(CRServo.class, "vexLift");


        /**
         * @see frontRange the front of the robots {@link ModernRoboticsI2cRangeSensor} distance sensor
         * @see backRange the back of the robots {@link ModernRoboticsI2cRangeSensor} distance sensor
         * it is expected to add the other sides of the robot later
         */
        //frontRange = hw.get(ModernRoboticsI2cRangeSensor.class, "frontRange");
        //backRange  = hw.get(ModernRoboticsI2cRangeSensor.class, "backRange");
        int cameraMonitorViewId = hw.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hw.appContext.getPackageName());
        yeetCam = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);

      //  yeetCam.initVuforia(hw, true);
        //yeetCam = hw.get(WebcamName.class, "yeetCam");

    }
}
