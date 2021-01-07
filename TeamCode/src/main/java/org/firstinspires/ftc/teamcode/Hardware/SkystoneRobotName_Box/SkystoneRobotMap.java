package org.firstinspires.ftc.teamcode.Hardware.SkystoneRobotName_Box;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.hardware.*;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Hardware.Sensors.NavX;

public class SkystoneRobotMap {
//
//    public static DcMotor bright, fright, bleft, fleft, intLeft, intRight, lift, rotator;
//    public static Servo gripper, swinger, hooker, booker, servo;
//    public static NavX gyro;
//    public static WebcamName stoneCam;
//    public static ModernRoboticsI2cRangeSensor frontRange, backRange;
//
//    public SkystoneRobotMap(HardwareMap hw, double startHeading) {
//
//
//        /**
//         * @see <a href="https://ftc-tricks.com/dc-motors/"</a>
//         * @see DcMotor.RunMode.RUN_USING_ENCODER this implements a PID for all of the motors
//         * This elminates the problems such as the inconsistent auto and having to charge the battery to full every use
//         * @see DcMotorSimple.Direction.REVERSE is the correct place to change the directions of the motors
//         * it should not be done in a higher level code this is the correct spot
//         */
//        bright  = hw.get(DcMotor.class, "bright");
//        //RUN_USING_ENCODER gives each motor a PID and ensures the motors run at the same speed every time.
//        bright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//
//        fright  = hw.get(DcMotor.class, "fright");
//        fright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//
//        bleft   = hw.get(DcMotor.class, "bleft");
//        bleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        bleft.setDirection(DcMotorSimple.Direction.REVERSE);
//
//        fleft   = hw.get(DcMotor.class, "fleft");
//        fleft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        fleft.setDirection(DcMotorSimple.Direction.REVERSE);
//
//        /**
//         * These are the motors that we are not currently using
//         */
////        lift    = hw.get(DcMotor.class, "lift");
////        rotator = hw.get(DcMotor.class, "rotator");
//
//
////        intLeft = hw.get(DcMotor.class, "intLeft");
////        intRight = hw.get(DcMotor.class, "intRight");
//
////        intRight.setDirection(DcMotorSimple.Direction.REVERSE);
//
//
//        /**
//         * @see NavX is constructed with the heading from the beginning of an {@link org.firstinspires.ftc.teamcode.ftc10650.Auto} and
//         * {@link org.firstinspires.ftc.teamcode.ftc10650.Tele}
//         */
//        gyro = new NavX(hw, "navX",startHeading);
//
//
//        stoneCam = hw.get(WebcamName.class, "stoned cam");
//
//
//        /**
//         * the servos are not currently in use but this is where the would be initialized
//         */
//        gripper = hw.get(Servo.class, "firm grasp");
//        swinger = hw.get(Servo.class, "ragtime");
//        hooker  = hw.get(Servo.class, "hooker");
//        booker  = hw.get(Servo.class,"booker");
//
//
//        /**
//         * @see frontRange the front of the robots {@link ModernRoboticsI2cRangeSensor} distance sensor
//         * @see backRange the back of the robots {@link ModernRoboticsI2cRangeSensor} distance sensor
//         * it is expected to add the other sides of the robot later
//         */
//        frontRange = hw.get(ModernRoboticsI2cRangeSensor.class, "frontRange");
//        backRange  = hw.get(ModernRoboticsI2cRangeSensor.class, "backRange");
//    }
}