package org.firstinspires.ftc.teamcode.Op;

import org.firstinspires.ftc.teamcode.Calculators.OtherCalcs;
import org.firstinspires.ftc.teamcode.Hardware.SkystoneRobotName_Box.RobotMap;
import org.firstinspires.ftc.teamcode.Utilities.*;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Calculators.Interfaces;
import org.firstinspires.ftc.teamcode.Hardware.Sensors.CompleteController;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.Hardware.MecanumDrive;

import java.io.IOException;
import java.net.*;

public abstract class ComplexOp extends LinearOpMode{

    private MecanumDrive mecanumDrive;

    public void ComplexMove(Interfaces.SpeedCalc speedCalc,
                            Interfaces.MotionCalc motionCalc,
                            Interfaces.OrientationCalc orientationCalc,
                            Interfaces.OtherCalc... otherCalc) throws InterruptedException {

        d.progress = 0;

        Vector2D vector = new Vector2D();

        float endGameTime = 0;

        d.lastCommand = d.currentCommand;
        d.currentCommand = new Interfaces.MoveData.Command(0, vector,0.0);
        DatagramSocket ds = null;
        try {
            ds = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        while(d.progress < 1.0) {
            //_______________________
            if(ds != null) {
                InetAddress ip = null;
                try {
                    //ip = InetAddress.getByName("192.168.42.131");
                    ip = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                String str = String.valueOf(Math.round(d.wPos.x))+":"+String.valueOf(Math.round(d.wPos.y))+":"+String.valueOf(Math.round(d.heading));
                byte[] strBytes = str.getBytes();
                DatagramPacket DpSend =
                        new DatagramPacket(strBytes, strBytes.length, ip, 10650);
                try {
                    ds.send(DpSend);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //_______________________


            d.heading = d.robot.gyro.getHeading();

            Vector2D encoderPre = d.encoderPos.clone();
            d.encoderPos = mecanumDrive.getVectorDistanceCm();
            Vector2D deltaMove = d.encoderPos.getSubtracted(encoderPre);
            deltaMove.rotateBy(Math.toRadians(-d.heading));
            d.preWPos.set(d.wPos);
            d.wPos.add(deltaMove);

            d.currentCommand.orientationSpeed = orientationCalc.CalcOrientation(d);
            d.currentCommand.motionSpeed = motionCalc.CalcMotion(d);
            d.currentCommand.motionSpeed.rotateBy(Math.toRadians(d.heading));
            d.currentCommand.speed = speedCalc.CalcSpeed(d);

            for (Interfaces.OtherCalc calc : otherCalc) calc.CalcOther(d);

            if (d.timeRemainingUntilEndgame >= 0) endGameTime = (float)(Math.round(d.timeRemainingUntilEndgame / 100) / 10.0);

            //could add specific telemetry data to show through an implementation of complexOp

            telemetry.addData("Position: ("+String.valueOf(Math.round(d.wPos.x))+", "+String.valueOf(Math.round(d.wPos.y))+")", "\n"+d.field);
            telemetry.addData("heading", Math.round(d.heading*10)/10.0);
            //telemetry.addData("position", " "+String.valueOf(Math.round(d.wPos.x))+", "+String.valueOf(Math.round(d.wPos.y)));
            telemetry.update();


            mecanumDrive.driveMecanum(
                    d.currentCommand.motionSpeed,
                    d.currentCommand.speed,
                    d.currentCommand.orientationSpeed);


            d.progress = MathUtil.findMaxList(
                    motionCalc.myProgress(d),
                    orientationCalc.myProgress(d),
                    speedCalc.myProgress(d));


            for (Interfaces.OtherCalc calc : otherCalc) d.progress = Math.max(d.progress,calc.myProgress(d));


            if (!opModeIsActive()) throw new InterruptedException();
        }
    }

    //How data is transferred between calculators and complexOp
    private Interfaces.MoveData d = new Interfaces.MoveData();//if you delete this the world will end

    void initHardware(HardwareMap hwMap) {
        d.robot = new RobotMap(hwMap, startPositionAndOrientation().StartHeading);
        d.heading = startPositionAndOrientation().StartHeading;
        mecanumDrive = new MecanumDrive(d.robot);
    }

    public abstract Interfaces.MoveData.StartData startPositionAndOrientation();

    public abstract void body() throws InterruptedException;

    void exit(){//so we don't run into a wall at full speed
        d.robot.bright.setPower(0);
        d.robot.fright.setPower(0);
        d.robot.bleft.setPower(0);
        d.robot.fleft.setPower(0);
    }



    @Override
    public void runOpMode() throws InterruptedException{

        //INITIALIZATION
        telemetry.addData("Initializing", "Started");
        telemetry.update();

        d.isFinished = false;
        d.isStarted = false;

        d.driver = new CompleteController();
        d.manip = new CompleteController();

        d.driver.CompleteController(gamepad1);
        d.manip.CompleteController(gamepad2);

        initHardware(hardwareMap);

        //START POSITION
        if (d.startData == null) {
            d.startData = this.startPositionAndOrientation();
            d.preWPos.set(d.startData.StartPos.clone());
            d.wPos.set(d.startData.StartPos.clone());
        }

        final Interfaces.OtherCalc posDisplay = OtherCalcs.TelemetryPosition();
        posDisplay.CalcOther(d);

        telemetry.addData("Place robot here", "\n"+d.field);
        telemetry.addData("heading"," "+d.startData.StartHeading+" | position: ("+String.valueOf(Math.round(d.startData.StartPos.x))+", "+String.valueOf(Math.round(d.startData.StartPos.y))+")");
        telemetry.update();

        waitForStart();

        d.isStarted = true;

        telemetry.addData("Body", "Started");
        telemetry.update();

        //BODY
        try {
            body();
        } catch (InterruptedException ie) {
            telemetry.addData("Interrupted","Exception");
            telemetry.update();
        }
        telemetry.addData("Body", "Finished");
        telemetry.update();

        //EXIT
        telemetry.addData("Exit", "Started");
        telemetry.update();
        exit();
        d.isFinished = true;
        telemetry.addData("Exit", "Finished");
        telemetry.update();
    }
}
