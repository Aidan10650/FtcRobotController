package org.firstinspires.ftc.teamcode.Hardware.Sensors;

import org.firstinspires.ftc.robotcore.external.Predicate;
import org.firstinspires.ftc.teamcode.Utilities.Vector2D;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;
import org.opencv.objdetect.Objdetect;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class GoalPositionPipeline extends OpenCvPipeline {

    static class AreaComparator implements Comparator<MatOfPoint> {

        @Override
        public int compare(MatOfPoint t1, MatOfPoint t2) {
            if(t1.empty() || t2.empty()) return 0;
            if(Imgproc.contourArea(t1) > Imgproc.contourArea(t2)) return -1;
            else return 1;
        }
    }

        double[] hsvArr = new double[3];

        Point p = new Point(-1, -1);

        Mat mask = new Mat();


        /*
         * Points which actually define the sample region rectangles, derived from above values
         *
         * Example of how points A and B work to define a rectangle
         *
         *   ------------------------------------
         *   | (0,0) Point A                    |
         *   |                                  |
         *   |                                  |
         *   |                                  |
         *   |                                  |
         *   |                                  |
         *   |                                  |
         *   |                  Point B (70,50) |
         *   ------------------------------------
         *
         */

        float centerXFirst = -1;
        float centerYFirst = -1;

        Mat YCrCb = new Mat();
        Mat Cb = new Mat();

        Rect boundingRect = new Rect(-1,-1,-1,-1);
        Rect boundingRect2 = new Rect(-1,-1,-1,-1);
//        Rect finalBoundingRect = new Rect(-1,-1,-1,-1);


        void inputToCb(Mat input)
        {
            Imgproc.cvtColor(input, YCrCb, Imgproc.COLOR_RGB2YCrCb);
            Core.extractChannel(YCrCb, Cb, 2);
        }

        @Override
        public void init(Mat firstFrame)
        {

            inputToCb(firstFrame);

        }

        @Override
        public Mat processFrame(Mat input)
        {

            final Scalar lower = new Scalar(0, 25, 50);
            final Scalar upper = new Scalar(12, 255, 255);
//            Mat nHSV = new Mat();

//            Rect rectCrop = new Rect(0, 0, input.width()/2, input.height());
//            Mat hsv = new Mat(nHSV, rectCrop);
            Mat hsv = new Mat();
            Imgproc.cvtColor(input, hsv, Imgproc.COLOR_RGB2HSV);

            Core.inRange(hsv, lower, upper, mask);

//            mask = mask.submat(0, mask.rows()/3, 0, mask.cols());

            Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size((24) + 1, (24)+1));
            Imgproc.erode(mask, mask, kernel);
            Imgproc.dilate(mask, mask, kernel);



            List<MatOfPoint> contours = new ArrayList<>();
            Mat hierarchy = new Mat();
            Imgproc.findContours(mask, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
            double maxArea = 0;
            double secondMaxArea = 0;

            MatOfPoint firstContour = new MatOfPoint();
            MatOfPoint secondContour = new MatOfPoint();
            Collections.sort(contours, new AreaComparator());
            List<MatOfPoint> contours2 = new ArrayList<>();
            for(MatOfPoint c:contours) {
                Rect r = Imgproc.boundingRect(c);
                if(r.x > (1280/3)) continue;
                if(r.width < (1280/10)) continue;
                contours2.add(c);
            }
//            for (MatOfPoint contour : contours) {
//                double area = Imgproc.contourArea(contour);
//                if (area > maxArea) {
//                    maxArea = area;
//                    firstContour = contour;
//                } else if (area > secondMaxArea) {
//                    secondMaxArea = area;
//                    secondContour = contour;
//                }
//            }

            if(contours2.size()>0) {
                boundingRect = Imgproc.boundingRect(contours2.get(0));
                p = new Point((boundingRect.tl().x + boundingRect.br().x) / 2.0,
                              (boundingRect.tl().y + boundingRect.br().y) / 2.0);
            }
            if(contours2.size()>1) {
                boundingRect2 = Imgproc.boundingRect(contours2.get(1));
                Point p2 = new Point((boundingRect2.tl().x + boundingRect2.br().x) / 2.0,
                                     (boundingRect2.tl().y + boundingRect2.br().y) / 2.0);
                p.x = (p.x + p2.x)/2.0;
                p.y = (p.y + p2.y)/2.0;
            }


//            Moments mFirst = Imgproc.moments(mask, true);
//
//            centerXFirst = (float)(mFirst.m10 / mFirst.m00);
//            centerYFirst = (float)(mFirst.m01 / mFirst.m00);
//            if(Float.isNaN(centerXFirst)||Float.isNaN(centerYFirst)){
//                centerXFirst = -1;
//                centerYFirst = -1;
//            }

//            hsvArr = hsv.get(120, 160);
//            Imgproc.putText(input, Double.toString(hsvArr[0]) + ' ' + Double.toString(hsvArr[1]) + ' ' + Double.toString(hsvArr[2]), new Point(100, 100), 1, 1, new Scalar( 0, 255, 0));
//            Imgproc.circle(input, new Point(160, 120), 5, new Scalar(255, 0, 0));
//
//            Imgproc.circle(mask, new Point(centerXFirst, centerYFirst), 5, new Scalar(255, 0 ,0));

//            int lowestX = boundingRect.x<boundingRect2.x?boundingRect.x:boundingRect2.x;
//            int lowestY = boundingRect.y<boundingRect2.y?boundingRect.y:boundingRect2.y;
//            int highestX = boundingRect.x+boundingRect.width>boundingRect2.x+boundingRect2.width?boundingRect.x+boundingRect.width:boundingRect2.x+boundingRect2.width;
//            int highestY = boundingRect.y+boundingRect.height>boundingRect2.y+boundingRect2.height?boundingRect.y+boundingRect.height:boundingRect2.y+boundingRect2.height;
//            //new MatOfRect(boundingRect, boundingRect2)
//
//            finalBoundingRect = new Rect (lowestX, lowestY, highestX-lowestX, highestY-lowestY);


            Imgproc.rectangle(mask, boundingRect, new Scalar(100, 255, 255), 10);
//            Imgproc.putText(mask, Integer.toString(lowestX) + " " + Integer.toString(lowestY) + " " + Integer.toString(highestX) + " " + Integer.toString(highestY), new Point(100, 100), 1, 1, new Scalar( 255, 255, 255));
            Imgproc.rectangle(mask, boundingRect2, new Scalar(100, 255, 255), 10);
//            Imgproc.rectangle(mask, finalBoundingRect, new Scalar(50, 50, 50));

            Imgproc.cvtColor(mask, mask, Imgproc.COLOR_GRAY2RGB);


            hsv.release();
            hierarchy.release();
            kernel.release();
//            nHSV.release();

            return mask;
        }

        /*
         * Call this from the OpMode thread to obtain the latest analysis
         */
        public Vector2D getPos(){ return new Vector2D(p.x, p.y); }
        public double[] hsvValues() {return hsvArr;}
}
